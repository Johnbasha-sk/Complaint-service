<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - JWT</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2rem; }
        input[type=text] { width: 480px; }
    </style>
</head>
<body>
<h3>Paste your JWT token</h3>
<form method="post" action="/login">
    <label>JWT Token</label><br/>
    <input type="text" name="token" placeholder="eyJhbGciOi..." required/>
    <button type="submit">Save Token</button>
</form>
<p><a href="/">Back</a></p>
</body>
</html>