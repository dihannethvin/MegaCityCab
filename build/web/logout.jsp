<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    // Destroy the session
    session.invalidate();
    response.sendRedirect("login.jsp");
%>
