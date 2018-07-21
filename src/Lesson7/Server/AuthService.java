package Lesson7.Server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:userDB.db");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            String query = String.format("SELECT nickname FROM main " +
                    "WHERE login = '%s' AND password = '%s'", login, pass);
            ResultSet rs = statement.executeQuery(query);
            return rs.next() ? rs.getString("nickname") : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
