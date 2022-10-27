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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.Analysis;
import sample.entity.AnalysisForControl;
import sample.util.AlertHandler;
import sample.util.SceneChanger;

public class AnalysisControlUser {

    @FXML
    private Button logOutButton;

    @FXML
    private TableView<AnalysisForControl> tableAnalysisControl;

    @FXML
    private TableColumn<AnalysisForControl, String> a1;

    @FXML
    private TableColumn<AnalysisForControl, String> a2;

    @FXML
    private TableColumn<AnalysisForControl, String> a3;

    @FXML
    private TableColumn<AnalysisForControl, String> a4;

    @FXML
    private TableColumn<AnalysisForControl, Double> w1;

    @FXML
    private TableColumn<AnalysisForControl, Double> w2;

    @FXML
    private TableColumn<AnalysisForControl, Double> w3;

    @FXML
    private TableColumn<AnalysisForControl, Double> w4;

    @FXML
    private TextField search;

    @FXML
    private Button makeBar;

    @FXML
    private Button openTextFile;

    @FXML
    private BarChart<String, Number> barChart;


    @FXML
    void initialize() {
        try {
            ClientRequest clientRequest = new ClientRequest("getAnalysis", null);
            Main.sendData(clientRequest);

            ServerResponse serverResponse = Main.getData();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonProd = serverResponse.getData().toString();


            List<AnalysisForControl> items = new LinkedList<>(Arrays.asList(objectMapper.readValue(jsonProd, AnalysisForControl[].class)));
            fillTable(items);
        } catch (IOException e) {
            e.printStackTrace();
        }

        openTextFile.setOnAction(event -> openFile());

        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/UserMenu.fxml");
        });


        makeBar.setOnAction(event -> {
            try {
                barChart.getData().clear();
                AnalysisForControl analysisToBar = tableAnalysisControl.getSelectionModel().getSelectedItem();

                double w1 = analysisToBar.getW1();
                double w2 = analysisToBar.getW2();
                double w3 = analysisToBar.getW3();
                double w4 = analysisToBar.getW4();

                makeBarFunction (w1, w2, w3, w4);
            } catch (NullPointerException e) {
                AlertHandler.ShowErrorMessage("Необходимо выбрать строку для создания гистограммы!");
            }
        });
    }

    private void openFile() {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            assert desktop != null;
            desktop.open(new File("AnalysisFile.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable(List<AnalysisForControl> items) {

        a1.setCellValueFactory(new PropertyValueFactory<>("alternative1"));
        a2.setCellValueFactory(new PropertyValueFactory<>("alternative2"));
        a3.setCellValueFactory(new PropertyValueFactory<>("alternative3"));
        a4.setCellValueFactory(new PropertyValueFactory<>("alternative4"));
        w1.setCellValueFactory(new PropertyValueFactory<>("w1"));
        w2.setCellValueFactory(new PropertyValueFactory<>("w2"));
        w3.setCellValueFactory(new PropertyValueFactory<>("w3"));
        w4.setCellValueFactory(new PropertyValueFactory<>("w4"));
        tableAnalysisControl.setItems(FXCollections.observableArrayList(items));

        FilteredList<AnalysisForControl> filteredData = new FilteredList<>(FXCollections.observableArrayList(items), p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(analysis -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (analysis.getAlternative1().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (analysis.getAlternative2().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }else if (analysis.getAlternative3().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }else return analysis.getAlternative4().toLowerCase().contains(lowerCaseFilter);
        }));


        SortedList<AnalysisForControl> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableAnalysisControl.comparatorProperty());
        tableAnalysisControl.setItems(sortedData);
    }

    private void makeBarFunction(double w1,double w2, double w3, double w4){

        XYChart.Series ds = new XYChart.Series();
        ds.getData().add(new XYChart.Data<String, Number>("1 альт-ва", w1));
        ds.getData().add(new XYChart.Data<String, Number>("2 альт-ва", w2));
        ds.getData().add(new XYChart.Data<String, Number>("3 альт-ва", w3));
        ds.getData().add(new XYChart.Data<String, Number>("4 альт-ва", w4));
        barChart.getData().add(ds);
    }
}
