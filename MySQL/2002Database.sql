CREATE TABLE Patient (
    PatientID int (4)        NOT NULL,
    FirstName varchar(20)    NOT NULL,
    LastName varchar(20)     NOT NULL,
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
	PRIMARY KEY (HouseNumber, Postcode)
);

CREATE TABLE Appointment (
	AppointmentDate date    NOT NULL,
	StartTime time          NOT NULL,
	Partner varchar(15)     NOT NULL, 
	EndTime time,           NOT NULL
	Empty boolean,          NOT NULL
	PatientID int(9)        NOT NULL,
	PRIMARY KEY (AppointmentDate, StartTime, Partner)
);

CREATE TABLE treatment(
	TreatmentName varchar(25)      NOT NULL,
	TreatmentCost decimal(10, 2),
	AppointmentDate date           NOT NULL,
	StartTime time                 NOT NULL,
	Partner varchar(15)            NOT NULL,
	PRIMARY KEY (TreatmentName)
);

CREATE TABLE Patient-HCP-LINKER (
	PatientID int(8)      NOT NULL,
	HCPName varchar(15)   NOT NULL
);

CREATE TABLE HCP (
	HCPName varchar(10)   NOT NULL,
	MonthlyPayment int(10),
	CheckupsMax int (2),
	CheckupsTaken int(2),
	HygeneMax int(2),
	HygeneTaken int(2),
	RepairsMax int(2),
	RepairsTaken int(2)
);