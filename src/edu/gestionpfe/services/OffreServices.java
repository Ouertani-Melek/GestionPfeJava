/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.services;

import static edu.gestionpfe.controllers.ConnectionController.usr;
import edu.gestionpfe.models.ConnectionToDataBase;
import edu.gestionpfe.models.Offre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahia
 */
public class OffreServices {
    public static List<Offre> SelectOffreByUser() throws SQLException
    {
        List<Offre> offres=new ArrayList<>();
        
            
            String qql="select * from offre where iduser="+usr.getId();
            System.out.println(usr.getId());
            Connection connection = ConnectionToDataBase.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(qql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                Offre o = new Offre();
                    o.setId(resultSet.getInt("id"));
                    offres.add(o);
                    
            }
        
        return offres;
    }
    
}   
    
