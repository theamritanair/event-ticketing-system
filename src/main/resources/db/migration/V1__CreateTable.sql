CREATE SCHEMA IF NOT EXISTS events_db;
CREATE TABLE IF NOT EXISTS events_db.users(
    id varchar(5) NOT NULL,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(25) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    wallet DECIMAL(10, 2) NOT NULL,
    CONSTRAINT unique_user_id UNIQUE (id),
    CONSTRAINT unique_user_email UNIQUE (email));
CREATE TABLE IF NOT EXISTS events_db.events(
    id uuid NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    event_start_date DATE NOT NULL,
    event_end_date DATE NOT NULL,
    total_tickets INT NOT NULL CHECK (total_tickets > 0),
    available_tickets INT NOT NULL CHECK (available_tickets >= 0),
    ticket_price DECIMAL(10, 2) NOT NULL CHECK (ticket_price >= 0),
    created_by varchar(5) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    status VARCHAR(25) NOT NULL,
    CONSTRAINT unique_event_id UNIQUE (id),
    CONSTRAINT unique_event_title UNIQUE (title),
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id),
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
