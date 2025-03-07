package servlets.admin;

import dao.VehicleDAO;
import models.Vehicle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
                int vehicleId = Integer.parseInt(request.getParameter("id"));
                Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
                request.setAttribute("vehicle", vehicle);
                request.getRequestDispatcher("/admin/manage-vehicles.jsp").forward(request, response);
            } else {
                fetchVehicles(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid vehicle ID format", e);
            response.sendRedirect("/error.jsp");
        }
    }

    private void fetchVehicles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vehicle> vehicleList = vehicleDAO.getAllVehicles();
        request.setAttribute("vehicleList", vehicleList);
        request.getRequestDispatcher("/admin/manage-vehicles.jsp").forward(request, response);
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
            fetchVehicles(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            response.sendRedirect("/error.jsp");
        }
    }

    // Add a new vehicle
    private void addVehicle(HttpServletRequest request) {
        String plateNumber = request.getParameter("plate_number");
        String vehicleType = request.getParameter("vehicle_type");

        Vehicle newVehicle = new Vehicle(0, plateNumber, vehicleType);
        if (vehicleDAO.addVehicle(newVehicle)) {
            LOGGER.info("Vehicle added successfully!");
        } else {
            LOGGER.warning("Failed to insert vehicle.");
        }
    }

    // Update an existing vehicle
    private void updateVehicle(HttpServletRequest request) {
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
            if (vehicleDAO.updateVehicle(updatedVehicle)) {
                LOGGER.info("Vehicle updated successfully!");
            } else {
                LOGGER.warning("Failed to update vehicle.");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid vehicle ID format", e);
        }
    }

    // Delete a vehicle
    private void deleteVehicle(HttpServletRequest request) {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("id"));
            if (vehicleDAO.deleteVehicle(vehicleId)) {
                LOGGER.info("Vehicle deleted successfully!");
            } else {
                LOGGER.warning("Failed to delete vehicle.");
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid vehicle ID format", e);
        }
    }
}
