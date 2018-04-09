/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.DemandeControllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import static edu.gestionpfe.controllers.DemandeControllers.EntrepriseCvController.diag;
import static edu.gestionpfe.controllers.DemandeControllers.EntrepriseCvController.verification;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.dialog;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idDemande;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idOffrePourEntretien;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.myAnchorP;
import edu.gestionpfe.services.DemandesServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
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
public class EntretienDateDemandeController implements Initializable {
    public static int loadAgain;
    private int count;
    @FXML
    public AnchorPane EntretienAnchor;

    @FXML
    private JFXDatePicker DateEnJour;
    @FXML
    private JFXTimePicker TempsExact;
    @FXML
    private JFXComboBox<String> ListMethode;
private boolean test;   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DemandesServices service = new DemandesServices();
        List<Timestamp> lis = service.getAllEntretienDates(idOffrePourEntretien);

        lis.stream().forEach(e -> {
           // System.out.println(Collections.frequency(lis, e));

            //System.err.println(e.toLocalDateTime().getDayOfMonth());
            DateEnJour.setDayCellFactory((picker) -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    LocalDate today = LocalDate.now();

                    if (MonthDay.from(item).equals(MonthDay.of(e.toLocalDateTime().getMonthValue(), e.toLocalDateTime().getDayOfMonth()))
                            && !(getStyleClass().contains("next-month") || getStyleClass().contains("previous-month"))) {
                        setTooltip(new Tooltip("Vous avez un entretien pour ce jour !"));

                        setStyle("-fx-background-color: #ff4444;");
                        setDisable(empty);

                    } else {
                        setTooltip(null);
                        setStyle(null);
                    }
                    setDisable(empty || item.compareTo(today) < 0);
                }

            });

        });
        TempsExact.setEditable(false);
        TempsExact._24HourViewProperty().set(true);
//         DateEnJour.valueProperty().addListener((ov, oldValue, newValue) -> {
//
//                            System.out.println("date changed !"+oldValue +"to  "+newValue);
//                            
//                            lis.stream().forEach(e->{
//                                if(e.toLocalDateTime().toLocalDate().equals(newValue))
//                 {  
//                     DateEnJour.setStyle("-fx-border-color : #FF0000");
//                     test=true;
//                 }
//                                else
//                 {
//                         DateEnJour.setStyle("-fx-border-color : #ffff");
//                         test=false;
//                 }
//                            });
//         });
//        
        ListMethode.getItems().addAll("Skype","Reuinion Direct","Viber");
       

    }

    @FXML
    private void AjouterEntretien(ActionEvent event) {
 DemandesServices service = new DemandesServices();
 if(TempsExact.getValue()!=null && DateEnJour.getValue()!=null && ListMethode.getValue()!=null)       
 {LocalDate date = DateEnJour.getValue();
        LocalTime temp= TempsExact.getValue();
        
        LocalDateTime dt = LocalDateTime.of(date,temp);
 
 
        Timestamp tt=Timestamp.valueOf(dt);

        List<Timestamp> lis = service.getAllEntretienDates(idOffrePourEntretien);
       if(lis.contains(tt))
       {
                    DateEnJour.setStyle("-fx-border-color : #FF0000");
                   
                     TempsExact.setStyle("-fx-border-color : #FF0000");
                     
                      TrayNotification tray = new TrayNotification();
        tray.setTitle("Erreur");
        tray.setMessage("La date ou le temps de l'entretien existe  !");
        tray.setNotificationType(NotificationType.ERROR);
        tray.setAnimationType(AnimationType.FADE);
        tray.showAndDismiss(Duration.seconds(10));
                    
       }
       else
       {
     try {
         DateEnJour.setStyle("-fx-border-color : transparent");
          ListMethode.setStyle("-fx-border-color : transparent");
         TempsExact.setStyle("-fx-border-color : transparent");
         service.ajouterEntretien(idDemande, tt,ListMethode.getValue());
          TrayNotification tray = new TrayNotification();
        tray.setTitle("Gestion PFE Acceptation Candidat");
        tray.setMessage("Candidat Accepté pour entretien avec succés");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.setAnimationType(AnimationType.POPUP);
        tray.showAndDismiss(Duration.seconds(10));
        // ListDesDemandesController again =new ListDesDemandesController();
         

        diag.close();
        diag = new Stage();
        dialog.close();
        dialog=new Stage();
        
      verification=1;
     } catch (SQLException ex) {
         Logger.getLogger(EntretienDateDemandeController.class.getName()).log(Level.SEVERE, null, ex);
     }
           
       
       }
 }
 else{
      DateEnJour.setStyle("-fx-border-color : #FF0000");
                   
                     TempsExact.setStyle("-fx-border-color : #FF0000");
                     ListMethode.setStyle("-fx-border-color : #FF0000");
                      TrayNotification tray = new TrayNotification();
        tray.setTitle("Erreur");
        tray.setMessage("La date ou le temps de l'entretien existe deja  ou l'un des champs est vide !");
        tray.setNotificationType(NotificationType.ERROR);
        tray.setAnimationType(AnimationType.FADE);
        tray.showAndDismiss(Duration.seconds(10));
 }
         
       
        
        
        
        
        
        
                
                
           
       
    }

    @FXML
    private void Leave(ActionEvent event) {
        diag.close();
        diag = new Stage();

    }
    
//    public  void LoadAgain(){
//        
//        try {
//            AnchorPane node= (AnchorPane)FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Demandes/ListDesDemandes.fxml"));
//            myAnchorP=node;
//            // loadAgain=1;
//        } catch (IOException ex) {
//            Logger.getLogger(EntretienDateDemandeController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//         
//    
//    }

}
