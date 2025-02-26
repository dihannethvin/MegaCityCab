package servlets;

import dao.VehicleDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteVehicle")
public class DeleteVehicleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
        VehicleDAO vehicleDAO = new VehicleDAO();
        try {
            if (vehicleDAO.deleteVehicle(vehicleId)) {
                response.sendRedirect("getAllVehicles"); // Redirect to the page showing all vehicles
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting vehicle");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting vehicle");
        }
    }
}
