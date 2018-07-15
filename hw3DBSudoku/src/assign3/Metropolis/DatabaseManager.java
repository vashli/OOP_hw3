package assign3.Metropolis;

import java.sql.*;

public class DatabaseManager {

    public static Connection getConnection() {

        String url = "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER + "/" + MyDBInfo.MYSQL_DATABASE_NAME;
        String user = MyDBInfo.MYSQL_USERNAME;
        String password = MyDBInfo.MYSQL_PASSWORD;
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("Connected to database");
        return conn;
    }

}
