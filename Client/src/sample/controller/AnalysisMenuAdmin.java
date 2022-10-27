package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.util.SceneChanger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AnalysisMenuAdmin {

    @FXML
    private Button createNewAnalysisButton;

    @FXML
    private Button viewAnalysisButton;

    @FXML
    private Button logOutButtonAnalysisAdminMenu;

    @FXML
    void initialize() {
        createNewAnalysisButton.setOnAction(event -> {
            createNewAnalysisButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AnalysisAdd.fxml");
        });

        logOutButtonAnalysisAdminMenu.setOnAction(event ->{
            logOutButtonAnalysisAdminMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");
        });
        viewAnalysisButton.setOnAction(event -> {
            logOutButtonAnalysisAdminMenu.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AnalysisControl.fxml");

        });
    }

}
