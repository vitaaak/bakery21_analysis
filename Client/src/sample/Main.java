package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.codehaus.jackson.map.ObjectMapper;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;

import java.io.*;
import java.net.Socket;

public class Main extends Application {
    private static Socket socket;

    @Override
    public void start(Stage primaryStage) throws IOException {
        socket = new Socket("127.0.0.1", 8882);
        System.out.println("Socket is connected with server!");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/UserLogin.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Пекарня21");
        primaryStage.setScene(new Scene(root, 1050, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void sendData(ClientRequest data) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(data);
            outputStream.writeObject(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ServerResponse getData() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectMapper mapper = new ObjectMapper();
            String json = (String) inputStream.readObject();
            System.out.println(json);
            return mapper.readValue(json, ServerResponse.class);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
