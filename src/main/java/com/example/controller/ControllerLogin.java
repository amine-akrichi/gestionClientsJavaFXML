package com.example.controller;

import com.example.DAO.DAOUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    @FXML
    TextField textFieldUsername;
    @FXML
    TextField textFieldPassword;
    @FXML
    Label labelIncorrect;

    public static String CryptoSHA(String password) throws Exception {
        String str = password;
        MessageDigest msg = MessageDigest.getInstance("SHA-256");
        byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuilder s = new StringBuilder();
        for (byte b : hash) {
            s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return s.toString();
    }

    public void login() {
        try {
            String username = textFieldUsername.getText();
            String password = CryptoSHA(textFieldPassword.getText());

            System.out.println(CryptoSHA(textFieldPassword.getText()));

            boolean isAuthenticated = DAOUtilisateur.login(username, password);
            if (isAuthenticated) {
                Stage loginStage = (Stage) textFieldUsername.getScene().getWindow();
                loginStage.close();
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/view/FXMLGestionClients.fxml"));
                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.show();
            }else{
                labelIncorrect.setText("username ou mot de passe incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inscrire(){
        try {
            String username = textFieldUsername.getText();
            String password = CryptoSHA(textFieldPassword.getText());
            DAOUtilisateur.ajouter(username, password);
            Stage loginStage = (Stage) textFieldUsername.getScene().getWindow();
            loginStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/view/FXMLGestionClients.fxml"));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

