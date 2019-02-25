package controller;

import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DebtReminderController{

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
    private TableView<DebtReminder> tableDebtReminder;

    @FXML
    private TableColumn<DebtReminder, String> dueDateCol;

    @FXML
    private TableColumn<DebtReminder, String> roomNameCol;

    @FXML
    private TableColumn<DebtReminder, String> customerNameCol;

    @FXML
    private TableColumn<DebtReminder, String> phoneNumberCol;

    @FXML
    private TableColumn<DebtReminder, String> roomTypeCol;

    @FXML
    private TableColumn<DebtReminder, String> debtCol;

    @FXML
    private TableColumn<DebtReminder, Button> buttonCol;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TextField filterField;

    @FXML
    private Button clearButton;

    @FXML
    Image imageOk = new Image(getClass().getResourceAsStream("../images/yes.png"));

    @FXML
    Image imageNo = new Image(getClass().getResourceAsStream("../images/no.png"));

    public static ObservableList<DebtReminder> noData = FXCollections.observableArrayList();
    public static ObservableList<DebtReminder> yesData = FXCollections.observableArrayList();
    int amountMonth;

    @FXML
    public void initialize() throws IOException {
        noData.clear();
        yesData.clear();
        initTable();
        setStyleCols();
        loadData();
    }

    private void initTable(){
        initCols();
    }

    private void initCols(){
//        date, roomName,customerName, phoneNumber, roomType, debt, status;

        dueDateCol.setCellValueFactory(new PropertyValueFactory<DebtReminder, String>("dueDate"));
        roomNameCol.setCellValueFactory(new PropertyValueFactory<DebtReminder, String>("roomName"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<DebtReminder, String>("customerName"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<DebtReminder, String>("phoneNumber"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<DebtReminder, String>("roomType"));
        debtCol.setCellValueFactory(new PropertyValueFactory<DebtReminder, String>("debt"));
        buttonCol.setCellValueFactory(new PropertyValueFactory<DebtReminder, Button>("status"));

    }

    private void loadData() throws IOException {
        ObservableList<DebtReminder> data_table = FXCollections.observableArrayList();

        ArrayList<Debt> debt = SqlConnection.getSqlConnection().selectAllFromDebt();

        for(int i=0 ; i< debt.size() ; i++){

            int idReserve = debt.get(i).getId_reserve();
            Reservation reservations = SqlConnection.getSqlConnection().getReservationByID(idReserve);

            int idRoom = reservations.getId_room();
            Room rooms = SqlConnection.getSqlConnection().getRoomByID(idRoom);

            int idTypeRoom = rooms.getId_type_room();
            TypeRoom typeRoom = SqlConnection.getSqlConnection().getTypeRoomByID(idTypeRoom);

            //เช็ควันเดือนปี ณ ปัจจุบัน
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formatDateTime = now.format(formatter);
            String[] dateMonthYearCurrent = formatDateTime.split("/");
            String dateCurrent = dateMonthYearCurrent[0];
            String monthCurrent = dateMonthYearCurrent[1];
            String yearCurrent = dateMonthYearCurrent[2];

            if (dateCurrent.charAt(0) == '0'){
                dateCurrent = dateCurrent.charAt(1)+"";
            }
            if (monthCurrent.charAt(0) == '0'){
                monthCurrent = monthCurrent.charAt(1)+"";
            }

            //เช็ควันเดือนปีประจำค่างวดนั้นๆ
            String[] dateMonthYearPayDebt = debt.get(i).getDate_pay_debt().split("-");
            String datePayDebt = dateMonthYearPayDebt[2];
            String monthPayDebt = dateMonthYearPayDebt[1];
            String yearPayDebt = dateMonthYearPayDebt[0];
            if (datePayDebt.charAt(0) == '0'){
                datePayDebt = datePayDebt.charAt(1)+"";
            }
            if (monthPayDebt.charAt(0) == '0'){
                monthPayDebt = monthPayDebt.charAt(1)+"";
            }

            //กรณีปีมากกว่า
            boolean yearMoreThan = (Integer.parseInt(yearCurrent)) > Integer.parseInt(yearPayDebt);

            //กรณีปีเท่ากัน เดือนมากกว่า
            boolean yearEqualMonthMoreThan =  ((Integer.parseInt(yearCurrent)) == Integer.parseInt(yearPayDebt))
                    && (Integer.parseInt(monthCurrent) > (Integer.parseInt(monthPayDebt)));

            //กรณีปีเท่ากัน เดือนเท่ากัน วันที่มากกว่าเหรือท่ากับ
            boolean yearEqualMonthEqualDayMoreThanEqual = (Integer.parseInt(yearCurrent)) == Integer.parseInt(yearPayDebt) &&
                    Integer.parseInt(monthCurrent) == Integer.parseInt(monthPayDebt)
                    && Integer.parseInt(dateCurrent) >= Integer.parseInt(datePayDebt);

            //เช็คจาก3กรณีอันใดอันหนึ่งเป็นจริง ข้อมูลนั้นจะได้ add เข้า tableview
            if (yearMoreThan || yearEqualMonthMoreThan || yearEqualMonthEqualDayMoreThanEqual){
                if (debt.get(i).getStatus().equals("active")) {
                    //จะเป็นปุ่มชำระแล้ว(สีเขยีว) ถ้า status เป็น active
                    data_table.add(new DebtReminder(debt.get(i).getId_debt(),debt.get(i).getDate_pay_debt(), rooms.getRoom_name(), reservations.getName_guest(), reservations.getPhone_number()
                            , typeRoom.getTypeRoom(), debt.get(i).getDebt_balance() + "", new Button(" ชำระเงินแล้ว", new ImageView(imageOk))));

                }else{
                    //จะเป็นปุ่มมียอดค้างชำระ(สีแดง) ถ้า status เป็น unactive
                    data_table.add(new DebtReminder(debt.get(i).getId_debt(),debt.get(i).getDate_pay_debt(), rooms.getRoom_name(), reservations.getName_guest(), reservations.getPhone_number()
                            , typeRoom.getTypeRoom(), debt.get(i).getDebt_balance() + "", new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
                }
            }
        }

        //ไว้แยกข้อมูลที่ชำระแล้วกับยังไม่ได้ชำระ
        for (int i = 0; i < data_table.size() ; i++) {
            String[] s = (data_table.get(i).getStatus().toString().split(" "));
            if (s[1].equals("มียอดค้างชำระ'")){
                noData.add(data_table.get(i));

            }else{
                yesData.add(data_table.get(i));
            }
        }
        filter();
    }

    private void setStyleCols(){
        //CENTER-RIGHT,CENTER-LEFT,CENTER
        tableDebtReminder.setEditable(false);
        dueDateCol.setStyle("-fx-alignment: CENTER");
        roomNameCol.setStyle("-fx-alignment: CENTER");
        customerNameCol.setStyle("-fx-alignment: CENTER");
        phoneNumberCol.setStyle("-fx-alignment: CENTER");
        roomTypeCol.setStyle("-fx-alignment: CENTER");
        debtCol.setStyle("-fx-alignment: CENTER");
        buttonCol.setStyle("-fx-alignment: CENTER");
    }

    private void filter() {
        ObservableList<DebtReminder> dataSort;
        if (checkBox.isSelected() == false) {
            dataSort = noData;
        } else {
            dataSort = yesData;
        }

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<DebtReminder> filteredData = new FilteredList<>(dataSort, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(debtReminder -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (debtReminder.getDueDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches date.
                } else if (debtReminder.getRoomName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches room name.
                } else if (debtReminder.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches customer name.
                } else if (debtReminder.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches phone number.
                } else if (debtReminder.getRoomType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches room type.
                } else if (debtReminder.getDebt().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches debt.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<DebtReminder> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableDebtReminder.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableDebtReminder.setItems(sortedData);

    }

    @FXML
    void check(ActionEvent event) {
        if (checkBox.isSelected()== true){
            debtCol.setText("จำนวนเงินที่ชำระแล้ว");
            filterField.setText("");
            tableDebtReminder.setItems(yesData);
            filter();

        }else{
            debtCol.setText("ยอดค้างชำระ");
            filterField.setText("");
            tableDebtReminder.setItems(noData);
            filter();
        }
    }

    @FXML
    void clear(ActionEvent event) {
        filterField.setText("");
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