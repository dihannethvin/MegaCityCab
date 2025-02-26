package servlets;

import dao.DriverDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delete-driver")
public class DeleteDriverServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        DriverDAO driverDAO = new DriverDAO();

        try {
            if (driverDAO.deleteDriver(id)) {
                response.sendRedirect("manage-drivers.jsp?success=Driver deleted successfully!");
            } else {
                response.sendRedirect("manage-drivers.jsp?error=Failed to delete driver.");
            }
        } catch (SQLException e) {
            response.sendRedirect("manage-drivers.jsp?error=Database error.");
        }
    }
}
