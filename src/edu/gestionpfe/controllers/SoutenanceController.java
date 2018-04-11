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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class SoutenanceController implements Initializable {

    
    
    
    @FXML
    private AnchorPane anch;

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
    private ComboBox prof2;

    @FXML
    private ComboBox prof3;
     @FXML
    private ComboBox salle;

        List<User> profs = new ArrayList<>();
        List<User> etudiants = new ArrayList<>();
    @FXML
    private Button ok;
    @FXML
    private ComboBox etu;

   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        prof1.getItems().add("Choisir un prof"); 
      prof1.setValue("Choisir un prof");
       prof2.getItems().add("Choisir un prof"); 
      prof2.setValue("Choisir un prof");
       prof3.getItems().add("Choisir un prof"); 
      prof3.setValue("Choisir un prof");
      salle.getItems().add("c01");
          salle.getItems().add("c02");
          salle.getItems().add("c03");
          salle.getItems().add("c04");
          salle.getItems().add("c05");
        UserServices pfs = new UserServices();
        profs=pfs.profs();
        etudiants=pfs.etudiant();
         for (User e : etudiants)
        {
        etu.getItems().add(e.getNom()+" "+e.getPrenom());
        }
        for (User p:profs)
        {
        prof1.getItems().add(p.getNom()+" "+p.getPrenom());
        prof2.getItems().add(p.getNom()+" "+p.getPrenom());
        prof3.getItems().add(p.getNom()+" "+p.getPrenom());
        }
          Thread t=new Thread(() -> { 
         prof1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
           
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
       
       if (!oldValue.toString().equals("Choisir un prof"))
        { 
            
           System.out.println("new value :" +newValue.toString());
            System.out.println("old value :"+oldValue.toString());
            
            prof3.getItems().add(oldValue);
           prof2.getItems().add(oldValue);
        }prof2.getItems().remove(newValue);
             prof3.getItems().remove(newValue);
           }
         
      });
     });
          Thread t2=new Thread(() -> { 
       prof2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener () {
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
         /*  for (Prof p :profs )
           {
              if (prof1.getValue().toString().equals(p.getId()))*/
        if (!oldValue.toString().equals("Choisir un prof"))
        {
            
            prof3.getItems().add(oldValue);
           prof1.getItems().add(oldValue);
            System.out.println(oldValue.toString());
        }
        prof1.getItems().remove(newValue);
             prof3.getItems().remove(newValue);
             
         
          }
      });
       });
           Thread t3=new Thread(() -> { 
        prof3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
      /*   if (prof1.getValue().toString().equals(p.getId()))
        {prof2.getItems().remove(p.getId());
        prof3.getItems().remove(p.getId());
        }
         if (prof2.getValue().toString().equals(p.getId()))
        {prof3.getItems().remove(p.getId());
        
        }*/ 
         if (!oldValue.toString().equals("Choisir un prof"))
        {
            
            prof2.getItems().add(oldValue);
           prof1.getItems().add(oldValue);
        }
         prof1.getItems().remove(newValue);
             prof2.getItems().remove(newValue);
        
           }
      });
        });
           
       /*   date.valueProperty().addListener(new ChangeListener<LocalDate>() {

            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
            
                SoutenanceServices ss = new SoutenanceServices();
                List<Soutenance> l= ss.selectDisponibilites();
                
                l.stream().forEach(e->{
                for(Soutenance st : l ){
                if(st.getDatesoutenancecom().equals(newValue)){
                
                    System.out.println("duplicated");
                
                }
                
                }                
                });
            }
        });*/
           debut.valueProperty().addListener(new ChangeListener<LocalTime>() {

            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                SoutenanceServices ss = new SoutenanceServices();
                ArrayList<Soutenance> l = ss.selectDisponibilites();
                l.stream().forEach(e->{
                    for(Soutenance st : l ){
                        if (st.getHeuredeb().equals(newValue)){
                        System.out.println("duplicate");
                        }
                    }
                });
            }
        });
              date.valueProperty().addListener(new ChangeListener() {
            
        //thread//thread//thread//thread//thread//thread//thread
           @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               
                SoutenanceServices disps = new SoutenanceServices();
                 for (User p :profs){
//                int count=disps.Verification(java.sql.Date.valueOf(date.getValue()), p.getId());
//                if (count!=0)
                     {  
      //          System.out.println("count="+count);
                    prof1.getItems().clear();
                prof2.getItems().clear();
                prof3.getItems().clear();
                    prof1.getItems().add("Choisir un prof"); 
      prof1.setValue("Choisir un prof");
                    prof2.getItems().add("Choisir un prof"); 
      prof2.setValue("Choisir un prof");
                    prof3.getItems().add("Choisir un prof"); 
      prof3.setValue("Choisir un prof");
                 for (User pr :profs )
      { 
      prof1.getItems().add(pr.getNom()+" "+pr.getPrenom());
      prof2.getItems().add(pr.getNom()+" "+pr.getPrenom());
      prof3.getItems().add(pr.getNom()+" "+pr.getPrenom());
      }
                    prof1.getItems().remove(p.getId());
                    prof2.getItems().remove(p.getId());
                    prof3.getItems().remove(p.getId());
                }
                
                }
             
            }
        });
         t.run();
      t2.run();
      t3.run();
      } 
    

    
        
    
    @FXML
     public void valider() throws SQLException, MessagingException
    {
        DatePicker db = (DatePicker)vbox.lookup("#date");
        Date d=java.sql.Date.valueOf(db.getValue());
        JFXTimePicker dba = (JFXTimePicker)vbox.lookup("#debut");
        Time deb=java.sql.Time.valueOf(dba.getValue());
        JFXTimePicker dbab = (JFXTimePicker)vbox.lookup("#fin");
        Time finn=java.sql.Time.valueOf(dbab.getValue());
        String Type= "commerciale";
        String Salle= salle.getSelectionModel().getSelectedItem().toString();
       String[] e=   etu.getValue().toString().split(" ");
        int ide = UserServices.getNomByIdNom(e[0], e[1]).getId();
        String et = UserServices.getNomByIdNom(e[0], e[1]).getEmail();
        Soutenance dis=new Soutenance(ide,Type,d,null,Salle,deb,finn);
        SoutenanceServices dss = new SoutenanceServices();
        
        dss.ajouterDisponibilite(dis);
        String[] part1=   prof1.getValue().toString().split(" ");
        int id1 = UserServices.getNomByIdNom(part1[0], part1[1]).getId();
        String[] part2=   prof2.getValue().toString().split(" ");
        int id2 = UserServices.getNomByIdNom(part2[0], part2[1]).getId();
        String[] part3=   prof3.getValue().toString().split(" ");
        int id3 = UserServices.getNomByIdNom(part3[0], part3[1]).getId();
        
        
        
        dss.ajouterDispo_user(id1,dis.getId());
        dss.ajouterDispo_user(id2,dis.getId());
        dss.ajouterDispo_user(id3,dis.getId());
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
           
                message.setText("Vous avez une Soutenance "+Type+" a "+d+" "+deb+" dans la salle  "+Salle+"\n Cordialement,l'équipe AFK \n Année Scolaire 2017/2018 \n Esprit Ghazela");
        
                 
                 
                 
            Transport.send(message);
       
        
         
    }
}
