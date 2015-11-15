/**
 * Created by anton on 10/19/15.
 * Class for interacting with database
 */
import java.sql.*;
import java.util.ArrayList;
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
                "deptid INTEGER REFERENCES Department (did)," +
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
                "deptid INTEGER REFERENCES Department (did)," +
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
                " INTO Faculty VALUES (11,'Anna Whinery',1)" +
                " INTO Faculty VALUES (12,'Royale Purcell',2)" +
                " INTO Faculty VALUES (13,'Alysha Watkins',3)" +
                " INTO Faculty VALUES (14,'Meghan Wilmer',4)" +
                " INTO Faculty VALUES (15,'Nina Turner',5)" +
                " INTO Faculty VALUES (16,'Fredrick Mottershead',6)" +
                " INTO Faculty VALUES (17,'Angus Aitken',10)" +
                " INTO Faculty VALUES (18,'Andrew Turnbull',1)" +
                " INTO Faculty VALUES (19,'Lenore Proudfoot',2)" +
                " INTO Faculty VALUES (20,'Beatrice Phillips',3)" +
                " SELECT * FROM dual";
        String CoursesData = "INSERT ALL" +
                " INTO Courses VALUES ('CS430','Databases','TTh 2-3:15pm','Faner 1111',11,30)" +
                " INTO Courses VALUES ('IST225','Networking','MWF 9-9:50am','ASA 125',12,35)" +
                " INTO Courses VALUES ('ISAT415','Advanced Networking','TWTh 10-10:50am','ASA 220',13,100)" +
                " INTO Courses VALUES ('CS101','Intro to Computer Science','TTh 1-2:15pm','Lindegren 5',14,50)" +
                " INTO Courses VALUES ('CP101','Intro to Films','MWF 10-10:50am','Parkinson 101',15,200)" +
                " INTO Courses VALUES ('ENG222','Creative Writing','MWF 11-11:50am','Wham 315',16,32)" +
                " INTO Courses VALUES ('CP330','Film as Literary Art','MW 2-4pm','Engineering D128',17,30)" +
                " INTO Courses VALUES ('MAT108','College Algebra','TTh 4-5:15pm','Neckers 229',18,34)" +
                " INTO Courses VALUES ('MAT125','Technical Math','MWF 12-12:50pm','Lawson 185',19,15)" +
                " INTO Courses VALUES ('UC101','University Experience','MWF 1-1:50pm','Morris 601',20,27)" +
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
                " INTO Staff VALUES (21,'Hubert Davidson',7)" +
                " INTO Staff VALUES (22,'Iona Overton',7)" +
                " INTO Staff VALUES (23,'Raphael Tifft',7)" +
                " INTO Staff VALUES (24,'Anneka Irvine',8)" +
                " INTO Staff VALUES (25,'Carley Revie',9)" +
                " INTO Staff VALUES (26,'Aric Samson',8)" +
                " INTO Staff VALUES (27,'Charnette Gardyner',7)" +
                " INTO Staff VALUES (28,'Reynold Stacy',8)" +
                " INTO Staff VALUES (29,'Vince Hanley',9)" +
                " INTO Staff VALUES (30,'Cynthia Coel',7)" +
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

    public boolean isFac (Connection con, String userid){
        if (userid == null){
            System.err.println("No Statement given!");
            return false;
        }
        try {
            String query = "SELECT fid FROM Faculty";
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

    public boolean isStaff (Connection con, String userid){
        if (userid == null){
            System.err.println("No Statement given!");
            return false;
        }
        try {
            String query = "SELECT sid from Staff";
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

    public Integer findDepID (Connection con, String dname) {
        try {
            String depquery = "SELECT did FROM Department WHERE dname=?";
            PreparedStatement ps = con.prepareStatement(depquery);
            ps.setString(1,dname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer Rdid = rs.getInt(1);
                rs.close();
                ps.close();
                return Rdid;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+e);
            return null;
        }
        return null;
    }

    public Object[] getDepList (Connection con) {
        Object[] deps = null;
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            String query = "SELECT dname FROM Department ORDER BY dname";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            deps = new Object[list.size()];
            list.toArray(deps);
            rs.close();
            st.close();
            return deps;
        } catch (SQLException e) {
            e.printStackTrace();
            return deps;
        }
    }

    public Object[][] searchStu (Connection con){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        try {
            String query = "SELECT sid,sname,major,s_level,age FROM Student ORDER BY sid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rsid = rs.getInt(1);
                String Rsname = rs.getString(2);
                String Rmajor = rs.getString(3);
                String Rs_level = rs.getString(4);
                Integer Rage = rs.getInt(5);
                Object[] row = {Rsid.toString(),Rsname,Rmajor,Rs_level,Rage.toString()};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchStu (Connection con, Integer sid, String sname, String major, String s_level, Integer age){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        // if nothing was passed in, don't try to build the sql expression - it will just throw a sql exception
        // because of the dangling "WHEN"
        if ((sid ==null || sid == -1) && (sname == null || sname.equals("-1")) &&
                (major == null || major.equals("-1")) && (s_level == null || s_level.equals("-1")) &&
                (age == null || age == -1)) {
            return obj;
        }
        try {
            String buildquery = "SELECT sid,sname,major,s_level,age FROM Student WHERE ";
            if (sid != null && sid != -1) {
                buildquery += "sid='"+sid+"' AND ";
            }
            if (sname != null && !sname.equals("-1")) {
                buildquery += "sname='"+sname+"' AND ";
            }
            if (major != null && !major.equals("-1")) {
                buildquery += "major='"+major+"' AND ";
            }
            if (s_level != null && !s_level.equals("-1")) {
                buildquery += "s_level='"+s_level+"' AND ";
            }
            if (age != null && age != -1) {
                buildquery += "age='"+age+"' AND ";
            }
            Pattern lastand = Pattern.compile(" AND \\Z");
            Matcher m = lastand.matcher(buildquery);
            String query = m.replaceAll("");
            query += "ORDER BY sid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rsid = rs.getInt(1);
                String Rsname = rs.getString(2);
                String Rmajor = rs.getString(3);
                String Rs_level = rs.getString(4);
                Integer Rage = rs.getInt(5);
                Object[] row = {Rsid.toString(),Rsname,Rmajor,Rs_level,Rage.toString()};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public void editStu (Connection con, Integer sid, String sname, String major, String s_level, Integer age) {
        // if nothing was passed in, do nothing
        if ((sid ==null || sid == -1) && (sname == null || sname.equals("-1")) &&
                (major == null || major.equals("-1")) && (s_level == null || s_level.equals("-1")) &&
                (age == null || age == -1)) {
            return;
        }
        try {
            String buildquery = "UPDATE Student SET ";
            if (sid == null || sid == -1) {
                // cannot edit row without primary key
                return;
            }
            if (sname != null && !sname.equals("-1")) {
                buildquery += "sname='"+sname+"',";
            }
            if (major != null && !major.equals("-1")) {
                buildquery += "major='"+major+"',";
            }
            // cannot have empty s_level due to constraint
            if (s_level != null && !s_level.equals("-1") && !s_level.isEmpty()) {
                buildquery += "s_level='"+s_level+"',";
            }
            if (age != null && age != -1) {
                buildquery += "age='"+age+"',";
            }
            // remove trailing comma
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " WHERE sid="+sid;
            Statement st = con.createStatement();
            st.executeUpdate(buildquery);
            st.close();
            return;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return;
        }
    }

    public void newStu (Connection con, Integer sid, String sname, String major, String s_level, Integer age) {
        // if nothing was passed in, do nothing
        if ((sid ==null || sid == -1) && (sname == null || sname.equals("-1")) &&
                (major == null || major.equals("-1")) && (s_level == null || s_level.equals("-1")) &&
                (age == null || age == -1)) {
            return;
        }
        try {
            String buildquery = "INSERT INTO Student ";
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<String> vals = new ArrayList<String>();
            if (sid != null && sid != -1) {
                cols.add("sid");
                vals.add(sid.toString());
            }
            else {
                // cannot enter row without primary key
                return;
            }
            if (sname != null && !sname.equals("-1")) {
                cols.add("sname");
                vals.add(sname);
            }
            if (major != null && !major.equals("-1")) {
                cols.add("major");
                vals.add(major);
            }
            if (s_level != null && !s_level.equals("-1")) {
                cols.add("s_level");
                vals.add(s_level);
            }
            else {
                // cannot enter row without level due to constraint
                return;
            }
            if (age != null && age != -1) {
                cols.add("age");
                vals.add(age.toString());
            }
            buildquery += "(";
            for (String c : cols) {
                buildquery += c + ",";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ") VALUES (";
            for (String v : vals) {
                buildquery += "'" + v + "',";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ")";
            Statement st = con.createStatement();
            st.executeUpdate(buildquery);
            st.close();
            return;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return;
        }
    }

    public void delStu (Connection con, Integer sid) {
        // cannot do anything without id
        if (sid ==null || sid == -1) {
            return;
        }
        try {
            String query = "DELETE FROM Student WHERE sid=" + sid;
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            return;
        }
        catch (Exception ex) {
            System.out.println("SQLException: " + ex);
            return;
        }
    }

    public Object[][] searchFac (Connection con){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        try {
            String query = "SELECT fid,fname,dname FROM Faculty JOIN Department ON Faculty.deptid=Department.did ORDER BY fid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rfid = rs.getInt(1);
                String Rfname = rs.getString(2);
                String Rdname = rs.getString(3);
                Object[] row = {Rfid.toString(),Rfname,Rdname};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchFac (Connection con, Integer fid, String fname, String dname){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        // if nothing was passed in, don't try to build the sql expression - it will just throw a sql exception
        // because of the dangling "WHEN"
        if ((fid ==null || fid == -1) && (fname == null || fname.equals("-1")) &&
                (dname == null || dname.equals("-1"))) {
            return obj;
        }
        try {
            String buildquery = "SELECT fid,fname,dname FROM Faculty JOIN Department ON Faculty.deptid=Department.did WHERE ";
            if (fid != null && fid != -1) {
                buildquery += "fid='"+fid+"' AND ";
            }
            if (fname != null && !fname.equals("-1")) {
                buildquery += "fname='"+fname+"' AND ";
            }
            if (dname != null && !dname.equals("-1")) {
                buildquery += "dname='"+dname+"' AND ";
            }
            Pattern lastand = Pattern.compile(" AND \\Z");
            Matcher m = lastand.matcher(buildquery);
            String query = m.replaceAll("");
            query += "ORDER BY fid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rfid = rs.getInt(1);
                String Rfname = rs.getString(2);
                String Rdname = rs.getString(3);
                Object[] row = {Rfid.toString(),Rfname,Rdname};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public void editFac (Connection con, Integer fid, String fname, String dname) {
        // if nothing was passed in, do nothing
        if ((fid ==null || fid == -1) && (fname == null || fname.equals("-1")) &&
                (dname == null || dname.equals("-1"))) {
            return;
        }
        try {
            String buildquery = "UPDATE Faculty SET ";
            if (fid == null || fid == -1) {
                // cannot edit row without primary key
                return;
            }
            if (fname != null && !fname.equals("-1")) {
                buildquery += "fname='"+fname+"',";
            }
            if (dname != null && !dname.equals("-1") && !dname.isEmpty()) {
                Integer deptid = findDepID(con,dname);
                if (deptid != null) {
                    buildquery += "deptid='" + deptid + "',";
                }
            }
            // remove trailing comma
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " WHERE fid="+fid;
            Statement st = con.createStatement();
            st.executeUpdate(buildquery);
            st.close();
            return;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return;
        }
    }

    public void newFac (Connection con, Integer fid, String fname, String dname) {
        // if nothing was passed in, do nothing
        if ((fid ==null || fid == -1) && (fname == null || fname.equals("-1")) &&
                (dname == null || dname.equals("-1"))) {
            return;
        }
        try {
            String buildquery = "INSERT INTO Faculty ";
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<String> vals = new ArrayList<String>();
            if (fid != null && fid != -1) {
                cols.add("fid");
                vals.add(fid.toString());
            }
            else {
                // cannot enter row without primary key
                return;
            }
            if (fname != null && !fname.equals("-1")) {
                cols.add("fname");
                vals.add(fname);
            }
            if (dname != null && !dname.equals("-1")) {
                Integer deptid = findDepID(con,dname);
                if (deptid != null) {
                    cols.add("deptid");
                    vals.add(deptid.toString());
                }
            }
            buildquery += "(";
            for (String c : cols) {
                buildquery += c + ",";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ") VALUES (";
            for (String v : vals) {
                buildquery += "'" + v + "',";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ")";
            Statement st = con.createStatement();
            st.executeUpdate(buildquery);
            st.close();
            return;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return;
        }
    }

    public void delFac (Connection con, Integer fid) {
        // cannot do anything without id
        if (fid ==null || fid == -1) {
            return;
        }
        try {
            String query = "DELETE FROM Faculty WHERE fid=" + fid;
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            return;
        }
        catch (Exception ex) {
            System.out.println("SQLException: " + ex);
            return;
        }
    }

    public Object[][] searchSta (Connection con){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        try {
            String query = "SELECT sid,sname,dname FROM Staff JOIN Department ON Staff.deptid=Department.did ORDER BY sid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rsid = rs.getInt(1);
                String Rsname = rs.getString(2);
                String Rdname = rs.getString(3);
                Object[] row = {Rsid.toString(),Rsname,Rdname};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchSta (Connection con, Integer sid, String sname, Integer deptid){
        Object[][] obj = null;
        ArrayList<Object[]> temp = null;
        String searchsid = "%";
        String searchdid = "%";
        if (sid != null) {
            searchsid = sid.toString();
        }
        if (deptid != null) {
            searchdid = deptid.toString();
        }
        try {
            String query = "SELECT sid,sname,deptid FROM Staff WHERE sid='"+searchsid+"' AND " +
                    "sname='"+sname+"' AND deptid='"+searchdid+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rsid = rs.getInt(1);
                String Rsname = rs.getString(2);
                Integer Rdid = rs.getInt(3);
                Object[] row = {Rsid.toString(),Rsname,Rdid.toString()};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchDep (Connection con){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        try {
            String query = "SELECT did,dname FROM Department ORDER BY did";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rdid = rs.getInt(1);
                String Rdname = rs.getString(2);
                Object[] row = {Rdid.toString(),Rdname};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchDep (Connection con, Integer did, String dname){
        Object[][] obj = null;
        ArrayList<Object[]> temp = null;
        String searchdid = "%";
        if (did != null) {
            searchdid = did.toString();
        }
        try {
            String query = "SELECT did,dname FROM Department WHERE did='"+searchdid+"' AND dname='"+dname+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rdid = rs.getInt(1);
                String Rdname = rs.getString(2);
                Object[] row = {Rdid.toString(),Rdname};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchCor (Connection con){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        try {
            String query = "SELECT cid,cname,meets_at,room,fname,limit FROM Courses JOIN Faculty ON Courses.fid=Faculty.fid ORDER BY cid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String Rcid = rs.getString(1);
                String Rcname = rs.getString(2);
                String Rmeets_at = rs.getString(3);
                String Rroom = rs.getString(4);
                String Rfname = rs.getString(5);
                Integer Rlimit = rs.getInt(6);
                Object[] row = {Rcid,Rcname,Rmeets_at,Rroom,Rfname,Rlimit.toString()};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchCor (Connection con, String cid, String cname, String meets_at, String room, Integer fid,
                                 Integer limit){
        Object[][] obj = null;
        ArrayList<Object[]> temp = null;
        String searchfid = "%";
        String searchlim = "%";
        if (fid != null) {
            searchfid = fid.toString();
        }
        if (limit != null) {
            searchlim = limit.toString();
        }
        try {
            String query = "SELECT cid,cname,meets_at,room,fid,limit FROM Courses WHERE cid='"+searchfid+"' AND " +
                    "cname='"+cname+"' AND meets_at='"+meets_at+"' AND room='"+room+"' AND fid='"+fid+"' AND " +
                    "limit='"+searchlim+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String Rcid = rs.getString(1);
                String Rcname = rs.getString(2);
                String Rmeets = rs.getString(3);
                String Rroom = rs.getString(4);
                Integer Rfid = rs.getInt(5);
                Integer Rlim = rs.getInt(6);
                Object[] row = {Rcid,Rcname,Rmeets,Rroom,Rfid.toString(),Rlim.toString()};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchEnrl (Connection con){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        try {
            String query = "SELECT cid,sname,exam1,exam2,final FROM Enrolled JOIN Student ON Enrolled.sid=Student.sid ORDER BY cid,Enrolled.sid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String Rcid = rs.getString(1);
                String Rsname = rs.getString(2);
                Integer Rexam1 = rs.getInt(3);
                Integer Rexam2 = rs.getInt(4);
                Integer Rfinal = rs.getInt(5);
                Object[] row = {Rcid,Rsname,Rexam1.toString(),Rexam2.toString(),Rfinal.toString()};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }

    public Object[][] searchEnrl (Connection con, Integer sid, String cid, Integer exam1, Integer exam2, Integer finalg){
        Object[][] obj = null;
        ArrayList<Object[]> temp = null;
        String searchsid = "%";
        String searchexam1 = "%";
        String searchexam2 = "%";
        String searchfinal = "%";
        if (sid != null) {
            searchsid = sid.toString();
        }
        if (exam1 != null) {
            searchexam1 = exam1.toString();
        }
        if (exam2 != null) {
            searchexam2 = exam2.toString();
        }
        if (finalg != null) {
            searchfinal= finalg.toString();
        }
        try {
            String query = "SELECT sid,cid,exam1,exam2,final FROM Enrolled WHERE sid='"+searchsid+"' AND " +
                    "cid='"+cid+"' AND exam1='"+searchexam1+"' AND exam2='"+searchexam2+"' AND final='"+searchfinal+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Integer Rsid = rs.getInt(1);
                String Rcid = rs.getString(2);
                Integer Rexam1 = rs.getInt(3);
                Integer Rexam2 = rs.getInt(4);
                Integer Rfinal = rs.getInt(5);
                Object[] row = {Rsid.toString(),Rcid,Rexam1.toString(),Rexam2.toString(),Rfinal.toString()};
                temp.add(row);
            }
            obj = new Object[temp.size()][];
            temp.toArray(obj);
            rs.close();
            st.close();
            return obj;
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
            return obj;
        }
    }


}