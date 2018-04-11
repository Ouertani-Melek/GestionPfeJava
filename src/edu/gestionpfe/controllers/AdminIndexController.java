/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import gestionpfe.NaivgationDrawer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author yahia
 */
public class AdminIndexController  implements Initializable{

    @FXML
    private AnchorPane root;
    
    public static AnchorPane rootP;
    @FXML
    private JFXButton AfficherAccepte;
    @FXML
    private AnchorPane container;
    public  static  AnchorPane containerP;
    @FXML
    private Text label;
    @FXML
    private JFXButton b1;
    public static AnchorPane pane;
    @FXML
    private JFXButton quit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        if (!NaivgationDrawer.isSplashLoaded) {
                loadSplashScreen();
            }
            label.setText("Connecté en tant que Administrateur" );
            label.setStyle("-fx-text-inner-color: #9eca51");
            rootP=root;
            pane=container;
           
        
    }
     private void loadSplashScreen() {
        try {
            NaivgationDrawer.isSplashLoaded = true;

            StackPane pane = FXMLLoader.load(getClass().getResource(("/edu/gestionpfe/views/Default/SplashFXML.fxml")));
            root.getChildren().setAll(pane);
                    
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/edu/gestionpfe/views/Default/AdminIndex.fxml")));
                    root.getChildren().setAll(parentContent);

                } catch (IOException ex) {
                    Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    @FXML
    private void encadrement(ActionEvent event) throws IOException {
        Node node=FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/stages/liststages.fxml"));
                                       container.getChildren().setAll(node);
    }

    @FXML
    private void quit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void allsoutenance(ActionEvent event) throws IOException {
       try {
//                   FXMLLoader root = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/Soutenance/affectationPresident.fxml"));
//                    AnchorPane x = root.load();
//                    AffectationPresidentController c = root.getController();
//
//                    containerP.getChildren().clear();
//                    containerP.getChildren().add((Node) x);
 Node node=FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Soutenance/affectationPresident.fxml"));
                                       pane.getChildren().setAll(node);
                } catch (IOException ex) {
                    Logger.getLogger(SoutenanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        
    }
    
}
