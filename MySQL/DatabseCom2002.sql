CREATE TABLE Patient (
    PatientID int (4),
    FirstName varchar(20),
    LastName varchar(20),
    DOB date,
    ContactNumber varchar(7)
	HouseNumber varchar(10)
	Postcode varchar (10)
);

CREATE TABLE Address (
    HouseNumber varchar(10),
	Postcode varchar(10),
	StreetName varchar(15),
	DistrictName varchar(15),
	CityName varchar(15)
);

CREATE TABLE Appointment (
	AppointmentDate date,
	StartTime datetime,
	Partner varchar(15), 
	EndTime datetime,
	Empty boolean, 
	PatientID int(9)
);

CREATE TABLE treatment(
	TreatmentName varchar(25),
	TreatmentCost decimal(10),
	AppointmentDate date,
	StartTime datetime,
	Partner varchar(15)
);

CREATE TABLE Patient-HCP-LINKER (
	PatientID int(8),
	HCPName varchar(15)
);

CREATE TABLE HCP (
	HCPName varchar(10),
	MonthlyPayment int(10),
	CheckupsMax int (2),
	CheckupsTaken int(2),
	HygeneMax int(2),
	HygeneTaken int(2),
	RepairsMax int(2),
	RepairsTaken int(2)
);