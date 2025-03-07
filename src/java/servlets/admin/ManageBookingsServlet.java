package servlets.admin;

import dao.BookingDAO;
import dao.DriverDAO;
import dao.VehicleDAO;
import models.Booking;
import models.Driver;
import models.Vehicle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-bookings")
public class ManageBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookingDAO bookingDAO = new BookingDAO();
    private final DriverDAO driverDAO = new DriverDAO();
    private final VehicleDAO vehicleDAO = new VehicleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch all bookings
        List<Booking> bookings = bookingDAO.getAllBookings();
        request.setAttribute("bookings", bookings);

        // Fetch available drivers
        List<Driver> availableDrivers = driverDAO.getAvailableDrivers();
        request.setAttribute("availableDrivers", availableDrivers);

        // Fetch available vehicles
        List<Vehicle> availableVehicles = vehicleDAO.getAvailableVehicles();
        request.setAttribute("availableVehicles", availableVehicles);

        // Forward to the JSP page
        request.getRequestDispatcher("/admin/manage-bookings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("booking_id"));
            String action = request.getParameter("action");

            if ("confirm".equals(action)) {
                int driverId = Integer.parseInt(request.getParameter("driver_id"));
                int vehicleId = Integer.parseInt(request.getParameter("vehicle_id"));

                // Ensure driver and vehicle are valid selections
                if (driverId > 0 && vehicleId > 0) {
                    boolean success = bookingDAO.assignDriverAndVehicle(bookingId, driverId, vehicleId);

                    if (success) {
                        response.sendRedirect("admin/manage-bookings.jsp?success=Booking confirmed successfully.");
                    } else {
                        response.sendRedirect("admin/manage-bookings.jsp?error=Failed to confirm booking.");
                    }
                } else {
                    response.sendRedirect("admin/manage-bookings.jsp?error=Invalid driver or vehicle selection.");
                }
            } else if ("cancel".equals(action)) {
                boolean success = bookingDAO.cancelBooking(bookingId);

                if (success) {
                    response.sendRedirect("admin/manage-bookings.jsp?success=Booking canceled successfully.");
                } else {
                    response.sendRedirect("admin/manage-bookings.jsp?error=Failed to cancel booking.");
                }
            } else {
                response.sendRedirect("admin/manage-bookings.jsp?error=Invalid action.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("admin/manage-bookings.jsp?error=Invalid input data.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin/manage-bookings.jsp?error=An unexpected error occurred.");
        }
    }
}
