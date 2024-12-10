CREATE TABLE project (
    project_id SERIAL PRIMARY KEY,
    user_id INT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    start_date DATE,
    end_date DATE,
    status VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);