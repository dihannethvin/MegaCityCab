package servlets.admin;

import dao.DriverDAO;
import models.Driver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ManageDriversServlet")
public class ManageDriversServlet extends HttpServlet {
    private final DriverDAO driverDAO = new DriverDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                // Retrieve the driver ID from the request to fetch the specific driver
                int driverId = Integer.parseInt(request.getParameter("id"));
                Driver driver = driverDAO.getDriverById(driverId);
                request.setAttribute("driver", driver);
                // Forward to the update driver page
                request.getRequestDispatcher("../admin/update-driver.jsp").forward(request, response);
            } else {
                // If action is not "edit", fetch and display all drivers
                fetchDrivers(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // Redirect to error page if an exception occurs
        }
    }

    // Helper method to fetch the list of all drivers
    private void fetchDrivers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Driver> driverList = DriverDAO.getAllDrivers();  // Fetch drivers from the DAO
        request.setAttribute("driverList", driverList);  // Set the list as an attribute for the JSP
        // Forward to the manage-drivers.jsp to display the drivers
        request.getRequestDispatcher("/admin/manage-drivers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            // Handle different actions: adding, updating, and deleting drivers
            if ("add".equals(action)) {
                addDriver(request);
            } else if ("update".equals(action)) {
                updateDriver(request);
            } else if ("delete".equals(action)) {
                deleteDriver(request);
            }
            // After performing the action, redirect to the driver list page
            response.sendRedirect("ManageDriversServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // Redirect to error page if an exception occurs
        }
    }

    // Helper method to add a new driver
    private void addDriver(HttpServletRequest request) throws SQLException {
        Driver newDriver = new Driver(
                0, // ID is set to 0 to let the database auto-generate it
                request.getParameter("name"),
                request.getParameter("phone"),
                request.getParameter("gender"),
                request.getParameter("vehicle_type"),
                request.getParameter("license_number"),
                Integer.parseInt(request.getParameter("vehicle_id"))
        );
        driverDAO.addDriver(newDriver);  // Add the new driver using the DAO
    }

    // Helper method to update an existing driver
    private void updateDriver(HttpServletRequest request) throws SQLException {
        try {
            int driverId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String vehicleType = request.getParameter("vehicle_type");
            String licenseNumber = request.getParameter("license_number");
            int vehicleId = Integer.parseInt(request.getParameter("vehicle_id"));

            // Check if driver ID is valid
            Driver existingDriver = driverDAO.getDriverById(driverId);
            if (existingDriver == null) {
                System.out.println("Driver with ID " + driverId + " not found.");
                return; // Stop execution if driver does not exist
            }

            // Create updated driver object
            Driver updatedDriver = new Driver(driverId, name, phone, gender, vehicleType, licenseNumber, vehicleId);

            // Update the driver in the database
            boolean success = driverDAO.updateDriver(updatedDriver);
            if (!success) {
                System.out.println("Failed to update driver with ID " + driverId);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }


    // Helper method to delete a driver
    private void deleteDriver(HttpServletRequest request) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));  // Retrieve the driver ID to delete
        driverDAO.deleteDriver(id);  // Delete the driver using the DAO
    }
}