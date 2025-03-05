package dao;

import DatabaseConnection.DatabaseConnection;
import static DatabaseConnection.DatabaseConnection.getConnection;
import models.Customer;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());

    // Authenticate Customer Login
    public Customer login(String username, String password) throws SQLException {
        String query = "SELECT * FROM customers WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("nic"),
                    rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during customer login", e);
            throw e;
        }
        return null; // Return null if no matching customer is found
    }

    // Register a New Customer
    public boolean register(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, email, username, password, address, nic, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());
            stmt.setString(7, customer.getPhone());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during customer registration", e);
            throw e;
        }
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql;
        if (customer.getPassword() == null) {
            sql = "UPDATE customers SET name=?, email=?, username=?, address=?, nic=?, phone=? WHERE id=?";
        } else {
            sql = "UPDATE customers SET name=?, email=?, username=?, password=?, address=?, nic=?, phone=? WHERE id=?";
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getUsername());

            if (customer.getPassword() == null) {
                stmt.setString(4, customer.getAddress());
                stmt.setString(5, customer.getNic());
                stmt.setString(6, customer.getPhone());
                stmt.setInt(7, customer.getId());
            } else {
                stmt.setString(4, customer.getPassword());
                stmt.setString(5, customer.getAddress());
                stmt.setString(6, customer.getNic());
                stmt.setString(7, customer.getPhone());
                stmt.setInt(8, customer.getId());
            }

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete Customer by ID
    public boolean deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
            throw e;
        }
    }

    // Get All Customers
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("nic"),
                    rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all customers", e);
            throw e;
        }
        return customers;
    }
}
