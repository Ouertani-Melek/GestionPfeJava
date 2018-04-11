/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.gestionpfe.models.Soutenance;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.SoutenanceServices;
import edu.gestionpfe.services.UserServices;
import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author Melek OUERTANI
 */
public class SoutenanceTechController implements Initializable {
    @FXML
    private VBox vbox;
    
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker debut;
    @FXML
    private JFXTimePicker fin;
    @FXML
    private ComboBox prof1;
    @FXML
    private ComboBox salle;
    @FXML
    private Button ok;
     List<User> profs = new ArrayList<>();
     List<User> etudiants = new ArrayList<>();
    @FXML
    private ComboBox etu;
    @FXML
    private AnchorPane anch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          prof1.getItems().add("Choisir un prof"); 
          prof1.setValue("Choisir un prof");
          
          salle.getItems().add("c01");
          salle.getItems().add("c02");
          salle.getItems().add("c03");
          salle.getItems().add("c04");
          salle.getItems().add("c05");
        UserServices pfs = new UserServices();
        profs=pfs.profs();
        etudiants=pfs.etudiant();
        for (User p:profs)
        {
        prof1.getItems().add(p.getNom()+" "+p.getPrenom());
        
        }
        for (User e : etudiants)
        {
        etu.getItems().add(e.getNom()+" "+e.getPrenom());
        }
        
        
    }    

    @FXML
    private void valider(ActionEvent event) throws SQLException, MessagingException {
        DatePicker db = (DatePicker)vbox.lookup("#date");
        Date d=java.sql.Date.valueOf(db.getValue());
        JFXTimePicker dba = (JFXTimePicker)vbox.lookup("#debut");
        Time deb=java.sql.Time.valueOf(dba.getValue());
        JFXTimePicker dbab = (JFXTimePicker)vbox.lookup("#fin");
        Time finn=java.sql.Time.valueOf(dbab.getValue());
        String Type= "technique";
        
        String Salle= salle.getSelectionModel().getSelectedItem().toString();  
       
        String[] e=   etu.getValue().toString().split(" ");
        int ide = UserServices.getNomByIdNom(e[0], e[1]).getId();
        String et = UserServices.getNomByIdNom(e[0], e[1]).getEmail();
        
        Soutenance dis=new Soutenance(ide,Type,null,d,Salle,deb,finn);
        SoutenanceServices dss = new SoutenanceServices();
        
        
        
        dss.ajouterDisponibilite(dis);
        String[] part1=   prof1.getValue().toString().split(" ");
        int id1 = UserServices.getNomByIdNom(part1[0], part1[1]).getId();
        
        dss.ajouterDispo_user(id1,dis.getId());
         
          
         
        
        
        try {
                   FXMLLoader root = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/Soutenance/affectationPresident.fxml"));
                    AnchorPane x = root.load();
                    AffectationPresidentController c = root.getController();

                    anch.getChildren().clear();
                    anch.getChildren().add((Node) x);
                } catch (IOException ex) {
                    Logger.getLogger(SoutenanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
     
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        
       Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("educarepfe@gmail.com","afk123456789");
            }
        });
        

            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress("educarepfe@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(et));
            
            message.setSubject("GESTIONPFE Soutenance");
           
                message.setText("Vous avez une Soutenance "+Type+"  a "+d+" "+deb+" dans la salle  "+Salle+"\n Cordialement,l'équipe AFK \n Année Scolaire 2017/2018 \n Esprit Ghazela");
        
                 
                 
                 
            Transport.send(message);
        
    }
    
}
