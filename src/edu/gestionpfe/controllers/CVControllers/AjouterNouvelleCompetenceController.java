/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.CVControllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.IdExistant;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.dialogueCompetence;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.ltec;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.isValidName;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.niveaux;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.possibleSuggestions;
import edu.gestionpfe.models.CompetencesTechniques;
import edu.gestionpfe.services.CompetencesTechniquesServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class AjouterNouvelleCompetenceController implements Initializable {

    @FXML
    private JFXTextField compet;
    @FXML
    private JFXComboBox<String> niveauEstime;
    private Boolean veriff;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<String> newls=new ArrayList<>();
        for(String s : possibleSuggestions)
        {   
           
            newls.add(s);
        }
//        newls.stream().anyMatch(e->{
//        if(ltec.stream().anyMatch(t->t.equalsIgnoreCase(e)))
//                {
//                    newls.remove(e);
//                }
//        });
//        for(String s : ltec){
//        if(newls.stream().anyMatch(e->e.equalsIgnoreCase(s)))
//        {
//                newls.remove(s);
//        }
//        }
               
                
                
        
        TextFields.bindAutoCompletion(compet, newls);
          compet.textProperty().addListener((observable,oldv,newv) -> {
            
          
          
                             if (!isValidName(newv)&& !newls.stream().anyMatch(e->e.equalsIgnoreCase(newv))) {
                                compet.setStyle("-fx-border-color : #FF0000");
                                compet.setPromptText("Caractéres speciales detectés ou Competence Existe Deja ");
                               
                                veriff = true;
                            } else {

                               
                                
                                compet.setStyle("-fx-border-color : #0000");
                                compet.setPromptText("Competence");
                                veriff = false;
                               
                               
                            }
        });
          niveauEstime.getItems().addAll(niveaux);
        
    }    

    @FXML
    private void NouvelleCompetence(ActionEvent event) throws SQLException {
         if(niveauEstime.getValue()!=null && veriff==false)
        {        CompetencesTechniques lan=new CompetencesTechniques();
                CompetencesTechniquesServices l =new CompetencesTechniquesServices();
       lan.setIdCv(IdExistant);
       lan.setCompetence(compet.getText());
     lan.setLevel(niveauEstime.getValue());
       l.AjouterCompetenceUnique(lan);
       
       TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Competence techniques");
            tray.setMessage("Operation terminée avec succés");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
            
            dialogueCompetence.close();
            dialogueCompetence =new Stage();
        
        }
        else
        {
        
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout Competence techniques");
            tray.setMessage("Les champs ne doivent pas etre vide");
            tray.setNotificationType(NotificationType.WARNING);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
            
           
        
        }
        
        
        
        
    }

    @FXML
    private void quitter(ActionEvent event) {
         dialogueCompetence.close();
            dialogueCompetence =new Stage();
    }
    
}
