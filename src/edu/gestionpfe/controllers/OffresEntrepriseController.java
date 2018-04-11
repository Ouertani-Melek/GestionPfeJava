/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXTextField;
import static edu.gestionpfe.controllers.EntrepriseIndexController.containerP;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.services.OffreServices;
import edu.gestionpfe.services.TechnologiesServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class OffresEntrepriseController implements Initializable {

    @FXML
    private VBox myAnchor;
    private int offreNbr = 0;
    private int layoutX = 10;
    private int layoutY = 10;
    public static JFXTextField aa = new JFXTextField();
    private String concatinatedTechs = "";
    private String content = "<html>"
            + "    <head>"
            + "<link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n"
            + "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js\"></script>\n"
            + "<script src=\"https://code.jquery.com/jquery-1.11.1.min.js\"></script>"
            + "<!------ Include the above in your HEAD tag ---------->\n"
            + "\n"
            + "<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css\" rel='stylesheet' type='text/css'>"
            + "    </head>" + "<style>.panel-table .panel-body{\n"
            + "  padding:0;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-body .table-bordered{\n"
            + "  border-style: none;\n"
            + "  margin:0;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-body .table-bordered > thead > tr > th:first-of-type {\n"
            + "    text-align:center;\n"
            + "    width: 100px;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-body .table-bordered > thead > tr > th:last-of-type,\n"
            + ".panel-table .panel-body .table-bordered > tbody > tr > td:last-of-type {\n"
            + "  border-right: 0px;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-body .table-bordered > thead > tr > th:first-of-type,\n"
            + ".panel-table .panel-body .table-bordered > tbody > tr > td:first-of-type {\n"
            + "  border-left: 0px;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-body .table-bordered > tbody > tr:first-of-type > td{\n"
            + "  border-bottom: 0px;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-body .table-bordered > thead > tr:first-of-type > th{\n"
            + "  border-top: 0px;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-footer .pagination{\n"
            + "  margin:0; \n"
            + "}\n"
            + "\n"
            + "/*\n"
            + "used to vertically center elements, may need modification if you're not using default sizes.\n"
            + "*/\n"
            + ".panel-table .panel-footer .col{\n"
            + " line-height: 34px;\n"
            + " height: 34px;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-heading .col h3{\n"
            + " line-height: 30px;\n"
            + " height: 30px;\n"
            + "}\n"
            + "\n"
            + ".panel-table .panel-body .table-bordered > tbody > tr > td{\n"
            + "  line-height: 34px;\n"
            + "}\n"
            + "</style>"
            + "    <body>"
            + "<div class=\"container\">\n"
            + "    <div class=\"row\">" + " <div class=\"col-md-10 col-md-offset-1\">\n"
            + "\n"
            + "            <div class=\"panel panel-default panel-table\">\n"
            + "              <div class=\"panel-heading\">\n"
            + "                <div class=\"row\">\n"
            + "                  <div class=\"col col-xs-6\">\n"
            + "                    <h3 class=\"panel-title\">Ma liste des offres</h3>\n"
            + "                  </div>\n"
            + "                  <div class=\"col col-xs-6 text-right\">\n"
            + "                    <button type=\"button\" onclick=\"java.callbackFromJavaScriptAdd();\" class=\"btn btn-sm btn-primary btn-create\">Ajouter une offre</button>\n"
            + "                  </div>\n"
            + "                </div>\n"
            + "              </div>\n"
            + "              <div class=\"panel-body\">\n"
            + "                <table class=\"table table-striped table-bordered table-list\">\n"
            + "                  <thead>\n"
            + "                    <tr>\n"
            + "                        <th><em class=\"fa fa-cog\"></em></th>\n"
            + "                        <th>Titre du stage</th>\n"
            + "                        <th>Etat</th>\n"
            + "                        <th>Nombre d'etudiants</th>\n"
            + "                        <th>Durée du stage</th>\n"
            + "                        <th>Technologies demandées</th>\n"
            + "                        <th>Description</th>\n"
            + "                    </tr>\n"
            + "                  </thead>\n"
            + "                  <tbody>\n";

    private void afficher(Offre o) {
        try {
            concatinatedTechs = "";
            String etat = "Non validée";
            TechnologiesServices.selectTechnologies(o).stream().forEach(a -> {
                if (a != null) {
                    concatinatedTechs += a.getTech() + "<br>";
                }
            });
            String titreS = o.getTitre();
            if (o.getTitre().length() > 20) {
                titreS = o.getTitre().substring(0, Math.min(o.getTitre().length(), 20)) + "...";
            }
            if (o.isEtat()) {
                etat = "validée";
            }
            content += " <tr>\n"
                    + "                            <td align=\"center\">\n"
                    + "                              <a class=\"btn btn-default\" onclick=\"java.callbackFromJavaScript('" + String.valueOf(o.getId()) + "');\"><em class=\"fa fa-pencil\"></em></a>\n"
                    + "                              <a class=\"btn btn-danger\" onclick=\"java.callbackFromJavaScriptDelete('" + String.valueOf(o.getId()) + "');\"><em class=\"fa fa-trash\"></em></a>\n"
                    + "                            </td>\n"
                    + "                            <td>" + o.getTitre() + "</td>\n"
                    + "                            <td>" + etat + "</td>\n"
                    + "                            <td>" + String.valueOf(o.getNbr_demandes()) + "</td>\n"
                    + "                            <td>" + String.valueOf(o.getDuree()) + "</td>\n"
                    + "                            <td>" + concatinatedTechs + "</td>\n"
                    + "                            <td>" + titreS + "</td>\n"
                    + "</tr>";
        } catch (SQLException ex) {
            Logger.getLogger(OffresEntrepriseController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Offre> mesOffres = OffreServices.selectConnectedUserOffres();
            mesOffres.stream().forEach(o -> afficher(o));
            content += "</tbody>\n"
                    + "                </table>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
            final WebView webView = new WebView();
            webView.getEngine().loadContent(content);
            myAnchor.setPadding(new Insets(20));
            myAnchor.setStyle("-fx-background-color: gray");
            webView.prefWidthProperty().bind(myAnchor.widthProperty());
            webView.prefHeightProperty().bind(myAnchor.heightProperty());

            myAnchor.getChildren().add(webView);
            webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                    if (t1 == Worker.State.SUCCEEDED) {
                        // this will be run as soon as WebView is initialized.
                        JSObject jsobj = (JSObject) webView.getEngine().executeScript("window");
                        jsobj.setMember("java", new MyBridge());

                    }
                }
            });
            aa.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.equals("ok")) {
                    aa.setText("");
                    
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                containerP.getChildren().clear();
                                AnchorPane node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Offres/OffresEntreprise.fxml"));
                                containerP.getChildren().setAll(node);
                            } catch (IOException ex) {
                                Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                } else if (newValue.equals("add")) {
                aa.setText("");
                    
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                containerP.getChildren().clear();
                                AnchorPane node = (AnchorPane) FXMLLoader.load(getClass().getResource("/edu/gestionpfe/views/Offres/AjouterOffre.fxml"));
                                containerP.getChildren().setAll(node);
                            } catch (IOException ex) {
                                Logger.getLogger(EntrepriseIndexController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                }
            });

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
