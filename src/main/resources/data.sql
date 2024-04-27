-- Inserting into orders table
INSERT INTO orders 
    (ID, DESCRIPTION, ORIGIN_ADDRESS, DESTINATION_ADDRESS, DISTANCE, STATUS, CREATION_DATE, VERSION) 
VALUES 
    (1, 'Pickup for Carlos', 'Address 1', 'Address 2', 16, 'UNASSIGNED', '2024-04-05', 1),
    (2, 'Delivery for Anna', 'Address 1', 'Address 2', 20, 'TAKEN', '2024-04-06', 2),
    (3, 'Pickup for John', 'Address 1', 'Address 2', 15, 'COMPLETED', '2024-04-07', 3);

-- Inserting into users table
INSERT INTO users 
    (ID, USERNAME, PASSWORD, ROLE) 
VALUES 
    (1, 'xa', 'xa', 'ADMIN');
