<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
    <!-- Add your CSS styles or include external stylesheets here -->
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: #f8f8f8;
        }

        .error-container {
            margin: 50px auto;
            padding: 20px;
            max-width: 600px;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #ff0000;
        }

        p {
            color: #333;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h1>Error</h1>
    <p>${error}</p>
    <!-- Add any additional details or instructions here -->
    <p><a href="index.html">Go back to the home page</a></p>
</div>
</body>
</html>
