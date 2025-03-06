package servlets.admin;

import dao.VehicleDAO;
import models.Vehicle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/manage-vehicles")
public class ManageVehiclesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private static final Logger LOGGER = Logger.getLogger(ManageVehiclesServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                // Fetch specific vehicle details for editing
                int vehicleId = Integer.parseInt(request.getParameter("id"));
                Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
                request.setAttribute("vehicle", vehicle);
                request.getRequestDispatcher("/admin/manage-vehicles.jsp").forward(request, response);
            } else {
                // Fetch all vehicles for display
                fetchVehicles(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid vehicle ID format", e);
            response.sendRedirect("/error.jsp");
        }
    }

    private void fetchVehicles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Vehicle> vehicleList = vehicleDAO.getAllVehicles();
            request.setAttribute("vehicleList", vehicleList);
            request.getRequestDispatcher("/admin/manage-vehicles.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching vehicle list", e);
            response.sendRedirect("/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addVehicle(request);
            } else if ("update".equals(action)) {
                updateVehicle(request);
            } else if ("delete".equals(action)) {
                deleteVehicle(request);
            }

            // Refresh the vehicle list after action
            fetchVehicles(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error processing request", e);
            response.sendRedirect("/error.jsp");
        }
    }

    // Add Vehicle
    private void addVehicle(HttpServletRequest request) throws SQLException {
        String plateNumber = request.getParameter("plate_number");
        String vehicleType = request.getParameter("vehicle_type");

        Vehicle newVehicle = new Vehicle(0, plateNumber, vehicleType);
        boolean success = vehicleDAO.addVehicle(newVehicle);

        if (!success) {
            LOGGER.warning("Failed to insert vehicle into database.");
        } else {
            LOGGER.info("Vehicle added successfully!");
        }
    }

    // Update Vehicle
    private void updateVehicle(HttpServletRequest request) throws SQLException {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("id"));
            String plateNumber = request.getParameter("plate_number");
            String vehicleType = request.getParameter("vehicle_type");

            Vehicle existingVehicle = vehicleDAO.getVehicleById(vehicleId);
            if (existingVehicle == null) {
                LOGGER.warning("Vehicle with ID " + vehicleId + " not found.");
                return;
            }

            Vehicle updatedVehicle = new Vehicle(vehicleId, plateNumber, vehicleType);
            boolean success = vehicleDAO.updateVehicle(updatedVehicle);

            if (!success) {
                LOGGER.warning("Failed to update vehicle with ID " + vehicleId);
            } else {
                LOGGER.info("Vehicle updated successfully!");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid vehicle ID format", e);
        }
    }

    // Delete Vehicle
    private void deleteVehicle(HttpServletRequest request) throws SQLException {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("id"));
            boolean success = vehicleDAO.deleteVehicle(vehicleId);

            if (!success) {
                LOGGER.warning("Failed to delete vehicle with ID " + vehicleId);
            } else {
                LOGGER.info("Vehicle deleted successfully!");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid vehicle ID format", e);
        }
    }
}
