package com.gn.module.login;

import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import com.gn.App;
import com.gn.Database.Database;
import com.gn.GNAvatarView;
import com.gn.global.plugin.ViewManager;
import com.gn.global.*;
import com.gn.global.plugin.SectionManager;
import com.gn.global.plugin.UserManager;
import com.gn.module.main.Main;
import com.gn.objects.Register;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/11/2018
 * Version 1.0.2
 */
public class login implements Initializable {

    @FXML private GNAvatarView avatar;
    @FXML private HBox box_username;
    @FXML private HBox box_password;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button login;

    @FXML private Label lbl_password;
    @FXML private Label lbl_username;
    @FXML private Label lbl_error;

    private RotateTransition rotateTransition = new RotateTransition();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rotateTransition.setNode(avatar);
        rotateTransition.setByAngle(360);
        rotateTransition.setDuration(Duration.seconds(1));
        rotateTransition.setAutoReverse(true);

        addEffect(password);
        addEffect(username);

        setupListeners();

    }

    private void addEffect(Node node){
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            rotateTransition.play();
            Pulse pulse = new Pulse(node.getParent());
            pulse.setDelay(Duration.millis(100));
            pulse.setSpeed(5);
            pulse.play();
            node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
        });

        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!node.isFocused())
                node.getParent().setStyle("-icon-color : -dark-gray; -fx-border-color : transparent");
            else node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
        });
    }

    private void setupListeners(){
        password.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!validPassword()){
                if(!newValue){
                    Flash swing = new Flash(box_password);
                    lbl_password.setVisible(true);
                    new SlideInLeft(lbl_password).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_password.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    lbl_password.setVisible(false);
                }
            } else {
                lbl_error.setVisible(false);
            }
        });

        username.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!validUsername()){
                if(!newValue){
                    Flash swing = new Flash(box_username);
                    lbl_username.setVisible(true);
                    new SlideInLeft(lbl_username).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_username.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    lbl_username.setVisible(false);
                }
            } else {
                lbl_error.setVisible(false);
            }
        });
    }

    private boolean validPassword(){
        return !password.getText().isEmpty() && password.getLength() > 3;
    }

    private boolean validUsername(){
        return !username.getText().isEmpty() && username.getLength() > 3;
    }


    @FXML
    private void loginAction() throws Exception {
//        Pulse pulse = new Pulse(login);
//        pulse.setDelay(Duration.millis(20));
//        pulse.play();
//        if(validPassword() && validUsername())
//            enter();
//        else {
//            lbl_password.setVisible(true);
//            lbl_username.setVisible(true);
//        }

        Register user = login(username.getText(),password.getText());

        if(user != null) {
            enters(user);
        }
        else {
            lbl_password.setVisible(true);
            lbl_username.setVisible(true);
        }
    }

    public Register login(String username, String password) throws Exception {
        ArrayList<String> str = new ArrayList<>();
        Register user = new Register();
        try {
            Connection connection = Database.getConnection();
            String query = "SELECT * FROM users WHERE username = '" +username+"' AND password = '" +password+"'";

            Statement psmt = connection.createStatement();
            ResultSet rs = psmt.executeQuery(query);

            while(rs.next()) {
                user.setUser_Id(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRoles(rs.getString("roles"));
            }
            connection.close();


        } catch (Exception ex){
            ex.printStackTrace();
        }

        if(user != null)
            return user;
        else return null;
    }


    public void enters(Register user) {
        if(user.getUsername().equals(this.username.getText()) && user.getPassword().equals(this.password.getText())){
            Section section = new Section();
            section.setLogged(false);
            section.setUserLogged(this.username.getText());
            SectionManager.save(section);

            Main.ctrl.setUser(user);
            Main.ctrl.fullname.setText(user.getUsername());
            App.decorator.setContent(ViewManager.getInstance().get("main"));

            UserDetail detail = App.getUserDetail();
            detail.setText(user.getName());
            detail.setHeader(user.getUsername());

            App.decorator.addCustom(App.getUserDetail());

            App.getUserDetail().setProfileAction(event -> {
                App.getUserDetail().getPopOver().hide();
                Main.ctrl.title.setText("Profile");
                Main.ctrl.body.setContent(ViewManager.getInstance().get("profile"));
            });

            App.getUserDetail().setSignAction(event -> {
                App.getUserDetail().getPopOver().hide();
                App.decorator.setContent(ViewManager.getInstance().get("login"));
                this.username.setText("");
                this.password.setText("");
                if(Main.popConfig.isShowing()) Main.popConfig.hide();
                if(Main.popup.isShowing()) Main.popup.hide();
                App.decorator.removeCustom(App.getUserDetail());
            });

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(()-> {
                                // add notification in later
                                //                                    TrayNotification tray = new TrayNotification();
                                //                                    tray.setNotificationType(NotificationType.NOTICE);
                                //                                    tray.setRectangleFill(Color.web(""));
                                //                                    tray.setTitle("Welcome!");
                                //                                    tray.setMessage("Welcome back " + username);
                                //                                    tray.showAndDismiss(Duration.millis(10000));
                            }
                    );
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask, 300);

        } else {
            lbl_error.setVisible(true);
        }
    }

//    public ArrayList<String> login(String userId, String password) throws Exception {
//        ArrayList<String> str = new ArrayList<>();
//        try {
//            Connection connection = Database.connect("localhost/se_db", "root", "");
//            String query = "SELECT * FROM users WHERE user_id = '" +userId+"' AND password = '" +password+"'";
//
//            Statement psmt = connection.createStatement();
//            ResultSet rs = psmt.executeQuery(query);
//
//            str.add(rs.getString("user_id"));
//            str.add(rs.getString("name"));
//
//            connection.close();
//
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        if(str != null)
//            return str;
//        else return null;
//    }

    private void enter() {

        User user = UserManager.get(username.getText());

        if(user.getUserName().equals(this.username.getText()) && user.getPassword().equals(this.password.getText())){
            Section section = new Section();
            section.setLogged(false);
            section.setUserLogged(this.username.getText());
            SectionManager.save(section);

            App.decorator.setContent(ViewManager.getInstance().get("main"));

            UserDetail detail = App.getUserDetail();
            detail.setText(user.getFullName());
            detail.setHeader(user.getUserName());

            App.decorator.addCustom(App.getUserDetail());

            App.getUserDetail().setProfileAction(event -> {
                App.getUserDetail().getPopOver().hide();
                Main.ctrl.title.setText("Profile");
                Main.ctrl.body.setContent(ViewManager.getInstance().get("profile"));
            });

            App.getUserDetail().setSignAction(event -> {
                App.getUserDetail().getPopOver().hide();
                App.decorator.setContent(ViewManager.getInstance().get("login"));
                this.username.setText("");
                this.password.setText("");
                if(Main.popConfig.isShowing()) Main.popConfig.hide();
                if(Main.popup.isShowing()) Main.popup.hide();
                App.decorator.removeCustom(App.getUserDetail());
            });

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(()-> {
                                // add notification in later
                                //                                    TrayNotification tray = new TrayNotification();
                                //                                    tray.setNotificationType(NotificationType.NOTICE);
                                //                                    tray.setRectangleFill(Color.web(""));
                                //                                    tray.setTitle("Welcome!");
                                //                                    tray.setMessage("Welcome back " + username);
                                //                                    tray.showAndDismiss(Duration.millis(10000));
                            }
                    );
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask, 300);

        } else {
            lbl_error.setVisible(true);
        }
    }

    @FXML
    private void switchCreate(){
        App.decorator.setContent(ViewManager.getInstance().get("account"));
    }
}
