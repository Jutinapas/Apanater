package Controllers;


import Models.SqlConnection;
import Models.TypeRoom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PageRoomManagementEditTypeRoomController {

    private TypeRoom tr;

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
    private Button btnIC;

    @FXML
    private Button btnC;

    @FXML
    private TextField tf;

    @FXML
    private Spinner<Double> spinnerMonth;

    @FXML
    private Spinner<Double> spinnerDay;

    @FXML
    private Button leftArrow;

    String datasave ;
    Double msave,dsave;


    @FXML
    public  void initialize() {
        setSpinnerMoth(1,100000);
        setSpinnerDay(1,100000);
//        setData("sex room",3500,300);

    }

    @FXML
    void setData(String textF,Double m,Double d,TypeRoom typeRoom){
        tf.setText(textF);
        spinnerMonth.getValueFactory().setValue(m);
        spinnerDay.getValueFactory().setValue(d);

        tr = typeRoom;

        datasave = textF;
        msave = m;
        dsave = d;
    }


    @FXML
    void setSpinnerMoth(double min,double max){
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, min);
        spinnerMonth.setValueFactory(valueFactory);
        spinnerMonth.setEditable(true);
    }

    @FXML
    void setSpinnerDay(double min,double max){
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, min);
        spinnerDay.setValueFactory(valueFactory);
        spinnerDay.setEditable(true);
    }

    @FXML
    void  clear(){
        tf.setText("");
        spinnerDay.getValueFactory().setValue(1.0);
        spinnerMonth.getValueFactory().setValue(1.0);
    }

    @FXML
    void BtnCorrect(ActionEvent event) throws IOException {
        SqlConnection.getSqlConnection().updateTypeRoom(tr.getIdTypeRoom(),tf.getText(),spinnerMonth.getValue(),spinnerDay.getValue());
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementTypeAll.fxml"));
        gridPane.getChildren().setAll(pane);
        clear();

    }

    @FXML
    void BtnInCorrect(ActionEvent event) throws IOException {
//        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
//        gridPane.getChildren().setAll(pane);

        Button b = (Button) event.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load(), 1280, 800));

            PageRoomManagementInfoTypeRoomController controller = loader.getController();
            controller.setData(datasave,msave,dsave,tr);

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    @FXML
    void BtnLeftArrow(ActionEvent event) throws IOException {
//        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
//        gridPane.getChildren().setAll(pane);

        Button b = (Button) event.getSource();

        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PageRoomManagementInfoTypeRoom.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load(), 1280, 800));

            PageRoomManagementInfoTypeRoomController controller = loader.getController();
            controller.setData(datasave,msave,dsave,tr);

            stage.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    void handleFeature1Btn(ActionEvent event) {

    }

    @FXML
    void handleFeature2Btn(ActionEvent event) {

    }

    @FXML
    void handleFeature4Btn(ActionEvent event) throws IOException {
        GridPane pane = FXMLLoader.load(getClass().getResource("/fxml/PageRoomManagementMain.fxml"));
        gridPane.getChildren().setAll(pane);

    }

    @FXML
    void handleFeature5Btn(ActionEvent event) {

    }

}
