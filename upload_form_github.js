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

  const title = el("div", { textContent: "GitHub Upload Test Panel" });
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

  const branchInput = el("input", {
    value: "",
    placeholder: "Branch (optional)"
  });

  const fileInput = el("input", { type: "file" });
  const output = el("pre", { textContent: "Ready." });

  for (const input of [tokenInput, commitMessageInput, branchInput, fileInput]) {
    input.style.cssText =
      "display:block;width:100%;margin:0 0 8px 0;padding:8px;box-sizing:border-box;background:#1b1b1b;color:#eee;border:1px solid #555;border-radius:6px;";
  }

  output.style.cssText =
    "white-space:pre-wrap;word-break:break-word;background:#1b1b1b;padding:10px;border-radius:6px;border:1px solid #333;min-height:160px;";

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
    const branch = branchInput.value.trim();
    const file = fileInput.files[0];

    if (!token) {
      throw new Error("Missing GitHub token");
    }
    if (!commitMessage) {
      throw new Error("Missing commit message");
    }
    if (!file) {
      throw new Error("Select a file first");
    }

    const content = await fileToBase64(file);
    const targetFilepath = file.name;
    const url = `${GITHUB_API_BASE}/${GITHUB_OWNER}/${GITHUB_REPO}/contents/${encodeURIComponent(targetFilepath)}`;
    const payload = {
      message: commitMessage,
      content
    };
    if (branch) {
      payload.branch = branch;
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

    return {
      request: {
        url,
        method: "PUT",
        headers: {
          Authorization: "Bearer ***",
          Accept: "application/vnd.github+json"
        },
        body: {
          message: payload.message,
          branch: payload.branch || null,
          contentLength: payload.content.length,
          targetFilepath
        }
      },
      response: {
        ok: response.ok,
        status: response.status,
        body: responseJson
      }
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

  panel.append(
    title,
    el("div", { textContent: `Repo: ${GITHUB_OWNER}/${GITHUB_REPO}` }),
    el("div", { textContent: "GitHub Token" }),
    tokenInput,
    el("div", { textContent: "Commit Message" }),
    commitMessageInput,
    el("div", { textContent: "Branch" }),
    branchInput,
    el("div", { textContent: "File" }),
    fileInput,
    buttons,
    output
  );

  document.body.appendChild(panel);
})();
