/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.CVControllers;

import com.jfoenix.controls.JFXComboBox;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.IdExistant;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.dialogueCentre;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.lcentr;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.CentresInterets;
import edu.gestionpfe.models.CentresInterets;
import edu.gestionpfe.services.CentresInteretsServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class AjouterNouvelleCentreController implements Initializable {

    @FXML
    private JFXComboBox<String> centre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        centre.getItems().addAll(CentresInterets);
         lcentr.forEach(e->centre.getItems().remove(e));
      
        
    }    

    @FXML
    private void NouvelleCompetence(ActionEvent event) throws SQLException {
         if(centre.getValue()!=null)
        {        CentresInteretsServices l=new CentresInteretsServices();
       CentresInterets lan=new CentresInterets();
       lan.setIdCv(IdExistant);
       lan.setCentreInteret(centre.getValue());
     
       l.AjouterCentreUnique(lan);
       
       TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Centre Interet");
            tray.setMessage("Operation terminée avec succés");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
            
            dialogueCentre.close();
            dialogueCentre =new Stage();
        
        }
        else
        {
        
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Centre Interet");
            tray.setMessage("Les champs ne doivent pas etre vide");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
            
          
        
        }
    }

    @FXML
    private void quitter(ActionEvent event) {
         dialogueCentre.close();
            dialogueCentre =new Stage();
    }
    
}
