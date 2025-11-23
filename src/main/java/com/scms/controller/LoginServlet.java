package com.scms.controller;

import com.scms.dao.UserDAO;
import com.scms.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/*
 Handles login POST requests and redirects based on role.
*/
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // initialize DAO
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // show login page
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userDAO.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Login success
            HttpSession session = req.getSession();
            session.setAttribute("user", user); // store user in session

            // Role-based redirect
            switch (user.getRole()) {
                case "student":
                    resp.sendRedirect("student/dashboard");
                    break;
                case "teacher":
                    resp.sendRedirect("teacher/dashboard");
                    break;
                case "admin":
                    resp.sendRedirect("admin/dashboard.jsp");
                    break;
                default:
                    resp.sendRedirect("login");
            }

        } else {
            // Login failed
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
