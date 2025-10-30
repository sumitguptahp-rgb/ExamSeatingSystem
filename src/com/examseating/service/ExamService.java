package com.examseating.service;

import com.examseating.model.Exam;
import com.examseating.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Exam operations
 */
public class ExamService {
    
    /**
     * Add a new exam
     */
    public boolean addExam(Exam exam) {
        String sql = "INSERT INTO exams (exam_name, exam_date, start_time, end_time, course, semester) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, exam.getExamName());
            pstmt.setDate(2, Date.valueOf(exam.getExamDate()));
            pstmt.setTime(3, Time.valueOf(exam.getStartTime()));
            pstmt.setTime(4, Time.valueOf(exam.getEndTime()));
            pstmt.setString(5, exam.getCourse());
            pstmt.setInt(6, exam.getSemester());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Exam added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding exam: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Get all exams
     */
    public List<Exam> getAllExams() {
        String sql = "SELECT * FROM exams ORDER BY exam_id";
        List<Exam> exams = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Exam exam = new Exam();
                exam.setExamId(rs.getInt("exam_id"));
                exam.setExamName(rs.getString("exam_name"));
                exam.setExamDate(rs.getDate("exam_date").toLocalDate());
                exam.setStartTime(rs.getTime("start_time").toLocalTime());
                exam.setEndTime(rs.getTime("end_time").toLocalTime());
                exam.setCourse(rs.getString("course"));
                exam.setSemester(rs.getInt("semester"));
                exams.add(exam);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching exams: " + e.getMessage());
        }
        
        return exams;
    }
    
    /**
     * Display all exams
     */
    public void displayAllExams() {
        List<Exam> exams = getAllExams();
        
        if (exams.isEmpty()) {
            System.out.println("No exams found.");
            return;
        }
        
        System.out.println("\n--- All Exams ---");
        System.out.println("--------------------------------------------------");
        for (Exam exam : exams) {
            System.out.println(exam);
        }
        System.out.println("--------------------------------------------------");
    }
    
    /**
     * Get exam by ID
     */
    public Exam getExamById(int examId) {
        String sql = "SELECT * FROM exams WHERE exam_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Exam exam = new Exam();
                    exam.setExamId(rs.getInt("exam_id"));
                    exam.setExamName(rs.getString("exam_name"));
                    exam.setExamDate(rs.getDate("exam_date").toLocalDate());
                    exam.setStartTime(rs.getTime("start_time").toLocalTime());
                    exam.setEndTime(rs.getTime("end_time").toLocalTime());
                    exam.setCourse(rs.getString("course"));
                    exam.setSemester(rs.getInt("semester"));
                    return exam;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching exam: " + e.getMessage());
        }
        
        return null;
    }
}

