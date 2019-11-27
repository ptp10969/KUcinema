package com.gn.module.movie;

import com.gn.Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.sql.*;

public class CheckProgramController {
//    @FXML
//    private DatePicker date;
//    @FXML
//    private Label movie900;
//
//    @FXML
//    private Label movie1200;
//
//    @FXML
//    private Label movie1500;
//
//    @FXML
//    private Label movie1800;
//
//    @FXML
//    private Button delete900;
//
//    @FXML
//    private Button delete1200;
//
//    @FXML
//    private Button delete1800;
//
//    @FXML
//    void delete1200(ActionEvent event) {
//
//    }
//
//    @FXML
//    void delete1500(ActionEvent event) {
//
//    }
//    @FXML
//    void delete900(ActionEvent event) {
//
//    }
//
//    @FXML
//    void delete1800(ActionEvent event) {
//
//    }
//
//            @FXML
//            public void handleClickCheckDate(ActionEvent event) {
//                String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";
//                Date date1 = java.sql.Date.valueOf(date.getValue());
//                // Define the data you will be returning, in this case, a List of Strings for the ComboBox
//
//                try {
//                    Class.forName("com.mysql.cj.jdbc.Driver");
//                    Connection connection = Database.getConnection();
//                    String query = "SELECT COUNT(*)  FROM programs where DATE = '" +date1+ "'";
//                    Statement stmt = connection.createStatement();
//                    //Query to get the number of rows in a table
//                    //Executing the query
//                    ResultSet rs = stmt.executeQuery(query);
//                    //Retrieving the result
//                    rs.next();
//                    int count = rs.getInt(1);
//                    if(count>0){
//
//                        String query900 = "SELECT COUNT(*) FROM showtimes where  time = '09.00' AND DATE = '" +date1+ "'";
//                        String query1200 = "SELECT COUNT(*) FROM showtimes where  time = '12.00' AND DATE = '" +date1+ "'";
//                        String query1500 = "SELECT COUNT(*) FROM showtimes where  time = '15.00' AND  DATE = '" +date1+ "'";
//                        String query1800 = "SELECT COUNT(*) FROM showtimes where  time = '18.00' AND DATE = '" +date1+ "'";
//                        ResultSet rs900 = stmt.executeQuery(query900);
//                        rs900.next();
//                        int count900 = rs900.getInt(1);
//                        if(count900!=1){
//                            time9.setDisable(false);
//
//                        }
//                        else{
//                            time9.setDisable(true);
//                        }
//                        ResultSet rs1200 = stmt.executeQuery(query1200);
//                        rs1200.next();
//                        int count1200 = rs1200.getInt(1);
//                        if(count1200!=1){
//                            time12.setDisable(false);
//                        }
//                        else{
//                            time12.setDisable(true);
//                        }
//                        ResultSet rs1500 = stmt.executeQuery(query1500);
//                        rs1500.next();
//
//                        int count1500 = rs1500.getInt(1);
//                        if(count1500!=1){
//                            time15.setDisable(false);
//                        }
//                        else{
//                            time15.setDisable(true);
//                        }
//                        ResultSet rs1800 = stmt.executeQuery(query1800);
//                        rs1800.next();
//                        int count1800 = rs1800.getInt(1);
//                        if(count1800!=1){
//                            time18.setDisable(false);
//                        }
//                        else{
//                            time18.setDisable(true);
//                        }
//
//
//
//                    }
//                    else{
//                        addProgram.setDisable(true);
//                        time9.setDisable(true);
//                        time12.setDisable(true);
//                        time15.setDisable(true);
//                        time18.setDisable(true);
//                    }
//
//
//
//
//
//                } catch (ClassNotFoundException | SQLException ex) {
//
//
//                }
//            }
//        }
//
//
//
//
//
//
//

}
