import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.io.*;

/**
 * Created by anton on 10/17/15.
 * Main class for project
 * @author Anton Castelli
 */
public class MainClass {
    /**
     * Main Method for project
     * Looks for pre-existing config file and displays setup window if not found.
     * Then starts user login dialog.
     * @param args
     */
    public static void main(String[] args){
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
        else {
            Setup setupbox = new Setup();
            setupbox.setVisible(true);
        }
        
    }
}
