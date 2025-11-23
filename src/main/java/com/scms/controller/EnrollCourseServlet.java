package com.scms.controller;

import com.scms.dao.EnrollmentDAO;
import com.scms.model.Enrollment;
import com.scms.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import org.bson.types.ObjectId;

@WebServlet("/student/enrollCourse")
public class EnrollCourseServlet extends HttpServlet {

    private EnrollmentDAO enrollmentDAO;

    @Override
    public void init() throws ServletException {
        enrollmentDAO = new EnrollmentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if(user == null || !"student".equals(user.getRole())){
            resp.sendRedirect("../login");
            return;
        }

        String courseIdStr = req.getParameter("courseId");
        if(courseIdStr != null && !courseIdStr.isEmpty()){
            Enrollment enrollment = new Enrollment(user.getUsername(), new ObjectId(courseIdStr));
            enrollmentDAO.enrollStudent(enrollment);
        }

        resp.sendRedirect("dashboard.jsp");
    }
}
