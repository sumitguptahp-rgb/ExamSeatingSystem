package com.examseating;

import com.examseating.dao.SeatingDAO;
import com.examseating.model.*;
import com.examseating.service.ExamService;
import com.examseating.service.RoomService;
import com.examseating.service.StudentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class WITHOUT database dependency
 * Use this to test the application structure
 */
public class ExamSeatingMainNoDB {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static RoomService roomService = new RoomService();
    private static ExamService examService = new ExamService();
    private static SeatingDAO seatingDAO = new SeatingDAO();
    
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   Exam Seating Arrangement System");
        System.out.println("   (Running in DEMO mode - No Database)");
        System.out.println("============================================\n");
        
        System.out.println("⚠️  WARNING: Running without database connection");
        System.out.println("This is a demonstration mode.");
        System.out.println("Database features will not work.\n");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 0:
                    System.out.println("Thank you for using the Exam Seating Arrangement System!");
                    running = false;
                    break;
                case 99:
                    showInstructions();
                    break;
                default:
                    System.out.println("\n⚠️  This feature requires database connection.");
                    System.out.println("Please install MySQL connector to use this feature.");
                    System.out.println("Run: FIX_DATABASE_CONNECTION.bat for instructions.");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n--- Main Menu (DEMO MODE) ---");
        System.out.println("99. Show Setup Instructions");
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
    
    private static void showInstructions() {
        System.out.println("\n============================================");
        System.out.println("   Setup Instructions");
        System.out.println("============================================");
        System.out.println();
        System.out.println("TO FIX THE DATABASE CONNECTION ERROR:");
        System.out.println();
        System.out.println("1. Download MySQL Connector:");
        System.out.println("   - Run: DOWNLOAD_MYSQL_CONNECTOR.bat");
        System.out.println("   - OR go to: https://dev.mysql.com/downloads/connector/j/");
        System.out.println("   - Download: mysql-connector-java-8.0.33.jar");
        System.out.println("   - Save to: lib folder");
        System.out.println();
        System.out.println("2. Setup MySQL Database:");
        System.out.println("   - Install MySQL Server");
        System.out.println("   - Run: mysql -u root -p < database\\schema.sql");
        System.out.println();
        System.out.println("3. Update Database Credentials:");
        System.out.println("   - Edit: src\\com\\examseating\\util\\DatabaseConnection.java");
        System.out.println("   - Change username and password");
        System.out.println();
        System.out.println("4. Run the Full Application:");
        System.out.println("   - Use: RUN_NOW.bat (with MySQL connector)");
        System.out.println("   - Or compile and run ExamSeatingMain.java");
        System.out.println();
        System.out.println("============================================");
    }
}

