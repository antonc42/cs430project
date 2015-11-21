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

    public boolean isCourse (Connection con, String cid) {
        if (cid == null || cid.isEmpty()){
            System.err.println("No Course given!");
            return false;
        }
        try {
            String query = "SELECT cid FROM Courses WHERE cid='"+cid+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()){
                String id = rs.getString(1);
                if (id.toString().equals(cid)) {
                    rs.close();
                    st.close();
                    return true;
                }
                else { return false; }
            }
            else {
                return false;
            }
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

    public Integer findFacID (Connection con, String fname) {
        try {
            String depquery = "SELECT fid FROM Faculty WHERE fname=?";
            PreparedStatement ps = con.prepareStatement(depquery);
            ps.setString(1,fname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer Rfid = rs.getInt(1);
                rs.close();
                ps.close();
                return Rfid;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+e);
            return null;
        }
        return null;
    }

    public Integer findStuID (Connection con, String fname) {
        try {
            String depquery = "SELECT sid FROM Student WHERE sname=?";
            PreparedStatement ps = con.prepareStatement(depquery);
            ps.setString(1,fname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer Rsid = rs.getInt(1);
                rs.close();
                ps.close();
                return Rsid;
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

    public Object[] getFacList (Connection con) {
        Object[] deps = null;
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            String query = "SELECT fname FROM Faculty ORDER BY fname";
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

    public Object[] getCorList (Connection con) {
        Object[] deps = null;
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            String query = "SELECT cid FROM Courses ORDER BY cid";
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

    public Object[] getStuList (Connection con) {
        Object[] deps = null;
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            String query = "SELECT sname FROM Student ORDER BY sname";
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

    // student table
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
            if (sname != null && !sname.equals("-1") && !sname.isEmpty()) {
                buildquery += "sname='"+sname+"',";
            }
            // name cannot be empty
            else { return; }
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
            if (sname != null && !sname.equals("-1") && !sname.isEmpty()) {
                cols.add("sname");
                vals.add(sname);
            }
            // name cannot be empty
            else { return; }
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
            buildquery += ") SELECT ";
            for (String v : vals) {
                buildquery += "'" + v + "',";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " FROM DUAL WHERE NOT EXISTS (" +
                    "(SELECT fid FROM Faculty WHERE fid="+sid+")" +
                    " UNION " +
                    "(SELECT sid from Staff WHERE sid="+sid+"))";
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

    // faculty table
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
            if (fname != null && !fname.equals("-1") && !fname.isEmpty()) {
                buildquery += "fname='"+fname+"',";
            }
            // name cannot be empty
            else { return; }
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
            if (fname != null && !fname.equals("-1") && !fname.isEmpty()) {
                cols.add("fname");
                vals.add(fname);
            }
            // name cannot be empty
            else { return; }
            if (dname != null && !dname.equals("-1") && !dname.isEmpty()) {
                Integer deptid = findDepID(con,dname);
                if (deptid != null) {
                    cols.add("deptid");
                    vals.add(deptid.toString());
                }
                // cannot have invalid department
                else { return; }
            }
            // cannot have empty department
            else { return; }
            buildquery += "(";
            for (String c : cols) {
                buildquery += c + ",";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ") SELECT ";
            for (String v : vals) {
                buildquery += "'" + v + "',";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " FROM DUAL WHERE NOT EXISTS (" +
                    "(SELECT sid from Student WHERE sid="+fid+")" +
                    " UNION " +
                    "(SELECT sid from Staff WHERE sid="+fid+"))";
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

    // staff table
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

    public Object[][] searchSta (Connection con, Integer sid, String sname, String dname){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        // if nothing was passed in, don't try to build the sql expression - it will just throw a sql exception
        // because of the dangling "WHEN"
        if ((sid ==null || sid == -1) && (sname == null || sname.equals("-1")) &&
                (dname == null || dname.equals("-1"))) {
            return obj;
        }
        try {
            String buildquery = "SELECT sid,sname,dname FROM Staff JOIN Department ON Staff.deptid=Department.did WHERE ";
            if (sid != null && sid != -1) {
                buildquery += "sid='"+sid+"' AND ";
            }
            if (sname != null && !sname.equals("-1")) {
                buildquery += "sname='"+sname+"' AND ";
            }
            if (dname != null && !dname.equals("-1")) {
                buildquery += "dname='"+dname+"' AND ";
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

    public void editSta (Connection con, Integer sid, String sname, String dname) {
        // if nothing was passed in, do nothing
        if ((sid ==null || sid == -1) && (sname == null || sname.equals("-1")) &&
                (dname == null || dname.equals("-1"))) {
            return;
        }
        try {
            String buildquery = "UPDATE Staff SET ";
            if (sid == null || sid == -1) {
                // cannot edit row without primary key
                return;
            }
            if (sname != null && !sname.equals("-1") && !sname.isEmpty()) {
                buildquery += "sname='"+sname+"',";
            }
            // name cannot be empty
            else { return; }
            if (dname != null && !dname.equals("-1") && !dname.isEmpty()) {
                Integer deptid = findDepID(con,dname);
                if (deptid != null) {
                    buildquery += "deptid='" + deptid + "',";
                }
                // department cannot be invalid
                else { return; }
            }
            // department cannot be empty
            else { return; }
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

    public void newSta (Connection con, Integer sid, String sname, String dname) {
        // if nothing was passed in, do nothing
        if ((sid ==null || sid == -1) && (sname == null || sname.equals("-1")) &&
                (dname == null || dname.equals("-1"))) {
            return;
        }
        try {
            String buildquery = "INSERT INTO Staff ";
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
            if (sname != null && !sname.equals("-1") && ! sname.isEmpty()) {
                cols.add("sname");
                vals.add(sname);
            }
            // name cannot be empty
            else { return; }
            if (dname != null && !dname.equals("-1") && !dname.isEmpty()) {
                Integer deptid = findDepID(con,dname);
                if (deptid != null) {
                    cols.add("deptid");
                    vals.add(deptid.toString());
                }
                // department cannot be invalid
                else { return; }
            }
            // departement cannot be empty
            else { return; }
            buildquery += "(";
            for (String c : cols) {
                buildquery += c + ",";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ") SELECT ";
            for (String v : vals) {
                buildquery += "'" + v + "',";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " FROM DUAL WHERE NOT EXISTS (" +
                    "(SELECT fid FROM Faculty WHERE fid="+sid+")" +
                    " UNION " +
                    "(SELECT sid FROM Student WHERE sid="+sid+"))";
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

    public void delSta (Connection con, Integer sid) {
        // cannot do anything without id
        if (sid ==null || sid == -1) {
            return;
        }
        try {
            String query = "DELETE FROM Staff WHERE sid=" + sid;
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

    // department table
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
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        // if nothing was passed in, don't try to build the sql expression - it will just throw a sql exception
        // because of the dangling "WHEN"
        if ((did ==null || did == -1) && (dname == null || dname.equals("-1"))) {
            return obj;
        }
        try {
            String buildquery = "SELECT did,dname FROM Department WHERE ";
            if (did != null && did != -1) {
                buildquery += "did='"+did+"' AND ";
            }
            if (dname != null && !dname.equals("-1")) {
                buildquery += "dname='"+dname+"' AND ";
            }
            Pattern lastand = Pattern.compile(" AND \\Z");
            Matcher m = lastand.matcher(buildquery);
            String query = m.replaceAll("");
            query += "ORDER BY did";
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

    public void editDep (Connection con, Integer did, String dname) {
        // if nothing was passed in, do nothing
        if ((did ==null || did == -1) && (dname == null || dname.equals("-1"))) {
            return;
        }
        try {
            String buildquery = "UPDATE Department SET ";
            if (did == null || did == -1) {
                // cannot edit row without primary key
                return;
            }
            if (dname != null && !dname.equals("-1") && !dname.isEmpty()) {
                buildquery += "dname='"+dname+"',";
            }
            // name cannot be empty
            else { return; }
            // remove trailing comma
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " WHERE did="+did;
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

    public void newDep (Connection con, Integer did, String dname) {
        // if nothing was passed in, do nothing
        if ((did ==null || did == -1) && (dname == null || dname.equals("-1"))) {
            return;
        }
        try {
            String buildquery = "INSERT INTO Department ";
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<String> vals = new ArrayList<String>();
            if (did != null && did != -1) {
                cols.add("did");
                vals.add(did.toString());
            }
            else {
                // cannot enter row without primary key
                return;
            }
            if (dname != null && !dname.equals("-1") && !dname.isEmpty()) {
                cols.add("dname");
                vals.add(dname);
            }
            // name cannot be empty
            else { return; }
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

    public void delDep (Connection con, Integer did) {
        // cannot do anything without id
        if (did ==null || did == -1) {
            return;
        }
        try {
            String query = "DELETE FROM Department WHERE did=" + did;
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

    // courses table
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

    public Object[][] searchCor (Connection con, String cid, String cname, String meets_at, String room, String fname,
                                 Integer limit){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        // if nothing was passed in, don't try to build the sql expression - it will just throw a sql exception
        // because of the dangling "WHEN"
        if ((cid ==null || cid.equals("-1")) && (cname == null || cname.equals("-1")) && (meets_at == null ||
                meets_at.equals("-1")) && (room == null || room.equals("-1")) && (fname == null ||
                fname.equals("-1")) && (limit == null || limit ==-1)) {
            return obj;
        }
        try {
            String buildquery = "SELECT cid,cname,meets_at,room,fname,limit FROM Courses JOIN Faculty ON Courses.fid=Faculty.fid WHERE ";
            if (cid != null && !cid.equals("-1")) {
                buildquery += "cid='"+cid+"' AND ";
            }
            if (cname != null && !cname.equals("-1")) {
                buildquery += "cname='"+cname+"' AND ";
            }
            if (meets_at != null && !meets_at.equals("-1")) {
                buildquery += "meets_at='"+meets_at+"' AND ";
            }
            if (room != null && !room.equals("-1")) {
                buildquery += "room='"+room+"' AND ";
            }
            if (fname != null && !fname.equals("-1")) {
                buildquery += "fname='"+fname+"' AND ";
            }
            if (limit != null && limit !=-1) {
                buildquery += "limit="+limit+" AND ";
            }
            Pattern lastand = Pattern.compile(" AND \\Z");
            Matcher m = lastand.matcher(buildquery);
            String query = m.replaceAll("");
            query += "ORDER BY cid";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String Rcid = rs.getString(1);
                String Rcname = rs.getString(2);
                String Rmeets_at = rs.getString(3);
                String Rroom = rs.getString(4);
                String Rfname = rs.getString(5);
                Integer Rlimit = rs.getInt(6);
                Object[] row = {Rcid.toString(),Rcname,Rmeets_at,Rroom,Rfname,Rlimit.toString()};
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

    public void editCor (Connection con, String cid, String cname, String meets_at, String room, String fname,
                         Integer limit) {
        // if nothing was passed in, do nothing
        if ((cid ==null || cid.equals("-1")) && (cname == null || cname.equals("-1")) && (meets_at == null ||
                meets_at.equals("-1")) && (room == null || room.equals("-1")) && (fname == null ||
                fname.equals("-1")) && (limit == null || limit ==-1)) {
            return;
        }
        try {
            String buildquery = "UPDATE Courses SET ";
            if (cid == null || cid.equals("-1")) {
                // cid cannot be null
                return;
            }
            if (cname != null && !cname.equals("-1")) {
                buildquery += "cname='"+cname+"',";
            }
            if (meets_at != null && !meets_at.equals("-1")) {
                buildquery += "meets_at='"+meets_at+"',";
            }
            if (room != null && !room.equals("-1")) {
                buildquery += "room='"+room+"',";
            }
            if (fname != null && !fname.equals("-1") && !fname.isEmpty()) {
                Integer fid = findFacID(con, fname);
                if (fid != null) {
                    buildquery += "fid='" + fid + "',";
                }
                // faculty cannot be invalid
                else { return; }
            }
            // faculty cannot be empty
            else { return; }
            if (limit != null && limit !=-1) {
                buildquery += "limit='"+limit+"',";
            }
            // remove trailing comma
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " WHERE cid='"+cid+"'";
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

    public void newCor (Connection con, String cid, String cname, String meets_at, String room, String fname,
                        Integer limit) {
        // if nothing was passed in, do nothing
        if ((cid ==null || cid.equals("-1")) && (cname == null || cname.equals("-1")) && (meets_at == null ||
                meets_at.equals("-1")) && (room == null || room.equals("-1")) && (fname == null ||
                fname.equals("-1")) && (limit == null || limit ==-1)) {
            return;
        }
        try {
            String buildquery = "INSERT INTO Courses ";
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<String> vals = new ArrayList<String>();
            if (cid != null && !cid.equals("-1")) {
                cols.add("cid");
                vals.add(cid);
            }
            else {
                // cannot enter row without primary key
                return;
            }
            if (cname != null && !cname.equals("-1")) {
                cols.add("cname");
                vals.add(cname);
            }
            if (meets_at != null && !meets_at.equals("-1")) {
                cols.add("meets_at");
                vals.add(meets_at);
            }
            if (room != null && !room.equals("-1")) {
                cols.add("room");
                vals.add(room);
            }
            if (fname != null && !fname.equals("-1") && !fname.isEmpty()) {
                Integer fid = findFacID(con, fname);
                if (fid != null) {
                    cols.add("fid");
                    vals.add(fid.toString());
                }
                // faculty cannot be invalid
                else { return; }
            }
            // faculty cannot be empty
            else { return; }
            if (limit != null && limit != -1) {
                cols.add("limit");
                vals.add(limit.toString());
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

    public void delCor (Connection con, String cid) {
        // cannot do anything without id
        if (cid ==null || cid.equals("-1")) {
            return;
        }
        try {
            String query = "DELETE FROM Courses WHERE cid='" + cid +"'";
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

    // enrolled table
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

    public Object[][] searchEnrl (Connection con, String cid, String sname, Integer exam1, Integer exam2, Integer finalg){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        // if nothing was passed in, don't try to build the sql expression - it will just throw a sql exception
        // because of the dangling "WHEN"
        if ((cid ==null || cid.equals("-1")) && (sname == null || sname.equals("-1")) && (exam1 == null ||
                exam1 == -1) && (exam2 == null || exam2 == -1) && (finalg == null || finalg ==-1)) {
            return obj;
        }
        try {
            String buildquery = "SELECT cid,sname,exam1,exam2,final FROM Enrolled JOIN Student ON Enrolled.sid=Student.sid WHERE ";
            if (cid != null && !cid.equals("-1")) {
                buildquery += "cid='"+cid+"' AND ";
            }
            if (sname != null && !sname.equals("-1")) {
                buildquery += "sname='"+sname+"' AND ";
            }
            if (exam1 != null && exam1 != -1) {
                buildquery += "exam1='"+exam1+"' AND ";
            }
            if (exam2 != null && exam2 != -1) {
                buildquery += "exam2='"+exam2+"' AND ";
            }
            if (finalg != null && finalg !=-1) {
                buildquery += "finalg="+finalg+" AND ";
            }
            Pattern lastand = Pattern.compile(" AND \\Z");
            Matcher m = lastand.matcher(buildquery);
            String query = m.replaceAll("");
            query += "ORDER BY cid,sname";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String Rcid = rs.getString(1);
                String Rsname = rs.getString(2);
                Integer Rexam1 = rs.getInt(3);
                Integer Rexam2 = rs.getInt(4);
                Integer Rfinalg = rs.getInt(5);
                Object[] row = {Rcid.toString(),Rsname,Rexam1.toString(),Rexam2.toString(),Rfinalg.toString()};
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

    public void editEnrl (Connection con, String cid, String sname, Integer exam1, Integer exam2, Integer finalg) {
        // if nothing was passed in, do nothing
        if ((cid ==null || cid.equals("-1")) && (sname == null || sname.equals("-1")) && (exam1 == null ||
                exam1 == -1) && (exam2 == null || exam2 == -1) && (finalg == null || finalg ==-1)) {
            return;
        }
        try {
            String buildquery = "UPDATE Enrolled SET ";
            Integer sid;
            if (cid == null || cid.equals("-1")) {
                // cid cannot be null
                return;
            }
            if (sname != null && !sname.equals("-1") && !sname.isEmpty()) {
                Integer tempid = findStuID(con,sname);
                if (tempid != null) {
                    sid = tempid;
                }
                // student cannot be invalid
                else { return; }
            }
            // student cannot be empty
            else { return; }
            if (exam1 != null && exam1 != -1) {
                buildquery += "exam1="+exam1+",";
            }
            if (exam2 != null && exam2 != -1) {
                buildquery += "exam2="+exam2+",";
            }
            if (finalg != null && finalg !=-1) {
                buildquery += "final="+finalg+",";
            }
            // remove trailing comma
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " WHERE cid='"+cid+"' AND sid="+sid;
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

    public void newEnrl (Connection con, String cid, String sname, Integer exam1, Integer exam2, Integer finalg) {
        // if nothing was passed in, do nothing
        if ((cid ==null || cid.equals("-1")) && (sname == null || sname.equals("-1")) && (exam1 == null ||
                exam1 == -1) && (exam2 == null || exam2 == -1) && (finalg == null || finalg ==-1)) {
            return;
        }
        try {
            String buildquery = "INSERT INTO Enrolled ";
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<String> vals = new ArrayList<String>();
            if (cid != null && !cid.equals("-1")) {
                cols.add("cid");
                vals.add(cid);
            }
            else {
                // cannot enter row without primary key
                return;
            }
            if (sname != null && !sname.equals("-1") && !sname.isEmpty()) {
                Integer sid = findStuID(con,sname);
                if (sid != null) {
                    cols.add("sid");
                    vals.add(sid.toString());
                }
                // student cannot be invalid
                else { return; }
            }
            // student cannot be empty
            else { return; }
            if (exam1 != null && exam1 != -1) {
                cols.add("exam1");
                vals.add(exam1.toString());
            }
            if (exam2 != null && exam2 != -1) {
                cols.add("exam2");
                vals.add(exam2.toString());
            }
            if (finalg != null && finalg != -1) {
                cols.add("final");
                vals.add(finalg.toString());
            }
            buildquery += "(";
            for (String c : cols) {
                buildquery += c + ",";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ") SELECT ";
            for (String v : vals) {
                buildquery += "'" + v + "',";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " FROM DUAL " +
                    "WHERE '"+cid+"' NOT IN (" +
                    "SELECT cid " +
                    "FROM FullCourses" +
                    ")";
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

    public void newEnrl (Connection con, String cid, Integer sid, Integer exam1, Integer exam2, Integer finalg) {
        // if nothing was passed in, do nothing
        if ((cid ==null || cid.equals("-1")) && (sid == null || sid ==-1) && (exam1 == null ||
                exam1 == -1) && (exam2 == null || exam2 == -1) && (finalg == null || finalg ==-1)) {
            return;
        }
        try {
            String buildquery = "INSERT INTO Enrolled ";
            ArrayList<String> cols = new ArrayList<String>();
            ArrayList<String> vals = new ArrayList<String>();
            if (cid != null && !cid.equals("-1")) {
                cols.add("cid");
                vals.add(cid);
            }
            else {
                // cannot enter row without primary key
                return;
            }
            if (sid != null && sid != -1) {
                cols.add("sid");
                vals.add(sid.toString());
            }
            // student cannot be empty
            else { return; }
            if (exam1 != null && exam1 != -1) {
                cols.add("exam1");
                vals.add(exam1.toString());
            }
            if (exam2 != null && exam2 != -1) {
                cols.add("exam2");
                vals.add(exam2.toString());
            }
            if (finalg != null && finalg != -1) {
                cols.add("final");
                vals.add(finalg.toString());
            }
            buildquery += "(";
            for (String c : cols) {
                buildquery += c + ",";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += ") SELECT ";
            for (String v : vals) {
                buildquery += "'" + v + "',";
            }
            if (buildquery.endsWith(",")) {
                buildquery = buildquery.substring(0,buildquery.length() - 1);
            }
            buildquery += " FROM DUAL " +
                    "WHERE '"+cid+"' NOT IN (" +
                    "SELECT cid " +
                    "FROM FullCourses" +
                    ")";
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

    public void delEnrl (Connection con, String cid, String sname) {
        Integer sid;
        // cannot do anything without id
        if (cid ==null || cid.equals("-1")) {
            return;
        }
        if (sname != null && !sname.equals("-1") && !sname.isEmpty()) {
            Integer tempid = findStuID(con,sname);
            if (tempid != null) {
                sid = tempid;
            }
            // student cannot be invalid
            else { return; }
        }
        // student cannot be null
        else { return; }
        try {
            String query = "DELETE FROM Enrolled WHERE cid='" + cid +"' AND sid="+sid;
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

    // student courses
    public Object[][] getEnrolled (Connection con, Integer sid){
        Object[][] obj = null;
        ArrayList<Object[]> temp = new ArrayList<Object[]>();
        // if nothing was passed in, don't try to build the sql expression - it will just throw a sql exception
        // because of the dangling "WHEN"
        if ((sid == null || sid ==-1)) {
            return obj;
        }
        try {
            String buildquery = "SELECT Enrolled.cid,cname,meets_at,room,fname,enrl,limit,exam1,exam2,final " +
                    "FROM Enrolled " +
                    "JOIN EnrollCount " +
                    "ON Enrolled.cid=EnrollCount.cid " +
                    "JOIN Courses " +
                    "ON Enrolled.cid=Courses.cid " +
                    "JOIN Faculty " +
                    "ON Courses.fid=Faculty.fid " +
                    "WHERE sid="+sid;

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(buildquery);
            while (rs.next()){
                String Rcid = rs.getString(1);
                String Rcname = rs.getString(2);
                String Rmeets = rs.getString(3);
                String Rroom = rs.getString(4);
                String Rfname = rs.getString(5);
                Integer Renrl = rs.getInt(6);
                Integer Rlimit = rs.getInt(7);
                Integer Rexam1 = rs.getInt(8);
                Integer Rexam2 = rs.getInt(9);
                Integer Rfinalg = rs.getInt(10);
                Object[] row = {Rcid.toString(),Rcname,Rmeets,Rroom,Rfname,Renrl,Rlimit.toString(),Rexam1.toString(),Rexam2.toString(),Rfinalg.toString()};
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