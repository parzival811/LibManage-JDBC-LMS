CREATE DATABASE IF NOT EXISTS librarydb;
USE librarydb;

CREATE TABLE Genre (
	Gcode char(4) NOT NULL PRIMARY KEY,
	Gname varchar(30) NOT NULL,
	Gtype char(11) NOT NULL, 
	CHECK (Gtype IN ("Fiction","Non-Fiction")) );


CREATE TABLE Book (
	Book_No int NOT NULL,
	Copy_No int NOT NULL,
	Title varchar(70) NOT NULL,
	Author varchar(30) NOT NULL,
	Publisher varchar(30),
	Gcode char(4) NOT NULL,
	Price int,
	Isbn bigint,
	Edition year(4),
	Available boolean DEFAULT (TRUE),
	PRIMARY KEY (Book_No, Copy_No),
	FOREIGN KEY Book(Gcode) REFERENCES Genre(Gcode));

CREATE TABLE Student (
	Regno int NOT NULL PRIMARY KEY,
	Fname varchar(30) NOT NULL,
	Lname varchar(30) );

CREATE TABLE Issue_Table (
	Issue_Id int NOT NULL PRIMARY KEY,
	Issuer_Id int NOT NULL,
	Book_No int NOT NULL,
	Copy_No int NOT NULL,
	Borrow_Date date NOT NULL,
	Return_Date date NOT NULL,
	FOREIGN KEY (Book_No, Copy_No) REFERENCES Book(Book_No, Copy_No),
	FOREIGN KEY (Issuer_Id) REFERENCES Student(Regno));
	

CREATE TABLE Admin (
	User_Id int NOT NULL PRIMARY KEY,
	Username varchar(30) NOT NULL,
	Designation varchar(20) NOT NULL DEFAULT ("Administrator"),
	Password varchar(40) NOT NULL );


# Inserting data into admin table

INSERT INTO Admin
VALUES (811, "Percy811", "Adminstrator", "root") ;

# Inserting data into genre table

INSERT INTO Genre
VALUES ('A589', "Action & Adventure","Fiction"),
('R753',"Anthology","Fiction"),
('I750', "Comic book", "Fiction"),
('Q894', "Drama", "Fiction"),
("K167","Fantasy", "Fiction"),
("X398", "Mystery", "Fiction"),
("V266", "Science fiction", "Fiction"),
("G404", "Horror", "Fiction"),
("X818", "Autobiography", "Non-Fiction"),
("T427", "Business/economics", "Non-Fiction"),
("K692","Encyclopedia", "Non-Fiction"),
("C134", "History", "Non-Fiction"),
("L989", "Textbook", "Non-Fiction"),
("F261", "Science", "Non-Fiction") ;

# Inserting data into book table

INSERT INTO Book (Book_No, Copy_No, Title, Author, Publisher, Gcode, Price, Isbn, Edition)
VALUES (94082, 1, "The Hobbit", "J.R.R. Tolkien", "Pearson","K167",560,9053019069259,2004),
(42373, 1, "Harry Potter And The Philosopher's Stone", "J. K. Rowling", "Bloomsbury","K167",1115,2175018308108,2009),
(42373, 2, "Harry Potter And The Philosopher's Stone", "J. K. Rowling", "Bloomsbury","K167",1115,2175018308108,2009),
(17200, 1, "The Da Vinci Code", "Dan Brown", "Simon","X398",595,6353522554582,2014),
(35398, 1, "The Alchemist", "Paulo Coelho", "Pearson","K167",380,1010913124214,2010),
(92606, 1, "Angels And Demons", "Dan Brown", "Simon","X398",1280,9279022105814,2020),
(19783, 1, "The Diary Of Anne Frank", "Anne Frank", "Contact","X818",410,5815478731701,1991),
(11211, 1, "The Lost Symbol", "Dan Brown", "Simon","X398",1450,3764938760849,2021),
(66797, 1, "A Brief History Of Time", "Stephen Hawking", "Bantam Dell","F261",850,7618738350357,2007),
(66211, 1, "Charlie And The Chocolate Factory", "Roald Dahl", "Pearson","K167",760,6768119609329,2005),
(50816, 1, "Mein Kampf", "Adolf Hitler", "Franz","X818",1405,9488141987254,2014),
(79693, 1, "The Maze Runner", "James Dashner", "Delacourt","V266",1985,8135345715300,2007),
(86698, 1, "The Hunger Games", "Suzanne Collins", "Scholastic","V266",755,4563562806558,2000),
(86698, 2, "The Hunger Games", "Suzanne Collins", "Scholastic","V266",755,4563562806558,2000),
(15288, 1, "Divergent", "Veronica Roth", "Scholastic","V266",385,2337174022204,2012),
(80857, 1, "It", "Stephen King", "Pearson","G404",660,8357097764286,2016),
(80857, 2, "It", "Stephen King", "Pearson","G404",660,8357097764286,2016),
(81312, 1, "The Haunting Of Hill House", "Shirley Jackson","Pearson","G404",1410,4666161599904,2018);

# Inserting data into student table

INSERT INTO Student
VALUES (16434, "Rahul", "Singh"), 
(50865, "Aman", "Srivastava"), 
(20881, "Naveen", "Mishra"),
(45305,"Shivansh", "Trivedi"),
(34029, "Richa", "Singh"), 
(19496, "Ragini", "Sharma"),
 (60809, "Raman", "Yadav"),
 (11466, "Kaveri", "Singh"),
(64228, "Aditi", "Yadav"),
(61368, "Suman", "Srivastava") ;