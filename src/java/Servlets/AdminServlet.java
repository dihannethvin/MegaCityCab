package Servlets;

import Classes.AdminDAO;
import Classes.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (AdminDAO.addAdmin(username, password)) {
                response.sendRedirect("manage-admins.jsp?message=Admin added successfully");
            } else {
                response.sendRedirect("manage-admins.jsp?error=Failed to add admin");
            }

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            if (AdminDAO.deleteAdmin(id)) {
                response.sendRedirect("manage-admins.jsp?message=Admin deleted successfully");
            } else {
                response.sendRedirect("manage-admins.jsp?error=Failed to delete admin");
            }

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (AdminDAO.updateAdmin(id, username, password)) {
                response.sendRedirect("manage-admins.jsp?message=Admin updated successfully");
            } else {
                response.sendRedirect("manage-admins.jsp?error=Failed to update admin");
            }
        }
    }
}
