(function () {
  const state = {
    logName: "",
    transactions: [],
    filteredTransactions: [],
    selectedId: null,
    sqlModes: new Set(["executing", "executing batch", "batching"]),
  };

  const fileInput = document.getElementById("fileInput");
  const pasteArea = document.getElementById("pasteArea");
  const dropZone = document.getElementById("dropZone");
  const loadSampleButton = document.getElementById("loadSampleButton");
  const clearButton = document.getElementById("clearButton");
  const searchInput = document.getElementById("searchInput");
  const statusFilter = document.getElementById("statusFilter");
  const txCount = document.getElementById("txCount");
  const txList = document.getElementById("txList");
  const summary = document.getElementById("summary");
  const emptyState = document.getElementById("emptyState");
  const detailPanel = document.getElementById("detailPanel");
  const detailTitle = document.getElementById("detailTitle");
  const detailSubtitle = document.getElementById("detailSubtitle");
  const detailStats = document.getElementById("detailStats");
  const identifierGrid = document.getElementById("identifierGrid");
  const timeline = document.getElementById("timeline");
  const sqlList = document.getElementById("sqlList");
  const sqlFilterMeta = document.getElementById("sqlFilterMeta");
  const copyTxIdButton = document.getElementById("copyTxIdButton");
  const copyXidButton = document.getElementById("copyXidButton");
  const summaryCardTemplate = document.getElementById("summaryCardTemplate");
  const sqlModeInputs = Array.from(document.querySelectorAll(".mode-filter-option input"));
  const panelToggles = [
    ["transactionsToggle", "transactionsPanelBody"],
    ["identifiersToggle", "identifiersPanelBody"],
    ["timelineToggle", "timelinePanelBody"],
    ["sqlToggle", "sqlPanelBody"],
  ];

  const headerRegex =
    /^\[(\d{1,2})\/(\d{1,2})\/(\d{2}) (\d{2}):(\d{2}):(\d{2}):(\d{3}) ([A-Z]+)\]\s+([0-9A-Fa-f]{8})\s+(\S+)\s*(.*)$/;
  const beginRegex = /Transaction BEGIN occurred for TX: (\d+)/;
  const tidRegex = /#tid=(\d+)/;
  const resourceTxRegex = /RESOURCE registered with Transaction\. TX: (\d+)/;
  const xidDataRegex = /data\(([0-9a-fA-F]+)\)/;
  const appXidRegex = /^\s*(\S+)\s+([0-9A-F]{80,})\s*$/;
  const txPrimaryKeyRegex = /TxPrimaryKey@([0-9a-f]+);([0-9A-F]+):(\d+):([0-9A-F]+)/i;

  fileInput.addEventListener("change", async (event) => {
    const [file] = event.target.files || [];
    if (!file) {
      return;
    }

    await loadFile(file);
  });

  ["dragenter", "dragover"].forEach((eventName) => {
    dropZone.addEventListener(eventName, (event) => {
      event.preventDefault();
      dropZone.classList.add("active");
    });
  });

  ["dragleave", "dragend"].forEach((eventName) => {
    dropZone.addEventListener(eventName, () => {
      dropZone.classList.remove("active");
    });
  });

  dropZone.addEventListener("drop", async (event) => {
    event.preventDefault();
    dropZone.classList.remove("active");
    const [file] = Array.from(event.dataTransfer?.files || []);
    if (!file) {
      return;
    }

    await loadFile(file);
  });

  loadSampleButton.addEventListener("click", () => {
    const text = pasteArea.value.trim();
    if (!text) {
      return;
    }
    parseAndRender(text, "pasted log");
  });

  clearButton.addEventListener("click", () => {
    fileInput.value = "";
    pasteArea.value = "";
    searchInput.value = "";
    statusFilter.value = "all";
    sqlModeInputs.forEach((input) => {
      input.checked = true;
    });
    state.sqlModes = new Set(["executing", "executing batch", "batching"]);
    state.logName = "";
    state.transactions = [];
    state.filteredTransactions = [];
    state.selectedId = null;
    render();
  });

  searchInput.addEventListener("input", applyFilters);
  statusFilter.addEventListener("change", applyFilters);
  sqlModeInputs.forEach((input) => {
    input.addEventListener("change", applySqlModeFilters);
  });

  copyTxIdButton.addEventListener("click", () => {
    const tx = getSelectedTransaction();
    if (tx) {
      navigator.clipboard.writeText(tx.id);
    }
  });

  copyXidButton.addEventListener("click", () => {
    const tx = getSelectedTransaction();
    if (tx && tx.xid) {
      navigator.clipboard.writeText(tx.xid);
    }
  });

  panelToggles.forEach(([toggleId, panelId]) => {
    const toggle = document.getElementById(toggleId);
    const panel = document.getElementById(panelId);
    toggle.addEventListener("click", () => {
      const expanded = toggle.getAttribute("aria-expanded") === "true";
      toggle.setAttribute("aria-expanded", String(!expanded));
      panel.classList.toggle("hidden", expanded);
    });
  });

  function parseAndRender(text, logName) {
    state.logName = logName;
    state.transactions = parseLog(text);
    state.transactions.sort((left, right) => {
      const leftTime = left.startDate ? left.startDate.getTime() : 0;
      const rightTime = right.startDate ? right.startDate.getTime() : 0;
      return rightTime - leftTime;
    });
    state.selectedId = state.transactions[0] ? state.transactions[0].id : null;
    applyFilters();
  }

  function parseLog(text) {
    const lines = text.split(/\r?\n/);
    const transactions = new Map();
    const activeTxByThread = new Map();
    const recentTxByThread = new Map();
    const threadContext = new Map();
    let pendingSql = null;
    let currentHeader = null;

    function getOrCreateTransaction(id) {
      if (!transactions.has(id)) {
        transactions.set(id, {
          id,
          xid: "",
          appName: "",
          appMethod: "",
          startDate: null,
          endDate: null,
          startText: "",
          endText: "",
          timezone: "",
          threadIds: new Set(),
          events: [],
          queries: [],
          status: "active",
          resources: 0,
          prepares: 0,
          commits: 0,
          rollbacks: 0,
          xaCommits: 0,
          xaPrepares: 0,
          lines: [],
        });
      }
      return transactions.get(id);
    }

    function addEvent(tx, type, label, header, details) {
      tx.events.push({
        type,
        label,
        details: details || "",
        line: header.lineNumber,
        timestamp: header.timestampText,
        date: header.date,
        threadId: header.threadId,
        component: header.component,
      });
      tx.lines.push(header.lineNumber);
      tx.threadIds.add(header.threadId);
    }

    function finalizeSql(nextHeader) {
      if (!pendingSql) {
        return;
      }

      const tx = pendingSql.txId ? getOrCreateTransaction(pendingSql.txId) : null;
      if (tx) {
        const statement = pendingSql.sqlLines
          .filter((line) => line && !line.startsWith("[params="))
          .join("\n")
          .trim();
        const paramsLine =
          pendingSql.sqlLines.find((line) => line.startsWith("[params=")) || "";
        if (statement) {
          const kind = statement.split(/\s+/)[0].toUpperCase();
          tx.queries.push({
            kind,
            line: pendingSql.lineNumber,
            timestamp: pendingSql.timestampText,
            date: pendingSql.date,
            threadId: pendingSql.threadId,
            sql: statement,
            params: paramsLine.replace(/^\[params=/, "").replace(/\]$/, ""),
            durationMs: pendingSql.durationMs,
            mode: pendingSql.mode,
          });
        }
      }

      pendingSql = null;

      if (
        nextHeader &&
        /openjpa\.jdbc\.SQL: Trace: <t .*?> \[(\d+) ms\] spent/.test(nextHeader.message)
      ) {
        return;
      }
    }

    function applyStatusFromLine(tx, header, message) {
      if (message.includes("RolledbackException")) {
        tx.status = "rolledback";
        tx.rollbacks += 1;
        tx.endDate = header.date;
        tx.endText = header.timestampText;
        addEvent(tx, "rollback", "Rolled back", header, message.trim());
        activeTxByThread.delete(header.threadId);
        return;
      }

      if (message.includes("TranManagerIm <  commit (SPI) Exit")) {
        tx.status = "committed";
        tx.endDate = header.date;
        tx.endText = header.timestampText;
        addEvent(tx, "commit", "Commit completed", header, "");
        activeTxByThread.delete(header.threadId);
        return;
      }

      if (message.includes("TransactionIm <  rollback (SPI) Exit")) {
        tx.status = "rolledback";
        tx.rollbacks += 1;
        tx.endDate = header.date;
        tx.endText = header.timestampText;
        addEvent(tx, "rollback", "Rollback completed", header, "");
        activeTxByThread.delete(header.threadId);
        return;
      }
    }

    lines.forEach((rawLine, index) => {
      const lineNumber = index + 1;
      const headerMatch = rawLine.match(headerRegex);

      if (headerMatch) {
        const header = createHeader(headerMatch, lineNumber);

        if (pendingSql) {
          const spentMatch = header.message.match(
            /openjpa\.jdbc\.SQL: Trace: <t .*?> \[(\d+) ms\] spent/
          );
          if (spentMatch) {
            pendingSql.durationMs = Number(spentMatch[1]);
            finalizeSql(header);
          } else {
            finalizeSql(header);
          }
        }

        currentHeader = header;
        threadContext.set(header.threadId, header);

        const beginMatch = header.message.match(beginRegex);
        const explicitTidMatch = header.message.match(tidRegex);
        const resourceMatch = header.message.match(resourceTxRegex);

        let txId =
          (beginMatch && beginMatch[1]) ||
          (explicitTidMatch && explicitTidMatch[1]) ||
          (resourceMatch && resourceMatch[1]) ||
          activeTxByThread.get(header.threadId) ||
          recentTxByThread.get(header.threadId) ||
          null;

        if (beginMatch) {
          const tx = getOrCreateTransaction(beginMatch[1]);
          tx.status = "active";
          tx.startDate = header.date;
          tx.startText = header.timestampText;
          tx.timezone = header.timezone;
          addEvent(tx, "begin", "Transaction begin", header, header.message.trim());
          activeTxByThread.set(header.threadId, tx.id);
          recentTxByThread.set(header.threadId, tx.id);
          txId = tx.id;
        }

        if (txId) {
          const tx = getOrCreateTransaction(txId);
          tx.threadIds.add(header.threadId);

          const xidDataMatch = header.message.match(xidDataRegex);
          if (xidDataMatch && !tx.xid) {
            tx.xid = xidDataMatch[1].toUpperCase();
          }

          if (header.message.includes("registerSynchronization")) {
            addEvent(tx, "sync", "Synchronization registered", header, "");
          } else if (header.message.includes("beforeCompletion Entry")) {
            addEvent(tx, "before", "beforeCompletion", header, "");
          } else if (header.message.includes("afterCompletion Entry")) {
            addEvent(tx, "after", "afterCompletion", header, "");
          } else if (header.message.includes("JTAXAResource >  prepare Entry")) {
            tx.prepares += 1;
            tx.xaPrepares += 1;
            addEvent(tx, "prepare", "XA prepare", header, "");
          } else if (header.message.includes("JTAXAResource >  commit Entry")) {
            tx.commits += 1;
            tx.xaCommits += 1;
            addEvent(tx, "xa-commit", "XA commit", header, "");
          } else if (header.message.includes("RESOURCE registered with Transaction")) {
            tx.resources += 1;
            addEvent(tx, "resource", "Resource enlisted", header, header.message.trim());
          } else if (header.component === "SystemOut") {
            addEvent(tx, "app-log", "Application log", header, header.message.trim());
          }

          if (/openjpa\.jdbc\.SQL: Trace: <t .*?> (executing|batching|executing batch)/.test(header.message)) {
            pendingSql = {
              txId,
              lineNumber,
              threadId: header.threadId,
              timestampText: header.timestampText,
              date: header.date,
              durationMs: null,
              mode: header.message.includes("executing batch")
                ? "executing batch"
                : header.message.includes("batching")
                  ? "batching"
                  : "executing",
              sqlLines: [],
            };
          }

          applyStatusFromLine(tx, header, header.message);
        }

        return;
      }

      if (pendingSql) {
        pendingSql.sqlLines.push(rawLine);
      }

      if (!currentHeader) {
        return;
      }

      const txId =
        activeTxByThread.get(currentHeader.threadId) ||
        recentTxByThread.get(currentHeader.threadId) ||
        null;

      if (!txId) {
        return;
      }

      const tx = getOrCreateTransaction(txId);
      const appXidMatch = rawLine.match(appXidRegex);
      const xidDataMatch = rawLine.match(xidDataRegex);
      const txPrimaryKeyMatch = rawLine.match(txPrimaryKeyRegex);

      if (appXidMatch) {
        tx.appName = appXidMatch[1];
        if (!tx.xid) {
          tx.xid = appXidMatch[2];
        }

        const parts = tx.appName.split(".");
        tx.appMethod = parts[parts.length - 1] || tx.appName;
      }

      if (xidDataMatch && !tx.xid) {
        tx.xid = xidDataMatch[1].toUpperCase();
      }

      if (txPrimaryKeyMatch) {
        tx.txPrimaryKey = txPrimaryKeyMatch[0];
      }

      if (rawLine.includes("from STATE_COMMITTING to STATE_COMMITTED")) {
        tx.status = "committed";
        addEvent(tx, "state", "State committed", currentHeader, rawLine.trim());
      } else if (rawLine.includes("STATE_ROLLEDBACK")) {
        tx.status = "rolledback";
        tx.endDate = currentHeader.date;
        tx.endText = currentHeader.timestampText;
        addEvent(tx, "state", "State rolled back", currentHeader, rawLine.trim());
      } else if (rawLine.includes("STATE_COMMITTED")) {
        tx.status = "committed";
      } else if (rawLine.includes("RolledbackException")) {
        tx.status = "rolledback";
        tx.endDate = currentHeader.date;
        tx.endText = currentHeader.timestampText;
      }
    });

    finalizeSql(null);

    return Array.from(transactions.values()).map(finalizeTransaction);
  }

  function finalizeTransaction(tx) {
    const threadIds = Array.from(tx.threadIds).sort();
    const queryCount = tx.queries.length;
    const sqlKinds = tx.queries.reduce((counts, query) => {
      counts[query.kind] = (counts[query.kind] || 0) + 1;
      return counts;
    }, {});

    const durationMs =
      tx.startDate && tx.endDate ? Math.max(0, tx.endDate - tx.startDate) : null;

    return {
      ...tx,
      threadIds,
      queryCount,
      totalQueryCount: queryCount,
      sqlKinds,
      durationMs,
      xidShort: tx.xid ? `${tx.xid.slice(0, 24)}...${tx.xid.slice(-12)}` : "",
      startLabel: tx.startDate ? formatDate(tx.startDate) : "Unknown",
      endLabel: tx.endDate ? formatDate(tx.endDate) : "Open",
    };
  }

  function createHeader(match, lineNumber) {
    const [
      ,
      month,
      day,
      year,
      hour,
      minute,
      second,
      millis,
      timezone,
      threadId,
      component,
      message,
    ] = match;

    const date = new Date(
      2000 + Number(year),
      Number(month) - 1,
      Number(day),
      Number(hour),
      Number(minute),
      Number(second),
      Number(millis)
    );

    return {
      lineNumber,
      date,
      timezone,
      threadId: threadId.toUpperCase(),
      component,
      message,
      timestampText: `${month}/${day}/${year} ${hour}:${minute}:${second}.${millis} ${timezone}`,
    };
  }

  function applyFilters() {
    const query = searchInput.value.trim().toLowerCase();
    const status = statusFilter.value;

    state.filteredTransactions = state.transactions.filter((tx) => {
      if (status !== "all" && tx.status !== status) {
        return false;
      }

      if (!query) {
        return true;
      }

      const haystack = [
        tx.id,
        tx.xid,
        tx.appName,
        tx.appMethod,
        tx.threadIds.join(" "),
        tx.queries.map((queryItem) => queryItem.sql).join(" "),
      ]
        .join(" ")
        .toLowerCase();

      return haystack.includes(query);
    });

    if (!state.filteredTransactions.some((tx) => tx.id === state.selectedId)) {
      state.selectedId = state.filteredTransactions[0]
        ? state.filteredTransactions[0].id
        : null;
    }

    render();
  }

  function applySqlModeFilters() {
    state.sqlModes = new Set(
      sqlModeInputs.filter((input) => input.checked).map((input) => input.value)
    );
    render();
  }

  async function loadFile(file) {
    const text = await file.text();
    parseAndRender(text, file.name);
  }

  function render() {
    renderSummary();
    renderTransactionList();
    renderDetail();
  }

  function renderSummary() {
    summary.innerHTML = "";

    const committed = state.transactions.filter((tx) => tx.status === "committed").length;
    const rolledback = state.transactions.filter((tx) => tx.status === "rolledback").length;
    const active = state.transactions.filter((tx) => tx.status === "active").length;
    const avgDuration = average(
      state.transactions
        .map((tx) => tx.durationMs)
        .filter((duration) => typeof duration === "number")
    );
    const visibleSqlStatements = state.transactions.reduce(
      (sum, tx) => sum + getVisibleQueries(tx).length,
      0
    );
    const hasSqlModeFilter = state.sqlModes.size !== sqlModeInputs.length;

    const cards = [
      {
        label: "Transactions",
        value: state.transactions.length,
        meta: state.logName || "No log loaded",
      },
      {
        label: "Committed",
        value: committed,
        meta: `${rolledback} rolled back`,
      },
      {
        label: "Open / incomplete",
        value: active,
        meta: `${visibleSqlStatements} SQL statements${hasSqlModeFilter ? " shown" : ""}`,
      },
      {
        label: "Average duration",
        value: avgDuration ? formatDuration(avgDuration) : "--",
        meta: "Across finished transactions",
      },
    ];

    cards.forEach((card) => {
      const fragment = summaryCardTemplate.content.cloneNode(true);
      fragment.querySelector(".summary-label").textContent = card.label;
      fragment.querySelector(".summary-value").textContent = String(card.value);
      fragment.querySelector(".summary-meta").textContent = card.meta;
      summary.appendChild(fragment);
    });
  }

  function renderTransactionList() {
    txList.innerHTML = "";

    if (!state.transactions.length) {
      txCount.textContent = "No log loaded";
      txList.innerHTML = '<p class="muted">Load a WebSphere trace file to begin.</p>';
      return;
    }

    txCount.textContent = `${state.filteredTransactions.length} of ${state.transactions.length} transactions`;

    if (!state.filteredTransactions.length) {
      txList.innerHTML = '<p class="muted">No transactions match the current filters.</p>';
      return;
    }

    state.filteredTransactions.forEach((tx) => {
      const button = document.createElement("button");
      button.className = `tx-item${tx.id === state.selectedId ? " active" : ""}`;
      button.type = "button";
      button.addEventListener("click", () => {
        state.selectedId = tx.id;
        renderTransactionList();
        renderDetail();
      });

      const appLabel = tx.appName || "Unknown component";
      const duration = tx.durationMs != null ? formatDuration(tx.durationMs) : "Open";
      const visibleQueryCount = getVisibleQueries(tx).length;

      button.innerHTML = `
        <div class="tx-item-top">
          <h3>TX ${escapeHtml(tx.id)}</h3>
          <span class="badge ${tx.status}">${prettyStatus(tx.status)}</span>
        </div>
        <p>${escapeHtml(appLabel)}</p>
        <code>${escapeHtml(tx.xidShort || "No XID captured")}</code>
        <div class="tx-meta-row">
          <span>${escapeHtml(tx.startLabel)}</span>
          <span>${formatSqlCount(visibleQueryCount, tx.totalQueryCount)}</span>
        </div>
        <div class="tx-meta-row">
          <span>${escapeHtml(duration)}</span>
          <span>${escapeHtml(tx.threadIds.join(", "))}</span>
        </div>
      `;

      txList.appendChild(button);
    });
  }

  function renderDetail() {
    const tx = getSelectedTransaction();
    const hasSelection = Boolean(tx);

    emptyState.classList.toggle("hidden", hasSelection);
    detailPanel.classList.toggle("hidden", !hasSelection);

    if (!tx) {
      return;
    }

    detailTitle.textContent = `TX ${tx.id}`;
    detailSubtitle.textContent = `${tx.appName || "Unknown application"}${tx.durationMs != null ? ` • ${formatDuration(tx.durationMs)}` : ""}`;

    renderDetailStats(tx);
    renderIdentifiers(tx);
    renderTimeline(tx);
    renderSql(tx);
  }

  function renderDetailStats(tx) {
    detailStats.innerHTML = "";
    const visibleQueryCount = getVisibleQueries(tx).length;

    const stats = [
      { label: "Status", value: prettyStatus(tx.status) },
      { label: "Duration", value: tx.durationMs != null ? formatDuration(tx.durationMs) : "Open" },
      { label: "SQL statements", value: formatSqlCount(visibleQueryCount, tx.totalQueryCount) },
      { label: "XA phases", value: `${tx.xaPrepares} prepare / ${tx.xaCommits} commit` },
    ];

    stats.forEach((stat) => {
      const article = document.createElement("article");
      article.className = "detail-stat card";
      article.innerHTML = `
        <p class="label">${escapeHtml(stat.label)}</p>
        <p class="value">${escapeHtml(String(stat.value))}</p>
      `;
      detailStats.appendChild(article);
    });
  }

  function renderIdentifiers(tx) {
    const rows = [
      ["TX id", tx.id],
      ["XID", tx.xid || "Not captured"],
      ["Component", tx.appName || "Unknown"],
      ["Threads", tx.threadIds.join(", ") || "Unknown"],
      ["Started", tx.startText || "Unknown"],
      ["Finished", tx.endText || "Open"],
    ];

    identifierGrid.innerHTML = rows
      .map(
        ([label, value]) => `
          <div>
            <dt>${escapeHtml(label)}</dt>
            <dd>${escapeHtml(value)}</dd>
          </div>
        `
      )
      .join("");
  }

  function renderTimeline(tx) {
    timeline.innerHTML = "";

    const items = [...tx.events].sort((left, right) => {
      return left.date.getTime() - right.date.getTime() || left.line - right.line;
    });

    if (!items.length) {
      timeline.innerHTML = '<p class="muted">No timeline events captured.</p>';
      return;
    }

    items.forEach((event) => {
      const article = document.createElement("article");
      article.className = "timeline-item";
      article.innerHTML = `
        <div class="time">${escapeHtml(event.timestamp)}</div>
        <div>
          <h4>${escapeHtml(event.label)}</h4>
          <p>${escapeHtml(
            `${event.component} • thread ${event.threadId} • line ${event.line}${
              event.details ? ` • ${event.details}` : ""
            }`
          )}</p>
        </div>
      `;
      timeline.appendChild(article);
    });
  }

  function renderSql(tx) {
    sqlList.innerHTML = "";
    const visibleQueries = getVisibleQueries(tx);

    if (!tx.queries.length) {
      sqlFilterMeta.textContent = "No SQL captured";
      sqlList.innerHTML = '<p class="muted">No SQL captured for this transaction.</p>';
      return;
    }

    sqlFilterMeta.textContent = `${formatSqlCount(visibleQueries.length, tx.totalQueryCount)} visible`;

    if (!visibleQueries.length) {
      sqlList.innerHTML =
        '<p class="muted">No SQL statements match the selected SQL mode filters.</p>';
      return;
    }

    visibleQueries.forEach((query) => {
      const article = document.createElement("article");
      article.className = "sql-item";
      article.innerHTML = `
        <div class="sql-head">
          <div>
            <div class="sql-kind">${escapeHtml(query.kind)}</div>
            <p>${escapeHtml(
              `${query.timestamp} • thread ${query.threadId} • line ${query.line}${
                query.durationMs != null ? ` • ${query.durationMs} ms` : ""
              }`
            )}</p>
          </div>
          <div class="badge ${tx.status}">${escapeHtml(query.mode)}</div>
        </div>
        <div class="sql-body">
          <pre><code>${escapeHtml(query.sql)}</code></pre>
          ${
            query.params
              ? `<p class="params">params: ${escapeHtml(query.params)}</p>`
              : ""
          }
        </div>
      `;
      sqlList.appendChild(article);
    });
  }

  function getSelectedTransaction() {
    return state.filteredTransactions.find((tx) => tx.id === state.selectedId) || null;
  }

  function getVisibleQueries(tx) {
    return tx.queries.filter((query) => state.sqlModes.has(query.mode));
  }

  function formatSqlCount(visibleCount, totalCount) {
    if (visibleCount === totalCount) {
      return `${visibleCount} SQL`;
    }
    return `${visibleCount} / ${totalCount} SQL`;
  }

  function formatDate(date) {
    return date.toLocaleString();
  }

  function formatDuration(durationMs) {
    if (durationMs < 1000) {
      return `${durationMs} ms`;
    }

    const seconds = durationMs / 1000;
    if (seconds < 60) {
      return `${seconds.toFixed(2)} s`;
    }

    const minutes = Math.floor(seconds / 60);
    const remainderSeconds = Math.round(seconds % 60);
    return `${minutes}m ${remainderSeconds}s`;
  }

  function average(values) {
    if (!values.length) {
      return 0;
    }
    return Math.round(values.reduce((sum, value) => sum + value, 0) / values.length);
  }

  function prettyStatus(status) {
    if (status === "committed") {
      return "Committed";
    }
    if (status === "rolledback") {
      return "Rolled back";
    }
    return "Active";
  }

  function escapeHtml(value) {
    return String(value)
      .replaceAll("&", "&amp;")
      .replaceAll("<", "&lt;")
      .replaceAll(">", "&gt;")
      .replaceAll('"', "&quot;")
      .replaceAll("'", "&#039;");
  }

  render();
})();
