USE vehicleinsurance;
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    PasswordHash VARCHAR(255),
    Address TEXT,
    DOB DATE,
    AadhaarNumber VARCHAR(12) UNIQUE,
    PANNumber VARCHAR(10),
    Role ENUM('User', 'Officer') DEFAULT 'User',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Vehicles (
    VehicleID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    VehicleType ENUM('Car', 'Bike', 'Camper Van', 'Truck'),
    RegistrationNumber VARCHAR(20) UNIQUE,
    ManufactureYear INT,
    Model VARCHAR(50),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Policies (
    PolicyID INT PRIMARY KEY AUTO_INCREMENT,
    PolicyName VARCHAR(100),
    Description TEXT,
    BasePremium DECIMAL(10,2),
    AddOnFeatures TEXT,
    CreatedBy INT,
    FOREIGN KEY (CreatedBy) REFERENCES Users(UserID)
);

CREATE TABLE Proposals (
    ProposalID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    VehicleID INT,
    PolicyID INT,
    Status ENUM('Proposal Submitted', 'Quote Generated', 'Active', 'Expired', 'Rejected'),
    SubmittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ApprovedAt TIMESTAMP NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (VehicleID) REFERENCES Vehicles(VehicleID),
    FOREIGN KEY (PolicyID) REFERENCES Policies(PolicyID)
);

CREATE TABLE Quotes (
    QuoteID INT PRIMARY KEY AUTO_INCREMENT,
    ProposalID INT,
    PremiumAmount DECIMAL(10,2),
    GeneratedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ProposalID) REFERENCES Proposals(ProposalID)
);

CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    QuoteID INT,
    PaymentDate DATE,
    AmountPaid DECIMAL(10,2),
    PaymentStatus ENUM('Pending', 'Completed'),
    FOREIGN KEY (QuoteID) REFERENCES Quotes(QuoteID)
);

CREATE TABLE Claims (
    ClaimID INT PRIMARY KEY AUTO_INCREMENT,
    PolicyID INT,
    ClaimDate DATE,
    Description TEXT,
    Status ENUM('Initiated', 'Under Review', 'Approved', 'Rejected'),
    FOREIGN KEY (PolicyID) REFERENCES Policies(PolicyID)
);

CREATE TABLE Documents (
    DocumentID INT PRIMARY KEY AUTO_INCREMENT,
    ProposalID INT,
    DocumentType VARCHAR(50),
    DocumentPath VARCHAR(255),
    UploadedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ProposalID) REFERENCES Proposals(ProposalID)
);

CREATE TABLE PolicyReminders (
    ReminderID INT PRIMARY KEY AUTO_INCREMENT,
    PolicyID INT,
    ReminderDate DATE,
    Sent BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (PolicyID) REFERENCES Policies(PolicyID)
);
