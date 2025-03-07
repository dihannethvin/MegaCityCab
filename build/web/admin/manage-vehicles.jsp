<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Vehicle" %>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }

    List<Vehicle> vehicleList = (List<Vehicle>) request.getAttribute("vehicleList");

    if (vehicleList == null) {
        response.sendRedirect("manage-vehicles");
        return;
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Vehicles - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .navbar {
            background-color: #343a40 !important;
        }
        .footer {
            background-color: #343a40;
            color: white;
            text-align: center;
            padding: 20px 0;
            margin-top: auto;
        }
        .footer p {
            margin-bottom: 0;
        }
        .footer a {
            color: #ffc107;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
        .dashboard-links a {
            color: #007bff;
            font-weight: bold;
            text-decoration: none;
            margin-bottom: 10px;
            display: block;
        }
        .dashboard-links a:hover {
            text-decoration: underline;
        }
        .logout-btn {
            background-color: #dc3545;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            margin-top: 20px;
        }
        .logout-btn:hover {
            background-color: #c82333;
        }
        .btn-custom {
            background-color: #007bff;
            color: white;
        }
        .btn-custom:hover {
            background-color: #0056b3;
        }
        .card-header {
            background-color: #007bff;
            color: white;
        }
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
            <h2>Manage Vehicles</h2>
            <p>View, add, and remove vehicles from the system.</p>
        </div>
    </div>

    <!-- Add Vehicle Form -->
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-primary text-white text-center">
                        <h3>Add New Vehicle</h3>
                    </div>
                    <div class="card-body">
                        <form action="manage-vehicles" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="mb-3">
                                <label class="form-label">Plate Number</label>
                                <input type="text" name="plate_number" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Vehicle Type</label>
                                <select name="vehicle_type" class="form-control" required>
                                    <option value="car">Car</option>
                                    <option value="suv">SUV</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-custom w-100">Add Vehicle</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Vehicles List -->
    <div class="container mt-4 mb-4">
        <div class="card">
            <div class="card-header bg-secondary text-white text-center">
                <h3>Vehicle List</h3>
            </div>
            <div class="card-body">
                <% if (vehicleList == null || vehicleList.isEmpty()) { %>
                    <p class="text-center text-muted">No vehicles available.</p>
                <% } else { %>
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Plate Number</th>
                                <th>Vehicle Type</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Vehicle vehicle : vehicleList) { %>
                                <tr>
                                    <td><%= vehicle.getId() %></td>
                                    <td><%= vehicle.getPlateNumber() %></td>
                                    <td><%= vehicle.getVehicleType() %></td>
                                    <td>
                                        <!-- Edit Button for Modal -->
                                        <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#updateVehicleModal<%= vehicle.getId() %>">Edit</button>
                                        <!-- Delete Button -->
                                        <a href="manage-vehicles?action=delete&id=<%= vehicle.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                                    </td>
                                </tr>

                                <!-- Update Modal -->
                                <div class="modal fade" id="updateVehicleModal<%= vehicle.getId() %>" tabindex="-1" aria-labelledby="updateVehicleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="updateVehicleModalLabel">Update Vehicle Information</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="manage-vehicles" method="post">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="id" value="<%= vehicle.getId() %>">
                                                    <div class="mb-3">
                                                        <label class="form-label">Plate Number</label>
                                                        <input type="text" name="plate_number" class="form-control" value="<%= vehicle.getPlateNumber() %>" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Vehicle Type</label>
                                                        <select name="vehicle_type" class="form-control" required>
                                                            <option value="car" <%= "car".equals(vehicle.getVehicleType()) ? "selected" : "" %>>Car</option>
                                                            <option value="suv" <%= "suv".equals(vehicle.getVehicleType()) ? "selected" : "" %>>SUV</option>
                                                        </select>
                                                    </div>
                                                    <button type="submit" class="btn btn-custom w-100">Update Vehicle</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            <% } %>
                        </tbody>
                    </table>
                <% } %>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p class="mb-1">🚖 <strong>Mega City Cab</strong> - Admin Dashboard</p>
            <p class="mb-0">&copy; 2025 Mega City Cab. All rights reserved.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
