import model.DBConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/main/resources/fxml/Feature1Page1.fxml").toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Apanater");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        DBConnector.getDBConnector().createAllTable();
        launch(args);
    }

}
