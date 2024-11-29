CREATE TABLE myUser (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(25) NOT NULL,
    first_name VARCHAR(25),
    last_name VARCHAR(25),
    role VARCHAR(5),
    deleted TINYINT(1) DEFAULT 0
);