<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Booking, models.Driver" %>
<%@ page import="dao.BookingDAO, dao.DriverDAO" %>
<%
    // Session handling - Redirect if not logged in
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
    
    // Fetch bookings and drivers from request attributes
    List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
    List<Driver> drivers = (List<Driver>) request.getAttribute("drivers");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Bookings - Mega City Cab</title>
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
        <h2 class="text-center">Manage Bookings</h2>
        <p class="text-center">Assign drivers, update or delete bookings.</p>
    </div>

    <!-- Booking List -->
    <div class="container mt-4">
        <div class="card">
            <div class="card-header bg-secondary text-white">Booking List</div>
            <div class="card-body">
                <table class="table table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>Booking ID</th>
                            <th>Customer Name</th>
                            <th>Vehicle Type</th>
                            <th>Plate Number</th>
                            <th>Pickup Location</th>
                            <th>Dropoff Location</th>
                            <th>Fare</th>
                            <th>Status</th>
                            <th>Booking Time</th>
                            <th>Driver</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (bookings != null && !bookings.isEmpty()) { %>
                            <% for (Booking booking : bookings) { %>
                                <tr>
                                    <td><%= booking.getId() %></td>
                                    <td><%= booking.getCustomerName() %></td> <!-- Ensure correct customer name here -->
                                    <td><%= booking.getVehicleType() %></td> <!-- Correct vehicle type -->
                                    <td><%= booking.getPlateNumber() != null ? booking.getPlateNumber() : "Not Assigned" %></td>
                                    <td><%= booking.getPickupLocation() %></td>
                                    <td><%= booking.getDropoffLocation() %></td>
                                    <td><%= booking.getFare() %></td>
                                    <td><%= booking.getStatus() %></td>
                                    <td><%= booking.getBookingTime() %></td>
                                    <td>
                                        <%= (booking.getDriverName() != null) ? booking.getDriverName() : "Not Assigned" %>
                                    </td>
                                    <td>
                                        <% if (booking.getDriverId() == 0) { %>
                                            <!-- Assign Driver -->
                                            <button class="btn btn-primary btn-sm"
                                                onclick="openAssignModal('<%= booking.getId() %>')">
                                                Assign Driver
                                            </button>
                                        <% } %>
                                        <!-- Update Booking -->
                                        <button class="btn btn-warning btn-sm"
                                            onclick="openEditModal('<%= booking.getId() %>', '<%= booking.getPickupLocation() %>', '<%= booking.getDropoffLocation() %>', '<%= booking.getBookingTime() %>')">
                                            Edit
                                        </button>
                                        <!-- Delete Booking -->
                                        <form action="manage-bookings" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="id" value="<%= booking.getId() %>">
                                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?');">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            <% } %>
                        <% } else { %>
                            <tr>
                                <td colspan="12" class="text-center">No bookings available.</td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Assign Driver Modal -->
    <div class="modal fade" id="assignDriverModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Assign Driver</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="AssignDriverServlet" method="post">
                        <input type="hidden" name="booking_id" id="assignBookingId">
                        <div class="mb-3">
                            <label class="form-label">Select Driver</label>
                            <select name="driver_id" class="form-control" required>
                                <% if (drivers != null && !drivers.isEmpty()) { %>
                                    <% for (Driver driver : drivers) { %>
                                        <option value="<%= driver.getId() %>"><%= driver.getName() %></option>
                                    <% } %>
                                <% } %>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Assign Driver</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Update Booking Modal -->
    <div class="modal fade" id="editBookingModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Edit Booking</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="manage-bookings" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="editBookingId">
                        <div class="mb-3">
                            <label class="form-label">Pickup Location</label>
                            <input type="text" name="pickup" id="editPickup" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Dropoff Location</label>
                            <input type="text" name="dropoff" id="editDropoff" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Date & Time</label>
                            <input type="datetime-local" name="date_time" id="editDateTime" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Update Booking</button>
                    </form>
                </div>
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

    <!-- Bootstrap & JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function openAssignModal(bookingId) {
            document.getElementById("assignBookingId").value = bookingId;
            new bootstrap.Modal(document.getElementById("assignDriverModal")).show();
        }
        function openEditModal(id, pickup, dropoff, dateTime) {
            document.getElementById("editBookingId").value = id;
            document.getElementById("editPickup").value = pickup;
            document.getElementById("editDropoff").value = dropoff;
            document.getElementById("editDateTime").value = dateTime;
            new bootstrap.Modal(document.getElementById("editBookingModal")).show();
        }
    </script>
</body>
</html>
