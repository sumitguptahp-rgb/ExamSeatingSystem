# ExamSeatingSystem
A Java-based console application for managing exam seating arrangements in educational institutions. Database Mysql
# Exam Seating Arrangement System

A Java-based console application for managing exam seating arrangements in educational institutions.

## Features

- **Student Management**: Add and view student information
- **Room Management**: Add and view examination rooms
- **Exam Management**: Create and manage exams
- **Seat Assignment**: Automatically assign students to seats across available rooms
- **View Arrangements**: Display seating arrangements for specific exams
- **Statistics**: View system-wide statistics

## Prerequisites

- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher (for building the project)

## Database Setup

1. Make sure MySQL is running on your system
2. Create the database and tables by running the SQL script:

```bash
mysql -u root -p < database/schema.sql
```

Or manually execute the SQL file in MySQL Workbench or command line.

3. Update the database credentials in `src/com/examseating/util/DatabaseConnection.java`:
   - `DB_USERNAME`: Your MySQL username
   - `DB_PASSWORD`: Your MySQL password

## Building the Project

### Using Maven:

```bash
# Navigate to the project directory
cd C:\Users\Asus\ExamSeatingSystem

# Compile the project
mvn clean compile

# Package the project into a JAR file
mvn package
```

This will create a JAR file in the `target` directory.

### Using Java Compiler (Alternative):

```bash
# Create a classpath with MySQL connector
# First, download mysql-connector-java-8.0.33.jar and place it in the lib folder

# Compile all Java files
javac -cp "lib/mysql-connector-java-8.0.33.jar" -d bin src/com/examseating/**/*.java
```

## Running the Application

### Using Maven:

```bash
mvn exec:java -Dexec.mainClass="com.examseating.ExamSeatingMain"
```

### Using the compiled JAR:

```bash
java -jar target/ExamSeatingSystem-1.0-SNAPSHOT.jar
```

### Using Java directly:

```bash
java -cp "bin;lib/mysql-connector-java-8.0.33.jar" com.examseating.ExamSeatingMain
```

## Usage

1. Start the application
2. The system will verify the database connection
3. Use the menu to:
   - Add students with their course and semester information
   - Add examination rooms with their capacity
   - Create exams for specific courses and semesters
   - Assign seats to students automatically
   - View seating arrangements
   - Delete seating arrangements if needed
   - View system statistics

## Project Structure

```
ExamSeatingSystem/
├── src/
│   └── com/
│       └── examseating/
│           ├── model/           # Model classes (Student, Room, Exam, SeatingArrangement)
│           ├── dao/             # Data Access Objects (SeatingDAO)
│           ├── service/         # Service classes (StudentService, RoomService, ExamService)
│           ├── util/            # Utility classes (DatabaseConnection)
│           └── ExamSeatingMain.java  # Main application class
├── database/
│   └── schema.sql               # Database schema and sample data
├── pom.xml                      # Maven configuration
└── README.md                    # This file
```

## Database Schema

The application uses the following main tables:

- **students**: Stores student information (ID, name, course, semester, email)
- **rooms**: Stores room information (ID, room number, building, capacity)
- **exams**: Stores exam information (ID, name, date, time, course, semester)
- **seating_arrangements**: Maps students to rooms and seats for specific exams

## Sample Data

The database schema includes sample data:
- 6 students (3 Computer Science, 2 Electrical Engineering, 1 Mechanical Engineering)
- 3 rooms with varying capacities
- 2 sample exams

## Configuration

Before running the application, ensure you update the database connection details in:
- `src/com/examseating/util/DatabaseConnection.java`

Default configuration:
- Host: localhost
- Port: 3306
- Database: exam_seating_db
- Username: root
- Password: root

## Troubleshooting

1. **Database Connection Error**: 
   - Verify MySQL is running
   - Check database credentials
   - Ensure the database 'exam_seating_db' exists

2. **Class Not Found Error**:
   - Ensure MySQL JDBC driver is in the classpath
   - Verify all Java files are compiled

3. **SQL Errors**:
   - Check if the schema.sql was executed successfully
   - Verify table names and column names match the SQL schema

## License

This project is created for educational purposes.

## Author SUMIT GUPTA

Created for managing exam seating arrangements in educational institutions.


