package yeppy.service.databaseUtility;

import yeppy.service.databaseUtility.mysql.MySQLConnection;

import java.sql.Connection;

public class DBConnectionFactory {
    // This should change based on the pipeline.
    private static final String DEFAULT_DB = "mysql";

    public static MySQLConnection getConnection(String db) {
        switch (db) {
            case "mysql":
                return new MySQLConnection();
//            case "mongodb":
//                // return new MongoDBConnection();
//                return new MongoDBConnection();
            default:
                throw new IllegalArgumentException("Invalid db:" + db);
        }

    }

    //public static Connection getConnection() {
    //    return getConnection(DEFAULT_DB).getCon();
    //}
}

