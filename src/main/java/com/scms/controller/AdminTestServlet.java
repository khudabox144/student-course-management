package com.scms.controller;

import com.scms.dao.CourseDAO;
import com.scms.model.Course;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/testcourse")
public class AdminTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");

        CourseDAO courseDAO = new CourseDAO();

        Course course = new Course("Mathematics", "teacher01");
        courseDAO.createCourse(course);

        resp.getWriter().println("Course added: " + course.getCourseName() + ", Assigned Teacher: " + course.getAssignedTeacher());
    }
}
