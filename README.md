# cs430project

Semester project for CS430 Fall 2015. Constructing a simple Java GUI application and Oracle database backend for users at a university (students/faculty/staff) to view and edit data.

## Schema

Primary keys are in **bold**.

Student(**sid**: integer, sname: string, major: string, s_level: string, age: integer)  
Courses(**cid**: string, cname:string, meets_at: string, room: string, fid: integer, limit: integer)  
Enrolled(**sid**: integer, **cid**: string, exam1: integer, exam2: integer, final: integer)  
Faculty(**fid**: integer, fname: string, deptid: integer)  
Staff(**sid**: integer, sname: string, deptid: integer)  
Department(**did**: integer, dname: string)

## Setup

### Prerequisites

1. Java JDK 7 or 8
2. Access to CS Oracle DB server
3. SQL*Plus Oracle client

### Compile Project

1. 

### Setup Database

1. Using SQL*Plus client, log into Oracle DB server
2. Remove and existing tables or views with the same names by running the RemoveAll.sql script
`START /path/to/project/directory/RemoveAll.sql`
3. Load the DB schema using the Schema.sql script
`START /path/to/project/directory/Schema.sql`
4. Load the example entries using the ExampleData.sql script
`START /path/to/project/directory/ExampleData.sql`