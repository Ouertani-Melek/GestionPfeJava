/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.CVControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.IdExistant;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.dialogueFormation;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.lfa;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.isValidName;
import edu.gestionpfe.models.Formation;
import edu.gestionpfe.services.FormationServices;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
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
public class AjouterNouvelleFormationController implements Initializable {

    @FXML
    private JFXTextField NouvelleFormation;
    @FXML
    private JFXDatePicker NouvelleDate;
       
        @FXML
    private JFXButton ajoutfo;
        private Boolean veriff=true;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Formation f=new Formation();
            FormationServices s=new FormationServices();
              
          
           f.setIdCv(IdExistant);
        NouvelleFormation.textProperty().addListener((observable,oldv,newv) -> {
            
            
           if (lfa.stream().anyMatch(e->e.equalsIgnoreCase(newv))) {
                                NouvelleFormation.setStyle("-fx-border-color : #FF0000");
                                NouvelleFormation.setPromptText("Formation Existe Deja");
                               
                                veriff = true;
                            } else if (!isValidName(newv)) {
                                NouvelleFormation.setStyle("-fx-border-color : #FF0000");
                                NouvelleFormation.setPromptText("Caractéres speciales detectés ");
                               
                                veriff = true;
                            } else {

                               
                                
                                NouvelleFormation.setStyle("-fx-border-color : #0000");
                                NouvelleFormation.setPromptText("La Formation");
                                veriff = false;
                                 f.setFormation(NouvelleFormation.getText());
                               
                            }
        });
        
        
             NouvelleDate.setDayCellFactory((picker) -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        
               ajoutfo.setOnAction((ActionEvent e) -> {
        
       
       if(veriff==false&& NouvelleDate.getValue()!=null)
       {
           f.setAnnee(NouvelleDate.getValue());
         

          
           s.ajouterFormationCv(f);
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Formation");
            tray.setMessage("Operation terminée avec succés");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
            
            dialogueFormation.close();
            dialogueFormation =new Stage();
            
       }
       else
       {
           System.out.println(veriff);
        TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Formation");
            tray.setMessage("La Formation existe Deja ou le champ formation est vide !");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
       }
        
        });
            
        
       
       
    }    

  

    @FXML
    private void quitter(ActionEvent event) {
        dialogueFormation.close();
        dialogueFormation =new Stage();
    }
    
}
