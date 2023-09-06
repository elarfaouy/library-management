CREATE DATABASE IF NOT EXISTS library_db;

USE library_db;

CREATE TABLE authors (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL
);

CREATE TABLE clients (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL
);

CREATE TABLE books (
	isbn VARCHAR(100) PRIMARY KEY,
	title VARCHAR(250) NOT NULL,
	status ENUM('available', 'on loan', 'lost') NOT NULL,
	quantity INT,
	quantity_lost INT,
	author_id INT,
	FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE borrowed_books (
	id INT AUTO_INCREMENT PRIMARY KEY,
	borrow_date DATE NOT NULL,
	return_date DATE,
	due_date DATE NOT NULL,
	book_isbn VARCHAR(100),
	client_id INT,
	FOREIGN KEY (book_isbn) REFERENCES books(isbn),
	FOREIGN KEY (client_id) REFERENCES clients(id)
);


INSERT INTO authors (name, surname) VALUES
    ('John', 'Doe'),
    ('Jane', 'Smith'),
    ('David', 'Johnson'),
    ('Mary', 'Johnson'),
    ('Michael', 'Brown'),
    ('Sarah', 'Smith');

INSERT INTO clients (name, surname) VALUES
    ('Alice', 'Johnson'),
    ('Bob', 'Smith'),
    ('Eve', 'Davis'),
    ('Charlie', 'Davis'),
    ('Emma', 'Wilson'),
    ('James', 'Anderson');

INSERT INTO books (isbn, title, status, quantity, quantity_lost, author_id) VALUES
    ('ISBN-001', 'The domain.entities.Book of Java', 'available', 50, 0, 1),
    ('ISBN-002', 'Python Programming', 'on loan', 30, 5, 2),
    ('ISBN-003', 'SQL Mastery', 'available', 20, 0, 3),
    ('ISBN-004', 'JavaScript Essentials', 'lost', 10, 2, 1),
    ('ISBN-005', 'The Art of Programming', 'available', 15, 1, 4),
    ('ISBN-006', 'Data Science Fundamentals', 'available', 25, 0, 5),
    ('ISBN-007', 'Web Development Basics', 'on loan', 10, 3, 6),
    ('ISBN-008', 'Machine Learning Essentials', 'available', 18, 0, 4);

INSERT INTO borrowed_books (borrow_date, return_date, due_date, book_isbn, client_id) VALUES
    ('2023-09-01', '2023-09-10', '2023-09-15', 'ISBN-002', 1),
    ('2023-09-02', '2023-09-11', '2023-09-16', 'ISBN-001', 2),
    ('2023-09-03', NULL, '2023-09-14', 'ISBN-003', 3),
    ('2023-09-04', '2023-09-13', '2023-09-18', 'ISBN-005', 4),
    ('2023-09-05', NULL, '2023-09-15', 'ISBN-007', 5),
    ('2023-09-06', '2023-09-16', '2023-09-20', 'ISBN-008', 6),
    ('2023-09-07', NULL, '2023-09-17', 'ISBN-006', 4);
