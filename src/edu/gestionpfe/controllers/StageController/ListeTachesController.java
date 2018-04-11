/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.StageController;

import edu.gestionpfe.controllers.Avancement;
import com.itextpdf.text.BaseColor;
import java.io.File;

import java.io.FileOutputStream;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXTextField;
import static com.sun.deploy.panel.JreFindDialog.search;

import edu.gestionpfe.controllers.Avancement;

import edu.gestionpfe.models.ConnectionToDataBase;

import edu.gestionpfe.models.Tache;
import edu.gestionpfe.services.TachesServices;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.emailstage;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.ids;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.nom;
import static edu.gestionpfe.controllers.StageController.ListstagesEncadrantController.nomstage;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static jdk.nashorn.internal.objects.NativeString.search;
import static org.apache.commons.io.output.DeferredFileOutputStreamTest.data;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ListeTachesController implements Initializable {

    @FXML
    private TableView<Tache> listetaches;
    @FXML
    private TableColumn<Tache, String> titrestage;
    @FXML
    private TableColumn<Tache, String> titretache;
    @FXML
    private TableColumn<Tache, Boolean> etattache;
    @FXML
    private Button generate;

    private ButtonType Terminer;

    private Alert att;

    private Alert validation;
    private ButtonType Valider;
    private ButtonType Devalider;
    private ButtonType Annuler;
    private Alert done;
    private ButtonType merci;
    @FXML
    private Button avancement;
    @FXML
    private JFXTextField search;
    
    
    //public static final String IMG1 = "D:\\esprit.jpg";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        

        
        TachesServices TacheService = new TachesServices();
        ArrayList arrayList = (ArrayList) TacheService.selectAllTaches(ids);
        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        listetaches.setItems(observableList);

        titrestage.setCellValueFactory(new PropertyValueFactory<>("Idstage"));
        titretache.setCellValueFactory(new PropertyValueFactory<>("Tache"));
        etattache.setCellValueFactory(new PropertyValueFactory<>("Etat"));
       

        att = new Alert(Alert.AlertType.CONFIRMATION);
        att.setTitle("gestion pfe");
        att.setHeaderText("Les Taches du stage");
        att.setContentText("-----Vous avez Generer un PDF des taches------");
        att.getButtonTypes().clear();// annuler button oui et non 
        Terminer = new ButtonType("Terminer", ButtonBar.ButtonData.OK_DONE);

        att.getButtonTypes().addAll(Terminer);

        done = new Alert(Alert.AlertType.CONFIRMATION);
        done.setTitle("gestion pfe");
        done.setHeaderText("gestion validation tache");
        done.setContentText("Votre modification a été fait avec success");
        done.getButtonTypes().clear();// annuler button oui et non 
        merci = new ButtonType("Merci", ButtonBar.ButtonData.OK_DONE);

        done.getButtonTypes().addAll(merci);

        //el bloc hédha mta3 l Alert mta3 l validation
        validation = new Alert(Alert.AlertType.CONFIRMATION);
        validation.setTitle("gestion pfe");
        validation.setHeaderText("gestion validation tache");
        validation.setContentText("Voulez Vous Valider/Devalider cette Tache Monsieur ?");
        validation.getButtonTypes().clear();// annuler button oui et non 
        Valider = new ButtonType("Valider Tache", ButtonBar.ButtonData.OK_DONE);
        Devalider = new ButtonType("Devalider Tache", ButtonBar.ButtonData.OK_DONE);

        Annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        validation.getButtonTypes().addAll(Valider, Devalider, Annuler);

        listetaches.setRowFactory(tv -> {
            TableRow<Tache> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {

                    Optional<ButtonType> result = validation.showAndWait();
                    if (result.isPresent() && result.get() == Valider) {
                        try {
                            int idt = row.getItem().getId();
                            TachesServices tach = new TachesServices();
                            tach.validtache(idt);
                            Optional<ButtonType> result2 = done.showAndWait();
                            //System.out.println(idt);

                        } catch (Exception ex) {
                            
                        }
                    } else if (result.isPresent() && result.get() == Devalider) {
                        try {

                            int idt = row.getItem().getId();
                            TachesServices tach = new TachesServices();
                            tach.invalidtache(idt);
                            Optional<ButtonType> result2 = done.showAndWait();

                        } catch (Exception ex) {
                            
                        }
                    }

                }
            });
            return row;
        });
        avancement.setOnAction(((event) -> {
            Platform.runLater(()->{
            try {
                Avancement a = new Avancement();
                a.init();
                javafx.stage.Stage dialog = new javafx.stage.Stage();
                a.start(dialog);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        
        }));
        
        
        //code recherche
        FilteredList<Tache> filteredData = new FilteredList<>(observableList, e -> true);

    

        search.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Tache>) Tache -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Tache.getTache().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Tache.getEtat().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Tache> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(listetaches.comparatorProperty());
        listetaches.setItems(sortedData);
        
        
       
    }

    @FXML
    private void GeneratePDF(ActionEvent event) throws SQLException, DocumentException {

        try {

            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            /* Define the SQL query */
            ResultSet query_set = stmt.executeQuery("SELECT * FROM tache where idestage=" + ids);
            /* Step-2: Initialize PDF documents - logical objects */
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream(new File("D:\\Etudiant "+nom+".pdf")));
            
            my_pdf_report.open();
            
            
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.RED);
            com.itextpdf.text.Font titleFont3 = FontFactory.getFont(FontFactory.COURIER_BOLD, 10, BaseColor.BLACK);
            com.itextpdf.text.Font titleFont2 = FontFactory.getFont(FontFactory.COURIER_BOLD, 15, BaseColor.RED);
            Paragraph First = new Paragraph(" ESPRIT \n Cité Ghazela, Tunis \n 2017/2018 ", titleFont3);
            Paragraph Second = new Paragraph("L'Etudiant: "+nom+"\n Email: "+emailstage+"\n Titre Stage: "+nomstage, titleFont3);
            First.setAlignment(Element.ALIGN_LEFT);
            Second.setAlignment(Element.ALIGN_RIGHT);
            my_pdf_report.add(First);
            my_pdf_report.add(Second);
            Paragraph docTitle = new Paragraph("GestionPFE EduCare", titleFont);
            my_pdf_report.add(docTitle);
            com.itextpdf.text.Font subtitleFont = FontFactory.getFont("Times Roman", 17, BaseColor.BLACK);
            Paragraph subTitle = new Paragraph("Ceci est la liste Des Taches", subtitleFont);
            //subTitle.setAlignment(Element.ALIGN_RIGHT);
            my_pdf_report.add(subTitle);
            com.itextpdf.text.Font importantNoticeFont = FontFactory.getFont("Courier", 9, BaseColor.BLACK);
            Paragraph importantNotice = new Paragraph("---Tache de stage---", importantNoticeFont);

            my_pdf_report.add(importantNotice);

            //we have 3 columns in our table
            PdfPTable my_report_table = new PdfPTable(2);
            my_report_table.setWidthPercentage(105);
            my_report_table.setSpacingBefore(11f);
            my_report_table.setSpacingAfter(11f);

            //create a cell object
            PdfPCell table_cell;
            
            PdfPCell table_cell3;
            PdfPCell table_cell4;
            
            
            
           // String tit = "Titre Tache";
            Paragraph tit;
            tit = new Paragraph("Titre Tache", titleFont2);
                table_cell3 = new PdfPCell(new Phrase(tit));
                my_report_table.addCell(table_cell3);
            //String et = "Etat Tache";
            Paragraph et = new Paragraph("Etat Tache", titleFont2);

                table_cell4 = new PdfPCell(new Phrase(et));
                my_report_table.addCell(table_cell4);

            while (query_set.next()) {

                
                String tache = query_set.getString("tache");
                table_cell = new PdfPCell(new Phrase(tache));
                my_report_table.addCell(table_cell);
                Boolean Etat = query_set.getBoolean("Etat");
                if(Etat){
                 table_cell = new PdfPCell(new Phrase("Validée"));
                } 
                else {table_cell = new PdfPCell(new Phrase("Non Validée"));}
               
                my_report_table.addCell(table_cell);
            }
            /* Attach report table to PDF */
            my_pdf_report.add(my_report_table);
            my_pdf_report.close();

            /* Close all DB related objects */
            query_set.close();
            stmt.close();
            connection.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        Optional<ButtonType> result = att.showAndWait();
    }

}
