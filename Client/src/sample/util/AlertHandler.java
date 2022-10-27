package sample.util;

import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

public class AlertHandler {
    public static void ShowErrorMessage(String message){
        Alert alert = new Alert(ERROR, message);
        alert.show();
    }

    public static void ShowInformationMessage(String message){
        Alert alert = new Alert(INFORMATION, message);
        alert.show();
    }

    public static void ShowWarningMessage(String message){
        Alert alert = new Alert(WARNING, message);
        alert.show();
    }
}
