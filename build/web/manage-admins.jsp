<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Admin" %>
<%
    List<Admin> adminList = (List<Admin>) request.getAttribute("adminList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Admins</title>
    <link rel="stylesheet" href="styles.css"> <!-- Include your CSS file -->
    <script>
        function setUpdateForm(id, name, username, password) {
            document.getElementById("updateId").value = id;
            document.getElementById("updateName").value = name;
            document.getElementById("updateUsername").value = username;
            document.getElementById("updatePassword").value = password;
        }
        function confirmDelete(id) {
            if (confirm("Are you sure you want to delete this admin?")) {
                document.getElementById("deleteForm" + id).submit();
            }
        }
    </script>
</head>
<body>
    <h2>Manage Admins</h2>

    <!-- Add Admin Button -->
    <button onclick="document.getElementById('addModal').style.display='block'">+ Add Admin</button>

    <!-- Admins Table -->
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
                    <button onclick="setUpdateForm('<%= admin.getId() %>', '<%= admin.getName() %>', '<%= admin.getUsername() %>', '<%= admin.getPassword() %>'); document.getElementById('updateModal').style.display='block'">Edit</button>
                    
                    <form id="deleteForm<%= admin.getId() %>" action="delete-admin" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= admin.getId() %>">
                        <button type="button" onclick="confirmDelete(<%= admin.getId() %>)">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <!-- Add Admin Modal -->
    <div id="addModal" style="display:none;">
        <form action="add-admin" method="post">
            <h3>Add Admin</h3>
            <label>Name:</label>
            <input type="text" name="name" required>
            <label>Username:</label>
            <input type="text" name="username" required>
            <label>Password:</label>
            <input type="password" name="password" required>
            <button type="submit">Add</button>
            <button type="button" onclick="document.getElementById('addModal').style.display='none'">Cancel</button>
        </form>
    </div>

    <!-- Update Admin Modal -->
    <div id="updateModal" style="display:none;">
        <form action="update-admin" method="post">
            <h3>Update Admin</h3>
            <input type="hidden" id="updateId" name="id">
            <label>Name:</label>
            <input type="text" id="updateName" name="name" required>
            <label>Username:</label>
            <input type="text" id="updateUsername" name="username" required>
            <label>Password:</label>
            <input type="password" id="updatePassword" name="password" required>
            <button type="submit">Update</button>
            <button type="button" onclick="document.getElementById('updateModal').style.display='none'">Cancel</button>
        </form>
    </div>
</body>
</html>
