CREATE SCHEMA IF NOT EXISTS events_db;
CREATE TABLE IF NOT EXISTS events_db.users(
    id varchar(5) NOT NULL,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(25) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    wallet DECIMAL(10, 2) NOT NULL,
    role VARCHAR(25) NOT NULL,
    CONSTRAINT unique_user_id UNIQUE (id),
    CONSTRAINT unique_user_email UNIQUE (email));
CREATE TABLE IF NOT EXISTS events_db.events(
    id uuid NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    event_date DATE NOT NULL,
    total_tickets INT NOT NULL CHECK (total_tickets > 0),
    available_tickets INT NOT NULL CHECK (available_tickets >= 0),
    ticket_price DECIMAL(10, 2) NOT NULL CHECK (ticket_price >= 0),
    created_by varchar(5) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    status VARCHAR(25) NOT NULL,
    CONSTRAINT unique_event_id UNIQUE (id),
    CONSTRAINT unique_event_title UNIQUE (title),
    CONSTRAINT chk_available_tickets CHECK (available_tickets <= total_tickets)
);

CREATE TABLE IF NOT EXISTS events_db.tickets(
    transaction_id uuid NOT NULL,
    event_id uuid NOT NULL,
    user_id varchar(5) NOT NULL,
    purchase_date_time TIMESTAMP NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);



INSERT INTO events_db.users(id, name, email,username, created_at, wallet, role) VALUES ('U0012', 'Amrita', 'amrita@gmail.com', 'amrita','2021-01-01', 10000.00, 'USER');
INSERT INTO events_db.users(id, name, email, username, created_at, wallet, role) VALUES ('U0011', 'Jane', 'janedoe@hotmail.com', 'janedoe', '2021-01-01', 5000.00, 'USER');
INSERT INTO events_db.users(id, name, email, username, created_at, wallet, role) VALUES ('AD001', 'Admin 1', 'admin@hotmail.com','admin', '2021-01-01', 500.00, 'ADMIN');

INSERT INTO events_db.events(id, title, description, event_date, total_tickets, available_tickets, ticket_price, created_by, created_at, updated_at, status)
VALUES ('f7b3b8b3-1b3b-4b3b-8b3b-4b4b4b3b3b3c', 'Moana', 'The original Moana movie', '2024-12-01', 150, 100, 400.00, 'AD001', '2023-01-01 16:00:00', '2024-11-01 17:00:00', 'PUBLISHED');
INSERT INTO events_db.events(id, title, description, event_date, total_tickets, available_tickets, ticket_price, created_by, created_at, updated_at, status)
VALUES ('f7b3b3b3-1b1b-4b3b-8b3b-3b3b3b3b3b3c', 'Diljit Dosanjh - Dil Luminati Tour', '', '2024-12-02', 1000, 900, 2000.00, 'AD001', '2023-01-01 16:00:00', '2024-11-04 16:00:00', 'PUBLISHED');
INSERT INTO events_db.events(id, title, description, event_date, total_tickets, available_tickets, ticket_price, created_by, created_at, updated_at, status)
VALUES ('f7c3b8b3-1b2b-4b4b-8b6b-4b7b4b7b3b3c', 'Moana - 2', 'The sequel of Moana movie', '2026-12-01', 150, 100, 510.00, 'AD001', '2023-01-01 16:00:00', '2023-01-01 16:00:00', 'PUBLISHED');

