package servlets;

import dao.VehicleDAO;
import models.Vehicle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getAllVehicles")
public class GetAllVehiclesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VehicleDAO vehicleDAO = new VehicleDAO();
        try {
            List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
            request.setAttribute("vehicles", vehicles);
            request.getRequestDispatcher("vehicles-list.jsp").forward(request, response); // Redirect to a JSP page to display the vehicles
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving vehicles");
        }
    }
}
