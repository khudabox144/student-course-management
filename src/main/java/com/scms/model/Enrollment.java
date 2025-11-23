package com.scms.model;

import org.bson.types.ObjectId;

/*
 Represents a student's enrollment in a course
 Fields: id, studentUsername, courseId
*/
public class Enrollment {

    private ObjectId id;
    private String studentUsername;
    private ObjectId courseId;

    public Enrollment() {}

    public Enrollment(String studentUsername, ObjectId courseId) {
        this.studentUsername = studentUsername;
        this.courseId = courseId;
    }

    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getStudentUsername() { return studentUsername; }
    public void setStudentUsername(String studentUsername) { this.studentUsername = studentUsername; }

    public ObjectId getCourseId() { return courseId; }
    public void setCourseId(ObjectId courseId) { this.courseId = courseId; }
}
