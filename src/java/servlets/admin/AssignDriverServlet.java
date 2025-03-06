package servlets.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AssignDriverServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int bookingId = Integer.parseInt(request.getParameter("booking_id"));
        int driverId = Integer.parseInt(request.getParameter("driver_id"));

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/megacitycab", "root", "1234")) {
            // Get vehicle assigned to the driver
            String getVehicleSql = "SELECT vehicle_id FROM vehicles WHERE driver_id = ?";
            int vehicleId = -1;

            try (PreparedStatement vehicleStmt = conn.prepareStatement(getVehicleSql)) {
                vehicleStmt.setInt(1, driverId);
                ResultSet rs = vehicleStmt.executeQuery();
                if (rs.next()) {
                    vehicleId = rs.getInt("vehicle_id");
                }
            }

            if (vehicleId == -1) {
                response.sendRedirect("manage-bookings.jsp?error=No vehicle assigned to this driver.");
                return;
            }

            // Update booking record with the assigned driver and vehicle
            String updateSql = "UPDATE bookings SET driver_id = ?, vehicle_id = ?, status = 'Confirmed' WHERE booking_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, driverId);
                updateStmt.setInt(2, vehicleId);
                updateStmt.setInt(3, bookingId);
                int rowsUpdated = updateStmt.executeUpdate();
                
                if (rowsUpdated > 0) {
                    response.sendRedirect("manage-bookings.jsp?success=Driver assigned successfully.");
                } else {
                    response.sendRedirect("manage-bookings.jsp?error=Failed to assign driver.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("manage-bookings.jsp?error=Database error: " + e.getMessage());
        }
    }
}
