package com.gn.module.movie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ShowTimeController {
    @FXML private ImageView movie_picture = new ImageView();
    @FXML private Button button_st1;
    @FXML private Button button_st2;
    @FXML private Button button_st3;
    @FXML private Button button_st4;

    public static ShowTimeController stc;


    public void initialize(){
        stc = this;
    }

    public void setMoviePicture(ImageView img){
        this.movie_picture.setImage(img.getImage());
    }

    public void setButtonText(int number , String text){
        if(number == 1){
            button_st1.setText(text);
        } else if (number == 2){
            button_st2.setText(text);
        } else if (number == 3){
            button_st3.setText(text);
        } else if (number == 4){
            button_st4.setText(text);
        }
    }

    public void setVisible(int number , boolean b){
        if(number == 1){
            button_st1.setVisible(b);
        } else if (number == 2){
            button_st2.setVisible(b);
        } else if (number == 3){
            button_st3.setVisible(b);
        } else if (number == 4){
            button_st4.setVisible(b);
        }
    }

    public Button getButton(int number){
        if(number == 1){
           return button_st1;
        } else if (number == 2){
            return button_st2;
        } else if (number == 3){
            return button_st3;
        } else if (number == 4){
            return button_st4;
        }
        return null;
    }
}
