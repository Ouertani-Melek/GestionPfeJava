/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.StageController;

import edu.gestionpfe.models.Encadrement;
import edu.gestionpfe.services.EncadrementsServices;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.ids;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.m;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterdatereunionController implements Initializable {

    @FXML
    private DatePicker dater;
    @FXML
    private Button valid;

    private Alert add;

    private Alert problem;

    private ButtonType OK;

    private ButtonType Terminer;
    @FXML
    private ComboBox<Integer> nbrjours;
    @FXML
    private Text txt;

    /**
     * Initializes the controller class.
     *
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txt.setCache(true);
       
        txt.setFill(Color.RED);
        txt.setFont(Font.font("Serif", FontWeight.BOLD, 30));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        txt.setEffect(r);
        

        nbrjours.getItems().addAll(1, 2, 3, 4, 5);
        add = new Alert(Alert.AlertType.CONFIRMATION);
        add.setTitle("gestionPFE care");
        add.setHeaderText("Ajout d'une Reunion");
        add.setContentText("-----Vous avez Ajouté une nouvelle Reunion!------");
        add.getButtonTypes().clear();// annuler button oui et non 
        Terminer = new ButtonType("Terminer", ButtonBar.ButtonData.OK_DONE);

        add.getButtonTypes().addAll(Terminer);

        problem = new Alert(Alert.AlertType.CONFIRMATION);
        problem.setTitle("gestionPFE care");
        problem.setHeaderText("Ajout d'une Reunion");
        problem.setContentText("-----Il faut entrer une date valide!------");
        problem.getButtonTypes().clear();// annuler button oui et non 
        OK = new ButtonType("D'accord", ButtonBar.ButtonData.OK_DONE);

        problem.getButtonTypes().addAll(OK);

        dater.setDayCellFactory((picker) -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 1);
            }
        });

    }

    @FXML
    private void ajouterdate(ActionEvent event) throws AddressException, MessagingException {
        EncadrementsServices encad = new EncadrementsServices();

        if (dater.getValue() != null) {
            encad.insertdatereunion(dater.getValue());
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("educarepfe@gmail.com", "afk123456789");
                }
            });
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("educarepfe@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(m));
                message.setSubject("GESTIONPFE reunion");

                message.setText("Vous avez une nouvelle Reunion le :" + dater.getValue() + "\n Cordialement,l'équipe AFK \n Année Scolaire 2017/2018 \n Esprit Ghazela");

                Transport.send(message);
                Optional<ButtonType> result = add.showAndWait();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            Optional<ButtonType> result = problem.showAndWait();
        }
    }

    @FXML
    private void postpone(ActionEvent event) {

        EncadrementsServices encad = new EncadrementsServices();

        encad.updatedatereunion(nbrjours.getValue());
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("educarepfe@gmail.com", "afk123456789");
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("educarepfe@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(m));
            message.setSubject("GESTIONPFE reunion");

            message.setText("Votre reunion a été reporté pour le :" + nbrjours.getValue() + "\n Cordialement,l'équipe AFK \n Année Scolaire 2017/2018 \n Esprit Ghazela");

            Transport.send(message);
            Optional<ButtonType> result = add.showAndWait();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
