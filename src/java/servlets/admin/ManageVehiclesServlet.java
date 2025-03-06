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
        try {
            List<Vehicle> vehicleList = vehicleDAO.getAllVehicles();
            if (vehicleList != null && !vehicleList.isEmpty()) {
                request.setAttribute("vehicleList", vehicleList);
            } else {
                request.setAttribute("vehicleList", null); // Handle case where no vehicles exist
            }
            request.getRequestDispatcher("manage-vehicles.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching vehicle list", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error while retrieving vehicles.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.getWriter().write("Invalid action.");
            return;
        }

        try {
            switch (action) {
                case "add":
                    addVehicle(request, response);
                    break;
                case "update":
                    updateVehicle(request, response);
                    break;
                case "delete":
                    deleteVehicle(request, response);
                    break;
                default:
                    response.getWriter().write("Invalid action.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error in processing request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error while processing request.");
        }
    }

    // Add Vehicle
    private void addVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        String plateNumber = request.getParameter("plate_number");
        String vehicleType = request.getParameter("vehicle_type");

        Vehicle vehicle = new Vehicle(0, plateNumber, vehicleType);
        try {
            boolean isAdded = vehicleDAO.addVehicle(vehicle);
            if (isAdded) {
                response.sendRedirect("manage-vehicles.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding vehicle.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding vehicle", e);
            throw e;
        }
    }

    // Update Vehicle
    private void updateVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String plateNumber = request.getParameter("plate_number");
        String vehicleType = request.getParameter("vehicle_type");

        Vehicle vehicle = new Vehicle(id, plateNumber, vehicleType);
        try {
            boolean isUpdated = vehicleDAO.updateVehicle(vehicle);
            if (isUpdated) {
                response.sendRedirect("manage-vehicles.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating vehicle.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating vehicle", e);
            throw e;
        }
    }

    // Delete Vehicle
    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            if (vehicleDAO.deleteVehicle(id)) {
                response.sendRedirect("manage-vehicles.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting vehicle.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting vehicle", e);
            throw e;
        }
    }
}
