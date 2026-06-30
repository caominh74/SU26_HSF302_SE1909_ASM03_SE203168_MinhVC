USE ParkingManagementSystem;
GO

/* Roles and sample accounts. PasswordHash values are placeholders until authentication is implemented. */
IF NOT EXISTS (SELECT 1 FROM Roles WHERE RoleName = 'STAFF')
    INSERT INTO Roles (RoleName, Description) VALUES ('STAFF', 'Parking facility staff');
IF NOT EXISTS (SELECT 1 FROM Roles WHERE RoleName = 'CUSTOMER')
    INSERT INTO Roles (RoleName, Description) VALUES ('CUSTOMER', 'Registered parking customer');

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = 'staff@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive)
    SELECT 'Demo Staff', 'staff@parking.local', '0900000001', 'NOT_FOR_LOGIN', RoleID, 1
    FROM Roles WHERE RoleName = 'STAFF';

IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = 'customer@parking.local')
    INSERT INTO Users (FullName, Email, PhoneNumber, PasswordHash, RoleID, IsActive)
    SELECT 'Demo Customer', 'customer@parking.local', '0900000002', 'NOT_FOR_LOGIN', RoleID, 1
    FROM Roles WHERE RoleName = 'CUSTOMER';

/* Vehicle types used by the create-session form. */
IF NOT EXISTS (SELECT 1 FROM VehicleTypes WHERE TypeName = 'CAR')
    INSERT INTO VehicleTypes (TypeName, Description) VALUES ('CAR', 'Passenger car');
IF NOT EXISTS (SELECT 1 FROM VehicleTypes WHERE TypeName = 'MOTORBIKE')
    INSERT INTO VehicleTypes (TypeName, Description) VALUES ('MOTORBIKE', 'Motorbike or scooter');
IF NOT EXISTS (SELECT 1 FROM VehicleTypes WHERE TypeName = 'TRUCK')
    INSERT INTO VehicleTypes (TypeName, Description) VALUES ('TRUCK', 'Truck or large vehicle');

/* Building -> floor -> zone hierarchy required by ParkingSlots. */
IF NOT EXISTS (SELECT 1 FROM Buildings WHERE BuildingName = 'Demo Parking Building')
    INSERT INTO Buildings
        (BuildingName, Address, TotalFloors, OperatingStartTime, OperatingEndTime)
    VALUES
        ('Demo Parking Building', 'University Campus', 1, '06:00', '23:00');

DECLARE @BuildingID INT = (
    SELECT TOP 1 BuildingID FROM Buildings WHERE BuildingName = 'Demo Parking Building'
);

IF NOT EXISTS (SELECT 1 FROM Floors WHERE BuildingID = @BuildingID AND FloorNumber = 1)
    INSERT INTO Floors (BuildingID, FloorNumber, FloorName)
    VALUES (@BuildingID, 1, 'Ground Floor');

DECLARE @FloorID INT = (
    SELECT TOP 1 FloorID FROM Floors WHERE BuildingID = @BuildingID AND FloorNumber = 1
);

IF NOT EXISTS (SELECT 1 FROM Zones WHERE FloorID = @FloorID AND ZoneName = 'Zone A')
    INSERT INTO Zones (FloorID, ZoneName, Description)
    VALUES (@FloorID, 'Zone A', 'Car parking zone');
IF NOT EXISTS (SELECT 1 FROM Zones WHERE FloorID = @FloorID AND ZoneName = 'Zone B')
    INSERT INTO Zones (FloorID, ZoneName, Description)
    VALUES (@FloorID, 'Zone B', 'Motorbike and truck parking zone');

DECLARE @ZoneAID INT = (SELECT TOP 1 ZoneID FROM Zones WHERE FloorID = @FloorID AND ZoneName = 'Zone A');
DECLARE @ZoneBID INT = (SELECT TOP 1 ZoneID FROM Zones WHERE FloorID = @FloorID AND ZoneName = 'Zone B');
DECLARE @CarTypeID INT = (SELECT VehicleTypeID FROM VehicleTypes WHERE TypeName = 'CAR');
DECLARE @MotorbikeTypeID INT = (SELECT VehicleTypeID FROM VehicleTypes WHERE TypeName = 'MOTORBIKE');
DECLARE @TruckTypeID INT = (SELECT VehicleTypeID FROM VehicleTypes WHERE TypeName = 'TRUCK');

IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = 'CAR-A01')
    INSERT INTO ParkingSlots (ZoneID, SlotCode, VehicleTypeID, Status, IsActive)
    VALUES (@ZoneAID, 'CAR-A01', @CarTypeID, 'AVAILABLE', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = 'CAR-A02')
    INSERT INTO ParkingSlots (ZoneID, SlotCode, VehicleTypeID, Status, IsActive)
    VALUES (@ZoneAID, 'CAR-A02', @CarTypeID, 'AVAILABLE', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = 'BIKE-B01')
    INSERT INTO ParkingSlots (ZoneID, SlotCode, VehicleTypeID, Status, IsActive)
    VALUES (@ZoneBID, 'BIKE-B01', @MotorbikeTypeID, 'AVAILABLE', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = 'BIKE-B02')
    INSERT INTO ParkingSlots (ZoneID, SlotCode, VehicleTypeID, Status, IsActive)
    VALUES (@ZoneBID, 'BIKE-B02', @MotorbikeTypeID, 'AVAILABLE', 1);
IF NOT EXISTS (SELECT 1 FROM ParkingSlots WHERE SlotCode = 'TRUCK-B01')
    INSERT INTO ParkingSlots (ZoneID, SlotCode, VehicleTypeID, Status, IsActive)
    VALUES (@ZoneBID, 'TRUCK-B01', @TruckTypeID, 'AVAILABLE', 1);
GO

SELECT SlotCode, Status, IsActive FROM ParkingSlots
WHERE SlotCode IN ('CAR-A01', 'CAR-A02', 'BIKE-B01', 'BIKE-B02', 'TRUCK-B01');
GO
