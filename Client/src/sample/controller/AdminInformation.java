package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.User;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

import java.io.IOException;

public class AdminInformation extends User {

    @FXML
    private Button logOut;

    @FXML
    private Label name;

    @FXML
    private Label lastName;

    @FXML
    private Label age;

    @FXML
    private Label gender;

    @FXML
    private Label login;

    @FXML
    void initialize() {
        User user = getInfo();
        name.setText(user.getName());
        lastName.setText(user.getLastName());
        age.setText(String.valueOf(user.getAge()));
        gender.setText(user.getGender());
        login.setText(user.getLogin());
        logOut.setOnAction(event -> {
            logOut.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");
        });
    }

    User getInfo() {
        User user = new User();
        String userLogin = getStaticLogin();
        ClientRequest clientRequest = new ClientRequest("getUserInfo", userLogin);
        Main.sendData(clientRequest);

        try {
            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();
            user = objectMapper.readValue(jsonProd, User.class);
            return user;
        } catch (IOException e) {
            AlertHandler.ShowErrorMessage("Возникла ошибка.");
        }
        return user;
    }
}
