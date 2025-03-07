package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Random;
import models.Customer;

public class BookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Customer customer = (session != null) ? (Customer) session.getAttribute("customer") : null;
        if (customer == null) {
            response.sendRedirect("customer-login.jsp");
            return;
        }
        String pickupLocation = request.getParameter("pickup_location");
        String dropoffLocation = request.getParameter("dropoff_location");
        String vehicleType = request.getParameter("vehicle_type");
        if (pickupLocation == null || dropoffLocation == null || vehicleType == null) {
            response.getWriter().write("Missing required fields.");
            return;
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/megacitycab", "root", "1234")) {
            double fare = getRandomFare();
            String sql = "INSERT INTO bookings (customer_id, driver_id, vehicle_id, vehicle_type, pickup_location, dropoff_location, fare, status, booking_time) "
                       + "VALUES (?, NULL, NULL, ?, ?, ?, ?, 'Pending', NOW())";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, customer.getId()); // Set customer ID
                stmt.setString(2, vehicleType); // Set vehicle type
                stmt.setString(3, pickupLocation); // Set pickup location
                stmt.setString(4, dropoffLocation); // Set dropoff location
                stmt.setDouble(5, fare); // Set fare
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int bookingId = rs.getInt(1);
                    }
                    response.sendRedirect("booking-success.jsp");
                } else {
                    response.getWriter().write("Booking failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }

    // Generate a random fare between Rs.100 and Rs.2000
    private double getRandomFare() {
        return new Random().nextDouble() * 1900 + 100;
    }
}
