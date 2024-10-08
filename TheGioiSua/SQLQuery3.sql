-- product table
CREATE TABLE Product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    productcode VARCHAR(10) NULL,
    productname VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    milktypeid INT NOT NULL,
    status INT NOT NULL
);

-- container table
CREATE TABLE Container (
    id INT PRIMARY KEY AUTO_INCREMENT,
    containername VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,
    status INT NOT NULL
);

-- milktype table
CREATE TABLE Milktype (
    id INT PRIMARY KEY AUTO_INCREMENT,
    milktypename VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,
    status INT NOT NULL
);

-- size table
CREATE TABLE Size (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sizename VARCHAR(100) NOT NULL,
    status INT NOT NULL
);

-- packaging table
CREATE TABLE Packaging (
    id INT PRIMARY KEY AUTO_INCREMENT,
    packagingname VARCHAR(100) NOT NULL,
    status INT NOT NULL
);

-- packagingunit table
CREATE TABLE Packagingunit (
    id INT PRIMARY KEY AUTO_INCREMENT,
    packagingunitname VARCHAR(100) NOT NULL,
    status INT NOT NULL
);

-- targetuser table
CREATE TABLE Targetuser (
    id INT PRIMARY KEY AUTO_INCREMENT,
    targetname VARCHAR(100) NOT NULL,
    description VARCHAR(255) NULL,
    status INT NOT NULL
);

-- usagecapacity table
CREATE TABLE Usagecapacity (
    id INT PRIMARY KEY AUTO_INCREMENT,
    capacity INT NOT NULL,
    unit VARCHAR(255) NOT NULL,
    status INT NOT NULL
);

-- milkdetail table
CREATE TABLE Milkdetail (
    id INT PRIMARY KEY AUTO_INCREMENT,
    productid INT NOT NULL,
    containerid INT NOT NULL,
    sizeid INT NOT NULL,
    packagingid INT NOT NULL,
    packagingunitid INT NOT NULL,
    usagecapacityid INT NOT NULL,
    expirationdate VARCHAR(255) NOT NULL,
    price DECIMAL(18, 0) NOT NULL,
    description VARCHAR(255) NOT NULL,
    stockquantity INT NOT NULL,
    status INT NOT NULL
);

-- role table
CREATE TABLE Role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    rolename VARCHAR(100) NOT NULL,
    status INT NOT NULL
);

-- user table
CREATE TABLE User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    roleid INT NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    registrationdate DATETIME DEFAULT NOW(),
    phonenumber VARCHAR(10) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    status INT NOT NULL
);

-- invoice table
CREATE TABLE Invoice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    invoicecode VARCHAR(10) NULL,
    creationdate DATETIME DEFAULT NOW(),
    voucherid INT NULL,
    discountamount DECIMAL(18, 0) NULL,
    totalamount DECIMAL(18, 0) NOT NULL,
    status INT NOT NULL
);

-- invoicedetail table
CREATE TABLE InvoiceDetail (
    id INT PRIMARY KEY AUTO_INCREMENT,
    invoiceid INT NOT NULL,
    milkdetailid INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(18, 0) NOT NULL,
    totalprice DECIMAL(18, 0) GENERATED ALWAYS AS (quantity * price) STORED,
    status INT NOT NULL
);

-- userinvoice table
CREATE TABLE UserInvoice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userid INT NOT NULL,
    invoiceid INT NOT NULL,
    status INT NOT NULL
);

-- voucher table
CREATE TABLE Voucher (
    id INT PRIMARY KEY AUTO_INCREMENT,
    vouchercode VARCHAR(50) NOT NULL UNIQUE,
    startdate DATETIME DEFAULT NOW(),
    enddate DATETIME NOT NULL,
    discountpercentage INT NOT NULL,
    exchangeamount DECIMAL(18, 0),
    usagecount INT NOT NULL,
    status INT NOT NULL
);

INSERT INTO Product (productcode, productname, quantity, milktypeid, status) VALUES
('SP001', 'Powdered Milk', 100, 1, 1),
('SP002', 'Fresh Milk', 200, 1, 1),
('SP003', 'Nut Milk', 150, 2, 1);

INSERT INTO Container (containername, description, status) VALUES
('Bottle', 'Plastic milk bottle', 1),
('Box', 'Paper milk box', 1);

INSERT INTO Milktype (milktypename, description, status) VALUES
('Powdered Milk', 'Powdered milk for children', 1),
('Fresh Milk', 'Pure fresh milk', 1),
('Condensed Milk', 'Sweetened condensed milk', 1);

INSERT INTO Size (sizename, status) VALUES
('Large', 1),
('Small', 1),
('1L', 1);

INSERT INTO Packaging (packagingname, status) VALUES
('Box', 1),
('Tray', 1);

INSERT INTO Packagingunit (packagingunitname, status) VALUES
('Box', 1),
('Tray', 1);

INSERT INTO Targetuser (targetname, description, status) VALUES
('Children', 'Products for children', 1),
('Adults', 'Products for adults', 1);

INSERT INTO Usagecapacity (capacity, unit, status) VALUES
(100, 'ml', 1),
(200, 'ml', 1),
(1000, 'ml', 1);

INSERT INTO Milkdetail (productid, containerid, sizeid, packagingid, packagingunitid, usagecapacityid, expirationdate, price, description, stockquantity, status) VALUES
(1, 1, 1, 1, 1, 1, '2025-12-31', 25000, 'Nutritional powdered milk', 50, 1),
(2, 2, 2, 2, 2, 2, '2025-12-31', 20000, 'Natural fresh milk', 100, 1);

INSERT INTO Role (rolename, status) VALUES
('Admin', 1),
('User', 1);

INSERT INTO User (roleid, username, password, fullname, phonenumber, address, email, status) VALUES
(1, 'admin', 'password123', 'Nguyen Van A', '0123456789', 'Hanoi', 'admin@example.com', 1),
(2, 'user1', 'password123', 'Nguyen Van B', '0987654321', 'Ho Chi Minh City', 'user1@example.com', 1);

INSERT INTO Invoice (invoicecode, voucherid, discountamount, totalamount, status) VALUES
('HD001', NULL, 0, 50000, 1),
('HD002', 1, 5000, 45000, 1);

INSERT INTO InvoiceDetail (invoiceid, milkdetailid, quantity, price, status) VALUES
(1, 1, 2, 25000, 1),
(2, 2, 1, 20000, 1);

INSERT INTO Userinvoice (userid, invoiceid, status) VALUES
(1, 1, 1),
(2, 2, 1);

INSERT INTO Voucher (vouchercode, startdate, enddate, discountpercentage, exchangeamount, usagecount, status) VALUES
('VOUCHER01', NOW(), '2025-12-31', 10, NULL, 100, 1),
('VOUCHER02', NOW(), '2025-12-31', 15, 5000, 50, 1);
