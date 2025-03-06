<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.DriverDAO" %>
<%@ page import="models.Driver" %>
<%@ page import="java.util.List" %>

<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    List<Driver> driverList = DriverDAO.getAllDrivers();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Drivers - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body { background-color: #f8f9fa; display: flex; flex-direction: column; min-height: 100vh; }
        .navbar, .footer { background-color: #343a40; color: white; }
        .footer { text-align: center; padding: 20px 0; margin-top: auto; }
        .btn-custom { background-color: #007bff; color: white; border-radius: 5px; }
        .btn-custom:hover { background-color: #0056b3; }
        .card { border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
    </style>
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
        <div class="alert alert-primary text-center">
            <h2>Manage Drivers</h2>
            <p>View, add, and remove drivers from the system.</p>
        </div>
    </div>

    <!-- Add Driver Form -->
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-primary text-white text-center">
                        <h3>Add New Driver</h3>
                    </div>
                    <div class="card-body">
                        <form action="ManageDriversServlet" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="mb-3">
                                <label class="form-label">Name</label>
                                <input type="text" name="name" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Phone</label>
                                <input type="text" name="phone" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Gender</label>
                                <select name="gender" class="form-control" required>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">License Number</label>
                                <input type="text" name="license_number" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Vehicle Type</label>
                                <input type="text" name="vehicle_type" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-custom w-100">Add Driver</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Drivers List -->
    <div class="container mt-4">
        <div class="card">
            <div class="card-header bg-secondary text-white text-center">
                <h3>Driver List</h3>
            </div>
            <div class="card-body">
                <% if (driverList == null || driverList.isEmpty()) { %>
                    <p class="text-center text-muted">No drivers available.</p>
                <% } else { %>
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Phone</th>
                                <th>Gender</th>
                                <th>License Number</th>
                                <th>Vehicle Type</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Driver driver : driverList) { %>
                                <tr>
                                    <td><%= driver.getId() %></td>
                                    <td><%= driver.getName() %></td>
                                    <td><%= driver.getPhone() %></td>
                                    <td><%= driver.getGender() %></td>
                                    <td><%= driver.getLicenseNumber() %></td>
                                    <td><%= driver.getVehicleType() %></td>
                                    <td>
                                        <a href="ManageDriversServlet?action=edit&id=<%= driver.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                                        <form action="ManageDriversServlet" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="id" value="<%= driver.getId() %>">
                                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?');">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } %>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2025 Mega City Cab. All rights reserved.</p>
    </footer>

</body>
</html>