package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Random;
import java.util.concurrent.*;

import models.Customer;

public class BookingServlet extends HttpServlet {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

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
            int driverId = getRandomDriverId(conn, vehicleType);
            int vehicleId = getVehicleByType(conn, vehicleType);

            if (driverId == -1 || vehicleId == -1) {
                response.getWriter().write("No driver or vehicle found.");
                return;
            }

            double distance = getRandomDistance();
            double fare = getRandomFare();

            String sql = "INSERT INTO bookings (customer_id, driver_id, vehicle_id, vehicle_type, pickup_location, dropoff_location, distance, fare, status, booking_time) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Pending', NOW())";

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, customer.getId());
                stmt.setInt(2, driverId);
                stmt.setInt(3, vehicleId);
                stmt.setString(4, vehicleType);
                stmt.setString(5, pickupLocation);
                stmt.setString(6, dropoffLocation);
                stmt.setDouble(7, distance);
                stmt.setDouble(8, fare);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int bookingId = rs.getInt(1);
                        confirmBookingAfterDelay(bookingId);
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

    // Get a random driver for the selected vehicle type (No availability check)
    private int getRandomDriverId(Connection conn, String vehicleType) throws SQLException {
        String sql = "SELECT id FROM drivers WHERE vehicle_type = ? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehicleType);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id") : -1;
        }
    }

    // Get a random vehicle of the selected type
    private int getVehicleByType(Connection conn, String vehicleType) throws SQLException {
        String sql = "SELECT vehicle_id FROM vehicles WHERE vehicle_type = ? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehicleType);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("vehicle_id") : -1;
        }
    }

    // Generate a random distance between 1 km and 50 km
    private double getRandomDistance() {
        return new Random().nextDouble() * 49 + 1;
    }

    // Generate a random fare between Rs.100 and Rs.2000
    private double getRandomFare() {
        return new Random().nextDouble() * 1900 + 100;
    }

    // Confirm the booking after 20 seconds
    private void confirmBookingAfterDelay(int bookingId) {
        executorService.submit(() -> {
            try {
                Thread.sleep(20000); // Wait for 20 seconds
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/megacitycab", "root", "1234")) {
                    String updateSql = "UPDATE bookings SET status = 'Confirmed' WHERE booking_id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                        stmt.setInt(1, bookingId);
                        stmt.executeUpdate();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
