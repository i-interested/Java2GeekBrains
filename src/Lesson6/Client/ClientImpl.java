package Lesson6.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientImpl {
    final String IP_ADRESS = "localhost";
    final int PORT = 8189;
    Socket socket;

    public ClientImpl() {
        Scanner consoleScanner;

        Scanner in;
        PrintWriter out;
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            consoleScanner = new Scanner(System.in);
            Thread inputThread = new Thread(() -> {
                while (true) {
                    if (!in.hasNext()) continue;
                    String line = in.nextLine();
                    if (line.equalsIgnoreCase("/serverClosed"))
                        break;
                    System.out.println("Server:" + line);
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
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
