/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
/**
 *
 * @author ahmed
 */
public class PageAccueilController implements Initializable {

    @FXML
    private Button connecter;

    @FXML
    private void AfficherModalConnectionAction(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/PageConnection.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Se connecter");
        stage.initStyle(StageStyle.UNDECORATED);
        // stage.initModality(Modality.WINDOW_MODAL);
        // stage.initOwner(
        //    ((Node) e.getSource()).getScene().getWindow());
        Stage currentstage = (Stage) connecter.getScene().getWindow();
        currentstage.close();
        stage.show();

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
