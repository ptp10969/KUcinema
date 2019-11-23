package com.gn.module.movie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class VideoPlayer extends Application {
    public static void main(String[] args) { launch(args); }

    public static WebView webView;

    public VideoPlayer(){
        webView = new WebView();
    }

    @Override public void start(Stage stage) throws Exception {
        webView = new WebView();
        webView.getEngine().load(
                "https://www.youtube.com/embed/F6QaLsw8EWY"
        );
        webView.setPrefSize(640, 390);
        Scene scene = new Scene(webView);
        stage.setScene(scene);
        stage.show();
    }
}