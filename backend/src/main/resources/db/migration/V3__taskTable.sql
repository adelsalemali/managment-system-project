CREATE TABLE task (
    task_id SERIAL PRIMARY KEY,
    project_id INT,
    task_name VARCHAR(50) NOT NULL,
    task_description VARCHAR(50),
    due_date DATE,
    status VARCHAR(50),
    deleted BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (project_id)
        REFERENCES project(project_id)
        ON DELETE CASCADE
);