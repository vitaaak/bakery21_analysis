package sample.controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import javafx.scene.control.cell.PropertyValueFactory;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.User;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class UsersControl {


    @FXML
    private Button logOutButtonUsersControl;

    @FXML
    private TableView<User> tableUsersControl;

    @FXML
    private TableColumn<User, Integer> idUsersControl;

    @FXML
    private TableColumn<User, String> loginUsersControl;

    @FXML
    private TableColumn<User, Integer> nameUsersControl;

    @FXML
    private TableColumn<User, Integer> lastNameUsersControl;

    @FXML
    private TableColumn<User, Integer> ageUsersControl;

    @FXML
    private TableColumn<User, String> genderUsersControl;

    @FXML
    private Button deleteButtonUsersControl;

    @FXML
    void initialize() {
        fillTable();
        logOutButtonUsersControl.setOnAction(event -> {
            logOutButtonUsersControl.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");
        });

        deleteButtonUsersControl.setOnAction(event -> {
            try {
                User userToDelete = tableUsersControl.getSelectionModel().getSelectedItem();
                String userLogin = userToDelete.getLogin();

                ClientRequest clientRequest = new ClientRequest("deleteUser", userLogin);
                Main.sendData(clientRequest);

                ServerResponse serverResponse = Main.getData();

                if (serverResponse.getSuccess()) {
                    User selectedItem = tableUsersControl.getSelectionModel().getSelectedItem();
                    tableUsersControl.getItems().remove(selectedItem);
                    AlertHandler.ShowInformationMessage("Успешно удалено!");
                } else AlertHandler.ShowErrorMessage("Что-то пошло не так...");
            } catch (NullPointerException e) {
                AlertHandler.ShowErrorMessage("Необходимо выбрать строку для удаления!");
            }

        });
    }



    private void fillTable() {
        try {
            ClientRequest clientRequest = new ClientRequest("getUsers", "");
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();


            List<User> itemsUser = Arrays.asList(objectMapper.readValue(jsonProd, User[].class));

            idUsersControl.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
            loginUsersControl.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
            nameUsersControl.setCellValueFactory(new PropertyValueFactory<User, Integer>("name"));
            lastNameUsersControl.setCellValueFactory(new PropertyValueFactory<User, Integer>("lastName"));
            ageUsersControl.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
            genderUsersControl.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
            tableUsersControl.setItems(FXCollections.observableArrayList(itemsUser));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}