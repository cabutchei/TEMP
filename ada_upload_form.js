(() => {
  const API_BASE = "https://lms-back.ada.tech";

  const pathMatch = location.pathname.match(
    /\/student\/projects\/by-module-id\/([^/]+)(?:\/by-project-submission-id\/([^/]+))?/
  );

  const defaultModuleId = pathMatch?.[1] || "";
  const defaultSubmissionId = pathMatch?.[2] || "";

  function encodeTrpcQueryInput(value) {
    return encodeURIComponent(JSON.stringify({ 0: { json: value } }));
  }

  function encodeTrpcMutationBody(payload) {
    return JSON.stringify({ 0: { json: payload } });
  }

  function removeExisting() {
    document.getElementById("__project_test_panel__")?.remove();
  }

  function el(tag, props = {}, children = []) {
    const node = document.createElement(tag);
    Object.assign(node, props);
    for (const child of children) {
      node.append(child);
    }
    return node;
  }

  async function parseJsonResponse(res) {
    const text = await res.text();
    let json;
    try {
      json = JSON.parse(text);
    } catch {
      json = text;
    }
    if (!res.ok) {
      throw new Error(typeof json === "string" ? json : JSON.stringify(json, null, 2));
    }
    return json;
  }

  removeExisting();

  const panel = el("div", { id: "__project_test_panel__" });
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

  const title = el("div", { textContent: "Project Submission Test Panel" });
  title.style.cssText = "font-weight:bold;margin-bottom:12px;font-size:13px;";

  const moduleInput = el("input", { value: defaultModuleId, placeholder: "classModuleId" });
  const submissionInput = el("input", { value: defaultSubmissionId, placeholder: "projectSubmissionId" });
  const githubInput = el("input", { value: "", placeholder: "githubLink (optional)" });
  const fileInput = el("input", { type: "file" });
  const output = el("pre", { textContent: "Ready." });

  for (const input of [moduleInput, submissionInput, githubInput, fileInput]) {
    input.style.cssText = "display:block;width:100%;margin:0 0 8px 0;padding:8px;box-sizing:border-box;background:#1b1b1b;color:#eee;border:1px solid #555;border-radius:6px;";
  }

  output.style.cssText = "white-space:pre-wrap;word-break:break-word;background:#1b1b1b;padding:10px;border-radius:6px;border:1px solid #333;min-height:160px;";

  function setOutput(value) {
    output.textContent = typeof value === "string" ? value : JSON.stringify(value, null, 2);
  }

  function btn(label, onClick) {
    const b = el("button", { textContent: label });
    b.style.cssText = "margin:0 8px 8px 0;padding:8px 10px;background:#2a2a2a;color:#fff;border:1px solid #555;border-radius:6px;cursor:pointer;";
    b.onclick = () => run(onClick);
    return b;
  }

  async function listProjectSubmissions() {
    const classModuleId = moduleInput.value.trim();
    const input = encodeTrpcQueryInput(classModuleId);

    const res = await fetch(
      `${API_BASE}/trpc/projectSubmissions.listByClassModuleId?batch=1&input=${input}`,
      {
        method: "GET",
        credentials: "include",
      }
    );

    return parseJsonResponse(res);
  }

  async function findProjectSubmission() {
    const projectSubmissionId = submissionInput.value.trim();
    const input = encodeTrpcQueryInput(projectSubmissionId);

    const res = await fetch(
      `${API_BASE}/trpc/projectSubmissions.find?batch=1&input=${input}`,
      {
        method: "GET",
        credentials: "include",
      }
    );

    return parseJsonResponse(res);
  }

  async function uploadProject() {
    const projectSubmissionId = submissionInput.value.trim();
    const githubLink = githubInput.value.trim() || undefined;
    const file = fileInput.files[0];

    if (!projectSubmissionId) throw new Error("Missing projectSubmissionId");
    if (!file) throw new Error("Select a file first");

    const form = new FormData();
    form.append("file", file);

    const uploadRes = await fetch(
      `${API_BASE}/project-submissions/upload/${projectSubmissionId}`,
      {
        method: "POST",
        credentials: "include",
        body: form,
      }
    );

    const uploadJson = await parseJsonResponse(uploadRes);
    const fileUrl = uploadJson?.fileUrl;
    if (!fileUrl) throw new Error("Upload succeeded but no fileUrl returned");

    const saveRes = await fetch(
      `${API_BASE}/trpc/projectSubmissions.saveAnswer?batch=1`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "content-type": "application/json",
        },
        body: encodeTrpcMutationBody({
          id: projectSubmissionId,
          fileUrl,
          githubLink,
          finished: true,
        }),
      }
    );

    const saveJson = await parseJsonResponse(saveRes);
    return { upload: uploadJson, save: saveJson };
  }

  async function deleteProjectFile() {
    const projectSubmissionId = submissionInput.value.trim();
    if (!projectSubmissionId) throw new Error("Missing projectSubmissionId");

    const res = await fetch(
      `${API_BASE}/trpc/projectSubmissions.saveAnswer?batch=1`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "content-type": "application/json",
        },
        body: encodeTrpcMutationBody({
          id: projectSubmissionId,
          deleteFile: true,
          finished: false,
        }),
      }
    );

    return parseJsonResponse(res);
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
    btn("List", listProjectSubmissions),
    btn("Find", findProjectSubmission),
    btn("Upload", uploadProject),
    btn("Delete File", deleteProjectFile),
    (() => {
      const b = btn("Close", async () => {});
      b.onclick = () => panel.remove();
      return b;
    })(),
  ]);

  panel.append(
    title,
    el("div", { textContent: "Class Module ID" }),
    moduleInput,
    el("div", { textContent: "Project Submission ID" }),
    submissionInput,
    el("div", { textContent: "GitHub Link" }),
    githubInput,
    el("div", { textContent: "File" }),
    fileInput,
    buttons,
    output
  );

  document.body.appendChild(panel);
})();
