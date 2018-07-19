package Lesson6.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerImpl {
    Scanner in;
    PrintWriter out;

    public ServerImpl() {
        ServerSocket server = null;
        Socket socket = null;
        Scanner consoleScanner;

        try {
            server = new ServerSocket(8189);
            System.out.println("Server started, wait clients...");
            socket = server.accept();
            System.out.println("Client attach");
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            consoleScanner = new Scanner(System.in);
            Thread inputThread = new Thread(() -> {
                while (true) {
                    if (!in.hasNext()) continue;
                    String str = in.nextLine();
                    if (str.equalsIgnoreCase("/end"))
                        break;
                    System.out.println("Client:" + str);
                }
            });
            inputThread.start();

            Thread outputThread = new Thread(() -> {
                while (true) {
                    if (!consoleScanner.hasNext()) continue;
                    String line = consoleScanner.nextLine();
                    if (line.isEmpty()) continue;
                    out.println(line);
                    out.flush();
                }
            });
            outputThread.setDaemon(true);
            outputThread.start();

            inputThread.join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                out.println("/serverClosed");
                out.flush();
                server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}