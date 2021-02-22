package com.jvetter.databaseclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class DatabaseApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        User.initializeDatabase();

        URL url = new File("src/main/java/sample/database_client.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("DB Editor");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
