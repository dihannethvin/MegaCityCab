<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="models.Customer" %>

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
    <title>Customer Dashboard</title>
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
        .footer a {
            color: #ffc107;
            text-decoration: none;
            margin: 0 10px;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">🚖 Mega City Cab</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="book.jsp">Book a Ride</a></li>
                    <li class="nav-item"><a class="nav-link" href="booking-history.jsp">Booking History</a></li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-danger text-white px-4 py-2" href="LogoutServlet">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="container mt-4">
        <div class="alert alert-primary text-center">
            <h2>Welcome, <%= customer.getName() %>!</h2>
            <p>Manage your rides and profile with ease.</p>
        </div>
    </div>

    <!-- Dashboard Content -->
    <div class="container mt-4">
        <ul class="nav nav-tabs" id="dashboardTabs">
            <li class="nav-item"><a class="nav-link active" data-bs-toggle="tab" href="#profile">Profile</a></li>
        </ul>

        <div class="tab-content mt-4">
            <!-- Profile Section -->
            <div id="profile" class="tab-pane fade show active">
                <h3>My Profile</h3>
                <p><strong>Full Name:</strong> <%= customer.getName() %></p>
                <p><strong>Username:</strong> <%= customer.getUsername() %></p>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p class="mb-1">🚖 <strong>Mega City Cab</strong> - Your Trusted Ride</p>
            <p class="mb-0">&copy; 2025 Mega City Cab. All rights reserved.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
