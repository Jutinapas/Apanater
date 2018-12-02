package model;

import controller.DebtReminderController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class DebtReminder {
    private String dueDate, roomName, customerName, phoneNumber, roomType, debt;
    private Button status;

    public DebtReminder(String dueDate, String roomName, String customerName, String phoneNumber, String roomType, String debt, Button status) {
        this.dueDate = dueDate;
        this.roomName = roomName;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.roomType = roomType;
        this.debt = debt;
        this.status = status;

        status.setOnAction(event -> {

            String[] s = (this.status.toString().split(" "));
            if (s[1].equals("มียอดค้างชำระ'")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("ยืนยันการชำระเงิน");
                alert.setHeaderText("ลูกค้าท่านนี้ได้ชำระเงินค่าหอพักแล้วใช่หรือไม่");
                alert.setContentText("ชื่อห้อง : "+this.roomName+ "\nชื่อลูกค้า : "+this.customerName
                        + "\nประเภท : "+this.roomType + "\nจำนวนเงิน : " + this.debt +" บาท"
                        + "\nวันที่ครบกำหนดชำระ : "+ this.dueDate);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // จ่ายแล้วยอดค้างชำระเป็น 0 บาท
                    // this.setDebt("0.00");
                    status.setText(" ชำระเงินแล้ว");
                    status.setGraphic(new ImageView("/images/yes.png"));
                    DebtReminderController.noData.remove(this);
                    DebtReminderController.yesData.add(this);

                } else {
                    // ... user chose CANCEL or closed the dialog}
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ยืนยันการชำระเงิน");
                alert.setHeaderText("ลูกค้าท่านนีได้ชำระค่าเช่างวดนี้แล้ว");
                alert.setContentText("");

                alert.showAndWait();
            }
        });
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

    public Button getStatus() {
        return status;
    }

    public void setStatus(Button status) {
        this.status = status;
    }

}
