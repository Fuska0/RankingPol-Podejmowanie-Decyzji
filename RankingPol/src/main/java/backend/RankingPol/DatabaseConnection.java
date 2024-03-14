package backend.RankingPol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://mysql.agh.edu.pl:3306/fuskacp1";
    private static final String USER = "fuskacp1";
    private static final String PASSWORD = "ShwgNsJeUekYtqHU";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
