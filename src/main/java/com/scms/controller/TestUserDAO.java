package com.scms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scms.dao.UserDAO;
import com.scms.model.User;

/*
 This servlet tests UserDAO functionality
*/
@WebServlet("/testuser")
public class TestUserDAO extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");
        UserDAO userDAO = new UserDAO();

        // Create a test user
        User user = new User("sakib01", "12345", "student", "Sakib Biswas");
        userDAO.createUser(user);

        // Find the user
        User found = userDAO.findByUsername("sakib01");

        if (found != null) {
            resp.getWriter().println("User found: " + found.getFullName() + ", Role: " + found.getRole());
        } else {
            resp.getWriter().println("User not found");
        }
    }
}
