/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import static edu.gestionpfe.controllers.CVControllers.AfficherCvController.IdExistant;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import static edu.gestionpfe.controllers.UserIndexController.containerP;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.CvServices;
import edu.gestionpfe.services.UserServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
public class ProfilesidebarController implements Initializable {

    @FXML
    private JFXButton b1;
    @FXML
    private JFXButton b2;
    @FXML
    private JFXButton b3;

    @FXML
    private JFXButton exit;
    @FXML
    private VBox vbox;
    private ScrollPane node;
    private StackPane pane1;
    @FXML
    private Text nometprenom;
    @FXML
    private Circle userImage;
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image imageUser;
            Text txt; 
           imageUser = UserServices.getUserImage(ConnectionController.getUser()).getImage();
           
            ImageView img;
            
            User userr = UserServices.selectUserById(usr.getId());
            
            
            img = UserServices.getUserImage(userr);
            Image image = img.getImage();
           
            userImage.setFill(new ImagePattern(image));
           
            nometprenom.setText(usr.getNom()+" "+usr.getPrenom());
            b1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int test=usr.getId();
                               
                                
                                    if(CvServices.findCv().getId()==0)
                                    {
                                    try {
                                        
                                        node = (ScrollPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Cv/remplirCv.fxml"));
                                   
                                       
                                    } catch (IOException ex) {
                                        System.out.println(ex.getMessage());
                                    }
                                        containerP.getChildren().setAll(node);
                                    }
                                    else{
                                        System.out.println(CvServices.findCv());
                                    try {
                                        pane1 = (StackPane)FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Cv/afficherCv.fxml"));
                                            b2.setDisable(false);
                                    } catch (IOException ex) {
                                        System.out.println("erreur"+ex.getMessage());
                                    }
                                        containerP.getChildren().setAll(pane1);
                                    }
                                

                            } catch (SQLException ex) {
                                Logger.getLogger(ProfilesidebarController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });
                }
            });

        } catch (Exception ex) {
            System.out.println("can't load image");
        }
        
        b2.setOnAction((ActionEvent e) -> {
        
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes Vous sure de supprimer votre Cv?", ButtonType.OK, ButtonType.NO);
                        alert.initStyle(StageStyle.DECORATED);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                                CvServices s=new CvServices();
                 try {
                     s.supprimerCv(CvServices.findCv().getId());
                      node = (ScrollPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Cv/remplirCv.fxml"));
                                 
                                        containerP.getChildren().setAll(node);
                                        
                                         TrayNotification tray = new TrayNotification();
                                        tray.setTitle("Gestion PFE CV");
                                        tray.setMessage("terminée avec succés ! Remplir votre Cv de nouveau!");
                                        tray.setNotificationType(NotificationType.SUCCESS);
                                        tray.setAnimationType(AnimationType.SLIDE);
                                        tray.showAndDismiss(Duration.seconds(10));
                     
                 } catch (SQLException ex) {
                      TrayNotification tray = new TrayNotification();
                                        tray.setTitle("Gestion PFE CV");
                                        tray.setMessage("Vous n'avez de Cv pour le Supprimer");
                                        tray.setNotificationType(NotificationType.ERROR);
                                        tray.setAnimationType(AnimationType.SLIDE);
                                        tray.showAndDismiss(Duration.seconds(10));
                 } catch (IOException ex) {
                     Logger.getLogger(ProfilesidebarController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                        }
        
        });
      
        
    }

   /*private void goToCv(ActionEvent event) {

        try {

            AnchorPane node;
            node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Cv/remplirCv.fxml"));
            containerP.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(ProfilesidebarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/

    
    @FXML
    private void changeColor(ActionEvent event) {

        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        switch (btn.getText()) {
            case "Supprimer Cv":
                UserIndexController.containerP.setStyle("-fx-background-color:#00FF00");
                break;
            case "Color 2":
                UserIndexController.containerP.setStyle("-fx-background-color:#0000FF");
                break;
            case "Color 3":
                UserIndexController.containerP.setStyle("-fx-background-color:#FF0000");
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
