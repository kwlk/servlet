package com.servlet;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RequestDispatcher rs = request.getRequestDispatcher("login.html");
        rs.include(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        System.out.println(username);
        RequestDispatcher rs;
        if ("admin".equals(username)) {
            rs = request.getRequestDispatcher("admin");
        } else {
            rs = request.getRequestDispatcher("user");
        }
        rs.forward(request, response);
    }
}
