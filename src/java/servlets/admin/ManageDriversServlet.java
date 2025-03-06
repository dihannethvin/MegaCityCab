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
                int driverId = Integer.parseInt(request.getParameter("id"));
                Driver driver = driverDAO.getDriverById(driverId);
                request.setAttribute("driver", driver);
                request.getRequestDispatcher("../admin/manage-drivers.jsp").forward(request, response);
            } else {
                fetchDrivers(request, response);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void fetchDrivers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Driver> driverList = DriverDAO.getAllDrivers();
        request.setAttribute("driverList", driverList);
        request.getRequestDispatcher("../admin/manage-drivers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addDriver(request);
            } else if ("update".equals(action)) {
                updateDriver(request);
            } else if ("delete".equals(action)) {
                deleteDriver(request);
            }

            response.sendRedirect("ManageDriversServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    private void addDriver(HttpServletRequest request) throws SQLException {
        Driver newDriver = new Driver(
                0,
                request.getParameter("name"),
                request.getParameter("phone"),
                request.getParameter("gender"),
                request.getParameter("vehicle_type"),
                request.getParameter("license_number")
        );
        driverDAO.addDriver(newDriver);
    }

    private void updateDriver(HttpServletRequest request) throws SQLException {
        try {
            int driverId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String vehicleType = request.getParameter("vehicle_type");
            String licenseNumber = request.getParameter("license_number");

            Driver existingDriver = driverDAO.getDriverById(driverId);
            if (existingDriver == null) {
                System.out.println("Driver with ID " + driverId + " not found.");
                return;
            }

            Driver updatedDriver = new Driver(driverId, name, phone, gender, vehicleType, licenseNumber);
            boolean success = driverDAO.updateDriver(updatedDriver);

            if (!success) {
                System.out.println("Failed to update driver with ID " + driverId);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private void deleteDriver(HttpServletRequest request) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        driverDAO.deleteDriver(id);
    }
}