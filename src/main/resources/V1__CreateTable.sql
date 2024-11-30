CREATE SCHEMA IF NOT EXISTS events_db;
CREATE TABLE IF NOT EXISTS events_db.events(
    id uuid NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    event_date DATE NOT NULL,
    total_tickets INT NOT NULL,
    available_tickets INT NOT NULL,
    ticket_price DECIMAL(10, 2) NOT NULL,
    created_by UUID NOT NULL,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id)
)

CREATE TABLE IF NOT EXISTS events_db.tickets(
    transaction_id uuid NOT NULL,
    event_id uuid NOT NULL,
    user_id varchar(5) NOT NULL,
    purchase_date DATE NOT NULL,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
)

CREATE TABLE IF NOT EXISTS events_db.users(
    id uuid NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL
    wallet DECIMAL(10, 2) NOT NULL
)