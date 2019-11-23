package com.gn.module.movie;
import com.gn.Main;
import com.gn.global.plugin.ViewManager;
import com.gn.objects.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import java.sql.Blob;
import java.sql.SQLException;

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
        byte[] byteArray = new byte[(int) file.length()];
        FileInputStream inputStream =  new FileInputStream(file);
        inputStream.read(byteArray);
        Blob blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
        movie.create(file,name.getText(),detail.getText(),link.getText());



    }

    }




