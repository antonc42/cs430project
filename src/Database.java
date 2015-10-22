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
        /*String sqlfile = "schema.sql";
        String line = null;
        try {
            FileReader read = new FileReader(sqlfile);
            BufferedReader buffer = new BufferedReader(read);
            while ((line = buffer.readLine()) != null){
                System.out.println(line);
            }
            buffer.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Cannot open file '"+sqlfile+"'");
        }
        catch (IOException ex){
            System.out.println("Cannot read file '"+sqlfile+"'");
        }*/
        String StudentSchema = "CREATE TABLE IF NOT EXISTS Student (" +
                "sid INTEGER," +
                "sname VARCHAR(100)," +
                "major VARCHAR(50)," +
                "s_level VARCHAR(10)," +
                "age INTEGER," +
                "CONSTRAINT (s_level IN ('freshman','sophomore','junior','senior','master','phd'))," +
                "PRIMARY KEY (sid)" +
                ")";
        String DepartmentSchema = "CREATE TABLE IF NOT EXISTS Department (" +
                "did INTEGER," +
                "dname VARCHAR(100)," +
                "PRIMARY KEY (did)" +
                ")";
        String FacultySchema = "CREATE TABLE IF NOT EXISTS Faculty (" +
                "fid INTEGER," +
                "fname VARCHAR(100)," +
                "depid INTEGER REFERENCES Department (did)," +
                "PRIMARY KEY (fid)" +
                ")";
        String CoursesSchema = "CREATE TABLE IF NOT EXISTS Courses (" +
                "cid VARCHAR(10)," +
                "cname VARCHAR(50)," +
                "meets_at VARCHAR(30)," +
                "room VARCHAR(20)," +
                "fid INTEGER REFERENCES Faculty (fid)," +
                "limit INTEGER," +
                "PRIMARY KEY (cid)" +
                ")";
        String EnrolledSchema = "CREATE TABLE IF NOT EXISTS Enrolled (" +
                "sid INTEGER REFERENCES Student (sid)," +
                "cid VARCHAR(10) REFERENCES Courses (cid)," +
                "exam1 INTEGER CONSTRAINT (exam1 >= 0 AND exam1 <= 100)," +
                "exam2: INTEGER CONSTRAINT (exam2 >= 0 AND exam2 <= 100)," +
                "final INTEGER CONSTRAINT (final >= 0 AND final <= 100)," +
                "PRIMARY KEY (sid, cid)" +
                ")";
        String StaffSchema = "CREATE TABLE IF NOT EXISTS Staff (" +
                "sid INTEGER," +
                "sname VARCHAR(100)," +
                "depid INTEGER REFERENCES Department (did)," +
                "PRIMARY KEY (sid)" +
                ")";
        /*System.out.println(StudentSchema);
        System.out.println(DepartmentSchema);
        System.out.println(FacultySchema);
        System.out.println(CoursesSchema);
        System.out.println(EnrolledSchema);
        System.out.println(StaffSchema);*/
        String clearStudent = "DROP TABLE Student";
        String clearDepartment = "DROP TABLE Department";
        String clearFaculty = "DROP TABLE Faculty";
        String clearCourses = "DROP TABLE Courses";
        String clearEnrolled = "DROP TABLE Enrolled";
        String clearStaff = "DROP TABLE Staff";
        try {
            Statement st = con.createStatement();
            st.executeQuery(clearStudent);
            st.executeQuery(clearDepartment);
            st.executeQuery(clearFaculty);
            st.executeQuery(clearCourses);
            st.executeQuery(clearEnrolled);
            st.executeQuery(clearStaff);
            st.executeQuery(StudentSchema);
            st.executeQuery(DepartmentSchema);
            st.executeQuery(FacultySchema);
            st.executeQuery(CoursesSchema);
            st.executeQuery(EnrolledSchema);
            st.executeQuery(StaffSchema);
            st.close();
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
        }
        return true;
    }
}
