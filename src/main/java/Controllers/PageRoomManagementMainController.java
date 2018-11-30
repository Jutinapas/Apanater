package Controllers;


import Models.Room;
import Models.RoomManagement;
import Models.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

public class PageRoomManagementMainController {
//    PageRoomManagement


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
    private TableView<RoomManagement> table_info;

    @FXML
    private TableColumn<RoomManagement, String> col_room;

    @FXML
    private TableColumn<RoomManagement, String> col_type;

    @FXML
    private TableColumn<RoomManagement, String> col_floor;

    @FXML
    private TableColumn<RoomManagement, Button> col_detail;




    @FXML
    public  void initialize() throws IOException {
        initTable();
        setStyleCols();
        loadData();
    }





    private void initTable(){
        initCols();
    }
    private void initCols(){
//        name,type,floor;

        col_room.setCellValueFactory(new PropertyValueFactory<RoomManagement, String>("name"));
        col_type.setCellValueFactory(new PropertyValueFactory<RoomManagement, String>("type"));
        col_floor.setCellValueFactory(new PropertyValueFactory<RoomManagement, String>("floor"));
        col_detail.setCellValueFactory(new PropertyValueFactory<RoomManagement, Button>("detail"));
        editTableCols();
    }
    private void setStyleCols(){
        //CENTER-RIGHT,CENTER-LEFT,CENTER
        table_info.setEditable(false);
        col_room.setStyle("-fx-alignment: CENTER");
        col_type.setStyle("-fx-alignment: CENTER");
        col_floor.setStyle("-fx-alignment: CENTER");
        col_detail.setStyle("-fx-alignment: CENTER");
    }
    private void editTableCols(){
        col_room.setCellFactory(TextFieldTableCell.<RoomManagement>forTableColumn());

        col_room.setOnEditCommit(e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });



        col_type.setCellFactory(TextFieldTableCell.<RoomManagement>forTableColumn());

        col_type.setOnEditCommit( e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setType(e.getNewValue());
        });

        col_floor.setCellFactory(TextFieldTableCell.<RoomManagement>forTableColumn());

        col_floor.setOnEditCommit( e-> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setFloor(e.getNewValue());
        });


        table_info.setEditable(true);

    }

    private void loadData() throws IOException {
        ObservableList<RoomManagement> data_table = FXCollections.observableArrayList();
        String fxml = "/fxml/PageRoomManagementDetail.fxml" ;
        ArrayList<Room> rooms = SqlConnection.getSqlConnection().selectAllRoom();
        for(int i=0 ; i<rooms.size() ; i++){
            data_table.add(new RoomManagement(rooms.get(i).getRoom_name(), SqlConnection.getSqlConnection().getStringTypeRoomFromIDRoom(rooms.get(i).getId_type_room())+"",rooms.get(i).getFloor()+"",new Button("รายละเอียด"),fxml,rooms.get(i)));

        }

        table_info.setItems(data_table);
    }



    @FXML
    void BtnAddNewRoom(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementAddNewRoom.fxml"));
        gridPane.getChildren().setAll(pane);
    }


    @FXML
    void BtnTypeAll(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementTypeAll.fxml"));
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
