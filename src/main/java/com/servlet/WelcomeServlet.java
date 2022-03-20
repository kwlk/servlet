package com.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "welcomeServlet", value = "")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) getServletContext().getAttribute("loggedUser");
        RequestDispatcher rs;
        if (username == null){
            rs = request.getRequestDispatcher("login");
        } else {
            if ("admin".equals(username)) {
                rs = request.getRequestDispatcher("adminDashboard");
            } else {
                rs = request.getRequestDispatcher("dashboard");
            }
        }
        rs.forward(request, response);
    }
}
