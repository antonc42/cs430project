/**
 * Created by anton on 10/19/15.
 */
import java.sql.*;
public class Database {
    public static Connection connect(String server, String db, String user, String pass) throws Exception{
        Connection con = null;
        try {
            // initialize the JDBC driver
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);
            //DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

        }
        catch (Exception ex){
            System.out.println("Cannot load Oracle driver");
        }
        try {
            // connect to the db
            String url = "jdbc:oracle:thin:@"+server+":1521:"+db;
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
        }
        return con;
    }
}
