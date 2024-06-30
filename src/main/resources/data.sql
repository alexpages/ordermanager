-- Inserting into users table
INSERT INTO users 
    (ID, USERNAME, PASSWORD, ROLE) 
VALUES 
    (1, 'usuario', '$2a$10$BHIK9zzmlxC4gQ4WY9b4m.3DyRHks2B6h9cLGeT0vlnFmgWbh98NC', 'ADMIN');

-- Inserting into orders table
INSERT INTO orders 
    (ID, DESCRIPTION, ORIGIN_ADDRESS, DESTINATION_ADDRESS, DISTANCE, STATUS, CREATION_DATE, VERSION, USER_ID) 
VALUES 
    (1, 'Pickup for Carlos', 'Address 1', 'Address 2', 16, 'UNASSIGNED', '2024-04-05', 1, 1),
    (2, 'Delivery for Anna', 'Address 1', 'Address 2', 20, 'TAKEN', '2024-04-06', 2, 1),
    (3, 'Pickup for John', 'Address 1', 'Address 2', 15, 'COMPLETED', '2024-04-07', 3, 1);