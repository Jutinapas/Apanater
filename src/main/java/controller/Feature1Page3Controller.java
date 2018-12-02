package controller;

import javafx.scene.control.*;
import model.Room;
import model.SqlConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

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

    private Room room;
    private LocalDate date_in, date_out;
    private int count;
    private String type;

    @FXML
    void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dateInLabel.setText(date_in.toString());
                dateOutLabel.setText(date_out.toString());
                roomNameLabel.setText(room.getRoom_name());
                roomTypeLabel.setText(SqlConnection.getSqlConnection().getTypeRoomByID(room.getId_type_room()).getTypeRoom());
                floorLabel.setText(room.getFloor() + "");
                reserveTypeLabel.setText(type);
            }
        });
    }

    @FXML
    void acceptBtnHandle(ActionEvent event) throws IOException {
        if (!nameField.getText().isEmpty() && !telField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("คุณต้องการจะเพิ่มการจองใหม่ใช่หรือไม่ ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK){
                // new reserve
                SqlConnection.getSqlConnection().insertReservation(date_in, date_out, room.getId_room(), type, nameField.getText(), telField.getText());
                int id_reserve = SqlConnection.getSqlConnection().getRecentReservation();
                if (type.equals("DAILY")) {
                    // new daily debt
                    long daysBetween = DAYS.between(date_in, date_out);
                    Double debt = SqlConnection.getSqlConnection().getTypeRoomByID(room.getId_type_room()).getRentPerDay();
                    double balance = debt * daysBetween;
                    SqlConnection.getSqlConnection().insertDebt(id_reserve, date_out.toString(), balance);
                } else if (type.equals("MONTHLY")) {
                    // new monthly debt
                    double balance = SqlConnection.getSqlConnection().getTypeRoomByID(room.getId_type_room()).getRentPerMonth();
                    for (int i = 1; i <= count; i++) {
                        LocalDate date = date_in.plusMonths(i);
                        SqlConnection.getSqlConnection().insertDebt(id_reserve, date.toString(), balance);
                    }
                }
                GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/Feature1Page1.fxml"));
                gridPane.getChildren().setAll(pane);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ไม่สามารถเพิ่มการจองได้");
            alert.setHeaderText("โปรดกรอกข้อมูลให้ครบก่อนกดยืนยัน");
            alert.showAndWait();
        }
    }

    @FXML
    void cancelBtnHandle(ActionEvent event) throws IOException {
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
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/DebtReminder.fxml"));
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

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setDate_in(LocalDate date_in) {
        this.date_in = date_in;
    }

    public void setDate_out(LocalDate date_out) {
        this.date_out = date_out;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }

}
