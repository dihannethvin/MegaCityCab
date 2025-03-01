package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import models.Customer;

public class CancelBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session to retrieve the customer object
        HttpSession session = request.getSession(false);
        Customer customer = (session != null) ? (Customer) session.getAttribute("customer") : null;

        if (customer == null) {
            response.sendRedirect("customer-login.jsp"); // Redirect if customer is not logged in
            return;
        }

        // Get the booking ID from the request
        int bookingId = Integer.parseInt(request.getParameter("booking_id"));

        // Prepare SQL query to cancel the booking
        String sql = "UPDATE bookings SET status = 'Cancelled' WHERE booking_id = ? AND customer_id = ? AND status = 'Pending'";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/megacitycab", "root", "1234");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the booking ID and customer ID in the prepared statement
            stmt.setInt(1, bookingId);
            stmt.setInt(2, customer.getId());  // Use the customer ID from session

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("booking-history.jsp");  // Redirect to booking history page after successful cancellation
            } else {
                response.getWriter().write("No booking found or booking already confirmed/cancelled.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }
}
