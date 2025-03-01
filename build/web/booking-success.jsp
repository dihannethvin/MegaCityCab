<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Successful</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="alert alert-success text-center">
            <h2>Booking Successful!</h2>
            <p>Your booking has been confirmed, and a driver has been assigned to you.</p>
        </div>

        <div class="card mt-4">
            <div class="card-header bg-primary text-white">
                Booking Details
            </div>
            <div class="card-body">
                <p><strong>Pickup Location:</strong> <%= request.getParameter("pickup_location") %></p>
                <p><strong>Dropoff Location:</strong> <%= request.getParameter("dropoff_location") %></p>
                <p><strong>Vehicle Type:</strong> <%= request.getParameter("vehicle_type") %></p>
                <p><strong>Fare:</strong> Rs. <%= request.getParameter("fare") %></p>
                <p><strong>Distance:</strong> <%= request.getParameter("distance") %> km</p>
            </div>
        </div>

        <div class="mt-4">
            <a href="customer-dashboard.jsp" class="btn btn-primary">Go to Dashboard</a>
            <a href="booking-history.jsp" class="btn btn-secondary">View Booking History</a>
        </div>
    </div>

</body>
</html>
