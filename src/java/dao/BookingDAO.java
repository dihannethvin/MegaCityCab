package dao;

import DatabaseConnection.DatabaseConnection;
import models.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    // Method to retrieve all bookings, including driver details (For Admins)
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.id, b.customer_id, c.name AS customer_name, b.vehicle_type, b.pickup_location, "
                     + "b.dropoff_location, b.distance, b.fare, b.status, b.booking_time, "
                     + "d.id AS driver_id, d.name AS driver_name "
                     + "FROM bookings b "
                     + "INNER JOIN customers c ON b.customer_id = c.id "
                     + "LEFT JOIN drivers d ON b.driver_id = d.id "  // Left join to handle cases without driver assignment
                     + "ORDER BY b.booking_time DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking booking = new Booking(
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
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Method to retrieve bookings for a specific customer (For Customer Side)
    public List<Booking> getBookingsForCustomer(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT b.id, b.vehicle_type, b.pickup_location, b.dropoff_location, "
                     + "b.distance, b.fare, b.status, b.booking_time, "
                     + "d.id AS driver_id, d.name AS driver_name "
                     + "FROM bookings b "
                     + "LEFT JOIN drivers d ON b.driver_id = d.id "
                     + "WHERE b.customer_id = ? "
                     + "ORDER BY b.booking_time DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                        rs.getInt("id"),
                        customerId,
                        rs.getString("vehicle_type"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location"),
                        rs.getDouble("distance"),
                        rs.getDouble("fare"),
                        rs.getString("status"),
                        rs.getTimestamp("booking_time"),
                        null, // Customer name not required for this query
                        rs.getInt("driver_id")  // Set driver ID (can be null)
                    );
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Method to add a new booking (For Customer Side)
    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (customer_id, vehicle_type, pickup_location, dropoff_location, "
                     + "distance, fare, status, booking_time, driver_id) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setString(2, booking.getVehicleType());
            stmt.setString(3, booking.getPickupLocation());
            stmt.setString(4, booking.getDropoffLocation());
            stmt.setDouble(5, booking.getDistance());
            stmt.setDouble(6, booking.getFare());
            stmt.setString(7, booking.getStatus());
            stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(9, booking.getDriverId());  // Set driver ID (assuming driver is assigned)
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update an existing booking (For Admin Side, including driver assignment)
    public boolean updateBooking(Booking booking) {
        String query = "UPDATE bookings SET pickup_location = ?, dropoff_location = ?, status = ?, "
                     + "vehicle_type = ?, distance = ?, fare = ?, driver_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, booking.getPickupLocation());
            stmt.setString(2, booking.getDropoffLocation());
            stmt.setString(3, booking.getStatus());
            stmt.setString(4, booking.getVehicleType());
            stmt.setDouble(5, booking.getDistance());
            stmt.setDouble(6, booking.getFare());
            stmt.setInt(7, booking.getDriverId());  // Update driver ID (admin assigns)
            stmt.setInt(8, booking.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete a booking (For Admin Side)
    public boolean deleteBooking(int bookingId) {
        String query = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}