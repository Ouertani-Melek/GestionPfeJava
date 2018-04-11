/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import static edu.gestionpfe.controllers.OffresEntrepriseController.aa;
import edu.gestionpfe.services.OffreServices;
import edu.gestionpfe.services.TechnologiesServices;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ahmed
 */
public class MyBridge {

    public MyBridge() {

    }

    public void callbackFromJavaScript(String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    int idOffre = Integer.parseInt(id);
                    System.out.println(idOffre);
                    EditOffreController.idOffre = idOffre;
                    EditOffreController.layouY = 22;
                    EditOffreController.layouX = 339;
                    ScrollPane node = (ScrollPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Offres/EditOffre.fxml"));
                    Stage dialog = new Stage();
                    dialog.setScene(new Scene(node));
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.show();

                    return;
                } catch (IOException ex) {
                    Logger.getLogger(MyBridge.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void callbackFromJavaScriptDelete(String id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Voulez vous vraiment Supprimer cette Offre ?  ", ButtonType.OK, ButtonType.CANCEL);
                    alert.initStyle(StageStyle.UNDECORATED);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        int temp =Integer.parseInt(id);
                        TechnologiesServices.deleteTechs(TechnologiesServices.selectTechnologies(OffreServices.getOffreById(temp)));
                        OffreServices.deleteOffre(temp);
                        aa.setText("ok");
                    } else {
                        //alert.hide();
                    }

                    return;
                } catch (SQLException ex) {
                    Logger.getLogger(MyBridge.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    public void callbackFromJavaScriptAdd() {
       
                aa.setText("add");
                
        
    }
}
