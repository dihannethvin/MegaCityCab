<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Customer" %>

<%
    HttpSession currentSession = request.getSession(false);
    Customer customer = (currentSession != null) ? (Customer) currentSession.getAttribute("customer") : null;

    if (customer == null) {
        response.sendRedirect("customer-login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book a Ride</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
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
        .footer a {
            color: #ffc107;
            text-decoration: none;
            margin: 0 10px;
        }
        .footer a:hover {
            text-decoration: underline;
        }
        .container {
            max-width: 600px;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 30px;
        }
        #map {
            width: 100%;
            height: 400px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand fw-bold" href="#">🚖 Mega City Cab</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="customer-dashboard.jsp">Dashboard</a></li>
                <li class="nav-item"><a class="nav-link active" href="book.jsp">Book a Ride</a></li>
                <li class="nav-item"><a class="nav-link" href="booking-history.jsp">Booking History</a></li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white px-4 py-2" href="LogoutServlet">Logout</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Booking Form -->
    <div class="container">
        <h2 class="text-center">Book Your Ride</h2>

        <form action="BookingServlet" method="post">
            <div class="mb-3">
                <label class="form-label">Full Name</label>
                <input type="text" class="form-control" name="customer_name" value="<%= customer.getName() %>" readonly>
            </div>

            <div class="mb-3">
                <label class="form-label">Phone Number</label>
                <input type="tel" class="form-control" name="phone" value="<%= customer.getPhone() %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Pickup Location</label>
                <input type="text" class="form-control" id="pickup_location" name="pickup_location" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Drop-off Location</label>
                <input type="text" class="form-control" id="dropoff_location" name="dropoff_location" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Select Vehicle Type</label>
                <select class="form-select" name="vehicle_type" id="vehicle_type" required>
                    <option value="car">Car</option>
                    <option value="suv">SUV</option>
                </select>
            </div>

            <input type="hidden" id="distance" name="distance">
            <input type="hidden" id="fare" name="fare">

            <div class="d-grid gap-2 mt-3">
                <button type="submit" class="btn btn-primary">Confirm Booking</button>
            </div>
        </form>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <p class="mb-1">🚖 <strong>Mega City Cab</strong> - Your Trusted Ride</p>
        <p class="mb-0">&copy; 2025 Mega City Cab. All rights reserved.</p>
    </footer>


</body>
</html>
