package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Feature1Page2Controller {

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
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> dateInCol;

    @FXML
    private TableColumn<?, ?> dateOutCol;

    @FXML
    private TableColumn<?, ?> reserveTypeCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> telCol;

    @FXML
    private Button backBtn;

    @FXML
    void backBtnHandle(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/Feature1Page1.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    @FXML
    void handleFeature1Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/Feature1Page1.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    @FXML
    void handleFeature2Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/room-main.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    @FXML
    void handleFeature4Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    @FXML
    void handleFeature5Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/ManageApartmentAndEditPage.fxml"));
        gridPane.getChildren().setAll(pane);
    }

}
