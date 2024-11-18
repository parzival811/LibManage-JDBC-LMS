// Import required packages

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class imt2022124_librarydb {

   // Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/librarydb?useSSL=false";

   // Database credentials
   static final String USER = "root";// add your user
   static final String PASSWORD = "admin";// add password

   public static void main(String[] args) {
      Connection conn = null;
      Statement stmt = null;

      // Connecting to the Database
      try {
         // Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // Open a connection
         System.out.println("Connecting to database...");
         conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

         // Set auto commit as false.
         conn.setAutoCommit(false);

         // Execute a query
         System.out.println("Creating statement...");
         stmt = conn.createStatement();
         
         Scanner scn = new Scanner(System.in);

         //User Authentication
         System.out.printf("Username:");
         String Username = scn.nextLine();
         System.out.printf("Password:");
         String Password = scn.nextLine();
         String query = "SELECT Username, Password, Designation from Admin";
         ResultSet rs = stmt.executeQuery(query);
         int flag=0;
         while (rs.next()) {
            // Retrieve by column name
            String Uname = rs.getString("Username");
            String pwd = rs.getString("Password");
            String designation = rs.getString("Designation");
            if(Username.equals(Uname) && Password.equals(pwd)){
               flag=1;
               System.out.printf("Successfully logged in as %s\n", designation);
               break;
            }
            else System.out.println("Incorrect Username or password");
         }

         // Main Menu
         if(flag == 1){
            System.out.println("=".repeat(20) + "Library Management System" + "=".repeat(20));
            System.out.println("Welcome to the L.M.S. dashboard!");
            while(true){
               System.out.println("- - - - - - - - MAIN MENU - - - - - - - -");
               System.out.println("1. Add Book\n2. Add Student\n3. Remove Book\n4. Remove Student\n5. List All Available Books by an Author\n6. List All Available Books by Genres\n7. Recommend Books by Favorite Genre\n8. Issue Book\n9. Return Book\n10. List All Issued Books\n11. Change System Password\n12. Exit");
               System.out.println("- - - - - - - - - - - - - - - - - - - - -");
               System.out.printf("Enter your choice:");
               int n = scn.nextInt();
               scn.nextLine();
               
               // Handling different menu options
               if(n==1){   // Add Book
                  // Book details input
                  System.out.printf("Enter Book_No:");
                  int BNo = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter Copy_No:");
                  int CNo = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter Title:");
                  String ttl = scn.nextLine();
                  System.out.printf("Enter Author Name:");
                  String athr = scn.nextLine();
                  System.out.printf("Enter Publisher Name:");
                  String pub = scn.nextLine();
                  
                  // Fetching available genres
                  query="SELECT Gcode,Gname from Genre";
                  rs=stmt.executeQuery(query);
                  System.out.println("GenreCode | GenreName");
                  while(rs.next()){
                     System.out.printf("%s        %s\n",rs.getString("Gcode"),rs.getString("Gname"));
                  }

                  System.out.printf("Enter GenreCode:");
                  String gcode = scn.nextLine();
                  System.out.printf("Enter Price:");
                  int prc = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter ISBN:");
                  long isbn = scn.nextLong();
                  scn.nextLine();
                  System.out.printf("Enter Edition:");
                  String yr = scn.nextLine();

                  // Inserting book details into the database
                  query = String.format("INSERT INTO Book VALUES (%d, %d, \"%s\", \"%s\", \"%s\", \"%s\", %d, %d, \"%s\", 1)", BNo,CNo,ttl,athr,pub,gcode,prc,isbn,yr);
                  stmt.executeUpdate(query);
                  conn.commit();
                  System.out.println("Book added successfully.");
               }

               else if(n==2){ // Add Student
                  // Student details input
                  System.out.printf("Enter RegNo:");
                  int RgNo = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter First Name:");
                  String fnm = scn.nextLine();
                  System.out.printf("Enter Last Name:");
                  String lnm = scn.nextLine();

                  // Inserting student details into the database
                  query = String.format("INSERT INTO Student VALUES (%d, \"%s\", \"%s\")", RgNo,fnm,lnm);
                  stmt.executeUpdate(query);
                  conn.commit();
                  System.out.println("Student added successfully.");
               }
               else if(n==3){ // Remove Book
                  // Book removal process
                  System.out.printf("Enter Book_No:");
                  int BNo = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter Copy_No:");
                  int CNo = scn.nextInt();
                  scn.nextLine();

                  // Checking if the book exists
                  query = String.format("SELECT Book_No, Copy_No from Book");
                  rs=stmt.executeQuery(query);
                  int flag1=0;
                  while(rs.next()){
                     if(BNo == rs.getInt("Book_No") && CNo == rs.getInt("Copy_No")) flag1=1;
                  }
                  if(flag1==0){
                     System.out.println("There is no such book entry.");
                     continue;
                  }

                  // Deleting the book entry from the database
                  query = String.format("DELETE FROM Book WHERE Book_No = %d AND Copy_No = %d", BNo,CNo);
                  stmt.executeUpdate(query);
                  conn.commit();
                  System.out.println("Book entry deleted successfully.");
               }
               else if(n==4){ // Remove Student
                  // Student removal process
                  System.out.printf("Enter Student's RegNo:");
                  int RgNo = scn.nextInt();
                  scn.nextLine();

                  // Checking if the student exists
                  query = String.format("SELECT Regno from Student");
                  rs=stmt.executeQuery(query);
                  int flag1=0;
                  while(rs.next()){
                     if(RgNo == rs.getInt("Regno")) flag1=1;
                  }
                  if(flag1==0){
                     System.out.println("There is no such student entry.");
                     continue;
                  }

                  // Deleting the student entry from the database
                  query = String.format("DELETE FROM Student WHERE Regno = %d", RgNo);
                  stmt.executeUpdate(query);
                  conn.commit();
                  System.out.println("Student entry deleted successfully.");
               }
               else if(n==5){
                  // List All Available Books by an Author
                  System.out.printf("Enter the Author's name:");
                  String athr = scn.nextLine();
                  query = String.format("SELECT DISTINCT Title from Book where Author = \"%s\" AND Available = 1",athr);
                  rs=stmt.executeQuery(query);
                  if(!rs.next()){
                     System.out.printf("There are no books available by author %s.\n",athr);
                     continue;
                  }
                  else{
                     System.out.printf("Books by %s:\n",athr);
                     System.out.println(rs.getString("Title"));
                  }
                  while(rs.next()){
                     System.out.println(rs.getString("Title"));
                  }
               }
               else if(n==6){
                  // List All Available Books by Genres
                  query="SELECT DISTINCT b.Title, g.Gname from Book b INNER JOIN Genre g ON b.Gcode=g.Gcode WHERE Available = 1 ORDER BY g.Gname";
                  rs=stmt.executeQuery(query);
                  System.out.println("|       Genre       |               Title               |");
                  System.out.println("---------------------------------------------------------");
                  while(rs.next()){
                     String gname=rs.getString("Gname");
                     System.out.printf("%s"+" ".repeat(20-gname.length()) +"%s\n",gname,rs.getString("Title"));
                  }
               }
               else if(n==7){
                  // Recommend Books by Favorite Genre
                  System.out.printf("Enter Your Favorite Genre:");
                  String gnr = scn.nextLine();
                  query = String.format("SELECT DISTINCT b.Title, b.Author from Book b INNER JOIN Genre g ON g.Gcode = b.Gcode where g.Gname = \"%s\" AND Available = 1",gnr);
                  rs=stmt.executeQuery(query);
                  if(!rs.next()){
                     System.out.printf("\nThere are no books available for the genre %s.\n",gnr);
                     continue;
                  }
                  else{
                     System.out.printf("\nBooks in genre %s:\n",gnr);
                     System.out.printf("%s  by  %s\n",rs.getString("Title"),rs.getString("Author"));
                  }
                  while(rs.next()){
                     System.out.printf("%s  by  %s\n",rs.getString("Title"),rs.getString("Author"));
                  }
               }
               else if(n==8){
                  // Issue Book
                  System.out.printf("Enter Student's RegNo:");
                  int RegNo = scn.nextInt();
                  scn.nextLine();
                  int flag1=0;
                  query="SELECT Regno from Student";
                  rs=stmt.executeQuery(query);
                  while(rs.next()) if(rs.getInt("Regno")==RegNo) flag1=1;
                  if (flag1==0){
                     System.out.println("No student with the given Regno exists.");
                     continue;
                  }

                  System.out.printf("Enter Book_No:");
                  int BNo = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter Book's CopyNo:");
                  int CNo = scn.nextInt();
                  scn.nextLine();
                  Boolean a=true;
                  query="SELECT Book_No,Copy_No,Available from Book";
                  rs=stmt.executeQuery(query);
                  while(rs.next()){
                     if(rs.getInt("Book_No")==BNo && rs.getInt("Copy_No")==CNo){
                        a=rs.getBoolean("Available");
                     }
                  }
                  
                  if (!a){
                     System.out.println("The book with the given Book_No and Copy_No has already been issued.");
                     continue;
                  }

                  int issueId = Math.floorDiv(RegNo,1000) * 1000 + Math.floorDiv(BNo, 1000) * 10 + CNo;
                  Date currDate = Date.valueOf(LocalDate.now());
                  Date retDate = Date.valueOf(LocalDate.now().plusDays(7));
                  query=String.format("INSERT INTO Issue_Table VALUES (%d,%d,%d,%d,\"%s\",\"%s\")", issueId,RegNo,BNo,CNo,currDate,retDate);
                  stmt.executeUpdate(query);
                  System.out.println("Book Issued Successfully.");

                  query=String.format("UPDATE book SET available = FALSE WHERE Book_No=%d AND Copy_No=%d",BNo,CNo);
                  stmt.executeUpdate(query);
                  conn.commit();
               }
               else if(n==9){
                  // Return Book
                  System.out.printf("Enter IssuerId (RegNo):");
                  int x = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter Book_No:");
                  int BNo = scn.nextInt();
                  scn.nextLine();
                  System.out.printf("Enter Copy_No:");
                  int CNo = scn.nextInt();
                  scn.nextLine();

                  query="SELECT Issuer_Id, Book_No, Copy_No from Issue_Table";
                  rs=stmt.executeQuery(query);
                  int flag1=0;
                  while(rs.next()) if(rs.getInt("Issuer_Id")==x && BNo==rs.getInt("Book_No") && CNo==rs.getInt("Copy_No")) flag1=1;
                     if (flag1==0){
                     System.out.printf("No book with bookID %d and copyId %d has been issued for the student with studentID %d.\n",BNo,CNo,x);
                     continue;
                  }

                  query=String.format("DELETE FROM Issue_Table WHERE Issuer_Id = %d",x);
                  stmt.executeUpdate(query);
                  System.out.println("Book Returned successfully.");

                  query=String.format("UPDATE Book SET Available = TRUE WHERE Book_No = %d AND Copy_No = %d", BNo,CNo);
                  stmt.executeUpdate(query);
                  conn.commit();
               }
               else if(n==10){
                  // List All Issued Books
                  query="SELECT * from Issue_Table";
                  rs=stmt.executeQuery(query);
                  if(!rs.next()) System.out.println("There are no books issued.");
                  else{
                     System.out.println("Issue_Id | Issuer_Id | Book_No | Copy_No | Borrow_Date | Return_Date");
                     System.out.printf("%d       %d        %d       %d      %s   %s\n",rs.getInt("Issue_Id"),rs.getInt("Issuer_Id"),rs.getInt("Book_No"),rs.getInt("Copy_No"),rs.getDate("Borrow_Date"),rs.getDate("Return_Date"));
                  }
                  while(rs.next()){
                     System.out.printf("%d       %d        %d       %d      %s   %s\n",rs.getInt("Issue_Id"),rs.getInt("Issuer_Id"),rs.getInt("Book_No"),rs.getInt("Copy_No"),rs.getDate("Borrow_Date"),rs.getDate("Return_Date"));
                  }
               }
               else if(n==11){
                  // Change System Password
                  System.out.printf("Enter existing password:");
                  String pwd=scn.nextLine();
                  query = String.format("SELECT Password from Admin WHERE Username = \"%s\"",Username);
                  rs=stmt.executeQuery(query);
                  int flag1=0;
                  while(rs.next()){
                     if(pwd.equals(rs.getString("Password"))) flag1=1;
                  }
                  if(flag1 == 0){
                     System.out.println("The entered password is wrong.");
                     continue;
                  }
                  System.out.printf("Enter new password:");
                  pwd = scn.nextLine();
                  query = String.format("UPDATE Admin SET Password = \"%s\" WHERE Username = \"%s\"",pwd,Username);
                  stmt.executeUpdate(query);
                  conn.commit();
                  System.out.println("Password changed successfully");
               }
               else if(n==12){   // Exit
                  break;
               }
               else System.out.println("Invalid Entry\n");
            }
         }
         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
         scn.close();
      } catch (SQLException se) { 
         // Handle errors for JDBC
         se.printStackTrace();
         // If there is an error then rollback the changes.
         System.out.println("Rolling back data here....");
         try{
            if(conn!=null)
            conn.rollback();
         }catch(SQLException se2){
	         System.out.println("Rollback failed....");
                 se2.printStackTrace();
         }
      } catch (Exception e) {
         // Handle errors for Class.forName
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
      } // end try
   } // end main
} // end class

