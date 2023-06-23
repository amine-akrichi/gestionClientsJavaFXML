package com.example.controller;


import com.example.DAO.DAOFacture;
import com.example.DAO.LaConnexion;
import com.example.classes.Facture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerFacture implements Initializable {


    @FXML TableView<Facture> tableView = new TableView<>();
    @FXML TableColumn<Facture,String> colNum;
    @FXML TableColumn<Facture,String> colDate;
    @FXML TableColumn<Facture,String> colTotal;
    @FXML TableColumn<Facture,String> colRefCli;
    @FXML TextField textFieldNum;
    @FXML DatePicker datePickerDateFact;
    @FXML TextField textFieldTotal;
    @FXML TextField textFieldRefCli;


    ObservableList<Facture> observableList;
    Connection cn;

    public void remiseAzero(){
        textFieldNum.setDisable(false);
        textFieldNum.clear();
        datePickerDateFact.setValue(null);
        textFieldTotal.clear();
        textFieldRefCli.clear();
    }

    public void lister(){
        cn = LaConnexion.seConnecter();
        observableList.clear();

        try {
            ResultSet resultSet = cn.createStatement().executeQuery("SELECT * FROM facture;");
            while (resultSet.next()){
                observableList.add(new Facture(resultSet.getString(1),resultSet.getDate(2),resultSet.getFloat(3),resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableView.setItems(observableList);
        System.out.println(tableView.getItems());
    }

    @FXML
    private void ajouter() {
        DAOFacture.ajouter(textFieldNum.getText(), java.sql.Date.valueOf(datePickerDateFact.getValue()),Float.parseFloat(textFieldTotal.getText()),textFieldRefCli.getText());
        lister();
        remiseAzero();
    }


    @FXML
    private void modifier(){
        DAOFacture.modifier(textFieldNum.getText(), java.sql.Date.valueOf(datePickerDateFact.getValue()),Float.parseFloat(textFieldTotal.getText()),textFieldRefCli.getText());
        lister();
        remiseAzero();
    }

    @FXML private void archiver(){
        DAOFacture.archiver(textFieldNum.getText());
        lister();
        remiseAzero();
    }

    public void selectLigne(){
        Facture f = tableView.getSelectionModel().getSelectedItem();
        textFieldNum.setDisable(true);
        textFieldNum.setText(f.getNumFacture());
        datePickerDateFact.setValue(f.getDateFacture().toLocalDate());
        textFieldTotal.setText(String.valueOf(f.getTotal()));
        textFieldRefCli.setText(f.getRefCli());
    }

    public void openGestionClients() throws IOException {
        Stage loginStage = (Stage) textFieldNum.getScene().getWindow();
        loginStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/view/FXMLGestionClients.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList = FXCollections.observableArrayList();
        colNum.setCellValueFactory(new PropertyValueFactory<>("numFacture"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRefCli.setCellValueFactory(new PropertyValueFactory<>("refCli"));
        lister();

        tableView.setItems(observableList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectLigne();
        });
    }
}
