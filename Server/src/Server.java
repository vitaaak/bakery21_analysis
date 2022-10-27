import util.ClientHandler;
import util.ConnectionPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.create("jdbc:mysql://localhost:3306/bakery21?serverTimezone=UTC", "root", "admin");
        new Thread(new Server()).start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8882);
            System.out.println("Сервер ожидает подключения клиентов.");
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println("Подключился новый пользователь!");
                new Thread(clientHandler).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}