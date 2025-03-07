package dao;

import DatabaseConnection.DatabaseConnection;
import models.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Retrieve all bookings including driver and vehicle details (For Admins)
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.booking_id, b.customer_id, c.name AS customer_name, b.vehicle_type, b.pickup_location, "
                     + "b.dropoff_location, b.fare, b.status, b.booking_time, "
                     + "d.id AS driver_id, d.name AS driver_name, "
                     + "v.id AS vehicle_id, v.plate_number "
                     + "FROM bookings b "
                     + "INNER JOIN customers c ON b.customer_id = c.id "
                     + "LEFT JOIN drivers d ON b.driver_id = d.id " // Left join to include bookings without a driver assigned
                     + "LEFT JOIN vehicles v ON b.vehicle_id = v.id " // Left join to include bookings without a vehicle assigned
                     + "ORDER BY b.booking_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer driverId = rs.getInt("driver_id") > 0 ? rs.getInt("driver_id") : null;
                Integer vehicleId = rs.getInt("vehicle_id") > 0 ? rs.getInt("vehicle_id") : null;

                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getString("vehicle_type"),
                    rs.getString("pickup_location"),
                    rs.getString("dropoff_location"),   
                    rs.getDouble("fare"),
                    rs.getString("status"),
                    rs.getTimestamp("booking_time"),
                    rs.getString("customer_name"), // Set customer name
                    driverId, // Set driver id (nullable)
                    rs.getString("driver_name"), // Set driver name (nullable)
                    vehicleId, // Set vehicle id (nullable)
                    rs.getString("plate_number") // Set vehicle plate number (nullable)
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Assign a driver and vehicle to a booking
    public boolean assignDriverAndVehicle(int bookingId, Integer driverId, Integer vehicleId) {
        // Ensure both driverId and vehicleId are not null before proceeding
        if (driverId == null || vehicleId == null) {
            return false;
        }

        String query = "UPDATE bookings SET driver_id = ?, vehicle_id = ?, status = 'confirmed' WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, driverId);
            stmt.setInt(2, vehicleId);
            stmt.setInt(3, bookingId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancel a booking if no drivers are available
    public boolean cancelBooking(int bookingId) {
        String query = "UPDATE bookings SET status = 'cancelled' WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
