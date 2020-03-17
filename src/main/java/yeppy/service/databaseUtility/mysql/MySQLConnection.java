package yeppy.service.databaseUtility.mysql;

import java.sql.*;

public class MySQLConnection {
    private Connection conn;

    public MySQLConnection() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(MySQLDBUtil.getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getCon(){
        return this.conn;
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
