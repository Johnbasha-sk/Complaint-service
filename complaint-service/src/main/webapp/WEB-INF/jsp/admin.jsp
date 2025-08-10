<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem; }
        pre { background: #f6f6f6; padding: 1rem; }
    </style>
    <script>
        function getCookie(name){return document.cookie.split('; ').find(r=>r.startsWith(name+'='))?.split('=')[1];}
        function authHeaders(){ const t = getCookie('jwt'); return t ? { 'Authorization': 'Bearer ' + t } : {} }
        async function del(){ const id = document.getElementById('did').value; const r = await fetch(`/admin/complaints/${id}`, { method:'DELETE', headers: authHeaders()}); document.getElementById('dres').textContent = 'Status ' + r.status; }
    </script>
</head>
<body>
<h3>Admin</h3>
<p><a href="/">Home</a></p>
<section>
    <h4>Delete Complaint</h4>
    <input type="number" id="did" placeholder="Complaint ID"/>
    <button onclick="del()">Delete</button>
    <pre id="dres"></pre>
</section>
</body>
</html>