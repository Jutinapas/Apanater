package controller;


import model.DBConnector;
import model.TypeRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class PageRoomManagementEditTypeRoomController {

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
    private Button btnIC;

    @FXML
    private Button btnC;

    @FXML
    private TextField tf;

    @FXML
    private Spinner<Double> spinnerMonth;

    @FXML
    private Spinner<Double> spinnerDay;

    @FXML
    private Button leftArrow;

    String datasave ;
    Double msave,dsave;


    @FXML
    public  void initialize() {
        setSpinnerMoth(0,Double.MAX_VALUE);
        setSpinnerDay(0,Double.MAX_VALUE);
//        setData("sex room",3500,300);

    }

    @FXML
    void setData(String textF,Double m,Double d,TypeRoom typeRoom){
        tf.setText(textF);
        spinnerMonth.getValueFactory().setValue(m);
        spinnerDay.getValueFactory().setValue(d);

        tr = typeRoom;

        datasave = textF;
        msave = m;
        dsave = d;
    }


    @FXML
    void setSpinnerMoth(double min,double max){
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, min);
        spinnerMonth.setValueFactory(valueFactory);
        spinnerMonth.setEditable(true);
        TextFormatter formatter = new TextFormatter(valueFactory.getConverter(), valueFactory.getValue());
        spinnerMonth.getEditor().setTextFormatter(formatter);
        valueFactory.valueProperty().bindBidirectional(formatter.valueProperty());
    }

    @FXML
    void setSpinnerDay(double min,double max){
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, min);
        spinnerDay.setValueFactory(valueFactory);
        spinnerDay.setEditable(true);
        TextFormatter formatter = new TextFormatter(valueFactory.getConverter(), valueFactory.getValue());
        spinnerDay.getEditor().setTextFormatter(formatter);
        valueFactory.valueProperty().bindBidirectional(formatter.valueProperty());
    }

    @FXML
    void  clear(){
        tf.setText("");
        spinnerDay.getValueFactory().setValue(1.0);
        spinnerMonth.getValueFactory().setValue(1.0);
    }

    @FXML
    void BtnCorrect(ActionEvent event) throws IOException {
        if(tf.getText().length() > 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("คุณต้องการจะแก้ไขข้อมูลประเภทห้องใช่หรือไม่ ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK){
                DBConnector.getDBConnector().updateTypeRoom(tr.getIdTypeRoom(),tf.getText(),spinnerMonth.getValue(),spinnerDay.getValue());
                GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementTypeAll.fxml"));
                gridPane.getChildren().setAll(pane);
                clear();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ไม่สามารถแก้ไขประเภทห้องได้");
            alert.setHeaderText("โปรดกรอกข้อมูลให้ครบ");
            alert.showAndWait();
        }

    }

    @FXML
    void BtnInCorrect(ActionEvent event) throws IOException {
//        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
//        gridPane.getChildren().setAll(pane);

        Button b = (Button) event.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load(), 1280, 800));

            PageRoomManagementInfoTypeRoomController controller = loader.getController();
            controller.setData(datasave,msave,dsave,tr);

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    @FXML
    void BtnLeftArrow(ActionEvent event) throws IOException {
//        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
//        gridPane.getChildren().setAll(pane);

        Button b = (Button) event.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load(), 1280, 800));

            PageRoomManagementInfoTypeRoomController controller = loader.getController();
            controller.setData(datasave,msave,dsave,tr);

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

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
