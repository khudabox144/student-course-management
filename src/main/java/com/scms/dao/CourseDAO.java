package com.scms.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.scms.config.MongoConfig;
import com.scms.model.Course;

/*
 Handles all database operations related to courses.
*/
public class CourseDAO {

    private MongoCollection<Document> courseCollection;

    public CourseDAO() {
        // Initialize lazily on first use
        courseCollection = null;
    }

    private MongoCollection<Document> getCollection() {
        if (courseCollection == null) {
            MongoDatabase db = MongoConfig.getDatabase();
            courseCollection = db.getCollection("courses");
        }
        return courseCollection;
    }

    // Create a new course
    public void createCourse(Course course) {
        Document doc = new Document();
        doc.append("courseName", course.getCourseName());
        doc.append("assignedTeacher", course.getAssignedTeacher());
        getCollection().insertOne(doc);
        course.setId(doc.getObjectId("_id"));
    }

    // Delete a course by ID
    public void deleteCourse(ObjectId id) {
        getCollection().deleteOne(eq("_id", id));
    }

    // List all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            Course c = new Course();
            c.setId(doc.getObjectId("_id"));
            c.setCourseName(doc.getString("courseName"));
            c.setAssignedTeacher(doc.getString("assignedTeacher"));
            courses.add(c);
        }
        return courses;
    }
}
