package com.gn.module.movie;

import com.gn.global.plugin.ViewManager;
import com.gn.module.main.Main;
import com.gn.objects.Movie;
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
import javafx.scene.layout.Pane;
import jfxtras.styles.jmetro8.JMetro;

public class HomeController {
    @FXML private Pane container;
    @FXML TableView<Movie> movie_list;
    @FXML TableColumn<Movie,String> movie_column_name;
    @FXML TableColumn<Movie,String> movie_column_detail;
    @FXML TableColumn<Movie, Button> movie_column_button;
    @FXML TableColumn<Movie, ImageView> movie_column_picture;
    public static HomeController HomeCtr;

    public void initialize(){
        HomeCtr = this;
        for (String k : Main.ctrl.keys){
            Movie m = Main.ctrl.movies.get(k);
            m.getButton().setOnAction(e ->
                    detail(Integer.toString(m.getId())));
        }
        addMovie();
        movie_column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        movie_column_detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        movie_column_button.setCellValueFactory(new PropertyValueFactory<>("button"));
        movie_column_picture.setCellValueFactory(new PropertyValueFactory<>("picture"));
    }
    @FXML public void detail(String movie_id){
        Movie temp = Main.ctrl.movies.get(movie_id);
        MovieDetailController.mdc.setImage(temp.getPicture());
        MovieDetailController.mdc.setTitle(temp.getName());
        MovieDetailController.mdc.setDetail(temp.getDetail());
        Main.ctrl.body.setContent(ViewManager.getInstance().get("MovieDetail"));
    }

    private void addMovie(){
        for (String k : Main.ctrl.keys){
            Movie m = Main.ctrl.movies.get(k);
            movie_list.getItems().add(m);
        }
    }
}
