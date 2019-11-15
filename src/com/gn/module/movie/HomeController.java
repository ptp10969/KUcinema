package com.gn.module.movie;

import com.gn.global.plugin.ViewManager;
import com.gn.module.main.Main;
import com.gn.objects.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class HomeController {
    @FXML
    private Button submit1;
    @FXML private Button submit2;
    @FXML private Button submit3;
    @FXML private Button submit4;
    @FXML private Button submit5;
    @FXML private Pane container;
    private Label label1 ;
    private Label label2 ;
    public static HomeController HomeCtr;

    public void initialize(){
        HomeCtr = this;
        Button temp = Main.ctrl.movies.get(0).getButton();
        temp.setOnAction( e ->
                detail(1)
        );
        container.getChildren().addAll(temp);
    }
    @FXML public void detail(int movie_id){
        Movie temp = Main.ctrl.movies.get(movie_id);
        MovieDetailController.mdc.setImage(temp.getPicture());
        Main.ctrl.body.setContent(ViewManager.getInstance().get("MovieDetail"));
    }
}
