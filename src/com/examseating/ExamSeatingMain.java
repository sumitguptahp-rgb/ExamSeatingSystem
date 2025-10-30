package com.examseating;

import com.examseating.dao.SeatingDAO;
import com.examseating.model.*;
import com.examseating.service.ExamService;
import com.examseating.service.RoomService;
import com.examseating.service.StudentService;
import com.examseating.util.DatabaseConnection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class for Exam Seating Arrangement System
 */
public class ExamSeatingMain {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static RoomService roomService = new RoomService();
    private static ExamService examService = new ExamService();
    private static SeatingDAO seatingDAO = new SeatingDAO();
    
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   Exam Seating Arrangement System");
        System.out.println("============================================\n");
        
        // Test database connection
        if (!DatabaseConnection.testConnection()) {
            System.err.println("Database connection failed! Please check your database configuration.");
            System.err.println("Make sure MySQL is running and the database 'exam_seating_db' exists.");
            return;
        }
        
        System.out.println("Database connection successful!\n");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    addRoom();
                    break;
                case 4:
                    viewAllRooms();
                    break;
                case 5:
                    addExam();
                    break;
                case 6:
                    viewAllExams();
                    break;
                case 7:
                    assignSeats();
                    break;
                case 8:
                    viewSeatingArrangement();
                    break;
                case 9:
                    deleteSeatingArrangement();
                    break;
                case 10:
                    viewStatistics();
                    break;
                case 0:
                    System.out.println("Thank you for using the Exam Seating Arrangement System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1.  Add Student");
        System.out.println("2.  View All Students");
        System.out.println("3.  Add Room");
        System.out.println("4.  View All Rooms");
        System.out.println("5.  Add Exam");
        System.out.println("6.  View All Exams");
        System.out.println("7.  Assign Seats for Exam");
        System.out.println("8.  View Seating Arrangement");
        System.out.println("9.  Delete Seating Arrangement");
        System.out.println("10. View Statistics");
        System.out.println("0.  Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void addStudent() {
        System.out.println("\n--- Add Student ---");
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Course: ");
        String course = scanner.nextLine();
        
        System.out.print("Semester: ");
        int semester = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        Student student = new Student(studentId, name, course, semester, email);
        studentService.addStudent(student);
    }
    
    private static void viewAllStudents() {
        studentService.displayAllStudents();
    }
    
    private static void addRoom() {
        System.out.println("\n--- Add Room ---");
        System.out.print("Room Number: ");
        String roomNumber = scanner.nextLine();
        
        System.out.print("Building Name: ");
        String buildingName = scanner.nextLine();
        
        System.out.print("Capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Floor Number: ");
        int floorNumber = Integer.parseInt(scanner.nextLine());
        
        Room room = new Room(0, roomNumber, buildingName, capacity, floorNumber);
        roomService.addRoom(room);
    }
    
    private static void viewAllRooms() {
        roomService.displayAllRooms();
    }
    
    private static void addExam() {
        System.out.println("\n--- Add Exam ---");
        System.out.print("Exam Name: ");
        String examName = scanner.nextLine();
        
        System.out.print("Exam Date (YYYY-MM-DD): ");
        LocalDate examDate = LocalDate.parse(scanner.nextLine());
        
        System.out.print("Start Time (HH:MM:SS): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());
        
        System.out.print("End Time (HH:MM:SS): ");
        LocalTime endTime = LocalTime.parse(scanner.nextLine());
        
        System.out.print("Course: ");
        String course = scanner.nextLine();
        
        System.out.print("Semester: ");
        int semester = Integer.parseInt(scanner.nextLine());
        
        Exam exam = new Exam(0, examName, examDate, startTime, endTime, course, semester);
        examService.addExam(exam);
    }
    
    private static void viewAllExams() {
        examService.displayAllExams();
    }
    
    private static void assignSeats() {
        System.out.println("\n--- Assign Seats for Exam ---");
        viewAllExams();
        
        System.out.print("Enter Exam ID: ");
        int examId = Integer.parseInt(scanner.nextLine());
        
        Exam exam = examService.getExamById(examId);
        if (exam == null) {
            System.out.println("Exam not found!");
            return;
        }
        
        System.out.println("\nExam: " + exam.getExamName());
        System.out.println("Course: " + exam.getCourse() + ", Semester: " + exam.getSemester());
        
        int studentCount = seatingDAO.getStudentCountForExam(examId);
        int totalCapacity = seatingDAO.getTotalRoomCapacity();
        
        System.out.println("Students registered: " + studentCount);
        System.out.println("Total room capacity: " + totalCapacity);
        
        if (studentCount > totalCapacity) {
            System.out.println("Warning: Not enough seats for all students!");
        }
        
        System.out.print("\nDo you want to proceed with seat assignment? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            seatingDAO.assignSeats(examId);
        } else {
            System.out.println("Seat assignment cancelled.");
        }
    }
    
    private static void viewSeatingArrangement() {
        System.out.println("\n--- View Seating Arrangement ---");
        viewAllExams();
        
        System.out.print("Enter Exam ID: ");
        int examId = Integer.parseInt(scanner.nextLine());
        
        List<SeatingArrangement> arrangements = seatingDAO.getSeatingArrangementDetails(examId);
        
        if (arrangements.isEmpty()) {
            System.out.println("No seating arrangement found for this exam.");
            return;
        }
        
        System.out.println("\n--- Seating Arrangement ---");
        System.out.println("======================================================================");
        for (SeatingArrangement arrangement : arrangements) {
            System.out.println(arrangement);
        }
        System.out.println("======================================================================");
    }
    
    private static void deleteSeatingArrangement() {
        System.out.println("\n--- Delete Seating Arrangement ---");
        viewAllExams();
        
        System.out.print("Enter Exam ID: ");
        int examId = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Are you sure you want to delete the seating arrangement? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            seatingDAO.deleteSeatingArrangement(examId);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private static void viewStatistics() {
        System.out.println("\n--- Statistics ---");
        
        List<Student> students = studentService.getAllStudents();
        List<Room> rooms = roomService.getAllRooms();
        List<Exam> exams = examService.getAllExams();
        
        System.out.println("Total Students: " + students.size());
        System.out.println("Total Rooms: " + rooms.size());
        System.out.println("Total Exams: " + exams.size());
        
        if (!rooms.isEmpty()) {
            int totalCapacity = seatingDAO.getTotalRoomCapacity();
            System.out.println("Total Room Capacity: " + totalCapacity);
        }
    }
}

