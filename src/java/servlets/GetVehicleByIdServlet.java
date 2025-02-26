package servlets;

import dao.VehicleDAO;
import models.Vehicle;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getVehicle")
public class GetVehicleByIdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        VehicleDAO vehicleDAO = new VehicleDAO();
        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);
        if (vehicle != null) {
            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("vehicle-details.jsp").forward(request, response); // Redirect to a JSP page to display the vehicle
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
        }
    }
}
