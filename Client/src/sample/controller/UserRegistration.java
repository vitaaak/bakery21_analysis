package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.User;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class UserRegistration {
    @FXML
    private Button userRegistrationButton;

    @FXML
    private PasswordField registrationPassword;

    @FXML
    private TextField registrationLastName;

    @FXML
    private TextField registrationAge;

    @FXML
    private TextField registrationLogin;

    @FXML
    private TextField registrationName;

    @FXML
    private CheckBox registrationBoxMale;

    @FXML
    private CheckBox registrationBoxFemale;

    @FXML
    void initialize() {userRegistrationButton.setOnAction(event -> RegisterUser());
    }

    private void RegisterUser() {

        if (Validate()) {
            User user = new User();
            if (CreateUser(user)) {

                ClientRequest clientRequest = new ClientRequest("registerUser", user);
                Main.sendData(clientRequest);

                ServerResponse serverResponse = Main.getData();
                if (serverResponse.getSuccess()) {
                    userRegistrationButton.getScene().getWindow().hide();
                    SceneChanger.getInstance().changeScene("/sample/fxml/UserLogin.fxml");
                    AlertHandler.ShowInformationMessage("Пользователь успешно зарегистрирован.");
                } else {
                    AlertHandler.ShowErrorMessage("Попробуйте придумать новый логин.");
                }
            }
            else AlertHandler.ShowErrorMessage("Введите цифру в поле возраста!");
            } else {
                AlertHandler.ShowErrorMessage("Заполните все поля!");
            }

    }

    private Boolean CreateUser(User user){
        try {
            user.setLastName(registrationLastName.getText());

            user.setName(registrationName.getText());
            user.setPassword(registrationPassword.getText());
            user.setLogin(registrationLogin.getText());
            user.setPassword(registrationPassword.getText());
            user.setAge(Integer.parseInt(registrationAge.getText()));

            String gender = registrationBoxMale.isSelected() ? "мужской" : "женский";
            user.setGender(gender);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }

    }

    private Boolean Validate() {
        return !registrationName.getText().equalsIgnoreCase("")
                && !registrationLastName.getText().equalsIgnoreCase("")
                && !registrationLogin.getText().equalsIgnoreCase("")
                && !registrationPassword.getText().equalsIgnoreCase("")
                && !registrationAge.getText().equalsIgnoreCase("")
                && (registrationBoxMale.isSelected() || registrationBoxFemale.isSelected());
    }

}