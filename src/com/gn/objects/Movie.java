package com.gn.objects;

import com.gn.module.movie.HomeController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Movie {
    private int id;
    private String name;
    private String detail;
    private Button button;
    private ImageView picture;

    public Movie(int id ,String name , String detail , Image img){
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.button = new Button("รายละเอียด");
        this.picture = new ImageView(img);
    }

    public static HashMap<String,Movie> getMoviesData(Connection connection){
        HashMap<String,Movie> movies = new HashMap<>();
        try {
            String query = "Select * from movies";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int movie_id = resultSet.getInt(1);
                String movie_name = resultSet.getString(2);
                String movie_detail = resultSet.getString(3);
                InputStream is = resultSet.getBinaryStream(4);
                OutputStream os = new FileOutputStream(new File("picture.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content)) != -1){
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                Image image = new Image("file:picture.jpg",100,150,true,true);
                movies.put(Integer.toString(movie_id),new Movie(movie_id,movie_name,movie_detail,image));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return movies;
    }

    public static ArrayList<String> getMovieKey(Connection connection){
        ArrayList<String> key = new ArrayList<>();
        HashMap<String,Movie> movies = new HashMap<>();
        try {
            String query = "Select * from movies";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int movie_id = resultSet.getInt(1);
                key.add(Integer.toString(movie_id));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public Button getButton() {
        return button;
    }

    public ImageView getPicture() {
        return picture;
    }

    public int getId() {
        return id;
    }

    private void setButtonAction(EventHandler e){
        button.setOnAction(e);
    }
}
