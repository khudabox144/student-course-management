package com.scms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scms.dao.CourseDAO;
import com.scms.dao.UserDAO;
import com.scms.model.Course;
import com.scms.model.User;

@WebServlet("/admin/addCourse")
public class AddCourseServlet extends HttpServlet {

    private CourseDAO courseDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
        userDAO = new UserDAO();
    }

    // Load teachers list
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<User> teachers = userDAO.getAllTeachers();
        req.setAttribute("teachers", teachers);

        req.getRequestDispatcher("/admin/AddCourse.jsp").forward(req, resp);
    }

    // Handle form submission
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String courseName = req.getParameter("courseName");
        String teacherUsername = req.getParameter("teacherUsername");

        Course course = new Course(courseName, teacherUsername);
        courseDAO.createCourse(course);

        resp.sendRedirect("dashboard.jsp");
    }
}
