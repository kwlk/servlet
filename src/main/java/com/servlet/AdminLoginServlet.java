package com.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "adminLoginServlet", value = "/admin")
public class AdminLoginServlet extends HttpServlet {
    private  String login;
    private  String password;

    public void init() {
        login = "admin";
        password = "admin";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String pass = request.getParameter("pwd");

        RequestDispatcher rs;
        if(login.equals(username) && password.equals(pass)){
            getServletContext().setAttribute("loggedUser", username);
            rs = request.getRequestDispatcher("adminDashboard");
        } else {
            rs = request.getRequestDispatcher("loginFailed.html");
        }
        rs.forward(request, response);
    }
}
