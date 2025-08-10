<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Staff Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem; }
        pre { background: #f6f6f6; padding: 1rem; }
    </style>
    <script>
        function getCookie(name){return document.cookie.split('; ').find(r=>r.startsWith(name+'='))?.split('=')[1];}
        function authHeaders(){ const t = getCookie('jwt'); return t ? { 'Authorization': 'Bearer ' + t, 'Content-Type':'application/json' } : { 'Content-Type':'application/json'} }
        async function loadAll(){ const r = await fetch('/staff/complaints', { headers: authHeaders()}); document.getElementById('all').textContent = await r.text(); }
        async function assign(){ const id = document.getElementById('aid').value; const dept = document.getElementById('adept').value; const r = await fetch(`/staff/complaints/${id}/assign`,{method:'PUT', headers: authHeaders(), body: JSON.stringify({assignedDepartment: dept})}); document.getElementById('assignRes').textContent = await r.text(); }
        async function status(){ const id = document.getElementById('sid').value; const st = document.getElementById('sstatus').value; const r = await fetch(`/staff/complaints/${id}/status`,{method:'PUT', headers: authHeaders(), body: JSON.stringify({status: st})}); document.getElementById('statusRes').textContent = await r.text(); }
        async function comment(){ const id = document.getElementById('cid').value; const txt = document.getElementById('ctxt').value; const r = await fetch(`/staff/complaints/${id}/comments`,{method:'POST', headers: authHeaders(), body: JSON.stringify({commentText: txt})}); document.getElementById('commentRes').textContent = await r.text(); }
    </script>
</head>
<body>
<h3>Staff</h3>
<p><a href="/">Home</a></p>
<section>
    <h4>All Complaints</h4>
    <button onclick="loadAll()">Load</button>
    <pre id="all"></pre>
</section>
<section>
    <h4>Assign Department</h4>
    <input type="number" id="aid" placeholder="Complaint ID"/>
    <input type="text" id="adept" placeholder="Department"/>
    <button onclick="assign()">Assign</button>
    <pre id="assignRes"></pre>
</section>
<section>
    <h4>Update Status</h4>
    <input type="number" id="sid" placeholder="Complaint ID"/>
    <select id="sstatus">
        <option>PENDING</option>
        <option>IN_PROGRESS</option>
        <option>RESOLVED</option>
    </select>
    <button onclick="status()">Update</button>
    <pre id="statusRes"></pre>
</section>
<section>
    <h4>Add Comment</h4>
    <input type="number" id="cid" placeholder="Complaint ID"/>
    <input type="text" id="ctxt" placeholder="Comment"/>
    <button onclick="comment()">Add</button>
    <pre id="commentRes"></pre>
</section>
</body>
</html>