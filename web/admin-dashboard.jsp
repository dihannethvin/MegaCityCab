<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Classes.User, Classes.UserDAO, Classes.AdminDAO" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRole().equals("admin")) {
        response.sendRedirect("login.jsp");
        return;
    }

    int totalCustomers = UserDAO.getTotalCustomers();
    int totalAdmins = AdminDAO.getTotalAdmins();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="sidebar.css">
    <link rel="stylesheet" href="admin-dashboard.css">
</head>
<body>

    <!-- Sidebar -->
    <div class="sidebar">
        <h2>Mega City Cab</h2>
        <ul>
            <li class="active"><a href="admin-dashboard.jsp">Dashboard</a></li>
            <li><a href="manage-bookings.jsp">Manage Bookings</a></li>
            <li><a href="manage-admins.jsp">Manage Admins</a></li>
            <li><a href="manage-customers.jsp">Manage Customers</a></li>
            <li><a href="manage-drivers.jsp">Manage Drivers</a></li>
            <li><a href="manage-cars.jsp">Manage Cars</a></li>
            <li><a href="view-reports.jsp">View Reports</a></li>
            <li><a href="logout.jsp" class="logout">Logout</a></li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="header">Mega City Cab - Admin Dashboard</div>

        <div class="dashboard-content">
            <h2>Welcome, <%= user.getUsername() %> (Admin)</h2>
            <p>Manage your cab service operations easily from this dashboard.</p>
            
            <!-- Dashboard Stats Grid -->
            <div class="dashboard-grid">
                <div class="dashboard-card admins">
                    <i class="icon">&#128100;</i>
                    <h3><%= totalAdmins %></h3>
                    <p>Admins</p>
                </div>
                <div class="dashboard-card customers">
                    <i class="icon">&#128100;</i>
                    <h3><%= totalCustomers %></h3>
                    <p>Customers</p>
                </div>
               
            </div>
        </div>
    </div>

</body>
</html>
