package dao;

import DatabaseConnection.DatabaseConnection;
import models.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    // Retrieve all vehicles
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("id"), // Using 'id' instead of 'vehicle_id'
                        rs.getString("plate_number"),
                        rs.getString("vehicle_type")
                ));
            }
        }
        return vehicles;
    }

    // Get a vehicle by ID
    public Vehicle getVehicleById(int id) {
        Vehicle vehicle = null;
        String query = "SELECT * FROM vehicles WHERE id = ?"; // Using 'id'
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vehicle = new Vehicle(
                        rs.getInt("id"), // Using 'id'
                        rs.getString("plate_number"),
                        rs.getString("vehicle_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Debugging output
        }
        return vehicle;
    }

    // Add a new vehicle
    public boolean addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicles (plate_number, vehicle_type) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getPlateNumber());
            stmt.setString(2, vehicle.getVehicleType());
            return stmt.executeUpdate() > 0;
        }
    }

    // Update vehicle details
    public boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String query = "UPDATE vehicles SET plate_number=?, vehicle_type=? WHERE id=?"; // Using 'id'
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicle.getPlateNumber());
            stmt.setString(2, vehicle.getVehicleType());
            stmt.setInt(3, vehicle.getId()); // Using 'id'
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete a vehicle
    public boolean deleteVehicle(int vehicleId) throws SQLException {
        String query = "DELETE FROM vehicles WHERE id=?"; // Using 'id'
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            return stmt.executeUpdate() > 0;
        }
    }
}
