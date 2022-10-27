package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.User;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class UserLogin {
    @FXML
    private Button userRegistrationButton;

    @FXML
    private Button userLoginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;


    @FXML
    void initialize() {
        userRegistrationButton.setOnAction(event ->{
            userRegistrationButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/UserRegistration.fxml");
        });
        userLoginButton.setOnAction(event -> Login());
    }

    private void Login(){
        if(Validate()){
            User user = CreateUser();

            ClientRequest clientRequest = new ClientRequest("loginUser", user);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();
            if(serverResponse.getRoleSuccess()==1){
                userLoginButton.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");
            }
            else if (serverResponse.getRoleSuccess()==2) {
                userLoginButton.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/sample/fxml/UserMenu.fxml");

            }
            else {
                AlertHandler.ShowErrorMessage("Возникла ошибка при авторизации пользователя.");
            }
        }
        else{
            AlertHandler.ShowErrorMessage("Заполните все поля!");
        }
    }

    private User CreateUser(){
        User user = new User();
        user.setLogin(loginField.getText());
        User.setStaticLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        return user;
    }

    private Boolean Validate(){
        return !loginField.getText().equalsIgnoreCase("") &&
                !passwordField.getText().equalsIgnoreCase("");
    }
}