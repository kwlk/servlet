package com.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "userLoginServlet", value = "/user")
public class UserLoginServlet extends HttpServlet {
    private Map<String, String> users;

    public void init() {
        users = new HashMap<String, String>() {{
            put("test", "test");
            put("user", "password");
            put("user2", "1234");
        }};
    }
    private boolean checkUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String pass = request.getParameter("pwd");

        RequestDispatcher rs;
        if(checkUser(username, pass)){
            getServletContext().setAttribute("loggedUser", username);
            rs = request.getRequestDispatcher("dashboard");
        } else {
            rs = request.getRequestDispatcher("loginFailed.html");
        }
        rs.forward(request, response);
    }
}
