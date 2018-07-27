package Lesson8.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick = "";
    private Boolean isAdmin;

    List<String> blackList;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] tokens = str.split(" ");
                            String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                            if (newNick != null) {
                                if (server.isNickFree(newNick)) {
                                    sendMsg("/authok");
                                    nick = newNick;
                                    server.subscribe(this);
                                    blackList = AuthService.GetBlockedUsers(this);
                                    isAdmin = AuthService.IsAdmin(this);
                                    break;
                                }
                                sendMsg("Nick logged in earlier");
                                continue;
                            }
                            sendMsg("Incorrect login/password or blocked");
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        Date msgDate = new Date();
                        if (str.equalsIgnoreCase("/end")) {
                            out.writeUTF("/serverclosed");
                            break;
                        }
                        if (str.startsWith("/w ")) {
                            String[] tokens = str.split(" ", 3);
                            if (tokens.length > 2) {
                                server.sendToNick(this, tokens[1], tokens[2]);
                                System.out.println(nick + " to " + tokens[1] + ": " + tokens[2]);
                            }
                            continue;
                        }
                        if (str.startsWith("/blacklist ")) { // /blacklist nick3
                            String[] tokens = str.split(" ");
                            blackList.add(tokens[1]);
                            sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                            continue;
                        }
                        if (str.startsWith("/history ")) {
                            StringBuilder stringBuilder = AuthService.getHistoryChat();
                            out.writeUTF(stringBuilder.toString());
                            continue;
                        }

                        if (isAdmin && str.startsWith("/block")) {
                            String[] tokens = str.split(" ");
                            AuthService.UpdateUserBlockState(tokens[1], true);
                            server.BlockUser(tokens[1]);
                            continue;
                        }

                        if (str.startsWith("/unblock ")) {
                            String[] tokens = str.split(" ");
                            AuthService.UpdateUserBlockState(tokens[1], false);
                            continue;
                        }

                        AuthService.saveHistory(nick, str, msgDate);
                        server.broadcastMsg(this, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(msgDate) + " " + nick + ": " + str);
                        System.out.println(nick + ": " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
