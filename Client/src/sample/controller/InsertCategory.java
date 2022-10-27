package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.User;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class InsertCategory {

    @FXML
    private Button product;

    @FXML
    private Button logOut;

    @FXML
    private TextField categoryField;

    @FXML
    private Button acceptCategory;

    @FXML
    void initialize() {

        logOut.setOnAction(event ->{
                logOut.getScene().getWindow().hide();
                    SceneChanger.getInstance().changeScene("/sample/fxml/ProductsControl.fxml");
                }
        );

        product.setOnAction(event -> {
            product.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/InsertProduct.fxml");
        });

        acceptCategory.setOnAction(event -> {
            insertCategory();
        });
    }

    void insertCategory(){
        String category = categoryField.getText();
        ClientRequest clientRequest = new ClientRequest("insertCategory", category);
        Main.sendData(clientRequest);

        ServerResponse serverResponse = Main.getData();

        if (serverResponse.getSuccess()) {
            AlertHandler.ShowInformationMessage("Успешно добавлено!");
        }
        else AlertHandler.ShowErrorMessage("Что-то пошло не так...");    }
}