package edu.gestionpfe.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import edu.gestionpfe.models.Soutenance;
import edu.gestionpfe.services.SoutenanceServices;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AffectationPresidentController implements Initializable {

    
    private Alert al;
    private ButtonType comm;
    private ButtonType tech;
    private ButtonType can;
    
    ArrayList<Soutenance> dis = new ArrayList<>();
    SoutenanceServices ds = new SoutenanceServices();
    @FXML
    private AnchorPane anch;

    public void affichage(Calendar nonvalide, Calendar valide) { //int nbr=0;

        //nbr=ds.selectcountdispo();
        dis = ds.selectDisponibilites();
        for (Soutenance d : dis) {
            //   int i = ds.Verif(d);
            //    if (i == 3) {
            try {
                Entry n = new Entry();

                // LocalDate date = d.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                //  ZoneId z =(ZoneId) d.getHeure_debut();
                LocalDate date = Instant.ofEpochMilli(d.getDatesoutenancetec().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalTime t = d.getHeuredeb().toLocalTime();

                LocalTime f = d.getHeurefin().toLocalTime();
                LocalDateTime dt = LocalDateTime.of(date, t);
                LocalDateTime df = LocalDateTime.of(date, f);
                n.setTitle("Soutenance Technique");
                n.setInterval(t, f);
                n.setInterval(dt, df);
                valide.addEntries(n);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());

            }
            //    } else {
            try {
                Entry n1 = new Entry();

//             LocalDate date = d.getDatesoutenancecom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                //  ZoneId z =(ZoneId) d.getHeure_debut();
                LocalDate date1 = Instant.ofEpochMilli(d.getDatesoutenancecom().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalTime t1 = d.getHeuredeb().toLocalTime();

                LocalTime f1 = d.getHeurefin().toLocalTime();
                LocalDateTime dt1 = LocalDateTime.of(date1, t1);
                LocalDateTime df1 = LocalDateTime.of(date1, f1);
                n1.setTitle("Soutenance Commerciale");
                n1.setInterval(t1, f1);
                n1.setInterval(dt1, df1);
                nonvalide.addEntries(n1);
                //}
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());

            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        al=new Alert(Alert.AlertType.CONFIRMATION);
        al.setTitle("Soutenance");
        al.setHeaderText("Soutenance");
        al.setContentText("Choisir le type de soutenance");
        al.getButtonTypes().clear();
        comm = new ButtonType("Commerciale",ButtonBar.ButtonData.OK_DONE);
        tech = new ButtonType("Technique",ButtonBar.ButtonData.OK_DONE);
        can = new ButtonType("Cancel",ButtonBar.ButtonData.OK_DONE);
        al.getButtonTypes().addAll(comm,tech,can);
        // CalendarView calendarView = new CalendarView();
        CalendarView calendarView = new CalendarView();
        Calendar valide = new Calendar("Soutenances Techniques");
        Calendar nonvalide = new Calendar("Soutenances Commerciales ");

        valide.setStyle(Style.STYLE1);
        nonvalide.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("Type des soutenances");

        myCalendarSource.getCalendars().addAll(valide, nonvalide);

        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        ;
        };
                
      nonvalide.setReadOnly(true);
        valide.setReadOnly(true);

        affichage(nonvalide, valide);
        calendarView.setDefaultCalendarProvider(param -> nonvalide);
        calendarView.addEventHandler(CalendarEvent.CALENDAR_CHANGED, new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent event) {
                

            }
        });

        nonvalide.addEventHandler(new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent event) {

              //  
                    dis = ds.selectDisponibilites();
                    
                    Optional<ButtonType> result = al.showAndWait();
                    if(result.isPresent() && result.get()==comm)
                    {
                   try {
                   FXMLLoader root = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/Soutenance/Soutenance.fxml"));
                    AnchorPane x = root.load();
                    SoutenanceController c = root.getController();

                    anch.getChildren().clear();
                    anch.getChildren().add((Node) x);
                } catch (IOException ex) {
                    Logger.getLogger(SoutenanceController.class.getName()).log(Level.SEVERE, null, ex);
                }}
                     if(result.isPresent() && result.get()==tech)
                    {
                   try {
                   FXMLLoader root = new FXMLLoader(getClass().getResource("/edu/gestionpfe/views/Soutenance/SoutenanceTech.fxml"));
                    AnchorPane x = root.load();
                    SoutenanceTechController c = root.getController();

                    anch.getChildren().clear();
                    anch.getChildren().add((Node) x);
                } catch (IOException ex) {
                    Logger.getLogger(SoutenanceController.class.getName()).log(Level.SEVERE, null, ex);
                }}
            }
        });

        Stage primaryStage = new Stage();
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        anch.getChildren().add(calendarView);
        // Scene scene = new Scene(anch);
        /* primaryStage.setTitle("Soutenances");
         primaryStage.setScene(scene);
         primaryStage.setWidth(1300);
         primaryStage.setHeight(1000);
         primaryStage.centerOnScreen();
         primaryStage.show();*/
    }

}

   //
