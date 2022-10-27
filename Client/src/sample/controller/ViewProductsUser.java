package sample.controller;

import java.util.LinkedList;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.Addresses;
import sample.entity.Products;
import sample.entity.User;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ViewProductsUser {

    @FXML
    private Button logOutButtonProductsControl;

    @FXML
    private TableView<Products> tableProductsControl;

    @FXML
    private TableColumn<Products, String> productNameProductsControl;

    @FXML
    private TableColumn<Products, String> categoryNameProductsControl;

    @FXML
    private TableColumn<Products, Double> priceProductsControl;

    @FXML
    private TableColumn<Products, Integer> kcalProductsControl;

    @FXML
    private TableColumn<Products, Integer> weightProductsControl;

    @FXML
    private TextField search;

    @FXML
    private Button addToFavourites;

    @FXML
    private Button openFavourites;


    @FXML
    void initialize() {
        try {
            ClientRequest clientRequest = new ClientRequest("getProducts", null);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();


            List<Products> itemsProd = new LinkedList<>(Arrays.asList(objectMapper.readValue(jsonProd, Products[].class)));
            fillTable(itemsProd);
        } catch (IOException e) {
            e.printStackTrace();
        }


        logOutButtonProductsControl.setOnAction(event -> {
            logOutButtonProductsControl.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/UserMenu.fxml");
        });

        openFavourites.setOnAction(event -> {
            openFavourites.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/FavouritesOfUser.fxml");
        });
        addToFavourites.setOnAction(event -> {addToFavourites();});

    }

    private void addToFavourites() {
        Products prodToAddToFav = tableProductsControl.getSelectionModel().getSelectedItem();
        prodToAddToFav.setCategoryName(User.getStaticLogin());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(prodToAddToFav);
            ClientRequest clientRequest = new ClientRequest("insertProdToFavourites", json);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();
            if (serverResponse.getSuccess())
                AlertHandler.ShowInformationMessage("Успешно добавлено!");
        } catch (IOException e) {
            AlertHandler.ShowErrorMessage("Что-то пошло не так...");
        }

    }


    private void fillTable(List<Products> itemsProd) {

        productNameProductsControl.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryNameProductsControl.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        priceProductsControl.setCellValueFactory(new PropertyValueFactory<>("price"));
        kcalProductsControl.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        weightProductsControl.setCellValueFactory(new PropertyValueFactory<>("weight"));
        tableProductsControl.setItems(FXCollections.observableArrayList(itemsProd));

        FilteredList<Products> filteredData = new FilteredList<>(FXCollections.observableArrayList(itemsProd), p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return product.getCategoryName().toLowerCase().contains(lowerCaseFilter);
        }));


        SortedList<Products> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableProductsControl.comparatorProperty());
        tableProductsControl.setItems(sortedData);
    }
}