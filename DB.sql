CREATE DATABASE PharmacyDB;
USE PharmacyDB;

-- User Table (Base class for all users)
CREATE TABLE User (
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(255) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Gender VARCHAR(10),
    Age INT NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    DOB DATE NOT NULL
);

-- Supplier Table (Extends User)
CREATE TABLE Supplier (
    SupId INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) UNIQUE,
    LicenseNumber VARCHAR(50) NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (Username) REFERENCES User(Username) ON DELETE CASCADE
);

-- Pharmacist Table (Extends User)
CREATE TABLE Pharmacist (
    PharmaId INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) UNIQUE,
    LicenseNumber VARCHAR(50) NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (Username) REFERENCES User(Username) ON DELETE CASCADE
);

-- Owner Table (Extends User)
CREATE TABLE Owner (
    OwnerId INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) UNIQUE,
    FOREIGN KEY (Username) REFERENCES User(Username) ON DELETE CASCADE
);

-- Inventory Manager Table (Extends User)
CREATE TABLE InventoryManager (
    IMId INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) UNIQUE,
    Status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (Username) REFERENCES User(Username) ON DELETE CASCADE
);

-- Medicine Table
CREATE TABLE Medicine (
    MedicineId INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Expiry DATE NOT NULL,
    Manufactor DATE NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    SupplyStatus VARCHAR(50) DEFAULT 'Started',
    SupplierId INT,
    FOREIGN KEY (SupplierId) REFERENCES Supplier(SupId) ON DELETE SET NULL
);

-- Inventory Manager - Medicine Relationship (Inventory contains medicines)
CREATE TABLE Inventory (
    InventoryManagerId INT,
    MedicineId INT,
    PRIMARY KEY (InventoryManagerId, MedicineId),
    FOREIGN KEY (InventoryManagerId) REFERENCES InventoryManager(IMId) ON DELETE CASCADE,
    FOREIGN KEY (MedicineId) REFERENCES Medicine(MedicineId) ON DELETE CASCADE
);
