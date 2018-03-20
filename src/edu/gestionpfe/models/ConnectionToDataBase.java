/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gestionpfe.models;

/**
 *
 * @author user
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed
 */
public class ConnectionToDataBase {

    private static ConnectionToDataBase instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/gestionpfe?autoReconnect=true&useSSL=false";
    private String userName = "root";
    private String password = "";

    private ConnectionToDataBase()  {
        try {
            this.connection = DriverManager.getConnection(url, userName, password);

        } catch (SQLException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     *
     * @return @throws SQLException
     */
    public static ConnectionToDataBase getInstance()  {
        if (instance == null) {
            instance = new ConnectionToDataBase();
        } else try {
            if (instance.getConnection().isClosed()) {
                instance = new ConnectionToDataBase();
            }
        } catch (SQLException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }

        return instance;
    }

    public static void closeConnection(Connection conn) {

        try {

            conn.close();

        } catch (SQLException e) {

        }

    }

}

