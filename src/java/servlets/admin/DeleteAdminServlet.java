package servlets.admin;

import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-admin")
public class DeleteAdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        AdminDAO adminDAO = new AdminDAO();

        try {
            if (adminDAO.deleteAdmin(id)) {
                response.sendRedirect("manage-admins");
            } else {
                response.getWriter().write("Error deleting admin.");
            }
        } catch (SQLException e) {
        }
    }
}
