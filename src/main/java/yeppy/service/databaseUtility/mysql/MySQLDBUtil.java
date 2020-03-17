package yeppy.service.databaseUtility.mysql;

public class MySQLDBUtil {
    private static final String HOSTNAME = "localhost";
    private static final String PORT_NUM = "3306";
    public static final String DB_NAME = "yeppy";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
//    public static final String URL = "jdbc:mysql://"
//            + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
//            + "?user=" + USERNAME + "&password=" + PASSWORD
//            + "&autoReconnect=true&serverTimezone=UTC";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    public static String getURL() {
        return URL;
    }
}