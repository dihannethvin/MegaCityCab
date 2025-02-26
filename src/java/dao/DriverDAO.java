package dao;

import DatabaseConnection.DatabaseConnection;
import models.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    // Retrieve all drivers
    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                drivers.add(new Driver(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("vehicle_type"),
                        rs.getString("license_number"),
                        rs.getInt("vehicle_id")  // Correct field name
                ));
            }
        }
        return drivers;
    }

    // Add a new driver
    public boolean addDriver(Driver driver) throws SQLException {
        String query = "INSERT INTO drivers (name, phone, gender, vehicle_type, license_number, vehicle_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getPhone());
            stmt.setString(3, driver.getGender());
            stmt.setString(4, driver.getVehicleType());
            stmt.setString(5, driver.getLicenseNumber());
            stmt.setInt(6, driver.getVehicleId());  // Handle vehicle_id as an int

            return stmt.executeUpdate() > 0;
        }
    }

    // Update driver details
    public boolean updateDriver(Driver driver) throws SQLException {
        String query = "UPDATE drivers SET name=?, phone=?, gender=?, vehicle_type=?, license_number=?, vehicle_id=? WHERE id=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getPhone());
            stmt.setString(3, driver.getGender());
            stmt.setString(4, driver.getVehicleType());
            stmt.setString(5, driver.getLicenseNumber());
            stmt.setInt(6, driver.getVehicleId());  // Handle vehicle_id as an int
            stmt.setInt(7, driver.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete a driver
    public boolean deleteDriver(int id) throws SQLException {
        String query = "DELETE FROM drivers WHERE id=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
