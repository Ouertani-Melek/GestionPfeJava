/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.CVControllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.isValidName;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.langues;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.niveaux;
import static edu.gestionpfe.controllers.CVControllers.RemplirCvController.possibleSuggestions;
import edu.gestionpfe.models.CentresInterets;
import edu.gestionpfe.models.CompetencesTechniques;
import edu.gestionpfe.models.Formation;
import edu.gestionpfe.models.Langues;
import edu.gestionpfe.services.CentresInteretsServices;
import edu.gestionpfe.services.CompetencesTechniquesServices;
import edu.gestionpfe.services.CvServices;
import edu.gestionpfe.services.FormationServices;
import edu.gestionpfe.services.languesServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DateCell;
import javafx.scene.control.ScrollPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class AfficherCvController implements Initializable {

    @FXML
    private JFXTabPane mainTabPane;
    @FXML
    private Tab bookIssueTab;
    @FXML
    private HBox book_info;
    @FXML
    private StackPane bookInfoContainer;
    @FXML
    private HBox member_info;
    @FXML
    private JFXTextField memberIDInput;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Text AdresseFull;
    @FXML
    private Text NumerTelUser;
    @FXML
    private Text Universite;
    @FXML
    private Text UserFullName;
    @FXML
    private Text niveau;
    @FXML
    private HBox book_info1;
    @FXML
    private StackPane bookInfoContainer1;
    @FXML
    private Text FormationEtDate;
    @FXML
    private Tab bookIssueTab11;
    @FXML
    private HBox book_info11;
    @FXML
    private StackPane bookInfoContainer11;
    @FXML
    private Text LanguesEtNiveau;
    @FXML
    private Tab bookIssueTab111;
    @FXML
    private HBox member_info111;
    @FXML
    private JFXTextField memberIDInput111;
    @FXML
    private StackPane memberInfoContainer111;
    @FXML
    private Text memberName111;
    @FXML
    private Text memberMobile111;
    @FXML
    private Tab bookIssueTab2;
    @FXML
    private HBox book_info2;
    @FXML
    private StackPane bookInfoContainer2;
    @FXML
    private Text EmailUser;
    @FXML
    private VBox FormationContent;
    @FXML
    private Tab bookIssueTab1;
    @FXML
    private VBox LanguesContent;
    @FXML
    private VBox centresContent;
    @FXML
    private VBox techsContent;
    private Boolean verifForm;
    private String newF;
    @FXML
    private ScrollPane scrollformation;
    @FXML
    private ScrollPane scrollLangue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
//         final ImageView imageView = new ImageView(usr.getImage());
//            ImageUser.setClip(imageView);
scrollLangue.getStyleClass().add("scroll-pane");
scrollformation.getStyleClass().add("scroll-pane");


        UserFullName.setText("Nom et Prénom : " + usr.getPrenom() + " " + usr.getNom());
        niveau.setText("Niveau : 5éme Année ");
        NumerTelUser.setText("Tel : " + usr.getNumTel());
        EmailUser.setText("Email : " + usr.getEmail());
        AdresseFull.setText("Adresse : Tunisie" + "," + usr.getVille() + "," + usr.getRue());
        Universite.setText("Université :  Ecole superieure privée \n d'ingenieurie et technologie (ESPRIT)");
        FormationServices formSer = new FormationServices();
        languesServices Lang = new languesServices();
        CentresInteretsServices cenSer = new CentresInteretsServices();
        CompetencesTechniquesServices techSer = new CompetencesTechniquesServices();
        int IdExistant = 0;

        try {
            IdExistant = CvServices.CVID();
        } catch (SQLException ex) {
            Logger.getLogger(AfficherCvController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(IdExistant);

        int i = 0;
        List<Formation> listeForm = formSer.AfficherFormation(IdExistant);
        List<String> lf = new ArrayList<>();
        listeForm.stream().forEach(e -> lf.add(e.getFormation()));
        for (Formation f : listeForm) {
            i++;

            Label label = new Label("Formation " + i);

            label.getStyleClass().add("main-text");
            Tooltip t = new Tooltip("Activez  pour modifier le champ formation avec 2 clicks ou La date avec 3 clicks");
            Tooltip.install(label, t);
            TextField formation = new TextField(f.getFormation());

            JFXDatePicker formationDate = new JFXDatePicker(f.getAnnee());
            formationDate.setDisable(true);
            formation.setDisable(true);
            formation.setId(label.getText());

            label.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String CurrentVal = f.getFormation();
                        formation.setDisable(false);
                        formation.textProperty().addListener((observable, oldValue, newValue) -> {
//    System.out.println("textfield changed from " + oldValue + " to " + newValue);

                            if (lf.stream().anyMatch(e -> e.equalsIgnoreCase(newValue))) {
                                formation.setStyle("-fx-border-color : #FF0000");
                                label.setText("Formation Existe Deja");
                                label.setStyle("-fx-text-fill :#FF0000");
                                verifForm = true;
                            } else if (!isValidName(newValue)) {
                                formation.setStyle("-fx-border-color : #FF0000");
                                label.setText("Caractéres speciales detectés ");
                                label.setStyle("-fx-text-fill :#FF0000");
                                verifForm = true;
                            } else {

                                label.setText(formation.getId());
                                label.setStyle("-fx-text-fill :#000000");
                                formation.setStyle("-fx-border-color : #0000");
                                verifForm = false;
                                newF = newValue;
                            }
                        });
                        formation.setOnKeyPressed((KeyEvent keyEvent) -> {

                            if (keyEvent.getCode() == KeyCode.ENTER) {

                                //ajouter formation ici
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Vous etes sure de modifier cette formation ?");
                                alert.setContentText("Confirmer ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK && verifForm == false) {
                                    // ... user chose OK

                                    formSer.updateFormation(f.getId(), formation.getText());
                                    System.out.println("confirmer");
                                    formation.setDisable(true);
                                    lf.remove(CurrentVal);
                                    lf.add(newF);

//                                            String musicFile = "12.mp3";     // For example
//
//                                        Media sound = new Media(new File(musicFile).toURI().toString());
//                                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//                                        mediaPlayer.play();
                                } else {
                                    formation.setText(f.getFormation());
                                    formation.setDisable(true);
                                }
                            }
                        });

                    } else if (mouseEvent.getClickCount() == 3) {
                        formationDate.setDisable(false);
                        formationDate.setEditable(false);
                        formation.setDisable(true);
                        formationDate.setDayCellFactory((picker) -> new DateCell() {
                            public void updateItem(LocalDate date, boolean empty) {
                                super.updateItem(date, empty);
                                LocalDate today = LocalDate.now();

                                setDisable(empty || date.compareTo(today) > 0);
                            }
                        });

                        formationDate.valueProperty().addListener((ov, oldValue, newValue) -> {

                            // System.out.println("date changed !"+oldValue +"to  "+newValue);
                            formationDate.setOnKeyPressed((KeyEvent keyEvent) -> {

                                if (keyEvent.getCode() == KeyCode.ENTER) {

                                    //ajouter formation ici
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation");
                                    alert.setHeaderText("Vous etes sure de modifier la date cette formation ?");
                                    alert.setContentText("Confirmer ?");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // ... user chose OK

                                        formSer.updateFormationDate(f.getId(), formationDate.getValue());
                                        System.out.println("confirmer date");
                                        formationDate.setDisable(true);

                                    } else {
                                        formationDate.setValue(f.getAnnee());
                                        formationDate.setDisable(true);
                                    }
                                }

                            });
                        });

                    }
                }
            });
            // formation.setText(String.valueOf(f.getAnnee()));
            formationDate.setId(Integer.toString(f.getId()));
            formationDate.getStyleClass().add("main-text");
            FormationContent.getChildren().addAll(label, formation, formationDate);
            //  FormationContent.getChildren().add(formationDate);
            // texts.add(formationDate);

        }

        int j = 0;
        List<Langues> LesLangues = Lang.AfficherLangue(IdExistant);
        List<String> lg = new ArrayList<>();

        LesLangues.stream().forEach(e -> lg.add(e.getLangue()));
        for (Langues l : LesLangues) {
            j++;
            Label label = new Label("Langue " + j);
            label.getStyleClass().add("main-text");
            Tooltip t = new Tooltip("Activez  pour modifier la langue avec 2 clicks ou le niveau avec 3 clicks");
            Tooltip.install(label, t);
            JFXComboBox<String> langue = new JFXComboBox<>();
            //langue.getStyleClass().add("main-text");
            langue.getStyleClass().add("main-text");

            JFXComboBox<String> niv = new JFXComboBox<>();
           niv.getStyleClass().add("main-text");

            langue.setPromptText(l.getLangue());
            niv.setPromptText(l.getNiveau());
            langue.setDisable(true);

            niv.setDisable(true);
            langue.setId(label.getText());

            langue.getItems().addAll(langues);
            langue.getItems().remove(l.getLangue());
            niv.getItems().addAll(niveaux);

            label.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String CurrentVal = langue.getPromptText();
                        langue.setDisable(false);

//                        String[] str_array = langues;
//List<String> list = new ArrayList<String>(Arrays.asList(str_array));
//list.remove();
//str_array = list.toArray(new String[0]);
//                        
                        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
//    System.out.println("textfield changed from " + oldValue + " to " + newValue);

                            if (lg.stream().anyMatch(e -> e.equalsIgnoreCase(newValue))) {
                                langue.setStyle("-fx-border-color : #FF0000");
                                label.setText("Langue existe deja");
                                label.setStyle("-fx-text-fill :#FF0000");
                                verifForm = true;

                            } else {

                                label.setText(langue.getId());
                                label.setStyle("-fx-text-fill :#000000");
                                langue.setStyle("-fx-border-color : #0000");
                                verifForm = false;

                                newF = newValue;
                            }
                                   langue.setOnKeyPressed((KeyEvent keyEvent) -> {

                            if (keyEvent.getCode() == KeyCode.ENTER) {

                                //ajouter formation ici
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Vous etes sure de modifier cette langue ?");
                                alert.setContentText("Confirmer ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK && verifForm == false) {
                                    // ... user chose OK
                                    if (langue.getValue() != null) {
                                        Lang.updateLangue(l.getId(), langue.getValue());
                                        
                                        System.out.println("confirmer null");
                                        langue.setDisable(true);
                                        lg.remove(CurrentVal);
                                        langue.setPromptText(langue.getValue());
                                        lg.add(newF);
                                    } else {
                                        //Lang.updateLangue(l.getId(), langue.getValue());
                                        System.out.println("deja ajouteeee");
                                        langue.setDisable(true);
                                        //lg.remove(CurrentVal);
                                       // lg.add(newF);
                                    }

//                                            String musicFile = "12.mp3";     // For example
//
//                                        Media sound = new Media(new File(musicFile).toURI().toString());
//                                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//                                        mediaPlayer.play();
                                } else {
                                    langue.setValue(l.getLangue());
                                    langue.setPromptText(l.getLangue());
                                    langue.setDisable(true);
                                }
                            }
                        });
                        });
                 

                    } else if (mouseEvent.getClickCount() == 3) {
                        niv.setDisable(false);

                        langue.setDisable(true);

                        niv.valueProperty().addListener((ov, oldValue, newValue) -> {

                            // System.out.println("date changed !"+oldValue +"to  "+newValue);
                            niv.setOnKeyPressed((KeyEvent keyEvent) -> {

                                if (keyEvent.getCode() == KeyCode.ENTER) {

                                    //ajouter formation ici
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation");
                                    alert.setHeaderText("Vous etes sure de modifier la date ce niveau de langue ?");
                                    alert.setContentText("Confirmer ?");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // ... user chose OK

                                        Lang.updateNiveau(l.getId(), niv.getValue());
                                        System.out.println("confirmer date");
                                        niv.setDisable(true);

                                    } else {
                                        niv.setValue(l.getNiveau());
                                        niv.setDisable(true);
                                    }
                                }

                            });
                        });

                    }
                }
            });

            // formation.setText(String.valueOf(f.getAnnee()));
            LanguesContent.getChildren().addAll(label, langue, niv);

        }

        int c = 0;
        List<CentresInterets> lc=cenSer.AfficherCentre(IdExistant);
        List<String> AllCent=new ArrayList<>();
        lc.stream().forEach(e->AllCent.add(e.getCentreInteret()));
        for (CentresInterets l : lc ) {
            c++;
            Label label = new Label("Centre d'interet " + c);
            label.setId(label.getText());
            label.getStyleClass().add("main-text");
            JFXComboBox<String> centre = new JFXComboBox<>();
            
            centre.getStyleClass().add("main-text");

            centre.getItems().addAll(edu.gestionpfe.controllers.CVControllers.RemplirCvController.CentresInterets);
            centre.getItems().remove(l.getCentreInteret());
            centre.setPromptText(l.getCentreInteret());
            centre.setId(l.getCentreInteret());

            centre.setDisable(true);
            Tooltip t=new Tooltip("2 Click pour Activer le champ centre d'interet pour pouvoir modifier ");
            Tooltip.install(label, t);
            label.setOnMouseClicked((MouseEvent mouseEvent) -> {
               if(mouseEvent.getClickCount()==2)
               {
               centre.setDisable(false);
                                       centre.valueProperty().addListener((observable, oldValue, newValue) -> {
//    System.out.println("textfield changed from " + oldValue + " to " + newValue);

                            if (AllCent.stream().anyMatch(e -> e.equalsIgnoreCase(newValue))) {
                                centre.setStyle("-fx-border-color : #FF0000");
                                label.setText("Centre d'interet existe deja");
                                label.setStyle("-fx-text-fill :#FF0000");
                                verifForm = true;

                            } else {

                                label.setText(label.getId());
                                label.setStyle("-fx-text-fill :#000000");
                                centre.setStyle("-fx-border-color : #0000");
                                verifForm = false;

                                newF = newValue;
                            }
                            
            centre.setOnKeyPressed((KeyEvent keyEvent) -> {

                            if (keyEvent.getCode() == KeyCode.ENTER) {

                                //ajouter formation ici
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Vous etes sure de modifier ce Centre d'interet ?");
                                alert.setContentText("Confirmer ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get()== ButtonType.OK && verifForm == false) {
                                    // ... user chose OK
                                    if (centre.getValue() == null) {
                                       
                                       // System.out.println("confirmer null");
                                        centre.setDisable(true);
                                        //lg.remove(centre.getId());
                                        //centre.setId(newF);
                                        centre.setPromptText(centre.getId());
                                        //lg.add(newF);
                           } 
                                    else {
                                         cenSer.updateCentre(l.getId(), newF);
                                        centre.setValue(newF);
                                        //System.out.println("confirmer");
                                        centre.setDisable(true);
                                        AllCent.remove(centre.getId());
                                        centre.setId(newF);
                                        AllCent.add(newF);
                                        
                                        
                                    }

//                                            String musicFile = "12.mp3";     // For example
//
//                                        Media sound = new Media(new File(musicFile).toURI().toString());
//                                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//                                        mediaPlayer.play();
                                } else {
                                    centre.setValue(centre.getId());
                                    centre.setPromptText(centre.getId());
                                    centre.setDisable(true);
                                }
                            }
                        });

                            
                        });
               }
            });
            // formation.setText(String.valueOf(f.getAnnee()));
            centresContent.getChildren().addAll(label, centre);

        }

        int k = 0;
        int level = 0;

        List<CompetencesTechniques> techniques = new ArrayList<>();
        techniques = techSer.AfficherTechs(IdExistant);
        List<String> alltech = new ArrayList<>();
        techniques.stream().forEach(e -> alltech.add(e.getCompetence()));
        for (CompetencesTechniques l : techniques) {
            k++;

            Label label = new Label("Competence " + k);
            Tooltip t = new Tooltip("Activez  pour modifier le champ Competence avec 2 clicks ou L'Estimation de niveau avec 3 clicks");
            Tooltip.install(label, t);
            TextField tech = new TextField(l.getCompetence());
            tech.setId(label.getText());
            TextFields.bindAutoCompletion(tech, possibleSuggestions);
            tech.setDisable(true);
            label.getStyleClass().add("main-text");

            JFXSlider techlevel = new JFXSlider();

            techlevel.setMin(0);
            techlevel.setMax(3);

            techlevel.setMinorTickCount(0);
            techlevel.setMajorTickUnit(1);
            techlevel.setSnapToTicks(true);
            techlevel.setShowTickMarks(true);
            techlevel.setShowTickLabels(true);

            techlevel.setLabelFormatter(new StringConverter<Double>() {
                @Override
                public String toString(Double n) {
                    if (n < 0.5) {
                        return "Faible";
                    }
                    if (n < 1.5) {
                        return "Peu de connaissance";
                    }
                    if (n < 2.5) {
                        return "Bien";
                    }

                    return "Expert";
                }

                @Override
                public Double fromString(String s) {
                    switch (s) {
                        case "Faible":
                            return 0d;
                        case "Peu de connaissance":
                            return 1d;
                        case "Bien":
                            return 2d;
                        case "Expert":
                            return 3d;

                        default:
                            return 3d;
                    }
                }
            });

            techlevel.setMinWidth(380);

            if (l.getLevel().equalsIgnoreCase("faible")) {
                level = 0;
                techlevel.setStyle("-fx-control-inner-background: #660000;");
            } else if (l.getLevel().equalsIgnoreCase("peu de connaissance")) {
                level = 1;
                techlevel.setStyle("-fx-control-inner-background: #ff8000;");
            } else if (l.getLevel().equalsIgnoreCase("bien")) {
                level = 2;
                techlevel.setStyle("-fx-control-inner-background: #99e600;");

            } else {
                level = 3;
                techlevel.setStyle("-fx-control-inner-background: #1f7a1f;");
            }
            techlevel.setValue(level);
            techlevel.setDisable(true);
            label.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getClickCount() == 2) {
                    String CurrentVal = l.getCompetence();
                    tech.setDisable(false);
                    techlevel.setDisable(true);
                    tech.textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("newvalue" + newValue);
                        tech.setOnKeyPressed((KeyEvent keyEvent) -> {
                            if (alltech.stream().anyMatch(e -> e.equalsIgnoreCase(newValue))) {
                                tech.setStyle("-fx-border-color : #FF0000");
                                label.setText("competence Existe Deja");
                                label.setStyle("-fx-text-fill :#FF0000");
                                verifForm = true;
                            } else {

                                label.setText(tech.getId());
                                label.setStyle("-fx-text-fill :#000000");
                                tech.setStyle("-fx-border-color : #0000");
                                verifForm = false;
                                newF = newValue;
                            }
                            if (keyEvent.getCode() == KeyCode.ENTER) {
                                //ajouter formation ici
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setHeaderText("Vous etes sure de modifier cette formation ?");
                                alert.setContentText("Confirmer ?");

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK && verifForm == false && !tech.getText().equalsIgnoreCase(" ") && !tech.getText().isEmpty()) {
                                    // ... user chose OK

                                    techSer.updateCompetence(l.getId(), tech.getText());
                                    System.out.println("confirmer");
                                    tech.setDisable(true);
                                    alltech.remove(CurrentVal);
                                    alltech.add(newF);

//                                            String musicFile = "12.mp3";     // For example
//
//                                        Media sound = new Media(new File(musicFile).toURI().toString());
//                                        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//                                        mediaPlayer.play();
                                } else {
                                    tech.setText(l.getCompetence());
                                    tech.setDisable(true);
                                }
                            }
                        });

                    });
                } else if (mouseEvent.getClickCount() == 3) {
                    tech.setDisable(true);
                    techlevel.setDisable(false);
                    techlevel.valueProperty().addListener(new ChangeListener<Number>() {
                        public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {

                            System.out.println(new_val);
                            techlevel.setOnKeyPressed((KeyEvent keyEvent) -> {

                                if (keyEvent.getCode() == KeyCode.ENTER) {
                                    String LevelS;

                                    if (new_val.intValue() == 0) {
                                        LevelS = "faible";
                                        techlevel.setId(LevelS);

                                    }
                                    if (new_val.intValue() == 1) {
                                        LevelS = "Peu de connaissance";

                                        techlevel.setId(LevelS);
                                    }
                                    if (new_val.intValue() == 2) {
                                        LevelS = "Bien";

                                        techlevel.setId(LevelS);
                                    }
                                    if (new_val.intValue() == 3) {

                                        LevelS = "Expert";
                                        techlevel.setId(LevelS);
                                    }
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation");
                                    alert.setHeaderText("Vous etes sure de modifier la date ce niveau de Competence ?");
                                    alert.setContentText("Confirmer ?");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        // ... user chose OK

                                        techSer.updateLevel(l.getId(), techlevel.getId());
                                        System.out.println("confirmer date");
                                        techlevel.setDisable(true);

                                    } else {
                                        techlevel.setLabelFormatter(new StringConverter<Double>() {
                                            @Override
                                            public String toString(Double n) {
                                                if (n < 0.5) {
                                                    return "Faible";
                                                }
                                                if (n < 1.5) {
                                                    return "Peu de connaissance";
                                                }
                                                if (n < 2.5) {
                                                    return "Bien";
                                                }

                                                return "Expert";
                                            }

                                            @Override
                                            public Double fromString(String s) {
                                                switch (s) {
                                                    case "Faible":
                                                        return 0d;
                                                    case "Peu de connaissance":
                                                        return 1d;
                                                    case "Bien":
                                                        return 2d;
                                                    case "Expert":
                                                        return 3d;

                                                    default:
                                                        return 3d;
                                                }
                                            }
                                        });
                                        int lev;
                                        if (l.getLevel().equalsIgnoreCase("faible")) {
                                            lev = 0;
                                            techlevel.setStyle("-fx-control-inner-background: #660000;");
                                        } else if (l.getLevel().equalsIgnoreCase("peu de connaissance")) {
                                            lev = 1;
                                            techlevel.setStyle("-fx-control-inner-background: #ff8000;");
                                        } else if (l.getLevel().equalsIgnoreCase("bien")) {
                                            lev = 2;
                                            techlevel.setStyle("-fx-control-inner-background: #99e600;");

                                        } else {
                                            lev = 3;
                                            techlevel.setStyle("-fx-control-inner-background: #1f7a1f;");
                                        }
                                        techlevel.setValue(lev);

                                        techlevel.setDisable(true);
                                    }

                                }
                            });

                        }
                    });
                }
            });

            // formation.setText(String.valueOf(f.getAnnee()));
            techsContent.getChildren().addAll(label, tech, techlevel);

        }

    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
    }

}
