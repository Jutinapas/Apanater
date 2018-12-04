package controller;

import model.Reservation;
import model.Room;
import model.SqlConnection;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<ReserveRecord> tableView;

    @FXML
    private TableColumn<ReserveRecord, String> dateInCol;

    @FXML
    private TableColumn<ReserveRecord, String> dateOutCol;

    @FXML
    private TableColumn<ReserveRecord, String> reserveTypeCol;

    @FXML
    private TableColumn<ReserveRecord, String> nameCol;

    @FXML
    private TableColumn<ReserveRecord, String> telCol;

    @FXML
    private Button backBtn;

    private Room room;

    private ObservableList<ReserveRecord> records;

    public class ReserveRecord {

        private final SimpleStringProperty id_reserve, date_check_in, date_check_out, id_room, type_reserve, name_guest, phone_number, status;

        public ReserveRecord(Reservation reservation) {
            this.id_reserve = new SimpleStringProperty(reservation.getId_reservation() + "");
            this.date_check_in = new SimpleStringProperty(reservation.getDate_check_in());
            this.date_check_out = new SimpleStringProperty(reservation.getDate_check_out());
            this.id_room = new SimpleStringProperty(reservation.getId_room() + "");
            this.type_reserve = new SimpleStringProperty(reservation.getType_reserve());
            this.name_guest = new SimpleStringProperty(reservation.getName_guest());
            this.phone_number = new SimpleStringProperty(reservation.getPhone_number());
            this.status = new SimpleStringProperty(reservation.getStatus());
        }

        public String getId_reserve() {
            return id_reserve.get();
        }

        public SimpleStringProperty id_reserveProperty() {
            return id_reserve;
        }

        public String getDate_check_in() {
            return date_check_in.get();
        }

        public SimpleStringProperty date_check_inProperty() {
            return date_check_in;
        }

        public String getDate_check_out() {
            return date_check_out.get();
        }

        public SimpleStringProperty date_check_outProperty() {
            return date_check_out;
        }

        public String getId_room() {
            return id_room.get();
        }

        public SimpleStringProperty id_roomProperty() {
            return id_room;
        }

        public String getType_reserve() {
            return type_reserve.get();
        }

        public SimpleStringProperty type_reserveProperty() {
            return type_reserve;
        }

        public String getName_guest() {
            return name_guest.get();
        }

        public SimpleStringProperty name_guestProperty() {
            return name_guest;
        }

        public String getPhone_number() {
            return phone_number.get();
        }

        public SimpleStringProperty phone_numberProperty() {
            return phone_number;
        }

    }

    @FXML
    void initialize() {
        records = FXCollections.observableArrayList();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                roomNameLabel.setText(room.getRoom_name());
                roomTypeLabel.setText(SqlConnection.getSqlConnection().getTypeRoomByID(room.getId_type_room()).getTypeRoom());
                floorLabel.setText(room.getFloor() + "");

                for (Reservation reservation: SqlConnection.getSqlConnection().selectReservationWithRoom(room.getId_room())) {
                    System.out.println(reservation.getName_guest());
                    records.add(new ReserveRecord(reservation));
                }
            }
        });

        dateInCol.setCellValueFactory(new PropertyValueFactory<ReserveRecord, String>("date_check_in"));
        dateOutCol.setCellValueFactory(new PropertyValueFactory<ReserveRecord, String>("date_check_out"));
        reserveTypeCol.setCellValueFactory(new PropertyValueFactory<ReserveRecord, String>("type_reserve"));
        nameCol.setCellValueFactory(new PropertyValueFactory<ReserveRecord, String>("name_guest"));
        telCol.setCellValueFactory(new PropertyValueFactory<ReserveRecord, String>("phone_number"));

        tableView.setItems(records);
    }

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

}
