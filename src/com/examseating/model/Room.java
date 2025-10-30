package com.examseating.model;

/**
 * Room model class
 */
public class Room {
    private int roomId;
    private String roomNumber;
    private String buildingName;
    private int capacity;
    private int floorNumber;
    
    public Room() {}
    
    public Room(int roomId, String roomNumber, String buildingName, int capacity, int floorNumber) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.buildingName = buildingName;
        this.capacity = capacity;
        this.floorNumber = floorNumber;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getBuildingName() {
        return buildingName;
    }
    
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getFloorNumber() {
        return floorNumber;
    }
    
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }
    
    @Override
    public String toString() {
        return "Room [ID: " + roomId + ", Number: " + roomNumber + ", Building: " + buildingName + 
               ", Capacity: " + capacity + ", Floor: " + floorNumber + "]";
    }
}

