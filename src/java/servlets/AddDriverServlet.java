package servlets;

import dao.DriverDAO;
import models.Driver;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add-driver")
public class AddDriverServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String vehicleType = request.getParameter("vehicle_type");
        String licenseNumber = request.getParameter("license_number");
        int carId = Integer.parseInt(request.getParameter("car_id")); // ✅ Convert to int

        Driver driver = new Driver(0, name, phone, gender, vehicleType, licenseNumber, carId);
        DriverDAO driverDAO = new DriverDAO();

        try {
            if (driverDAO.addDriver(driver)) {
                response.sendRedirect("manage-drivers.jsp?success=Driver added successfully!");
            } else {
                response.sendRedirect("manage-drivers.jsp?error=Failed to add driver.");
            }
        } catch (SQLException e) {
            response.sendRedirect("manage-drivers.jsp?error=Database error.");
        }
    }
}
