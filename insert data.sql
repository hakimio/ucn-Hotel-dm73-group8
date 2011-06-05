use AHPI;

insert into hotels (name, address)
values('Toronto Don Valley', '1250 Eglinton Avenue East, Toronto, 
Canada');

insert into hotels(name, address)
values('Crowne Plaza Chateau Lacombe', '10111 Bellamy Hill, Edmonton, 
Canada');

insert into guests(name, hotelName)
values('Joe Doe', 'Toronto Don Valley');

insert into guests(name, hotelName)
values('Brick Thompson', 'Toronto Don Valley');

insert into guests(name, hotelName)
values('John Black', 'Crowne Plaza Chateau Lacombe');

insert into expenses(name, price, guestName)
values('martini', 120, 'Joe Doe');

insert into expenses(name, price, guestName)
values('map', 20, 'Joe Doe');

insert into expenses(name, price, guestName)
values('Fanta', 30, 'Brick Thompson');

insert into rooms(roomNr, meterCost, sqMeters, nrOfBedrooms, hotelName)
values(1, 7, 25, 2, 'Toronto Don Valley');

insert into rooms(roomNr, meterCost, sqMeters, nrOfBedrooms, hotelName)
values(2, 9, 55, 3, 'Toronto Don Valley');

insert into rooms(roomNr, meterCost, sqMeters, nrOfBedrooms, hotelName)
values(1, 11, 35, 3, 'Crowne Plaza Chateau Lacombe');

insert into rooms(roomNr, meterCost, sqMeters, nrOfBedrooms, hotelName)
values(3, 7, 15, 1, 'Toronto Don Valley');

insert into rooms(roomNr, meterCost, sqMeters, nrOfBedrooms, hotelName)
values(2, 5, 25, 2, 'Crowne Plaza Chateau Lacombe');

insert into bookings(id, roomNr, guestName, arrivalDate, leavingDate, discount,
hotelName)
values(1, 1, 'Joe Doe', CAST('2011-10-12 00:00:00' AS datetime), 
CAST('2011-10-15 00:00:00' AS datetime), 0, 'Toronto Don Valley');

insert into bookings(id, roomNr, guestName, arrivalDate, leavingDate, discount,
hotelName)
values(2, 3, 'Joe Doe', CAST('2011-09-13 00:00:00' AS datetime), 
CAST('2011-09-17 00:00:00' AS datetime), 0, 'Toronto Don Valley');

insert into bookings(id, roomNr, guestName, arrivalDate, leavingDate, discount,
hotelName)
values(3, 1, 'Brick Thompson', CAST('2011-10-12 00:00:00' AS datetime), 
CAST('2011-10-15 00:00:00' AS datetime), 0, 'Toronto Don Valley');

insert into bookings(id, roomNr, guestName, arrivalDate, leavingDate, discount,
hotelName)
values(1, 1, 'John Black', CAST('2011-10-15 00:00:00' AS datetime), 
CAST('2011-10-19 00:00:00' AS datetime), 0, 'Crowne Plaza Chateau Lacombe');
