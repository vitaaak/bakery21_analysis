package sample.controller;

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
import sample.entity.Addresses;
import sample.entity.Products;
import sample.util.SceneChanger;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ViewAddresses {

    @FXML
    private TableColumn<Addresses, String> address;

    @FXML
    private TableColumn<Addresses, String> city;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField search;

    @FXML
    private TableView<Addresses> tableAddresses;

    @FXML
    private TableColumn<Addresses, Integer> telephone;

    @FXML
    void initialize() {

        try {
            ClientRequest clientRequest = new ClientRequest("getAddresses", null);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();


            List<Addresses> itemsProd = new LinkedList<>(Arrays.asList(objectMapper.readValue(jsonProd, Addresses[].class)));
            fillTable(itemsProd);
        } catch (
                IOException e) {
            e.printStackTrace();
        }


        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/UserMenu.fxml");
        });

    }
    private void fillTable(List<Addresses> itemsAddr) {

        city.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        FilteredList<Addresses> filteredData = new FilteredList<>(FXCollections.observableArrayList(itemsAddr), p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (product.getCityName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return product.getAddress().toLowerCase().contains(lowerCaseFilter);
        }));


        SortedList<Addresses> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableAddresses.comparatorProperty());
        tableAddresses.setItems(sortedData);
    }
}