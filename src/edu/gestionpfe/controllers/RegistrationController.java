/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.gestionpfe.services.UserServices;
import gestionpfe.GestionPfe;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;

/**
 *
 * @author ahmed
 */
public class RegistrationController implements Initializable {

    @FXML
    private JFXComboBox role;
    @FXML
    private JFXButton inscription;
    @FXML
    private JFXButton effacer;
    @FXML
    private ImageView retour;
    @FXML
    private Pane main_pain;
    @FXML
    private AnchorPane myAnchorPane;
    @FXML
    private JFXButton choisirImage;
    @FXML
    private Pane imagePain;
    @FXML
    private Pane errorPain;
    @FXML
    private Pane weakPass;

    @FXML
    private Pane mediumPass;

    @FXML
    private Pane strongPass;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField numTel;
    @FXML
    private JFXTextField ville;
    @FXML
    private JFXTextField rue;
    @FXML
    private JFXTextField codePostal;

    private File image;
    private boolean usernameValidator = false;
    private boolean passwordCheckValidator = false;
    private String fxNom = "";
    private String fxPrenom = "";
    private String fxEmail = "";
    private String fxPassword = "";

    private final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean emailValidator(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public boolean containsSpecialCharacter(String s) {
        Pattern regex = Pattern.compile("[^A-Za-z0-9.]");
        Matcher matcher = regex.matcher(s);
        return matcher.find();
    }

    public boolean containsOnlyNumbers(String s) {
        Pattern regex = Pattern.compile("[^0-9]");
        Matcher matcher = regex.matcher(s);
        return matcher.find();
    }

    public boolean containsSpecialCharacterNonumber(String s) {
        Pattern regex = Pattern.compile("[^A-Za-z']");
        Matcher matcher = regex.matcher(s);
        return matcher.find();
    }

    public boolean adresseEmailEntreprise(String s) {
        Pattern regex = Pattern.compile("[^A-Za-z0-9@.]");
        Matcher matcher = regex.matcher(s);
        return matcher.find();
    }

    public boolean containsSpecialCharacterWithSpaces(String s) {
        Pattern regex = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher matcher = regex.matcher(s);
        return matcher.find();
    }

    @FXML
    private void inscriptionAction() throws JSONException, IOException {
        if (!usernameValidator || "".equals(username.getText())) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez choisir un nom d'utilisateur valide !", ButtonType.OK);
            alert.show();
            return;
        }
        if ("".equals(numTel.getText())) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez saisir votre numéro de téléphone !", ButtonType.OK);
            alert.show();
            return;
        }
        if ("".equals(ville.getText())) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez saisir le nom de votre ville !", ButtonType.OK);
            alert.show();
            return;
        }
        if ("".equals(rue.getText())) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez saisir le nom de la rue où vous habiter !", ButtonType.OK);
            alert.show();
            return;
        }
        if ("".equals(codePostal.getText())) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez remplir le champ du code postal !", ButtonType.OK);
            alert.show();
            return;
        }
        // System.out.println(role.getValue().toString());
        
        if (null == image) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez choisir une photo!", ButtonType.OK);
            alert.show();
            return;
        }
         
              if (role.getValue().toString().equals("Entreprise")) {
            if ("".equals(fxNom)) {
                Alert alert = new Alert(AlertType.ERROR, "Veillez saisir le nom de l'entreprise !", ButtonType.OK);
                alert.show();
                return;
            }
        }
        if (((("".equals(fxNom) || "".equals(fxPrenom))
                && (role.getValue().toString().equals("Etudiant") || role.getValue().toString().equals("Enseignant"))))) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez saisir votre nom et votre prenom !", ButtonType.OK);
            alert.show();
            return;
        }
             if ("".equals(fxEmail)) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez saisir une adresse email valide !", ButtonType.OK);
            alert.show();
            return;
        }
        if (!passwordCheckValidator) {
            Alert alert = new Alert(AlertType.ERROR, "Veillez saisir un mot de passe convenable!", ButtonType.OK);
            alert.show();
            return;

        }
  
        /* if (!"Entreprise".equals(role.getValue().toString())) {
            fxEmail = fxEmail + "@esprit.tn";
        }*/
        //    Stage currentstage = animationLoading();
        inscription.setDisable(true);
        boolean register = UserServices.register(username.getText(), fxPassword, ville.getText(), rue.getText(), role.getValue().toString(), codePostal.getText(), numTel.getText(), fxNom, fxPrenom, fxEmail, image);
        if (register) {
            Stage stage = new Stage();
            Parent root;
            if (role.getValue().toString().equals("Entreprise")) {
                root = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Default/EntrepriseIndex.fxml"));
            } else if (role.getValue().toString().equals("Enseignant")) {
                root = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Default/EnseignantIndex.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Default/UserIndex.fxml"));
            }
            stage.setScene(new Scene(root));
            stage.setTitle("Se connecter");
            stage.initStyle(StageStyle.UNDECORATED);
            Stage currentstage = (Stage) username.getScene().getWindow();
            currentstage.close();
            stage.show();

        } else {
            System.out.println("false");
        }
    }

    @FXML
    private void effacerToutLesChampsAction() {
        weakPass.setStyle("-fx-background-color:   #F5F5F5");
        mediumPass.setStyle("-fx-background-color:   #F5F5F5");
        strongPass.setStyle("-fx-background-color:   #F5F5F5");
        main_pain.getChildren().clear();
        imagePain.getChildren().clear();
        role.setValue(null);
        username.clear();
        numTel.clear();
        rue.clear();
        ville.clear();
        codePostal.clear();
        image = null;
    }

    @FXML
    private void chooseImageAction() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        String[] formatNames = ImageIO.getReaderFormatNames();
        if (selectedFile != null && !FilenameUtils.isExtension(selectedFile.getName(), formatNames)) {
            Alert alert = new Alert(AlertType.WARNING, "vous ne pouvez pas selectionner un fichier de type " + FilenameUtils.getExtension(selectedFile.getName()), ButtonType.OK);
            alert.showAndWait();
            chooseImageAction();
        } else if (selectedFile != null && FilenameUtils.isExtension(selectedFile.getName(), formatNames)) {
            imagePain.getChildren().clear();
            Label imageName = new Label();
            imageName.setLayoutY(10);
            imageName.setLayoutX(10);
            if (selectedFile.getName().length() > 25) {
                imageName.setText("... " + selectedFile.getName().substring(selectedFile.getName().length() - 25));
            } else {
                imageName.setText(selectedFile.getName());
            }
            imagePain.getChildren().add(imageName);
            image = selectedFile;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            List<String> allUserNames = UserServices.getAllUserNames();
            role.getItems().removeAll(role.getItems());
            role.getItems().addAll("Entreprise", "Etudiant", "Enseignant");
            Tooltip t = new Tooltip("Retourner à la page d'accueil");
            Tooltip.install(retour, t);
            retour.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                Stage currentstage = (Stage) inscription.getScene().getWindow();
                currentstage.close();
                Stage stage = new Stage();
                GestionPfe g = new GestionPfe();
                try {
                    g.start(stage);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                event.consume();
            });

            role.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue != null && newValue.equals("Entreprise")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                weakPass.setStyle("-fx-background-color:   #F5F5F5");
                                mediumPass.setStyle("-fx-background-color:   #F5F5F5");
                                strongPass.setStyle("-fx-background-color:   #F5F5F5");
                                main_pain.getChildren().clear();
                                JFXTextField nom = new JFXTextField();
                                nom.setLayoutX(37);
                                nom.setLayoutY(16);
                                nom.setPrefWidth(212);
                                nom.setPrefHeight(30);
                                nom.setPromptText("Nom de l'entreprise");
                                JFXTextField email = new JFXTextField();
                                email.setLayoutX(37);
                                email.setLayoutY(66);
                                email.setPrefWidth(212);
                                email.setPrefHeight(30);
                                email.setPromptText("Email");
                                JFXPasswordField password = new JFXPasswordField();
                                password.setLayoutX(37);
                                password.setLayoutY(116);
                                password.setPrefWidth(212);
                                password.setPrefHeight(30);
                                password.setPromptText("Créez un mot de passe");
                                JFXPasswordField ConfirmPassword = new JFXPasswordField();
                                ConfirmPassword.setLayoutX(37);
                                ConfirmPassword.setLayoutY(166);
                                ConfirmPassword.setPrefWidth(212);
                                ConfirmPassword.setPrefHeight(30);
                                ConfirmPassword.setPromptText("Confirmer votre mot de passe");
                                Image img = new Image("/edu/gestionpfe/views/images/check.png");
                                ImageView imageView = new ImageView(img);
                                imageView.setLayoutX(37 + 213);
                                imageView.setLayoutY(166);
                                imageView.setFitWidth(30);
                                imageView.setFitHeight(30);
                                imageView.setVisible(false);
                                main_pain.getChildren().add(imageView);
                                main_pain.getChildren().add(nom);
                                main_pain.getChildren().add(email);
                                main_pain.getChildren().add(password);
                                main_pain.getChildren().add(ConfirmPassword);

                                nom.textProperty().addListener((observable, oldValue, newValue) -> {
                                    fxNom = nom.getText();
                                    if (!"".equals(newValue)) {
                                        errorPain.setStyle("-fx-background-color:   #F5F5F5");
                                        errorPain.getChildren().clear();
                                        if (containsSpecialCharacterNonumber(newValue)) {
                                            nom.setText(oldValue);
                                            errorPain.getChildren().clear();
                                            Label errorLabel = new Label();
                                            errorLabel.setLayoutY(10);
                                            errorLabel.setLayoutX(10);
                                            errorLabel.setText("Veuillez n'utiliser que des lettres \n (de a à z)!");
                                            errorPain.setStyle("-fx-background-color: #FF0000");
                                            errorPain.getChildren().add(errorLabel);
                                        }
                                    }
                                });
                                ConfirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
                                    imageView.setVisible(false);
                                    if (!"".equals(newValue)) {
                                        if (newValue.equals(password.getText())) {
                                            imageView.setVisible(true);
                                            fxPassword = password.getText();
                                            passwordCheckValidator = true;
                                        }
                                    }
                                });
                                email.textProperty().addListener((observable, oldValue, newValue) -> {

                                    if (!"".equals(newValue)) {
                                        try {
                                            errorPain.setStyle("-fx-background-color:   #F5F5F5");
                                            errorPain.getChildren().clear();
                                            if (!emailValidator(newValue)) {
                                                fxEmail = "";
                                                errorPain.getChildren().clear();
                                                Label errorLabel = new Label();
                                                errorLabel.setLayoutY(10);
                                                errorLabel.setLayoutX(10);
                                                errorLabel.setText("veuillez entrer une adresse email valide \n s'il vous plait!");
                                                errorPain.setStyle("-fx-background-color: #FF0000");
                                                errorPain.getChildren().add(errorLabel);
                                            } else if (UserServices.getAllEmails().stream().anyMatch(e -> e.equalsIgnoreCase(newValue))) {
                                                fxEmail = "";
                                                errorPain.getChildren().clear();
                                                Label errorLabel = new Label();
                                                errorLabel.setLayoutY(10);
                                                errorLabel.setLayoutX(10);
                                                errorLabel.setText("cette adresse email est déjà utilisée!");
                                                errorPain.setStyle("-fx-background-color: #FF0000");
                                                errorPain.getChildren().add(errorLabel);
                                            } else {
                                                fxEmail = email.getText();
                                            }
                                        } catch (SQLException ex) {
                                            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });
                                password.textProperty().addListener((observable, oldValue, newValue) -> {
                                    weakPass.setStyle("-fx-background-color:   #F5F5F5");
                                    mediumPass.setStyle("-fx-background-color:   #F5F5F5");
                                    strongPass.setStyle("-fx-background-color:   #F5F5F5");
                                    if (!"".equals(newValue)) {
                                        if (!containsSpecialCharacterNonumber(newValue) || !containsOnlyNumbers(newValue)) {
                                            weakPass.setStyle("-fx-background-color: #FF0000");
                                        } else if (!containsSpecialCharacter(newValue)) {
                                            weakPass.setStyle("-fx-background-color:  #FFBF00");
                                            mediumPass.setStyle("-fx-background-color:  #FFBF00");
                                        } else {
                                            weakPass.setStyle("-fx-background-color:  #00C853");
                                            mediumPass.setStyle("-fx-background-color:  #00C853");
                                            strongPass.setStyle("-fx-background-color:  #00C853");
                                        }
                                    }
                                });

                            }
                        });
                    } else if (newValue != null && (newValue.equals("Etudiant") || newValue.equals("Enseignant"))) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                weakPass.setStyle("-fx-background-color:   #F5F5F5");
                                mediumPass.setStyle("-fx-background-color:   #F5F5F5");
                                strongPass.setStyle("-fx-background-color:   #F5F5F5");
                                main_pain.getChildren().clear();
                                JFXTextField nom = new JFXTextField();
                                nom.setLayoutX(37);
                                nom.setLayoutY(16);
                                nom.setPrefWidth(104);
                                nom.setPrefHeight(30);
                                nom.setPromptText("Votre Nom");
                                JFXTextField prenom = new JFXTextField();
                                prenom.setLayoutX(144);
                                prenom.setLayoutY(16);
                                prenom.setPrefWidth(104);
                                prenom.setPrefHeight(30);
                                prenom.setPromptText("Votre prénom");
                                JFXTextField email = new JFXTextField();
                                email.setLayoutX(37);
                                email.setLayoutY(66);
                                email.setPrefWidth(150);
                                email.setPrefHeight(30);
                                email.setPromptText("Email");
                                Label espritTn = new Label();
                                espritTn.setLayoutY(76);
                                espritTn.setLayoutX(37 + 150);
                                espritTn.setText("@esprit.tn");
                                JFXPasswordField password = new JFXPasswordField();
                                password.setLayoutX(37);
                                password.setLayoutY(116);
                                password.setPrefWidth(212);
                                password.setPrefHeight(30);
                                password.setPromptText("Créez un mot de passe");
                                JFXPasswordField ConfirmPassword = new JFXPasswordField();
                                ConfirmPassword.setLayoutX(37);
                                ConfirmPassword.setLayoutY(166);
                                ConfirmPassword.setPrefWidth(212);
                                ConfirmPassword.setPrefHeight(30);
                                ConfirmPassword.setPromptText("Confirmer votre mot de passe");
                                Image img = new Image("/edu/gestionpfe/views/images/check.png");
                                ImageView imageView = new ImageView(img);
                                imageView.setLayoutX(37 + 213);
                                imageView.setLayoutY(166);
                                imageView.setFitWidth(30);
                                imageView.setFitHeight(30);
                                imageView.setVisible(false);
                                main_pain.getChildren().add(imageView);
                                main_pain.getChildren().add(nom);
                                main_pain.getChildren().add(prenom);
                                main_pain.getChildren().add(email);
                                main_pain.getChildren().add(password);
                                main_pain.getChildren().add(ConfirmPassword);
                                main_pain.getChildren().add(espritTn);
                                email.textProperty().addListener((observable, oldValue, newValue) -> {
                                    fxEmail = email.getText() + "@esprit.tn";
                                    if (containsSpecialCharacter(newValue)) {
                                        email.setText(oldValue);
                                        Alert alert = new Alert(AlertType.WARNING, "Vous êtes un " + role.getValue().toString() + " d'esprit. Le  nom de domaine par defaut est \"esprit.tn\", veillez entrer uniquement votre identifiant qui ne peut contenir que des lettres et des chiffres" + ".", ButtonType.OK);
                                        alert.show();
                                    }
                                });
                                nom.textProperty().addListener((observable, oldValue, newValue) -> {
                                    fxNom = nom.getText();
                                    if (!"".equals(newValue)) {
                                        errorPain.setStyle("-fx-background-color:   #F5F5F5");
                                        errorPain.getChildren().clear();
                                        if (containsSpecialCharacterNonumber(newValue)) {
                                            nom.setText(oldValue);
                                            errorPain.getChildren().clear();
                                            Label errorLabel = new Label();
                                            errorLabel.setLayoutY(10);
                                            errorLabel.setLayoutX(10);
                                            errorLabel.setText("Veuillez n'utiliser que des lettres \n (de a à z)!");
                                            errorPain.setStyle("-fx-background-color: #FF0000");
                                            errorPain.getChildren().add(errorLabel);
                                        }
                                    }
                                });
                                ConfirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
                                    imageView.setVisible(false);
                                    if (!"".equals(newValue)) {
                                        if (newValue.equals(password.getText())) {
                                            imageView.setVisible(true);
                                            fxPassword = password.getText();
                                            passwordCheckValidator = true;
                                        }
                                    }
                                });
                                prenom.textProperty().addListener((observable, oldValue, newValue) -> {
                                    fxPrenom = prenom.getText();
                                    if (!"".equals(newValue)) {
                                        errorPain.setStyle("-fx-background-color:   #F5F5F5");
                                        errorPain.getChildren().clear();
                                        if (containsSpecialCharacterNonumber(newValue)) {
                                            prenom.setText(oldValue);
                                            errorPain.getChildren().clear();
                                            Label errorLabel = new Label();
                                            errorLabel.setLayoutY(10);
                                            errorLabel.setLayoutX(10);
                                            errorLabel.setText("Veuillez n'utiliser que des lettres \n (de a à z)!");
                                            errorPain.setStyle("-fx-background-color: #FF0000");
                                            errorPain.getChildren().add(errorLabel);
                                        }
                                    }
                                });
                                password.textProperty().addListener((observable, oldValue, newValue) -> {
                                    weakPass.setStyle("-fx-background-color:   #F5F5F5");
                                    mediumPass.setStyle("-fx-background-color:   #F5F5F5");
                                    strongPass.setStyle("-fx-background-color:   #F5F5F5");
                                    if (!"".equals(newValue)) {
                                        if (!containsSpecialCharacterNonumber(newValue) || !containsOnlyNumbers(newValue)) {
                                            weakPass.setStyle("-fx-background-color: #FF0000");
                                        } else if (!containsSpecialCharacter(newValue)) {
                                            weakPass.setStyle("-fx-background-color:  #FFBF00");
                                            mediumPass.setStyle("-fx-background-color:  #FFBF00");
                                        } else {
                                            weakPass.setStyle("-fx-background-color:  #00C853");
                                            mediumPass.setStyle("-fx-background-color:  #00C853");
                                            strongPass.setStyle("-fx-background-color:  #00C853");
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
            });
            numTel.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    numTel.setText(oldValue);

                }
            });
            codePostal.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    codePostal.setText(oldValue);

                }
            });
            username.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!"".equals(newValue)) {
                    errorPain.setStyle("-fx-background-color:   #F5F5F5");
                    errorPain.getChildren().clear();
                    if (containsSpecialCharacter(newValue)) {
                        username.setText(oldValue);
                        errorPain.getChildren().clear();
                        Label errorLabel = new Label();
                        errorLabel.setLayoutY(10);
                        errorLabel.setLayoutX(10);
                        errorLabel.setText("Veuillez n'utiliser que des lettres \n (de a à z)et des chiffres!");
                        errorPain.setStyle("-fx-background-color: #FF0000");
                        errorPain.getChildren().add(errorLabel);
                    } else if (allUserNames.stream().anyMatch(e -> e.equalsIgnoreCase(newValue))) {
                        errorPain.getChildren().clear();
                        Label errorLabel = new Label();
                        errorLabel.setLayoutY(10);
                        errorLabel.setLayoutX(10);
                        errorLabel.setText("ce nom d'utilisateur existe déjà");
                        usernameValidator = false;
                        errorPain.setStyle("-fx-background-color: #FF0000");
                        errorPain.getChildren().add(errorLabel);
                    } else {
                        usernameValidator = true;
                    }
                }
            });
            rue.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!"".equals(newValue)) {
                    errorPain.setStyle("-fx-background-color:   #F5F5F5");
                    errorPain.getChildren().clear();
                    if (containsSpecialCharacterWithSpaces(newValue)) {
                        rue.setText(oldValue);
                        errorPain.getChildren().clear();
                        Label errorLabel = new Label();
                        errorLabel.setLayoutY(10);
                        errorLabel.setLayoutX(10);
                        errorLabel.setText("Veuillez n'utiliser que des lettres \n (de a à z), des chiffres et des espaces!");
                        errorPain.setStyle("-fx-background-color: #FF0000");
                        errorPain.getChildren().add(errorLabel);
                    }
                }
            });
            ville.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!"".equals(newValue)) {
                    errorPain.setStyle("-fx-background-color:   #F5F5F5");
                    errorPain.getChildren().clear();
                    if (containsSpecialCharacterWithSpaces(newValue)) {
                        ville.setText(oldValue);
                        errorPain.getChildren().clear();
                        Label errorLabel = new Label();
                        errorLabel.setLayoutY(10);
                        errorLabel.setLayoutX(10);
                        errorLabel.setText("Veuillez n'utiliser que des lettres \n (de a à z)et des chiffres et des espaces!");
                        errorPain.setStyle("-fx-background-color: #FF0000");
                        errorPain.getChildren().add(errorLabel);
                    }
                }
            });

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
