CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(25) NOT NULL UNIQUE,
    first_name VARCHAR(25),
    last_name VARCHAR(25),
    role VARCHAR(5),
    password VARCHAR(25) NOT NULL,
    deleted BOOLEAN DEFAULT FALSE
);
