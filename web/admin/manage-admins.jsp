<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- Import ArrayList here -->
<%@ page import="models.Admin" %>
<%
    List<Admin> adminList = (List<Admin>) request.getAttribute("adminList");
    if (adminList == null) {
        adminList = new ArrayList<>(); // Make sure it's never null
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Admins</title>
</head>
<body>
    <h2>Manage Admins</h2>

    <!-- Add Admin Form -->
    <h3>Add Admin</h3>
    <form action="manage-admins" method="post">
        <input type="hidden" name="action" value="add">
        Name: <input type="text" name="name" required><br>
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        <button type="submit">Add Admin</button>
    </form>

    <!-- Admin List -->
    <h3>Admin List</h3>
    <% if (!adminList.isEmpty()) { %>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Username</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Admin admin : adminList) { %>
                    <tr>
                        <td><%= admin.getId() %></td>
                        <td><%= admin.getName() %></td>
                        <td><%= admin.getUsername() %></td>
                        <td>
                            <!-- Update Form -->
                            <form action="manage-admins" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="<%= admin.getId() %>">
                                Name: <input type="text" name="name" value="<%= admin.getName() %>" required>
                                Username: <input type="text" name="username" value="<%= admin.getUsername() %>" required>
                                Password: <input type="password" name="password" value="<%= admin.getPassword() %>" required>
                                <button type="submit">Update</button>
                            </form>

                            <!-- Delete Form -->
                            <form action="manage-admins" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= admin.getId() %>">
                                <button type="submit" onclick="return confirm('Are you sure you want to delete?');">Delete</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <p>No admins found.</p>
    <% } %>
</body>
</html>
