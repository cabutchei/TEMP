(() => {
  const GITHUB_API_BASE = "https://api.github.com/repos";
  const GITHUB_OWNER = "mrbeast6000704";
  const GITHUB_REPO = "uploads";

  function removeExisting() {
    document.getElementById("__github_upload_panel__")?.remove();
  }

  function el(tag, props = {}, children = []) {
    const node = document.createElement(tag);
    Object.assign(node, props);
    for (const child of children) {
      node.append(child);
    }
    return node;
  }

  function fileToBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => {
        const result = String(reader.result || "");
        const commaIndex = result.indexOf(",");
        resolve(commaIndex >= 0 ? result.slice(commaIndex + 1) : result);
      };
      reader.onerror = () => reject(reader.error || new Error("Failed to read file"));
      reader.readAsDataURL(file);
    });
  }

  removeExisting();

  const panel = el("div", { id: "__github_upload_panel__" });
  panel.style.cssText = [
    "position:fixed",
    "top:16px",
    "right:16px",
    "z-index:999999",
    "width:420px",
    "max-height:85vh",
    "overflow:auto",
    "background:#111",
    "color:#eee",
    "border:1px solid #444",
    "border-radius:10px",
    "padding:14px",
    "font:12px/1.4 monospace",
    "box-shadow:0 8px 30px rgba(0,0,0,.45)"
  ].join(";");

  const title = el("div", { textContent: "GitHub File Upload Test Panel" });
  title.style.cssText = "font-weight:bold;margin-bottom:12px;font-size:13px;";

  const tokenInput = el("input", {
    value: "",
    placeholder: "GitHub token"
  });
  tokenInput.type = "password";

  const commitMessageInput = el("input", {
    value: "upload from browser form",
    placeholder: "Commit message"
  });

  const modeSelect = el("select", {}, [
    el("option", { value: "upsert", textContent: "Create or update" }),
    el("option", { value: "create", textContent: "Create new files only" }),
    el("option", { value: "update", textContent: "Update existing files only" })
  ]);

  const branchInput = el("input", {
    value: "",
    placeholder: "Branch (optional)"
  });

  const repoPathInput = el("input", {
    value: "",
    placeholder: "Repo path prefix (optional), e.g. some/path/"
  });

  const fileInput = el("input", { type: "file" });
  fileInput.multiple = true;
  const fileInfo = el("div", { textContent: "No file selected" });
  const output = el("pre", { textContent: "Ready." });

  for (const input of [tokenInput, commitMessageInput, modeSelect, branchInput, repoPathInput, fileInput]) {
    input.style.cssText =
      "display:block;width:100%;margin:0 0 8px 0;padding:8px;box-sizing:border-box;background:#1b1b1b;color:#eee;border:1px solid #555;border-radius:6px;";
  }

  output.style.cssText =
    "white-space:pre-wrap;word-break:break-word;background:#1b1b1b;padding:10px;border-radius:6px;border:1px solid #333;min-height:160px;";
  fileInfo.style.cssText = "margin:-2px 0 10px 0;color:#bbb;";

  function formatBytes(bytes) {
    if (!Number.isFinite(bytes) || bytes < 0) return "unknown size";
    if (bytes < 1024) return `${bytes} B`;
    if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(2)} KB`;
    if (bytes < 1024 * 1024 * 1024) return `${(bytes / (1024 * 1024)).toFixed(2)} MB`;
    return `${(bytes / (1024 * 1024 * 1024)).toFixed(2)} GB`;
  }

  function normalizeRepoPath(path) {
    const trimmed = path.trim();
    if (!trimmed) return "";
    return trimmed.replace(/^\/+/, "").replace(/\/+$/, "");
  }

  function encodeContentPath(path) {
    return path
      .split("/")
      .map(encodeURIComponent)
      .join("/");
  }

  function buildContentUrl(targetFilepath, branch) {
    const encodedPath = encodeContentPath(targetFilepath);
    const baseUrl = `${GITHUB_API_BASE}/${GITHUB_OWNER}/${GITHUB_REPO}/contents/${encodedPath}`;
    return branch ? `${baseUrl}?ref=${encodeURIComponent(branch)}` : baseUrl;
  }

  async function getExistingFileSha(token, targetFilepath, branch) {
    const response = await fetch(buildContentUrl(targetFilepath, branch), {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        Accept: "application/vnd.github+json"
      }
    });

    const responseText = await response.text();
    let responseBody;
    try {
      responseBody = JSON.parse(responseText);
    } catch {
      responseBody = responseText;
    }

    if (response.status === 404) {
      return { exists: false, response: responseBody };
    }
    if (!response.ok) {
      throw new Error(`Failed to check ${targetFilepath}: ${response.status} ${responseText}`);
    }
    if (Array.isArray(responseBody) || !responseBody?.sha) {
      throw new Error(`${targetFilepath} exists, but it is not a file response with a sha`);
    }

    return { exists: true, sha: responseBody.sha, response: responseBody };
  }

  function updateFileInfo() {
    const files = Array.from(fileInput.files || []);
    if (!files.length) {
      fileInfo.textContent = "No file selected";
      return;
    }
    fileInfo.textContent = files
      .map(file => `Selected: ${file.name} (${formatBytes(file.size)})`)
      .join("\n");
  }

  function setOutput(value) {
    output.textContent =
      typeof value === "string" ? value : JSON.stringify(value, null, 2);
  }

  function btn(label, onClick) {
    const b = el("button", { textContent: label });
    b.style.cssText =
      "margin:0 8px 8px 0;padding:8px 10px;background:#2a2a2a;color:#fff;border:1px solid #555;border-radius:6px;cursor:pointer;";
    b.onclick = () => run(onClick);
    return b;
  }

  async function uploadToGithub() {
    const token = tokenInput.value.trim();
    const commitMessage = commitMessageInput.value.trim();
    const mode = modeSelect.value;
    const branch = branchInput.value.trim();
    const repoPath = normalizeRepoPath(repoPathInput.value);
    const files = Array.from(fileInput.files || []);

    if (!token) {
      throw new Error("Missing GitHub token");
    }
    if (!commitMessage) {
      throw new Error("Missing commit message");
    }
    if (!files.length) {
      throw new Error("Select at least one file first");
    }

    const results = [];
    for (const file of files) {
      const content = await fileToBase64(file);
      const targetFilepath = repoPath ? `${repoPath}/${file.name}` : file.name;
      const existingFile = mode === "create"
        ? { exists: false, sha: null }
        : await getExistingFileSha(token, targetFilepath, branch);

      if (mode === "update" && !existingFile.exists) {
        results.push({
          request: {
            targetFilepath,
            mode
          },
          response: {
            ok: false,
            status: 404,
            body: `Cannot update ${targetFilepath}; the file does not exist.`
          }
        });
        continue;
      }

      const url = `${GITHUB_API_BASE}/${GITHUB_OWNER}/${GITHUB_REPO}/contents/${encodeContentPath(targetFilepath)}`;
      const payload = {
        message: commitMessage,
        content
      };
      if (branch) {
        payload.branch = branch;
      }
      if (existingFile.exists) {
        payload.sha = existingFile.sha;
      }

      const response = await fetch(url, {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
          Accept: "application/vnd.github+json"
        },
        body: JSON.stringify(payload)
      });

      const responseText = await response.text();
      let responseJson;
      try {
        responseJson = JSON.parse(responseText);
      } catch {
        responseJson = responseText;
      }

      results.push({
        request: {
          url,
          method: "PUT",
          headers: {
            Authorization: "Bearer ***",
            Accept: "application/vnd.github+json"
          },
          body: {
            message: payload.message,
            mode,
            branch: payload.branch || null,
            sha: payload.sha || null,
            contentLength: payload.content.length,
            targetFilepath
          }
        },
        response: {
          ok: response.ok,
          status: response.status,
          body: responseJson
        }
      });
    }

    return {
      filesSelected: files.length,
      results
    };
  }

  async function run(fn) {
    try {
      setOutput("Running...");
      const result = await fn();
      setOutput(result);
    } catch (err) {
      setOutput(err?.message || String(err));
    }
  }

  const buttons = el("div", {}, [
    btn("Upload to GitHub", uploadToGithub),
    (() => {
      const b = btn("Close", async () => {});
      b.onclick = () => panel.remove();
      return b;
    })()
  ]);

  fileInput.addEventListener("change", updateFileInfo);

  panel.append(
    title,
    el("div", { textContent: `Repo: ${GITHUB_OWNER}/${GITHUB_REPO}` }),
    el("div", { textContent: "GitHub Token" }),
    tokenInput,
    el("div", { textContent: "Commit Message" }),
    commitMessageInput,
    el("div", { textContent: "Upload Mode" }),
    modeSelect,
    el("div", { textContent: "Branch" }),
    branchInput,
    el("div", { textContent: "Repo Path Prefix" }),
    repoPathInput,
    el("div", { textContent: "Files" }),
    fileInput,
    fileInfo,
    buttons,
    output
  );

  document.body.appendChild(panel);
})();
