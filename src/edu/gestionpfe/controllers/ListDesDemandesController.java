/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import static edu.gestionpfe.controllers.RemplirCvController.verif;
import edu.gestionpfe.models.Demandes;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.CvServices;
import edu.gestionpfe.services.DemandesServices;
import edu.gestionpfe.services.UserServices;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
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
 * @author yahia
 */
public class ListDesDemandesController implements Initializable {

    @FXML
    private Pagination pagination;
  
    private int DemandeNbr = 0;
    private int layoutX = 10;
    private int layoutY = 10;
    private String concatinatedTechs = "";
    @FXML
    private AnchorPane myAnchor;
    
    public  static AnchorPane myAnchorP;
    
    
    private void afficherDem(Demandes o) throws Exception {
        try {
            Pane p = new Pane();
            p.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            DemandeNbr++;
            ImageView img;
            User usr = UserServices.selectUserById(o.getIdUser());
            img = UserServices.getUserImage(usr);
            p.setPrefSize(180, 250);
            Button b = new Button("Actions");
            b.setLayoutX(50);
            b.setLayoutY(80);
            b.setVisible(false);
            Image image = img.getImage();
            Circle c = new Circle(70, 70, 70);
            c.setFill(new ImagePattern(image));
//            Label titre = new Label();
//            String titreS = o.getTitre();
//            titre.setText(titreS);
//            if (o.getTitre().length() > 20) {
//                titreS = o.getTitre().substring(0, Math.min(o.getTitre().length(), 20));
//                titre.setText(titreS + "...");
//            }
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
                        AnchorPane pane = new AnchorPane();
                        pane.setOpacity(0.95);
//                        pane.setStyle(
//                                "-fx-background-color: #696969;"
//                                + "-fx-background-image: url("
//                                + "'/edu/gestionpfe/views/images/backgrounds/cource-search-bg.jpg'"
//                                + "); "
//                                + "-fx-background-size: cover;"
//                                + "-fx-background-radius: 10;"
//                        );

                        Pane pa = new Pane();
                        pa.setPrefSize(800, 30);
                        pa.setStyle("-fx-background-color: #00FF00");
                        Image imageD = img.getImage();
                        Circle cD = new Circle(70, 70, 70);
                        cD.setLayoutX(20);
                        cD.setLayoutY(30);
                        cD.setFill(new ImagePattern(imageD));

//                        TechnologiesServices.selectTechnologies(o).stream().forEach(a -> {
//                            concatinatedTechs += a.getTech() + "\n";
//                        });
//                        Label description = new Label();
//                        description.setPrefWidth(215);
//                        description.setWrapText(true);
//                        description.setText("Description générale :\n" + o.getDescription() + "\n\n\nTechnologies Demandées : \n" + concatinatedTechs);
//                        concatinatedTechs = "";
//                        description.setLayoutX(420);
//                        description.setLayoutY(40);
//                        description.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");                        
                        JFXButton postuler = new JFXButton("Accepter ");
                        postuler.setLayoutX(650);
                        postuler.setLayoutY(370);
                        postuler.setStyle("-fx-background-color: #00FF00");
                        JFXDatePicker dateentre=new JFXDatePicker();
                        
                        dateentre.setLayoutX(450);
                        dateentre.setLayoutY(40);
                        dateentre.setPromptText("Date Entretien");
                        dateentre.setEditable(false);
                        JFXComboBox<String> methodeCom=new JFXComboBox<>();
                        methodeCom.setLayoutX(450);
                        methodeCom.setLayoutY(70);
                        methodeCom.getItems().addAll("Viber","Skype","Reunion live");
                        methodeCom.setPromptText("methode");
                       
//                        Label titreD = new Label();
//                        titreD.setText("Titre du stage : " + o.getTitre());
//                        titreD.setLayoutX(20);
//                        titreD.setLayoutY(190);
//                        titreD.setOpacity(5);
//                        titreD.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
                       Label nomEntreprise = new Label();
                        nomEntreprise.setText("Nom de l'entreprise : " + usr.getNom());
                        nomEntreprise.setLayoutX(20);
                        nomEntreprise.setLayoutY(210);
                        nomEntreprise.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
                       
                        
                        //pane.getChildren().add(titreD);
                        pane.getChildren().add(pa);
                       // pane.getChildren().add(description);
                        pane.getChildren().add(cD);
                        pane.getChildren().add(nomEntreprise);
                        pane.getChildren().add(dateentre);
                        pane.getChildren().add(methodeCom);
                        pane.getChildren().add(postuler);
                        
                        Stage dialog = new Stage();
                        dialog.initOwner((Stage) myAnchor.getScene().getWindow());
                        dialog.initModality(Modality.APPLICATION_MODAL);
                       // dialog.setTitle(o.getTitre());
                        Scene scene = new Scene(pane, 800, 400);
                        dialog.setScene(scene);
                        //dialog.initStyle(StageStyle.TRANSPARENT);
                        dialog.show();
                        b.setVisible(false);
                         postuler.setOnAction((ActionEvent d) -> {
                            Platform.runLater(() -> {
                                try {
                                    DemandesServices newsx=new DemandesServices();
                                    
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Le RDV  sera le  "+dateentre.getValue() , ButtonType.OK,ButtonType.NO);
                                        alert.initStyle(StageStyle.DECORATED);
                                        
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            newsx.ajouterEntretien(o.getId(),dateentre.getValue());
                                            dialog.close();
                                            p.setVisible(false);
                                           
                                        }
                                } catch (SQLException ex) {
                                    Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            });
                        });

                    } catch (Exception ex) {
                        Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
//            titre.setLayoutX(5);
//            titre.setLayoutY(160);
//            p.getChildren().add(titre);
            p.getChildren().add(c);
            p.getChildren().add(b);
            if (DemandeNbr == 1) {
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY);
            }
            if (DemandeNbr == 2) {
                layoutX += 250;
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY);
            }
            if (DemandeNbr == 3) {
                DemandeNbr = 0;
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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        myAnchorP=myAnchor;
        DemandesServices dem = new DemandesServices();
        List<Demandes> ses=new ArrayList<>();
        ses=dem.AfficherDemande();
        ses.stream().forEach(d->{
            try {
                afficherDem(d);
                System.out.println(d.getId());
            } catch (Exception ex) {
                Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });



    }
    ///pagination
    public List<Demandes> getTableData()
{
       List<Demandes>data=new ArrayList<>(); 
       return data;
}
}
