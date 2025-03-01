<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="DatabaseConnection.DatabaseConnection"%>
<%@ page import="java.sql.*, java.util.*, models.Customer" %>

<%
    // Get logged-in customer
    HttpSession currentSession = request.getSession(false);
    Customer customer = (currentSession != null) ? (Customer) currentSession.getAttribute("customer") : null;

    if (customer == null) {
        response.sendRedirect("customer-login.jsp");
        return;
    }

    // Establish Database Connection
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking History</title>
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
        .container {
            max-width: 1500px;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 30px;
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
                <li class="nav-item"><a class="nav-link" href="book.jsp">Book a Ride</a></li>
                <li class="nav-item"><a class="nav-link active" href="booking-history.jsp">Booking History</a></li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white px-4 py-2" href="LogoutServlet">Logout</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Booking History Table -->
    <div class="container">
        <h2 class="text-center">Your Booking History</h2>

        <div class="table-responsive mt-4">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Booking ID</th>
                        <th>Customer ID</th>
                        <th>Driver ID</th>
                        <th>Vehicle ID</th>
                        <th>Vehicle Type</th> <%-- Added Vehicle Type --%>
                        <th>Pickup</th>
                        <th>Dropoff</th>
                        <th>Distance (km)</th>
                        <th>Fare (Rs.)</th>
                        <th>Status</th>
                        <th>Booking Time</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        try {
                            // Fetch all required details from bookings table
                            String fetchBookingsQuery = "SELECT booking_id, customer_id, driver_id, vehicle_id, vehicle_type, pickup_location, dropoff_location, distance, fare, status, booking_time FROM bookings WHERE customer_id = ? ORDER BY booking_time DESC";
                            pstmt = conn.prepareStatement(fetchBookingsQuery);
                            pstmt.setInt(1, customer.getId());
                            rs = pstmt.executeQuery();

                            int count = 1;
                            boolean hasBookings = false;

                            while (rs.next()) {
                                hasBookings = true;
                    %>
                    <tr>
                        <td><%= count++ %></td>
                        <td><%= rs.getInt("booking_id") %></td>
                        <td><%= rs.getInt("customer_id") %></td>
                        <td><%= rs.getInt("driver_id") %></td>
                        <td><%= rs.getInt("vehicle_id") %></td>
                        <td><%= rs.getString("vehicle_type") %></td> <%-- Display Vehicle Type --%>
                        <td><%= rs.getString("pickup_location") %></td>
                        <td><%= rs.getString("dropoff_location") %></td>
                        <td><%= rs.getDouble("distance") %></td>
                        <td>Rs. <%= rs.getDouble("fare") %></td>
                        <td>
                            <span class="badge 
                                <%= "Completed".equals(rs.getString("status")) ? "bg-success" : 
                                    "Pending".equals(rs.getString("status")) ? "bg-warning text-dark" : 
                                    "Cancelled".equals(rs.getString("status")) ? "bg-danger" : "bg-secondary" %>">
                                <%= rs.getString("status") %>
                            </span>
                        </td>
                        <td><%= rs.getTimestamp("booking_time") %></td>
                        <td>
                            <% if ("Pending".equals(rs.getString("status"))) { %>
                                <form action="CancelBookingServlet" method="post">
                                    <input type="hidden" name="booking_id" value="<%= rs.getInt("booking_id") %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                </form>
                            <% } else { %>
                                <span class="text-muted">-</span>
                            <% } %>
                        </td>
                    </tr>
                    <%
                            }

                            if (!hasBookings) {
                    %>
                        <tr><td colspan="13" class="text-center text-muted">No bookings found.</td></tr>
                    <%
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                    %>
                        <tr><td colspan="13" class="text-center text-danger">Error: <%= e.getMessage() %></td></tr>
                    <%
                        } finally {
                            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
                            if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
                            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <p class="mb-1">🚖 <strong>Mega City Cab</strong> - Your Trusted Ride</p>
        <p class="mb-0">&copy; 2025 Mega City Cab. All rights reserved.</p>
    </footer>

</body>
</html>
