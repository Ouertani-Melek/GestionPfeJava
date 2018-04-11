/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Melek OUERTANI
 */
public class AffectationController implements Initializable {
    @FXML
    private TableColumn<?, ?> type;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> heure;
    @FXML
    private TableView<?> listsoutenance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
