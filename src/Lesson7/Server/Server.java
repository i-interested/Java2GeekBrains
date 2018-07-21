package Lesson7.Server;

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

    public void broadcastMsg(String msg) {
        for (ClientHandler client : clients) {
            client.sendMsg(msg);
        }
    }

    public void sendToNick(String nickFrom, String nickTo, String msg) {
        for (ClientHandler client : clients) {
            if (nickFrom.equalsIgnoreCase(client.getNick()) || nickTo.equalsIgnoreCase(client.getNick()))
                client.sendMsg(msg);
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public boolean isNickFree(String nick) {
        for (ClientHandler client : clients) {
            if (client.getNick().equals(nick))
                return false;
        }
        return true;
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }
}
