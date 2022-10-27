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
import sample.entity.Products;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProductsControl extends Products {

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
    private Button editButtonProductsControl;

    @FXML
    private Button deleteButtonProductsControl;

    @FXML
    private Button addButtonProductsControl;

    @FXML
    private TextField search;

    @FXML
    void initialize() {
        fillTable();


        logOutButtonProductsControl.setOnAction(event -> {
            logOutButtonProductsControl.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");
        });

        addButtonProductsControl.setOnAction(event -> {
            logOutButtonProductsControl.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/InsertCategory.fxml");
        });


        deleteButtonProductsControl.setOnAction(event -> {
            try {
                Products productToDelete = tableProductsControl.getSelectionModel().getSelectedItem();
                String productName = productToDelete.getProductName();

                ClientRequest clientRequest = new ClientRequest("deleteProduct", productName);
                Main.sendData(clientRequest);

                ServerResponse serverResponse = Main.getData();

                if (serverResponse.getSuccess()) {
                    AlertHandler.ShowInformationMessage("Успешно удалено!");
                    fillTable();
                } else AlertHandler.ShowErrorMessage("Что-то пошло не так...");
            } catch (NullPointerException e) {
                AlertHandler.ShowErrorMessage("Необходимо выбрать строку для удаления!");
            }

        });

        editButtonProductsControl.setOnAction(event -> {
            try {
                Products productToUpdate = tableProductsControl.getSelectionModel().getSelectedItem();
                String productName = productToUpdate.getProductName();

                setStaticName(productName);
                logOutButtonProductsControl.getScene().getWindow().hide();
                SceneChanger.getInstance().changeScene("/sample/fxml/UpdateProduct.fxml");
            } catch (NullPointerException e) {
                AlertHandler.ShowErrorMessage("Необходимо выбрать строку для изменения!");
            }
        });
    }


    private void fillTable() {
        try {
            ClientRequest clientRequest = new ClientRequest("getProducts", null);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();


            List<Products> itemsProd = new LinkedList<>(Arrays.asList(objectMapper.readValue(jsonProd, Products[].class)));
            productNameProductsControl.setCellValueFactory(new PropertyValueFactory<Products, String>("productName"));
            categoryNameProductsControl.setCellValueFactory(new PropertyValueFactory<Products, String>("categoryName"));
            priceProductsControl.setCellValueFactory(new PropertyValueFactory<Products, Double>("price"));
            kcalProductsControl.setCellValueFactory(new PropertyValueFactory<Products, Integer>("kcal"));
            weightProductsControl.setCellValueFactory(new PropertyValueFactory<Products, Integer>("weight"));
            tableProductsControl.setItems(FXCollections.observableArrayList(itemsProd));

            FilteredList<Products> filteredData = new FilteredList<>(FXCollections.observableArrayList(itemsProd), p -> true);

            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (product.getCategoryName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });


            SortedList<Products> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableProductsControl.comparatorProperty());
            tableProductsControl.setItems(sortedData);
           // fillTable(itemsProd);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}