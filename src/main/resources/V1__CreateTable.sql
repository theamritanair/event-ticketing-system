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
