/**
 * Created by anton on 10/19/15.
 * Class for interacting with database
 */
import java.sql.*;
import java.io.*;
public class Database {
    public Connection connect(String server, String port, String db, String user, String pass) throws Exception{
        Connection con = null;
        try {
            // initialize the JDBC driver
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);
            // other way of initializing the driver
            //DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

        }
        catch (Exception ex){
            System.out.println("Cannot load Oracle driver");
        }
        try {
            // connect to the db
            String url = "jdbc:oracle:thin:@"+server+":"+port+":"+db;
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
        }
        // returns null on failure
        return con;
    }
    public boolean setupDB(Connection con){
        // drop table queries
        String clearStudent = "DROP TABLE Student";
        String clearDepartment = "DROP TABLE Department";
        String clearFaculty =  "DROP TABLE Faculty";
        String clearCourses =  "DROP TABLE Courses";
        String clearEnrolled =  "DROP TABLE Enrolled";
        String clearStaff =  "DROP TABLE Staff";
        // table schema
        String StudentSchema = "CREATE TABLE Student (" +
                "sid INTEGER," +
                "sname VARCHAR(100)," +
                "major VARCHAR(50)," +
                "s_level VARCHAR(10)," +
                "age INTEGER," +
                "CONSTRAINT student_level CHECK (s_level IN ('freshman','sophomore','junior','senior','master','phd'))," +
                "PRIMARY KEY (sid)" +
                ")";
        String DepartmentSchema = "CREATE TABLE Department (" +
                "did INTEGER," +
                "dname VARCHAR(100)," +
                "PRIMARY KEY (did)" +
                ")";
        String FacultySchema = "CREATE TABLE Faculty (" +
                "fid INTEGER," +
                "fname VARCHAR(100)," +
                "depid INTEGER REFERENCES Department (did)," +
                "PRIMARY KEY (fid)" +
                ")";
        String CoursesSchema = "CREATE TABLE Courses (" +
                "cid VARCHAR(10)," +
                "cname VARCHAR(50)," +
                "meets_at VARCHAR(30)," +
                "room VARCHAR(20)," +
                "fid INTEGER REFERENCES Faculty (fid)," +
                "limit INTEGER," +
                "PRIMARY KEY (cid)" +
                ")";
        String EnrolledSchema = "CREATE TABLE Enrolled (" +
                "sid INTEGER REFERENCES Student (sid)," +
                "cid VARCHAR(10) REFERENCES Courses (cid)," +
                "exam1 INTEGER CHECK (exam1 >= 0 AND exam1 <= 100)," +
                "exam2 INTEGER CHECK (exam2 >= 0 AND exam2 <= 100)," +
                "final INTEGER CHECK (final >= 0 AND final <= 100)," +
                "PRIMARY KEY (sid, cid)" +
                ")";
        String StaffSchema = "CREATE TABLE Staff (" +
                "sid INTEGER," +
                "sname VARCHAR(100)," +
                "depid INTEGER REFERENCES Department (did)," +
                "PRIMARY KEY (sid)" +
                ")";
        // insert data queries
        String EnrolledData = "INSERT ALL" +
                "INTO Enrolled VALUES (1,'CS430',90,85,97)" +
                "INTO Enrolled VALUES (2,'IST225',50,65,70)" +
                "INTO Enrolled VALUES (3,'ISAT415',100,100,100)" +
                "INTO Enrolled VALUES (4,'CS101',0,0,0)" +
                "INTO Enrolled VALUES (5,'CP101',23,18,35)" +
                "INTO Enrolled VALUES (6,'ENG222',82,77,75)" +
                "INTO Enrolled VALUES (7,'CP330',50,51,52)" +
                "INTO Enrolled VALUES (8,'MAT108',63,18,23)" +
                "INTO Enrolled VALUES (9,'MAT125',53,19,68)" +
                "INTO Enrolled VALUES (10,'UC101',90,97,95)" +
                "SELECT * FROM dual";
        String StaffData = "INSERT ALL" +
                "INTO Staff VALUES (1,'Hubert Davidson',1)" +
                "INTO Staff VALUES (2,'Iona Overton,2)" +
                "INTO Staff VALUES (3,'Raphael Tifft',3)" +
                "INTO Staff VALUES (4,'Anneka Irvine',4)" +
                "INTO Staff VALUES (5,'Carley Revie',5)" +
                "INTO Staff VALUES (6,'Aric Samson',6)" +
                "INTO Staff VALUES (7,'Charnette Gardyner',7)" +
                "INTO Staff VALUES (8,'Reynold Stacy',8)" +
                "INTO Staff VALUES (9,'Vince Hanley',9)" +
                "INTO Staff VALUES (10,'Cynthia Coel',10)" +
                "SELECT * FROM dual";
        // remove any existing tables
        runDefineManip(con,clearStaff);
        runDefineManip(con,clearEnrolled);
        runDefineManip(con,clearCourses);
        runDefineManip(con,clearFaculty);
        runDefineManip(con,clearDepartment);
        runDefineManip(con,clearStudent);
        // create tables
        runDefineManip(con,StudentSchema);
        runDefineManip(con,DepartmentSchema);
        runDefineManip(con,FacultySchema);
        runDefineManip(con,CoursesSchema);
        runDefineManip(con,EnrolledSchema);
        runDefineManip(con,StaffSchema);
        return true;
    }
    public boolean ignoreSQLError (Integer sqlerror){
        if (sqlerror == null){
            System.out.println("No SQL Error given!");
            return false;
        }
        // table or view does not exist
        if (sqlerror == 942){
            return true;
        }
        return false;
    }
    public boolean runDefineManip (Connection con, String statement){
        if (statement == null){
            System.err.println("No Statement given!");
            return false;
        }
        try {
            PreparedStatement prep = con.prepareStatement(statement);
            prep.execute();
            prep.close();
            return true;
        }
        catch (Exception ex){
            if (ex instanceof SQLException){
                if (ignoreSQLError(((SQLException)ex).getErrorCode()) == false) {
                    System.out.println("SQLException: "+ex);
                }
            }
            else {
                System.out.println("SQLException: "+ex);
            }
            return false;
        }
    }
}
