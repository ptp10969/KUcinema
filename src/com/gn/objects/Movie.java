package com.gn.objects;

import animatefx.animation.Swing;
import com.gn.Database.Database;
import com.gn.module.movie.HomeController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Movie {
    private int id;
    private String name;
    private String detail;
    private Button button;
    private ImageView picture;
    private ImageView big_picture;
    private String youtube_link;


    public Movie(int id ,String name , String detail , Image img,Image big_img , String youtube_link){
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.button = new Button("รายละเอียด");
        this.picture = new ImageView(img);
        this.big_picture = new ImageView(big_img);
        this.youtube_link = youtube_link;
    }
    public Movie(int id ,String name , String detail , String youtube_link){
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.youtube_link = youtube_link;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }

    public void setBig_picture(ImageView big_picture) {
        this.big_picture = big_picture;
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
                String youtube_link = resultSet.getString(5);
                OutputStream os = new FileOutputStream(new File("picture.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content)) != -1){
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                Image image = new Image("file:picture.jpg",130,170,true,true);
                Image big_image = new Image("file:picture.jpg",542,782,true,true);
                movies.put(Integer.toString(movie_id),new Movie(movie_id,movie_name,movie_detail,image,big_image,youtube_link));
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
    public static void create(File file,String name , String detail,String link) throws IOException, SQLException {
        byte[] byteArray = new byte[(int) file.length()];
        FileInputStream inputStream =  new FileInputStream(file);
        inputStream.read(byteArray);
        Blob blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
        try {
            Connection connection = Database.getConnection();
            String sql = "INSERT INTO movies(name,detail,picture,link)"
                    + "VALUES(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,name);
            pstmt.setString(2, detail);
            pstmt.setBlob(3, blob) ;
            pstmt.setString(4, link);
            pstmt.executeUpdate();
        } catch (Exception ex){ }

    }
    private void setButtonAction(EventHandler e){
        button.setOnAction(e);
    }

    public ImageView getBig_picture() {
        return big_picture;
    }

    public String getYoutube_link() {
        return youtube_link;
    }
    public int checkMovie(String nameMovie){
        String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=TestDB;integratedSecurity=true;";
        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        int count =0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = Database.getConnection();
            String query = "SELECT COUNT(*)  FROM movies where name = '" +nameMovie+ "'";
            Statement stmt = connection.createStatement();
            //Query to get the number of rows in a table
            //Executing the query
            ResultSet rs = stmt.executeQuery(query);
            //Retrieving the result
            rs.next();
            count = rs.getInt(1);


        } catch (ClassNotFoundException | SQLException ex) {


        }
        return  count;
    }
}
