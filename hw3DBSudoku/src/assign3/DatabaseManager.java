package assign3;
import java.sql.*;

public class DatabaseManager {

    public Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER + "/" + MyDBInfo.MYSQL_DATABASE_NAME;
        String user = MyDBInfo.MYSQL_USERNAME;
        String password = MyDBInfo.MYSQL_PASSWORD;
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println("Connected to database");
        return conn;
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            ex.printStackTrace();
            System.exit(1);
        }


        DatabaseManager DBManager = new DatabaseManager();
        try {
            Connection conn  = DBManager.getConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
