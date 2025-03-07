package dao;

import DatabaseConnection.DatabaseConnection;
import models.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    // Retrieve all vehicles
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("plate_number"),
                        rs.getString("vehicle_type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    // Retrieve a single vehicle by ID
    public Vehicle getVehicleById(int id) {
        String query = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("id"),
                        rs.getString("plate_number"),
                        rs.getString("vehicle_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all available vehicles (vehicles not assigned to any ongoing booking)
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE id NOT IN (SELECT vehicle_id FROM bookings WHERE status = 'confirmed')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                    rs.getInt("id"),
                    rs.getString("plate_number"),
                    rs.getString("vehicle_type")
                );
                availableVehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableVehicles;
    }

    // Add a new vehicle
    public boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (plate_number, vehicle_type) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, vehicle.getPlateNumber());
            stmt.setString(2, vehicle.getVehicleType());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing vehicle
    public boolean updateVehicle(Vehicle vehicle) {
        String query = "UPDATE vehicles SET plate_number = ?, vehicle_type = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, vehicle.getPlateNumber());
            stmt.setString(2, vehicle.getVehicleType());
            stmt.setInt(3, vehicle.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a vehicle
    public boolean deleteVehicle(int id) {
        String query = "DELETE FROM vehicles WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
