package com.scms.controller;

import com.scms.dao.UserDAO;
import com.scms.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/*
 Handles user registration with duplicate username check
*/
@WebServlet("/registerUser")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8"); // prevent encoding issues
        resp.setContentType("text/html;charset=UTF-8");

        // Read form parameters
        String fullName = req.getParameter("fullName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Debugging (optional)
        System.out.println("fullName=" + fullName + ", username=" + username + ", password=" + password + ", role=" + role);

        if(fullName != null && username != null && password != null && role != null &&
           !fullName.isEmpty() && !username.isEmpty() && !password.isEmpty() && !role.isEmpty()) {

            User user = new User(fullName, username, password, role);

            // Attempt to create user
            boolean success = userDAO.createUser(user);
            if(success){
                // Redirect to login page after successful registration
                resp.sendRedirect("login");
            } else {
                // Username already exists
                resp.getWriter().println("<h3>Username '" + username + "' already exists! Please choose another.</h3>");
                resp.getWriter().println("<a href='register.jsp'>Go Back to Register</a>");
            }

        } else {
            resp.getWriter().println("<h3>All fields are required!</h3>");
            resp.getWriter().println("<a href='register.jsp'>Go Back to Register</a>");
        }
    }
}
