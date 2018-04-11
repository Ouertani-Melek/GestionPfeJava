/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.OffreServices;
import edu.gestionpfe.services.UserServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class OffreAdminController implements Initializable {

    private int offreNbr = 0;
    private int layoutX = 10;
    private int layoutY = 10;
    @FXML
    private AnchorPane anchor;

    private void offres(Offre o) {
        try {
            if (!o.isEtat()) {
                Pane p = new Pane();
                offreNbr++;
                p.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                ImageView img;
                User usr = UserServices.selectUserById(o.getIdUser());
                img = UserServices.getUserImage(usr);
                p.setPrefSize(200, 200);
                //buttons
                Button b = new Button("Voir Plus");
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    VoirPlusAdminController.o = o;
                                    Node node = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Offres/VoirPlusAdmin.fxml"));
                                    Stage dialog = new Stage();
                                    dialog.setScene(new Scene((Parent) node));
                                    dialog.initModality(Modality.APPLICATION_MODAL);
                                    dialog.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        });
                    }
                });
                Button Delet = new Button("Refuser");
                b.getStyleClass().add("button");
                Delet.getStyleClass().add("button");
                b.setLayoutX(30);
                b.setLayoutY(100);
                Delet.setLayoutX(25);
                Delet.setLayoutY(70);
                Delet.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    Alert alert = new Alert(Alert.AlertType.WARNING, "Voulez vous vraiment refuser cet offre ?  ", ButtonType.OK, ButtonType.CANCEL);
                                    alert.initStyle(StageStyle.UNDECORATED);

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        OffreServices.deleteOffre(o.getId());

                                    } else {
                                        //alert.hide();
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(OffreAdminController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        });
                    }
                });
                Label l = new Label(o.getTitre());
                l.setLayoutX(10);
                l.setLayoutY(110);
                l.getStyleClass().add("main-text");
                Image image = img.getImage();
                Circle c = new Circle(70, 70, 70);
                c.setFill(new ImagePattern(image));
                p.getChildren().add(c);
                p.getChildren().add(Delet);
                p.getChildren().add(b);
                p.getChildren().add(l);
                if (offreNbr == 1) {
                    p.setLayoutX(layoutX);
                    p.setLayoutY(layoutY + 30);
                }
                if (offreNbr == 2) {
                    layoutX += 310;
                    p.setLayoutX(layoutX);
                    p.setLayoutY(layoutY + 30);
                }
                if (offreNbr == 3) {
                    offreNbr = 0;
                    layoutX += 310;
                    p.setLayoutX(layoutX);
                    p.setLayoutY(layoutY + 30);
                    layoutY += 250;
                    layoutX = 10;
                }
                anchor.getChildren().add(p);
            }
        } catch (Exception ex) {
            Logger.getLogger(OffreAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("ok");
        try {
            OffreServices.selectOffresFalse().stream().forEach(o -> {
                try {
                    System.out.println("ok");
                    offres(o);
                    System.out.println(o.toString());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
