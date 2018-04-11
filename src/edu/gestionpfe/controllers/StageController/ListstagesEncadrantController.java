/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.StageController;

import static edu.gestionpfe.controllers.Avancement.idStage;
import edu.gestionpfe.models.Stage;
import edu.gestionpfe.services.StagesServices;
import edu.gestionpfe.services.UserServices;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ListstagesEncadrantController implements Initializable {

    
    
    
    @FXML
    private TableView<Stage> listeStageEncadrant;
     @FXML
    private TableColumn<Stage,String> stage;
    @FXML
    private TableColumn<Stage,String> entreprise;
    @FXML
    private TableColumn<Stage,String> prenometudiant;
    @FXML
    private TableColumn<Stage,String> nometudiant;
    @FXML
    private Text title;
    
    @FXML
    public  ScrollPane  mypane;
    
    private Alert at;
    
    private ButtonType AjouterTache;
    private ButtonType AfficherTaches;
    private ButtonType AjouterDate;
    private ButtonType Annuler;
    
    Button b2 = new Button();
    public static int ids;
    public static String m;
    public static String nom;
    public static String nomstage;
    public static String emailstage;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        title.setCache(true);
       
        title.setFill(Color.RED);
        title.setFont(Font.font("Serif", FontWeight.BOLD, 20));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        title.setEffect(r);
        
         mypane.setStyle("-fx-background-color:transparent;");
      
        title.setLayoutX(300);
        listeStageEncadrant.setLayoutX(150);
       
        UserServices us = new UserServices();
        
        StagesServices StageService=new StagesServices();
        ArrayList arrayList= (ArrayList) StageService.selectAllEncadrant();
        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        listeStageEncadrant.setItems(observableList);

        
        stage.setCellValueFactory(new PropertyValueFactory<>("TitreStage"));
        entreprise.setCellValueFactory(new PropertyValueFactory<>("Identreprise"));
        nometudiant.setCellValueFactory(new PropertyValueFactory<>("Idetudiant"));
        prenometudiant.setCellValueFactory(new PropertyValueFactory<>("getPrenom"));
        
  
     
      /*  try {
            AnchorPane node=FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/stages/ListeTaches.fxml"));
              mypane.setContent(node);
        } catch (IOException ex) {
            Logger.getLogger(ListstagesEncadrantController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
     //Stage.button2.setOnAction(new EventHandler<ActionEvent>() {
     
     
     at = new Alert(Alert.AlertType.CONFIRMATION);
     at.setTitle("gestion pfe");
     at.setHeaderText("gestion stage");
     at.setContentText("Faites votre choix senpai");
     at.getButtonTypes().clear();// annuler button oui et non 
     AfficherTaches = new ButtonType("Afficher Taches",ButtonBar.ButtonData.OK_DONE);
     AjouterTache = new ButtonType("Ajouter Taches",ButtonBar.ButtonData.OK_DONE);
     AjouterDate = new ButtonType("Ajouter Date",ButtonBar.ButtonData.OK_DONE);
     Annuler = new ButtonType("Annuler",ButtonBar.ButtonData.CANCEL_CLOSE);
     at.getButtonTypes().addAll(AfficherTaches,AjouterTache,AjouterDate,Annuler);
 //   int id_selected= listeStageEncadrant.getSelectionModel().getSelectedItem().getId();
     listeStageEncadrant.setRowFactory(tv->{
                TableRow<Stage> row= new TableRow<>();
                        row.setOnMouseClicked(e->{
                            if(e.getClickCount()==2 && (!row.isEmpty()))
                            {
                                
                               Optional<ButtonType> result= at.showAndWait();
                               if(result.isPresent() && result.get()==AfficherTaches)
                               {
                                      try {
                            nom=row.getItem().getGetPrenom();
                            nomstage=row.getItem().getTitreStage();
                            emailstage=row.getItem().getMail();
                           ids=row.getItem().getId();
                           idStage = ids;
                                         
                         AnchorPane node=FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/stages/ListeTaches.fxml"));
                         //mypane.getChildrenUnmodifiable().clear();
                         mypane.setContent(node);  
                               
                            } catch (Exception ex) {
                               
                            }
                               }
                               else if (result.isPresent() && result.get()==AjouterTache)
                               {        
                                   try {
                                       m=row.getItem().getMail();
                                       
                                       ids=row.getItem().getId();
                                      
                                      
                           //row.getItem();
                         AnchorPane node=FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/stages/ajoutertache.fxml"));
                         //mypane.getChildrenUnmodifiable().clear();
                         mypane.setContent(node);  
                               
                            } catch (Exception ex) {
                               
                            }
                               }
                                else if (result.isPresent() && result.get()==AjouterDate)
                               {
                                   try {
                                        m=row.getItem().getMail();
                                       ids=row.getItem().getId();
                           //row.getItem();
                         AnchorPane node=FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/stages/ajouterdatereunion.fxml"));
                         //mypane.getChildrenUnmodifiable().clear();
                         mypane.setContent(node);  
                               
                            } catch (Exception ex) {
                               
                            }
                               }
                                
                               
                               
                               }
                        });
                        return row;
                        });
            
        
                       
        
        
        
        
    }    

    public ScrollPane  getMypane() {
        return mypane;
    }

    public void setMypane(ScrollPane  mypane) {
        this.mypane = mypane;
    }
    
    
}
