package dao;

import DatabaseConnection.DatabaseConnection;
import models.Driver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {
    // Retrieve all drivers
    public static List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("vehicle_type"),
                        rs.getString("license_number")
                );
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    // Retrieve a single driver by ID
    public Driver getDriverById(int id) {
        String query = "SELECT * FROM drivers WHERE id=?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Driver(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("vehicle_type"),
                        rs.getString("license_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a new driver
    public boolean addDriver(Driver driver) {
        String query = "INSERT INTO drivers (name, phone, gender, vehicle_type, license_number) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getPhone());
            stmt.setString(3, driver.getGender());
            stmt.setString(4, driver.getVehicleType());
            stmt.setString(5, driver.getLicenseNumber());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update driver details
    public boolean updateDriver(Driver driver) {
        String query = "UPDATE drivers SET name=?, phone=?, gender=?, vehicle_type=?, license_number=? WHERE id=?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getPhone());
            stmt.setString(3, driver.getGender());
            stmt.setString(4, driver.getVehicleType());
            stmt.setString(5, driver.getLicenseNumber());
            stmt.setInt(6, driver.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a driver
    public boolean deleteDriver(int id) {
        String query = "DELETE FROM drivers WHERE id=?";
        
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