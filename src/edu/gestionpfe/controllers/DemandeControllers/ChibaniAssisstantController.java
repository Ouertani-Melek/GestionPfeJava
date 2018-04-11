/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.DemandeControllers;

import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.dialog;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idDemande;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idOffreChibani;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idU;
import edu.gestionpfe.services.DemandesServices;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class ChibaniAssisstantController implements Initializable {

    @FXML
    private ImageView img1;
    @FXML
    private Label lbl1;
    @FXML
    private ImageView img2;
    @FXML
    private Label lbl2;
    @FXML
    private ImageView img3;
    @FXML
    private Label lbl3;
    @FXML
    private ImageView img4;
    @FXML
    private Label lbl4;
    @FXML
    private ImageView img5;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Text text3;
    @FXML
    private Text text4;
    @FXML
    private Text text5;
    @FXML
    private Label lbl_msg;
     private RotateTransition rotateTransition1, rotateTransition2, rotateTransition3,
            rotateTransition4, rotateTransition5;
     private float percent;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                 DemandesServices chibaniDemande =new DemandesServices();
               try {
                   percent= chibaniDemande.ChibaniIA();
                   chibaniDemande.ChibaniIDDateEntretien();
                } catch (SQLException ex) {
                    Logger.getLogger(ChibaniAssisstantController.class.getName()).log(Level.SEVERE, null, ex);
                }
               img1.setImage(new Image("edu/gestionpfe/views/images/events/syn.png"));
        text1.setText("Recherche de Données");
        rotateTransition1 = new RotateTransition(Duration.seconds(2), img1);
        rotateTransition2 = new RotateTransition(Duration.seconds(2), img2);
        rotateTransition3 = new RotateTransition(Duration.seconds(2), img3);
        rotateTransition4 = new RotateTransition(Duration.seconds(2), img4);
        rotateTransition5 = new RotateTransition(Duration.seconds(2), img5);
        RotateTransition transition[] = {rotateTransition1, rotateTransition2,
            rotateTransition3, rotateTransition4, rotateTransition5};
        for (RotateTransition rTransition : transition) {
            rTransition.setCycleCount(1);
            rTransition.setAutoReverse(false);
            rTransition.setFromAngle(720);
            rTransition.setToAngle(0);
        }
        rotateTransition1.play();
               
        rotateTransition1.setOnFinished((e) -> {
            img1.setImage(new Image("edu/gestionpfe/views/images/events/ok.png"));
            lbl1.setStyle("-fx-background-color:#45A563");
            img2.setImage(new Image("edu/gestionpfe/views/images/events/syn.png"));
            text2.setText("Calcul Pourcentage");
             TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE CHIBANI ASSISSTANT");
            tray.setMessage("Pourcentage de succés est "+percent+"%");
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(5));
            rotateTransition2.play();
        });
        
        
         

        rotateTransition2.setOnFinished((e) -> {
            img2.setImage(new Image("edu/gestionpfe/views/images/events/ok.png"));
            lbl2.setStyle("-fx-background-color:#45A563");
            img3.setImage(new Image("edu/gestionpfe/views/images/events/syn.png"));
            text3.setText("Date Entretien ");
          
                    
             TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE CHIBANI ASSISSTANT");
                     try {
                         tray.setMessage("La Date Convenable est :\n"+chibaniDemande.ChibaniIDDateEntretien());
                     } catch (SQLException ex) {
                         Logger.getLogger(ChibaniAssisstantController.class.getName()).log(Level.SEVERE, null, ex);
                     }
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
                                            
                                           
               rotateTransition3.play();                         
           // rotateTransition3.play();
        });
        
        
        
        rotateTransition3.setOnFinished((e) -> {
            img3.setImage(new Image("edu/gestionpfe/views/images/events/ok.png"));
            lbl3.setStyle("-fx-background-color:#45A563");
            img4.setImage(new Image("edu/gestionpfe/views/images/events/syn.png"));
            text4.setText("Confirmation");
                     try {
     chibaniDemande.ajouterEntretien(idDemande, Timestamp.valueOf(chibaniDemande.ChibaniIDDateEntretien()), "SKYPE");
                     } catch (SQLException ex) {
                         Logger.getLogger(ChibaniAssisstantController.class.getName()).log(Level.SEVERE, null, ex);
                     }
            rotateTransition4.play();
        });
        rotateTransition4.setOnFinished((e) -> {
            img4.setImage(new Image("edu/gestionpfe/views/images/events/ok.png"));
            lbl4.setStyle("-fx-background-color:#45A563");
            img5.setImage(new Image("edu/gestionpfe/views/images/events/syn.png"));
            text5.setText("Ajout en cours !");
            
            rotateTransition5.play();
        });
        rotateTransition5.setOnFinished((e) -> {
            img5.setImage(new Image("edu/gestionpfe/views/images/events/ok.png"));
            lbl_msg.setVisible(true);
            dialog.close();
            dialog=new Stage();
          
        });
            }
        });
        
    }    

    @FXML
    private void close_app(MouseEvent event) {
       
    }
    
}
