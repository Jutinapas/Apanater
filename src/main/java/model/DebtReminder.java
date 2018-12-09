package model;

import controller.DebtReminderController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class DebtReminder {
    private String dueDate, roomName, customerName, phoneNumber, roomType, debt, status;
    private int id;
    private Button button;
    private ImageView view;

    public DebtReminder(int id, String dueDate, String roomName, String customerName, String phoneNumber, String roomType, String debt, String status,Button button, ImageView view) {
        this.id = id;
        this.dueDate = dueDate;
        this.roomName = roomName;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.roomType = roomType;
        this.debt = debt;
        this.status = status;
        this.button = button;
        this.view = view;

        button.setOnAction(event -> {

            if (status.equals("active")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("คุณต้องการจะชำระเงินของห้อง " + roomName + " ใช่หรือไม่ ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                    //update status ใน Table Debt
                    DBConnector.getDBConnector().updateStatusInDebt(this.id);

                    this.button = new Button(" ชำระเงินแล้ว", view);
                    DebtReminderController.noData.remove(this);
                    DebtReminderController.yesData.add(this);

                } else {
                    // ... user chose CANCEL or closed the dialog}
                }
            }

        });
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getStatus() {
        return status;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

}
