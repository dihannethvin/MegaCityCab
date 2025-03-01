<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Mega City Cab</title>
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
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto"> <!-- Add ms-auto here to push items to the right -->
                    <li class="nav-item"><a class="nav-link active" href="admin-dashboard.jsp">Dashboard</a></li>
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
            <h2>Welcome, Admin!</h2>
            <p>Manage all aspects of Mega City Cab's operations here.</p>
        </div>
    </div>

    <!-- Admin Dashboard Content -->
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-6 mb-3">
                <div class="card shadow-lg">
                    <div class="card-body">
                        <h3 class="fw-bold text-center">Manage Admins</h3>
                        <p class="text-center">Add, Update, and Delete Admin Users.</p>
                        <a href="manage-admins.jsp" class="btn btn-primary w-100">Go to Manage Admins</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="card shadow-lg">
                    <div class="card-body">
                        <h3 class="fw-bold text-center">Manage Drivers</h3>
                        <p class="text-center">Add, Update, and Delete Drivers.</p>
                        <a href="manage-drivers.jsp" class="btn btn-primary w-100">Go to Manage Drivers</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="card shadow-lg">
                    <div class="card-body">
                        <h3 class="fw-bold text-center">Manage Vehicles</h3>
                        <p class="text-center">Add, Update, and Delete Vehicles.</p>
                        <a href="manage-vehicles.jsp" class="btn btn-primary w-100">Go to Manage Vehicles</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="card shadow-lg">
                    <div class="card-body">
                        <h3 class="fw-bold text-center">Manage Customers</h3>
                        <p class="text-center">Add, Update, and Delete Customer Records.</p>
                        <a href="manage-customers.jsp" class="btn btn-primary w-100">Go to Manage Customers</a>
                    </div>
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

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
