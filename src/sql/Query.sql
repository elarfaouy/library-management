CREATE DATABASE IF NOT EXISTS library_dbb;

USE library_dbb;

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
	id INT AUTO_INCREMENT PRIMARY KEY,
	isbn VARCHAR(100) UNIQUE,
	title VARCHAR(250) NOT NULL,
	quantity INT,
	author_id INT,
	FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE copy_books (
	id INT AUTO_INCREMENT PRIMARY KEY,
	serial INT,
	status ENUM('available', 'on loan', 'lost') NOT NULL,
	book_id INT,
	FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE borrowing_transactions (
	id INT AUTO_INCREMENT PRIMARY KEY,
	borrow_date DATE NOT NULL,
	return_date DATE,
	due_date DATE NOT NULL,
	copy_book_id INT,
	client_id INT,
	FOREIGN KEY (copy_book_id) REFERENCES copy_books(id),
	FOREIGN KEY (client_id) REFERENCES clients(id)
);


INSERT INTO `authors` (`name`, `surname`) VALUES
    ('John', 'Doe'),
    ('Jane', 'Smith'),
    ('Michael', 'Johnson');

INSERT INTO `clients` (`name`, `surname`) VALUES
    ('Alice', 'Johnson'),
    ('Bob', 'Smith'),
    ('Eva', 'Davis');

INSERT INTO `books` (`isbn`, `title`, `quantity`, `author_id`) VALUES
    ('978-0451524935', 'Pride and Prejudice', 5, 2),
    ('978-1984801814', '1984', 3, 3),
    ('978-0061120084', 'To Kill a Mockingbird', 4, 1);

INSERT INTO `copy_books` (`serial`, `status`, `book_id`) VALUES
    (1, 'available', 1),
    (2, 'available', 1),
    (3, 'on loan', 2),
    (4, 'available', 3),
    (5, 'available', 3);

INSERT INTO `borrowing_transactions` (`borrow_date`, `return_date`, `due_date`, `copy_book_id`, `client_id`) VALUES
    ('2023-09-01', '2023-09-10', '2023-09-15', 1, 1),
    ('2023-09-02', NULL, '2023-09-20', 3, 2),
    ('2023-09-03', NULL, '2023-09-18', 4, 3);
