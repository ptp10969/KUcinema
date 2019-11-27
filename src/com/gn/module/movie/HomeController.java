package com.gn.module.movie;

import com.gn.Database.Database;
import com.gn.global.plugin.ViewManager;
import com.gn.module.main.Main;
import com.gn.objects.Movie;
import com.gn.objects.Program;
import com.gn.objects.Seat;
import com.gn.objects.ShowTime;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;



public class HomeController {
    @FXML private Pane container;
    @FXML TableView<Movie> movie_list;
    @FXML TableColumn<Movie,String> movie_column_name;
    @FXML TableColumn<Movie,String> movie_column_detail;
    @FXML TableColumn<Movie, Button> movie_column_button;
    @FXML TableColumn<Movie, ImageView> movie_column_picture;
    @FXML TableView<Program> program_list;
    @FXML TableColumn<Program,String> program_column_name;
    @FXML TableColumn<Program,String> program_column_detail;
    @FXML TableColumn<Program, Button> program_column_button;
    @FXML TableColumn<Program, Button> program_column_button_showtime;
    @FXML TableColumn<Program, ImageView> program_column_picture;


    public static HomeController HomeCtr;
    public HashMap<String,Program> programs;
    public ArrayList<String> keys;

    public void initialize(){
        programs = Program.readProgram(Main.ctrl.connection,Main.ctrl.movies);
        keys = Program.getProgramsKey(Main.ctrl.connection);
        HomeCtr = this;
        for (String k : Main.ctrl.keys){
            Movie m = Main.ctrl.movies.get(k);
            m.getButton().setOnAction(e ->
                    detail(Integer.toString(m.getId())));
        }
        for (String k : keys){
            Program p = programs.get(k);
            p.getButton().setOnAction(e ->
                    showTime(Integer.toString(p.getId())));
        }
        refresh();
        movie_column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        movie_column_detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        movie_column_button.setCellValueFactory(new PropertyValueFactory<>("button"));
        movie_column_picture.setCellValueFactory(new PropertyValueFactory<>("picture"));
        program_column_name.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        program_column_detail.setCellValueFactory(new PropertyValueFactory<>("movieDetail"));
        program_column_button.setCellValueFactory(new PropertyValueFactory<>("movieDetailButton"));
        program_column_button_showtime.setCellValueFactory(new PropertyValueFactory<>("button"));
        program_column_picture.setCellValueFactory(new PropertyValueFactory<>("moviePicture"));
    }
    @FXML public void detail(String movie_id){
        Movie temp = Main.ctrl.movies.get(movie_id);
        MovieDetailController.mdc.setImage(temp.getBig_picture());
        MovieDetailController.mdc.setTitle(temp.getName());
        MovieDetailController.mdc.setDetail(temp.getDetail());
        MovieDetailController.mdc.setLink(temp.getYoutube_link());
        MovieDetailController.mdc.runYoutubeVideo();
        Main.ctrl.body.setContent(ViewManager.getInstance().get("MovieDetail"));
    }

    @FXML public void showTime(String program_id){
        Program p = programs.get(program_id);
        Movie temp = Main.ctrl.movies.get(Integer.toString(p.getMovie().getId()));
        ShowTimeController.stc.setMoviePicture(temp.getBig_picture());
        //กำหนดไห้มีแค่สี่ปุ่ม
        for (int i = 1 ; i <= 4 ; i++){
            try {
                ShowTimeController.stc.setButtonText(i , p.getShowTimes().get(i-1).getTime());
                ShowTime showTime = p.getShowTimes().get(i-1);
                ShowTimeController.stc.getButton(i).setOnAction( e ->
                        seat(showTime.getId()));
                ShowTimeController.stc.setVisible(i,true);
            } catch (Exception ex){
                System.out.println("No time");
                ShowTimeController.stc.setVisible(i,false);
            }

        }
        Main.ctrl.body.setContent(ViewManager.getInstance().get("ShowTime"));
    }

    @FXML public void seat(int showtime_id){
        SeatController.sc.st_id = showtime_id;
        try {
            SeatController.sc.seats = Seat.readSeat(Database.getConnection(),showtime_id);
        } catch (Exception ex){
            System.out.println("read Error");
        }
        SeatController.sc.load();
        Main.ctrl.body.setContent(ViewManager.getInstance().get("Seat"));
    }

    public void addMovie(){
        /*ArrayList<String> movie_in_program = new ArrayList<>();
        for (String k : keys){
            Program p = programs.get(k);
            movie_in_program.add(Integer.toString(p.getMovieId()));
        }
        for (String k : Main.ctrl.keys){
            if (!movie_in_program.contains(k)){
                Movie m = Movie.getMoviesData(Database.getConnection()).get(k);
                movie_list.getItems().add(m);
            }
        }*/
        HashMap<String,Movie> movieHashMap = Movie.getMoviesData(Database.getConnection());
        for (String k : Movie.getMovieKey(Database.getConnection())){
            movie_list.getItems().add(movieHashMap.get(k));
        }
    }

    public ArrayList<Program> getProgram(){
        ArrayList<Program> p = new ArrayList<>();
        for (String k : keys){
            p.add(programs.get(k));
        }
        return p;
    }

    public void refresh(){
        programs = Program.readProgram(Database.getConnection(),Movie.getMoviesData(Database.getConnection()));
        keys = Program.getProgramsKey(Database.getConnection());
        for (String k : Main.ctrl.keys){
            Movie m = Movie.getMoviesData(Database.getConnection()).get(k);
            m.getButton().setOnAction(e ->
                    detail(Integer.toString(m.getId())));
        }
        for (String k : keys){
            Program p = programs.get(k);
            p.getButton().setOnAction(e ->
                    showTime(Integer.toString(p.getId())));
        }
        movie_list.getItems().clear();
        addMovie();
        program_list.getItems().clear();
        program_list.getItems().addAll(getProgram());
    }

}
