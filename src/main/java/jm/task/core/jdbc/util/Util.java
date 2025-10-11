package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/deus_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "_84Bibozoazapro";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
