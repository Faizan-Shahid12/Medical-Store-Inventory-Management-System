CREATE DATABASE se_project;

USE se_project;

-- User Table (Base class for all users)
CREATE TABLE UserList (
	UserId INT NOT NULL auto_increment  PRIMARY KEY,
    Username VARCHAR(50) UNIQUE,
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
    UserId INT NOT NULL,
    LicenseNumber VARCHAR(50) NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (UserId) REFERENCES UserList(UserId) ON DELETE CASCADE
);

-- Pharmacist Table (Extends User)
CREATE TABLE Pharmacist (
    PharmaId INT AUTO_INCREMENT PRIMARY KEY,
    UserId INT NOT NULL,
    LicenseNumber VARCHAR(50) NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (UserId) REFERENCES UserList(UserId) ON DELETE CASCADE
);

-- Owner Table (Extends User)
CREATE TABLE Owner (
    OwnerId INT AUTO_INCREMENT PRIMARY KEY,
    UserId INT NOT NULL,
    FOREIGN KEY (UserId) REFERENCES UserList(UserId) ON DELETE CASCADE
);

-- Inventory Manager Table (Extends User)
CREATE TABLE InventoryManager (
    IMId INT AUTO_INCREMENT PRIMARY KEY,
    UserId INT NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    FOREIGN KEY (UserId) REFERENCES UserList(UserId) ON DELETE CASCADE
);

CREATE TABLE Prescription (
    PresciId INT PRIMARY KEY,
    Quantity INT NOT NULL,
    AddDate DATE NOT NULL,
    PatientName VARCHAR(100) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    DoctorName VARCHAR(100) NOT NULL,
    HospitalName VARCHAR(100) NOT NULL
);

CREATE TABLE SoldMedicine (
	SoldMedId Int auto_increment Primary Key,
    MedicineId INT Not Null,
    Name VARCHAR(100) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Expiry DATE NOT NULL,
    Manufactor DATE NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    SupplyStatus VARCHAR(50) DEFAULT 'Started',
    SupplierId INT Not Null,
    SupplierName VarChar(100) Not Null
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

Create Table PresMed (
	PresciId Int ,
    MedicineId Int,
    PresMedId Int primary Key auto_increment,
    FOREIGN KEY (PresciId) REFERENCES Prescription(PresciId) ON DELETE SET NULL,
    FOREIGN KEY (MedicineId) REFERENCES Medicine(MedicineId) ON DELETE SET NULL
    
);