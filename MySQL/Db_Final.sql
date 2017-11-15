CREATE TABLE Patient (
    PatientID int (8)        NOT NULL AUTO_INCREMENT,
    FirstName varchar(20)    NOT NULL,
    LastName varchar(20),
    DOB date                 NOT NULL,
    ContactNumber varchar(7),
    HouseNumber varchar(10)  NOT NULL,
    Postcode varchar (10)    NOT NULL,
    PRIMARY KEY (PatientID)
    FOREIGN KEY (HouseNumber) REFERENCES Address(HouseNumber)
    FOREIGN KEY (Postcode) REFERENCES Address(Postcode)
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
    FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

CREATE TABLE Treatment-App_Linker (
    TreatmentID Int(8)        NOT NULL,
    AppointmentDate date      NOT NULL,
    StartTime time        NOT NULL,
    Partner varchar(15)       NOT NULL,
    CONSTANT PK_Treatment-App PRIMARY KEY (TreatmentID, AppointmentDate, StartTime, Partner)
    FOREIGN KEY (TreatmentID) REFERENCES Treatment(TreatmentID)
    FOREIGN KEY (AppointmentDate) REFERENCES Appointment(AppointmentDate)
    FOREIGN KEY (StartTime) REFERENCES Appointment(StartTime)
    FOREIGN KEY (Partner) REFERENCES Appointment(Partner)
);

CREATE TABLE Treatment (
    TreatmentID Int(8) NOT NULL AUTO_INCREMENT,
	TreatmentName varchar (15),
    PRIMARY KEY(TreatmentID)
    FOREIGN KEY (TreatmentName) REFERENCES TreatmentStore(TreatmentName)
);

CREATE TABLE TreatmentStore (
    TreatmentName varchar(15) NOT NULL,
    TreatmentCost Int(6) NOT NULL,
);

CREATE TABLE HCP (
    HCPID int(8) NOT NULL AUTO_INCREMENT,
    HCPName varchar(10)    NOT NULL,
    MonthlyPayment int(10) NOT NULL,
    CheckupsTaken int(2),
    HygeneTaken int(2),
    RepairsTaken int(2),
    PRIMARY KEY (HCPID)
    FOREIGN KEY (HCPName) REFERENCES HCPStore(HCPName)
);

CREATE TABLE HCP-Patient_Linker (
    HCPID int(8) NOT NULL,
    PatientID int(8) NOT NULL,
    Constraint PK_Appointment PRIMARY KEY (HCPID, PatientID)
    FOREIGN KEY (HCPID) REFERENCES HCP(HCPID)
    FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)
);

CREATE TABLE HCPStore (
    HCPName varchar(30) NOT NULL,
    MonthlyPayment Int (10) NOT NULL,
    CheckupsMax Int(2) NOT NULL,
    HygeneMax Int(2) NOT NULL,
    RepairsMax Int(2) NOT NULL,
    PRIMARY KEY (HCPName)
);