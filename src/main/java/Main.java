import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main {

    public static Connection db = null;

    public static void main(String[] args) {
        openDatabase("test.db");

        insertWeight(15, "dooley");
        listUsers();
        deleteUser(5);

        closeDatabase();
    }

    private static void openDatabase(String dbFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    private static void closeDatabase() {
        try {
            db.close();
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

    public static void insertWeight(int userID, String username) {
        try {
            PreparedStatement ps = db.prepareStatement("INSERT INTO Users (UserID, Username) VALUES (?, ?)");

            ps.setInt(1, userID);
            ps.setString(2, username);
            ps.executeUpdate();
            System.out.println("Record added to Users  table");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: Something as gone wrong.  Please contact the administrator with the error code WC-WA.");
        }
    }


    public static void listUsers() {
        try {

            PreparedStatement ps = db.prepareStatement("SELECT * FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int id = results.getInt(1);
                String username  = results.getString(2);

                System.out.println(id + " : "   + username);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void deleteUser (int userID){
        try {

            PreparedStatement ps = db.prepareStatement("DELETE FROM Users WHERE UserID = ?");
            ps.setInt(1, userID);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }



}
