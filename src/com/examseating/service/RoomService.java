package com.examseating.service;

import com.examseating.model.Room;
import com.examseating.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Room operations
 */
public class RoomService {
    
    /**
     * Add a new room
     */
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, building_name, capacity, floor_number) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, room.getRoomNumber());
            pstmt.setString(2, room.getBuildingName());
            pstmt.setInt(3, room.getCapacity());
            pstmt.setInt(4, room.getFloorNumber());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Room added successfully!");
                return true;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // Duplicate entry
                System.err.println("Room number already exists!");
            } else {
                System.err.println("Error adding room: " + e.getMessage());
            }
        }
        
        return false;
    }
    
    /**
     * Get all rooms
     */
    public List<Room> getAllRooms() {
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
        } catch (SQLException e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
        }
        
        return rooms;
    }
    
    /**
     * Display all rooms
     */
    public void displayAllRooms() {
        List<Room> rooms = getAllRooms();
        
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }
        
        System.out.println("\n--- All Rooms ---");
        System.out.println("--------------------------------------------------");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("--------------------------------------------------");
    }
}

