USE ParkingManagementSystem;
GO

/*
    Admin account setup
    -------------------
    Rerunnable: creates the account when absent and resets it when present.

    This project currently compares PasswordHash directly with the submitted
    password. Therefore, @Password is stored as plain text to remain compatible
    with the current login implementation. Do not use this approach in a
    production application.
*/

SET XACT_ABORT ON;
BEGIN TRANSACTION;

DECLARE @Email NVARCHAR(100) = N'admin@local.com';
DECLARE @Password NVARCHAR(255) = N'12345';
DECLARE @FullName NVARCHAR(100) = N'System Administrator';
DECLARE @PhoneNumber NVARCHAR(20) = N'0901000001';

IF NOT EXISTS (SELECT 1 FROM Roles WHERE RoleName = N'ADMIN')
BEGIN
    INSERT INTO Roles (RoleName, Description)
    VALUES (N'ADMIN', N'Parking system administrator');
END;

DECLARE @AdminRoleID INT = (
    SELECT RoleID
    FROM Roles
    WHERE RoleName = N'ADMIN'
);

IF EXISTS (SELECT 1 FROM Users WHERE Email = @Email)
BEGIN
    UPDATE Users
    SET FullName = @FullName,
        PhoneNumber = @PhoneNumber,
        PasswordHash = @Password,
        RoleID = @AdminRoleID,
        IsActive = 1
    WHERE Email = @Email;
END;
ELSE
BEGIN
    INSERT INTO Users (
        FullName,
        Email,
        PhoneNumber,
        PasswordHash,
        RoleID,
        IsActive,
        CreatedAt
    )
    VALUES (
        @FullName,
        @Email,
        @PhoneNumber,
        @Password,
        @AdminRoleID,
        1,
        GETDATE()
    );
END;

COMMIT TRANSACTION;

SELECT
    u.UserID,
    u.FullName,
    u.Email,
    r.RoleName,
    u.IsActive
FROM Users AS u
INNER JOIN Roles AS r ON r.RoleID = u.RoleID
WHERE u.Email = @Email;
GO
