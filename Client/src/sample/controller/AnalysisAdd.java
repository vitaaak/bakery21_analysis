package sample.controller;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.Analysis;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class AnalysisAdd {

    @FXML
    private Button logOutButton;

    @FXML
    private TextField scale;

    @FXML
    private ComboBox<String> alternative1;

    @FXML
    private ComboBox<String> alternative2;

    @FXML
    private ComboBox<String> alternative3;

    @FXML
    private ComboBox<String> alternative4;

    @FXML
    private Button acceptButton;


    @FXML
    void initialize() {
        Analysis newAnalysis = new Analysis();
        getAlternatives();
        acceptButton.setOnAction(event -> {
            acceptAlternatives(newAnalysis);
        });

        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AnalysisMenuAdmin.fxml");
        });


    }

    private void acceptAlternatives(Analysis newAnalysis) {

        try {
            int scaleInt = Integer.parseInt(scale.getText());
            if (scaleInt<5) throw new IllegalArgumentException();

            if (Validate()) {
                newAnalysis.setAlternatives(alternative1.getValue(), alternative2.getValue(), alternative3.getValue(), alternative4.getValue());
                newAnalysis.setScale(scaleInt);
                acceptButton.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/sample/fxml/NewAnalysis.fxml");
            }
            else
                AlertHandler.ShowErrorMessage("Все альтернативы должны быть уникальны!");

        } catch (NumberFormatException e) {
            AlertHandler.ShowErrorMessage("Введите число в поле значения шкалы!");
        }
        catch (NullPointerException ex){
            AlertHandler.ShowErrorMessage("Заполните все поля!");
        }
        catch (IllegalArgumentException e){
            AlertHandler.ShowErrorMessage("Введите число >5!");
        }



    }

    private boolean Validate() {

        return (!alternative1.getValue().equals(alternative2.getValue()) && !alternative1.getValue().equals(alternative3.getValue()) &&
                !alternative1.getValue().equals(alternative4.getValue()) && !alternative2.getValue().equals(alternative3.getValue()) &&
                !alternative2.getValue().equals(alternative4.getValue()) && !alternative3.getValue().equals(alternative4.getValue()));

    }

    private void getAlternatives() {
        try {
            ClientRequest clientRequest = new ClientRequest("getAlternatives", "");
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();
            List itemsProd = objectMapper.readValue(jsonProd, List.class);
            ObservableList alternativesList = FXCollections.observableArrayList(itemsProd);

            alternative1.setItems(alternativesList);
            alternative2.setItems(alternativesList);
            alternative3.setItems(alternativesList);
            alternative4.setItems(alternativesList);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
