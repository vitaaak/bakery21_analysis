package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.util.SceneChanger;

public class UserMenu {

    @FXML
    private Button logOutButtonUserMenu;

    @FXML
    private Button userInfoUserMenu;

    @FXML
    private Button productsTable;

    @FXML
    private Button addressesUserMenu;

    @FXML
    private Button favouritesUserMenu;

    @FXML
    void initialize() {

        productsTable.setOnAction(event -> {
            logOutButtonUserMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/ViewProductsUser.fxml");
        });

    addressesUserMenu.setOnAction(event -> {
        logOutButtonUserMenu.getScene().getWindow().hide();
        SceneChanger.getInstance().changeScene("/sample/fxml/ViewAddresses.fxml");
    });

    logOutButtonUserMenu.setOnAction(event -> {
        logOutButtonUserMenu.getScene().getWindow().hide();
        SceneChanger.getInstance().changeScene("/sample/fxml/UserLogin.fxml");
    });

    userInfoUserMenu.setOnAction(event -> {
        logOutButtonUserMenu.getScene().getWindow().hide();
        SceneChanger.getInstance().changeScene("/sample/fxml/UserInformation.fxml");
    });

        favouritesUserMenu.setOnAction(event -> {
            favouritesUserMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/FavouritesOfUser.fxml");
        });
    }
}
