<%@ page import="java.util.List" %>
<%@ page import="models.Customer" %>
<%@ page import="dao.CustomerDAO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Customers</title>
    <link rel="stylesheet" href="styles.css"> <!-- Include your CSS file -->
</head>
<body>
    <h2>Manage Customers</h2>

    <!-- Button to Add New Customer -->
    <button onclick="document.getElementById('addCustomerModal').style.display='block'">Add Customer</button>

    <!-- Customers Table -->
    <table border="1">
        <tr>
            <th>ID</th> <th>Name</th> <th>Email</th> <th>Username</th> <th>Address</th> <th>NIC</th> <th>Phone</th> <th>Actions</th>
        </tr>
        <%
            CustomerDAO customerDAO = new CustomerDAO();
            List<Customer> customers = customerDAO.getAllCustomers();
            for (Customer customer : customers) {
        %>
        <tr>
            <td><%= customer.getId() %></td>
            <td><%= customer.getName() %></td>
            <td><%= customer.getEmail() %></td>
            <td><%= customer.getUsername() %></td>
            <td><%= customer.getAddress() %></td>
            <td><%= customer.getNic() %></td>
            <td><%= customer.getPhone() %></td>
            <td>
                <form action="UpdateCustomerServlet" method="post">
                    <input type="hidden" name="id" value="<%= customer.getId() %>">
                    <input type="submit" value="Edit">
                </form>
                <form action="DeleteCustomerServlet" method="post" onsubmit="return confirm('Are you sure?');">
                    <input type="hidden" name="id" value="<%= customer.getId() %>">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <% } %>
    </table>

    <!-- Add Customer Modal -->
    <div id="addCustomerModal" style="display:none;">
        <form action="AddCustomerServlet" method="post">
            <label>Name:</label><input type="text" name="name" required><br>
            <label>Email:</label><input type="email" name="email" required><br>
            <label>Username:</label><input type="text" name="username" required><br>
            <label>Password:</label><input type="password" name="password" required><br>
            <label>Address:</label><input type="text" name="address" required><br>
            <label>NIC:</label><input type="text" name="nic" required><br>
            <label>Phone:</label><input type="text" name="phone" required><br>
            <input type="submit" value="Add Customer">
            <button type="button" onclick="document.getElementById('addCustomerModal').style.display='none'">Cancel</button>
        </form>
    </div>
</body>
</html>
