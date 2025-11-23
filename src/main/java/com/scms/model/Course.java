package com.scms.model;

import org.bson.types.ObjectId;

/*
 Represents a course in the system
 Fields: id, courseName, assignedTeacherUsername
*/
public class Course {

    private ObjectId id;
    private String courseName;
    private String assignedTeacher; // store teacher username for simplicity

    public Course() {}

    public Course(String courseName, String assignedTeacher) {
        this.courseName = courseName;
        this.assignedTeacher = assignedTeacher;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(String assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }
}
