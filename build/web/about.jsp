<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .hero-section {
            background: url('images/hero-wallpaper.jpg') center/cover no-repeat;
            height: 80vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            text-align: center;
            position: relative;
        }
        .hero-overlay {
            background: rgba(0, 0, 0, 0.6);
            padding: 50px;
            border-radius: 10px;
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

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp">🚖 Mega City Cab</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>
                    <li class="nav-item"><a class="nav-link active" href="about.jsp">About Us</a></li>
                    <li class="nav-item"><a class="nav-link" href="contact.jsp">Contact Us</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>About Mega City Cab</h2>
        <p>Welcome to Mega City Cab, the most trusted cab service in Colombo. Our goal is to provide a comfortable, reliable, and affordable taxi experience for our customers.</p>
        <h3>Why Choose Us?</h3>
        <ul>
            <li>24/7 Service</li>
            <li>Safe & Secure Rides</li>
            <li>Affordable Pricing</li>
            <li>Professional Drivers</li>
        </ul>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p class="mb-1">🚖 <strong>Mega City Cab</strong> - Your Trusted Ride</p>
            <p>
                <a href="index.jsp">Home</a> | 
                <a href="about.jsp">About</a> | 
                <a href="contact.jsp">Contact</a> | 
                <a href="customer-login.jsp">Customer Login</a> | 
                <a href="admin-login.jsp">Admin Login</a>
            </p>
            <p class="mb-0">&copy; 2025 Mega City Cab. All rights reserved.</p>
        </div>
    </footer>
        
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
