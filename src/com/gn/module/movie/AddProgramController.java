package com.gn.module.movie;

import com.gn.Database.Database;
import com.gn.Main;
import com.gn.global.util.Alerts;
import com.gn.objects.Movie;
import com.gn.objects.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProgramController {
    HashMap<String,Movie> movie= new HashMap<>() ;
    Program program = null;
    @FXML
    private DatePicker date;
    final ObservableList options = FXCollections.observableArrayList();
    @FXML
    private Button addProgram;

    // If you ComboBox is going to display Strings, you should define that datatype here
    @FXML
    private ComboBox<String> comboBoxx;
    @FXML
    private CheckBox time9;

    @FXML
    private CheckBox time12;

    @FXML
    private CheckBox time15;

    @FXML
    private CheckBox time18;

    public static AddProgramController apc;

    @FXML
    private void initialize() {
        apc = this;
        // Within this initialize method, you can initialize the data for the ComboBox. I have changed the
        // method from fillComboBox2() to getData(), which returns a List of Strings.
        // We need to set the ComboBox to use that list.
        comboBoxx.setItems(FXCollections.observableArrayList(getData()));
        LocalDate minDate =  LocalDate.now();

        date.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable( item.isBefore(minDate) || empty || item.getDayOfWeek() == DayOfWeek.SUNDAY|| empty || item.getDayOfWeek() == DayOfWeek.SATURDAY);

                    }});

        addProgram.setDisable(true);
        time9.setDisable(true);
        time12.setDisable(true);
        time15.setDisable(true);
        time18.setDisable(true);


    }

    public void load(){
        comboBoxx.setItems(FXCollections.observableArrayList(getData()));
        LocalDate minDate =  LocalDate.now();

        date.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable( item.isBefore(minDate) || empty || item.getDayOfWeek() == DayOfWeek.SUNDAY|| empty || item.getDayOfWeek() == DayOfWeek.SATURDAY);

                    }});

        addProgram.setDisable(true);
        time9.setDisable(true);
        time12.setDisable(true);
        time15.setDisable(true);
        time18.setDisable(true);
    }


    /**
     * Here we will define the method that builds the List used by the ComboBox
     */
    private List<String> getData() {

        String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";

        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = Database.getConnection();
            String query = "select * from movies";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet set = statement.executeQuery();

            while (set.next()) {
                options.add(set.getString(2));
                movie.put(set.getString(2),new Movie(set.getInt(1),set.getString(2),set.getString(3),set.getString(5)));
            }

            statement.close();
            set.close();

            // Return the List
            return options;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    @FXML public void handleClickAddProgram(ActionEvent e)  {
        Date date1 = java.sql.Date.valueOf(date.getValue());
        program = Program.create(movie.get((comboBoxx.getValue())),date1);
        if(time9.isSelected()){
            program.addShowTime("09.00");
        }
        if(time12.isSelected()){
            program.addShowTime("12.00");
        }
        if(time15.isSelected()){
            program.addShowTime("15.00");
        }
        if(time18.isSelected()){
            program.addShowTime("18.00");
        }
        Alerts.success("เพิ่มหนังสำเร็จ ", "เพิ่มโปรแกรมหนังสำเร็จ");
        HomeController.HomeCtr.refresh();



    }
    @FXML
    public void handleClickCheckDate(ActionEvent event) {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";
        Date date1 = java.sql.Date.valueOf(date.getValue());
        // Define the data you will be returning, in this case, a List of Strings for the ComboBox

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = Database.getConnection();
            String query = "SELECT COUNT(*)  FROM programs where DATE = '" +date1+ "'";
            Statement stmt = connection.createStatement();
            //Query to get the number of rows in a table
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            //Retrieving the result
            rs.next();
            int count = rs.getInt(1);
            if(count<4){
                addProgram.setDisable(false);
                String query900 = "SELECT COUNT(*) FROM showtimes where  time = '09.00' AND DATE = '" +date1+ "'";
                String query1200 = "SELECT COUNT(*) FROM showtimes where  time = '12.00' AND DATE = '" +date1+ "'";
                String query1500 = "SELECT COUNT(*) FROM showtimes where  time = '15.00' AND  DATE = '" +date1+ "'";
                String query1800 = "SELECT COUNT(*) FROM showtimes where  time = '18.00' AND DATE = '" +date1+ "'";
                ResultSet rs900 = stmt.executeQuery(query900);
                rs900.next();
                int count900 = rs900.getInt(1);
                if(count900!=1){
                    time9.setDisable(false);

                }
                else{
                    time9.setDisable(true);
                }
                ResultSet rs1200 = stmt.executeQuery(query1200);
                rs1200.next();
                int count1200 = rs1200.getInt(1);
                if(count1200!=1){
                    time12.setDisable(false);
                }
                else{
                    time12.setDisable(true);
                }
                ResultSet rs1500 = stmt.executeQuery(query1500);
                rs1500.next();

                int count1500 = rs1500.getInt(1);
                if(count1500!=1){
                    time15.setDisable(false);
                }
                else{
                    time15.setDisable(true);
                }
                ResultSet rs1800 = stmt.executeQuery(query1800);
                rs1800.next();
                int count1800 = rs1800.getInt(1);
                if(count1800!=1){
                    time18.setDisable(false);
                }
                else{
                    time18.setDisable(true);
                }



            }
            else{
                addProgram.setDisable(true);
                time9.setDisable(true);
                time12.setDisable(true);
                time15.setDisable(true);
                time18.setDisable(true);
            }





        } catch (ClassNotFoundException | SQLException ex) {


        }
    }
}