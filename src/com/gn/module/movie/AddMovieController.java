package com.gn.module.movie;
import com.gn.Database.Database;
import com.gn.global.plugin.ViewManager;
import com.gn.global.util.Alerts;
import com.gn.module.main.Main;
import com.gn.objects.Movie;
import com.gn.objects.Seat;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import static sun.nio.ch.IOUtil.load;

public class AddMovieController {


    @FXML
    TextField name;
    @FXML
    TextField link;
    @FXML
    TextArea detail;

    @FXML  ImageView myImageView;
    FileChooser fileChooser ;
    File file ;
    Image image;
    Movie movie = null;
    @FXML
    Label label;
    public Alert alert;
    public void initialize(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                alert = new Alert(Alert.AlertType.INFORMATION);
            }
        });
    }


    @FXML public void handleClickAddPhoto(ActionEvent e) {

        fileChooser = new FileChooser();
        fileChooser.setTitle("Open  Image file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        this.file = fileChooser.showOpenDialog(null);
        if(file !=null){
            image = new Image(file.toURI().toString());
            myImageView.setImage(image);
            myImageView.setPreserveRatio(true);

        }

    }
    @FXML public void handleClickAddMovie(ActionEvent e) throws IOException, SQLException {
        if (name.getText() == null || detail.getText() == null || link.getText() == null || myImageView.getImage() == null) {
            Alerts.error("เพิ่มหนังไม่สำเร็จ", "กรุณากรอกข้อมูลให้ครบ");
        } else {
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";
            // Define the data you will be returning, in this case, a List of Strings for the ComboBox
            int count = 0;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = Database.getConnection();
                String query = "SELECT COUNT(*)  FROM movies where name = '" + name.getText() + "'";
                Statement stmt = connection.createStatement();
                //Query to get the number of rows in a table
                //Executing the query
                ResultSet rs = stmt.executeQuery(query);
                //Retrieving the result
                rs.next();
                count = rs.getInt(1);


            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("fail");

            }
            if (count == 0) {
                byte[] byteArray = new byte[(int) file.length()];
                FileInputStream inputStream = new FileInputStream(file);
                inputStream.read(byteArray);
                Blob blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
                movie.create(file, name.getText(), detail.getText(), link.getText());
                Alerts.success("เพิ่มหนังสำเร็จ ", "เพิ่มหนัง " + name.getText() + " สำเร็จ");
                HomeController.HomeCtr.refresh();
            } else {
                Alerts.error("เพิ่มหนังไม่สำเร็จ", "ขออภัยหนังชื่อ" + name.getText() + " มีในระบบแล้ว");
            }
            AddProgramController.apc.load();
            load();
            name.setText("");
            link.setText("");
            detail.setText("");
            myImageView.setImage(null);

        }
    }


    }




