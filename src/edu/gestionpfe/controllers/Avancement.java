package edu.gestionpfe.controllers;

import edu.gestionpfe.services.TachesServices;
import eu.hansolo.medusa.Fonts;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import java.sql.SQLException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Avancement extends Application {

    private Gauge gauge;
    private Label value;
    private long lastTimerCall;
    private AnimationTimer timer;
    public static int idStage;

    @Override
    public void init() throws SQLException, Exception {
        gauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN).animated(true)
                .animationDuration(1000)
                .prefSize(200, 200).barColor(Color.GREENYELLOW)
                .title("Votre chance")
                .unit("Pour être acceptée")
                .build();

        gauge.valueProperty().addListener(o -> {
            value.setText(String.format(Locale.US, "%.0f", gauge.getValue()) + "% Terminé pour son stage");
            
        });

        
        value = new Label("entrain de calculer l'avencement");
        value.setFont(Fonts.latoBold(20));
        
        value.setStyle("-fx-text-fill:red");
        value.setAlignment(Pos.CENTER);
        value.setPrefWidth(400);

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 3_000_000_000l) {
                    try {
                        //  try {
                        float score = calcAvencement();
                        System.out.println(idStage);
                        gauge.setValue(TachesServices.selectTachesValid(idStage));
                        
                        value.setFont(Fonts.latoBold(30));
                        value.setStyle("-fx-text-fill:red");
                        value.setLayoutX(670);
                        value.setLayoutY(30);
                        timer.stop();
                        // lastTimerCall = now;
                    } catch (SQLException ex) {
                        Logger.getLogger(Avancement.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        };
    }

    @Override
    public void start(Stage stage) {
        Pane pane = new AnchorPane();

        pane.setStyle(
                "-fx-background-color: #696969;"
                + "-fx-background-image: url("
                + "'/edu/gestionpfe/views/images/backgrounds/cource-search-bg.jpg'"
                + "); "
                + "-fx-background-size: cover;"
                + "-fx-background-radius: 10;"
        );

        pane.setPrefSize(1200, 700);

        value.setLayoutX(550);
        value.setLayoutY(200);
        value.setTextFill(Color.web("#fff"));
        pane.getChildren().add(value);
        pane.getChildren().add(gauge);

        Scene scene = new Scene(pane);

        stage.setTitle("Avancement ");
        stage.setScene(scene);
        stage.show();

        timer.start();

    }

    @Override
    public void stop() {
        System.exit(0);
    }

    // ******************** Misc **********************************************
    private float calcAvencement() throws SQLException {

        return 50;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
