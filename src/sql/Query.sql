CREATE DATABASE IF NOT EXISTS library_dbd;

USE library_dbd;

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
	FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

CREATE TABLE book_copies (
	id INT AUTO_INCREMENT PRIMARY KEY,
	serial INT,
	status ENUM('available', 'on loan', 'lost') NOT NULL,
	book_id INT,
	FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

CREATE TABLE borrowing_transactions (
	id INT AUTO_INCREMENT PRIMARY KEY,
	borrow_date DATE NOT NULL,
	return_date DATE,
	due_date DATE NOT NULL,
	book_copy_id INT,
	client_id INT,
	FOREIGN KEY (book_copy_id) REFERENCES book_copies(id) ON DELETE CASCADE,
	FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
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
    ('78-04', 'Pride and Prejudice', 5, 2),
    ('97-19', '1984', 3, 3),
    ('98-00', 'To Kill a Mockingbird', 4, 1);

INSERT INTO `book_copies` (`serial`, `status`, `book_id`) VALUES
    (1, 'available', 1),
    (2, 'available', 1),
    (3, 'on loan', 2),
    (4, 'available', 3),
    (5, 'available', 3);

INSERT INTO `borrowing_transactions` (`borrow_date`, `return_date`, `due_date`, `book_copy_id`, `client_id`) VALUES
    ('2023-09-01', '2023-09-10', '2023-09-15', 1, 1),
    ('2023-09-02', NULL, '2023-09-20', 3, 2),
    ('2023-09-03', NULL, '2023-09-18', 4, 3);


DELIMITER //

CREATE PROCEDURE isAnyCopyExist(
    IN isbnParam VARCHAR(50),
    OUT idCopyAvailable INT
)
BEGIN
    DECLARE copy_id INT;

    SELECT c.id INTO copy_id 
    FROM book_copies AS c
    INNER JOIN books AS b ON c.book_id = b.id
    WHERE b.isbn = isbnParam AND c.`status` = 'available'
    LIMIT 1;
    
    SET idCopyAvailable = copy_id;
END;

CREATE TRIGGER addBookCopies
AFTER INSERT ON books
FOR EACH ROW
BEGIN
    DECLARE i INT;
    SET i = 1;
    
    WHILE i <= NEW.quantity DO
        INSERT INTO book_copies (book_id, serial) VALUES (NEW.isbn, FLOOR(1000 + RAND() * 9000));
        SET i = i + 1;
    END WHILE;
END;

CREATE EVENT `updateCopyStatus`
ON SCHEDULE EVERY 1 DAY
COMMENT ''
DO BEGIN
    DECLARE currentDate DATE;
    SET currentDate = CURDATE();
    
    UPDATE book_copies
    SET status = 'lost'
    WHERE id IN (
        SELECT bc.id
        FROM book_copies AS bc
        INNER JOIN borrowing_transactions AS bt ON bc.id = bt.book_copy_id
        WHERE bt.return_date IS NULL AND bt.due_date < currentDate
    );
END;

CREATE EVENT `updateBookQuantity`
ON SCHEDULE EVERY 1 DAY
COMMENT ''
DO BEGIN
    UPDATE books AS b
    SET b.quantity = (
        SELECT COUNT(*)
        FROM book_copies AS bc
        WHERE bc.book_id = b.id
    );
END;

//
DELIMITER ;
