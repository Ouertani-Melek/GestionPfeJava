/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers.DemandeControllers;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXTextField;
import com.skype.Skype;
import com.skype.SkypeException;
import edu.gestionpfe.models.Demandes;
import edu.gestionpfe.models.DemandesPDF;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.DemandesServices;
import edu.gestionpfe.services.UserServices;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author yahia
 */
public class ListeDesAccepteController implements Initializable {

    @FXML
    private AnchorPane mAnchorAccepte;
    @FXML
    private Pagination paginationAccepte;
    @FXML
    private JFXTextField searchAcceptee;
    public static Stage dialog = new Stage();
    private int DemandeNbr = 0;
    private int layoutX = 10;
    private int layoutY = 10;
    // private String concatinatedTechs = "";
    public static AnchorPane myAnchorAccepteP;
    public static int idU;
    public static int idDemande;
    public static int idOffrePourEntretien;
    
private String nickname;
   
    private void afficherDem(Demandes o) throws Exception {
        
        DemandesPDF PDFAccepte=new DemandesPDF();
            DemandesServices dms=new DemandesServices();
        try {
            Pane p = new Pane();
              if(!o.isEtatEntretien())
              { 
                  DemandeNbr++;
              
              }
            ImageView img;
            User usr = UserServices.selectUserById(o.getIdUser());
            img = UserServices.getUserImage(usr);
            
          
            ImageView SkypeAppel=new ImageView("/edu/gestionpfe/views/images/skype.png");
            SkypeAppel.setFitHeight(16);
            SkypeAppel.setFitWidth(16);
            SkypeAppel.setLayoutX(10);
            SkypeAppel.setLayoutY(10);
            SkypeAppel.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event)->{
            
            
                try {
                    MakeCall();
                     nickname=String.valueOf(usr.getNumTel());
                } catch (Exception ex) {
                    Logger.getLogger(ListeDesAccepteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            
            });
            
            
            
            p.setPrefSize(90, 90);
           //buttons
            Button b = new Button();
            Button Delet=new Button("Refuser");
            b.getStyleClass().add("button");
            Delet.getStyleClass().add("button");
            
            b.setLayoutX(30);
            b.setLayoutY(100);
            
            Delet.setLayoutX(25);
            Delet.setLayoutY(70);
            
            
            
             b.setVisible(false);
            Delet.setVisible(false);
            
            
            //end button
            //titre et user data
            Label l = new Label(usr.getPrenom() + " " + usr.getNom());

            l.setLayoutX(10);
            l.setLayoutY(20);
            l.setVisible(false);
            l.getStyleClass().add("main-text");
            Label titreOf = new Label();
            titreOf.getStyleClass().add("main-text");
            titreOf.setLayoutX(15);
            titreOf.setLayoutY(150);
            titreOf.setVisible(true);
            Tooltip t=new Tooltip();
            t.setText(o.gettitreOffrePrDemande());
            
            if (o.gettitreOffrePrDemande().length() > 12) {
                titreOf.setText(o.gettitreOffrePrDemande().substring(0, 12) + "..");
               
                Tooltip.install(titreOf, t);
            } else {
                titreOf.setText(o.gettitreOffrePrDemande());
                Tooltip.install(titreOf, t);
            }

           
            Image image = img.getImage();
            Circle c = new Circle(70, 70, 70);
            c.setFill(new ImagePattern(image));
            //end user data


            
             if(!o.isEtatEntretien()){
                Delet.setVisible(true);
                b.setText("valider");
                b.setVisible(true);
            
            b.setOnAction((ActionEvent e) -> {
                Platform.runLater(() -> {
                    try {
                        //action  accepter le candidat 
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes Vous sure d'accepter Ce Candidat pour vous rejoindre dans l'offre " + o.gettitreOffrePrDemande() + "?", ButtonType.OK, ButtonType.NO);
                        alert.initStyle(StageStyle.DECORATED);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            //newsx.ajouterEntretien(o.getId(),dateentre.getValue());
                            DemandesServices deman = new DemandesServices();
                            deman.AccepterEntretien(o.getId());
                            TrayNotification tray = new TrayNotification();
                            tray.setTitle("Gestion PFE Acceptation Candidat");
                            tray.setMessage("Entretien Validé avec succés !");
                            tray.setNotificationType(NotificationType.SUCCESS);
                            tray.setAnimationType(AnimationType.SLIDE);
                            tray.showAndDismiss(Duration.seconds(20));
                            b.setText("PDF");
                            l.setVisible(true);
                            Delet.setVisible(false);
                            b.setOnAction((ActionEvent a) -> {
                                Platform.runLater(() -> {
                                    try {
                                        PDFAccepte.setNomEtudiant(usr.getNom()+" "+usr.getPrenom());
                                        PDFAccepte.setAdresseEtudiant("Tunisie"+","+usr.getVille()+","+usr.getRue());
                                        PDFAccepte.setEtatD("Accepté(e)");
                                        PDFAccepte.setEtatE("Accepté(e)");
                                        PDFAccepte.setEmailEtu(usr.getEmail());
                                        PDFAccepte.setNumTelEtudiant("+216 " + usr.getNumTel());
                                        //PDFAccepte.setDateDemande((o.getDateDemande()).ge);
                                        //PDFAccepte.setDateEntretien(Date.valueOf((o.getDateEntretien()).toLocalDate()));
                                        PDFAccepte.setOffreDemande(t.getText());
                                        PDFAccepte.setMatriculeDemande(o.getId());
                                        User EntrepriseUSer=new User();    
                                        EntrepriseUSer= dms.selectPdfEntreprise(o.getIdOffre());
                                        PDFAccepte.setAdresseEntreprise("Tunisie,"+EntrepriseUSer.getVille()+","+EntrepriseUSer.getRue()+","+EntrepriseUSer.getCodePostal());
                                        PDFAccepte.setImageEntre(EntrepriseUSer.getImage());
                                        PDFAccepte.setImageUSr(usr.getImage());
                                        PDFAccepte.setEmailEntreprise(EntrepriseUSer.getEmail());
                                        PDFAccepte.setNomEntreprise(EntrepriseUSer.getUsername());
                                        PDFAccepte.setNumTelEntreprise("+216 "+EntrepriseUSer.getNumTel());
                                        PDFAccepte.setIdEntreprise(EntrepriseUSer.getId());
                                        PDFAccepte.setIdEtudiant(o.getIdUser());
                                        
                                        
           
                                        AnchorPane node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Demandes/FXMLDocument.fxml"));
                                        Stage dia = new Stage();

                                        dia.initOwner((Stage) mAnchorAccepte.getScene().getWindow());
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
                                        GeneratePDFQRCode(PDFAccepte);
                                        b.setDisable(true);
                                        TrayNotification tra = new TrayNotification();
                                            tra.setTitle("Gestion PFE Generation Confirmation");
                                            tra.setMessage("En cours.. ");
                                            tra.setNotificationType(NotificationType.INFORMATION);
                                            tra.setAnimationType(AnimationType.SLIDE);
                                            tra.showAndDismiss(Duration.seconds(11));

                                    } catch (IOException ex) {
                                        Logger.getLogger(ListeDesAccepteController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(ListeDesAccepteController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (Exception ex) {
                                        Logger.getLogger(ListeDesAccepteController.class.getName()).log(Level.SEVERE, null, ex);
                                    } 

                                });

                            });

                        }

                    } catch (Exception ex) {
                        Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
               });
            });
            l.setVisible(true);
            Delet.setOnAction((ActionEvent d) -> {
               Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes Vous sure de refuser Ce Candidat pour vous rejoindre dans l'offre " + o.gettitreOffrePrDemande() + "?", ButtonType.OK, ButtonType.NO);
                        alert.initStyle(StageStyle.DECORATED);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            
                       
                    try {
                        Delet.setText("Refusé");
                        Delet.setDisable(true);
                        b.setVisible(false);
                        dms.RefusCandidat(o.getId());
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Gestion PFE Refus Candidat");
                        tray.setMessage("Operation terminée avec succées!");
                        tray.setNotificationType(NotificationType.INFORMATION);
                        tray.setAnimationType(AnimationType.SLIDE);
                        tray.showAndDismiss(Duration.seconds(20));
                    } catch (SQLException ex) {
                        Logger.getLogger(ListeDesAccepteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        }
               
               
               });
            });
            }
             
            p.getChildren().add(c);
            p.getChildren().add(SkypeAppel);
            p.getChildren().add(Delet);
            p.getChildren().add(b);
            p.getChildren().add(l);
            p.getChildren().add(titreOf);
            
            if (DemandeNbr == 1) {
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY + 30);

            }
            if (DemandeNbr == 2) {
                layoutX += 250;
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY + 30);
            }
            if (DemandeNbr == 3) {
                DemandeNbr = 0;
                layoutX += 250;
                p.setLayoutX(layoutX);
                p.setLayoutY(layoutY + 30);
                layoutY += 180;
                layoutX = 10;
            }
            if(!o.isEtatEntretien())
            { mAnchorAccepte.getChildren().add(p);
            
            }
           
            
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
        List<Demandes> ses = new ArrayList<>();
        ses = dem.AfficherDemandeAccepte();
        ses.stream().forEach(d -> {

            try {

                afficherDem(d);
                // System.out.println(d.getId());
            } catch (Exception ex) {
                Logger.getLogger(ListDesDemandesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    public void GeneratePDFQRCode(DemandesPDF Yalla) throws Exception
    {
    
        try {
            User usrt=new User();
            usrt.setId(Yalla.getIdEtudiant());
            usrt.setImage(Yalla.getImageUSr());
            
           
          
           
           
            
         Image image =  UserServices.getUserImage(usrt).getImage();

if(usrt.getImage().contains(" "))
        usrt.setImage(usrt.getImage().replaceAll(" ","%20"));
        String imageUrl = "http://localhost/gestionpfe/web/uploads/user/" + usrt.getImage();

            com.itextpdf.text.Document my_pdf_report = new com.itextpdf.text.Document();
            PdfWriter.getInstance(my_pdf_report, 
                    new FileOutputStream(new File("C:\\Users\\yahia\\Documents\\NetBeansProjects\\GestionPfeJava\\src\\edu\\gestionpfe\\views\\Demandes\\ALLPDFS\\"+Yalla.getNumTelEtudiant()+".pdf")));
            my_pdf_report.open();
          
 com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 14, BaseColor.RED);
            Paragraph docTitle = new Paragraph("Lettre de Confirmation", titleFont);
                docTitle.setAlignment(Element.ALIGN_CENTER);
            
                

                Paragraph Etudiant=new Paragraph();
                Etudiant.add(Yalla.getNomEtudiant()+"\n"+Yalla.getAdresseEtudiant()+"\n"+"Matricule Demande: \n"+Yalla.getMatriculeDemande()+"\n"
                +Yalla.getNumTelEtudiant()+"\n"+Yalla.getEmailEtu());
                Etudiant.setAlignment(Element.ALIGN_RIGHT);
                Paragraph Entreprise=new Paragraph();
                Entreprise.add(Yalla.getNomEntreprise().toUpperCase()+"\n"+Yalla.getAdresseEntreprise()+"\n"+Yalla.getEmailEntreprise()+"\n"
                +Yalla.getNumTelEntreprise());
                Entreprise.setAlignment(Element.ALIGN_LEFT);
                
                
                my_pdf_report.add(Entreprise);
                

    com.itextpdf.text.Image a =com.itextpdf.text.Image.getInstance(imageUrl);
    a.setAlignment(Element.ALIGN_RIGHT);
    a.scaleAbsolute(100, 100);
                my_pdf_report.add( a) ;
           
                my_pdf_report.add(Etudiant);
               
            titleFont.isUnderlined();
           
            docTitle.setSpacingAfter(10);
            
            my_pdf_report.add(docTitle);
                

            //we have 2 columns in our table
            PdfPTable my_report_table = new PdfPTable(2);
           
            
            my_report_table.setWidthPercentage(105);
            my_report_table.setSpacingBefore(50f);
            my_report_table.setSpacingAfter(30f);
            PdfPCell table_cell;
            PdfPCell table_cell2;
            
            table_cell = new PdfPCell(new Phrase("Details Demande", titleFont));
            
            table_cell.setFixedHeight(30);
            table_cell.setBackgroundColor(BaseColor.DARK_GRAY);
            table_cell2 = new PdfPCell(new Phrase("Etat ", titleFont));
            table_cell2.setFixedHeight(30);
            my_report_table.setHeaderRows(1);
            my_report_table.addCell(table_cell);
            my_report_table.addCell(table_cell2);
            
            
            
            my_report_table.addCell("Demande");
            my_report_table.addCell("Accepté(e)");
            
            
            my_report_table.addCell("Entretien");
            my_report_table.addCell("Accepté(e)");
            
            Paragraph date=new Paragraph();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date2 = new Date();
            date.add(dateFormat.format(date2) +"\n \n" +"Merci pour la qualité de Vos etudiants!");
                date.setAlignment(Element.ALIGN_BOTTOM);
            
            
            
            
            
            my_pdf_report.add(my_report_table);
            my_pdf_report.add(date);
            
            //Qr Code Generation 
            
 BarcodeQRCode barcodeQRCode = new BarcodeQRCode(Yalla.getNomEtudiant()+"//"+"mat Dem"+Yalla.getMatriculeDemande()+
         "//"+Yalla.getNomEntreprise()+"//stage :"+Yalla.getOffreDemande()+"//MatEtu*Matentreprise"+Yalla.getIdEtudiant()+"*"+Yalla.getIdEntreprise(), 1150, 1150, null);
     com.itextpdf.text.Image  codeQrImage = barcodeQRCode.getImage();
        codeQrImage.scaleAbsolute(100, 100);
        
        codeQrImage.setAlignment(Element.ALIGN_RIGHT);
            Paragraph sign= new Paragraph("SIGNATURE");
            sign.setAlignment(Element.ALIGN_RIGHT);
            my_pdf_report.add(sign);
        my_pdf_report.add(codeQrImage);

                 
                    
                

//Fin Qr Code
            my_pdf_report.close();
            
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(ListeDesAccepteController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        

    
    }
    private void MakeCall() throws Exception{
        try {
          Skype.call(nickname);
      } catch (SkypeException e) {
          e.printStackTrace();
      }
        System.out.println("skype");
    }

}
