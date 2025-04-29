-- Insert User Data (Base class for all users)

DELETE FROM Medicine;
-- Insert Users
INSERT INTO UserList (UserId, Username, Password, Name, Gender, Age, Email, DOB)
VALUES
(1, 'sally_sup', 'pass123', 'Sally Supplier', 'Female', 35, 'sally@pharma.com', '1989-03-15'),
(2, 'phil_pharma', 'pass456', 'Phil Pharmacist', 'Male', 40, 'phil@pharma.com', '1985-06-22'),
(3, 'oliver_owner', 'ownerpass', 'Oliver Owner', 'Male', 45, 'oliver@pharma.com', '1980-01-10'),
(4, 'ivy_inv', 'invpass', 'Ivy Inventory', 'Female', 28, 'ivy@pharma.com', '1996-11-05'),
(5, 'jane_doe', 'janepass', 'Jane Doe', 'Female', 30, 'jane@pharma.com', '1993-08-30'),
(6, 'Faizan', 'Faizan123', 'Faizan Shahid', 'Male', 30, 'Faizan@pharma.com', '2004-11-17');

-- Insert Supplier Data (Extends User)
INSERT INTO Supplier (UserId, LicenseNumber, Status) VALUES
(1, 'SUP-98765', 'Approved'),
(5, 'SUP-11223', 'Pending');

-- Insert Pharmacist Data (Extends User)
INSERT INTO Pharmacist (UserId, LicenseNumber, Status) VALUES
(2, 'PHARMA-12345', 'Approved'),
(3, 'PHARMA-56789', 'Pending');
-- Insert Owner Data (Extends User)
INSERT INTO Owner (UserId) VALUES
(6);

-- Insert Inventory Manager Data (Extends User)
INSERT INTO InventoryManager (UserId, Status) VALUES
(4, 'Approved'),
(2, 'Pending');

-- Insert Medicine Data
INSERT INTO Medicine (Name, Type, Quantity, Price, Expiry, Manufactor, Status, SupplyStatus, SupplierId) VALUES
('Aspirin', 'Painkiller', 100, 10.50, '2026-12-31', '2023-03-01', 'Added', 'Started', 1),
('Paracetamol', 'Painkiller', 200, 5.20, '2025-08-15', '2023-04-01', 'Added', 'Started', 2),
('Ibuprofen', 'Anti-inflammatory', 150, 8.75, '2026-05-10', '2023-01-20', 'Approved', 'Started', 2),
('Amoxicillin', 'Antibiotic', 75, 20.00, '2026-06-20', '2022-12-15', 'Approved', 'Started', 1),
('Cough Syrup', 'Cough Medicine', 120, 12.00, '2026-02-28', '2023-02-10', 'Pending', 'Started', 2),
('Cough Syrup', 'Cough Medicine', 120, 12.00, '2026-02-28', '2023-02-10', 'Pending', 'Started', 1);

INSERT INTO Medicine (Name, Type, Quantity, Price, Expiry, Manufactor, Status, SupplyStatus, SupplierId) VALUES
('OldParacetamol', 'Tablet', 0, 12.99, '2024-12-15', '2022-12-10', 'Expired', 'Stopped', 1),
('ExpiredIbuprofen', 'Capsule', 0, 9.99, '2023-11-30', '2021-05-20', 'Expired', 'Stopped', 2),
('OutdatedAmoxicillin', 'Syrup', 10, 18.50, '2023-01-01', '2021-07-07', 'Expired', 'Stopped', 1),
('ExpiredVitaminC', 'Tablet', 5, 4.25, '2024-08-12', '2021-09-19', 'Expired', 'Stopped', 2),
('OldCetirizine', 'Tablet', 2, 6.75, '2023-02-28', '2020-04-05', 'Expired', 'Stopped', 1);


INSERT INTO Medicine (Name, Type, Quantity, Price, Expiry, Manufactor, Status, SupplyStatus, SupplierId) VALUES
('OldCetirizine', 'Tablet', 2, 6.75, '2023-02-28', '2020-04-05', 'Approved','Stopped', 6);

