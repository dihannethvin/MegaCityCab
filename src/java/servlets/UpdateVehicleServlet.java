package servlets;

import dao.VehicleDAO;
import models.Vehicle;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateVehicle")
public class UpdateVehicleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        VehicleDAO vehicleDAO = new VehicleDAO();
        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
        if (vehicle != null) {
            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("update-vehicle.jsp").forward(request, response); // Display form to update vehicle details
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        String plateNumber = request.getParameter("plateNumber");
        String vehicleType = request.getParameter("vehicleType");

        Vehicle vehicle = new Vehicle(vehicleId, plateNumber, vehicleType);
        VehicleDAO vehicleDAO = new VehicleDAO();
        try {
            if (vehicleDAO.updateVehicle(vehicle)) {
                response.sendRedirect("getAllVehicles"); // Redirect to the page showing all vehicles
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating vehicle");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating vehicle");
        }
    }
}
