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
        .card {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 700px;
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
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="admin-dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="manage-admins.jsp">Manage Admins</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="manage-drivers.jsp">Manage Drivers</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="manage-vehicles.jsp">Manage Vehicles</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="manage-customers.jsp">Manage Customers</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="LogoutServlet">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Admin Dashboard Content -->
    <div class="container my-5">
        <div class="card shadow-lg">
            <h2 class="fw-bold mb-4 text-center">Welcome, Admin</h2>

            <div class="dashboard-links">
                <a href="manage-admins.jsp">Manage Admins</a>
                <a href="manage-drivers.jsp">Manage Drivers</a>
                <a href="manage-vehicles.jsp">Manage Vehicles</a>
                <a href="manage-customers.jsp">Manage Customers</a>
            </div>

            <a href="LogoutServlet" class="logout-btn w-100 text-center">Logout</a>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <p class="mb-0">🚖 <strong>Mega City Cab</strong> - Admin Dashboard</p>
        </div>
    </footer>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
