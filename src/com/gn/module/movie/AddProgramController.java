package com.gn.module.movie;

import com.gn.Database.Database;
import com.gn.Main;
import com.gn.objects.Movie;
import com.gn.objects.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import java.sql.*;
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


    // If you ComboBox is going to display Strings, you should define that datatype here
    @FXML
    private ComboBox<String> comboBoxx;

    @FXML
    private void initialize() {

        // Within this initialize method, you can initialize the data for the ComboBox. I have changed the
        // method from fillComboBox2() to getData(), which returns a List of Strings.
        // We need to set the ComboBox to use that list.
        comboBoxx.setItems(FXCollections.observableArrayList(getData()));


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
            Connection connection = Database.connect("localhost/se_db", "root", "");
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
    program.create(movie.get((comboBoxx.getValue())),date1);



    }
}