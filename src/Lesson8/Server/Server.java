package Lesson8.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class Server {
    private Vector<ClientHandler> clients;

    public Server() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            AuthService.connect();
            //AuthService.addUser("login1","pass1","nick1");
            //AuthService.addUser("login2","pass2","nick2");
            //AuthService.addUser("login3","pass3","nick3");
            server = new ServerSocket(8189);
            System.out.println("Server started. Waiting of clients...");
            while (true) {
                socket = server.accept();
                System.out.println("Client attached");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler client : clients)
            if (!client.checkBlackList(from.getNick())) {
                client.sendMsg(msg);
            }
    }

    public void sendToNick(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler client : clients) {
            if (nickTo.equalsIgnoreCase(client.getNick()) &&
                    !client.checkBlackList(from.getNick()) &&
                    !from.checkBlackList(client.getNick()) &&
                    !client.getNick().equalsIgnoreCase(from.getNick())) {
                client.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("to " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Невозможно отправить сообшение клиенту с ником " + nickTo);
    }

    public boolean isNickFree(String nick) {
        for (ClientHandler client : clients) {
            if (client.getNick().equals(nick))
                return false;
        }
        return true;
    }

    public void broadcastClientsList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientslist ");
        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
        }
        String out = sb.toString();
        for (ClientHandler o : clients) {
            o.sendMsg(out);
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        broadcastClientsList();
    }

    public void BlockUser(String nick) {
        for (ClientHandler client : clients) {
            if (nick.equalsIgnoreCase(client.getNick())) {
                client.sendMsg("/serverclosed");
                unsubscribe(client);
                return;
            }
        }
    }
}
