package com.jvetter.databaseclient;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    @FXML //fx:id="text_field";
    private TextField queryTF;

    @FXML // fx:id="tableView";
            TableView<User> resultsTV;

    public void executeQuery(){

        ObservableList<User> values =
                null;
        try {
            System.out.println(queryTF.getText());
            values = User.getUsers("jdbc:sqlite:user.db", queryTF.getText());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TableColumn<User,String> first
                = new TableColumn<User, String>("First");
        first.setCellValueFactory(new PropertyValueFactory("First"));
        first.setStyle( "-fx-alignment: CENTER;");
        TableColumn<User, String> last
                = new TableColumn<User, String>("Last");
        last.setStyle("-fx-alignment: CENTER;");
        last.setCellValueFactory(new PropertyValueFactory("Last"));

        resultsTV.getColumns().setAll(first, last);
        resultsTV.setItems(values);
    }
}


