/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static edu.gestionpfe.controllers.EntrepriseIndexController.containerP;
import edu.gestionpfe.services.OffreServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class AjouterOffreController implements Initializable {

    @FXML
    private AnchorPane myPane;
    @FXML
    private JFXTextField titre;
    @FXML
    private JFXTextField duree;
    @FXML
    private JFXTextField nbrEtudiants;
    @FXML
    private JFXTextField technologie;
    @FXML
    private JFXTextArea description;
    @FXML
    private ImageView plus;
    @FXML
    private Pane techPane;
    @FXML
    private JFXButton ajouterButton;

    private int nbrTechs = 0;
    private int layoutY;
    private int temp = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip t = new Tooltip("Ajouter des champ pour plus de technologies");
        Tooltip.install(plus, t);
        Image minus = new Image("/edu/gestionpfe/views/images/minus.png");
        List<JFXTextField> techs = new ArrayList<>();
        List<ImageView> m = new ArrayList<>();//504
        plus.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            // System.out.println("aaaa");
            nbrTechs++;
            ImageView moins = new ImageView(minus);
            moins.setId(String.valueOf(nbrTechs));
            moins.setFitHeight(36);
            moins.setFitWidth(40);
            JFXTextField tech = new JFXTextField();
            tech.setId(String.valueOf(nbrTechs));
            tech.setPromptText("Technologie");
            tech.setPrefSize(289, 31);
            if (nbrTechs == 1) {
                layoutY = 38;
                tech.setLayoutY(layoutY);
                tech.setLayoutX(41);
                moins.setLayoutX(331);
                techPane.getChildren().add(tech);
            } else if (nbrTechs % 2 == 0) {
                tech.setLayoutY(layoutY);
                tech.setLayoutX(447);
                moins.setLayoutX(737);
                techPane.getChildren().add(tech);
            } else {
                layoutY += 94;
                techPane.setPrefHeight(techPane.getPrefHeight() + 94);
                tech.setLayoutY(layoutY);
                tech.setLayoutX(41);
                moins.setLayoutX(331);
                description.setLayoutY(description.getLayoutY() + 94);
                ajouterButton.setLayoutY(ajouterButton.getLayoutY() + 94);
                techPane.getChildren().add(tech);
            }
            moins.setLayoutY(layoutY);
            moins.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) -> {
                tech.setVisible(false);
                moins.setVisible(false);
                techPane.getChildren().forEach(te -> {
                    if (te instanceof JFXTextField) {
                        if (te.isVisible()) {
                            techs.add((JFXTextField) te);
                        }
                    } else if (te instanceof ImageView) {
                        if (te.isVisible()) {
                            m.add((ImageView) te);
                        }
                    }
                });
                description.setLayoutY(310);
                ajouterButton.setLayoutY(504);
                techPane.setPrefHeight(101);
                techPane.getChildren().clear();
                if (!techs.isEmpty()) {

                    techs.forEach((node) -> {
                        temp++;
                        if (temp == 1) {
                            layoutY = 38;
                            node.setLayoutY(layoutY);
                            node.setLayoutX(41);
                            m.get(temp - 1).setLayoutX(331);
                            techPane.getChildren().add(node);
                        } else if (temp % 2 == 0) {
                            node.setLayoutY(layoutY);
                            node.setLayoutX(447);
                            m.get(temp - 1).setLayoutX(737);
                            techPane.getChildren().add(node);
                        } else if (temp != 0) {
                            layoutY += 94;
                            techPane.setPrefHeight(techPane.getPrefHeight() + 94);
                            node.setLayoutY(layoutY);
                            node.setLayoutX(41);
                            m.get(temp - 1).setLayoutX(331);
                            description.setLayoutY(description.getLayoutY() + 94);
                            ajouterButton.setLayoutY(ajouterButton.getLayoutY() + 94);
                            techPane.getChildren().add(node);
                        }
                        m.get(temp - 1).setLayoutY(layoutY);
                        techPane.getChildren().add(m.get(temp - 1));
                    });
                }
                temp = 0;
                nbrTechs--;
                m.clear();
                techs.clear();
                ev.consume();
            });
            techPane.getChildren().add(moins);
            event.consume();
        });
        List<String> techsToSend = new ArrayList<>();
        ajouterButton.setOnAction((ActionEvent d) -> {
            Platform.runLater(() -> {
                techPane.getChildren().forEach(te -> {
                    if (te instanceof JFXTextField) {
                        techsToSend.add(((JFXTextField) te).getText());
                    }
                });
                techsToSend.add(technologie.getText());
                try {
                    OffreServices.insertOffre(techsToSend, titre.getText(), description.getText(), Integer.parseInt(duree.getText()), Integer.parseInt(nbrEtudiants.getText()));
                } catch (SQLException ex) {
                    //  Logger.getLogger(edu.gestionpfe.controllers.AjouterOffreController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            AnchorPane node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Offres/OffresEntreprise.fxml"));
                            containerP.getChildren().setAll(node);

                        } catch (IOException ex) {
                            Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
            });
        });
        nbrEtudiants.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                nbrEtudiants.setText(oldValue);

            }
        });
        duree.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                duree.setText(oldValue);

            }
        });
        ajouterButton.disableProperty().bind(Bindings.createBooleanBinding(()
                -> duree.getText().trim().isEmpty(),
                duree.textProperty()));
        ajouterButton.disableProperty().bind(Bindings.createBooleanBinding(()
                -> nbrEtudiants.getText().trim().isEmpty(),
                nbrEtudiants.textProperty()));
        ajouterButton.disableProperty().bind(Bindings.createBooleanBinding(()
                -> titre.getText().trim().isEmpty(),
                titre.textProperty()));
        ajouterButton.disableProperty().bind(Bindings.createBooleanBinding(()
                -> technologie.getText().trim().isEmpty(),
                technologie.textProperty()));
        ajouterButton.disableProperty().bind(Bindings.createBooleanBinding(()
                -> description.getText().trim().isEmpty(),
                description.textProperty()));
    }
}
