CREATE DATABASE ParkingManagementSystem;
GO

USE ParkingManagementSystem;
GO

/* =========================================================
   ROLE & USER MANAGEMENT
========================================================= */
CREATE TABLE Roles
(
    RoleID      INT PRIMARY KEY IDENTITY (1,1),
    RoleName    NVARCHAR(50) UNIQUE NOT NULL,
    Description NVARCHAR(255)
);

CREATE TABLE Users
(
    UserID       INT PRIMARY KEY IDENTITY (1,1),
    FullName     NVARCHAR(100)        NOT NULL,
    Email        NVARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber  NVARCHAR(20),
    PasswordHash NVARCHAR(255)        NOT NULL,
    RoleID       INT                  NOT NULL,
    IsActive     BIT      DEFAULT 1,
    CreatedAt    DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Users_Roles
        FOREIGN KEY (RoleID) REFERENCES Roles (RoleID)
);

/* =========================================================
   BUILDING MANAGEMENT
========================================================= */
CREATE TABLE Buildings
(
    BuildingID         INT PRIMARY KEY IDENTITY (1,1),
    BuildingName       NVARCHAR(100) NOT NULL,
    Address            NVARCHAR(255),
    TotalFloors        INT,
    OperatingStartTime TIME,
    OperatingEndTime   TIME,
    CreatedAt          DATETIME DEFAULT GETDATE()
);

CREATE TABLE Floors
(
    FloorID     INT PRIMARY KEY IDENTITY (1,1),
    BuildingID  INT NOT NULL,
    FloorNumber INT NOT NULL,
    FloorName   NVARCHAR(50),
    CONSTRAINT FK_Floors_Buildings
        FOREIGN KEY (BuildingID) REFERENCES Buildings (BuildingID),
    CONSTRAINT UQ_Building_Floor UNIQUE (BuildingID, FloorNumber) -- Tránh trùng tầng trong 1 tòa nhà
);

CREATE TABLE Zones
(
    ZoneID      INT PRIMARY KEY IDENTITY (1,1),
    FloorID     INT          NOT NULL,
    ZoneName    NVARCHAR(50) NOT NULL,
    Description NVARCHAR(255),
    CONSTRAINT FK_Zones_Floors
        FOREIGN KEY (FloorID) REFERENCES Floors (FloorID),
    CONSTRAINT UQ_Floor_Zone UNIQUE (FloorID, ZoneName) -- Tránh trùng Zone trong 1 tầng
);

/* =========================================================
   VEHICLE TYPES
========================================================= */
CREATE TABLE VehicleTypes
(
    VehicleTypeID INT PRIMARY KEY IDENTITY (1,1),
    TypeName      NVARCHAR(50) NOT NULL UNIQUE,
    Description   NVARCHAR(255)
    -- ĐÃ XÓA PricingPolicyID ở đây để tránh lỗi vòng lặp (Circular Dependency)
);

CREATE TABLE Vehicles
(
    VehicleID     INT PRIMARY KEY IDENTITY (1,1),
    LicensePlate  NVARCHAR(20) NOT NULL UNIQUE,
    VehicleTypeID INT          NOT NULL,
    OwnerName     NVARCHAR(100),
    OwnerPhone    NVARCHAR(20),
    CONSTRAINT FK_Vehicles_VehicleTypes
        FOREIGN KEY (VehicleTypeID) REFERENCES VehicleTypes (VehicleTypeID)
);

/* =========================================================
   PARKING SLOT MANAGEMENT
========================================================= */
CREATE TABLE ParkingSlots
(
    SlotID        INT PRIMARY KEY IDENTITY (1,1),
    ZoneID        INT          NOT NULL,
    SlotCode      NVARCHAR(20) NOT NULL UNIQUE,
    VehicleTypeID INT          NOT NULL,
    Status        NVARCHAR(20) NOT NULL
        CHECK (Status IN ('AVAILABLE', 'OCCUPIED', 'RESERVED', 'LOCKED')),
    IsActive      BIT DEFAULT 1,
    CONSTRAINT FK_ParkingSlots_Zones
        FOREIGN KEY (ZoneID) REFERENCES Zones (ZoneID),
    CONSTRAINT FK_ParkingSlots_VehicleTypes
        FOREIGN KEY (VehicleTypeID) REFERENCES VehicleTypes (VehicleTypeID)
);

/* =========================================================
   PRICING POLICY
========================================================= */
CREATE TABLE PricingPolicies
(
    PricingPolicyID    INT PRIMARY KEY IDENTITY (1,1),
    VehicleTypeID      INT            NOT NULL,
    PolicyName         NVARCHAR(100),
    BasePrice          DECIMAL(10, 2) NOT NULL,
    PricePerHour       DECIMAL(10, 2) NOT NULL,
    MaxDailyRate       DECIMAL(10, 2),
    LostTicketFee      DECIMAL(10, 2),
    OvertimeFeePerHour DECIMAL(10, 2),
    EffectiveFrom      DATETIME       NOT NULL,
    EffectiveTo        DATETIME,
    CONSTRAINT FK_PricingPolicies_VehicleTypes
        FOREIGN KEY (VehicleTypeID) REFERENCES VehicleTypes (VehicleTypeID)
);

/* =========================================================
   PARKING SESSION
========================================================= */
CREATE TABLE ParkingSessions
(
    SessionID    INT PRIMARY KEY IDENTITY (1,1),
    VehicleID    INT          NOT NULL,
    SlotID       INT          NOT NULL,
    EntryTime    DATETIME     NOT NULL DEFAULT GETDATE(),
    ExitTime     DATETIME     NULL,
    EntryGate    NVARCHAR(50),
    ExitGate     NVARCHAR(50),
    Status       NVARCHAR(20) NOT NULL
        CHECK (Status IN ('PARKING', 'COMPLETED', 'LOST_TICKET', 'UNPAID', 'VIOLATION')),
    EstimatedFee DECIMAL(10, 2),
    FinalFee     DECIMAL(10, 2),
    CreatedBy    INT,
    CONSTRAINT FK_ParkingSessions_Vehicles
        FOREIGN KEY (VehicleID) REFERENCES Vehicles (VehicleID),
    CONSTRAINT FK_ParkingSessions_Slots
        FOREIGN KEY (SlotID) REFERENCES ParkingSlots (SlotID),
    CONSTRAINT FK_ParkingSessions_Users
        FOREIGN KEY (CreatedBy) REFERENCES Users (UserID)
);

/* =========================================================
   RESERVATION SYSTEM
========================================================= */
CREATE TABLE Reservations
(
    ReservationID    INT PRIMARY KEY IDENTITY (1,1),
    UserID           INT      NOT NULL,
    VehicleTypeID    INT      NOT NULL,
    SlotID           INT      NULL,
    ReservationStart DATETIME NOT NULL,
    ReservationEnd   DATETIME NOT NULL,
    Status           NVARCHAR(20)
        CHECK (Status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'EXPIRED')),
    CreatedAt        DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Reservations_Users
        FOREIGN KEY (UserID) REFERENCES Users (UserID),
    CONSTRAINT FK_Reservations_VehicleTypes
        FOREIGN KEY (VehicleTypeID) REFERENCES VehicleTypes (VehicleTypeID),
    CONSTRAINT FK_Reservations_Slots
        FOREIGN KEY (SlotID) REFERENCES ParkingSlots (SlotID)
);

/* =========================================================
   PAYMENT MANAGEMENT
========================================================= */
CREATE TABLE Payments
(
    PaymentID     INT PRIMARY KEY IDENTITY (1,1),
    SessionID     INT            NOT NULL,
    Amount        DECIMAL(10, 2) NOT NULL,
    PaymentMethod NVARCHAR(30)
        CHECK (PaymentMethod IN ('CASH', 'BANK_TRANSFER', 'E_WALLET', 'CREDIT_CARD')),
    PaymentStatus NVARCHAR(20)
        CHECK (PaymentStatus IN ('PENDING', 'PAID', 'FAILED')),
    PaidAt        DATETIME,
    CONSTRAINT FK_Payments_Sessions
        FOREIGN KEY (SessionID) REFERENCES ParkingSessions (SessionID)
);

/* =========================================================
   INCIDENT MANAGEMENT
========================================================= */
CREATE TABLE IncidentReports
(
    IncidentID   INT PRIMARY KEY IDENTITY (1,1),
    SessionID    INT NULL,
    ReportedBy   INT NOT NULL,
    IncidentType NVARCHAR(50)
        CHECK (IncidentType IN
               ('LOST_TICKET', 'WRONG_LICENSE_PLATE', 'OVERTIME', 'WRONG_ZONE', 'UNPAID', 'SLOT_OCCUPIED', 'OTHER')),
    Description  NVARCHAR(500),
    Status       NVARCHAR(20)
        CHECK (Status IN ('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED')),
    CreatedAt    DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_IncidentReports_Sessions
        FOREIGN KEY (SessionID) REFERENCES ParkingSessions (SessionID),
    CONSTRAINT FK_IncidentReports_Users
        FOREIGN KEY (ReportedBy) REFERENCES Users (UserID)
);

/* =========================================================
   AI OPTIMIZATION SUPPORT
========================================================= */
CREATE TABLE ParkingPredictions
(
    PredictionID           INT PRIMARY KEY IDENTITY (1,1),
    VehicleTypeID          INT,
    FloorID                INT,
    PredictedOccupancyRate DECIMAL(5, 2),
    PredictedPeakHour      INT,
    PredictionDate         DATE,
    GeneratedAt            DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_ParkingPredictions_VehicleTypes
        FOREIGN KEY (VehicleTypeID) REFERENCES VehicleTypes (VehicleTypeID),
    CONSTRAINT FK_ParkingPredictions_Floors
        FOREIGN KEY (FloorID) REFERENCES Floors (FloorID)
);
GO