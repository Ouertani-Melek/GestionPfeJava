/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import edu.gestionpfe.models.CompetencesTechniques;
import edu.gestionpfe.models.Cv;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.CompetencesTechniquesServices;
import edu.gestionpfe.services.CvServices;
import edu.gestionpfe.services.OffreServices;
import edu.gestionpfe.services.UserServices;
import eu.hansolo.medusa.Gauge.SkinType;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class OffreController implements Initializable {

    @FXML
    private AnchorPane myAnchor;
    
    private int offreNbr = 0;
    private int layoutX = 10;
    private int layoutY = 10;
   
    private long lastTimerCall;
    private static final Random RND = new Random();

    private void afficherOffres(Offre o) throws Exception {
        try {
            Pane p = new Pane();
            p.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            offreNbr++;
            ImageView img;
            User usr = UserServices.selectUserById(o.getIdUser());
            img = UserServices.getUserImage(usr);
            p.setPrefSize(180, 250);
            Button b = new Button("Savoir plus");
            b.setLayoutX(50);
            b.setLayoutY(80);
            b.setVisible(false);
            Image image = img.getImage();
            Circle c = new Circle(70, 70, 70);
            c.setFill(new ImagePattern(image));
            Label titre = new Label();
            String titreS = o.getTitre();
            titre.setText(titreS);
            if (o.getTitre().length() > 20) {
                titreS = o.getTitre().substring(0, Math.min(o.getTitre().length(), 20));
                titre.setText(titreS + "...");
            }
            c.setOnMouseEntered((MouseEvent t) -> {
                b.setVisible(true);
            });
            c.setOnMouseExited((MouseEvent t) -> {
                b.setVisible(false);  //btnsa.setStyle("-fx-background-color:transparent;");
            });
            b.setOnMouseEntered((MouseEvent t) -> {
                b.setVisible(true);
            });
            b.setOnAction((ActionEvent e) -> {
                Platform.runLater(() -> {
                    try {
                    //    Gauge gauge =  GaugeBuilder.create().skinType(SkinType.SLIM).value(90).animated(true).animationDuration(9000).build();
                    
                        Stage dialog = new Stage();
                        Main m = new Main();
                        m.setO(o);
                        m.setUsr(usr);
                        m.init();
                        m.start(dialog);
                        b.setVisible(false);
                    } catch (Exception ex) {
                        Logger.getLogger(OffreController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
            titre.setLayoutX(5);
            titre.setLayoutY(160);
            p.getChildren().add(titre);
            p.getChildren().add(c);
            p.getChildren().add(b);
          

            if (offreNbr == 1) {
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY);
            }
            if (offreNbr == 2) {
                layoutX += 250;
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY);
            }
            if (offreNbr == 3) {
                offreNbr = 0;
                layoutX += 250;
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY);
                layoutY += 270;
                layoutX = 10;
            }
            myAnchor.getChildren().add(p);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            OffreServices.selectOffres().stream().forEach(o -> {
                try {
                    afficherOffres(o);
                    System.out.println(o);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
