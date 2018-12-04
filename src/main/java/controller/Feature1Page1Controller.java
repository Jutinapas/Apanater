package controller;

import model.Room;
import model.SqlConnection;
import model.TypeRoom;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Feature1Page1Controller {

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
    private Button monthlyBtn;
    @FXML
    private Button dailyBtn;

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private Spinner<Integer> numMonthField;
    @FXML
    private ComboBox<TypeRoom> roomTypeBox;
    @FXML
    private ComboBox<Integer> floorBox;

    @FXML
    private Button searchBtn;

    @FXML
    private Label toDateLabel;
    @FXML
    private Label numMonthLabel;
    @FXML
    private Label errorLabel;

    @FXML
    private TableView<RoomRecord> tableView;
    @FXML
    private TableColumn<RoomRecord, String> roomNameCol;
    @FXML
    private TableColumn<RoomRecord, String> roomTypeCol;
    @FXML
    private TableColumn<RoomRecord, String> floorCol;
    @FXML
    private TableColumn<RoomRecord, RoomRecord> buttonCol;

    private ObservableList<TypeRoom> typeRooms;

    private ObservableList<RoomRecord> rooms;

    private final int MONTHLY = 0;
    private final int DAILY = 1;
    private int reserveType;

    public class RoomRecord {

        private final SimpleStringProperty id_room, room_name, id_type_room, type_room, floor, status;

        public RoomRecord(Room room) {
            this.id_room = new SimpleStringProperty(room.getId_room() + "");
            this.room_name = new SimpleStringProperty(room.getRoom_name());
            this.id_type_room = new SimpleStringProperty(room.getId_type_room() + "");
            this.type_room = new SimpleStringProperty(SqlConnection.getSqlConnection().getTypeRoomByID(room.getId_type_room()).getTypeRoom());
            this.floor = new SimpleStringProperty(room.getFloor() + "");
            this.status = new SimpleStringProperty(room.getStatus());
        }

        public String getId_room() {
            return id_room.get();
        }

        public SimpleStringProperty id_roomProperty() {
            return id_room;
        }

        public String getRoom_name() {
            return room_name.get();
        }

        public SimpleStringProperty room_nameProperty() {
            return room_name;
        }

        public String getId_type_room() {
            return id_type_room.get();
        }

        public String getType_room() {
            return type_room.get();
        }

        public SimpleStringProperty type_roomProperty() {
            return type_room;
        }

        public SimpleStringProperty id_type_roomProperty() {
            return id_type_room;
        }

        public String getFloor() {
            return floor.get();
        }

        public SimpleStringProperty floorProperty() {
            return floor;
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }
    }

    @FXML
    private void initialize() {

        // Setup
        reserveType = MONTHLY;

        // Spinner
        SpinnerValueFactory factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
        numMonthField.setValueFactory(factory);
        numMonthField.setEditable(true);
        numMonthField.getValueFactory().setValue(1);
        TextFormatter formatter = new TextFormatter(factory.getConverter(), factory.getValue());
        numMonthField.getEditor().setTextFormatter(formatter);
        factory.valueProperty().bindBidirectional(formatter.valueProperty());

        // ComboBox
        typeRooms = FXCollections.observableArrayList();
        typeRooms.add(new TypeRoom(0, "ทุกประเภท", 0, 0, "active"));
        typeRooms.addAll(SqlConnection.getSqlConnection().selectAllTypeRoom());
        Callback<ListView<TypeRoom>, ListCell<TypeRoom>> typeFactory = lv -> new ListCell<TypeRoom>() {

            @Override
            protected void updateItem(TypeRoom typeRoom, boolean empty) {
                super.updateItem(typeRoom, empty);
                setText(empty ? "" : typeRoom.getTypeRoom());
            }

        };
        roomTypeBox.setConverter(new StringConverter<TypeRoom>() {
            @Override
            public String toString(TypeRoom type) {
                if (type == null){
                    return null;
                } else {
                    return type.getIdTypeRoom() + "";
                }
            }

            @Override
            public TypeRoom fromString(String id) {
                return null;
            }
        });
        roomTypeBox.setCellFactory(typeFactory);
        roomTypeBox.setButtonCell(typeFactory.call(null));
        roomTypeBox.setItems(typeRooms);
        roomTypeBox.getSelectionModel().selectFirst();

        SortedSet<Integer> temp = new TreeSet<>();
        for (Room room : SqlConnection.getSqlConnection().selectAllRoom()) {
            temp.add(room.getFloor());
        }
        ObservableList list = FXCollections.observableArrayList();
        list.add("ทุกชั้น");
        list.addAll(temp);
        floorBox.setItems(list);
        floorBox.getSelectionModel().selectFirst();

        // TableView
        rooms = FXCollections.observableArrayList();
        tableView.setEditable(true);

        roomNameCol.setCellValueFactory(new PropertyValueFactory<RoomRecord, String>("room_name"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<RoomRecord, String>("type_room"));
        floorCol.setCellValueFactory(new PropertyValueFactory<RoomRecord, String>("floor"));

        buttonCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RoomRecord, RoomRecord>, ObservableValue<RoomRecord>>() {
            @Override
            public ObservableValue<RoomRecord> call(TableColumn.CellDataFeatures<RoomRecord, RoomRecord> features) {
                return new ReadOnlyObjectWrapper(features.getValue());
            }
        });
        buttonCol.setCellFactory(new Callback<TableColumn<RoomRecord, RoomRecord>, TableCell<RoomRecord, RoomRecord>>() {
            @Override
            public TableCell<RoomRecord, RoomRecord> call(TableColumn<RoomRecord, RoomRecord> buttonCol) {
                return new TableCell<RoomRecord, RoomRecord>() {

                    final Button descButton = new Button();
                    final Button reserveButton = new Button();
                    ImageView view = new ImageView();

                    {
                        descButton.setMinWidth(150);
                        descButton.setMinHeight(50);
                        reserveButton.setMinWidth(150);
                        reserveButton.setMinHeight(50);
                        view.setImage(new Image("images/rounded-add-button.png"));
                        view.setFitHeight(25);
                        view.setFitWidth(25);
                        reserveButton.setGraphic(view);
                    }

                    @Override
                    public void updateItem(final RoomRecord room, boolean empty) {
                        super.updateItem(room, empty);
                        if (room != null) {

                            descButton.setText("ดูรายละเอียด");
                            descButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Stage stage = (Stage) gridPane.getScene().getWindow();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Feature1Page2.fxml"));
                                    try {
                                        stage.setScene(new Scene(loader.load(), 1280, 800));
                                        Feature1Page2Controller controller = loader.getController();
                                        controller.setRoom(new Room(Integer.parseInt(room.getId_room()), room.getRoom_name(), Integer.parseInt(room.getId_type_room()), Integer.parseInt(room.getFloor()), room.getStatus()));
                                        stage.show();
                                    }
                                    catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            reserveButton.setText("จองเลย");
                            reserveButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                        Stage stage = (Stage) gridPane.getScene().getWindow();
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Feature1Page3.fxml"));
                                        try {
                                            stage.setScene(new Scene(loader.load(), 1280, 800));
                                            Feature1Page3Controller controller = loader.getController();

                                            LocalDate date_in = fromDatePicker.getValue();
                                            controller.setDate_in(date_in);
                                            if (reserveType == MONTHLY) {
                                                controller.setType("MONTHLY");
                                                controller.setDate_out(date_in.plusMonths(numMonthField.getValue()));
                                                controller.setCount(numMonthField.getValue());
                                            } else if (reserveType == DAILY) {
                                                controller.setType("DAILY");
                                                controller.setDate_out(toDatePicker.getValue());
                                            }
                                            controller.setRoom(new Room(Integer.parseInt(room.getId_room()), room.getRoom_name(), Integer.parseInt(room.getId_type_room()), Integer.parseInt(room.getFloor()), room.getStatus()));
                                            stage.show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                }
                            });

                            GridPane gridPane = new GridPane();
                            gridPane.setMinSize(325, 75);
                            gridPane.setPadding(new Insets(5, 5, 5, 5));
                            gridPane.setVgap(5);
                            gridPane.setHgap(12.5);
                            gridPane.setAlignment(Pos.CENTER);

                            gridPane.add(descButton, 0, 0);
                            gridPane.add(reserveButton, 1, 0);

                            setGraphic(gridPane);

                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        update();

    }

    @FXML
    void update() {
        tableView.setItems(rooms);
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

    @FXML
    void handleDailyBtn(ActionEvent event) {
        if (reserveType == MONTHLY) {
            swapDisable(numMonthField);
            swapDisable(numMonthLabel);
            numMonthField.getValueFactory().setValue(1);
            swapDisable(toDatePicker);
            toDatePicker.setValue(null);
            swapDisable(toDateLabel);

            reserveType = DAILY;
            rooms.clear();
        }
    }

    @FXML
    void handleMonthlyBtn(ActionEvent event) {
        if (reserveType == DAILY) {
            swapDisable(toDatePicker);
            toDatePicker.setValue(null);
            swapDisable(toDateLabel);
            swapDisable(numMonthField);
            numMonthField.getValueFactory().setValue(1);
            swapDisable(numMonthLabel);

            reserveType = MONTHLY;
            rooms.clear();
        }
    }

    @FXML
    private void swapDisable(Node node) {
        if (node.isDisable()) {
            node.setDisable(false);
            node.setVisible(true);
        }
        else {
            node.setDisable(true);
            node.setVisible(false);
        }
    }

    @FXML
    void handleSearchBtn(ActionEvent event) throws IOException {
        SqlConnection instance = SqlConnection.getSqlConnection();

        LocalDate date_in = null;
        LocalDate date_out = null;

        if (reserveType == MONTHLY && fromDatePicker.getValue() != null) {
            date_in = fromDatePicker.getValue();
            date_out = date_in.plusMonths(numMonthField.getValue());
        }

        else if (reserveType == DAILY && fromDatePicker.getValue() != null && toDatePicker.getValue() != null) {
            date_in = fromDatePicker.getValue();
            date_out = toDatePicker.getValue();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ไม่สามารถค้นหาได้");
            alert.setHeaderText("โปรดกรอกข้อมูลให้ครบก่อนกดค้นหา");
            alert.showAndWait();
            return;
        }

        if (date_in != null && date_out != null) {

            if (reserveType == DAILY && (date_out.isBefore(date_in) || date_out.isEqual(date_in))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ไม่สามารถค้นหาได้");
                alert.setHeaderText("โปรดกรอกข้อมูลให้ถูกต้องก่อนกดค้นหา");
                alert.showAndWait();
                return;
            }

            String type_name = roomTypeBox.getValue().getTypeRoom();
            String floor = floorBox.getValue() + "";

            if (type_name.equals("ทุกประเภท") && floor.equals("ทุกชั้น")) {
                setResults(instance.selectAllRoom(), instance.selectIDRoomThatReservationNotInRange(date_in, date_out));
            } else if (type_name.equals("ทุกประเภท") && !floor.equals("ทุกชั้น")) {
                setResults(instance.selectAllRoomWithFloor(floorBox.getValue()), instance.selectIDRoomThatReservationNotInRangeFilterByFloor(date_in, date_out, floorBox.getValue()));
            } else if (!type_name.equals("ทุกประเภท") && floor.equals("ทุกชั้น")) {
                setResults(instance.selectAllRoomWithType(roomTypeBox.getValue().getIdTypeRoom()), instance.selectIDRoomThatReservationNotInRangeFilterByIdTypeRoom(date_in, date_out, roomTypeBox.getValue().getIdTypeRoom()));
            } else {
                setResults(instance.selectAllRoomWithTypeAndFloor(roomTypeBox.getValue().getIdTypeRoom(), floorBox.getValue()), instance.selectIDRoomThatReservationNotInRangeFilterByIdTypeRoomAndFloor(date_in, date_out, roomTypeBox.getValue().getIdTypeRoom(), floorBox.getValue()));
            }

            update();

        }

    }

    private void setResults(ArrayList<Room> list, SortedSet<Integer> roomIDs) {
        rooms.clear();

        if (roomIDs.size() > 0) {

            int index = 0;
            int id = roomIDs.first();
            Iterator<Integer> iterator = roomIDs.iterator();

            while (iterator.hasNext()) {
                if (id < list.get(index).getId_room())
                    id = iterator.next();
                else if (id == list.get(index).getId_room()) {
                    list.remove(index);
                    id = iterator.next();
                } else if (id > list.get(index).getId_room()) {
                    index++;
                }
            }

        }

        for (Room room : list) {
            rooms.add(new RoomRecord(room));
        }

    }

}
