package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.util.SceneChanger;

public class AdminMenu {


    @FXML
    private Button logOutButtonAdminMenu;

    @FXML
    private Button userInfoAdminMenu;

    @FXML
    private Button productsAdminMenu;

    @FXML
    private Button addressesAdminMenu;

    @FXML
    private Button usersControlAdminMenu;

    @FXML
    void initialize() {
        userInfoAdminMenu.setOnAction(event -> {
            logOutButtonAdminMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminInformation.fxml");
        });
        logOutButtonAdminMenu.setOnAction(event -> {
            logOutButtonAdminMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/UserLogin.fxml");
        });
        productsAdminMenu.setOnAction(event -> {
            logOutButtonAdminMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/ProductsControl.fxml");
        });

        usersControlAdminMenu.setOnAction(event -> {
            logOutButtonAdminMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/UsersControl.fxml");
        });

        addressesAdminMenu.setOnAction(event -> {
            logOutButtonAdminMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AddressesControl.fxml");
        });
    }
}
