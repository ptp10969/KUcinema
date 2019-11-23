package com.gn.module.movie;


import com.gn.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MovieDetailController{
    @FXML private ImageView img;
    @FXML private Label title;
    @FXML private Text detail;
    @FXML private Pane pane;
    public WebView webView;
    public WebEngine engine;
    public String link;
    public void initialize(){
        mdc = this;
    }

    public void runYoutubeVideo(){
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                webView = new WebView();
                engine = webView.getEngine();
                webView.setMaxWidth(651);
                webView.setMaxHeight(394);
                engine.load(link);
                pane.getChildren().add(webView);
            }
        });
    }

    public  static MovieDetailController mdc;

    public void setImage(ImageView img){
        this.img.setImage(img.getImage());
    }

    public void setDetail(String detail){
        this.detail.setText(detail);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setLink(String link){
        this.link = link;
    }


}
