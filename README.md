# Library-Management-System-DBMS

Overview
This Java program is a simple Library Management System designed to handle operations for both students and librarians. It utilizes MySQL database for data storage and management. The system allows students to borrow and return books, view available books, and librarians to manage students, books, and borrowings.

Setup Instructions
Database Setup:

Create a MySQL database named librarydb.
Import the provided SQL script librarydb.sql to create the necessary tables and populate initial data.
Java Environment Setup:

Ensure you have Java JDK installed on your system.
Import this Java project into your favorite IDE (like IntelliJ IDEA or Eclipse).
MySQL Connector:

Download the MySQL JDBC Connector (mysql-connector-java.jar) and add it to your project's build path.
Configuration:

Update the database connection details in IMT2022128_library.java:

USER: Your MySQL username.

PASSWORD: Your MySQL password.

DB_URL: JDBC URL for your MySQL database.

How to Run

Compile the Java program
.
Run the compiled program (IMT2022128_library.class).

Usage

When prompted, choose between Student (Press 1) or Librarian (Press 2) role.

Students can view books, borrow, and return books.

Librarians can add/remove students, add/remove books, and manage borrowed books.

Dependencies

Java SE Development Kit (JDK) 8 or higher

MySQL Database

MySQL Connector for Java (JDBC)

Contributors
Your Name - Saniya Ismail Kondkar
