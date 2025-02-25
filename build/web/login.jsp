<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Classes.User" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    User user = (User) session.getAttribute("user");
    if (user != null) {
        if (user.getRole().equals("admin")) {
            response.sendRedirect("admin-dashboard.jsp");
        } else {
            response.sendRedirect("customer-dashboard.jsp");
        }
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Login - Mega City Cab</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="login.css">
</head>
<body>

    <div class="header">Mega City Cab</div>

    <form action="login" method="POST">
        <h2>Login</h2>
        <% if (request.getParameter("error") != null) { %>
            <p style="color:red;">Invalid Username or Password</p>
        <% } %>
        <label>Username:</label>
        <input type="text" name="username" required>

        <label>Password:</label>
        <input type="password" name="password" required>

        <button type="submit">Login</button>
    </form>

    <div class="footer">© 2025 Mega City Cab</div>

</body>
</html>
