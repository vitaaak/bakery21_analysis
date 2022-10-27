package sample.controller;

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
import sample.entity.Products;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

import java.io.IOException;
import java.util.List;

public class InsertProduct {

    @FXML
    private Button category;

    @FXML
    private Button logOut;

    @FXML
    private Button acceptCategory;

    @FXML
    private TextField cost;

    @FXML
    private TextField productName;

    @FXML
    private TextField kcal;

    @FXML
    private TextField weight;

    @FXML
    private ComboBox<String> categoryName;

    @FXML
    void initialize() {
        Products product = new Products();
        getCategories();
        logOut.setOnAction(event -> {
            logOut.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/ProductsControl.fxml");
        });
        category.setOnAction(event -> {
            category.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/InsertCategory.fxml");
        });
        acceptCategory.setOnAction(event -> {
            acceptInfo(product);
        });

    }


    private void getCategories() {
        try {
            ClientRequest clientRequest = new ClientRequest("getCategories", null);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();
            List itemsProd = objectMapper.readValue(jsonProd, List.class);
            ObservableList alternativesList = FXCollections.observableArrayList(itemsProd);

            categoryName.setItems(alternativesList);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptInfo(Products product) {

        try {
            double priceDouble = Double.parseDouble(cost.getText());
            if (priceDouble < 0) throw new IllegalArgumentException();
            else product.setPrice(priceDouble);

            int kcalInt = Integer.parseInt(kcal.getText());
            if (kcalInt < 0) throw new IllegalArgumentException();
            else product.setKcal(kcalInt);

            int weightInt = Integer.parseInt(weight.getText());
            if (weightInt < 0) throw new IllegalArgumentException();
            else product.setWeight(weightInt);

            product.setProductName(productName.getText());
            product.setCategoryName(categoryName.getValue());
            insertProduct(product);

        } catch (NumberFormatException e) {
            AlertHandler.ShowErrorMessage("Введите корректные значения!");
        } catch (NullPointerException ex) {
            AlertHandler.ShowErrorMessage("Заполните все поля!");
        } catch (IllegalArgumentException e) {
            AlertHandler.ShowErrorMessage("Введите число >0!");
        }

    }

    void insertProduct(Products product) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(product);
            ClientRequest clientRequest = new ClientRequest("insertProduct", json);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();
            if (serverResponse.getSuccess())
                AlertHandler.ShowInformationMessage("Успешно добавлено!");
        } catch (IOException e) {
            AlertHandler.ShowErrorMessage("Что-то пошло не так...");
        }
    }
}

