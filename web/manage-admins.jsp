<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Classes.User, Classes.AdminDAO, java.util.List" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRole().equals("admin")) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<User> admins = AdminDAO.getAllAdmins();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Admins</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="sidebar.css">
    <link rel="stylesheet" href="manage-admins.css">
</head>
<body>

    <!-- Sidebar -->
    <div class="sidebar">
        <h2>Mega City Cab</h2>
        <ul>
            <li><a href="admin-dashboard.jsp">Dashboard</a></li>
            <li><a href="manage-bookings.jsp">Manage Bookings</a></li>
            <li class="active"><a href="manage-admins.jsp">Manage Admins</a></li>
            <li><a href="manage-customers.jsp">Manage Customers</a></li>
            <li><a href="manage-drivers.jsp">Manage Drivers</a></li>
            <li><a href="manage-cars.jsp">Manage Cars</a></li>
            <li><a href="view-reports.jsp">View Reports</a></li>
            <li><a href="logout.jsp" class="logout">Logout</a></li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="header">Mega City Cab - Manage Admins</div>

        <div class="dashboard-content">

            <!-- Success or Error Message -->
            <% String message = request.getParameter("message"); %>
            <% if (message != null) { %>
                <p class="success-message"><%= message %></p>
            <% } %>

            <% String error = request.getParameter("error"); %>
            <% if (error != null) { %>
                <p class="error-message"><%= error %></p>
            <% } %>

            <!-- Add Admin Form -->
            <div class="form-container">
                <h3>Add New Admin</h3>
                <form action="AdminServlet" method="POST">
                    <input type="hidden" name="action" value="add">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>

                    <button type="submit" class="btn">Add Admin</button>
                </form>
            </div>

            <!-- Admins Table -->
            <h3>Existing Admins</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (User admin : admins) { %>
                        <tr>
                            <td><%= admin.getId() %></td>
                            <td><%= admin.getUsername() %></td>
                            <td>
                                <form action="AdminServlet" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= admin.getId() %>">
                                    <button type="submit" class="delete-btn">Delete</button>
                                </form>

                                <button class="edit-btn" onclick="openEditForm('<%= admin.getId() %>', '<%= admin.getUsername() %>')">Edit</button>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <!-- Edit Admin Popup -->
            <div id="editPopup" class="popup">
                <div class="popup-content">
                    <h3>Edit Admin</h3>
                    <form action="AdminServlet" method="POST">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" id="edit-id" name="id">
                        
                        <label for="edit-username">Username:</label>
                        <input type="text" id="edit-username" name="username" required>

                        <label for="edit-password">New Password:</label>
                        <input type="password" id="edit-password" name="password">

                        <button type="submit" class="btn">Update</button>
                        <button type="button" class="btn cancel-btn" onclick="closeEditForm()">Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function openEditForm(id, username) {
            document.getElementById("edit-id").value = id;
            document.getElementById("edit-username").value = username;
            document.getElementById("editPopup").style.display = "block";
        }

        function closeEditForm() {
            document.getElementById("editPopup").style.display = "none";
        }
    </script>

</body>
</html>
