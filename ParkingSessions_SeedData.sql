USE ParkingManagementSystem;
GO

/*
    ParkingSessions sample data
    -------------------------
    - Non-destructive: no DELETE, TRUNCATE, or identity reset.
    - Rerunnable: records are guarded by stable unique values.
    - Creates 10 users, 10 slots, 10 vehicles, and 10 parking sessions.
    - Dedicated structural records avoid collisions in the shared database.
    - PasswordHash is test data only and is not a usable login password.
*/

SET XACT_ABORT ON;
BEGIN TRANSACTION;

/* =========================================================
   ROLES
========================================================= */
IF NOT EXISTS (SELECT 1 FROM Roles WHERE RoleName = N'ADMIN')
    INSERT INTO Roles (RoleName, Description)
    VALUES (N'ADMIN', N'Parking system administrator');

IF NOT EXISTS (SELECT 1 FROM Roles WHERE RoleName = N'CUSTOMER')
    INSERT INTO Roles (RoleName, Description)
    VALUES (N'CUSTOMER', N'Registered parking customer');

DECLARE @AdminRoleID INT = (SELECT RoleID FROM Roles WHERE RoleName = N'ADMIN');
DECLARE @CustomerRoleID INT = (SELECT RoleID FROM Roles WHERE RoleName = N'CUSTOMER');

/* =========================================================
   10 USERS: 1 ADMIN + 9 CUSTOMERS
========================================================= */
IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'admin@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'System Administrator', N'admin@parking.local', N'0901000001', N'NOT_FOR_LOGIN', @AdminRoleID, 1, '2026-06-01T08:00:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer01@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Nguyen Minh Anh', N'customer01@parking.local', N'0901000002', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:05:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer02@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Tran Gia Bao', N'customer02@parking.local', N'0901000003', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:10:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer03@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Le Hoang Chau', N'customer03@parking.local', N'0901000004', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:15:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer04@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Pham Ngoc Dung', N'customer04@parking.local', N'0901000005', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:20:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer05@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Vo Thanh Ha', N'customer05@parking.local', N'0901000006', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:25:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer06@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Do Quang Huy', N'customer06@parking.local', N'0901000007', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:30:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer07@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Bui Khanh Linh', N'customer07@parking.local', N'0901000008', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:35:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer08@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Hoang Tuan Nam', N'customer08@parking.local', N'0901000009', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:40:00');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = N'customer09@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive, CreatedAt)
    VALUES (N'Ngo Phuong Thao', N'customer09@parking.local', N'0901000010', N'NOT_FOR_LOGIN', @CustomerRoleID, 1, '2026-06-01T08:45:00');

/* =========================================================
   VEHICLE TYPES
========================================================= */
IF NOT EXISTS (SELECT 1 FROM VehicleTypes WHERE TypeName = N'CAR')
    INSERT INTO VehicleTypes (TypeName, Description) VALUES (N'CAR', N'Passenger car');

IF NOT EXISTS (SELECT 1 FROM VehicleTypes WHERE TypeName = N'MOTORBIKE')
    INSERT INTO VehicleTypes (TypeName, Description) VALUES (N'MOTORBIKE', N'Motorbike or scooter');

IF NOT EXISTS (SELECT 1 FROM VehicleTypes WHERE TypeName = N'TRUCK')
    INSERT INTO VehicleTypes (TypeName, Description) VALUES (N'TRUCK', N'Truck or large vehicle');

DECLARE @CarTypeID INT = (SELECT VehicleTypeID FROM VehicleTypes WHERE TypeName = N'CAR');
DECLARE @MotorbikeTypeID INT = (SELECT VehicleTypeID FROM VehicleTypes WHERE TypeName = N'MOTORBIKE');
DECLARE @TruckTypeID INT = (SELECT VehicleTypeID FROM VehicleTypes WHERE TypeName = N'TRUCK');

/* =========================================================
   BUILDING -> FLOOR -> ZONES
========================================================= */
IF NOT EXISTS (SELECT 1 FROM Buildings WHERE BuildingName = N'University Parking Building')
    INSERT INTO Buildings
        (BuildingName, Address, TotalFloors, OperatingStartTime, OperatingEndTime, CreatedAt)
    VALUES
        (N'University Parking Building', N'University Campus Parking Area', 1, '06:00', '23:00', '2026-06-01T07:00:00');

DECLARE @BuildingID INT = (
    SELECT TOP (1) BuildingID
    FROM Buildings
    WHERE BuildingName = N'University Parking Building'
);

IF NOT EXISTS (SELECT 1 FROM Floors WHERE BuildingID = @BuildingID AND FloorNumber = 1)
    INSERT INTO Floors (BuildingID, FloorNumber, FloorName)
    VALUES (@BuildingID, 1, N'Ground Floor');

DECLARE @FloorID INT = (
    SELECT FloorID
    FROM Floors
    WHERE BuildingID = @BuildingID AND FloorNumber = 1
);

IF NOT EXISTS (SELECT 1 FROM Zones WHERE FloorID = @FloorID AND ZoneName = N'Car Zone')
    INSERT INTO Zones (FloorID, ZoneName, Description)
    VALUES (@FloorID, N'Car Zone', N'Parking slots for passenger cars');

IF NOT EXISTS (SELECT 1 FROM Zones WHERE FloorID = @FloorID AND ZoneName = N'Bike Zone')
    INSERT INTO Zones (FloorID, ZoneName, Description)
    VALUES (@FloorID, N'Bike Zone', N'Parking slots for motorbikes');

IF NOT EXISTS (SELECT 1 FROM Zones WHERE FloorID = @FloorID AND ZoneName = N'Truck Zone')
    INSERT INTO Zones (FloorID, ZoneName, Description)
    VALUES (@FloorID, N'Truck Zone', N'Parking slots for trucks');

DECLARE @CarZoneID INT = (SELECT ZoneID FROM Zones WHERE FloorID = @FloorID AND ZoneName = N'Car Zone');
DECLARE @BikeZoneID INT = (SELECT ZoneID FROM Zones WHERE FloorID = @FloorID AND ZoneName = N'Bike Zone');
DECLARE @TruckZoneID INT = (SELECT ZoneID FROM Zones WHERE FloorID = @FloorID AND ZoneName = N'Truck Zone');

/* =========================================================
   10 PARKING SLOTS: 4 CAR + 4 MOTORBIKE + 2 TRUCK
========================================================= */
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'CAR-01')
    INSERT INTO ParkingSlots VALUES (@CarZoneID, N'CAR-01', @CarTypeID, N'OCCUPIED', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'CAR-02')
    INSERT INTO ParkingSlots VALUES (@CarZoneID, N'CAR-02', @CarTypeID, N'AVAILABLE', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'CAR-03')
    INSERT INTO ParkingSlots VALUES (@CarZoneID, N'CAR-03', @CarTypeID, N'AVAILABLE', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'CAR-04')
    INSERT INTO ParkingSlots VALUES (@CarZoneID, N'CAR-04', @CarTypeID, N'LOCKED', 0);

IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'BIKE-01')
    INSERT INTO ParkingSlots VALUES (@BikeZoneID, N'BIKE-01', @MotorbikeTypeID, N'OCCUPIED', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'BIKE-02')
    INSERT INTO ParkingSlots VALUES (@BikeZoneID, N'BIKE-02', @MotorbikeTypeID, N'AVAILABLE', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'BIKE-03')
    INSERT INTO ParkingSlots VALUES (@BikeZoneID, N'BIKE-03', @MotorbikeTypeID, N'RESERVED', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'BIKE-04')
    INSERT INTO ParkingSlots VALUES (@BikeZoneID, N'BIKE-04', @MotorbikeTypeID, N'AVAILABLE', 1);

IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'TRUCK-01')
    INSERT INTO ParkingSlots VALUES (@TruckZoneID, N'TRUCK-01', @TruckTypeID, N'OCCUPIED', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = N'TRUCK-02')
    INSERT INTO ParkingSlots VALUES (@TruckZoneID, N'TRUCK-02', @TruckTypeID, N'AVAILABLE', 1);

/* =========================================================
   10 VEHICLES
========================================================= */
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'51A-M0011')
    INSERT INTO Vehicles VALUES (N'51A-M0011', @CarTypeID, N'Nguyen Minh Anh', N'0901000002');
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'51A-M0022')
    INSERT INTO Vehicles VALUES (N'51A-M0022', @CarTypeID, N'Tran Gia Bao', N'0901000003');
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'51A-M0033')
    INSERT INTO Vehicles VALUES (N'51A-M0033', @CarTypeID, N'Le Hoang Chau', N'0901000004');
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'51A-M0044')
    INSERT INTO Vehicles VALUES (N'51A-M0044', @CarTypeID, N'Pham Ngoc Dung', N'0901000005');

IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'59M1-100.11')
    INSERT INTO Vehicles VALUES (N'59M1-100.11', @MotorbikeTypeID, N'Vo Thanh Ha', N'0901000006');
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'59M1-200.22')
    INSERT INTO Vehicles VALUES (N'59M1-200.22', @MotorbikeTypeID, N'Do Quang Huy', N'0901000007');
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'59M1-300.33')
    INSERT INTO Vehicles VALUES (N'59M1-300.33', @MotorbikeTypeID, N'Bui Khanh Linh', N'0901000008');
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'59M1-400.44')
    INSERT INTO Vehicles VALUES (N'59M1-400.44', @MotorbikeTypeID, N'Hoang Tuan Nam', N'0901000009');

IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'51D-M0088')
    INSERT INTO Vehicles VALUES (N'51D-M0088', @TruckTypeID, N'Ngo Phuong Thao', N'0901000010');
IF NOT EXISTS (SELECT 1 FROM Vehicles WHERE LicensePlate = N'51D-M0099')
    INSERT INTO Vehicles VALUES (N'51D-M0099', @TruckTypeID, N'Guest Driver', N'0901999999');

/* Resolve stable keys for session inserts. */
DECLARE @Customer01 INT = (SELECT UserID FROM Users WHERE Email = N'customer01@parking.local');
DECLARE @Customer02 INT = (SELECT UserID FROM Users WHERE Email = N'customer02@parking.local');
DECLARE @Customer03 INT = (SELECT UserID FROM Users WHERE Email = N'customer03@parking.local');
DECLARE @Customer04 INT = (SELECT UserID FROM Users WHERE Email = N'customer04@parking.local');
DECLARE @Customer05 INT = (SELECT UserID FROM Users WHERE Email = N'customer05@parking.local');
DECLARE @Customer06 INT = (SELECT UserID FROM Users WHERE Email = N'customer06@parking.local');
DECLARE @Customer07 INT = (SELECT UserID FROM Users WHERE Email = N'customer07@parking.local');
DECLARE @Customer08 INT = (SELECT UserID FROM Users WHERE Email = N'customer08@parking.local');
DECLARE @Customer09 INT = (SELECT UserID FROM Users WHERE Email = N'customer09@parking.local');

/* =========================================================
   10 FULL PARKING SESSIONS
   The last session is a guest session (CreatedBy = NULL).
========================================================= */
IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'51A-M0011' AND ps.EntryTime = '2026-06-20T07:30:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-20T07:30:00', NULL, N'North Gate', NULL,
           N'PARKING', 45000.00, NULL, @Customer01
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'51A-M0011' AND s.SlotCode = N'CAR-01';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'51A-M0022' AND ps.EntryTime = '2026-06-18T08:00:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-18T08:00:00', '2026-06-18T11:15:00', N'North Gate', N'South Gate',
           N'COMPLETED', 30000.00, 35000.00, @Customer02
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'51A-M0022' AND s.SlotCode = N'CAR-02';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'51A-M0033' AND ps.EntryTime = '2026-06-17T13:20:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-17T13:20:00', '2026-06-17T18:05:00', N'East Gate', N'East Gate',
           N'COMPLETED', 50000.00, 55000.00, @Customer03
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'51A-M0033' AND s.SlotCode = N'CAR-03';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'51A-M0044' AND ps.EntryTime = '2026-06-16T09:45:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-16T09:45:00', '2026-06-16T12:10:00', N'West Gate', N'South Gate',
           N'VIOLATION', 25000.00, 75000.00, @Customer04
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'51A-M0044' AND s.SlotCode = N'CAR-04';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'59M1-100.11' AND ps.EntryTime = '2026-06-20T06:50:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-20T06:50:00', NULL, N'East Gate', NULL,
           N'PARKING', 18000.00, NULL, @Customer05
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'59M1-100.11' AND s.SlotCode = N'BIKE-01';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'59M1-200.22' AND ps.EntryTime = '2026-06-15T10:00:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-15T10:00:00', '2026-06-15T12:30:00', N'East Gate', N'West Gate',
           N'COMPLETED', 12000.00, 15000.00, @Customer06
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'59M1-200.22' AND s.SlotCode = N'BIKE-02';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'59M1-300.33' AND ps.EntryTime = '2026-06-14T14:10:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-14T14:10:00', '2026-06-14T17:40:00', N'North Gate', N'North Gate',
           N'LOST_TICKET', 15000.00, 65000.00, @Customer07
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'59M1-300.33' AND s.SlotCode = N'BIKE-03';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'59M1-400.44' AND ps.EntryTime = '2026-06-13T18:00:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-13T18:00:00', '2026-06-13T21:20:00', N'South Gate', N'South Gate',
           N'UNPAID', 15000.00, 18000.00, @Customer08
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'59M1-400.44' AND s.SlotCode = N'BIKE-04';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'51D-M0088' AND ps.EntryTime = '2026-06-20T05:45:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-20T05:45:00', NULL, N'Cargo Gate', NULL,
           N'PARKING', 120000.00, NULL, @Customer09
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'51D-M0088' AND s.SlotCode = N'TRUCK-01';

IF NOT EXISTS (
    SELECT 1 FROM ParkingSessions ps JOIN Vehicles v ON v.VehicleID = ps.VehicleID
    WHERE v.LicensePlate = N'51D-M0099' AND ps.EntryTime = '2026-06-12T07:00:00'
)
    INSERT INTO ParkingSessions
        (VehicleID, SlotID, EntryTime, ExitTime, EntryGate, ExitGate, Status, EstimatedFee, FinalFee, CreatedBy)
    SELECT v.VehicleID, s.SlotID, '2026-06-12T07:00:00', '2026-06-12T16:30:00', N'Cargo Gate', N'Cargo Gate',
           N'COMPLETED', 150000.00, 165000.00, NULL
    FROM Vehicles v CROSS JOIN ParkingSlots s
    WHERE v.LicensePlate = N'51D-M0099' AND s.SlotCode = N'TRUCK-02';

COMMIT TRANSACTION;
GO

/* Verification summary. */
SELECT u.UserID, u.FullName, u.Email, r.RoleName, u.IsActive
FROM Users u
JOIN Roles r ON r.RoleID = u.RoleID
WHERE u.Email = N'admin@parking.local'
   OR u.Email LIKE N'customer[0-9][0-9]@parking.local'
ORDER BY u.Email;

SELECT s.SlotID, s.SlotCode, vt.TypeName AS VehicleType, s.Status, s.IsActive
FROM ParkingSlots s
JOIN VehicleTypes vt ON vt.VehicleTypeID = s.VehicleTypeID
WHERE s.SlotCode IN
      (N'CAR-01', N'CAR-02', N'CAR-03', N'CAR-04',
       N'BIKE-01', N'BIKE-02', N'BIKE-03', N'BIKE-04',
       N'TRUCK-01', N'TRUCK-02')
ORDER BY s.SlotCode;

SELECT ps.SessionID, v.LicensePlate, s.SlotCode, ps.EntryTime, ps.ExitTime,
       ps.EntryGate, ps.ExitGate, ps.Status, ps.EstimatedFee, ps.FinalFee,
       COALESCE(u.FullName, N'Guest') AS Customer
FROM ParkingSessions ps
JOIN Vehicles v ON v.VehicleID = ps.VehicleID
JOIN ParkingSlots s ON s.SlotID = ps.SlotID
LEFT JOIN Users u ON u.UserID = ps.CreatedBy
WHERE v.LicensePlate IN
      (N'51A-M0011', N'51A-M0022', N'51A-M0033', N'51A-M0044',
       N'59M1-100.11', N'59M1-200.22', N'59M1-300.33', N'59M1-400.44',
       N'51D-M0088', N'51D-M0099')
ORDER BY ps.EntryTime DESC;
GO

