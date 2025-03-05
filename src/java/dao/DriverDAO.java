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
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM drivers";

        try {
            // Establish connection
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // Process each result
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getInt("id"));
                driver.setName(rs.getString("name"));
                driver.setPhone(rs.getString("phone"));
                driver.setGender(rs.getString("gender"));
                driver.setVehicleType(rs.getString("vehicle_type"));
                driver.setLicenseNumber(rs.getString("license_number"));
                driver.setVehicleId(rs.getInt("vehicle_id"));
                drivers.add(driver);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return drivers;
    }


    // Retrieve a single driver by ID
    public Driver getDriverById(int id) throws SQLException {
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
                        rs.getString("license_number"),
                        rs.getInt("vehicle_id")
                );
            }
        }
        return null;
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
            stmt.setInt(6, driver.getVehicleId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Update driver details with proper error handling
    public boolean updateDriver(Driver driver) throws SQLException {
        String query = "UPDATE drivers SET name=?, phone=?, gender=?, vehicle_type=?, license_number=?, vehicle_id=? WHERE id=?";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DatabaseConnection.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setString(1, driver.getName());
            stmt.setString(2, driver.getPhone());
            stmt.setString(3, driver.getGender());
            stmt.setString(4, driver.getVehicleType());
            stmt.setString(5, driver.getLicenseNumber());
            stmt.setInt(6, driver.getVehicleId());
            stmt.setInt(7, driver.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
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
