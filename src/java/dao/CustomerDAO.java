package dao;

import DatabaseConnection.DatabaseConnection;
import models.Customer;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO {
    private static final Logger LOGGER = Logger.getLogger(CustomerDAO.class.getName());

    // Authenticate Customer Login (for both Admin and User Login)
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
             
            connection.setAutoCommit(true); // Ensure auto-commit is enabled

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());
            stmt.setString(7, customer.getPhone());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                LOGGER.log(Level.INFO, "Customer registered successfully: {0}", customer.getUsername());
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Customer registration failed for: {0}", customer.getUsername());
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during customer registration", e);
            throw e; // Rethrow the exception to be handled at a higher level
        }
    }

    // Update Customer Details
    public boolean updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET name=?, email=?, username=?, password=?, address=?, nic=?, phone=? WHERE id=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getNic());
            stmt.setString(7, customer.getPhone());
            stmt.setInt(8, customer.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                LOGGER.log(Level.INFO, "Customer updated successfully: {0}", customer.getUsername());
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No records updated for customer: {0}", customer.getUsername());
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating customer", e);
            throw e;
        }
    }

    // Delete Customer by ID
    public boolean deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                LOGGER.log(Level.INFO, "Customer deleted successfully with ID: {0}", id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No customer found with ID: {0}", id);
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting customer", e);
            throw e;
        }
    }
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Using the constructor with 'id' to load customer data
                Customer customer = new Customer(
                    rs.getInt("id"),          // id from database
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("nic"),
                    rs.getString("phone")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all customers", e);
            throw e;
        }
        return customers;
    }

}
