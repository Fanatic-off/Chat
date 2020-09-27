package ru.geekbrains.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class DBConn {

    private static DBConn instance;
    private Connection conn;

    private DBConn () throws SQLException {
        ResourceBundle rb = ResourceBundle.getBundle("db");
        String host = rb.getString("host");
        String port = rb.getString("port");
        String db = rb.getString("db");
        String user = rb.getString("user");
        String password = rb.getString("password");

        String jdbcURL = MessageFormat.format(
                "jdbc:mysql://{0}:{1}/{2}",
                host,port,db);
        conn = DriverManager.getConnection(jdbcURL,user,password);
    }

    public static DBConn getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConn();
        }
        return instance;
    }

    public static void setInstance(DBConn instance) {
        DBConn.instance = instance;
    }

    public Connection connection() {
        return conn;
    }
}
