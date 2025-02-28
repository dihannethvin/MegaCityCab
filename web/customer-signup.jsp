<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Signup - Mega City Cab</title>
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
        .card {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin: 0 auto;
            margin-top: 50px;
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
        .signup-form input {
            margin-bottom: 15px;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            border-radius: 5px;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .login-link {
            text-align: center;
            margin-top: 15px;
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
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="about.jsp">About Us</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="contact.jsp">Contact Us</a>
                    </li>
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

    <!-- Content -->
    <div class="container my-5">
        <div class="card shadow-lg">
            <h2 class="fw-bold mb-4 text-center">Customer Sign Up</h2>

            <!-- Sign Up Form -->
            <form action="CustomerSignup" method="post" class="signup-form">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <input type="text" id="address" name="address" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="nic" class="form-label">NIC</label>
                    <input type="text" id="nic" name="nic" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Phone</label>
                    <input type="text" id="phone" name="phone" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary w-100">Sign Up</button>
            </form>
            
            <c:if test="${not empty error}">
                <div style="color:red">${error}</div>
            </c:if>

            <c:if test="${not empty success}">
                <div style="color:green">${success}</div>
            </c:if>

            <!-- Login Link -->
            <div class="login-link">
                <p>Already have an account? <a href="customer-login.jsp" class="text-decoration-none text-primary">Login here</a></p>
            </div>
        </div>
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

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
