/**
 * Created by anton on 10/17/15.
 * Main class for project
 */
import java.sql.*;
import java.io.*;
public class MainClass {
    public static void main(String[] args){
    /*
        String server = "131.230.133.11";
        String port ="1521";
        String dbname = "cs";
        Database db = new Database();

        String queryString = "SELECT * FROM Student";
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        console.printf("Please enter your username: ");
        String username = console.readLine();
        console.printf("Please enter your password: ");
        char[] passwordChars = console.readPassword();
        String pass = new String(passwordChars);
        try {
    */
            // connect to the db
    //        Connection con = db.connect(server, port, dbname, username, pass);
    //        db.setupDB(con);
            /*// query the db
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryString);
            // print the result
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
            // close the db connection
            st.close();*/
    //        con.close();
    /*    }
        catch (Exception ex){
            System.out.println("SQLException: "+ex);
        }
    */
        // do some code here to check if setup is done already
        // if so, go straigt to login box
        // if not, go to setup
        Login loginbox = new Login();
        loginbox.pack();
        loginbox.setVisible(true);
    }
}
