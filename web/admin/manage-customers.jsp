<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Customer" %>
<%@ page import="dao.CustomerDAO" %>

<%
    // Fetch customers from DAO
    CustomerDAO customerDAO = new CustomerDAO();
    List<Customer> customers = customerDAO.getAllCustomers();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Customers - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">🚖 Mega City Cab</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="admin-dashboard.jsp">Dashboard</a></li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-danger text-white px-4 py-2" href="../LogoutServlet">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="container mt-4">
        <h2 class="text-center">Manage Customers</h2>
        <p class="text-center">View, add, update, and delete customers.</p>
    </div>

    <!-- Add Customer Form -->
    <div class="container">
        <div class="card">
            <div class="card-header bg-primary text-white">Add New Customer</div>
            <div class="card-body">
                <form action="manage-customers" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Name</label>
                            <input type="text" name="name" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Address</label>
                            <input type="text" name="address" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">NIC</label>
                            <input type="text" name="nic" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Phone</label>
                            <input type="text" name="phone" class="form-control" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Add Customer</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Customer List -->
    <div class="container mt-4">
        <div class="card">
            <div class="card-header bg-secondary text-white">Customer List</div>
            <div class="card-body">
                <table class="table table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Username</th>
                            <th>Address</th>
                            <th>NIC</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Customer customer : customers) { %>
                            <tr>
                                <td><%= customer.getId() %></td>
                                <td><%= customer.getName() %></td>
                                <td><%= customer.getEmail() %></td>
                                <td><%= customer.getUsername() %></td>
                                <td><%= customer.getAddress() %></td>
                                <td><%= customer.getNic() %></td>
                                <td><%= customer.getPhone() %></td>
                                <td>
                                    <button class="btn btn-warning btn-sm" 
                                        onclick="openEditModal('<%= customer.getId() %>', '<%= customer.getName() %>', '<%= customer.getEmail() %>', '<%= customer.getUsername() %>', '<%= customer.getAddress() %>', '<%= customer.getNic() %>', '<%= customer.getPhone() %>')">
                                        Edit
                                    </button>
                                    <form action="manage-customers" method="post" style="display:inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="<%= customer.getId() %>">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?');">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Update Customer Modal -->
    <div class="modal fade" id="editCustomerModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Edit Customer</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="manage-customers" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="editCustomerId">

                        <div class="mb-3">
                            <label class="form-label">Name</label>
                            <input type="text" name="name" id="editCustomerName" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" id="editCustomerEmail" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" name="username" id="editCustomerUsername" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password (Leave blank to keep current)</label>
                            <input type="password" name="password" id="editCustomerPassword" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Address</label>
                            <input type="text" name="address" id="editCustomerAddress" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">NIC</label>
                            <input type="text" name="nic" id="editCustomerNIC" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Phone</label>
                            <input type="text" name="phone" id="editCustomerPhone" class="form-control" required>
                        </div>

                        <button type="submit" class="btn btn-success w-100">Update Customer</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap & JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function openEditModal(id, name, email, username, address, nic, phone) {
            document.getElementById("editCustomerId").value = id;
            document.getElementById("editCustomerName").value = name;
            document.getElementById("editCustomerEmail").value = email;
            document.getElementById("editCustomerUsername").value = username;
            document.getElementById("editCustomerAddress").value = address;
            document.getElementById("editCustomerNIC").value = nic;
            document.getElementById("editCustomerPhone").value = phone;

            new bootstrap.Modal(document.getElementById("editCustomerModal")).show();
        }
    </script>

</body>
</html>
