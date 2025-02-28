<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<% if(request.getAttribute("error") != null) { out.println("Error: " + request.getAttribute("error")); } %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
</head>
<body>
    <h1 style="color: red;">Something went wrong!</h1>
    <p>Please try again later.</p>
    <a href="customer-signup.jsp">Go Back to Signup</a>
</body>
</html>