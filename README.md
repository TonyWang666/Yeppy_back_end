Yeppy_back_end
Backend for the Content-based Personal Restaurant Recommendation system 

Yeppy Description: 
A full-stack project with frontend using React.js and backend using Java, Rest API, Postgresql

How to run?
Using Tom-cat to connect the server to database. 
You can use Datagrid to serve as a database.
Open both the front-end and backend. (Front-end link is: https://github.com/TonyWang666/Yeppy_front_end.git)
After that, you can access front-end.

Below are the script to Create Tables in DataGrid for Server:

CREATE SCHEMA yeppy;
SET search_path TO yeppy;

CREATE TABLE IF NOT EXISTS yeppy.user(
   user_id varchar,
   username varchar UNIQUE ,
   password varchar,
   PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS yeppy.like(
   user_id varchar,
   business_id varchar,
   PRIMARY KEY (user_id, business_id),
   FOREIGN KEY (user_id) REFERENCES yeppy.user (user_id)
);

CREATE TABLE IF NOT EXISTS yeppy.user_preferences(
   user_id varchar,
   category varchar,
   count INT DEFAULT 0,
   PRIMARY KEY (user_id, category),
   FOREIGN KEY (user_id) REFERENCES yeppy.user (user_id)
);
