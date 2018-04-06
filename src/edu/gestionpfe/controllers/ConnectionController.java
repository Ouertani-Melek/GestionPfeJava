/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import edu.gestionpfe.services.UserServices;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import edu.gestionpfe.models.User;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
/**
 *
 * @author ahmed
 */
public class ConnectionController implements Initializable {

    @FXML
    private Button login;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button fermer;
    @FXML
    private Button register;
    
  

    
    private static User user = new User();

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ConnectionController.user = user;
    }
public static User usr;
    /**
     *
     * @param e
     * @throws java.sql.SQLException
     */
    @FXML
    public void seConnecterAction(ActionEvent e) throws SQLException, Exception {

       
        UserServices usrService = new UserServices();
        usr = UserServices.selectUser(username.getText());
        if (usr.getPassword() == null) {
            System.out.println("username n'existe pas");
        } else {
            BCrypt.gensalt(12);
            if (BCrypt.checkpw(password.getText(), usr.getPassword())) {
                user = usr;
                HttpClient httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost("http://localhost/gestionpfe/web/app_dev.php/javacheckpassword");
                ArrayList<NameValuePair> params;
                params = new ArrayList<>(2);
                params.add(new BasicNameValuePair("username", username.getText()));
                params.add(new BasicNameValuePair("password", password.getText()));
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(params));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        try (InputStream instream = entity.getContent();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(instream))) {
                            StringBuilder out = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                out.append(line);
                            }
                            //System.out.println(out.toString());
                            if (out.toString().equalsIgnoreCase("ROLE_ETUDIANT")) {
                                try {
                                    Stage currentstage = (Stage) login.getScene().getWindow();
                                    currentstage.close();

                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/Default/UserIndex.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.initStyle(StageStyle.UNDECORATED);
                                    Scene scene = new Scene(root1);
                                   

                                    stage.setScene(scene);
                                    stage.show();

                                } catch (IOException l) {
                                    l.printStackTrace();
                                }
                            } else if (out.toString().equalsIgnoreCase("ROLE_ENTREPRISE")) {
                                try {
                                    Stage currentstage = (Stage) login.getScene().getWindow();
                                    currentstage.close();

                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/Default/EntrepriseIndex.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.initStyle(StageStyle.UNDECORATED);
                                    Scene scene = new Scene(root1);
                                    stage.setTitle("Entreprise");
                                    stage.setScene(scene);
                                    stage.show();

                                } catch (IOException l) {
                                    l.printStackTrace();
                                }
                            } else if (out.toString().equalsIgnoreCase("ROLE_ENSEIGNANT")) {
                                try {
                                    Stage currentstage = (Stage) login.getScene().getWindow();
                                    currentstage.close();

                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/Default/EnseignantIndex.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.initStyle(StageStyle.UNDECORATED);
                                    Scene scene = new Scene(root1);
                                    stage.setTitle("Enseignant");
                                    stage.setScene(scene);
                                    stage.show();

                                } catch (IOException l) {
                                    l.printStackTrace();
                                }
                            } else if (out.toString().equalsIgnoreCase("ROLE_ADMIN")) {
                                try {
                                    Stage currentstage = (Stage) login.getScene().getWindow();
                                    currentstage.close();

                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/AdminIndex.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.initStyle(StageStyle.UNDECORATED);
                                    Scene scene = new Scene(root1);
                                    stage.setTitle("Administrateur");
                                    stage.setScene(scene);
                                    stage.show();

                                } catch (IOException l) {
                                    l.printStackTrace();
                                }
                            }

                        }
                    }
                } catch (IOException | UnsupportedOperationException r) {
                }

            } else {
                System.out.println("It does not match");
            }
        }
    }
    
    @FXML
    public void setAfficherPageRegisterAction() throws IOException
    {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/PageRegister.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Registration Page");
        Stage currentstage = (Stage) register.getScene().getWindow();
        currentstage.close();
        stage.show();

    
    }

    @FXML
    public void setCancelAction() throws IOException, Exception {
         Alert alert = new Alert(Alert.AlertType.WARNING, "Voulez vous vraiment quitter l'application ?  ", ButtonType.OK,ButtonType.CANCEL);
         alert.initStyle(StageStyle.UNDECORATED);
        
           Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    Platform.exit();
    
} else {
   //alert.hide();
}
       
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
