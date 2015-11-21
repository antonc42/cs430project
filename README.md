# cs430project

Semester project for CS430 Fall 2015. Constructing a simple Java GUI application and Oracle database backend for users at a university (students/faculty/staff) to view and edit data.

## Schema

Primary keys are in **bold**.

Student( **sid**: integer, sname: string, major: string, s_level: string, age: integer)  
Courses( **cid**: string, cname:string, meets_at: string, room: string, fid: integer, limit: integer)  
Enrolled( **sid**: integer, **cid**: string, exam1: integer, exam2: integer, final: integer)  
Faculty( **fid**: integer, fname: string, deptid: integer)  
Staff( **sid**: integer, sname: string, deptid: integer)  
Department( **did**: integer, dname: string)

## Setup

### Prerequisites

1. Java JDK 7 or 8
2. Access to CS Oracle DB server
3. SQL*Plus Oracle client

### Compile Project

1. Open a terminal window.
2. Go to the project directory.
3. Compile using JDK
`javac *.java`

### Setup Database

1. Using SQL*Plus client, log into Oracle DB server
2. Remove and existing tables or views with the same names by running the RemoveAll.sql script
`START /path/to/project/directory/RemoveAll.sql`
3. Load the DB schema using the Schema.sql script
`START /path/to/project/directory/Schema.sql`
4. Load the example entries using the ExampleData.sql script
`START /path/to/project/directory/ExampleData.sql`

## Run Project

1. In the project directory, run the main class.
`java MainClass`
2. The login to the Oracle database may take some time. Please be patient.

## Test Project

1. When the project is run for the first time, a dialog should appear prompting for the entry of the DB username and password.
2. Enter the Oracle DB username and password and check the Save Configuration box to save the username and password to a file.
3. **The saved configuration will be in plain text and in the file '.cs430dbconfig' in the user's home directory.**
4. Click the Login button.
5. **If the project has been run before and the configuration was saved, the program will start at this point.**
6. Wait for the program to log into the database. This may take some time.
7. A user login dialog will appear. Enter a user id from the sample data (either staff, faculty, or student).
8. Click Login.
9. If logging in as a student, the student window will appear.
  * The student can view information about themselves and a list of classes they are enrolled in.
  * Selecting an enrolled class from the list enables the Drop button, which can be used to drop the selected class.
  * The student can also view or search for available classes.
  * Enter search terms in the fields and click the Search button to find a class.
  * Click the Clear button to clear the fields and search results.
  * Selecting a class from the list enables the Enroll button, which can be used to enroll in the class.
10. 