package sandbox;

import java.sql.DriverManager;
import java.sql.Connection;

public class DB
{
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:jemaat.db");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
