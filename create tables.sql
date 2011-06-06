use DM73_8;

CREATE TABLE hotels
(
	name NvarChar(256)  NOT NULL,
	address NvarChar(512) NOT NULL,
	primary key(name)
);-- TYPE = INNODB;

CREATE TABLE guests
(
	name NvarChar(77) NOT NULL,
	hotelName NvarChar(256) NOT NULL,
	FOREIGN KEY(hotelName) REFERENCES hotels(name) ON UPDATE CASCADE 
		ON DELETE CASCADE,
	primary key(name, hotelName)
);-- TYPE = INNODB;

CREATE TABLE expenses
(
	name NvarChar(256)  NOT NULL,
	price float NOT NULL,
	guestName NvarChar(77) NOT NULL,
	hotelName NvarChar(256) NOT NULL,
	FOREIGN KEY(guestName, hotelName) REFERENCES guests(name, hotelName) 
		ON UPDATE CASCADE ON DELETE CASCADE,
	primary key(name, guestName)
);-- TYPE = INNODB;

CREATE TABLE rooms
(
	roomNr integer NOT NULL,
	meterCost integer NOT NULL,
	sqMeters integer NOT NULL,
	nrOfBedrooms integer NOT NULL,
	hotelName NvarChar(256) NOT NULL,
	FOREIGN KEY(hotelName) REFERENCES hotels(name) ON UPDATE CASCADE 
		ON DELETE CASCADE,
	primary key(roomNr, hotelName)
);-- TYPE = INNODB;

CREATE TABLE workers
(
	name NvarChar(128) NOT NULL,
	birthDate datetime NOT NULL,
	startedWorking datetime NOT NULL,
	income integer NOT NULL,
	position NvarChar(512) NOT NULL,
	hotelName NvarChar(256) NOT NULL,
	FOREIGN KEY(hotelName) REFERENCES hotels(name) ON UPDATE CASCADE 
		ON DELETE CASCADE,
	primary key(name, hotelName)
);-- TYPE = INNODB;

CREATE TABLE bookings
(
	id integer NOT NULL,
	roomNr integer  NOT NULL,
	guestName NvarChar(77) NOT NULL,
	arrivalDate datetime NOT NULL,
	leavingDate datetime NOT NULL,
	discount integer NOT NULL,
	hotelName NvarChar(256) NOT NULL,
	--stupid mssql. Initially it was designed to cascade on update and delete 
	--and foreign keys should have been seperated ie FOREIGN KEY(roomNr).
	FOREIGN KEY(roomNr, hotelName) REFERENCES rooms(roomNr, hotelName) ON UPDATE
		NO ACTION ON DELETE NO ACTION,
	FOREIGN KEY(guestName, hotelName) REFERENCES guests(name, hotelName) 
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(hotelName) REFERENCES hotels(name) ON UPDATE NO ACTION 
		ON DELETE NO ACTION,
	primary key(id, hotelName)
);-- TYPE = INNODB;
