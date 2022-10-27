package sample.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.Products;
import sample.entity.User;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class FavouritesOfUser {

    @FXML
    private Button deleteFavourites;

    @FXML
    private TableColumn<Products, Integer> kcalFavourites;

    @FXML
    private Button logOutButton;

    @FXML
    private TableColumn<Products, Double> priceFavourites;

    @FXML
    private TableColumn<Products, String> productNameFavourites;

    @FXML
    private TextField search;

    @FXML
    private TableView<Products> tableProductsControl;

    @FXML
    private TableColumn<Products, Integer> weightFavourites;

    @FXML
    void initialize() {
        fillTable();


        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/UserMenu.fxml");
        });
        deleteFavourites.setOnAction(event -> {
            try {
                ClientRequest clientRequest = new ClientRequest("deleteFavouriteProduct", User.getStaticLogin());
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
    }

    private void fillTable() {
        try {
            String userLogin = User.getStaticLogin();

            ClientRequest clientRequest = new ClientRequest("getFavouriteProducts", userLogin);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();


            List<Products> itemsProd = new LinkedList<>(Arrays.asList(objectMapper.readValue(jsonProd, Products[].class)));
            //fillTable(itemsProd);
            productNameFavourites.setCellValueFactory(new PropertyValueFactory<>("productName"));
            priceFavourites.setCellValueFactory(new PropertyValueFactory<>("price"));
            kcalFavourites.setCellValueFactory(new PropertyValueFactory<>("kcal"));
            weightFavourites.setCellValueFactory(new PropertyValueFactory<>("weight"));
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
