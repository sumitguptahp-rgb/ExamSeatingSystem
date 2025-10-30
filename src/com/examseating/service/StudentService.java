package com.examseating.service;

import com.examseating.model.Student;
import com.examseating.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Student operations
 */
public class StudentService {
    
    /**
     * Add a new student
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (student_id, name, course, semester, email) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getCourse());
            pstmt.setInt(4, student.getSemester());
            pstmt.setString(5, student.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added successfully!");
                return true;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // Duplicate entry
                System.err.println("Student ID already exists!");
            } else {
                System.err.println("Error adding student: " + e.getMessage());
            }
        }
        
        return false;
    }
    
    /**
     * Get all students
     */
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students ORDER BY student_id";
        List<Student> students = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setName(rs.getString("name"));
                student.setCourse(rs.getString("course"));
                student.setSemester(rs.getInt("semester"));
                student.setEmail(rs.getString("email"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        
        return students;
    }
    
    /**
     * Display all students
     */
    public void displayAllStudents() {
        List<Student> students = getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println("\n--- All Students ---");
        System.out.println("--------------------------------------------------");
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("--------------------------------------------------");
    }
}

