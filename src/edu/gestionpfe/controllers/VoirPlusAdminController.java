/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.services.OffreServices;
import edu.gestionpfe.services.TechnologiesServices;
import edu.gestionpfe.services.UserServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class VoirPlusAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static Offre o;
    AnchorPane pane = new AnchorPane();
        private String concatinatedTechs="";
    @FXML
    private AnchorPane myAnchor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ImageView img;
            img = UserServices.getUserImage(UserServices.selectUserById(o.getIdUser()));
            myAnchor.setOpacity(0.95);
            myAnchor.setStyle(
                    "-fx-background-color: #696969;"
                            + "-fx-background-image: url("
                            + "'/edu/gestionpfe/views/images/backgrounds/cource-search-bg.jpg'"
                            + "); "
                            + "-fx-background-size: cover;"
                            + "-fx-background-radius: 10;"
            );
            Image imageD = img.getImage();
            Circle cD = new Circle(70, 70, 70);
            cD.setLayoutX(20);
            cD.setLayoutY(30);
            cD.setFill(new ImagePattern(imageD));
            
            TechnologiesServices.selectTechnologies(o).stream().forEach(a -> {
                if(a!=null)
                    concatinatedTechs += a.getTech() + "\n";
            });
            Label description = new Label();
            description.setPrefWidth(215);
            description.setWrapText(true);
            description.setText("Description générale :\n" + o.getDescription() + "\n\n\nTechnologies Demandées :\n" + concatinatedTechs);
            System.out.println(concatinatedTechs);
            concatinatedTechs = "";
            description.setLayoutX(420);
            description.setLayoutY(40);
            description.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
            JFXButton postuler = new JFXButton("confirmer maintenant");
            postuler.setLayoutX(700);
            postuler.setLayoutY(370);
            postuler.setPrefSize(200, 50);
            postuler.setStyle("-fx-background-color: #00FF00");
            postuler.setOnAction((ActionEvent d) -> {
                Platform.runLater(() -> {
                    try {
                        OffreServices.setTrue(o.getId());
                    } catch (SQLException ex) {
                        Logger.getLogger(VoirPlusAdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
            Label titreD = new Label();
            titreD.setText("Titre du stage : " + o.getTitre());
            titreD.setLayoutX(20);
            titreD.setLayoutY(190);
            titreD.setOpacity(5);
            titreD.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
            Label nomEntreprise = new Label();
            nomEntreprise.setText("Nom de l'entreprise : " + UserServices.selectUserById(o.getIdUser()).getNom());
            nomEntreprise.setLayoutX(20);
            nomEntreprise.setLayoutY(210);
            nomEntreprise.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
            
            myAnchor.getChildren().add(titreD);
            
            myAnchor.getChildren().add(description);
            myAnchor.getChildren().add(cD);
            myAnchor.getChildren().add(nomEntreprise);
            myAnchor.getChildren().add(postuler);
            
            
            
            // TODO
        } catch (Exception ex) {
            Logger.getLogger(VoirPlusAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
