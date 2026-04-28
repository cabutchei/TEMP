# WAS Transaction Viewer

Small static web app for inspecting traditional WebSphere trace logs with:

- transaction begin / commit / rollback markers
- XID capture
- XA prepare / commit phases
- OpenJPA SQL statements grouped by transaction

## Files

- `index.html`
- `styles.css`
- `app.js`

## Use

Open `index.html` directly in a browser, or serve the folder locally:

```bash
cd /Users/cabutchei/Documents/vs_code/was-tx-viewer
python3 -m http.server 4173
```

Then open:

```text
http://localhost:4173
```

Upload a file such as:

```text
/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/logs/server1/jpa-sql.log
```

or paste log text into the page.

## Notes

- The parser is designed for traditional WebSphere trace logs, not Liberty JSON logs.
- It works best when `Transaction=all` and `openjpa.jdbc.SQL=all` are enabled together.
- It is heuristic. WebSphere trace is not transaction-centric by design, so the app infers transaction ownership mainly from thread IDs and `#tid=...` markers.
