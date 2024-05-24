import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.sql.*;

public class IMT2022128_library {

    // Set JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/librarydb";

    // Database credentials
    static final String USER = "root"; // add your user
    static final String PASSWORD = "admin"; // add password

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        // STEP 2. Connecting to the Database
        try {
            // STEP 2a: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 2b: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            conn.setAutoCommit(false);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            System.out.println("Welcome to the Library!");
            Scanner scanner = new Scanner(System.in);

            String choice;
            do {
                System.out.println("Press 1 if you are a Student.");
                System.out.println("Press 2 if you are a Librarian.");
                System.out.println("Press 0 to exit.");
                System.out.print("Enter your choice: ");

                choice = scanner.nextLine();
                
                

                if ("1".equals(choice)) {
                    System.out.println("Welcome, Student!");
                    System.out.println("Are you an existing member?");
                    System.out.println("Press 1 for Yes, 2 for No.");
                    String memberChoiceStr = scanner.nextLine();
                    int memberChoice = Integer.parseInt(memberChoiceStr);
                     boolean x = true;
                    while(x){
                    if (memberChoice == 1) {
                    
                        System.out.println("Please enter your ID:");
                        String studentIdStr = scanner.nextLine();
                        int studentId = Integer.parseInt(studentIdStr);
                        if (isMember(conn, studentId)) {
                            // System.out.println("Welcome back!");
                            int studentOption;
                            boolean studentMenuActive = true;
                            displayStudentMenu();
                            while (studentMenuActive) {
                                String studentOptionStr = scanner.nextLine();
                                studentOption = Integer.parseInt(studentOptionStr);
                                switch (studentOption) {
                                    case 1:
                                        displayAllBooks(stmt);
                                        break;
                                    case 2:
                                        System.out.println("Enter the genre:");
                                       // String genre = scanner.nextLine();
                                        Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                        showBooksByGenre(stm);
                                        break;
                                    case 3:
                                        System.out.println("Borrowing a book...");
                                        System.out.print("Enter the book ID: ");
                                        String bookIdString = scanner.nextLine();
                                        int bookId = Integer.parseInt(bookIdString);
                                        borrowBook(conn, studentId, bookId);
                                        break;
                                    case 4:
                                        System.out.println("Returning a book...");
                                        System.out.print("Enter the book ID: ");
                                        String bIdString = scanner.nextLine();
                                        int bId = Integer.parseInt(bIdString);
                                        returnBook(conn, bId);
                                        break;
                                    case 5:
                                        System.out.println("Viewing borrowed books...");
                                        viewBorrowedBooks(conn, studentId);
                                        break;
                                    case 0:
                                        System.out.println("Returning to main menu.");
                                        studentMenuActive = false;
                                        x = false;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            }
                        } else {
                            System.out.println("Sorry, your ID does not match.");
                            x = false;

                        }
                    
                    } else if (memberChoice == 2) {
                        System.out.println("Let's proceed with the registration process.");
                        String name;
                        int studentId = 0;
                        boolean isIdUnique = false;
                        while (!isIdUnique) {
                        System.out.print("Enter Student ID: ");
                        String studentIdStr = scanner.nextLine();
                        studentId = Integer.parseInt(studentIdStr);
                        stmt = conn.createStatement();

                        String checkStudentQuery = "SELECT * FROM student WHERE student_id = " + studentId;
                        ResultSet existingStudent = stmt.executeQuery(checkStudentQuery);

                         if (existingStudent.next()) {
                             System.out.println("Student with ID " + studentId + " already exists.");
                             System.out.println("Please provide another student ID.");
                             }
                        else{isIdUnique = true;}
                             }
                        System.out.print("Enter student name: ");
                        name = scanner.nextLine();
                        addStudent(conn, studentId, name);
                        memberChoice = 1;
                        System.out.println("Enjoy Reading!");
                        
                    } else {
                        System.out.println("Invalid choice for membership status.");
                    }
                }
                
                } else if ("2".equals(choice)) {
                
                    System.out.print("Enter Librarian ID: ");
                    String librarianIdStr = scanner.nextLine();
                    
                    int librarianId = Integer.parseInt(librarianIdStr);
                    if(isLibrarian(conn, librarianId)){
                        System.out.println("Welcome, Librarian!");
                        displayLibrarianMenu();
                        int librarianOption;
                        boolean op = true;
                        while(op){
                            String librarianOptionStr = scanner.nextLine();
                            librarianOption = Integer.parseInt(librarianOptionStr);
                            switch (librarianOption) {
                                case 1:
                                    System.out.println("Adding a student...");
                                    String name;
                                    int studentId = 0;
                                    boolean isIdUnique = false;
                                    while (!isIdUnique) {
                                    System.out.print("Enter Student Id: ");
                                    String studentIdStr = scanner.nextLine();
                                    studentId = Integer.parseInt(studentIdStr);
                                    stmt = conn.createStatement();
                                    
                                    String checkStudentQuery = "SELECT * FROM student WHERE student_id = " + studentId;
                                    ResultSet existingStudent = stmt.executeQuery(checkStudentQuery);
                                    
                                     if (existingStudent.next()) {
                                         System.out.println("Student with ID " + studentId + " already exists.");
                                         System.out.println("Please provide another student ID.");
                                         }
                                    else{isIdUnique = true;}
                                         }
                                    System.out.print("Enter student name: ");
                                    name = scanner.nextLine();

                                    
                                    addStudent(conn, studentId, name);
                                    // Call a method to add a student
                                    break;
                                case 2:
                                    System.out.println("Adding a book...");
                                    boolean isBookIdUnique = false;
                                    int bookId = 0;

                                    while (!isBookIdUnique) {
                                        try {
                                            System.out.print("Enter book ID: ");
                                            String bookIdStr = scanner.nextLine();
                                            bookId = Integer.parseInt(bookIdStr);
                                        
                                            stmt = conn.createStatement();
                                            String checkBookQuery = "SELECT * FROM book WHERE book_id = " + bookId;
                                            ResultSet existingBook = stmt.executeQuery(checkBookQuery);
                                        
                                            if (existingBook.next()) {
                                                System.out.println("Book with ID " + bookId + " already exists.");
                                                System.out.println("Please provide another book ID.");
                                            } else {
                                                isBookIdUnique = true;
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input. Please enter a valid book ID.");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                        String bookName, bookAuthor, genre;
                                        int quantity;
                                        System.out.print("Enter Book Name: ");
                                        bookName = scanner.nextLine();
                                        System.out.print("Enter Book Author: ");
                                        bookAuthor = scanner.nextLine();
                                        System.out.print("Enter Quantity: ");
                                        String quantityStr = scanner.nextLine();
                                        quantity = Integer.parseInt(quantityStr);
                                        System.out.print("Enter Genre: ");
                                        genre = scanner.nextLine();
                                        addBook(conn, bookId, bookName, bookAuthor, quantity, genre);
                                    break;
                                case 3:
                                    System.out.println("Showing all students...");
                                    showAllStudents(conn);
                                    break;
                                case 4:
                                    System.out.println("Showing all books...");
                                    displayAllBooks(stmt);
                                    break;
                                    
                                case 5:
                                    System.out.println("Showing borrowed books by student ID...");
                                    System.out.print("Enter Student ID: ");
                                    String inputString = scanner.nextLine();
                                    int number = Integer.parseInt(inputString);
                                    viewBorrowedBooks(conn, number);
                                    break;
                                case 6:
                                    System.out.println("Removing a student...");
                                    System.out.print("Enter Student ID: ");
                                    String StudentId = scanner.nextLine(); 
                                    int sid = Integer.parseInt(StudentId); 
                                    removeStudent(conn, sid);                                
                                    break;
                                case 7:
                                    System.out.println("Removing a book...");
                                    System.out.print("Enter Book ID: ");
                                    String bookIdStr = scanner.nextLine();
                                    int bId = Integer.parseInt(bookIdStr);
                                    removeBookById(conn, bId);
                                    break;
                                case 0:
                                    System.out.println("Returning to the main menu.");
                                    op = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please enter a valid option.");
                                    break;
                            }
                    }
                    }else{System.out.println("Librarian Id does not match.");}

                } else if ("0".equals(choice)) {
                    System.out.println("Exiting the program.");
                } else {
                    System.out.println("Invalid choice. Please enter 1 for student or 2 for librarian.");
                }
            } while (!"0".equals(choice));

        } catch (SQLException se) { // Handle errors for JDBC
            se.printStackTrace();
            System.out.println("Rolling back data here....");
            try{
               if(conn!=null)
                   conn.rollback();
            }catch(SQLException se2){
	            System.out.println("Rollback failed....");
                    se2.printStackTrace();
            }
        } catch (Exception e) { // Handle errors for Class.forName
            e.printStackTrace();
        } finally { // finally block used to close resources regardless of whether an exception was
            // thrown or not
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        }
        System.out.println("End of Code");
    } // end main

    public static boolean isMember(Connection conn, int studentId) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM student WHERE student_id = " + studentId;
            rs = stmt.executeQuery(sql);
            return rs.next(); // If rs.next() is true, the student ID exists; otherwise, false
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    public static void displayStudentMenu() {
        System.out.println("***************************");
        System.out.println("     Student Menu");
        System.out.println("***************************");
        System.out.println("1. Show all books");
        System.out.println("2. Show books by genre");
        System.out.println("3. Borrow a book");
        System.out.println("4. Return a book");
        System.out.println("5. View borrowed books");
        System.out.println("0. Exit");
        System.out.println("***************************");
    }

    public static void displayLibrarianMenu() {
    System.out.println("***************************");
    System.out.println("     Librarian Menu");
    System.out.println("***************************");
    System.out.println("1. Add a student");
    System.out.println("2. Add a book");
    System.out.println("3. Show all students");
    System.out.println("4. Show all books");
    System.out.println("5. Show borrowed books by student ID");
    System.out.println("6. Remove a student");
    System.out.println("7. Remove a book");
    System.out.println("0. Exit");
    System.out.println("***************************");
}
    public static void displayAllBooks(Statement stmt) throws SQLException {
        System.out.println("***************************");
        System.out.println("     All Books");
        System.out.println("***************************");

        String sql = "SELECT b.book_id, b.book_name, b.book_author, b.quantity, g.genre_name " +
                "FROM book b INNER JOIN genre g ON b.genre_no = g.genre_no";
        ResultSet rs = stmt.executeQuery(sql);

        // Displaying the books
        while (rs.next()) {
            int bookId = rs.getInt("book_id");
            String bookName = rs.getString("book_name");
            String author = rs.getString("book_author");
            int quantity = rs.getInt("quantity");
            String genreName = rs.getString("genre_name");

            System.out.println("Book ID: " + bookId);
            System.out.println("Book Name: " + bookName);
            System.out.println("Author: " + author);
            System.out.println("Quantity: " + quantity);
            System.out.println("Genre Name: " + genreName);
            System.out.println("--------------------------------------");
        }

        rs.close();
    }

   public static void showBooksByGenre(Statement stmt) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    //System.out.print("Enter the genre name: ");
    String genreName = scanner.nextLine().trim(); // Trim whitespace

    System.out.println("***************************");
    System.out.println("Books in Genre: " + genreName);
    System.out.println("***************************");

    String sql = "SELECT b.book_id, b.book_name, b.book_author, b.quantity " +
                 "FROM book b INNER JOIN genre g ON b.genre_no = g.genre_no " +
                 "WHERE g.genre_name = '" + genreName + "'";
    ResultSet rs = stmt.executeQuery(sql);

    // Check if there are any books in the specified genre
    if (!rs.next()) {
        System.out.println("No books found in the genre: " + genreName);
    } else {
        rs.beforeFirst(); // Move cursor back to first row for iteration

        // Displaying the books
        while (rs.next()) {
            int bookId = rs.getInt("book_id");
            String bookName = rs.getString("book_name");
            String author = rs.getString("book_author");
            int quantity = rs.getInt("quantity");

            System.out.println("Book ID: " + bookId);
            System.out.println("Book Name: " + bookName);
            System.out.println("Author: " + author);
            System.out.println("Quantity: " + quantity);
            System.out.println("--------------------------------------");
        }
    }

    rs.close();
}

   public static void borrowBook(Connection conn, int studentId, int bookId) throws SQLException {
      Statement stmt = null;
      try {
         stmt = conn.createStatement();

         // Check if the book is available (quantity > 0)
         String checkAvailabilityQuery = "SELECT quantity FROM book WHERE book_id = " + bookId;
         ResultSet availabilityRs = stmt.executeQuery(checkAvailabilityQuery);

         if (availabilityRs.next()) {
               int quantity = availabilityRs.getInt("quantity");
               if (quantity > 0) {
                  // Reduce the quantity of the book by 1
                  String reduceQuantityQuery = "UPDATE book SET quantity = " + (quantity - 1) + " WHERE book_id = " + bookId;
                  stmt.executeUpdate(reduceQuantityQuery);

                  // Add a new record in the student_book table
                  String borrowBookQuery = "INSERT INTO student_book (student_id, book_id) VALUES (" + studentId + ", " + bookId + ")";
                  stmt.executeUpdate(borrowBookQuery);
                  
                  conn.commit();

                  System.out.println("Book borrowed successfully.");
               } else {
                  System.out.println("Book is not available for borrowing.");
               }
         } else {
               System.out.println("Book not found.");
         }

         availabilityRs.close();
      } finally {
         if (stmt != null) {
               stmt.close();
         }
      }
   }

   public static void returnBook(Connection conn, int bookId) throws SQLException {
    Statement stmt = null;
    ResultSet rs = null;
    try {
        stmt = conn.createStatement();
        String sql = "SELECT b.book_id, b.book_name, b.book_author " +
                     "FROM student_book sb " +
                     "INNER JOIN book b ON sb.book_id = b.book_id " +
                     "WHERE sb.book_id = " + bookId;
        rs = stmt.executeQuery(sql);
        // Delete the entry from the student_book table based on book ID
        if (!rs.next()) {
            System.out.println("Book not borrowed with the given Id.");
        }else{
        String deleteSql = "DELETE FROM student_book WHERE book_id = " + bookId;
        stmt.executeUpdate(deleteSql);

        // Increase the quantity of the book in the book table
        String updateSql = "UPDATE book SET quantity = quantity + 1 WHERE book_id = " + bookId;
        stmt.executeUpdate(updateSql);

        conn.commit();

        System.out.println("Book returned successfully.");
        }
    } finally {
        if (stmt != null) {
            stmt.close();
        }
    }
}

public static void viewBorrowedBooks(Connection conn, int studentId) throws SQLException {
    Statement stmt = null;
    ResultSet rs = null;
    if(isMember(conn, studentId)){
    try {
        stmt = conn.createStatement();

        // Query to get borrowed books for the given student ID
        String sql = "SELECT b.book_id, b.book_name, b.book_author " +
                     "FROM student_book sb " +
                     "INNER JOIN book b ON sb.book_id = b.book_id " +
                     "WHERE sb.student_id = " + studentId;
        rs = stmt.executeQuery(sql);

        // Displaying the borrowed books
        System.out.println("Borrowed Books for Student ID " + studentId + ":");
        if (!rs.next()) {
            System.out.println("No books borrowed.");
        } else {
            do {
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                String author = rs.getString("book_author");

                System.out.println("Book ID: " + bookId);
                System.out.println("Book Name: " + bookName);
                System.out.println("Author: " + author);
                System.out.println("--------------------------------------");
            } while (rs.next());
        }
    } finally {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
    }}
    else{System.out.println("Student Id does not exist");}
}

public static void addStudent(Connection conn, int studentId, String studentName) throws SQLException {
    Statement stmt = null;

    try {
        stmt = conn.createStatement();

        String checkStudentQuery = "SELECT * FROM student WHERE student_id = " + studentId;
        ResultSet existingStudent = stmt.executeQuery(checkStudentQuery);

        if (existingStudent.next()) {
            System.out.println("Student with ID " + studentId + " already exists.");
            System.out.println("Please provide another student ID.");
        } else {
            String insertStudentQuery = "INSERT INTO student (student_id, student_name) VALUES (" +
                    studentId + ", '" + studentName + "')";
            int rowsAffected = stmt.executeUpdate(insertStudentQuery);
            conn.commit();
            if (rowsAffected > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        }
    } finally {
        if (stmt != null) {
            stmt.close();
        }
    }
}

public static boolean isLibrarian(Connection conn, int librarianId) throws SQLException {
    Statement stmt = null;
    ResultSet rs = null;

    try {
        stmt = conn.createStatement();
        String sql = "SELECT * FROM librarian WHERE librarian_id = " + librarianId;
        rs = stmt.executeQuery(sql);
        return rs.next(); // If rs.next() is true, the librarian ID exists; otherwise, false
    } finally {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
    }
}

public static void addBook(Connection conn, int bookId, String bookName, String bookAuthor, int quantity, String genre) throws SQLException {
    Statement stmt = null;
    ResultSet rs = null;


    try {
        stmt = conn.createStatement();

        // Retrieve the genre number based on the genre name
        String getGenreNoQuery = "SELECT genre_no FROM genre WHERE genre_name = '" + genre + "'";
        rs = stmt.executeQuery(getGenreNoQuery);

        if (rs.next()) {
            int genreNo = rs.getInt("genre_no");

            // Insert the book into the book table
            String insertBookQuery = "INSERT INTO book (book_id, book_name, book_author, quantity, genre_no) VALUES (" +
            bookId + ", '" + bookName + "', '" + bookAuthor + "', " + quantity + ", " + genreNo + ")";

            int rowsAffected = stmt.executeUpdate(insertBookQuery);
            
            conn.commit();

            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add book.");
            }
        } else {
            System.out.println("Genre not found.");
        }
    } finally {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
    }
}


public static void showAllStudents(Connection conn) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM student";
            rs = stmt.executeQuery(sql);

            System.out.println("************************************");
            System.out.println("          List of Students");
            System.out.println("************************************");
            System.out.println("Student ID\tStudent Name");

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                System.out.printf("%-11d\t%-21s\n", studentId, studentName);
            }

            System.out.println("************************************");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }


public static void removeBookById(Connection conn, int bookId) throws SQLException {
    Statement stmt = null;

    try {
        stmt = conn.createStatement();

        // Check if the book exists based on its ID
        String checkBookQuery = "SELECT * FROM book WHERE book_id = " + bookId;
        ResultSet existingBook = stmt.executeQuery(checkBookQuery);

        if (existingBook.next()) {
            // Book exists, proceed with deletion

            // Delete the book from the book table
            String deleteBookQuery = "DELETE FROM book WHERE book_id = " + bookId;
            int rowsAffected = stmt.executeUpdate(deleteBookQuery);

            conn.commit();

            if (rowsAffected > 0) {
                System.out.println("Book with ID " + bookId + " removed successfully.");
            } else {
                System.out.println("Failed to remove book with ID " + bookId + ".");
            }
        } else {
            // Book doesn't exist
            System.out.println("Book with ID " + bookId + " not found.");
        }
    } finally {
        if (stmt != null) {
            stmt.close();
        }
    }
}

    public static void removeStudent(Connection conn, int studentId) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            // Prepare the SQL statement
            String sql = "DELETE FROM student WHERE student_id = ?";
            pstmt = conn.prepareStatement(sql);

            // Set the student ID parameter in the prepared statement
            pstmt.setInt(1, studentId);

            // Execute the delete operation
            int rowsAffected = pstmt.executeUpdate();

            conn.commit();

            // Check if the deletion was successful
            if (rowsAffected > 0) {
                System.out.println("Student with ID " + studentId + " removed successfully.");
            } else {
                System.out.println("No student found with ID " + studentId + ". No changes made.");
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }


} // end class


