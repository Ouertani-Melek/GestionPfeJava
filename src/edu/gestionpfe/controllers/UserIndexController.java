/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import gestionpfe.NaivgationDrawer;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class UserIndexController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private AnchorPane root;

    public static AnchorPane rootP;
    @FXML
    private  AnchorPane container;
    
    public static AnchorPane containerP;
    
  
    private Label greetings;
    @FXML
    private Text label;
    
 
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!NaivgationDrawer.isSplashLoaded) {
            loadSplashScreen();
        }
        label.setText("Connecté en tant que"+" "+usr.getNom());
        label.setStyle("-fx-text-inner-color: #9eca51");
        
        rootP = root;
        containerP = container;

        try {
            VBox box = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Default/profilesidebar.fxml"));
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(UserIndexController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isShown()) {
                drawer.close();
            } else {
                drawer.open();
                
            }
        });
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
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/edu/gestionpfe/views/Default/UserIndex.fxml")));
                    root.getChildren().setAll(parentContent);
                    

                } catch (IOException ex) {
                    Logger.getLogger(UserIndexController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(UserIndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
}
