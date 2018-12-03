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

import java.text.DecimalFormat;
import java.time.LocalDate;
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

        ArrayList<Reservation> reservation = SqlConnection.getSqlConnection().selectAllReservation();
        ArrayList<Debt> debtsForCheck = SqlConnection.getSqlConnection().selectAllFromDebt();

        //ส่วนเพิ่มข้อมูลลง Table Debt
        for (int i = 0; i < reservation.size() ; i++) {

            int idReservation = reservation.get(i).getId_reservation();

            //เช็คว่าใน Table Debt มี id_reserve นี้แล้วหรือยัง เพื่อที่เวลา Load Data จะไม่เขียนข้อมูลเดิมลง Database
            if (SqlConnection.getSqlConnection().getIDDebtFromIDReserve(idReservation) != 0){
                continue;
            }

            //วันที่ check in จาก Table Reservation
            String[] dateMonthYearCheckIn = reservation.get(i).getDate_check_in().split("/");
            String dateCheckIn = dateMonthYearCheckIn[0];
            String monthCheckIn = dateMonthYearCheckIn[1];
            String yearCheckIn = dateMonthYearCheckIn[2];
            if (dateCheckIn.charAt(0) == '0'){
                dateCheckIn = dateCheckIn.charAt(1)+"";
            }
            if (monthCheckIn.charAt(0) == '0'){
                monthCheckIn = monthCheckIn.charAt(1)+"";
            }

            //วันที่ check out จาก Table Reservation
            String[] dateMonthYearCheckOut = reservation.get(i).getDate_check_out().split("/");
            String dateCheckOut = dateMonthYearCheckOut[0];
            String monthCheckOut = dateMonthYearCheckOut[1];
            String yearCheckOut = dateMonthYearCheckOut[2];
            if (dateCheckOut.charAt(0) == '0'){
                dateCheckOut = dateCheckOut.charAt(1)+"";
            }
            if (monthCheckOut.charAt(0) == '0'){
                monthCheckOut = monthCheckOut.charAt(1)+"";
            }

            //จำนวนเดือนที่เข้าพัก ใช้ method countAmountOfMonth
            amountMonth = countAmountOfMonth(yearCheckIn, yearCheckOut, monthCheckIn, monthCheckOut);

            //วันที่ชำระประจำเดือนของหอพัก
            String datePayMoney =SqlConnection.getSqlConnection().selectDatePayMoney();

            //เอาไว้เรียก rent_per_month จาก Table TypeRoom
            int idRoom = reservation.get(i).getId_room();
            Room rooms = SqlConnection.getSqlConnection().getRoomByID(idRoom);

            int idTypeRoom = rooms.getId_type_room();
            TypeRoom typeRoom = SqlConnection.getSqlConnection().getTypeRoomByID(idTypeRoom);

            if (reservation.get(i).getType_reserve().equals("รายวัน")){
                if (Integer.parseInt(yearCheckIn) == Integer.parseInt(yearCheckOut) && (Integer.parseInt(monthCheckIn) == Integer.parseInt(monthCheckOut))) {
                    double rent = (Integer.parseInt(dateCheckOut)-Integer.parseInt(dateCheckIn)) * typeRoom.getRentPerDay();
                    SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), reservation.get(i).getDate_check_in(),  rent);
                }
                if (Integer.parseInt(yearCheckIn) == Integer.parseInt(yearCheckOut) && (Integer.parseInt(monthCheckIn)!= Integer.parseInt(monthCheckOut))){
                    double rent = ((30-(Integer.parseInt(dateCheckIn)-1)) + Integer.parseInt(dateCheckOut)) * typeRoom.getRentPerDay();
                    SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), reservation.get(i).getDate_check_in(),  rent);
                }
                if (Integer.parseInt(yearCheckOut) - Integer.parseInt(yearCheckIn) == 1){
                    double rent = ((30-(Integer.parseInt(dateCheckIn)-1)) + Integer.parseInt(dateCheckOut)) * typeRoom.getRentPerDay();
                    SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), reservation.get(i).getDate_check_in(),  rent);
                }
                continue;
            }

            //เดือนถัดไปจากเดือนที่เข้าพัก กับ ปีที่เข้าพัก
            int month = Integer.parseInt(monthCheckIn)+1;
            int year = Integer.parseInt(yearCheckIn);

            //ลูปที่ใช้ insert data สู่ Table Debt โดยมีจำนวน record เท่่ากับ amountMonth(จำนวเดือนที่เข้าพัก)
            for (int j = 0; j < amountMonth ; j++) {
                double rentPerMonth = typeRoom.getRentPerMonth();

                //แค่เช็คว่าถ้าเดือนมากกว่า 12 ก็คือขึ้น เดือน1 ของปีถัดไป
                if (month > 12){
                    month = 1;
                    year ++;
                }

                //วันที่ชำระจากหน้าจัดการหอพัก
                LocalDate dueDate = LocalDate.of(year,month, Integer.parseInt(datePayMoney));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formatDateTime = dueDate.format(formatter);

                month ++;

                //เดือนแรกที่เข้าพัก
                if (j==0){
                    int numberDate = (30 - Integer.parseInt(dateCheckIn))+1;
                    if (amountMonth == 1 || numberDate == 30){
                        SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), formatDateTime,  rentPerMonth);
                    }else{
                        double rent = (rentPerMonth/30);
                        rentPerMonth = Math.rint(rent*numberDate);
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        rentPerMonth = Double.parseDouble(df.format(rentPerMonth));
                        SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), formatDateTime,  rentPerMonth);
                    }
                    //เดือนสุดท้ายที่เข้าพัก
                }else if(j == amountMonth-1){
                    double rent = (rentPerMonth/30);
                    if (Integer.parseInt(dateCheckOut) != 1){
                        rentPerMonth += Math.rint(rent*(Integer.parseInt(dateCheckOut)-1));
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        rentPerMonth = Double.parseDouble(df.format(rentPerMonth));
                        SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), formatDateTime,  rentPerMonth);
                    }else{
                        SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), formatDateTime,  rentPerMonth);
                    }
                }else{
                    //เดือนที่่ไม่ใช่เดือนแรกและเดือนสุดท้ายที่เข้าพัก
                    SqlConnection.getSqlConnection().insertDebt(reservation.get(i).getId_reservation(), formatDateTime,  rentPerMonth);
                }
            }

        }

        ArrayList<Debt> debts = SqlConnection.getSqlConnection().selectAllFromDebt();

        for(int i=0 ; i< debts.size() ; i++){
//            System.out.println("record "+(i+1)+" status = "+debts.get(i).getStatus());

            int idReserve = debts.get(i).getId_reserve();
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
            String[] dateMonthYearPayDebt = debts.get(i).getDate_pay_debt().split("/");
            String datePayDebt = dateMonthYearPayDebt[0];
            String monthPayDebt = dateMonthYearPayDebt[1];
            String yearPayDebt = dateMonthYearPayDebt[2];
            if (datePayDebt.charAt(0) == '0'){
                datePayDebt = datePayDebt.charAt(1)+"";
            }
            if (monthPayDebt.charAt(0) == '0'){
                monthPayDebt = monthPayDebt.charAt(1)+"";
            }

            //กรณีปีมากกว่า
            boolean yearMoreThan = (Integer.parseInt(yearCurrent)+543) > Integer.parseInt(yearPayDebt);
            //กรณีปีเท่ากัน เดือนมากกว่า
            boolean yearEqualMonthMoreThan =  ((Integer.parseInt(yearCurrent)+543) == Integer.parseInt(yearPayDebt))
                    && (Integer.parseInt(monthCurrent) > (Integer.parseInt(monthPayDebt)));
            //กรณีปีเท่ากัน เดือนเท่ากัน วันที่มากกว่าเหรือท่ากับ
            boolean yearEqualMonthEqualDayMoreThanEqual = (Integer.parseInt(yearCurrent)+543) == Integer.parseInt(yearPayDebt) &&
                    Integer.parseInt(monthCurrent) == Integer.parseInt(monthPayDebt)
                    && Integer.parseInt(dateCurrent) >= Integer.parseInt(datePayDebt);

            //เช็คจาก3กรณีอันใดอันหนึ่งเป็นจริง ข้อมูลนั้นจะได้ add เข้า tableview
            if (yearMoreThan || yearEqualMonthMoreThan || yearEqualMonthEqualDayMoreThanEqual){
                if (debts.get(i).getStatus().equals("active")) {
                    //จะเป็นปุ่มชำระแล้ว(สีเขยีว) ถ้า status เป็น active
                    data_table.add(new DebtReminder(debts.get(i).getDate_pay_debt(), rooms.getRoom_name(), reservations.getName_guest(), reservations.getPhone_number()
                            , typeRoom.getTypeRoom(), debts.get(i).getDebt_balance() + "", new Button(" ชำระเงินแล้ว", new ImageView(imageOk))));

                }else{
                    //จะเป็นปุ่มมียอดค้างชำระ(สีแดง) ถ้า status เป็น unactive
                    data_table.add(new DebtReminder(debts.get(i).getDate_pay_debt(), rooms.getRoom_name(), reservations.getName_guest(), reservations.getPhone_number()
                            , typeRoom.getTypeRoom(), debts.get(i).getDebt_balance() + "", new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
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

    //หาจำนวนเดือนที่เข้าพัก
    private int countAmountOfMonth(String yearCheckIn, String yearCheckOut, String monthCheckIn, String monthCheckOut){
        if(Integer.parseInt(yearCheckIn) == Integer.parseInt(yearCheckOut)){
            amountMonth = Integer.parseInt(monthCheckOut) - Integer.parseInt(monthCheckIn);
        }
        if(Integer.parseInt(yearCheckOut) - Integer.parseInt(yearCheckIn) == 1){
            amountMonth = (12-(Integer.parseInt(monthCheckIn))) + Integer.parseInt(monthCheckOut);
        }
        if(Integer.parseInt(yearCheckOut) - Integer.parseInt(yearCheckIn) > 1){
            amountMonth = (12-(Integer.parseInt(monthCheckIn)) +
                    ((Integer.parseInt(yearCheckOut)-((Integer.parseInt(yearCheckIn)+1)))*12)+Integer.parseInt(monthCheckOut));
        }
        return amountMonth;
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