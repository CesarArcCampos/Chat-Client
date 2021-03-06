package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.setProperty("javax.net.ssl.trustStore", "myTrustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword", "ksw8z7a");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 478, 396));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
