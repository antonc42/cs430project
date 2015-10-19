/**
 * Created by anton on 10/17/15.
 */
import java.sql.*;
import java.io.*;
public class MainClass {
    public static void main(String[] args){
        String url = "jdbc:oracle:thin:@131.230.133.11:1521:cs";
        String queryString = "SELECT * FROM Student";
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        console.printf("Please enter your username: ");
        String username = console.readLine();
        console.printf("Please enter your password: ");
        //String username = new String(userChars);
        char[] passwordChars = console.readPassword();
        String pass = new String(passwordChars);
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

        }
        catch (Exception ex){
            System.out.println("Cannot load Oracle driver");
        }
        try {
            Connection con = DriverManager.getConnection(url,username,pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryString);
            while (rs.next()){
                int id = rs.getInt(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                float grade = rs.getFloat(4);
                System.out.println("id is "+id);
                System.out.println("fname is "+fname);
                System.out.println("lname is "+lname);
                System.out.println("grade is "+grade);
            }
            st.close();
            con.close();
        }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
        }
    }
}
