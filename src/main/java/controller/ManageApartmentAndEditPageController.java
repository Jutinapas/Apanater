package controller;

import javafx.scene.Parent;
import model.ManageDatePay;
import model.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManageApartmentAndEditPageController {

    @FXML
    private GridPane gridPane;

    //ปุ่มของเมนู
    @FXML
    private Button feature1Btn;
    @FXML
    private Button feature2Btn;
    @FXML
    private Button feature4Btn;
    @FXML
    private Button feature5Btn;

    //จัดการหอพัก
    //ไบร์ทเขียน
    @FXML
    private Label name_apartment;

    @FXML
    private Label date_pay_money;

    @FXML
    private Button edit_data;

    @FXML
    private Button accept_button;
    @FXML
    private Button cancel_button;

    @FXML
    private Spinner<Integer> list_day ;
    @FXML
    private TextField name_apartment_edit;



    public void initialize(){
        name_apartment.setVisible(true);
        edit_data.setVisible(true);
        accept_button.setVisible(false);
        cancel_button.setVisible(false);
        list_day.setVisible(false);
        name_apartment_edit.setVisible(false);
        name_apartment.setText(DBConnector.getDBConnector().selectNameOfApartment());
        date_pay_money.setText(DBConnector.getDBConnector().selectDatePayMoney());


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
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
        gridPane.getChildren().setAll(pane);

    }

    //ไปหน้าจัดการหอพักจากเมนู
    @FXML
    void handleFeature5Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/ManageApartmentAndEditPage.fxml"));
        gridPane.getChildren().setAll(pane);

    }

    //เปลี่ยนสู่โหมดจัดการหอพัก
    @FXML
    void changeToEditMode(ActionEvent event) throws IOException {
        name_apartment.setVisible(false);
        accept_button.setVisible(true);
        cancel_button.setVisible(true);
        edit_data.setVisible(false);
        list_day.setVisible(true);
        name_apartment_edit.setVisible(true);
        name_apartment_edit.setText(DBConnector.getDBConnector().selectNameOfApartment());
        name_apartment_edit.setEditable(true);

        ManageDatePay m =new ManageDatePay();
        SpinnerValueFactory<Integer> dateValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,m.getDays(),Integer.parseInt(DBConnector.getDBConnector().selectDatePayMoney()));
        list_day.setValueFactory(dateValueFactory);

    }

    //ตกลงการจัดการหอพัก
    @FXML
    void acceptEdit(ActionEvent event){
        if (list_day.getValue() != null && !name_apartment_edit.getText().isEmpty()) {
            String date_we_choose_to_edit = list_day.getValue().toString();
            String name_apartment_edit_textfield = name_apartment_edit.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("คุณต้องการจะแก้ไขข้อมูลหอพักใช่หรือไม่ ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                DBConnector.getDBConnector().updateApartmentNameAndDatePayMoney(name_apartment_edit_textfield, date_we_choose_to_edit);
                initialize();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ไม่สามารถแก้ไขได้");
            alert.setHeaderText("โปรดกรอกข้อมูลให้ครบ");
        }
    }

    //ยกเลิกการจัดการหอพัก
    @FXML
    void cancelEdit(ActionEvent event){
        initialize();
    }

}
