<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Kite Auth Details</title>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            font-family: "Segoe UI", sans-serif;
            margin: 0;
            background-color: #f3f4f6;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
            padding: 32px 28px;
            width: 100%;
            max-width: 420px;
            animation: fadeIn 0.4s ease-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
        h2 {
            text-align: center;
            margin-bottom: 24px;
            color: #333;
        }
        .form-group {
            position: relative;
            margin-bottom: 20px;
        }
        .form-group i {
            position: absolute;
            top: 50%;
            left: 12px;
            transform: translateY(-50%);
            color: #888;
            font-size: 15px;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px 10px 10px 34px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
            color: #333;
        }
        input[type="text"]:focus {
            outline: none;
            border-color: #0078d4;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #0078d4;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            cursor: pointer;
            transition: background 0.2s ease;
        }
        input[type="submit"]:hover {
            background-color: #005fa3;
        }
        .message {
            text-align: center;
            margin-top: 15px;
            font-size: 14px;
            animation: fadeIn 0.3s ease;
        }
        .success {
            color: #28a745;
        }
        .error {
            color: #dc3545;
        }
        .get-url-container {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 25px;
            flex-wrap: wrap;
        }
        .get-url-container a {
            font-size: 14px;
            color: #0078d4;
            text-decoration: underline;
            cursor: pointer;
        }
        .get-url-container input[type="text"] {
            flex: 1;
            display: none;
            margin: 0;
            padding-left: 10px;
        }
        .copy-icon {
            background: none;
            border: none;
            cursor: pointer;
            font-size: 16px;
            color: #444;
            display: none;
        }
        .copy-icon:hover {
            color: #000;
        }
        @media (max-width: 480px) {
            .get-url-container {
                flex-direction: column;
                align-items: stretch;
            }
            .get-url-container input[type="text"],
            .copy-icon {
                width: 100%;
                text-align: center;
            }
        }
    </style>
    <script>
        function fetchLoginUrl() {
            fetch('/kite-auth/get-login-url')
                .then(response => response.text())
                .then(data => {
                    const field = document.getElementById('loginUrlField');
                    const copyBtn = document.getElementById('copyIcon');
                    field.value = data;
                    field.style.display = 'inline-block';
                    copyBtn.style.display = 'inline-block';
                });
        }

        function copyLoginUrl() {
            const field = document.getElementById('loginUrlField');
            navigator.clipboard.writeText(field.value)
                .then(() => {
                    const icon = document.getElementById('copyIcon');
                    const original = icon.innerHTML;
                    icon.innerHTML = '✔️';
                    setTimeout(() => icon.innerHTML = '📋', 1200);
                });
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Kite Authorization</h2>

        <div class="get-url-container">
            <a onclick="fetchLoginUrl()">Get Login URL</a>
            <input type="text" id="loginUrlField" readonly />
            <button id="copyIcon" class="copy-icon" onclick="copyLoginUrl()">📋</button>
        </div>

        <form method="post" action="/kite-auth/save">
            <div class="form-group">
                <i>🔑</i>
                <input type="text" id="requestToken" name="requestToken" required placeholder="Enter Request Token"/>
            </div>
            <input type="submit" value="Save Token"/>
        </form>

        <c:if test="${not empty success}">
            <div class="message success">${success}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>
    </div>
</body>
</html>
