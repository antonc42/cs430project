/**
 * Created by anton on 10/17/15.
 * Main class for project
 */
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.io.*;
public class MainClass {
    public static void main(String[] args){
        // if config file exists and is readable, skip setup dialog
        String filename = System.getProperty("user.home");
        filename += File.separator + ".cs430dbconfig";
        Path config = Paths.get(filename);
        if (Files.exists(config) && Files.isReadable(config)) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
                String username = reader.readLine();
                String password = reader.readLine();
                String server = "131.230.133.11";
                String port ="1521";
                String dbname = "cs";
                Database db = new Database();
                Connection con = db.connect(server, port, dbname, username, password);
                Login loginbox = new Login();
                loginbox.setConnection(con);
                loginbox.setVisible(true);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null,"Cannot open config file - not found!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Cannot open config file - error reading!");
            }
        }
        // if no config file exists, go to setup dialog
        else {
            Setup setupbox = new Setup();
            setupbox.setVisible(true);
        }
        
    }
}
