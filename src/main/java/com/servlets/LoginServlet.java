package com.servlets;

import com.beans.User;
import com.enums.Role;
import com.google.gson.Gson;
import com.requests.LoginRequest;
import com.responses.ExceptionResponse;
import com.responses.Response;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static com.beans.Constants.ERROR_RESPONSE_NO_USER;
import static com.beans.Constants.MESSAGE_USER_LOGGED;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private Map<String, String> users;
    private Role role;

    public void init() {
        users = new HashMap<String, String>() {{
            put("test", "test");
            put("user", "password");
            put("user2", "1234");
        }};
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        HttpSession session = request.getSession();

        try {
            LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);
            String username = loginRequest.getUsername();
            boolean isLoggable =  ("admin".equals(username)) ? isAdmin(loginRequest) : isUser(loginRequest);
            if (isLoggable) {
                Cookie cookie = codeUser(session, username, loginRequest.getPassword());
                Response res = new Response();
                res.setStatus(200);
                res.setMessage(MESSAGE_USER_LOGGED(username));
                response.addCookie(cookie);

                response.setStatus(200);
                gson.toJson(res, response.getWriter());
            } else {
                Response res = new Response();
                res.setStatus(400);
                response.setStatus(400);
                res.setMessage(ERROR_RESPONSE_NO_USER);
                gson.toJson(res, response.getWriter());
            }

        } catch (Exception ex) {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);
            gson.toJson(exResponse, response.getWriter());
        }
    }
    private String getBase64FromString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    private boolean isUser(LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        role = Role.USER;
        return users.containsKey(username) && users.get(username).equals(password);
    }

    private boolean isAdmin(LoginRequest loginRequest){
        String password = loginRequest.getPassword();
        role = Role.ADMIN;
        return "admin".equals(password);
    }

    private Cookie codeUser(HttpSession session, String username, String password) {
        User user = new User(username, password, role);
        session.setAttribute("loggedUser", user);
        String userId = getBase64FromString(username);
        return new Cookie("userId", userId);
    }

}