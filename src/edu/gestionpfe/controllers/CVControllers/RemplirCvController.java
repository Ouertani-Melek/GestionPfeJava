/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.CVControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import static edu.gestionpfe.controllers.UserIndexController.containerP;
import edu.gestionpfe.models.CentresInterets;
import edu.gestionpfe.models.CompetencesTechniques;
import edu.gestionpfe.models.Cv;
import edu.gestionpfe.models.Formation;
import edu.gestionpfe.models.Langues;
import edu.gestionpfe.services.CentresInteretsServices;
import edu.gestionpfe.services.CompetencesTechniquesServices;
import edu.gestionpfe.services.CvServices;
import edu.gestionpfe.services.FormationServices;
import edu.gestionpfe.services.languesServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class RemplirCvController implements Initializable {
    
    @FXML
    private AnchorPane remplir;
    @FXML
    private AnchorPane formationFields;
    @FXML
    private JFXButton plus;
    
    private static ArrayList<JFXTextField> AllFormations;
    private static ArrayList<String> FormationsContent;
    private static ArrayList<JFXDatePicker> AllFormationDates;
    
    private static ArrayList<JFXComboBox> AllLangues;
    private static ArrayList<JFXComboBox> AllNiveauLan;
    private static ArrayList<Langues> LanguesContent;
    private static ArrayList<JFXTextField> AllTech;
    
    private static ArrayList<JFXComboBox> AllLevelTech;
    private static ArrayList<CompetencesTechniques> TechContent;
    
    private static ArrayList<JFXComboBox> AllCentres;
    private static ArrayList<CentresInterets> CentreContent;
    
    static int i = 0;
    static int j = 0;
    static int l = 0;
    static int n = 0;
    static int k = 0;
    static int t = 0;
    static int y = 0;
    static int z = 0;
    static boolean verif;
    static boolean verifcentre;
    static boolean veriflangue;
    static boolean veriftech;
    
    public static String[] possibleSuggestions = {
        "ActionScript",
        "AppleScript",
        "Asp",
        "BASIC",
        "C",
        "C++",
        "Clojure",
        "COBOL",
        "ColdFusion",
        "Erlang",
        "Fortran",
        "Groovy",
        "Haskell",
        "Java",
        "JavaScript",
        "Lisp",
        "Perl",
        "PHP",
        "Python",
        "Ruby",
        "Scala",
        "Scheme"
    };
    public static String[] langues = {
        "arabe",
        "francais",
        "Anglais",
        "Allmand",
        "chinoise",
        "japponaise"
    
    };
     public static String[] niveaux = {
        "A1",
        "A2",
        "B1",
        "B2",
        "C1",
        "C2"
    
    };
     public static String[] CentresInterets = {
        "Activite sportive collective",
        "Activite artistique",
        "Activite culturelle",
        "Activite musicale"
    
    };
    public static  String[] TechLevel = {
        "Faible",
        "peu de connaissance",
        "Bien",
        "Excellent"
    
    };
    
    @FXML
    private ScrollPane scrollin;
    @FXML
    private JFXTextField formation;
    @FXML
    private JFXComboBox<String> langue;
    @FXML
    private JFXComboBox<String> niveau;
    @FXML
    private JFXButton btnLangue;
    @FXML
    private AnchorPane langueFields;
    @FXML
    private JFXTextField tech;
    @FXML
    private JFXButton btnTech;
    @FXML
    private AnchorPane techFields;
    @FXML
    private AnchorPane centresFields;
    @FXML
    private JFXButton confirmer;
    @FXML
    private JFXComboBox<String> centres;
    @FXML
    private JFXButton btnCentres;
    @FXML
    private JFXComboBox<String> level;
    @FXML
    private JFXDatePicker dateFormation;
    
    private JFXTextField formation1;
    private JFXDatePicker DateFormation1;
    private JFXTextField technologie1;
    private JFXComboBox<String> technologieLevel1;
    private JFXComboBox<String> langue1;
    private JFXComboBox<String> langueniv1;
    private JFXComboBox<String> centre1;
    @FXML
    private static Label wrongInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Tooltip tp = new Tooltip("Ajouter ces informations dans votre CV");
            Tooltip.install(confirmer, tp);
        AllFormations = new ArrayList<>();
        AllLangues = new ArrayList<>();
        AllCentres = new ArrayList<>();
        AllTech = new ArrayList<>();
        AllFormationDates = new ArrayList<>();
        AllLevelTech = new ArrayList<>();
        AllNiveauLan = new ArrayList<>();
        dateFormation.setDayCellFactory((picker) -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        //formations
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        i = i + 1;
                        
                        formation1 = new JFXTextField();
                        DateFormation1 = new JFXDatePicker();
                        DateFormation1.setDayCellFactory((picker) -> new DateCell() {
                            public void updateItem(LocalDate date, boolean empty) {
                                super.updateItem(date, empty);
                                LocalDate today = LocalDate.now();
                                
                                setDisable(empty || date.compareTo(today) > 0);
                            }
                        });
                        formation1.setId("f" + i);

                        //  System.out.println(formation.getId());
                        formation1.setLayoutX(4);
                        formation1.setLayoutY(j);
                        formation1.setPrefWidth(256);
                        formation1.setPrefHeight(29);
                        formation1.setPromptText("Formation");
                        formation1.setStyle("-fx-background-color: #fff");
                        formation1.setFocusColor(Paint.valueOf("#9eca51"));
                        
                        formation1.setLabelFloat(true);
                        
                        DateFormation1.setId("date" + i);
                        DateFormation1.setLayoutX(298);
                        DateFormation1.setLayoutY(j);
                        DateFormation1.setPrefWidth(243);
                        DateFormation1.setPrefHeight(29);
                        DateFormation1.setPromptText("Date de l'obtenation du diplome");
//                        dateFormation.setStyle("-fx-background-color: #fff");
                        DateFormation1.setEditable(false);
//                        dateFormation.setOverLay(true);
                        DateFormation1.setDefaultColor(Paint.valueOf("#9eca51"));
                        
                        j = j + 50;
                        
                        formationFields.getChildren().add(formation1);
                        formationFields.getChildren().add(DateFormation1);
                        AllFormations.add(formation1);
                        AllFormationDates.add(DateFormation1);
                        
                    }
                });
            }
        });
        //langues
        langue.getItems().addAll(langues);
        niveau.getItems().addAll(niveaux);
        btnLangue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        l = l + 1;
                        
                        langue1 = new JFXComboBox();
                        langueniv1 = new JFXComboBox();
                        langue1.getItems().addAll(langues);
                        langueniv1.getItems().addAll(niveaux);
                        langue1.setId("l" + l);
                        langue1.setLayoutX(4);
                        langue1.setLayoutY(n);
                        langue1.setPrefWidth(256);
                        langue1.setPrefHeight(29);
                        langue1.setPromptText("langue");
                        langue1.setStyle("-fx-background-color: #fff");
                        langue1.setFocusColor(Paint.valueOf("#9eca51"));
                        langue1.setEditable(false);
                        langue1.setLabelFloat(true);
                        
                        langueniv1.setId("niveau" + i);
                        langueniv1.setLayoutX(298);
                        langueniv1.setLayoutY(n);
                        langueniv1.setPrefWidth(243);
                        langueniv1.setPrefHeight(29);
                        langueniv1.setPromptText("niveau");
                        langueniv1.setStyle("-fx-background-color: #fff");
                        langueniv1.setEditable(false);
                        langueniv1.setFocusColor(Paint.valueOf("#9eca51"));
                        langueniv1.setLabelFloat(true);
                        
                        n = n + 50;
                        
                        langueFields.getChildren().add(langue1);
                        langueFields.getChildren().add(langueniv1);
                        AllLangues.add(langue1);
                        AllNiveauLan.add(langueniv1);
                        
                    }
                });
            }
        });
        //centres
        centres.getItems().addAll(CentresInterets);
        
        btnCentres.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        y = y + 1;
                        
                        centre1 = new JFXComboBox();
                        centre1.getItems().addAll(CentresInterets);
                        centre1.setId("c" + y);
                        centre1.setLayoutX(4);
                        centre1.setLayoutY(z);
                        centre1.setPrefWidth(256);
                        centre1.setPrefHeight(29);
                        centre1.setPromptText("centres d'interets");
                        centre1.setStyle("-fx-background-color: #fff");
                        centre1.setFocusColor(Paint.valueOf("#9eca51"));
                        centre1.setEditable(false);
                        centre1.setLabelFloat(true);
                        
                        z = z + 50;
                        
                        centresFields.getChildren().add(centre1);
                        AllCentres.add(centre1);
                        
                    }
                });
            }
        });
        //technologies

        TextFields.bindAutoCompletion(tech, possibleSuggestions);
        level.getItems().addAll(TechLevel);
        btnTech.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        k = k + 1;
                        
                        technologie1 = new JFXTextField();
                        technologieLevel1 = new JFXComboBox();
                        TextFields.bindAutoCompletion(technologie1, possibleSuggestions);
                        technologieLevel1.getItems().addAll(TechLevel);
                        technologie1.setId("t" + k);
                        technologie1.setLayoutX(4);
                        technologie1.setLayoutY(t);
                        technologie1.setPrefWidth(256);
                        technologie1.setPrefHeight(29);
                        technologie1.setPromptText("language");
                        technologie1.setStyle("-fx-background-color: #fff");
                        technologie1.setFocusColor(Paint.valueOf("#9eca51"));
                        
                        technologie1.setLabelFloat(true);
                        
                        technologieLevel1.setId("level" + k);
                        technologieLevel1.setLayoutX(298);
                        technologieLevel1.setLayoutY(t);
                        technologieLevel1.setPrefWidth(256);
                        technologieLevel1.setPrefHeight(29);
                        technologieLevel1.setPromptText("Estimation de niveau");
                        technologieLevel1.setStyle("-fx-background-color: #fff");
                        technologieLevel1.setFocusColor(Paint.valueOf("#9eca51"));
                        
                        technologieLevel1.setLabelFloat(true);
                        
                        t = t + 50;
                        
                        techFields.getChildren().add(technologie1);
                        techFields.getChildren().add(technologieLevel1);
                        AllLevelTech.add(technologieLevel1);
                        AllTech.add(technologie1);
                        
                    }
                });
            }
        });
    }
    
    public static boolean isValidName(String name) {
        String specialCharacters = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
        String str2[] = name.split("");
        
        int count = 0;
        for (int i = 0; i < str2.length; i++) {
            if (specialCharacters.contains(str2[i])) {
                count++;
            }
        }
        
        
        if (name != null && count == 0&&!name.endsWith(" ")) {
            return true;
        } else {
            return false;
        }
    }
    
    @FXML
    private void CreerCVAction(ActionEvent event) throws IOException {
        
        CentreContent = new ArrayList<>();
        TechContent = new ArrayList<>();
        
        FormationsContent = new ArrayList<>();
        LanguesContent = new ArrayList<>();
        
        CvServices services = new CvServices();
        FormationServices forma = new FormationServices();
        CompetencesTechniquesServices techs = new CompetencesTechniquesServices();
        CentresInteretsServices centresInt = new CentresInteretsServices();
        languesServices languesA = new languesServices();
        
        try {
            
            verif = FormationsContent.stream().anyMatch(e -> e.equalsIgnoreCase(formation.getText()));
            Cv cv1 = new Cv();
            cv1.setIdUser(usr.getId());
            Formation format = new Formation();
            Formation format1994 = new Formation();
            CompetencesTechniques ctech = new CompetencesTechniques();
            CompetencesTechniques ctech1994 = new CompetencesTechniques();
            Langues lan = new Langues();
            Langues lan1 = new Langues();
            
            CentresInterets cen = new CentresInterets();
            CentresInterets cen1 = new CentresInterets();
            if (formation.getText() != "" && isValidName(formation.getText()) && dateFormation.getValue() != null
                    && centres.getValue() != null && tech.getText() != null && level.getValue() != null
                    && langue.getValue() != null && niveau.getValue() != null ) {
                
                services.ajouterCv(cv1);
                
                format.setIdCv(CvServices.AddedId);
                
                format.setFormation(formation.getText());
                format.setAnnee(dateFormation.getValue());
                if (i == 0) {
                    forma.ajouterFormationCv(format);
                    System.out.println("khlat tawa ");
                }
                
                FormationsContent.add(formation.getText());
                forma.ajouterFormationCv(format);
                
                int iter = 0;
                //  for (int iter = 0; iter <= i; iter++) {
                while (iter <= i) {
                    
                    format1994.setIdCv(CvServices.AddedId);
                    String id = "f" + Integer.toString(iter);
                    String dat = "date" + Integer.toString(iter);
                    
                    for (Node a : formationFields.getChildren()) {
                        if (a instanceof JFXTextField) {
                            for (JFXTextField f : AllFormations) {
                                verif = FormationsContent.stream().anyMatch(e -> e.equalsIgnoreCase(f.getText()));
                                if (f.getText() != null && verif == false) {
                                    if (isValidName(f.getText())) {
                                        
                                        format1994.setFormation(formation1.getText());
                                        FormationsContent.add(formation1.getText());
                                        
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "de caracteres special sont detectés au niveau des formations saisis!! ou une formation a éte deja saisi ", ButtonType.OK);
                                        alert.initStyle(StageStyle.DECORATED);
                                        services.supprimerCv(CvServices.AddedId);
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            f.clear();
                                            verif = false;
                                        }
                                        
                                    }
                                }
                                
                            }
                            
                        }                        
                        for (JFXDatePicker d : AllFormationDates) {
                            
                            if (!d.getValue().equals("")) {
                                System.out.println(d.getValue());
                                
                                format1994.setAnnee(DateFormation1.getValue());
                                
                            }
                        }
                        
                        if (verif == false) {
                            System.out.println(DateFormation1.getValue());
                            forma.ajouterFormationCv(format1994);
                            
                        }
                        
                        break;
                        
                    }
                    
                    iter++;
                    
                }
                ctech.setCompetence(tech.getText());
                ctech.setIdCv(CvServices.AddedId);
                ctech.setLevel(level.getValue());
                
                if (k == 0) {
                    TechContent.add(ctech);
                    veriftech = false;
                }
                if (k != 0) {
                    TechContent.add(ctech);
                    for (JFXTextField xli : AllTech) {
                        veriftech = TechContent.stream().anyMatch(e -> e.getCompetence().equalsIgnoreCase(xli.getText()));
                        if (veriftech == false) {
                            
                            veriftech = false;
                            ctech1994.setIdCv(CvServices.AddedId);
                            ctech1994.setCompetence(xli.getText());
                            ctech1994.setLevel(technologieLevel1.getValue());
                            if (isValidName(xli.getText()) == true) {
                                TechContent.add(ctech1994);
                            }                            
                        } else {
                            System.out.println("erreur win mechy!!!!!!!!!!!");
                            Alert alert = new Alert(Alert.AlertType.ERROR, "langue redondant !! ", ButtonType.OK);
                            alert.initStyle(StageStyle.DECORATED);
                            services.supprimerCv(CvServices.AddedId);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                
                                technologie1.clear();
                                veriftech = false;
                            }
                            services.supprimerCv(CvServices.AddedId);
                            
                            break;
                            
                        }
                    }
                }
                if (veriftech == false) {
                    techs.ajouterCompetenceCv(TechContent);
                }
                //languessssss
                lan.setLangue(langue.getValue());
                lan.setIdCv(CvServices.AddedId);
                lan.setNiveau(niveau.getValue());
                
                if (l == 0) {
                    LanguesContent.add(lan);
                    
                    veriflangue = false;
                }
                if (l != 0) {
                    LanguesContent.add(lan);
                    for (JFXComboBox xl : AllLangues) {
                        
                        veriflangue = LanguesContent.stream().anyMatch(e -> e.getLangue().equalsIgnoreCase((String) xl.getValue()));
                        if (veriflangue == false) {
                            
                            verifcentre = false;
                            lan1.setIdCv(CvServices.AddedId);
                            lan1.setLangue((String) xl.getValue());
                            lan1.setNiveau(langueniv1.getValue());
                            if (xl.getValue() != null) {
                                LanguesContent.add(lan1);
                            }                            
                        } else {
                            System.out.println("erreur win mechy!!!!!!!!!!!");
                            Alert alert = new Alert(Alert.AlertType.ERROR, "langue redondant !! ", ButtonType.OK);
                            alert.initStyle(StageStyle.DECORATED);
                            services.supprimerCv(CvServices.AddedId);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                
                                langue1.getItems().remove(xl.getValue());
                                veriflangue = false;
                            }
                            services.supprimerCv(CvServices.AddedId);
                            
                            break;
                            
                        }
                    }
                }
                if (veriflangue == false) {
                    languesA.ajouterLangue(LanguesContent);
                }
                //centreee
                
                cen.setCentreInteret(centres.getValue());
                cen.setIdCv(CvServices.AddedId);
                
                if (y == 0) {
                    CentreContent.add(cen);
                    
                    verifcentre = false;
                    
                }
                if (y != 0) {
                    CentreContent.add(cen);
                    for (JFXComboBox x : AllCentres) {
                        verifcentre = CentreContent.stream().anyMatch(e -> e.getCentreInteret().equalsIgnoreCase((String) x.getValue()));
                        if (verifcentre == false) {
                            
                            verifcentre = false;
                            cen1.setIdCv(CvServices.AddedId);
                            cen1.setCentreInteret((String) x.getValue());
                            if (x.getValue() != null) {
                                CentreContent.add(cen1);
                            }                            
                        } else {
                            
                            System.out.println("erreur win mechy!!!!!!!!!!!");
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Centres d'interets redondant !! ", ButtonType.OK);
                            alert.initStyle(StageStyle.DECORATED);
                            services.supprimerCv(CvServices.AddedId);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                
                                centre1.getItems().remove(x.getValue());
                                
                                verif = false;
                            }
                            services.supprimerCv(CvServices.AddedId);
                            
                            break;
                            
                        }
                    }
                }
                if (verifcentre == false) {
                    centresInt.ajouterCentre(CentreContent);
                    System.out.println("wsolt hne");

//                   CentreContent.stream().forEach(e-> System.out.println(e->get));
                }
                if( verif==true&& verifcentre==true&&veriflangue==true
                            
                            &&veriftech==true)
                {
                Node node = null;
                
                node = (StackPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Cv/afficherCv.fxml"));
                FadeTransition ft = new FadeTransition(Duration.seconds(5), node);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
                
                containerP.getChildren().setAll(node);
                TrayNotification tray = new TrayNotification();
                 tray.setTitle("Gestion PFE Ajout CV");
            tray.setMessage("Voir Votre Cv !");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(5));
                }
                if(verif=true)
                {
                Node node = null;
                
                node = (StackPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Cv/afficherCv.fxml"));
                FadeTransition ft = new FadeTransition(Duration.seconds(5), node);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
                
                containerP.getChildren().setAll(node);
                 TrayNotification tray = new TrayNotification();
                 tray.setTitle("Gestion PFE Ajout CV");
            tray.setMessage("Voir Votre Cv !");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(5));
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vous devez remplir au moins un des champs pour chaque categories! ", ButtonType.OK);
                alert.initStyle(StageStyle.UNDECORATED);
                 TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Ajout CV");
            tray.setMessage("Essayez Encore et cliquer sur Mon Cv aprés!");
            tray.setNotificationType(NotificationType.ERROR);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("probleme" + ex.getMessage());
        }
    }
    
}
