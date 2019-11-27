package com.gn.module.login;

import animatefx.animation.*;
import com.gn.App;
import com.gn.Database.Database;
import com.gn.GNAvatarView;
import com.gn.decorator.GNDecorator;
import com.gn.global.plugin.ViewManager;
import com.gn.global.*;
import com.gn.global.plugin.SectionManager;
import com.gn.global.plugin.UserManager;
import com.gn.global.util.Mask;
import com.gn.module.main.Main;
import com.gn.objects.Register;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/11/2018
 */
public class Account implements Initializable {

    @FXML private GNAvatarView avatar;

    @FXML private HBox box_fullname;
    @FXML private HBox box_username;
    @FXML private HBox box_email;
    @FXML private HBox box_password;

    @FXML private TextField fullname;
    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private TextField password;

    @FXML private Label lbl_password;
    @FXML private Label lbl_fullname;
    @FXML private Label lbl_email;
    @FXML private Label lbl_username;

    @FXML private Label lbl_error;

    @FXML private Button register;
    Stage window;
    Scene scene1;



    private RotateTransition rotateTransition = new RotateTransition();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rotateTransition.setNode(avatar);
        rotateTransition.setByAngle(360);
        rotateTransition.setDuration(Duration.seconds(1));
        rotateTransition.setAutoReverse(true);

        addEffect(email);
        addEffect(fullname);
        addEffect(username);
        addEffect(password);

        Mask.nameField(fullname);
        Mask.noInitSpace(username);
        Mask.noSpaces(username);
        setupListeners();
    }

    @FXML
    private void register() throws Exception {
        Pulse pulse = new Pulse(register);
        pulse.setDelay(Duration.millis(20));
        pulse.play();

        if (validEmail() && validFullName() && validFullName() && validUsername() && validPassword()) {

            String user = username.getText();
            String extension = "properties";

            File directory = new File("user/");
            File file = new File("user/" + user + "." + extension);

            Register register = new Register();
            register.addUser(username.getText(),fullname.getText(),email.getText(),password.getText());

            Register users = login(username.getText(),password.getText());

            if (!directory.exists()) {
                directory.mkdir();
                file.createNewFile();
                setProperties(users);
            } else if (!file.exists()) {
                file.createNewFile();
                setProperties(users);
            } else {
                lbl_error.setVisible(true);
            }
        } else if (!validUsername()){
            lbl_username.setVisible(true);
        } else if (!validFullName()) {
            lbl_fullname.setVisible(true);
        } else if (!validEmail()){
            lbl_email.setVisible(true);
        } else {
            lbl_password.setVisible(true);
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


    private void setProperties(Register users){

        Section section = new Section(true, username.getText());
        SectionManager.save(section);

        User user = new User(username.getText(), fullname.getText(), email.getText(), password.getText());
        UserManager.save(user);

        UserDetail detail = App.getUserDetail();
        detail.setText(user.getFullName());
        detail.setHeader(user.getUserName());

        App.decorator.addCustom(detail);
        detail.setProfileAction(event -> {
            App.getUserDetail().getPopOver().hide();
            Main.ctrl.title.setText("Profile");
            Main.ctrl.body.setContent(ViewManager.getInstance().get("profile"));

        });

        detail.setSignAction(event -> {
            App.getUserDetail().getPopOver().hide();
            SectionManager.save(new Section(false, ""));

            this.password.setText("");
            this.email.setText("");
            this.fullname.setText("");
            this.username.setText("");

            App.decorator.setContent(ViewManager.getInstance().get("login"));

            App.decorator.removeCustom(detail);
        });
        Main.ctrl.setUser(users);
        Main.ctrl.fullname.setText(users.getUsername());
        App.decorator.setContent(ViewManager.getInstance().get("main"));
    }

    @FXML
    private void back(){
        App.decorator.setContent(ViewManager.getInstance().get("login"));
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

    private boolean validPassword(){
        return !password.getText().isEmpty() && password.getLength() > 3 ;
    }

    private boolean validUsername(){
        return !username.getText().isEmpty() && username.getLength() > 3 ;
    }

    private boolean validFullName(){
        return !fullname.getText().isEmpty() && fullname.getLength() > 3 ;
    }

    private boolean validEmail(){
        return Mask.isEmail(email);
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

        email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!validEmail()){
                if(!newValue){
                    Flash swing = new Flash(box_email);
                    lbl_email.setVisible(true);
                    new SlideInLeft(lbl_email).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_email.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    lbl_email.setVisible(false);
                }
            }  else {
                lbl_error.setVisible(false);
            }
        });

        fullname.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!validFullName()){
                if(!newValue){
                    Flash swing = new Flash(box_fullname);
                    lbl_fullname.setVisible(true);
                    new SlideInLeft(lbl_fullname).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_fullname.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    lbl_fullname.setVisible(false);
                }
            } else {
                lbl_error.setVisible(false);
            }
        });
    }
}
