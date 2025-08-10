<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Municipal Complaint System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem; }
        a { display: inline-block; margin: 0.5rem 0; }
        .token { margin-top: 1rem; }
    </style>
</head>
<body>
<h2>Municipal Complaint System</h2>
<p>
    <a href="/login">Login (Paste JWT)</a>
</p>
<p>Dashboards:</p>
<ul>
    <li><a href="/citizen-ui">Citizen</a></li>
    <li><a href="/staff-ui">Staff</a></li>
    <li><a href="/admin-ui">Admin</a></li>
</ul>
<div class="token">
    <script>
        function getCookie(name){return document.cookie.split('; ').find(r=>r.startsWith(name+'='))?.split('=')[1];}
        const t = getCookie('jwt');
        document.write('JWT cookie present: ' + (t ? 'Yes' : 'No'));
    </script>
</div>
</body>
</html>