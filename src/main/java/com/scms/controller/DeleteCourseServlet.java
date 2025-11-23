package com.scms.controller;

import com.scms.dao.CourseDAO;
import org.bson.types.ObjectId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/deleteCourse")
public class DeleteCourseServlet extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            courseDAO.deleteCourse(new ObjectId(id));
        }

        resp.sendRedirect("dashboard.jsp");
    }
}
