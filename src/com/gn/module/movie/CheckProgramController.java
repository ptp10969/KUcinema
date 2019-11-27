package com.gn.module.movie;

import com.gn.Database.Database;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class CheckProgramController {
    @FXML
    private DatePicker date;
    @FXML
    private Label movie900;

    @FXML
    private Label movie1200;

    @FXML
    private Label movie1500;

    @FXML
    private Label movie1800;

    @FXML
    private Button delete900;

    @FXML
    private Button delete1200;
    @FXML
    private Button delete1500;
    @FXML
    private Button delete1800;

    int idP9;
    String M9;
    int idP12;
    String M12;
    String M15;
    int idP15;
    int idP18;
    String M18;

    @FXML
    private void initialize() {


        LocalDate minDate = LocalDate.now();

        date.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate) || empty || item.getDayOfWeek() == DayOfWeek.SUNDAY || empty || item.getDayOfWeek() == DayOfWeek.SATURDAY);

                    }
                });
        delete900.setDisable(true);
        delete1200.setDisable(true);
       delete1500.setDisable(true);
        delete1800.setDisable(true);


    }

    @FXML
    void delete1200(ActionEvent event) {

    }

    @FXML
    void delete1500(ActionEvent event) {

    }

    @FXML
    void delete900(ActionEvent event) {

    }

    @FXML
    void delete1800(ActionEvent event) {

    }


    @FXML
    public void handleClickCheckDate(ActionEvent event) {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";
        Date date1 = java.sql.Date.valueOf(date.getValue());
        // Define the data you will be returning, in this case, a List of Strings for the ComboBox

        try {
            Connection connection = Database.getConnection();
            String query = "SELECT COUNT(*)  FROM programs where DATE = '" +date1+ "'";
            PreparedStatement stmt = connection.prepareStatement(query);
            //Query to get the number of rows in a table
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            //Retrieving the result
            rs.next();
            int count = rs.getInt(1);
            if(count==0) {
                delete900.setDisable(true);
                delete1200.setDisable(true);
                delete1500.setDisable(true);
                delete1800.setDisable(true);
                movie900.setText("");
                movie1200.setText("");
                movie1500.setText("");
                movie1800.setText("");
            }
            else {

                String queryShowTime9 = "SELECT * FROM showtimes Where TIME = '09.00' AND DATE = '" + date1 + "'";
                PreparedStatement stmtShowTime9 = connection.prepareStatement(queryShowTime9);
                ResultSet rsShowTime9 = stmtShowTime9.executeQuery(queryShowTime9);
                rsShowTime9.next();

                idP9 = rsShowTime9.getInt(3);
                String queryProgram9 = "SELECT * FROM programs Where id = '" + idP9 + "'";
                Statement stmtProgram9 = connection.createStatement();
                ResultSet rsProgram9 = stmtProgram9.executeQuery(queryProgram9);
                rsProgram9.next();

                M9 = rsProgram9.getString(2);
                String queryMovie9 = "SELECT * FROM movies Where id = '" + M9 + "'";
                Statement stmtMovie9 = connection.createStatement();
                ResultSet rsMovie9 = stmtMovie9.executeQuery(queryMovie9);
                rsMovie9.next();
                if (rsMovie9.getString(2) != null) {
                    movie900.setText(rsMovie9.getString(2));
                    delete900.setDisable(false);
                }
                if (rsMovie9.getString(2) == null) {
                    movie900.setText("");
                    delete900.setDisable(true);
                }


                String queryShowTime12 = "SELECT * FROM showtimes Where TIME = '12.00' AND DATE = '" + date1 + "'";
                PreparedStatement stmtShowTime12 = connection.prepareStatement(queryShowTime9);
                ResultSet rsShowTime12 = stmtShowTime12.executeQuery(queryShowTime12);
                rsShowTime12.next();

                idP12 = rsShowTime12.getInt(3);
                String queryProgram12 = "SELECT * FROM programs Where id = '" + idP12 + "'";
                Statement stmtProgram12 = connection.createStatement();
                ResultSet rsProgram12 = stmtProgram12.executeQuery(queryProgram12);
                rsProgram12.next();

                M12 = rsProgram12.getString(2);
                String queryMovie12 = "SELECT * FROM movies Where id = '" + M12 + "'";
                Statement stmtMovie12 = connection.createStatement();
                ResultSet rsMovie12 = stmtMovie12.executeQuery(queryMovie12);
                rsMovie12.next();
                if (rsMovie12.getString(2) != null) {
                    movie1200.setText(rsMovie12.getString(2));
                    delete1200.setDisable(false);
                }
                String queryShowTime15 = "SELECT * FROM showtimes Where TIME = '15.00' AND DATE = '" + date1 + "'";
                PreparedStatement stmtShowTime15 = connection.prepareStatement(queryShowTime15);
                ResultSet rsShowTime15 = stmtShowTime15.executeQuery(queryShowTime15);
                rsShowTime15.next();

                idP15 = rsShowTime15.getInt(3);
                String queryProgram15 = "SELECT * FROM programs Where id = '" + idP15 + "'";
                Statement stmtProgram15 = connection.createStatement();
                ResultSet rsProgram15 = stmtProgram15.executeQuery(queryProgram15);
                rsProgram15.next();

                M15 = rsProgram15.getString(2);
                String queryMovie15 = "SELECT * FROM movies Where id = '" + M15 + "'";
                Statement stmtMovie15 = connection.createStatement();
                ResultSet rsMovie15 = stmtMovie15.executeQuery(queryMovie15);
                rsMovie15.next();
                if (rsMovie15.getString(2) != null) {
                    movie1500.setText(rsMovie12.getString(2));
                    delete1500.setDisable(false);
                }
                String queryShowTime18 = "SELECT * FROM showtimes Where TIME = '18.00' AND DATE = '" + date1 + "'";
                PreparedStatement stmtShowTime18 = connection.prepareStatement(queryShowTime18);
                ResultSet rsShowTime18 = stmtShowTime18.executeQuery(queryShowTime18);
                rsShowTime15.next();

                idP18 = rsShowTime18.getInt(3);
                String queryProgram18 = "SELECT * FROM programs Where id = '" + idP18 + "'";
                Statement stmtProgram18 = connection.createStatement();
                ResultSet rsProgram18 = stmtProgram18.executeQuery(queryProgram18);
                rsProgram18.next();

                M18 = rsProgram18.getString(2);
                String queryMovie18 = "SELECT * FROM movies Where id = '" + M18 + "'";
                Statement stmtMovie18 = connection.createStatement();
                ResultSet rsMovie18 = stmtMovie15.executeQuery(queryMovie18);
                rsMovie18.next();
                if (rsMovie18.getString(2) != null) {
                    movie1800.setText(rsMovie18.getString(2));
                    delete1800.setDisable(false);
                }
            }
        } catch ( SQLException ex) {
            System.out.println("error");

        }
    }
}
