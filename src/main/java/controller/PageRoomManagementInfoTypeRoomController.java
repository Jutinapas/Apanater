package controller;

import model.DBConnector;
import model.TypeRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class PageRoomManagementInfoTypeRoomController {

    private TypeRoom tr;

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
    private Button leftArrow;

    @FXML
    private Button btnIC;

    @FXML
    private Button btnEdit;

    @FXML
    private Label label_type;

    @FXML
    private Label label_amountD;

    @FXML
    private Label label_amountM;


    @FXML
    public void setData(String type, Double m, Double d, TypeRoom typeRoom){
        label_type.setText(type);
        label_amountD.setText(""+d);
        label_amountM.setText(""+m);
        tr = typeRoom;

    }

    @FXML
    void BtnDelete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("คุณต้องการจะลบประเภทห้องใช่หรือไม่ ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            DBConnector.getDBConnector().deleteTypeRoom(tr.getIdTypeRoom());
            GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementTypeAll.fxml"));
            gridPane.getChildren().setAll(pane);
        }
    }

    @FXML
    void BtnEdit(ActionEvent event) throws IOException {
//        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementEditTypeRoom.fxml"));
//        gridPane.getChildren().setAll(pane);

        Button b = (Button) event.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PageRoomManagementEditTypeRoom.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load(), 1280, 800));

            PageRoomManagementEditTypeRoomController controller = loader.getController();
            controller.setData(label_type.getText(),Double.parseDouble(label_amountM.getText()),Double.parseDouble(label_amountD.getText()),tr);

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void BtnLeftArrow(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementTypeAll.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    //ไปหน้าค้นหาจากเมนู
    @FXML
    void handleFeature1Btn(ActionEvent event) throws IOException {
        URL url = new File("src/main/resources/fxml/Feature1Page1.fxml").toURL();
        GridPane pane = FXMLLoader.load(url);
        gridPane.getChildren().setAll(pane);
    }

    //ไปหน้าแจ้งชำระจากเมนู
    @FXML
    void handleFeature2Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/DebtReminder.fxml"));
        gridPane.getChildren().setAll(pane);
    }

    //ไปหน้าจัดการห้องจากเมนู
    @FXML
    void handleFeature4Btn(ActionEvent event) throws IOException {
        //Fluke Pipatphol coming
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
        gridPane.getChildren().setAll(pane);

    }

    //ไปหน้าจัดการหอพักจากเมนู
    @FXML
    void handleFeature5Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/ManageApartmentAndEditPage.fxml"));
        gridPane.getChildren().setAll(pane);
    }
}
