package Lesson7.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick = "";

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
                                    break;
                                }
                                sendMsg("Nick logged in earlier");
                                continue;
                            }
                            sendMsg("Incorrect login/password");
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.equalsIgnoreCase("/end")) {
                            out.writeUTF("/serverclosed");
                            break;
                        }
                        if (str.startsWith("/w")) {
                            String[] tokens = str.split(" ");
                            if (tokens.length > 2) {
                                server.sendToNick(nick, tokens[1], nick + ": " + tokens[2]);
                                System.out.println(nick + " to " + tokens[1] + ": " + tokens[2]);
                            }
                            continue;
                        }
                        server.broadcastMsg(nick + ": " + str);
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
