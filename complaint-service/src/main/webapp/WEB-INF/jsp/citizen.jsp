<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Citizen Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem; }
        textarea { width: 480px; height: 100px; }
        select, button { margin-top: 0.5rem; }
        pre { background: #f6f6f6; padding: 1rem; }
    </style>
    <script>
        function getCookie(name){return document.cookie.split('; ').find(r=>r.startsWith(name+'='))?.split('=')[1];}
        function authHeaders(){ const t = getCookie('jwt'); return t ? { 'Authorization': 'Bearer ' + t, 'Content-Type':'application/json' } : { 'Content-Type':'application/json'} }
        async function fileComplaint(){
            const category = document.getElementById('category').value;
            const description = document.getElementById('description').value;
            const res = await fetch('/citizen/complaints', { method:'POST', headers: authHeaders(), body: JSON.stringify({category, description}) });
            document.getElementById('result').textContent = await res.text();
        }
        async function loadMine(){
            const res = await fetch('/citizen/complaints', { headers: authHeaders() });
            document.getElementById('mine').textContent = await res.text();
        }
        async function addComment(){
            const id = document.getElementById('cid').value;
            const text = document.getElementById('ctext').value;
            const res = await fetch(`/citizen/complaints/${id}/comments`, { method:'POST', headers: authHeaders(), body: JSON.stringify({commentText: text}) });
            document.getElementById('commentResult').textContent = await res.text();
        }
    </script>
</head>
<body>
<h3>Citizen</h3>
<p><a href="/">Home</a></p>
<section>
    <h4>File Complaint</h4>
    <label>Category</label>
    <select id="category">
        <option>WATER</option>
        <option>SANITATION</option>
        <option>ROADS</option>
    </select><br/>
    <label>Description</label><br/>
    <textarea id="description"></textarea><br/>
    <button onclick="fileComplaint()">Submit</button>
    <pre id="result"></pre>
</section>
<section>
    <h4>My Complaints</h4>
    <button onclick="loadMine()">Load</button>
    <pre id="mine"></pre>
</section>
<section>
    <h4>Add Comment to My Complaint</h4>
    <input type="number" id="cid" placeholder="Complaint ID"/>
    <input type="text" id="ctext" placeholder="Your comment"/>
    <button onclick="addComment()">Add</button>
    <pre id="commentResult"></pre>
</section>
</body>
</html>