/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.StageController;

import edu.gestionpfe.models.Tache;
import edu.gestionpfe.services.TachesServices;
import static edu.gestionpfe.services.TachesServices.idSt;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.ids;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.m;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
 * @author user
 */
public class AjoutertacheController implements Initializable {

    @FXML
    private TextField tache;
    @FXML
    private Button ajoutertache;
    
    private Alert add;
    
    private Alert problem;
    
    private ButtonType OK;
    
    private ButtonType Terminer;
    @FXML
    private Text tit;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         tit.setCache(true);
       
        tit.setFill(Color.RED);
        tit.setFont(Font.font("Serif", FontWeight.BOLD, 28));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        tit.setEffect(r);
        
       
        add= new Alert(Alert.AlertType.INFORMATION);
     add.setTitle("gestionPFE care");
     add.setHeaderText("Ajout d'une Tache");
     add.setContentText("-----Vous avez Ajouté une nouvelle Tache!------");
     add.getButtonTypes().clear();// annuler button oui et non 
     Terminer = new ButtonType("Terminer",ButtonBar.ButtonData.OK_DONE);
     
     add.getButtonTypes().addAll(Terminer);
     
     
     problem= new Alert(Alert.AlertType.ERROR);
     problem.setTitle("gestionPFE care");
     problem.setHeaderText("Ajout d'une Tache");
     problem.setContentText("-----Votre tache n'est pas valide!------");
     problem.getButtonTypes().clear();// annuler button oui et non 
     OK = new ButtonType("D'accord",ButtonBar.ButtonData.OK_DONE);
     
     problem.getButtonTypes().addAll(OK);
        
    }    

    @FXML
    private void ajouterTaches(ActionEvent event) {
        TachesServices tach = new TachesServices();

        Tache t =new Tache(ids,tache.getText(),false);
       if (!tache.getText().equalsIgnoreCase("") && !(tache.getText().startsWith(" ")))
       {
           tach.inserttache(t);
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
        try {

            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress("educarepfe@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(m));
            
            message.setSubject("GESTIONPFE tache");
           
                message.setText("Vous avez une nouvelle tache : "+tache.getText()+"\n Cordialement,l'équipe AFK \n Année Scolaire 2017/2018 \n Esprit Ghazela");
        
                 
                 
                 
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
           Optional<ButtonType> result= add.showAndWait();
       }
       else {
        Optional<ButtonType> result= problem.showAndWait();
       }
       
        
        
    }
    
}
