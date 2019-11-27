package com.gn.module.movie;

import com.gn.Database.Database;
import com.gn.Main;
import com.gn.global.util.Alerts;
import com.gn.objects.Movie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditMovieController {
    HashMap<String,Movie> movie= new HashMap<>() ;
    @FXML
    private ComboBox<String> comboBoxx;

    @FXML
    private TextField name;

    @FXML
    private TextField detail;

    @FXML
    private TextField link;

    @FXML
    private Button addPhoto;

    @FXML
    private ImageView image;

    @FXML
    private Button edit;
    FileChooser fileChooser ;
    File file ;
    Image image2;
    int id;
    @FXML
    private Button delete;
    final ObservableList options = FXCollections.observableArrayList();
    public Alert alert;
    @FXML
    private void initialize() {

        // Within this initialize method, you can initialize the data for the ComboBox. I have changed the
        // method from fillComboBox2() to getData(), which returns a List of Strings.
        // We need to set the ComboBox to use that list.
        comboBoxx.setItems(FXCollections.observableArrayList(getData()));

        name.setDisable(true);
        detail.setDisable(true);
        link.setDisable(true);
        addPhoto.setDisable(true);
        edit.setDisable(true);
        delete.setDisable(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                alert = new Alert(Alert.AlertType.INFORMATION);
            }
        });



    }
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
    @FXML
    public void handleClickAddPhoto(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open  Image file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        this.file = fileChooser.showOpenDialog(null);
        if(file !=null){
            image2 = new Image(file.toURI().toString());
            image.setImage(image2);
            image.setPreserveRatio(true);

        }
    }

    @FXML
    public  void handleClickEditMovie(ActionEvent event) throws SQLException, IOException {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";
      if(file != null) {
          byte[] byteArray = new byte[(int) file.length()];
          FileInputStream inputStream = new FileInputStream(file);
          inputStream.read(byteArray);
          Blob blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
          try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection connection = Database.getConnection();

              PreparedStatement statement = connection.prepareStatement("UPDATE movies SET detail= ?,link=? , picture=? Where name = ?");
              statement.setString(1, detail.getText());
              statement.setString(2, link.getText());
              statement.setBlob(3, blob);
              statement.setString(4, name.getText());
              statement.executeUpdate();
              statement.close();
              connection.close();

              Alerts.success("แก้ไขเรียบร้อย", "แก้ไขข้อมูลของหนัง" + name.getText() + "เรียบร้อย");
              comboBoxx.setItems(FXCollections.observableArrayList(getData()));
              name.setDisable(true);
              detail.setDisable(true);
              link.setDisable(true);
              addPhoto.setDisable(true);
              edit.setDisable(true);
              delete.setDisable(true);
              name.setText("");
              detail.setText("");
              link.setText("");
              image.setImage(null);

          } catch (Exception ex) {
              System.out.println("error");
          }
      }
      else{
          try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection connection = Database.getConnection();
              PreparedStatement statement = connection.prepareStatement("UPDATE movies SET detail= ?,link=? Where name = ?");
              statement.setString(1, detail.getText());
              statement.setString(2, link.getText());
              statement.setString(3, name.getText());
              statement.executeUpdate();
              statement.close();
              connection.close();

              Alerts.success("แก้ไขเรียบร้อย", "แก้ไขข้อมูลของหนัง" + name.getText() + "เรียบร้อย");
              comboBoxx.setItems(FXCollections.observableArrayList(getData()));
              name.setDisable(true);
              detail.setDisable(true);
              link.setDisable(true);
              addPhoto.setDisable(true);
              edit.setDisable(true);
              delete.setDisable(true);
              name.setText("");
              detail.setText("");
              link.setText("");
              image.setImage(null);


          } catch (Exception ex) {
              System.out.println("error");
          }

      }
    }


    @FXML public
    void handleClickSearchMovie(ActionEvent event) {
        if(comboBoxx.getValue()==null){
            Alerts.warning("กรุณาเลือกหลัง", "กรุณาเลือกหนังที่จะแก้ไข" );
        }
        else{
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = Database.getConnection();
                String query = "Select * from movies where name = '"+comboBoxx.getValue()+"'";
                Statement stmt = connection.createStatement();
                //Query to get the number of rows in a table
                //Executing the query
                ResultSet resultSet = stmt.executeQuery(query);
                //Retrieving the result
                while (resultSet.next()){
                    id = resultSet.getInt(1);
                    name.setText(resultSet.getString(2));
                    detail.setText(resultSet.getString(3));
                    InputStream is = resultSet.getBinaryStream(4);
                    link.setText(resultSet.getString(5));
                    OutputStream os = new FileOutputStream(new File("picture.jpg"));
                    byte[] content = new byte[1024];
                    int size = 0;
                    while ((size = is.read(content)) != -1){
                        os.write(content,0,size);
                    }
                    os.close();
                    is.close();
                    Image im = new Image("file:picture.jpg",130,170,true,true);
                    Image big_image = new Image("file:picture.jpg",542,782,true,true);
                    image.setImage(im);
                    detail.setDisable(false);
                    link.setDisable(false);
                    addPhoto.setDisable(false);
                    edit.setDisable(false);
                    delete.setDisable(false);
                }
            } catch (Exception ex){

            }
        }
    }
    @FXML
    public void handleClickDeleteMovie(ActionEvent event) throws IOException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movies WHERE id=? ");
            statement.setInt(1,id);
            statement.executeUpdate();
            statement.close();
            PreparedStatement statement2 = connection.prepareStatement("DELETE FROM programs WHERE movie_id=? ");
            statement2.setInt(1, id);
            statement2.executeUpdate();
            statement2.close();
            connection.close();
            Alerts.success("ลบเรียบร้อย", "ลบหนังชื่อ" + name.getText() + "เรียบร้อย");
            comboBoxx.setItems(FXCollections.observableArrayList(getData()));
            name.setDisable(true);
            detail.setDisable(true);
            link.setDisable(true);
            addPhoto.setDisable(true);
            edit.setDisable(true);
            delete.setDisable(true);
            name.setText("");
            detail.setText("");
            link.setText("");
            image.setImage(null);


        } catch (Exception ex) {

        }

    }
}

