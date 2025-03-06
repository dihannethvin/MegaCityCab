package servlets.admin;

import DatabaseConnection.DatabaseConnection;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import models.Booking;

public class ManageBookingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if admin is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT b.id, b.customer_id, c.name AS customer_name, b.vehicle_type, b.pickup_location, "
                     + "b.dropoff_location, b.distance, b.fare, b.status, b.booking_time, "
                     + "d.id AS driver_id, d.name AS driver_name "
                     + "FROM bookings b "
                     + "INNER JOIN customers c ON b.customer_id = c.id "
                     + "LEFT JOIN drivers d ON b.driver_id = d.id "  // Changed to LEFT JOIN to handle missing driver assignments
                     + "ORDER BY b.booking_time DESC")) {
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("vehicle_type"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getDouble("distance"),
                        rs.getDouble("fare"),
                        rs.getString("status"),
                        rs.getTimestamp("booking_time"),
                        rs.getString("customer_name"), // Set customer name
                        rs.getInt("driver_id")         // Set driver ID (can be null)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("manage-bookings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }
        
        String bookingId = request.getParameter("booking_id");
        String newStatus = request.getParameter("status");
        
        if (bookingId == null || newStatus == null) {
            response.sendRedirect("manage-bookings.jsp?error=Missing booking details");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE bookings SET status = ? WHERE id = ?")) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, Integer.parseInt(bookingId));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                response.sendRedirect("manage-bookings.jsp?success=Booking updated successfully");
            } else {
                response.sendRedirect("manage-bookings.jsp?error=Failed to update booking");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("manage-bookings.jsp?error=Database error: " + e.getMessage());
        }
    }
}