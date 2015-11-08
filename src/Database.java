/**
 * Created by anton on 10/19/15.
 * Class for interacting with database
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

public class Database {
    public Connection connect(String server, String port, String db, String user, String pass) {
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

    public boolean setupSchema(Connection con){
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
                "CONSTRAINT student_pk PRIMARY KEY (sid)" +
                ")";
        String DepartmentSchema = "CREATE TABLE Department (" +
                "did INTEGER," +
                "dname VARCHAR(100)," +
                "CONSTRAINT dept_pk PRIMARY KEY (did)" +
                ")";
        String FacultySchema = "CREATE TABLE Faculty (" +
                "fid INTEGER," +
                "fname VARCHAR(100)," +
                "depid INTEGER REFERENCES Department (did)," +
                "CONSTRAINT faculty_pk PRIMARY KEY (fid)" +
                ")";
        String CoursesSchema = "CREATE TABLE Courses (" +
                "cid VARCHAR(10)," +
                "cname VARCHAR(50)," +
                "meets_at VARCHAR(30)," +
                "room VARCHAR(20)," +
                "fid INTEGER REFERENCES Faculty (fid)," +
                "limit INTEGER," +
                "CONSTRAINT courses_pk PRIMARY KEY (cid)" +
                ")";
        String EnrolledSchema = "CREATE TABLE Enrolled (" +
                "sid INTEGER REFERENCES Student (sid)," +
                "cid VARCHAR(10) REFERENCES Courses (cid)," +
                "exam1 INTEGER CHECK (exam1 >= 0 AND exam1 <= 100)," +
                "exam2 INTEGER CHECK (exam2 >= 0 AND exam2 <= 100)," +
                "final INTEGER CHECK (final >= 0 AND final <= 100)," +
                "CONSTRAINT enrolled_pk PRIMARY KEY (sid, cid)" +
                ")";
        String StaffSchema = "CREATE TABLE Staff (" +
                "sid INTEGER," +
                "sname VARCHAR(100)," +
                "depid INTEGER REFERENCES Department (did)," +
                "CONSTRAINT staff_pk PRIMARY KEY (sid)" +
                ")";

        // remove any existing tables
        runDrop(con,clearStaff);
        runDrop(con,clearEnrolled);
        runDrop(con,clearCourses);
        runDrop(con,clearFaculty);
        runDrop(con,clearDepartment);
        runDrop(con,clearStudent);
        // create tables
        runDefineManip(con,StudentSchema);
        runDefineManip(con,DepartmentSchema);
        runDefineManip(con,FacultySchema);
        runDefineManip(con,CoursesSchema);
        runDefineManip(con,EnrolledSchema);
        runDefineManip(con,StaffSchema);
        return true;
    }

    public boolean enterData (Connection con) {
        // insert data queries
        String StudentData = "INSERT ALL" +
                " INTO Student VALUES (1,'Kimberley Roach','IST','freshman',18)" +
                " INTO Student VALUES (2,'Duke Vernon','EST','sophomore',19)" +
                " INTO Student VALUES (3,'Elnora Martins','CS','junior',20)" +
                " INTO Student VALUES (4,'Randell Nicholson','MAT','senior',21)" +
                " INTO Student VALUES (5,'Herbie Emmett','MBA','master',23)" +
                " INTO Student VALUES (6,'Noelle Evelyn','PHL','phd',30)" +
                " INTO Student VALUES (7,'Lindsay Alvey','CS','freshman',19)" +
                " INTO Student VALUES (8,'Pacey Womack','KIN','sophomore',20)" +
                " INTO Student VALUES (9,'Melantha Cross','MCMA','junior',21)" +
                " INTO Student VALUES (10,'Miles Bass','ENG','senior',22)" +
                " SELECT * FROM dual";
        String DepartmentData = "INSERT ALL" +
                " INTO Department VALUES (1,'Psychology')" +
                " INTO Department VALUES (2,'Computer Science')" +
                " INTO Department VALUES (3,'Philosophy')" +
                " INTO Department VALUES (4,'Math')" +
                " INTO Department VALUES (5,'Physics')" +
                " INTO Department VALUES (6,'Cinema & Photography')" +
                " INTO Department VALUES (7,'Information Technology')" +
                " INTO Department VALUES (8,'Bursar')" +
                " INTO Department VALUES (9,'Admissions')" +
                " INTO Department VALUES (10,'Chemistry')" +
                " SELECT * FROM dual";
        String FacultyData = "INSERT ALL" +
                " INTO Faculty VALUES (1,'Anna Whinery',1)" +
                " INTO Faculty VALUES (2,'Royale Purcell',2)" +
                " INTO Faculty VALUES (3,'Alysha Watkins',3)" +
                " INTO Faculty VALUES (4,'Meghan Wilmer',4)" +
                " INTO Faculty VALUES (5,'Nina Turner',5)" +
                " INTO Faculty VALUES (6,'Fredrick Mottershead',6)" +
                " INTO Faculty VALUES (7,'Angus Aitken',10)" +
                " INTO Faculty VALUES (8,'Andrew Turnbull',1)" +
                " INTO Faculty VALUES (9,'Lenore Proudfoot',2)" +
                " INTO Faculty VALUES (10,'Beatrice Phillips',3)" +
                " SELECT * FROM dual";
        String CoursesData = "INSERT ALL" +
                " INTO Courses VALUES ('CS430','Databases','TTh 2-3:15pm','Faner 1111',1,30)" +
                " INTO Courses VALUES ('IST225','Networking','MWF 9-9:50am','ASA 125',2,35)" +
                " INTO Courses VALUES ('ISAT415','Advanced Networking','TWTh 10-10:50am','ASA 220',3,100)" +
                " INTO Courses VALUES ('CS101','Intro to Computer Science','TTh 1-2:15pm','Lindegren 5',4,50)" +
                " INTO Courses VALUES ('CP101','Intro to Films','MWF 10-10:50am','Parkinson 101',5,200)" +
                " INTO Courses VALUES ('ENG222','Creative Writing','MWF 11-11:50am','Wham 315',6,32)" +
                " INTO Courses VALUES ('CP330','Film as Literary Art','MW 2-4pm','Engineering D128',7,30)" +
                " INTO Courses VALUES ('MAT108','College Algebra','TTh 4-5:15pm','Neckers 229',8,34)" +
                " INTO Courses VALUES ('MAT125','Technical Math','MWF 12-12:50pm','Lawson 185',9,15)" +
                " INTO Courses VALUES ('UC101','University Experience','MWF 1-1:50pm','Morris 601',10,27)" +
                " SELECT * FROM dual";
        String EnrolledData = "INSERT ALL" +
                " INTO Enrolled VALUES (1,'CS430',90,85,97)" +
                " INTO Enrolled VALUES (2,'IST225',50,65,70)" +
                " INTO Enrolled VALUES (3,'ISAT415',100,100,100)" +
                " INTO Enrolled VALUES (4,'CS101',0,0,0)" +
                " INTO Enrolled VALUES (5,'CP101',23,18,35)" +
                " INTO Enrolled VALUES (6,'ENG222',82,77,75)" +
                " INTO Enrolled VALUES (7,'CP330',50,51,52)" +
                " INTO Enrolled VALUES (8,'MAT108',63,18,23)" +
                " INTO Enrolled VALUES (9,'MAT125',53,19,68)" +
                " INTO Enrolled VALUES (10,'UC101',90,97,95)" +
                " SELECT * FROM dual";
        String StaffData = "INSERT ALL" +
                " INTO Staff VALUES (1,'Hubert Davidson',7)" +
                " INTO Staff VALUES (2,'Iona Overton',7)" +
                " INTO Staff VALUES (3,'Raphael Tifft',7)" +
                " INTO Staff VALUES (4,'Anneka Irvine',8)" +
                " INTO Staff VALUES (5,'Carley Revie',9)" +
                " INTO Staff VALUES (6,'Aric Samson',8)" +
                " INTO Staff VALUES (7,'Charnette Gardyner',7)" +
                " INTO Staff VALUES (8,'Reynold Stacy',8)" +
                " INTO Staff VALUES (9,'Vince Hanley',9)" +
                " INTO Staff VALUES (10,'Cynthia Coel',7)" +
                " SELECT * FROM dual";
        // enter data
        runDefineManip(con,StudentData);
        runDefineManip(con,DepartmentData);
        runDefineManip(con,FacultyData);
        runDefineManip(con,CoursesData);
        runDefineManip(con,EnrolledData);
        runDefineManip(con,StaffData);
        return true;
    }
    public boolean ignoreDropError (Integer sqlerror){
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

    public boolean runDrop (Connection con, String statement){
        if (statement == null){
            System.err.println("No Statement given!");
            return false;
        }
        String pattern = "^DROP";
        Pattern regex = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher match = regex.matcher(statement);
        if (!match.find()){
            System.err.println("Not a SQL DROP statement!");
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
                if (ignoreDropError(((SQLException)ex).getErrorCode()) == false) {
                    System.out.println("SQLException: "+ex);
                }
            }
            else {
                System.out.println("SQLException: "+ex);
            }
            return false;
        }
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
            System.out.println("SQLException: "+ex);
            return false;
        }
    }

    public boolean isFacStaff (Connection con, String userid){
        if (userid == null){
            System.err.println("No Statement given!");
            return false;
        }
        try {
            String query = "(SELECT fid FROM Faculty) UNION (SELECT sid from Staff)";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer id = rs.getInt(1);
                if (id.toString().equals(userid)) {
                    rs.close();
                    st.close();
                    return true;
                }
            }
            return false;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return false;
        }
    }

    public boolean isStu (Connection con, String userid){
        if (userid == null){
            System.err.println("No Statement given!");
            return false;
        }
        try {
            String query = "SELECT sid FROM Student";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer id = rs.getInt(1);
                if (id.toString().equals(userid)) {
                    rs.close();
                    st.close();
                    return true;
                }
            }
            return false;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return false;
        }
    }

    /*private Object[][] append (Object[][] obj, Object[] newrow) {
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        temp = Arrays.asList(obj);

        temp.add(newrow);
        return
    }*/

    public Object[][] searchStu (Connection con, Integer sid, String sname, String major, String s_level, Integer age){
        Object[][] obj = null;
        String searchsid = "%";
        String searchage = "%";
        if (sid != null) {
            searchsid = sid.toString();
        }
        if (age != null) {
            searchage = age.toString();
        }
        try {
            String query = "SELECT sid,sname,major,s_level,age FROM Student WHERE sid='"+searchsid+"' AND sname='"+sname+"' AND major='"+major+"' AND s_level='"+s_level+"' AND age='"+searchage+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rsid = rs.getInt(1);
                String Rsname = rs.getString(2);
                String Rmajor = rs.getString(3);
                String Rs_level = rs.getString(4);
                String Rage = rs.getString(5);
                Object[] row = {Rsid.toString(),Rsname,Rmajor,Rs_level,Rage.toString()};
            }
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }
}
