package com.scms.controller;

import com.scms.dao.CourseDAO;
import com.scms.model.Course;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/addCourse")
public class AddCourseServlet extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
    }

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
