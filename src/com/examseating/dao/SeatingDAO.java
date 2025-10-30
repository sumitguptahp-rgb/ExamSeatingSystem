package com.examseating.dao;

import com.examseating.model.SeatingArrangement;
import com.examseating.model.Student;
import com.examseating.model.Room;
import com.examseating.model.Exam;
import com.examseating.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Seating Arrangement operations
 */
public class SeatingDAO {
    
    /**
     * Assign seats to students for an exam
     */
    public boolean assignSeats(int examId) {
        String sql = "INSERT INTO seating_arrangements (student_id, room_id, exam_id, seat_number) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get all students for the exam
            List<Student> students = getStudentsForExam(examId);
            if (students.isEmpty()) {
                System.out.println("No students found for this exam.");
                return false;
            }
            
            // Get all available rooms
            List<Room> rooms = getAllRooms();
            if (rooms.isEmpty()) {
                System.out.println("No rooms available.");
                return false;
            }
            
            // Check if seats are already assigned
            if (hasSeatsAssigned(examId)) {
                System.out.println("Seats are already assigned for this exam.");
                return false;
            }
            
            // Assign seats
            int currentSeat = 1;
            int currentRoomIndex = 0;
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Student student : students) {
                    if (currentRoomIndex >= rooms.size()) {
                        System.out.println("Not enough rooms for all students.");
                        break;
                    }
                    
                    Room currentRoom = rooms.get(currentRoomIndex);
                    
                    // Assign student to current room and seat
                    pstmt.setString(1, student.getStudentId());
                    pstmt.setInt(2, currentRoom.getRoomId());
                    pstmt.setInt(3, examId);
                    pstmt.setInt(4, currentSeat);
                    pstmt.addBatch();
                    
                    currentSeat++;
                    
                    // Move to next room if current room is full
                    if (currentSeat > currentRoom.getCapacity()) {
                        currentRoomIndex++;
                        currentSeat = 1;
                    }
                }
                
                pstmt.executeBatch();
                System.out.println("Seats assigned successfully!");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error assigning seats: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get students registered for a specific exam
     */
    private List<Student> getStudentsForExam(int examId) throws SQLException {
        String sql = "SELECT s.* FROM students s " +
                     "JOIN exams e ON s.course = e.course AND s.semester = e.semester " +
                     "WHERE e.exam_id = ?";
        
        List<Student> students = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setStudentId(rs.getString("student_id"));
                    student.setName(rs.getString("name"));
                    student.setCourse(rs.getString("course"));
                    student.setSemester(rs.getInt("semester"));
                    student.setEmail(rs.getString("email"));
                    students.add(student);
                }
            }
        }
        
        return students;
    }
    
    /**
     * Get all rooms
     */
    private List<Room> getAllRooms() throws SQLException {
        String sql = "SELECT * FROM rooms ORDER BY room_id";
        List<Room> rooms = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setBuildingName(rs.getString("building_name"));
                room.setCapacity(rs.getInt("capacity"));
                room.setFloorNumber(rs.getInt("floor_number"));
                rooms.add(room);
            }
        }
        
        return rooms;
    }
    
    /**
     * Check if seats are already assigned for an exam
     */
    private boolean hasSeatsAssigned(int examId) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM seating_arrangements WHERE exam_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Get seating arrangement details with student, room, and exam information
     */
    public List<SeatingArrangement> getSeatingArrangementDetails(int examId) {
        String sql = "SELECT sa.*, s.name as student_name, s.course, " +
                     "r.room_number, r.building_name, " +
                     "e.exam_name, e.exam_date " +
                     "FROM seating_arrangements sa " +
                     "JOIN students s ON sa.student_id = s.student_id " +
                     "JOIN rooms r ON sa.room_id = r.room_id " +
                     "JOIN exams e ON sa.exam_id = e.exam_id " +
                     "WHERE sa.exam_id = ? " +
                     "ORDER BY r.room_id, sa.seat_number";
        
        List<SeatingArrangement> arrangements = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SeatingArrangement arrangement = new SeatingArrangement();
                    arrangement.setArrangementId(rs.getInt("arrangement_id"));
                    arrangement.setStudentId(rs.getString("student_id"));
                    arrangement.setRoomId(rs.getInt("room_id"));
                    arrangement.setExamId(rs.getInt("exam_id"));
                    arrangement.setSeatNumber(rs.getInt("seat_number"));
                    
                    // Create and set related objects
                    Student student = new Student();
                    student.setName(rs.getString("student_name"));
                    student.setCourse(rs.getString("course"));
                    arrangement.setStudent(student);
                    
                    Room room = new Room();
                    room.setRoomNumber(rs.getString("room_number"));
                    room.setBuildingName(rs.getString("building_name"));
                    arrangement.setRoom(room);
                    
                    Exam exam = new Exam();
                    exam.setExamName(rs.getString("exam_name"));
                    exam.setExamDate(rs.getDate("exam_date").toLocalDate());
                    arrangement.setExam(exam);
                    
                    arrangements.add(arrangement);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching seating arrangement: " + e.getMessage());
        }
        
        return arrangements;
    }
    
    /**
     * Delete seating arrangement for an exam
     */
    public boolean deleteSeatingArrangement(int examId) {
        String sql = "DELETE FROM seating_arrangements WHERE exam_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Seating arrangement deleted successfully!");
                return true;
            } else {
                System.out.println("No seating arrangement found for this exam.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting seating arrangement: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get total number of students for an exam
     */
    public int getStudentCountForExam(int examId) {
        String sql = "SELECT COUNT(*) as count FROM students s " +
                     "JOIN exams e ON s.course = e.course AND s.semester = e.semester " +
                     "WHERE e.exam_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student count: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Get total capacity of all rooms
     */
    public int getTotalRoomCapacity() {
        String sql = "SELECT SUM(capacity) as total FROM rooms";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error getting total capacity: " + e.getMessage());
        }
        
        return 0;
    }
}

