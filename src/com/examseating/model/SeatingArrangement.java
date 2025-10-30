package com.examseating.model;

/**
 * Seating Arrangement model class
 */
public class SeatingArrangement {
    private int arrangementId;
    private String studentId;
    private int roomId;
    private int examId;
    private int seatNumber;
    private Student student;
    private Room room;
    private Exam exam;
    
    public SeatingArrangement() {}
    
    public SeatingArrangement(String studentId, int roomId, int examId, int seatNumber) {
        this.studentId = studentId;
        this.roomId = roomId;
        this.examId = examId;
        this.seatNumber = seatNumber;
    }
    
    // Getters and Setters
    public int getArrangementId() {
        return arrangementId;
    }
    
    public void setArrangementId(int arrangementId) {
        this.arrangementId = arrangementId;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public int getExamId() {
        return examId;
    }
    
    public void setExamId(int examId) {
        this.examId = examId;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }
    
    public Exam getExam() {
        return exam;
    }
    
    public void setExam(Exam exam) {
        this.exam = exam;
    }
    
    @Override
    public String toString() {
        if (student != null && room != null && exam != null) {
            return String.format("Student: %s (%s) | Room: %s (%s) | Seat: %d | Exam: %s on %s",
                    student.getName(), studentId, room.getRoomNumber(), room.getBuildingName(),
                    seatNumber, exam.getExamName(), exam.getExamDate());
        }
        return "SeatingArrangement [ID: " + arrangementId + ", Student ID: " + studentId + 
               ", Room ID: " + roomId + ", Exam ID: " + examId + ", Seat Number: " + seatNumber + "]";
    }
}

