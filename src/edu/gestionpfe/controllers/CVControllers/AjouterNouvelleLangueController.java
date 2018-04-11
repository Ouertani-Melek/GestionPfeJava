/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.CVControllers;

import com.jfoenix.controls.JFXComboBox;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.IdExistant;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.dialogueLangue;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.llangu;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.langues;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.niveaux;
import edu.gestionpfe.models.Langues;
import edu.gestionpfe.services.languesServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
public class AjouterNouvelleLangueController implements Initializable {

    @FXML
    private JFXComboBox<String> niveauEstime;
    @FXML
    private JFXComboBox<String> LangueNouvelle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
      
       
        
        LangueNouvelle.getItems().addAll(langues);
        llangu.forEach(e->LangueNouvelle.getItems().remove(e));
       niveauEstime.getItems().addAll(niveaux);
        
       
    }    

    @FXML
    private void NouvelleCompetence(ActionEvent event) throws SQLException {
        
        if(LangueNouvelle.getValue()!=null && niveauEstime.getValue()!=null)
        {        languesServices l=new languesServices();
       Langues lan=new Langues();
       lan.setIdCv(IdExistant);
       lan.setLangue(LangueNouvelle.getValue());
       lan.setNiveau(niveauEstime.getValue());
       l.ajoutLangueUnique(lan);
       
       TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Langue");
            tray.setMessage("Operation terminée avec succés");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
            
            dialogueLangue.close();
            dialogueLangue =new Stage();
        
        }
        else
        {
        
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Langue");
            tray.setMessage("Les champs ne doivent pas etre vide");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
            
          
        
        }
    }

    @FXML
    private void quitter(ActionEvent event) {
        
         dialogueLangue.close();
            dialogueLangue =new Stage();
    }
    
}
