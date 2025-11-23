package com.scms.dao;

import com.scms.config.MongoConfig;
import com.scms.model.Course;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

/*
 Handles all database operations related to courses.
*/
public class CourseDAO {

    private MongoCollection<Document> courseCollection;

    public CourseDAO() {
        MongoDatabase db = MongoConfig.getDatabase();
        courseCollection = db.getCollection("courses");
    }

    // Create a new course
    public void createCourse(Course course) {
        Document doc = new Document();
        doc.append("courseName", course.getCourseName());
        doc.append("assignedTeacher", course.getAssignedTeacher());
        courseCollection.insertOne(doc);
        course.setId(doc.getObjectId("_id"));
    }

    // Delete a course by ID
    public void deleteCourse(ObjectId id) {
        courseCollection.deleteOne(eq("_id", id));
    }

    // List all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        for (Document doc : courseCollection.find()) {
            Course c = new Course();
            c.setId(doc.getObjectId("_id"));
            c.setCourseName(doc.getString("courseName"));
            c.setAssignedTeacher(doc.getString("assignedTeacher"));
            courses.add(c);
        }
        return courses;
    }
}
