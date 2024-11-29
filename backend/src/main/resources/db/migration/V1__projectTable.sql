CREATE TABLE project (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    start_date DATE,
    end_date DATE,
    status VARCHAR(50),
    deleted TINYINT(1) DEFAULT 0,

    FOREIGN KEY (user_id)
    REFERENCES myUser(user_id)
    ON DELETE CASCADE
);
