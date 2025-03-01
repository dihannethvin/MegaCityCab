<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us - Mega City Cab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background: url('images/hero-wallpaper.jpg') center/cover no-repeat;
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

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp">🚖 Mega City Cab</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="index.jsp">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="about.jsp">About Us</a></li>
                    <li class="nav-item"><a class="nav-link active" href="contact.jsp">Contact Us</a></li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-primary text-white px-4 py-2 mx-2 fw-bold shadow-lg" 
                           href="customer-login.jsp" 
                           style="border-radius: 5px; transition: 0.3s; box-shadow: 0 0 10px rgba(0, 123, 255, 0.7);">
                            Are you a Customer?
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btn btn-primary text-white px-4 py-2 mx-2 fw-bold shadow-lg" 
                           href="admin-login.jsp" 
                           style="border-radius: 5px; transition: 0.3s; box-shadow: 0 0 10px rgba(255, 193, 7, 0.7);">
                            Are you an Admin?
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Contact Us</h2>
        <p>Have questions? Feel free to reach out to us!</p>
        <p><strong>Email:</strong> support@megacitycab.com</p>
        <p><strong>Phone:</strong> +94 71 234 5678</p>
        <p><strong>Address:</strong> No. 123, Main Street, Colombo, Sri Lanka</p>
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
