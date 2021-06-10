package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "insertURL";
    private static final String USERNAME = "insertUsername";
    private static final String PASSWORD = "insertPassword";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);

    }
}
