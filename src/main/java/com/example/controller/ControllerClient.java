package com.example.controller;

import com.example.DAO.DAOClient;
import com.example.DAO.LaConnexion;
import com.example.classes.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerClient implements Initializable {

    @FXML TextField textFieldCode;
    @FXML TextField textFieldNom;
    @FXML TextField textFieldAdresse;
    @FXML TextField textFieldEmail;

    @FXML
    TableView<Client> tableView = new TableView<>();
    @FXML TableColumn<Client,String> colCode;
    @FXML TableColumn<Client,String> colNom;
    @FXML TableColumn<Client,String> colAdresse;
    @FXML TableColumn<Client,String> colEmail;

    ObservableList<Client> observableList;
    Connection cn;

    public void remiseAzero(){
        textFieldCode.setDisable(false);
        textFieldCode.clear();
        textFieldNom.clear();
        textFieldAdresse.clear();
        textFieldEmail.clear();
    }

    public void lister(){
        cn = LaConnexion.seConnecter();
        observableList.clear();

        try {
            ResultSet resultSet = cn.createStatement().executeQuery("SELECT * FROM client;");
            while (resultSet.next()){
                observableList.add(new Client(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableView.setItems(observableList);
    }

    @FXML
    private void ajouter() {
        DAOClient.ajouter(textFieldCode.getText(),textFieldNom.getText(),textFieldAdresse.getText(),textFieldEmail.getText());
        lister();
        remiseAzero();
    }


    @FXML
    private void modifier(){
        DAOClient.modifier(textFieldCode.getText(),textFieldNom.getText(),textFieldAdresse.getText(),textFieldEmail.getText());
        lister();
        remiseAzero();
    }

    @FXML private void archiver(){
        DAOClient.archiever(textFieldCode.getText());
        lister();
        remiseAzero();
    }



    public void openGestionFacture() throws IOException {
        Stage loginStage = (Stage) textFieldCode.getScene().getWindow();
        loginStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/view/FXMLGestionFactures.fxml"));
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

    public void selectLigne(){
        Client c = tableView.getSelectionModel().getSelectedItem();
        textFieldCode.setText(c.getCodeCli());
        textFieldNom.setText(c.getNomCli());
        textFieldEmail.setText(c.getEmailCli());
        textFieldAdresse.setText(c.getAdresseCli());
        textFieldCode.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        observableList = FXCollections.observableArrayList();
        colCode.setCellValueFactory(new PropertyValueFactory<>("codeCli"));
        System.out.println(colCode.getCellFactory().toString());
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomCli"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresseCli"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailCli"));
        lister();

        tableView.setItems(observableList);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectLigne();
        });
    }



}