package sample.controller;

import java.awt.*;
import java.io.File;
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
import sample.entity.Addresses;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class AddressesControl {

    @FXML
    private TextField addAddress;

    @FXML
    private TextField addCity;

    @FXML
    private TextField addTelephone;

    @FXML
    private Button addFullAddress;

    @FXML
    private TableColumn<Addresses, String> address;

    @FXML
    private TableColumn<Addresses, String> city;

    @FXML
    private TableColumn<Addresses, Integer> telephone;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField search;

    @FXML
    private TableView<Addresses> tableAddresses;

    @FXML
    private Button deleteAddress;

    @FXML
    private Button openTextFile;


    @FXML
    void initialize() {
        Addresses fullAddress = new Addresses();
        fillTable();


        openTextFile.setOnAction(event -> {openFile();});

        addFullAddress.setOnAction(event -> {
            acceptInfo(fullAddress);
        });


        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");
        });

        deleteAddress.setOnAction(event -> { deleteAddress(); });

    }

    private void openFile() {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            assert desktop != null;
            desktop.open(new File("addressesFile.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAddress() {
        try {
            Addresses addressToDelete = tableAddresses.getSelectionModel().getSelectedItem();
            String addressDel = addressToDelete.getAddress();

            ClientRequest clientRequest = new ClientRequest("deleteAddress", addressDel);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            if (serverResponse.getSuccess()) {
                AlertHandler.ShowInformationMessage("?????????????? ??????????????!");
                fillTable();
            } else AlertHandler.ShowErrorMessage("??????-???? ?????????? ???? ??????...");
        } catch (NullPointerException e) {
            AlertHandler.ShowErrorMessage("???????????????????? ?????????????? ???????????? ?????? ????????????????!");
        }

    }

    private void acceptInfo(Addresses fullAddress) {

        try {
            int telephone = Integer.parseInt(addTelephone.getText());
            if (telephone < 0) throw new IllegalArgumentException();
            else fullAddress.setTelephone(telephone);

            fullAddress.setCityName(addCity.getText());
            fullAddress.setAddress(addAddress.getText());

        } catch (NumberFormatException e) {
            AlertHandler.ShowErrorMessage("?????????????? ?????????? ?? ???????? ???????????? ????????????????!");
        } catch (NullPointerException ex) {
            AlertHandler.ShowErrorMessage("?????????????????? ?????? ????????!");
        } catch (IllegalArgumentException e) {
            AlertHandler.ShowErrorMessage("?????????????? ?????????? >0!");
        }
        insertAddress(fullAddress);
    }

    private void insertAddress(Addresses fullAddress) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(fullAddress);
            ClientRequest clientRequest = new ClientRequest("insertAddress", json);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();
            if (serverResponse.getSuccess())
                AlertHandler.ShowInformationMessage("?????????????? ??????????????????!");
            fillTable();
        } catch (IOException e) {
            AlertHandler.ShowErrorMessage("??????-???? ?????????? ???? ??????...");
        }
    }


    private void fillTable() {
        try {
            ClientRequest clientRequest = new ClientRequest("getAddresses", null);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();


            List<Addresses> itemsAddr = new LinkedList<>(Arrays.asList(objectMapper.readValue(jsonProd, Addresses[].class)));
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
            //fillTable(itemsProd);
        } catch (
                IOException e) {
            e.printStackTrace();
        }



    }
}
