package com.scms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.client.MongoDatabase;
import com.scms.config.MongoConfig;

/*
 This servlet tests whether our project can successfully connect to MongoDB.
*/
@WebServlet("/testdb")
public class TestDbServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");

        try {
            MongoDatabase db = MongoConfig.getDatabase();
            // List collections to confirm connection
            String firstCollection = db.listCollectionNames().first();

            if (firstCollection == null) {
                resp.getWriter().println("Connected to MongoDB. No collections yet.");
            } else {
                resp.getWriter().println("Connected to MongoDB. First collection: " + firstCollection);
            }

        } catch (Exception e) {
            resp.getWriter().println("MongoDB connection failed: " + e.getMessage());
            e.printStackTrace(resp.getWriter());
        }
    }
}
