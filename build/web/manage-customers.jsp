<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="Classes.User, Classes.UserDAO, java.util.List" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRole().equals("admin")) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<User> customers = UserDAO.getAllCustomers();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Customers</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="sidebar.css">
    <link rel="stylesheet" href="manage-customers.css">
</head>
<body>

    <!-- Sidebar -->
    <div class="sidebar">
        <h2>Mega City Cab</h2>
        <ul>
            <li><a href="admin-dashboard.jsp">Dashboard</a></li>
            <li><a href="manage-bookings.jsp">Manage Bookings</a></li>
            <li><a href="manage-admins.jsp">Manage Admins</a></li>
            <li class="active"><a href="manage-customers.jsp">Manage Customers</a></li>
            <li><a href="manage-drivers.jsp">Manage Drivers</a></li>
            <li><a href="manage-cars.jsp">Manage Cars</a></li>
            <li><a href="view-reports.jsp">View Reports</a></li>
            <li><a href="logout.jsp" class="logout">Logout</a></li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="header">Mega City Cab - Manage Customers</div>

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

            <!-- Add Customer Form -->
            <div class="form-container">
                <h3>Add New Customer</h3>
                <form action="CustomerServlet" method="POST">
                    <input type="hidden" name="action" value="add">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>

                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" required>

                    <label for="phone">Phone:</label>
                    <input type="text" id="phone" name="phone" required>

                    <button type="submit" class="btn">Add Customer</button>
                </form>
            </div>

            <!-- Customers Table -->
            <h3>Existing Customers</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Name</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (User customer : customers) { %>
                        <tr>
                            <td><%= customer.getId() %></td>
                            <td><%= customer.getUsername() %></td>
                            <td><%= customer.getName() %></td>
                            <td><%= customer.getPhone() %></td>
                            <td>
                                <form action="CustomerServlet" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= customer.getId() %>">
                                    <button type="submit" class="delete-btn">Delete</button>
                                </form>

                                <button class="edit-btn" onclick="openEditForm('<%= customer.getId() %>', '<%= customer.getUsername() %>', '<%= customer.getName() %>', '<%= customer.getPhone() %>')">Edit</button>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <!-- Edit Customer Popup -->
            <div id="editPopup" class="popup">
                <div class="popup-content">
                    <h3>Edit Customer</h3>
                    <form action="CustomerServlet" method="POST">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" id="edit-id" name="id">
                        
                        <label for="edit-username">Username:</label>
                        <input type="text" id="edit-username" name="username" required>

                        <label for="edit-name">Name:</label>
                        <input type="text" id="edit-name" name="name" required>

                        <label for="edit-phone">Phone:</label>
                        <input type="text" id="edit-phone" name="phone" required>

                        <button type="submit" class="btn">Update</button>
                        <button type="button" class="btn cancel-btn" onclick="closeEditForm()">Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function openEditForm(id, username, name, phone) {
            document.getElementById("edit-id").value = id;
            document.getElementById("edit-username").value = username;
            document.getElementById("edit-name").value = name;
            document.getElementById("edit-phone").value = phone;
            document.getElementById("editPopup").style.display = "block";
        }

        function closeEditForm() {
            document.getElementById("editPopup").style.display = "none";
        }
    </script>

</body>
</html>
