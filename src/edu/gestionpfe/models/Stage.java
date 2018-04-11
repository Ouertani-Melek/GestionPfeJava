/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

import com.google.zxing.Result;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import static edu.gestionpfe.controllers.ConnectionController.usr;
import edu.gestionpfe.services.UserServices;
import edu.gestionpfe.controllers.StageController.ListstagesController;
import edu.gestionpfe.controllers.StageController.ListstagesEncadrantController;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author user
 */
public class Stage {

    private int id;
    private int identreprise;
    private int idencadrant;
    private int idetudiant;
    private String TitreStage;
    private String Descriptif;
    private Date dateDebut;
    private Date dateFin;
    private Button button;
    private Button button2;
    private Button button3;

    private String getPrenom;

    public Stage() {

    }

    public Stage(int id, int identreprise, int idencadrant, int idetudiant, String TitreStage, String Descriptif, Date dateDebut, Date dateFin) {
        this.id = id;
        this.identreprise = identreprise;
        this.idencadrant = idencadrant;
        this.idetudiant = idetudiant;
        this.TitreStage = TitreStage;
        this.Descriptif = Descriptif;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.button2 = new Button("Encadrement");

        this.button3 = new Button("Afficher Tache");
        this.button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override

            public void handle(ActionEvent event) {
                Dialog diag = new Dialog();
                diag.setResizable(true);
                diag.getDialogPane().setPrefSize(600, 200);
                //diag.getDialogPane().setContent();
                ButtonType Cancel = new ButtonType("Close");
                ButtonType Confirmer = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);

                GridPane grid = new GridPane();
                diag.setTitle("Liste des taches");
                diag.getDialogPane().getButtonTypes().stream()
                        .map(diag.getDialogPane()::lookupButton)
                        .forEach(node -> ButtonBar.setButtonUniformSize(node, false));
                diag.getDialogPane().setContent(grid);
                diag.getDialogPane().getButtonTypes().addAll(Cancel, Confirmer);

                diag.showAndWait();
            }
        });

        this.button = new Button("Affecter");
        button.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00),radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);-fx-background-radius: 6, 5;-fx-background-insets: 0, 1;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );-fx-text-fill:#395306;");

        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog diag = new TextInputDialog();
                diag.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);

                ButtonType Confirmer = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
                JFXComboBox combox2 = new JFXComboBox(FXCollections.observableArrayList(EnseiBelRole()));
                
                GridPane grid = new GridPane();

                grid.getChildren().add(combox2);
                diag.getDialogPane().setContent(grid);
                diag.getDialogPane().getButtonTypes().add(Confirmer);
                diag.getDialogPane().lookupButton(Confirmer).setVisible(false);
                combox2.valueProperty().addListener(new ChangeListener<String>() {

                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                        if (newValue == null) {
                            diag.getDialogPane().lookupButton(Confirmer).setVisible(false);
                        } else {
                            diag.getDialogPane().lookupButton(Confirmer).setVisible(true);
                        }
                        //boolean isMyComboBoxEmpty = (combox2.getSelectionModel().isEmpty());
                        //if (isMyComboBoxEmpty==true){
                        //   diag.getDialogPane().lookupButton(Confirmer).setVisible(false);
                        //}
                        //else {diag.getDialogPane().lookupButton(Confirmer).setVisible(true);}
                    }
                });

                diag.setResultConverter(new Callback<ButtonType, String>() {
                    @Override
                    public String call(ButtonType param) {
                        
                        if (param == Confirmer) {
                            String ch = combox2.getSelectionModel().getSelectedItem().toString();
                            UserServices us = new UserServices();
                            try {
                                int ide = us.getNomByIdNom(ch).getId();
                                if (us.Totalencad(ide) >= 2) {
                                    Alert alert = new Alert(Alert.AlertType.WARNING, "Voulez vous vraiment Affecter cet encadrant qui à déjà 2 etudiants ?  ", ButtonType.OK, ButtonType.CANCEL);
                                    alert.initStyle(StageStyle.UNDECORATED);
                                  
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                         SetAffected(ide, id);


                                    } else {
                                        //alert.hide();
                                    }
                                    
                                } else {
                                    SetAffected(ide, id);
                                }

                            } catch (SQLException ex) {
                                Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        return "ok";
                    }
                });
                diag.showAndWait();
            }
        });
    }

    public Button getButton3() {
        return button3;
    }

    public void setButton3(Button button3) {
        this.button3 = button3;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Button getButton2() {
        return button2;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }

    public String getIdentreprise() {
        try {
            UserServices us = new UserServices();
            return us.getNomByIdUser(this.identreprise).getNom();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setIdentreprise(int identreprise) {
        this.identreprise = identreprise;
    }

    public int getIdencadrant() {
        return idencadrant;
    }

    public void setIdencadrant(int idencadrant) {
        this.idencadrant = idencadrant;
    }

    public String getIdetudiant() {
        try {
            UserServices us = new UserServices();
            return us.getNomByIdUser(this.idetudiant).getNom();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getGetPrenom() {
        try {
            UserServices us = new UserServices();
            return us.getNomByIdUser(this.idetudiant).getPrenom();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getMail() {
        try {
            UserServices us = new UserServices();
            return us.getMailByIdUser(this.idetudiant).getEmail();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setGetPrenom(String getPrenom) {
        this.getPrenom = getPrenom;
    }

    public void setIdetudiant(int idetudiant) {
        this.idetudiant = idetudiant;
    }

    public String getTitreStage() {
        return TitreStage;
    }

    public void setTitreStage(String TitreStage) {
        this.TitreStage = TitreStage;
    }

    public String getDescriptif() {
        return Descriptif;
    }

    public void setDescriptif(String Descriptif) {
        this.Descriptif = Descriptif;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stage other = (Stage) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.identreprise != other.identreprise) {
            return false;
        }
        if (this.idencadrant != other.idencadrant) {
            return false;
        }
        if (this.idetudiant != other.idetudiant) {
            return false;
        }
        if (!Objects.equals(this.TitreStage, other.TitreStage)) {
            return false;
        }
        if (!Objects.equals(this.Descriptif, other.Descriptif)) {
            return false;
        }
        if (!Objects.equals(this.dateDebut, other.dateDebut)) {
            return false;
        }
        if (!Objects.equals(this.dateFin, other.dateFin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Stage{" + "id=" + id + ", identreprise=" + identreprise + ", idencadrant=" + idencadrant + ", idetudiant=" + idetudiant + ", TitreStage=" + TitreStage + ", Descriptif=" + Descriptif + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }

    public List<String> EnseiBelRole() {
        try {
            List<String> myList = new ArrayList();
            Connection cnx = ConnectionToDataBase.getInstance().getConnection();
            Statement myStmt = cnx.createStatement();
            ResultSet myRes = myStmt.executeQuery("SELECT * from user where roles LIKE '%ROLE_ENSEIGNANT%'");
            while (myRes.next()) {
                myList.add(myRes.getString("nom"));
            }
            return myList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void SetAffected(int id, int idStage) {
        try {
            Connection cnx = ConnectionToDataBase.getInstance().getConnection();
            PreparedStatement myStmt = cnx.prepareStatement("UPDATE stage set idencadrant = ? where id= ? ");
            myStmt.setInt(1, id);
            myStmt.setInt(2, idStage);
            myStmt.executeUpdate();

            PreparedStatement myStmt2 = cnx.prepareStatement("Insert into encadrement(idstage,Etat,pourcentage) values(?,?,?) ");
            myStmt2.setInt(1, idStage);

            myStmt2.setInt(2, 0);
            myStmt2.setDouble(3, 0);
            myStmt2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
