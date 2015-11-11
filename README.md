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

## Questions
1. Should I instantiate the Database class so many times? Just once per class? Too many instances? Garbage collection?
