/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import edu.gestionpfe.services.OffreServices;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ahmed
 */
public class LineChartController {

    ObservableList<XYChart.Series<String, Number>> series = FXCollections.observableArrayList();
    int temp=0;
    public LineChart drawData()  {
        
        try {
            ObservableList<XYChart.Data<String, Number>> series1Data = FXCollections.observableArrayList();
            //series1Data.add(new XYChart.Data<String, Number>(XYChart., Number.class.cast(0)));
            OffreServices.getDemandesByIdOffre(OffreServices.seletMaxPostuledOffre().getId()).forEach(y ->{
                if(y.getId() == -1)
                {
                return ;
                }
                temp++;
                series1Data.add(new XYChart.Data<String, Number>(y.getDateDemande().toString(), Number.class.cast(temp)));
            });
            
            series.add(new XYChart.Series<>("Votre meilleur offre", series1Data));
            AnchorPane aa = new AnchorPane();
            NumberAxis numberAxis = new NumberAxis();
            Axis axis = new CategoryAxis();
            axis.setLabel("Date de postulation");
            numberAxis.setLabel("Nombre de Postulation");
            
            LineChart lineChart = new LineChart(axis, numberAxis, series);  
            //lineChart.axisSortingPolicyProperty(LineChart.SortingPolicy.X_AXIS);
           
            return lineChart;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());        }
        return new LineChart(new NumberAxis(), new NumberAxis());
    }
}
