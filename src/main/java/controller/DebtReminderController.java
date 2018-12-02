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

    @FXML
    public void initialize() throws IOException {
        System.out.println("initialize");
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
        System.out.println("Load data!");
        ObservableList<DebtReminder> data_table = FXCollections.observableArrayList();
        for(int i=0 ; i<5 ; i++){
            data_table.add(new DebtReminder("date"+i, "roomName"+i,"customerName"+i,
                    "phoneNumber"+i, "roomType"+i, i+1+".00", new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
        }
//        data_table.add(new DebtReminder("15/10/2018","Hans", "Muster", "084-253-9753" , "รายเดือน", "3300.00", new Button(" ชำระเงินแล้ว", new ImageView(imageOk))));
//        data_table.add(new DebtReminder("15/10/2018","Ruth", "Mueller", "089-432-2287", "รายเดือน", "4500.00",new Button(" ชำระเงินแล้ว", new ImageView(imageOk))));
//        data_table.add(new DebtReminder("15/10/2018","Heinz", "Kurz", "090-432-2218","รายเดือน", "3300.00", new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
//        data_table.add(new DebtReminder("15/10/2018","Cornelia", "Meier", "088-404-4444", "รายเดือน", "850.00",new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
//        data_table.add(new DebtReminder("15/10/2018","Werner", "Meyer", "088-880-8787", "รายเดือน", "1250.00",new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
//        data_table.add(new DebtReminder("15/10/2018","Lydia", "Kunz", "086-963-3636", "รายเดือน", "1900.00",new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
//        data_table.add(new DebtReminder("15/10/2018","Anna", "Best", "091-103-3102", "รายเดือน", "5000.00",new Button(" ชำระเงินแล้ว", new ImageView(imageOk))));
//        data_table.add(new DebtReminder("15/10/2018","Stefan", "Meier", "090-567-8910", "รายเดือน", "1750.00",new Button(" มียอดค้างชำระ", new ImageView(imageNo))));
//        data_table.add(new DebtReminder("15/10/2018","Martin", "Mueller", "087-723-1090", "รายเดือน", "3500.00",new Button(" มียอดค้างชำระ", new ImageView(imageNo))));

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