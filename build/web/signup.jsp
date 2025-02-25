<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Registration</title>
</head>
<body>
    <h2>Customer Registration</h2>

    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;"><%= request.getParameter("error") %></p>
    <% } %>
    
    <form action="RegisterServlet" method="post">
        <label>Full Name:</label>
        <input type="text" name="name" required><br>
        
        <label>Address:</label>
        <input type="text" name="address" required><br>
        
        <label>NIC:</label>
        <input type="text" name="nic" required><br>
        
        <label>Phone:</label>
        <input type="text" name="phone" required><br>
        
        <label>Username:</label>
        <input type="text" name="username" required><br>
        
        <label>Password:</label>
        <input type="password" name="password" required><br>

        <button type="submit">Register</button>
    </form>

    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</body>
</html>
