/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static edu.gestionpfe.controllers.OffresEntrepriseController.aa;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.Technologie;
import edu.gestionpfe.services.OffreServices;
import edu.gestionpfe.services.TechnologiesServices;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class EditOffreController implements Initializable {

    @FXML
    private JFXTextField titre;
    @FXML
    private JFXTextField nbrDemandes;
    @FXML
    private JFXTextField duree;
    public static int idOffre;
    public static int layouY = 22;
    public static int layouX = 339;

    private int textFieldsAddedNumber = 0;
    @FXML
    private AnchorPane myAnchor;
    @FXML
    private AnchorPane techPane;
    private int temp = 0;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton valider;

    private void reArrange(List<JFXTextField> techs, List<ImageView> m) {
        description.setLayoutY(321);
        valider.setLayoutY(461);
        techPane.getChildren().clear();
        techPane.setPrefHeight(101);
        if (!techs.isEmpty()) {

            techs.forEach((node) -> {
                temp++;
                if (temp == 1) {
                    layouY = 22;
                    node.setLayoutY(layouY);
                    node.setLayoutX(20);
                    description.setLayoutY(description.getLayoutY() + 68);
                    valider.setLayoutY(valider.getLayoutY() + 68);
                    m.get(temp - 1).setLayoutX(270);
                    techPane.getChildren().add(node);
                } else if (temp % 2 == 0) {
                    node.setLayoutY(layouY);
                    node.setLayoutX(339);
                    m.get(temp - 1).setLayoutX(339 + 250);
                    techPane.getChildren().add(node);
                } else if (temp != 0) {
                    layouY += 68;

                    techPane.setPrefHeight(techPane.getPrefHeight() + 68);
                    description.setLayoutY(description.getLayoutY() + 68);
                    valider.setLayoutY(valider.getLayoutY() + 68);

                    node.setLayoutY(layouY);
                    node.setLayoutX(20);
                    m.get(temp - 1).setLayoutX(270);
                    techPane.getChildren().add(node);
                }
                m.get(temp - 1).setLayoutY(layouY);
                techPane.getChildren().add(m.get(temp - 1));
            });
        }

        temp = 0;
        textFieldsAddedNumber--;
        m.clear();
        techs.clear();

    }

    private void afficherTechs(Technologie tech) {
        System.out.println(tech);
        textFieldsAddedNumber++;

        //  myAnchor.getChildren().add(techField);
        if (textFieldsAddedNumber == 1) {
            JFXTextField techField = new JFXTextField();
            techField.setLabelFloat(true);
            techField.setPromptText("Technologie");
            techField.setText(tech.getTech());
            techField.setDisable(false);
            techField.setStyle("-fx-background-color: #fff;");
            techField.setOpacity(0.9);
            Image plusImage = new Image("/edu/gestionpfe/views/images/plus.png");
            Tooltip t = new Tooltip("Ajouter des champ pour plus de technologies");
            ImageView plus = new ImageView(plusImage);
            Tooltip.install(plus, t);
            plus.setFitHeight(36);
            plus.setFitWidth(40);
            techField.setLayoutX(339);
            techField.setLayoutY(122);
            layouX = 20;
            plus.setLayoutX(564);
            plus.setLayoutY(127);
            plus.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                Platform.runLater(() -> {
                    textFieldsAddedNumber++;

                    ImageView minus = new ImageView("/edu/gestionpfe/views/images/minus.png");
                    JFXTextField techFieldd = new JFXTextField();
                    techFieldd.setLabelFloat(true);
                    techFieldd.setPromptText("Technologie");
                    techFieldd.setStyle("-fx-background-color: #fff;");
                    techFieldd.setOpacity(0.9);

                    minus.setFitHeight(36);
                    minus.setFitWidth(40);

                    minus.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) -> {
                        Platform.runLater(() -> {
                            List<JFXTextField> techs = new ArrayList<>();
                            List<ImageView> m = new ArrayList<>();
                            techFieldd.setVisible(false);
                            minus.setVisible(false);
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
                            reArrange(techs, m);
                        });
                    });
                    techPane.getChildren().add(minus);
                    techPane.getChildren().add(techFieldd);
                    List<JFXTextField> techs = new ArrayList<>();
                    List<ImageView> m = new ArrayList<>();
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
                    reArrange(techs, m);

                });

            });

            myAnchor.getChildren().add(plus);
            myAnchor.getChildren().add(techField);
        } else {
            JFXTextField techField = new JFXTextField();
            techField.setLabelFloat(true);
            techField.setPromptText("Technologie");
            techField.setText(tech.getTech());
            techField.setDisable(false);
            techField.setStyle("-fx-background-color: #fff;");
            techField.setOpacity(0.9);
            ImageView minus = new ImageView("/edu/gestionpfe/views/images/minus.png");
            minus.setFitHeight(36);
            minus.setFitWidth(40);
            minus.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev) -> {
                Platform.runLater(() -> {
                    List<JFXTextField> techs = new ArrayList<>();
                    List<ImageView> m = new ArrayList<>();
                    techField.setVisible(false);
                    minus.setVisible(false);
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
                    reArrange(techs, m);
                });
            });
            List<JFXTextField> techs = new ArrayList<>();
            List<ImageView> m = new ArrayList<>();

            techPane.getChildren().add(minus);
            techPane.getChildren().add(techField);
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
            reArrange(techs, m);

        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    myAnchor.setStyle(
                            "-fx-background-image: url("
                            + "'/edu/gestionpfe/views/images/backgrounds/cource-search-bg.jpg'"
                            + "); "
                            + "-fx-background-size: cover;"
                            + "-fx-background-radius: 10;"
                    );
                    titre.setStyle("-fx-background-color: #fff;");
                    duree.setStyle("-fx-background-color: #fff;");
                    nbrDemandes.setStyle("-fx-background-color: #fff;");
                    description.setStyle("-fx-background-color: #fff;");
                    titre.setDisable(false);
                    duree.setDisable(false);
                    nbrDemandes.setDisable(false);
                    nbrDemandes.setOpacity(0.9);
                    description.setOpacity(0.9);
                    duree.setOpacity(0.9);
                    titre.setOpacity(0.9);
                    Offre offre = OffreServices.getOffreById(idOffre);
                    titre.setPromptText("Titre du stage");
                    titre.setText(offre.getTitre());
                    description.setText(offre.getDescription());
                    nbrDemandes.setPromptText("Nombre d'etudiants demandées");
                    nbrDemandes.setText(String.valueOf(offre.getNbr_demandes()));
                    duree.setPromptText("Durée du stage");
                    duree.setText(String.valueOf(offre.getDuree()));
                    List<Technologie> technologies;
                    technologies = TechnologiesServices.selectTechnologies(offre);
                    technologies.stream().forEach((Technologie tech) -> {
                        afficherTechs(tech);
                    });

                    //idOffre = 0;
                } catch (Exception ew) {
                    System.out.println(ew.getMessage());
                }
            }
        });

    }

    @FXML
    private void validerAction(ActionEvent event) {
        try {
            Offre offre = OffreServices.getOffreById(idOffre);
            List<String> technologies = new ArrayList<>();

            myAnchor.getChildren().forEach(te -> {
                if (te instanceof JFXTextField) {
                    if (((JFXTextField) te).getPromptText().equals("Nombre d'etudiants demandées")) {
                        if (!((JFXTextField) te).getText().trim().equals("")) {
                            offre.setNbr_demandes(Integer.parseInt(((JFXTextField) te).getText()));
                        }
                    }
                    if (((JFXTextField) te).getPromptText().equals("Titre du stage")) {
                        if (!((JFXTextField) te).getText().trim().equals("")) {
                            offre.setTitre((((JFXTextField) te).getText()));
                        }
                    }
                    if (((JFXTextField) te).getPromptText().equals("Durée du stage")) {
                        if (!((JFXTextField) te).getText().trim().equals("")) {
                            offre.setDuree(Integer.parseInt(((JFXTextField) te).getText()));
                        }
                    }

                    if (((JFXTextField) te).getPromptText().equals("Technologie")) {
                        if (!((JFXTextField) te).getText().trim().equals("")) {

                            technologies.add(((JFXTextField) te).getText());
                        }
                    }
                }
            });
            techPane.getChildren().forEach(te -> {
                if (te instanceof JFXTextField) {

                    if (!((JFXTextField) te).getText().trim().equals("")) {

                        technologies.add(((JFXTextField) te).getText());
                    }
                }
            });
            offre.setDescription(description.getText());
            OffreServices.updateOffre(technologies, offre);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Votre Offre à été modifiée avec succées", ButtonType.OK);
            a.showAndWait();
            Stage currentstage = (Stage) description.getScene().getWindow();
            aa.setText("ok");
            currentstage.close();
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

    }

}
