package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {

    }

    private BlockingQueue<Connection> availableConnection;
    private BlockingQueue<Connection> usedConnection;
    private final int SIZE = 20;

    public void create(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            availableConnection = new ArrayBlockingQueue<>(SIZE);
            usedConnection = new ArrayBlockingQueue<>(SIZE);
            for (int i = 0; i < SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                availableConnection.put(connection);
            }
        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection get() {
        Connection connection = null;
        try {
            connection = availableConnection.take();
            usedConnection.put(connection);
        } catch (InterruptedException e) {
            System.out.println("Error:" +e);
        }
        return connection;
    }

    public void release(Connection connection){
        availableConnection.add(connection);
        usedConnection.remove(connection);
    }

    public void destroy(){
        for(Connection connection:availableConnection){
            availableConnection.remove(connection);
        }
        for (Connection connection:usedConnection){
            usedConnection.remove(connection);
        }
    }

}