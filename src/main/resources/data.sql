-- Inserting into orders table
INSERT INTO orders 
    (ID, DESCRIPTION, DISTANCE, STATUS, CREATION_DATE, VERSION) 
VALUES 
    (2, 'Pickup for Carlos', 16, 'UNASSIGNED', '2024-04-05', 1),
    (3, 'Delivery for Anna', 20, 'TAKEN', '2024-04-06', 2),
    (4, 'Pickup for John', 15, 'COMPLETED', '2024-04-07', 3),
    (5, 'Delivery for Emily', 25, 'UNASSIGNED', '2024-04-08', 4),
    (6, 'Pickup for David', 18, 'TAKEN', '2024-04-09', 5),
    (7, 'Delivery for Sarah', 22, 'COMPLETED', '2024-04-10', 6),
    (8, 'Pickup for Michael', 17, 'UNASSIGNED', '2024-04-11', 7),
    (9, 'Delivery for Olivia', 30, 'TAKEN', '2024-04-12', 8),
    (10, 'Pickup for Sophia', 19, 'COMPLETED', '2024-04-13', 9),
    (11, 'Delivery for Ethan', 28, 'UNASSIGNED', '2024-04-14', 10),
    (12, 'Pickup for Emma', 21, 'TAKEN', '2024-04-15', 11),
    (13, 'Delivery for Noah', 35, 'COMPLETED', '2024-04-16', 12);

-- Inserting into users table
INSERT INTO users 
    (ID, USERNAME, PASSWORD, ROLE) 
VALUES 
    (1, 'xa', 'xa', 'ADMIN');
