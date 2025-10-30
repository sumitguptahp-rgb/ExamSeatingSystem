-- Exam Seating Arrangement System Database Schema
CREATE DATABASE IF NOT EXISTS exam_seating_db;
USE exam_seating_db;
CREATE TABLE students (
    student_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(50) NOT NULL,
    semester INT NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(20) UNIQUE NOT NULL,
    building_name VARCHAR(50),
    capacity INT NOT NULL,
    floor_number INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE exams (
    exam_id INT AUTO_INCREMENT PRIMARY KEY,
    exam_name VARCHAR(100) NOT NULL,
    exam_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    course VARCHAR(50),
    semester INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE seating_arrangements (
    arrangement_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL,
    room_id INT NOT NULL,
    exam_id INT NOT NULL,
    seat_number INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id) ON DELETE CASCADE,
    UNIQUE KEY unique_exam_seat (room_id, exam_id, seat_number)
);
INSERT INTO students VALUES
('CS001', 'John Doe', 'Computer Science', 3, 'john.doe@example.com', NOW()),
('CS002', 'Jane Smith', 'Computer Science', 3, 'jane.smith@example.com', NOW()),
('CS003', 'Bob Johnson', 'Computer Science', 3, 'bob.johnson@example.com', NOW()),
('EE001', 'Alice Williams', 'Electrical Engineering', 3, 'alice.w@example.com', NOW()),
('EE002', 'Charlie Brown', 'Electrical Engineering', 3, 'charlie.b@example.com', NOW()),
('ME001', 'David Lee', 'Mechanical Engineering', 3, 'david.l@example.com', NOW());
INSERT INTO rooms VALUES
(1, 'R101', 'Main Building', 3, 1, NOW()),
(2, 'R102', 'Main Building', 3, 1, NOW()),
(3, 'R201', 'Main Building', 4, 2, NOW());
INSERT INTO exams VALUES
(1, 'Database Systems', '2024-03-20', '10:00:00', '13:00:00', 'Computer Science', 3, NOW()),
(2, 'Circuit Analysis', '2024-03-21', '10:00:00', '13:00:00', 'Electrical Engineering', 3, NOW());
