const fs = require('fs');

const host = "https://api.github.com/repos";
const username = "mrbeast6000704";
const repo = "uploads";
const token = '';
const targetFilepath = "bundle";
const sourceFilepath = "/Users/cabutchei/bundle.b64";
const commitMessage = "bundle";
const content =  fs.readFileSync(sourceFilepath, 'utf8');
const payload = {
    "message": commitMessage,
    "content": content
  };

const url = `${host}/${username}/${repo}/contents/${targetFilepath}`;


fetch(url, {
    method: 'PUT',
    headers: {
        // 'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        'Accept': 'application/vnd.github+json'
    },
    body: JSON.stringify(payload)
})
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.error('Error:', error));