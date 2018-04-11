/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import static edu.gestionpfe.controllers.UserIndexController.rootP;
import gestionpfe.NaivgationDrawer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class EntrepriseIndexController implements Initializable {

    private int DemandeNbr = 0;
    private int layoutX = 10;
    private int layoutY = 10;
    private String concatinatedTechs = "";
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane container;

    AnchorPane node;

    @FXML
    private Text label;

    public static AnchorPane containerP;
    @FXML
    private JFXButton afficheD;
    @FXML
    private JFXButton AfficherAccepte;
    @FXML
    private JFXButton ajouterOffres;
    @FXML
    private JFXButton mesOffre;
       @FXML
    private JFXButton quitter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        if (!NaivgationDrawer.isSplashLoaded) {
            loadSplashScreen();
        }
        label.setText("ConnectÃ© en tant que" + " " + usr.getNom());
        label.setStyle("-fx-text-inner-color: #9eca51");

        rootP = root;
        containerP = container;
        LineChartController lineChart = new LineChartController();
        LineChart d = lineChart.drawData(); //try {
        d.setPrefSize(800, 600);
        container.getChildren().add(d);

        afficheD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Demandes/ListDesDemandes.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        container.getChildren().setAll(node);

                    }
                });
            }
        });
        AfficherAccepte.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Demandes/ListeDesAccepte.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        container.getChildren().setAll(node);

                    }
                });
            }
        });
        ajouterOffres.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Offres/AjouterOffre.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        container.getChildren().setAll(node);

                    }
                });
            }
        });
        mesOffre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Offres/OffresEntreprise.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        container.getChildren().setAll(node);

                    }
                });
            }
        });
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
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
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/edu/gestionpfe/views/Default/EntrepriseIndex.fxml")));
                    root.getChildren().setAll(parentContent);

                } catch (IOException ex) {
                    Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
