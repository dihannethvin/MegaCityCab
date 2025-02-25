<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Classes.User" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRole().equals("customer")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

    <div class="header">Mega City Cab - Customer Dashboard</div>

    <div style="text-align: center; margin-top: 50px;">
        <h2>Welcome, <%= user.getUsername() %> (Customer)</h2>
        <a href="logout.jsp">
            <button>Logout</button>
        </a>
    </div>

    <div class="footer">© 2025 Mega City Cab</div>

</body>
</html>
