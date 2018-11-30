package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Feature1Page3Controller {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button feature1Btn;

    @FXML
    private Button feature2Btn;

    @FXML
    private Button feature4Btn;

    @FXML
    private Button feature5Btn;

    @FXML
    private Label roomNameLabel;

    @FXML
    private Label roomTypeLabel;

    @FXML
    private Label floorLabel;

    @FXML
    private Label dateInLabel;

    @FXML
    private Label dateOutLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField telField;

    @FXML
    private Label reserveTypeLabel;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button acceptBtn;

    @FXML
    void acceptBtnHandle(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/Feature1Page1.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    @FXML
    void cancelBtnHandle(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/Feature1Page1.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    @FXML
    void handleFeature1Btn(ActionEvent event) {

    }

    @FXML
    void handleFeature2Btn(ActionEvent event) {

    }

    @FXML
    void handleFeature4Btn(ActionEvent event) {

    }

    @FXML
    void handleFeature5Btn(ActionEvent event) {

    }

}
