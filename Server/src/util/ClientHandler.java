package util;

import command.Command;
import command.factory.CommandFactory;
import cooperation.ClientRequest;
import cooperation.ServerResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean continueRunning = true;
        while (continueRunning) {
            try {
                ClientRequest request = getData();
                String action = request.getCommandName();

                System.out.println(action +":" + request.getData());

                CommandFactory factory = CommandFactory.getInstance();
                Command command = factory.createCommand(action, request, new ServerResponse());
                ServerResponse response = command.execute();

                sendData(response);
            } catch (SocketException e) {
                continueRunning = false;
                System.out.println(e.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void sendData(ServerResponse response) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        String outputJson = mapper.writeValueAsString(response);
        outputStream.writeObject(outputJson);
    }

    private ClientRequest getData() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        String inputJson = (String) inputStream.readObject();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputJson, ClientRequest.class);
    }
}