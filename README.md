# About ENSF409_Term_Project
The KerrYip University Dev Team:
Will Kerr - william.kerr@ucalgary.ca
Tyler Yip - tyler.yip1@ucalgary.ca

This application was built as the final project for our ENSF 409: Principles of Software Development course

Instructions on how to run the application:

## SETUP:
- This program requires a database to be made using mysql with the name "registration_app_database". The program will access the database via a user "ENSF409" and password "ENSF_409". 
  If tables are not present within the database upon running the program, the program should create them. It is also important to note that the data within these database tables must 
  be formatted in a very specific way. 
- There must be at least one admin user created manually, using the table "administrator" and creating a row with an id starting at 60000, a username, and a password. If this admin user 
  is not present, it will be impossible to login as a student or admin since there is no saved data on either. 
- There are README's for both the server and client located in "ENSF409_Term_Project" and "ENSF409_Term_Project_Client" that will go into more detail about how each should be run. 
  In short, the server must be run first. Then as many clients as desired should be able to connect concurrently. Upon running the client program, the user will be able to login to a 
  student user or admin user. As a student, the user will only be able to view courses, enrol in courses, drop a course, and search for a course. On the admin side, the user will be able
  to add courses, remove courses, add prerequisites to a course, search for a student, set a student's grade, and add new student users to the program. 

## BONUS FEATURES LIST:
- Improved GUI design appearance including colours, stylized buttons, logo, and stylized dialog panes
- Admin user with added functionalities such as adding a course, removing a course (not an easy process, much more work than adding a course), setting grades for students, 
  adding prerequisites to courses, and adding additional student users
- Maintaining a list of users using the database and implementing a login system (whilst only allowing one client to be logged in to one profile at a time)
- 2 comprehensive JUNIT tests completed for the Course class and Student class in the server test package
- Detailed server logging for better debugging and server maintenance 

## DOCUMENTATION:
- Javadocs are included in each of the respective project folders

# ENSF409_Term_Project_Client

There are README's for both the server and client located in "EMSF409_Term_Project" and "ENSF409_Term_Project_Client" respectively. Please read both of these before starting the program as they contain instructions on how the entire application must be run. In short, the server must be run first and it must have the database tables created and formatted properly. Please do not edit these tables manually as they follow a strict formatting. Also see the server README on how to operate the application from the server-side. 
