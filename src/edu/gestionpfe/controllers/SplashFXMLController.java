package edu.gestionpfe.controllers;

import static edu.gestionpfe.controllers.ConnectionController.usr;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SplashFXMLController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private Text bienvenue;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
      bienvenue.setText("Bienvenue"+"\n"+"    "+usr.getNom());
    }

}
