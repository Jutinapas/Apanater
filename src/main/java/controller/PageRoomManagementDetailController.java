package controller;

import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class PageRoomManagementDetailController {
    @FXML
    private GridPane gridPane;

    @FXML
    private TextField textF_name;

    @FXML
    private ComboBox<String> cb_type;

    @FXML
    private Spinner<Integer> spinner_floor;

//label
    @FXML
    private Label label_nameroom;

    @FXML
    private Label label_typeroom;

    @FXML
    private Label label_floor;

//btn
    @FXML
    private Button btnX;

    @FXML
    private Button btnY;

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
    private Button btnEdit;

    @FXML
    private Button btnIC;

    @FXML
    private TableView<RoomManagementDetail> table_detail;

    @FXML
    private TableColumn<RoomManagementDetail, String> col_dateS;

    @FXML
    private TableColumn<RoomManagementDetail, String> col_dateE;

    @FXML
    private TableColumn<RoomManagementDetail, String> col_tybe;

    @FXML
    private TableColumn<RoomManagementDetail, String> col_user;

    @FXML
    private TableColumn<RoomManagementDetail, String> col_phone;

    @FXML
    private TableColumn<RoomManagementDetail, Button> col_cancel;




     @FXML
    public  void initialize() throws IOException {

        setVisible();
        setSpinner_floor(1,Integer.MAX_VALUE);
        initTable();
        setStyleCols();
        loadData();
    }


    @FXML
    public void setData(String textF, String textT, int s) throws IOException {
        label_nameroom.setText(textF);
        label_typeroom.setText(textT);
        label_floor.setText(""+s);
        loadData();


    }

    @FXML
    public void setVisible(){
        btnX.setVisible(false);
        btnY.setVisible(false);
        textF_name.setVisible(false);
        cb_type.setVisible(false);
        spinner_floor.setVisible(false);

        ArrayList<TypeRoom> typeRooms = SqlConnection.getSqlConnection().selectAllTypeRoom();
        ArrayList<String> lst = new ArrayList<>();
        for(TypeRoom t:typeRooms){
            lst.add(t.getTypeRoom());
        }
        ObservableList<String> data = FXCollections.observableArrayList(lst);
        cb_type.setItems(data);
    }

    @FXML
    public void setEditDetail(String textF,String textT,int s){
        textF_name.setText(textF);
        cb_type.setValue(textT);
        spinner_floor.getValueFactory().setValue(s);


    }
    @FXML
    void setSpinner_floor(int min,int max){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, min);
        spinner_floor.setValueFactory(valueFactory);
        spinner_floor.setEditable(true);
        TextFormatter formatter = new TextFormatter(valueFactory.getConverter(), valueFactory.getValue());
        spinner_floor.getEditor().setTextFormatter(formatter);
        valueFactory.valueProperty().bindBidirectional(formatter.valueProperty());
    }




    private void initTable(){
        initCols();
    }
    private void initCols(){
        //date_star, date_end,type,user,phone

        col_dateS.setCellValueFactory(new PropertyValueFactory<RoomManagementDetail, String>("date_star"));
        col_dateE.setCellValueFactory(new PropertyValueFactory<RoomManagementDetail, String>("date_end"));
        col_tybe.setCellValueFactory(new PropertyValueFactory<RoomManagementDetail, String>("type"));
        col_user.setCellValueFactory(new PropertyValueFactory<RoomManagementDetail, String>("user"));
        col_phone.setCellValueFactory(new PropertyValueFactory<RoomManagementDetail, String>("phone"));
        col_cancel.setCellValueFactory(new PropertyValueFactory<RoomManagementDetail, Button>("cancel"));
        editTableCols();
    }
    private void setStyleCols(){
        //CENTER-RIGHT,CENTER-LEFT,CENTER
        table_detail.setEditable(false);
        col_dateS.setStyle("-fx-alignment: CENTER");
        col_dateE.setStyle("-fx-alignment: CENTER");
        col_tybe.setStyle("-fx-alignment: CENTER");
        col_user.setStyle("-fx-alignment: CENTER");
        col_phone.setStyle("-fx-alignment: CENTER");
        col_cancel.setStyle("-fx-alignment: CENTER");
    }
    private void editTableCols(){
        col_dateS.setCellFactory(TextFieldTableCell.<RoomManagementDetail>forTableColumn());

        col_dateS.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDate_star(e.getNewValue());
        });



        col_dateE.setCellFactory(TextFieldTableCell.<RoomManagementDetail>forTableColumn());

        col_dateE.setOnEditCommit( e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDate_end(e.getNewValue());
        });

        col_tybe.setCellFactory(TextFieldTableCell.<RoomManagementDetail>forTableColumn());

        col_tybe.setOnEditCommit( e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setType(e.getNewValue());
        });

        col_user.setCellFactory(TextFieldTableCell.<RoomManagementDetail>forTableColumn());

        col_user.setOnEditCommit( e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUser(e.getNewValue());
        });

        col_phone.setCellFactory(TextFieldTableCell.<RoomManagementDetail>forTableColumn());

        col_phone.setOnEditCommit( e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhone(e.getNewValue());
        });


        table_detail.setEditable(true);

    }

    private void loadData() throws IOException {
        ObservableList<RoomManagementDetail> data_table = FXCollections.observableArrayList();
        int id = SqlConnection.getSqlConnection().getIDroomByNameRoom(label_nameroom.getText());
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations = SqlConnection.getSqlConnection().selectReservationWithRoom(id);
        String fxml = "/fxml/PageRoomManagementMain.fxml" ;
        for(int i=0 ; i<reservations.size() ; i++){
            data_table.add(new RoomManagementDetail(reservations.get(i).getDate_check_in()+"",reservations.get(i).getDate_check_out()+"",reservations.get(i).getType_reserve(),reservations.get(i).getName_guest()+"",reservations.get(i).getPhone_number()+"",new Button("ยกเลิกการจอง"),fxml,reservations.get(i)));
        }

        table_detail.setItems(data_table);
    }

    @FXML
    void BtnDelete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("คอนเฟิร์ม การลบห้อง");
        alert.setHeaderText("คอนเฟิร์ม การลบ");
        alert.setContentText("คุณแน่ใจที่จะลบ ห้อง: "+label_nameroom.getText()+" ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
//            System.out.println("delete");
            int s = SqlConnection.getSqlConnection().getIDroomByNameRoom(label_nameroom.getText());
            SqlConnection.getSqlConnection().deleteRoom(s);
            GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
            gridPane.getChildren().setAll(pane);

        }

    }

    @FXML
    void BtnEdit(ActionEvent event) {
        btnX.setVisible(true);
        btnY.setVisible(true);
        textF_name.setVisible(true);
        cb_type.setVisible(true);
        spinner_floor.setVisible(true);
        btnEdit.setVisible(false);
        setEditDetail(label_nameroom.getText(),label_typeroom.getText(),Integer.parseInt(label_floor.getText()));
    }

    @FXML
    void BtnX(ActionEvent event) {
        btnX.setVisible(false);
        btnY.setVisible(false);
        btnEdit.setVisible(true);
        textF_name.setVisible(false);
        cb_type.setVisible(false);
        spinner_floor.setVisible(false);
    }

    @FXML
    void BtnY(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("คอนเฟิร์ม การแก้ไขห้อง");
        alert.setHeaderText("คอนเฟิร์ม การแก้ไข");
        alert.setContentText("คุณแน่ใจที่จะแก้ไข ห้อง: "+label_nameroom.getText()+" ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            System.out.println("edit");
            int s = SqlConnection.getSqlConnection().getIDroomByNameRoom(label_nameroom.getText());
            int t = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(cb_type.getValue());
//            System.out.println(cb_type.getValue());
//            System.out.println(t);
            SqlConnection.getSqlConnection().updateRoom(s,textF_name.getText(),spinner_floor.getValue(),t);
            GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
            gridPane.getChildren().setAll(pane);

        }
    }

    @FXML
    void BtnLeftArrow(ActionEvent event) throws IOException {
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
