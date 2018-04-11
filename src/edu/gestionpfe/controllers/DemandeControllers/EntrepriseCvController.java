/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.DemandeControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;

import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.dialog;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idDemande;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.idU;
import static edu.gestionpfe.controllers.DemandeControllers.ListDesDemandesController.myAnchorP;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.CentresInteretsServices;
import edu.gestionpfe.services.CompetencesTechniquesServices;
import edu.gestionpfe.services.CvServices;
import edu.gestionpfe.services.DemandesServices;
import edu.gestionpfe.services.FormationServices;
import edu.gestionpfe.services.UserServices;
import edu.gestionpfe.services.languesServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
/**
 * FXML Controller class
 *
 * @author yahia
 */
public class EntrepriseCvController implements Initializable {
      public static int verification;
private final static int WIDTH = 500;
	private final static int HEIGHT = 100;
    @FXML
    private AnchorPane CvAnchorPane;
    @FXML
    private  Circle UserImageHere;
    @FXML
    private JFXTextField NomPrenom;
    @FXML
    private JFXTextField maiUser;
    @FXML
    private JFXTextField universite;
    @FXML
    private JFXTextField Adresse;
    @FXML
    private JFXTextField Tel;
    @FXML
    private JFXTextField Niveau;
    @FXML
    private VBox PutFormation;
    @FXML
    private VBox PutCompetence;
    @FXML
    private VBox PutLangues;
    @FXML
    private VBox PutCentres;
  
 public static Stage diag = new Stage();
    @FXML
    private JFXButton acc;
    @FXML
    private JFXButton refu;
    @FXML
    private JFXButton AssistantChibani;

    public JFXButton getAssistantChibani() {
        return AssistantChibani;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//                 if(verification==1){
//                 acc.setVisible(false);
//                 }

            Tooltip chi=new Tooltip("Assisstance disponible pour fixer une date d'entretien dans le cas ou le candidat est favorable !");
       Tooltip.install(AssistantChibani, chi);
            
            try {
            ImageView img;
            User usr = UserServices.selectUserById(idU);
            
            // System.out.println(UserImageHere.getId());
            
            img = UserServices.getUserImage(usr);
            Image image = img.getImage();
            
            UserImageHere.setFill(new ImagePattern(image));
            NomPrenom.setText(usr.getNom()+" "+usr.getPrenom());
            Adresse.setText("Tunisie,"+usr.getVille()+","+usr.getRue()+","+usr.getCodePostal());
            Tel.setText("+216 "+usr.getNumTel());
            universite.setText("ESPRIT TN");
            Niveau.setText("5éme année");
            maiUser.setText(usr.getEmail());
            
            int idcv=CvServices.CVIdentifiant(idU);
            FormationServices formationsSer=new FormationServices();
            CompetencesTechniquesServices techs=new CompetencesTechniquesServices();
            languesServices langue=new languesServices();
            CentresInteretsServices centr=new CentresInteretsServices();
            formationsSer.AfficherFormation(idcv).stream().forEach(e->{
                JFXTextField formati=new JFXTextField(e.getFormation().toUpperCase()+"     / Diplome Obtenu en :   "+e.getAnnee());
                formati.setEditable(false);
                formati.setLabelFloat(true);
                PutFormation.getChildren().add(formati);
            });
             langue.AfficherLangue(idcv).stream().forEach(e->{
                JFXTextField lang=new JFXTextField(e.getLangue().toUpperCase()+"         / Niveau :   "+e.getNiveau());
                lang.setEditable(false);
                lang.setLabelFloat(true);
                PutLangues.getChildren().add(lang);
            });
              centr.AfficherCentre(idcv).stream().forEach(e->{
                JFXTextField cen=new JFXTextField(e.getCentreInteret().toUpperCase());
                cen.setEditable(false);
                cen.setAlignment(Pos.CENTER);
                cen.setLabelFloat(true);
                PutCentres.getChildren().add(cen);
            });
              
//                        JFXProgressBar rin=new JFXProgressBar();
//                        
//                           // rin.makeIndeterminate();
//                            rin.setProgress(0.1);
//                           rin.setPrefSize(335,15);
//                           
//                           rin.setLayoutX(10);
//                           rin.setLayoutY(50);
//                           
//                           
//                              Timeline timeline = new Timeline();
//
//                            KeyValue keyValue = new KeyValue(rin.progressProperty(), 0.8);
//                            KeyFrame keyFrame = new KeyFrame(new Duration(1500), keyValue);
//                            timeline.getKeyFrames().add(keyFrame);
//                            timeline.play();

              techs.AfficherTechs(idcv).stream().forEach(e->{
                  
                  BorderPane pane=new BorderPane();
                  Label label=new Label(e.getCompetence());
                  label.getStyleClass().add("text-field");
                  pane.setTop(label);
                  JFXProgressBar rin=new JFXProgressBar();
                  rin.getStyleClass().add("progress-bar");
                  if(e.getLevel().equalsIgnoreCase("faible")){
                  Tooltip t=new Tooltip("Faible avec Estimation de 10% de maitrise de language");
                  
                  Tooltip.install(rin, t);
                   rin.setProgress(0.1);
                           rin.setPrefSize(335,15);
                           
                           
                           
                           
                              Timeline timeline = new Timeline();

                            KeyValue keyValue = new KeyValue(rin.progressProperty(), 0.2);
                            KeyFrame keyFrame = new KeyFrame(new Duration(2000), keyValue);
                            timeline.getKeyFrames().add(keyFrame);
                            timeline.play();
                  
                }
                  if(e.getLevel().equalsIgnoreCase("peu de connaissance")){
                  Tooltip t=new Tooltip("Faible avec Estimation de 40% de maitrise de language");
                 
                  Tooltip.install(rin, t);
                   rin.setProgress(0.1);
                           rin.setPrefSize(335,15);
                           
                           
                           
                           
                              Timeline timeline = new Timeline();

                            KeyValue keyValue = new KeyValue(rin.progressProperty(), 0.45);
                            KeyFrame keyFrame = new KeyFrame(new Duration(2000), keyValue);
                            timeline.getKeyFrames().add(keyFrame);
                            timeline.play();
                  
                }
                  if(e.getLevel().equalsIgnoreCase("bien")){
                  Tooltip t=new Tooltip("Faible avec Estimation de 70% de maitrise de language");
                 
                  Tooltip.install(rin, t);
                   rin.setProgress(0.1);
                           rin.setPrefSize(335,15);
                           
                           
                           
                           
                              Timeline timeline = new Timeline();

                            KeyValue keyValue = new KeyValue(rin.progressProperty(), 0.75);
                            KeyFrame keyFrame = new KeyFrame(new Duration(2000), keyValue);
                            timeline.getKeyFrames().add(keyFrame);
                            timeline.play();
                  
                }
                  if(e.getLevel().equalsIgnoreCase("excellent")){
                  Tooltip t=new Tooltip("Faible avec Estimation de 97% de maitrise de language");
                  
                  Tooltip.install(rin, t);
                   rin.setProgress(0.1);
                           rin.setPrefSize(335,15);
                           
                           
                           
                           
                              Timeline timeline = new Timeline();

                            KeyValue keyValue = new KeyValue(rin.progressProperty(), 0.97);
                            KeyFrame keyFrame = new KeyFrame(new Duration(2000), keyValue);
                            timeline.getKeyFrames().add(keyFrame);
                            timeline.play();
                  
                }
                  if(e.getLevel()==null){
                  Tooltip t=new Tooltip("Faible avec Estimation de 10% de maitrise de language");
                  
                  Tooltip.install(rin, t);
                   rin.setProgress(0.1);
                           rin.setPrefSize(335,15);
                           
                           
                           
                           
                              Timeline timeline = new Timeline();

                            KeyValue keyValue = new KeyValue(rin.progressProperty(), 0.2);
                            KeyFrame keyFrame = new KeyFrame(new Duration(2000), keyValue);
                            timeline.getKeyFrames().add(keyFrame);
                            timeline.play();
                  
                }
                
                  pane.setCenter(rin);
                
                PutCompetence.getChildren().add(pane);
            });
             
              
                      
            
            
            
            // System.out.println(idU);
        } catch (SQLException ex) {
            Logger.getLogger(EntrepriseCvController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EntrepriseCvController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
   

    @FXML
    private void accepter(ActionEvent event) {
        
          
    try {
        AnchorPane node= (AnchorPane)FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Demandes/EntretienDateDemande.fxml"));
        
         
        diag.initOwner((Stage) myAnchorP.getScene().getWindow());
        diag.initModality(Modality.APPLICATION_MODAL);
        diag.initStyle(StageStyle.TRANSPARENT);
        // dialog.setTitle(o.getTitre());
//                        UserImageHere.setFill(new ImagePattern(imageD));
//                        NomPreno.setText(l.getText());
//                        System.out.println(NomPreno.getText());
Scene scene = new Scene( node);
diag.setScene(scene);
//dialog.initStyle(StageStyle.TRANSPARENT);
diag.show();
//newsx.ajouterEntretien(o.getId(),dateentre.getValue());
//dialog.close();
//p.setVisible(false);
    } catch (IOException ex) {
        Logger.getLogger(EntrepriseCvController.class.getName()).log(Level.SEVERE, null, ex);
    }
                 



           
                                           
                                        
    }

    @FXML
    private void Refuser(ActionEvent event) {
        DemandesServices d=new DemandesServices();
         Alert alert = new Alert(Alert.AlertType.WARNING, "Vous allez refuser ce candidat ! Etes vous sure ? ", ButtonType.OK,ButtonType.CLOSE);
                                        alert.initStyle(StageStyle.DECORATED);
                                        
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                                d.RefuserDemande(idDemande);

                                                verification=0;
                                                
                                                
                                            //newsx.ajouterEntretien(o.getId(),dateentre.getValue());
                                            //dialog.close();
                                            //p.setVisible(false);
                                         
             TrayNotification tray = new TrayNotification();
            tray.setTitle("Gestion PFE Refus Candidat");
            tray.setMessage("Candidat Refusé avec succés");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.SLIDE);
            tray.showAndDismiss(Duration.seconds(10));
                                            
                                           
                                        }
    }

    @FXML
    private void Quitter(ActionEvent event) {
        dialog.close();
        dialog=new Stage();
        
    }

    @FXML
    private void ChibaniPlayWithJava() throws IOException, SQLException {
                DemandesServices chibaniDemande=new DemandesServices();
                if(chibaniDemande.ChibaniIA()>=50.0)
                {
                try {
                    AnchorPane node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Demandes/ChibaniAssisstant.fxml"));
                    Stage dia = new Stage();
                    
                    dia.initOwner((Stage) CvAnchorPane.getScene().getWindow());
                    dia.initModality(Modality.APPLICATION_MODAL);
                    dia.initStyle(StageStyle.TRANSPARENT);
                    Scene scene = new Scene(node);
                    dia.setScene(scene);
                    
                    dia.show();
                    
                    //close this stage
                    PauseTransition delay = new PauseTransition(Duration.seconds(12));
                    delay.setOnFinished(event -> dia.close());
                    delay.play();
                    // System.out.println("Affichage user"+PDFAccepte.toString());
                    
                    
                    TrayNotification tra = new TrayNotification();
                    tra.setTitle("Gestion PFE CHIBANI ASSISSTANT");
                    tra.setMessage("Bienvenue! Le Traitement est En cours.. ");
                    tra.setNotificationType(NotificationType.INFORMATION);
                    tra.setAnimationType(AnimationType.SLIDE);
                    tra.showAndDismiss(Duration.seconds(11));
                } catch (IOException ex) {
                    Logger.getLogger(EntrepriseCvController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                else{
                
                TrayNotification tra = new TrayNotification();
                    tra.setTitle("Gestion PFE CHIBANI ASSISSTANT");
                    tra.setMessage("Bienvenue! Le pourcentage de succés de Candidat est inferieur a 50%");
                    tra.setNotificationType(NotificationType.WARNING);
                    tra.setAnimationType(AnimationType.SLIDE);
                    tra.showAndDismiss(Duration.seconds(11));
                    dialog.close();
                    dialog=new Stage();
                    AssistantChibani.setVisible(false);
                
                }

           
       
    }
    
}
