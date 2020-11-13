create database loginform_test; /* create the database with name loginform_test */
use loginform_test; /* we use this sql statement for making database loginform_test the current database */
create table employer ( email varchar(20), password varchar(20)); /* we use this sql statement for creating a table in database with the name employer. This table will have two columns the email and password */
show tables; /* we use this sql statement to see the tables in our database */
describe employer; /* we use this sql statement to verify that our table was created with the way we expected */
insert into employer
	values ('kitsos@kitsos.com', 'Kitsos1'); /* we use this sql statement to load data into our table */
insert into employer
	values ('mhtsos@mhtsos.com', 'Mhtsos1');
select * from employer /* we use this statement to retrieve all the data from the table */