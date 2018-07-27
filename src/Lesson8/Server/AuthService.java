package Lesson8.Server;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO main (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = statement.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "' and isBlocked = 0");
            int myHash = pass.hashCode();
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
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

    public static void saveHistory(String login, String msg, Date msgDate) {
        String sql = String.format(" INSERT INTO history(post, nick, postTime) " +
                " VALUES ('%s', '%s', '%s')", msg, login, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(msgDate));
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static StringBuilder getHistoryChat() {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = String.format("SELECT nick, post, postTime from history order by ID");
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("postTime")
                        + " "
                        + resultSet.getString("nick")
                        + ": "
                        + resultSet.getString("post") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    public static void AddToBlackList(ClientHandler client, String blockedNick) {
        String sql = String.format(" INSERT INTO blacklist (userId, blockedUserId) " +
                "Select id, (Select id FROM main where nickname = '%s') FROM main where nickname = '%s'", blockedNick, client.getNick());
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> GetBlockedUsers(ClientHandler client) {
        List<String> list = new ArrayList<>();
        String sql = String.format("SELECT nickname FROM main m " +
                "INNER JOIN blacklist b ON b.blockedUserId = m.id " +
                "and b.userId = (SELECT id FROM main WHERE nickname = '%s')", client.getNick());
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(resultSet.getString("nickname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Boolean IsAdmin(ClientHandler client) {
        Boolean isAdmin = false;
        String sql = String.format("SELECT isAdmin FROM main WHERE nickname = '%s'", client.getNick());
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                isAdmin = resultSet.getInt("isAdmin") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

    public static void UpdateUserBlockState(String nick, Boolean isBlocked) {
        String sql = String.format("UPDATE main SET isBlocked = %d WHERE nickname = '%s'", isBlocked ? 1 : 0, nick);
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
