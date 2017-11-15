CREATE TABLE Patient (
    PatientID int (8)        NOT NULL AUTO_INCREMENT,
    FirstName varchar(20)    NOT NULL,
    LastName varchar(20),
    DOB date,
    ContactNumber varchar(7),
    HouseNumber varchar(10)  NOT NULL,
    Postcode varchar (10)    NOT NULL,
    PRIMARY KEY (PatientID)
);

CREATE TABLE Address (
    HouseNumber varchar(10)  NOT NULL,
    Postcode varchar(10)     NOT NULL,
    StreetName varchar(15),
    DistrictName varchar(15),
    CityName varchar(15),
    Constraint PK_Address PRIMARY KEY (HouseNumber, Postcode)
);

CREATE TABLE Appointment (
    AppointmentDate date    NOT NULL,
    StartTime time          NOT NULL,
    Partner varchar(15)     NOT NULL, 
    EndTime time            NOT NULL,
    Empty boolean           NOT NULL,
    PatientID int(9)        NOT NULL,
    Constraint PK_Appointment PRIMARY KEY (AppointmentDate, StartTime, Partner)
);

CREATE TABLE HCP (
    HCPID int(8) NOT NULL AUTO_INCREMENT,
    HCPName varchar(10)    NOT NULL,
    MonthlyPayment int(10) NOT NULL,
    CheckupsTaken int(2),
    HygeneTaken int(2),
    RepairsTaken int(2),
	PRIMARY KEY (HCPID)
);

CREATE TABLE HCP-Patient (
    HCPID int(8) NOT NULL,
    HCPName varchar(10)   NOT NULL,
	Constraint PK_Appointment PRIMARY KEY (HCPID)
);

CREATE TABLE HCPStore (
    HCPName varchar(30) NOT NULL,
	MonthlyPayment Int (10) NOT NULL,
    CheckupsMax Int(2) NOT NULL,
	HygeneMax Int(2) NOT NULL,
	RepairsMax Int(2) NOT NULL,
	PRIMARY KEY (HCPName)
);