package com.examseating.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Exam model class
 */
public class Exam {
    private int examId;
    private String examName;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String course;
    private int semester;
    
    public Exam() {}
    
    public Exam(int examId, String examName, LocalDate examDate, LocalTime startTime, 
                LocalTime endTime, String course, int semester) {
        this.examId = examId;
        this.examName = examName;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
        this.semester = semester;
    }
    
    // Getters and Setters
    public int getExamId() {
        return examId;
    }
    
    public void setExamId(int examId) {
        this.examId = examId;
    }
    
    public String getExamName() {
        return examName;
    }
    
    public void setExamName(String examName) {
        this.examName = examName;
    }
    
    public LocalDate getExamDate() {
        return examDate;
    }
    
    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
    
    @Override
    public String toString() {
        return "Exam [ID: " + examId + ", Name: " + examName + ", Date: " + examDate + 
               ", Time: " + startTime + " - " + endTime + ", Course: " + course + 
               ", Semester: " + semester + "]";
    }
}

