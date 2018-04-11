/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.DemandeControllers;


import com.jfoenix.controls.JFXTextField;
import edu.gestionpfe.models.Demandes;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.DemandesServices;
import edu.gestionpfe.services.UserServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class ListDesDemandesController implements Initializable {
 
    @FXML
    private Pagination pagination;
   public static Stage dialog = new Stage();
    private int DemandeNbr = 0;
    private int layoutX = 10;
    private int layoutY = 10;
    private String concatinatedTechs = "";
     public  static AnchorPane myAnchorP;
      public static int idU;
      public static int idOffreChibani;
   public static int idDemande;
   public static int idOffrePourEntretien;
    @FXML
    private AnchorPane myAnchor;

    @FXML
    private JFXTextField searchTitle;
    
    
    private void afficherDem(Demandes o) throws Exception {
        
        try {
            Pane p = new Pane();
            
            DemandeNbr++;
            ImageView img;
            User usr = UserServices.selectUserById(o.getIdUser());
            img = UserServices.getUserImage(usr);
            p.setPrefSize(90,90 );
            Button b = new Button("Voir CV");
            b.getStyleClass().add("button");
            b.setLayoutX(40);
            b.setLayoutY(100);
            Label l=new Label(usr.getPrenom()+" "+usr.getNom());
            
            l.setLayoutX(10);
            l.setLayoutY(20);
            l.setVisible(false);
            l.getStyleClass().add("main-text");
            Label titreOf=new Label();
             titreOf.getStyleClass().add("main-text");
            titreOf.setLayoutX(15);
            titreOf.setLayoutY(150);
            titreOf.setVisible(true);
           
            if(o.gettitreOffrePrDemande().length()>12){
             titreOf.setText(o.gettitreOffrePrDemande().substring(0, 12)+"..");
              Tooltip t=new Tooltip(o.gettitreOffrePrDemande());
              Tooltip.install(titreOf, t);
            }
            else{
                titreOf.setText(o.gettitreOffrePrDemande());
                Tooltip t=new Tooltip(o.gettitreOffrePrDemande());
              Tooltip.install(titreOf, t);
            } 
           
            b.setVisible(false);
            Image image = img.getImage();
            Circle c = new Circle(70, 70, 70);
            c.setFill(new ImagePattern(image));
//            Label titre = new Label();
//            String titreS = o.getTitre();
//            titre.setText(titreS);
//            if (o.getTitre().length() > 20) {
//                titreS = o.getTitre().substring(0, Math.min(o.getTitre().length(), 20));
//                titre.setText(titreS + "...");
//            }
            c.setOnMouseEntered((MouseEvent t) -> {
                b.setVisible(true);
                l.setVisible(true);
               
                c.setOpacity(0.5);
            });
            c.setOnMouseExited((MouseEvent t) -> {
                b.setVisible(false);  //btnsa.setStyle("-fx-background-color:transparent;");
                l.setVisible(false);
                c.setOpacity(1);
            });
            b.setOnMouseEntered((MouseEvent t) -> {
                b.setVisible(true);
                l.setVisible(true);
                c.setOpacity(0.5);
               
            });
            b.setOnAction((ActionEvent e) -> {
                Platform.runLater(() -> {
                    try {
//                        AnchorPane pane = new AnchorPane();
//                        pane.setOpacity(0.95);
////                        pane.setStyle(
////                                "-fx-background-color: #696969;"
////                                + "-fx-background-image: url("
////                                + "'/edu/gestionpfe/views/images/backgrounds/cource-search-bg.jpg'"
////                                + "); "
////                                + "-fx-background-size: cover;"
////                                + "-fx-background-radius: 10;"
////                        );
//
//                        
                 Image imageD = img.getImage();
                  Circle cD = new Circle(70, 70, 70);
                        cD.setLayoutX(20);
                        cD.setLayoutY(30);
                       idU=o.getIdUser();
                       idOffreChibani=o.getIdOffre();
                       idDemande=o.getId();
                       idOffrePourEntretien=o.getIdOffre();
                     
//
////                        TechnologiesServices.selectTechnologies(o).stream().forEach(a -> {
////                            concatinatedTechs += a.getTech() + "\n";
////                        });
////                        Label description = new Label();
////                        description.setPrefWidth(215);
////                        description.setWrapText(true);
////                        description.setText("Description générale :\n" + o.getDescription() + "\n\n\nTechnologies Demandées : \n" + concatinatedTechs);
////                        concatinatedTechs = "";
////                        description.setLayoutX(420);
////                        description.setLayoutY(40);
////                        description.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");                        
//                        JFXButton postuler = new JFXButton("Accepter ");
//                        postuler.setLayoutX(650);
//                        postuler.setLayoutY(370);
//                        postuler.setStyle("-fx-background-color: #00FF00");
//                        JFXDatePicker dateentre=new JFXDatePicker();
//                        
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
//  
//                        
//                        dateentre.setLayoutX(450);
//                        dateentre.setLayoutY(40);
//                        dateentre.setPromptText("Date Entretien");
//                        dateentre.setEditable(false);
//                        JFXComboBox<String> methodeCom=new JFXComboBox<>();
//                        methodeCom.setLayoutX(450);
//                        methodeCom.setLayoutY(70);
//                        methodeCom.getItems().addAll("Viber","Skype","Reunion live");
//                        methodeCom.setPromptText("methode");
//                        
//                       
////                        Label titreD = new Label();
////                        titreD.setText("Titre du stage : " + o.getTitre());
////                        titreD.setLayoutX(20);
////                        titreD.setLayoutY(190);
////                        titreD.setOpacity(5);
////                        titreD.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
//                       Label nomEntreprise = new Label();
//                        nomEntreprise.setText("Nom de l'entreprise : " + usr.getNom());
//                        nomEntreprise.setLayoutX(20);
//                        nomEntreprise.setLayoutY(210);
//                        nomEntreprise.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
//                       
//                        
//                        //pane.getChildren().add(titreD);
//                       // pane.getChildren().add(pa);
//                       // pane.getChildren().add(description);
//                        //pane.getChildren().add(cD);
//                        pane.getChildren().add(nomEntreprise);
//                        pane.getChildren().add(dateentre);
//                        pane.getChildren().add(methodeCom);
//                        pane.getChildren().add(postuler);
//                        pane.getChildren().add(rin);
                            
                            AnchorPane node= (AnchorPane)FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Demandes/CvEntreprise.fxml"));
                            
                      
                        dialog.initOwner((Stage) myAnchor.getScene().getWindow());
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initStyle(StageStyle.TRANSPARENT);
                       // dialog.setTitle(o.getTitre());
//                        UserImageHere.setFill(new ImagePattern(imageD));
//                        NomPreno.setText(l.getText());
//                        System.out.println(NomPreno.getText());
                        Scene scene = new Scene( node);
                        dialog.setScene(scene);
                        //dialog.initStyle(StageStyle.TRANSPARENT);
                        dialog.show();
                        b.setVisible(false);
//                         postuler.setOnAction((ActionEvent d) -> {
//                            Platform.runLater(() -> {
//                                try {
//                                    DemandesServices newsx=new DemandesServices();
//                                    
//                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Le RDV  sera le  "+dateentre.getValue() , ButtonType.OK,ButtonType.NO);
//                                        alert.initStyle(StageStyle.DECORATED);
//                                        
//                                        Optional<ButtonType> result = alert.showAndWait();
//                                        if (result.get() == ButtonType.OK) {
//                                            newsx.ajouterEntretien(o.getId(),dateentre.getValue());
//                                            dialog.close();
//                                            p.setVisible(false);
//                                           
//                                        }
//                                } catch (SQLException ex) {
//                                    Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                                
//                            });
//                        });

                    } catch (Exception ex) {
                        Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
//            titre.setLayoutX(5);
//            titre.setLayoutY(160);
//            p.getChildren().add(titre);
            
            p.getChildren().add(c);
            p.getChildren().add(b);
            p.getChildren().add(l);
            p.getChildren().add(titreOf);
            if (DemandeNbr == 1) {
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY+30);
                
            }
            if (DemandeNbr == 2) {
                layoutX += 250;
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY+30);
            }
            if (DemandeNbr == 3) {
                DemandeNbr = 0;
                layoutX += 250;
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY+30);
                layoutY += 180;
                layoutX = 10;
            }
            myAnchor.getChildren().add(p);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DemandesServices dem = new DemandesServices();
                 int lon=dem.findDeadOffre().size();
                        
                    if(lon>=1){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Vous Avez des Offres 'DEAD'"+dem.findDeadOffre().toString(), ButtonType.OK);
                                        alert.initStyle(StageStyle.DECORATED);
                                        
                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                 }
                    }
            
              myAnchorP=myAnchor;
              
       
        List<Demandes> ses=new ArrayList<>();
        ses=dem.AfficherDemande();
        ses.stream().forEach(d->{
            
            try {
                
                afficherDem(d);
               // System.out.println(d.getId());
            } catch (Exception ex) {
                Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });



    }
    ///pagination
   
           


   
   
}
//class WorkerThread extends Thread{
//    @Override
//            public void start(){
//            
//            }
//        RingProgressIndicator rpi;
//        int progress=0;
//        public WorkerThread(RingProgressIndicator rpi){
//        this.rpi=rpi;
//        }
//        @Override
//        public void run(){
//        while(true)
//            {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(GestionPfe.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                Platform.runLater(()->{
//                rpi.setProgress(progress);
//                });
//                rpi.setProgress(progress);
//                if(progress>100)
//                    break;
//            
//            }
  //  }
//ardi}
