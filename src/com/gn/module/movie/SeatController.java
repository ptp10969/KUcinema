package com.gn.module.movie;

import com.gn.module.main.Main;
import com.gn.objects.Seat;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class SeatController {


    @FXML
    Pane pane;

    @FXML
    Button accept;

    public ArrayList<Seat> seats;
    public static SeatController sc;
    public Alert alert;

    public void initialize(){
        sc = this;
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                alert = new Alert(Alert.AlertType.WARNING);
            }
        });
    }

    public void load(){
        int x = 44;
        int y = 182;
        for (int i = 0 ; i < 36 ; i++){
            Seat seat = seats.get(i);
            ImageView temp = new ImageView();
            if (seat.getStatus().equals("empty")){
                temp.setImage(new Image("com/gn/module/media/chair/c1.png"));
                temp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                           @Override
                                           public void handle(MouseEvent event) {
                                               MouseButton button = event.getButton();
                                               if(button==MouseButton.PRIMARY){
                                                   temp.setImage(new Image("com/gn/module/media/chair/c2.png"));
                                                   seat.setStatus("selected");
                                               }else if(button==MouseButton.SECONDARY) {
                                                   temp.setImage(new Image("com/gn/module/media/chair/c1.png"));
                                                   seat.setStatus("empty");
                                               }
                                           }
                                       }
                );
            } else {
                temp.setImage(new Image("com/gn/module/media/chair/c3.png"));
            }
            temp.setX(x);
            temp.setY(y);
            temp.setFitHeight(63);
            temp.setFitWidth(63);
            if (i == 11 || i == 23){
                y += 99;
                x = 44;
            } else {
                x += 76;
            }
            pane.getChildren().add(temp);
        }
        x = 342;
        y = 455;
        for (int i = 36 ; i <= 40 ; i++){
            Seat seat = seats.get(i);
            ImageView temp = new ImageView();
            if (seat.getStatus().equals("empty")){
                temp.setImage(new Image("com/gn/module/media/chair/c4.png"));
                temp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                           @Override
                                           public void handle(MouseEvent event) {
                                               MouseButton button = event.getButton();
                                               if(button==MouseButton.PRIMARY){
                                                   temp.setImage(new Image("com/gn/module/media/chair/c5.png"));
                                                   seat.setStatus("selected");
                                               }else if(button==MouseButton.SECONDARY) {
                                                   temp.setImage(new Image("com/gn/module/media/chair/c4.png"));
                                                   seat.setStatus("empty");
                                               }
                                           }
                                       }
                );
            } else {
                temp.setImage(new Image("com/gn/module/media/chair/c6.png"));
            }
            temp.setX(x);
            temp.setY(y);
            temp.setFitHeight(63);
            temp.setFitWidth(63);
            x += 76;
            pane.getChildren().add(temp);

        }
    }

    @FXML
    public void acceptButtonOnClick(ActionEvent e){
        for (Seat s : seats){
            if (s.getStatus().equals("selected")){
                if (s.reserve(Main.ctrl.connection,1)){
                    alert.setContentText("จองที่นั่ง " + s.getSeat_name() + " สำเร็จ");
                } else {
                    alert.setContentText("จองที่นั่ง " + s.getSeat_name() + " ไม่สำเร็จ");
                }
                alert.showAndWait();
            }
        }
    }
}
