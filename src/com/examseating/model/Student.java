package com.examseating.model;

/**
 * Student model class
 */
public class Student {
    private String studentId;
    private String name;
    private String course;
    private int semester;
    private String email;
    
    public Student() {}
    
    public Student(String studentId, String name, String course, int semester, String email) {
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.semester = semester;
        this.email = email;
    }
    
    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public int getSemester() {
        return semester;
    }
    
    public void setSemester(int semester) {
        this.semester = semester;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Student [ID: " + studentId + ", Name: " + name + ", Course: " + course + 
               ", Semester: " + semester + ", Email: " + email + "]";
    }
}

