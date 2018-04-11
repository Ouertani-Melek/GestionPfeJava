package edu.gestionpfe.controllers;

import com.jfoenix.controls.JFXButton;
import edu.gestionpfe.models.CompetencesTechniques;
import edu.gestionpfe.models.Cv;
import edu.gestionpfe.models.Offre;
import edu.gestionpfe.models.Technologie;
import edu.gestionpfe.models.User;
import edu.gestionpfe.services.CompetencesTechniquesServices;
import edu.gestionpfe.services.CvServices;
import edu.gestionpfe.services.DemandesServices;
import edu.gestionpfe.services.TechnologiesServices;
import edu.gestionpfe.services.UserServices;
import eu.hansolo.medusa.Fonts;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import java.sql.SQLException;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * Created by hansolo on 02.02.16.
 */
public class Main extends Application {
    
    private static final Random RND = new Random();
    private static int noOfNodes = 0;
    private Gauge gauge;
    private Label value;
    private long lastTimerCall;
    private AnimationTimer timer;
    private String concatinatedTechs="";

    public Offre getO() {
        return o;
    }

    public void setO(Offre o) {
        this.o = o;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public AnchorPane getPane() {
        return pane;
    }

    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }
    Offre o;
    User usr;
    AnchorPane pane = new AnchorPane();

    @Override
    public void init() throws SQLException, Exception {
        ImageView img;
        img = UserServices.getUserImage(usr);
        pane.setOpacity(0.95);
        pane.setStyle(
                "-fx-background-color: #696969;"
                + "-fx-background-image: url("
                + "'/edu/gestionpfe/views/images/backgrounds/cource-search-bg.jpg'"
                + "); "
                + "-fx-background-size: cover;"
                + "-fx-background-radius: 10;"
        );
        Image imageD = img.getImage();
        Circle cD = new Circle(70, 70, 70);
        cD.setLayoutX(20);
        cD.setLayoutY(30);
        cD.setFill(new ImagePattern(imageD));

        TechnologiesServices.selectTechnologies(o).stream().forEach(a -> {
            if(a!=null)
            concatinatedTechs += a.getTech() + "\n";
        });
        Label description = new Label();
        description.setPrefWidth(215);
        description.setWrapText(true);
        description.setText("Description générale :\n" + o.getDescription() + "\n\n\nTechnologies Demandées :\n" + concatinatedTechs);
        System.out.println(concatinatedTechs);
        concatinatedTechs = "";
        description.setLayoutX(420);
        description.setLayoutY(40);
        description.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
        JFXButton postuler = new JFXButton("Postuler maintenant");
        postuler.setLayoutX(700);
        postuler.setLayoutY(370);
        postuler.setPrefSize(200, 50);
        postuler.setStyle("-fx-background-color: #00FF00");
        DemandesServices demandess=new DemandesServices();
        demandess.allDemandesForUser(ConnectionController.usr.getId()).stream().forEach(e->{
        
            try {
                if(e==o.getId()|| CvServices.findCv().getId()==0)
                {
                    postuler.setDisable(true);
                     TrayNotification tray = new TrayNotification();
                            tray.setTitle("Gestion PFE Posutler dans un offre");
                            tray.setMessage("Vous devez remplir votre Cv  ou vous a avez deja postuler dans cet offre!");
                            tray.setNotificationType(NotificationType.WARNING);
                            tray.setAnimationType(AnimationType.SLIDE);
                            tray.showAndDismiss(Duration.seconds(5));
                            
                }
                else
                    postuler.setDisable(false);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        postuler.setOnAction((ActionEvent d) -> {
            Platform.runLater(() -> {
                
                
                try {
                    demandess.Postuler(ConnectionController.usr.getId(),o.getId());
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                postuler.setDisable(true);
                 TrayNotification tray = new TrayNotification();
                            tray.setTitle("Gestion PFE Posutler dans un offre");
                            tray.setMessage("Operation Terminée avec succés !");
                            tray.setNotificationType(NotificationType.SUCCESS);
                            tray.setAnimationType(AnimationType.SLIDE);
                            tray.showAndDismiss(Duration.seconds(5));
               

            });
        });
              
        
        Label titreD = new Label();
        titreD.setText("Titre du stage : " + o.getTitre());
        titreD.setLayoutX(20);
        titreD.setLayoutY(190);
        titreD.setOpacity(5);
        titreD.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");
        Label nomEntreprise = new Label();
        nomEntreprise.setText("Nom de l'entreprise : " + usr.getNom());
        nomEntreprise.setLayoutX(20);
        nomEntreprise.setLayoutY(210);
        nomEntreprise.setStyle("-fx-text-fill: #000;-fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: 'Open Sans Light'");

        pane.getChildren().add(titreD);

        pane.getChildren().add(description);
        pane.getChildren().add(cD);
        pane.getChildren().add(nomEntreprise);
        pane.getChildren().add(postuler);
        gauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.BAR).animated(true)
                .animationDuration(1000)
                .prefSize(200, 200).barColor(Color.GREENYELLOW)
                .title("Votre chance")
                .unit("Pour être acceptée")
                .build();
        gauge.setLayoutX(650);
        gauge.setLayoutY(20);
        pane.getChildren().add(gauge);
        gauge.valueProperty().addListener(o -> {
            value.setText(String.format(Locale.US, "%.0f", gauge.getValue()) + "%");
        });

        value = new Label("entrain de calculer votre chance");
        value.setFont(Fonts.latoBold(20));
        value.setStyle("-fx-color: green");
        value.setAlignment(Pos.CENTER);
        value.setPrefWidth(400);

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 3_000_000_000l) {
                    try {
                        float score = calcscore() ;
                        gauge.setValue(score);
                        if(score == -1)
                        {
                            gauge.setValue(0);
                        }
                        value.setFont(Fonts.latoBold(30));
                        value.setLayoutX(670);
                        value.setLayoutY(30);
                        timer.stop();
                        // lastTimerCall = now;
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
    }

    @Override
    public void start(Stage stage) {
        value.setLayoutX(550);
        value.setLayoutY(200);
        value.setTextFill(Color.web("#fff"));
        pane.getChildren().add(value);

        Scene scene = new Scene(pane);

        stage.setTitle("L'offre \"" + o.getTitre() + "\"");
        stage.setScene(scene);
        stage.show();

        timer.start();

    }

    @Override
    public void stop() {
        System.exit(0);
    }
    float connu = 0;

    // ******************** Misc **********************************************
    private float calcscore() throws SQLException {
        Cv cv = CvServices.findCv();
        if (cv != null) {
            CompetencesTechniquesServices competences = new CompetencesTechniquesServices();
            List<CompetencesTechniques> listCompetences = competences.AfficherTechs(cv.getId());

            List<Technologie> technologies = TechnologiesServices.selectTechnologies(o);
            technologies.stream().forEach(a -> {
                listCompetences.stream().forEach(c -> {

                    if (a.getTech().equalsIgnoreCase(c.getCompetence())) {
                        System.out.println(a.getTech()+"   ///" +c.getCompetence() );
                        connu++;
                    }
                });
            });
            System.out.println((connu/technologies.size())*100);
            return (connu/technologies.size())*100;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
