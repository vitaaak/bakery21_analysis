package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.Products;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class UpdateProduct extends Products{

    @FXML
    private Button logOutButton;

    @FXML
    private Button acceptProduct;

    @FXML
    private  TextField productName;

    @FXML
    private TextField price;

    @FXML
    private TextField kcal;

    @FXML
    private TextField weight;


    @FXML
    void initialize() {
        productName.setText(getStaticName());
        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/ProductsControl.fxml");
        });
        acceptProduct.setOnAction(event -> {

            getInfo();
        });
    }


    private void getInfo() {
        try {
            Products product = new Products();
            product.setProductName(getStaticName());

            if(!price.getText().isEmpty()){
            double priceDouble = Double.parseDouble(price.getText());
            if (priceDouble < 0) throw new IllegalArgumentException();
            else product.setPrice(priceDouble);}
            else product.setPrice(0);

            if(!kcal.getText().isEmpty()){
            int kcalInt = Integer.parseInt(kcal.getText());
            if (kcalInt < 0) throw new IllegalArgumentException();
            else product.setKcal(kcalInt);}
            else product.setKcal(0);

            if(!weight.getText().isEmpty()){
            int weightInt = Integer.parseInt(weight.getText());
            if (weightInt < 0) throw new IllegalArgumentException();
            else product.setWeight(weightInt);}
            else product.setWeight(0);

            sendToServer(product);

        } catch (IllegalArgumentException e) {
            AlertHandler.ShowErrorMessage("Введите число >0!");
        }

    }

    private void sendToServer(Products product){
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(product);
            ClientRequest clientRequest = new ClientRequest("updateProduct", json);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();
            if (serverResponse.getSuccess())
                AlertHandler.ShowInformationMessage("Успешно обновлено!");
        } catch (Exception e) {
            AlertHandler.ShowErrorMessage("Что-то пошло не так...");
        }
    }
}

