package model;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class RoomManagementDetail {
    String date_star, date_end,type,user,phone;
    Button cancel;
    String fxml;
    Reservation reservation;

    public RoomManagementDetail(String date_star, String date_end, String type, String user, String phone, Button cancel, String fxml,Reservation reservation) {
        this.date_star = date_star;
        this.date_end = date_end;
        this.type = type;
        this.user = user;
        this.phone = phone;
        this.cancel = cancel;
        this.fxml = fxml;
        this.reservation = reservation;



       cancel.setOnAction(e -> {
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("คอนเฟิร์ม การยกเบิกการจอง");
           alert.setHeaderText("คอนเฟิร์ม การยกเลิก");
           alert.setContentText("คุณแน่ใจที่จะยกเลิกการจองของ ผู้พัก: "+getUser()+" ?");
           Optional<ButtonType> action = alert.showAndWait();

           if (action.get() == ButtonType.OK){

               DBConnector.getDBConnector().deleteReservationById(this.reservation.getId_reservation());

               Button b = (Button) e.getSource();

               Stage stage = (Stage) b.getScene().getWindow();

               FXMLLoader loader = new FXMLLoader(getClass().getResource(this.fxml));
               try {
                   stage.setScene(new Scene((Parent) loader.load(), 1280, 800));

                   stage.show();

               } catch (IOException e1) {
                   e1.printStackTrace();
               }
           }




        });
    }

    public String getDate_star() {
        return date_star;
    }

    public void setDate_star(String date_star) {
        this.date_star = date_star;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Button getCancel() {
        return cancel;
    }

    public void setCancel(Button cancel) {
        this.cancel = cancel;
    }

    public String getFxml() {
        return fxml;
    }

    public void setFxml(String fxml) {
        this.fxml = fxml;
    }
}
