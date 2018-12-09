package controller;
import model.DBConnector;
import model.TypeRoom;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class PageRoomManagementAddNewRoomController {

    @FXML
    private TextField tf;

    @FXML
    private Spinner<Integer> spinner = new Spinner<Integer>();


    @FXML
    private ComboBox<String> cb;

    @FXML
    private Button btnIC;

    @FXML
    private Button btnC;

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

    private ArrayList<TypeRoom> typeRooms;


    @FXML
    public  void initialize() {
        setSpinner(0,Integer.MAX_VALUE);
        setComboBox();

    }


    @FXML
    void setSpinner(int min,int max){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, min);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);
        spinner.getValueFactory().setValue(1);
        TextFormatter formatter = new TextFormatter(valueFactory.getConverter(), valueFactory.getValue());
        spinner.getEditor().setTextFormatter(formatter);
        valueFactory.valueProperty().bindBidirectional(formatter.valueProperty());

    }

    @FXML
    void setComboBox(){
        typeRooms = DBConnector.getDBConnector().selectAllTypeRoom();
        ArrayList<String> lst = new ArrayList<>();
        for(TypeRoom i:typeRooms){
            lst.add(i.getTypeRoom());
        }
        cb.setItems(FXCollections.observableArrayList(
                lst));
    }

    @FXML
    void  clear(){
        cb.getSelectionModel().select("");
        tf.setText("");
        spinner.getValueFactory().setValue(1);
    }

    @FXML
    void BtnCorrect(ActionEvent event) throws IOException{

        if(tf.getText().length() > 0 && cb.getValue() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("คอนเฟิร์ม การเพิ่มห้องใหม่");
            alert.setHeaderText("คอนเฟิร์ม การเพิ่มห้อง");
            alert.setContentText("คุณแน่ใจที่จะเพิ่ม ห้อง: "+tf.getText()+" ประเถทห้อง: "+cb.getValue()+" ชั้น: "+spinner.getValue()+" ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK){
                //update data to database
                int t = 0;
                for (TypeRoom i : typeRooms){
                    if (i.getTypeRoom().equals(cb.getValue())){
                        t = i.getIdTypeRoom();
                    }
                }
                DBConnector.getDBConnector().insertRoom(tf.getText(),t,spinner.getValue());
                GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
                gridPane.getChildren().setAll(pane);
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ไม่สามารถเพิ่มห้องได้");
            alert.setHeaderText("โปรดกรอกข้อมูลให้ครบก่อน");
            alert.showAndWait();

        }


    }

    @FXML
    void BtnInCorrect(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
        gridPane.getChildren().setAll(pane);

    }

    //ไปหน้าค้นหาจากเมนู
    @FXML
    void handleFeature1Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/Feature1Page1.fxml"));
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
