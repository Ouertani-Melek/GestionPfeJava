/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.StageController;

import edu.gestionpfe.models.Stage;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.StagesServices;
import edu.gestionpfe.services.UserServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ListstagesController implements Initializable {

    
    
    @FXML
    private TableView<Stage> listeStage;
    @FXML
    private TableColumn<Stage,String> entreprise;
    @FXML
    private TableColumn<Stage,String> nometudiant;
    @FXML
    private TableColumn<Stage,String> prenometudiant;
    @FXML
    private TableColumn<Stage,String> titrestage;
    @FXML
    private TableColumn<Stage,Date> datedebut;
    @FXML
    private TableColumn<Stage,Date> datefin;
    @FXML
    private Text stg;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        stg.setCache(true);
       
        stg.setFill(Color.RED);
        stg.setFont(Font.font("Serif", FontWeight.BOLD, 30));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        stg.setEffect(r);
        
        TableColumn actionCol = new TableColumn("action");
        UserServices us = new UserServices();
        listeStage.getColumns().addAll(actionCol);
        StagesServices StageService=new StagesServices();
        ArrayList arrayList= (ArrayList) StageService.selectAll();
        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        listeStage.setItems(observableList);
//        nometudiant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stage, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stage, String> param) {
//               
//                return null;
//            }
//        });
        
     entreprise.setCellValueFactory(new PropertyValueFactory<>("Identreprise"));
        nometudiant.setCellValueFactory(new PropertyValueFactory<>("Idetudiant"));
        prenometudiant.setCellValueFactory(new PropertyValueFactory<>("getPrenom"));
        titrestage.setCellValueFactory(new PropertyValueFactory<>("TitreStage"));
        datedebut.setCellValueFactory(new PropertyValueFactory<>("DateDebut"));
        datefin.setCellValueFactory(new PropertyValueFactory<>("DateFin"));
        
//        ArrayList<User> users =new ArrayList<>();
//       
//        for(int i=0;i<arrayList.size();i++)
//        {
//             users.add(UserServices.getNomByIdUser());
//        }
        actionCol.setCellValueFactory(
             new PropertyValueFactory<Stage,String>("button")
    ); 
        
        
    }    
    
}
