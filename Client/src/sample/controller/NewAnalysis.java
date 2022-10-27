package sample.controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import sample.Main;
import sample.cooperation.ClientRequest;
import sample.cooperation.ServerResponse;
import sample.entity.Analysis;
import sample.util.AlertHandler;
import sample.util.SceneChanger;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewAnalysis extends AnalysisAdd {

    @FXML
    private Button calculate;

    @FXML
    private Button save;

    @FXML
    private Button logOutButtonFrom2;

    @FXML
    private Spinner<Integer> E1Z11;

    @FXML
    private Spinner<Integer> E1Z12;

    @FXML
    private Spinner<Integer> E1Z13;

    @FXML
    private Spinner<Integer> E1Z14;

    @FXML
    private Spinner<Integer> E1Z21;

    @FXML
    private Spinner<Integer> E1Z22;

    @FXML
    private Spinner<Integer> E1Z23;

    @FXML
    private Spinner<Integer> E1Z24;

    @FXML
    private Spinner<Integer> E1Z31;

    @FXML
    private Spinner<Integer> E1Z32;

    @FXML
    private Spinner<Integer> E1Z33;

    @FXML
    private Spinner<Integer> E1Z34;

    @FXML
    private Spinner<Integer> E1Z41;

    @FXML
    private Spinner<Integer> E1Z42;

    @FXML
    private Spinner<Integer> E1Z43;

    @FXML
    private Spinner<Integer> E1Z44;

    @FXML
    private Spinner<Integer> E2Z11;

    @FXML
    private Spinner<Integer> E2Z12;

    @FXML
    private Spinner<Integer> E2Z13;

    @FXML
    private Spinner<Integer> E2Z14;

    @FXML
    private Spinner<Integer> E2Z21;

    @FXML
    private Spinner<Integer> E2Z22;

    @FXML
    private Spinner<Integer> E2Z23;

    @FXML
    private Spinner<Integer> E2Z24;

    @FXML
    private Spinner<Integer> E2Z31;

    @FXML
    private Spinner<Integer> E2Z32;

    @FXML
    private Spinner<Integer> E2Z33;

    @FXML
    private Spinner<Integer> E2Z34;

    @FXML
    private Spinner<Integer> E2Z41;

    @FXML
    private Spinner<Integer> E2Z42;

    @FXML
    private Spinner<Integer> E2Z43;

    @FXML
    private Spinner<Integer> E2Z44;

    @FXML
    private TextField E1f11;

    @FXML
    private TextField E1f12;

    @FXML
    private TextField E1f13;

    @FXML
    private TextField E1f14;

    @FXML
    private TextField E2f11;

    @FXML
    private TextField E2f12;

    @FXML
    private TextField E2f13;

    @FXML
    private TextField E2f14;

    @FXML
    private TextField E1v11;

    @FXML
    private TextField E1v12;

    @FXML
    private TextField E1v13;

    @FXML
    private TextField E1v14;

    @FXML
    private TextField E2v11;

    @FXML
    private TextField E2v12;

    @FXML
    private TextField E2v13;

    @FXML
    private TextField E2v14;

    @FXML
    private TextField W1;

    @FXML
    private TextField W2;

    @FXML
    private TextField W3;

    @FXML
    private TextField W4;


    Analysis newAnalysis = new Analysis();
    double w1, w2, w3, w4;
    int e1f11, e1f12, e1f13, e1f14, e2f11, e2f12, e2f13, e2f14;

    @FXML
    void initialize() {

        setConfines(newAnalysis);
        calculate.setOnAction(event -> calculateResults());

        save.setOnAction(event -> {
            sendResultsToServer();
        });

        logOutButtonFrom2.setOnAction(event -> {
            logOutButtonFrom2.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");

        });

    }


    void setConfines(Analysis newAnalysis) {
        int i;
        List<Integer> list = new ArrayList<Integer>();
        ObservableList<Integer> numbers = FXCollections.observableList(list);
        for (i = 0; i < newAnalysis.getScale()+1; i++) {
            numbers.add(i);
        }


        E1Z11.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z12.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z12.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E1Z21.setValueFactory(newValueF2);
            }
        });
        E1Z13.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z13.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E1Z31.setValueFactory(newValueF2);
            }
        });
        E1Z14.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z14.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E1Z41.setValueFactory(newValueF2);
            }
        });
        E1Z22.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z23.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z23.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E1Z32.setValueFactory(newValueF2);
            }
        });
        E1Z24.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z24.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E1Z42.setValueFactory(newValueF2);
            }
        });
        E1Z33.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z34.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E1Z34.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E1Z43.setValueFactory(newValueF2);
            }
        });
        E1Z44.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));


        //e2
        E2Z11.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z12.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z12.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E2Z21.setValueFactory(newValueF2);
            }
        });
        E2Z13.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z13.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E2Z31.setValueFactory(newValueF2);
            }
        });
        E2Z14.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z14.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E2Z41.setValueFactory(newValueF2);
            }
        });
        E2Z22.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z23.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z23.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E2Z32.setValueFactory(newValueF2);
            }
        });
        E2Z24.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z24.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E2Z42.setValueFactory(newValueF2);
            }
        });
        E2Z33.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z34.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
        E2Z34.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                ObservableList<Integer> newValueF = FXCollections.observableArrayList(newAnalysis.getScale() - newValue);
                SpinnerValueFactory<Integer> newValueF2 = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(newValueF);
                E2Z43.setValueFactory(newValueF2);
            }
        });
        E2Z44.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numbers));
    }

    void calculateResults() {
        try {

            e1f11 = E1Z12.getValue() + E1Z13.getValue() + E1Z14.getValue();
            e1f12 = E1Z21.getValue() + E1Z23.getValue() + E1Z24.getValue();
            e1f13 = E1Z31.getValue() + E1Z32.getValue() + E1Z34.getValue();
            e1f14 = E1Z41.getValue() + E1Z42.getValue() + E1Z43.getValue();

            e2f11 = E2Z12.getValue() + E2Z13.getValue() + E2Z14.getValue();
            e2f12 = E2Z21.getValue() + E2Z23.getValue() + E2Z24.getValue();
            e2f13 = E2Z31.getValue() + E2Z32.getValue() + E2Z34.getValue();
            e2f14 = E2Z41.getValue() + E2Z42.getValue() + E2Z43.getValue();

            E1f11.setText(Integer.toString(e1f11) + "/" + newAnalysis.getScale());
            E1f12.setText(Integer.toString(e1f12) + "/" + newAnalysis.getScale());
            E1f13.setText(Integer.toString(e1f13) + "/" + newAnalysis.getScale());
            E1f14.setText(Integer.toString(e1f14) + "/" + newAnalysis.getScale());

            E2f11.setText(Integer.toString(e2f11) + "/" + newAnalysis.getScale());
            E2f12.setText(Integer.toString(e2f12) + "/" + newAnalysis.getScale());
            E2f13.setText(Integer.toString(e2f13) + "/" + newAnalysis.getScale());
            E2f14.setText(Integer.toString(e2f14) + "/" + newAnalysis.getScale());

            E1v11.setText(Integer.toString(e1f11) + "/" + newAnalysis.getScale() + "/12");
            E1v12.setText(Integer.toString(e1f12) + "/" + newAnalysis.getScale() + "/12");
            E1v13.setText(Integer.toString(e1f13) + "/" + newAnalysis.getScale() + "/12");
            E1v14.setText(Integer.toString(e1f14) + "/" + newAnalysis.getScale() + "/12");

            E2v11.setText(Integer.toString(e2f11) + "/" + newAnalysis.getScale() + "/12");
            E2v12.setText(Integer.toString(e2f12) + "/" + newAnalysis.getScale() + "/12");
            E2v13.setText(Integer.toString(e2f13) + "/" + newAnalysis.getScale() + "/12");
            E2v14.setText(Integer.toString(e2f14) + "/" + newAnalysis.getScale() + "/12");

            double scale = newAnalysis.getScale();
            double e1f11d = e1f11;
            double e1f12d = e1f12;
            double e1f13d = e1f13;
            double e1f14d = e1f14;
            double e2f11d = e2f11;
            double e2f12d = e2f12;
            double e2f13d = e2f13;
            double e2f14d = e2f14;

            w1 = e1f11d / scale / 12 + e2f11d / scale / 12;
            w2 = e1f12d / scale / 12 + e2f12d / scale / 12;
            w3 = e1f13d / scale / 12 + e2f13d / scale / 12;
            w4 = e1f14d / scale / 12 + e2f14d / scale / 12;


            W1.setText(String.format("%.7f", w1));
            W2.setText(String.format("%.7f", w2));
            W3.setText(String.format("%.7f", w3));
            W4.setText(String.format("%.7f", w4));

        } catch (NullPointerException ex) {
            AlertHandler.ShowErrorMessage("Заполните все поля!");
        }

    }

    void sendResultsToServer() {
        try {

            e1f11 = E1Z12.getValue() + E1Z13.getValue() + E1Z14.getValue();
            e1f12 = E1Z21.getValue() + E1Z21.getValue() + E1Z24.getValue();
            e1f13 = E1Z31.getValue() + E1Z32.getValue() + E1Z34.getValue();
            e1f14 = E1Z41.getValue() + E1Z42.getValue() + E1Z43.getValue();

            e2f11 = E2Z12.getValue() + E2Z13.getValue() + E2Z14.getValue();
            e2f12 = E2Z21.getValue() + E2Z21.getValue() + E2Z24.getValue();
            e2f13 = E2Z31.getValue() + E2Z32.getValue() + E2Z34.getValue();
            e2f14 = E2Z41.getValue() + E2Z42.getValue() + E2Z43.getValue();

            double scale = newAnalysis.getScale();
            double e1f11d = e1f11;
            double e1f12d = e1f12;
            double e1f13d = e1f13;
            double e1f14d = e1f14;
            double e2f11d = e2f11;
            double e2f12d = e2f12;
            double e2f13d = e2f13;
            double e2f14d = e2f14;

            w1 = e1f11d / scale / 12 + e2f11d / scale / 12;
            w2 = e1f12d / scale / 12 + e2f12d / scale / 12;
            w3 = e1f13d / scale / 12 + e2f13d / scale / 12;
            w4 = e1f14d / scale / 12 + e2f14d / scale / 12;

            newAnalysis.setW1(w1);
            newAnalysis.setW2(w2);
            newAnalysis.setW3(w3);
            newAnalysis.setW4(w4);
            newAnalysis.setN(12);

        } catch (Exception e) {
            AlertHandler.ShowErrorMessage("Возникла ошибка при создании анализа.");
        }

        try{ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newAnalysis);
        ClientRequest clientRequest = new ClientRequest("addAnalysis", json);
        Main.sendData(clientRequest);
        ServerResponse serverResponse = Main.getData();
        if (serverResponse.getSuccess()) {
            logOutButtonFrom2.getScene().getWindow().hide();
            SceneChanger.getInstance().changeScene("/sample/fxml/AdminMenu.fxml");
            AlertHandler.ShowInformationMessage("Анализ успешно создан.");
        } else {
            AlertHandler.ShowErrorMessage("Возникла ошибка при создании анализа.");
        }} catch (IOException e) {
            AlertHandler.ShowErrorMessage("Возникла ошибка при создании анализа.");
        }


    }
}
