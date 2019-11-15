package com.gn.module.dashboard;


import com.gn.GNAvatarView;
import com.gn.global.plugin.ViewManager;
import com.gn.global.factory.AlertCell;
import com.gn.module.main.Main;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard {
    @FXML private Button submit1;
    @FXML private Button submit2;
    @FXML private Button submit3;
    @FXML private Button submit4;
    @FXML private Button submit5;
    @FXML private Pane container;
    private Label label1 ;
    private Label label2 ;

    public void initialize(){
        label1 = new Label("test");
        label2 = new Label("test2");
        container.getChildren().addAll(label1,label2);
    }
    @FXML public void detail(int movie_id){
        Main.ctrl.body.setContent(ViewManager.getInstance().get("mediaview"));
    }



}
