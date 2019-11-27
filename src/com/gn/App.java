

package com.gn;

import com.gn.global.*;
import com.gn.global.plugin.SectionManager;
import com.gn.global.plugin.UserManager;
import com.gn.global.plugin.ViewManager;
import com.gn.decorator.GNDecorator;
import com.gn.decorator.options.ButtonType;
import com.gn.module.loader.Loader;
import com.gn.module.main.Main;
import com.gn.module.movie.MovieDetailController;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class App extends Application {

    private float  increment = 0;
    private float  progress = 0;
    private Section section;
    private User    user;

    @Override
    public synchronized void init(){
        section = SectionManager.get();
        if(section.isLogged()){
            user = UserManager.get(section.getUserLogged());
            userDetail = new UserDetail(section.getUserLogged(), user.getFullName(), "subtitle");
        } else {
            userDetail = new UserDetail();
        }

        float total = 43; // the difference represents the views not loaded
        increment = 100f / total;



        load("main",     "main");

        load("profile", "profile");

        load("login", "login");
        load("login", "account");

        load("movie","Home");
        load("movie","MovieDetail");
        load("movie","ShowTime");
        load("movie","Seat");
        load("movie","AddMovie");
        load("movie","AddProgram");
//        System.out.println(ViewManager.getInstance().getSize());

        // delay
        try {
            wait(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){

    }

    public static final GNDecorator decorator = new GNDecorator();
    public static final Scene scene = decorator.getScene();

    public static ObservableList<String>    stylesheets;
    public static HostServices              hostServices;
    private static UserDetail userDetail = null;

    public static GNDecorator getDecorator(){
        return decorator;
    }

    private void configServices(){
        hostServices = getHostServices();
    }

    private void initialScene(){
        decorator.setTitle("KU CINEMA");
//        decorator.setIcon(null);
        decorator.addButton(ButtonType.FULL_EFFECT);
        decorator.initTheme(GNDecorator.Theme.DEFAULT);
  //      decorator.fullBody();

//        String log = logged();
        String log = "login";
        assert log != null;

        if (log.equals("account") || log.equals("login")) {
            decorator.setContent(ViewManager.getInstance().get(log));
        } else {
            App.decorator.addCustom(userDetail);
            userDetail.setProfileAction(event -> {
                Main.ctrl.title.setText("Profile");
                Main.ctrl.body.setContent(ViewManager.getInstance().get("profile"));
                userDetail.getPopOver().hide();
            });

            userDetail.setSignAction(event -> {
                App.decorator.setContent(ViewManager.getInstance().get("login"));
                section.setLogged(false);
                SectionManager.save(section);
                userDetail.getPopOver().hide();
                if(Main.popConfig.isShowing()) Main.popConfig.hide();
                if(Main.popup.isShowing()) Main.popup.hide();
                App.decorator.removeCustom(userDetail);
            });
            decorator.setContent(ViewManager.getInstance().get("main"));
        }

        decorator.getStage().setOnCloseRequest(event -> {
            App.getUserDetail().getPopOver().hide();
            if(Main.popConfig.isShowing()) Main.popConfig.hide();
            if(Main.popup.isShowing()) Main.popup.hide();
            Platform.exit();
        });
    }

    @Override
    public  void start(Stage primary) {
        configServices();
        initialScene();
        stylesheets = decorator.getScene().getStylesheets();

        stylesheets.addAll(
                getClass().getResource("/com/gn/theme/css/fonts.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/material-color.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/skeleton.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/light.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/bootstrap.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/shape.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/typographic.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/helpers.css").toExternalForm(),
                getClass().getResource("/com/gn/theme/css/master.css").toExternalForm()
        );

        decorator.setMaximized(true);

        decorator.getStage().getIcons().add(new Image("/com/gn/module/media/logo2.png"));
        decorator.show();

//        ScenicView.show(decorator.getScene());
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(App.class, Loader.class, args);
    }

    private void load(String module, String name){
        try {
            ViewManager.getInstance().put(
                    name,
                    FXMLLoader.load(getClass().getResource("/com/gn/module/" + module + "/" + name + ".fxml"))
            );
            preloaderNotify();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void preloaderNotify() {
        progress += increment;
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String logged(){
        try {
            File file = new File("dashboard.properties");
            Properties properties = new Properties();

            if(!file.exists()){
                file.createNewFile();
                return "account";
            } else {
                FileInputStream fileInputStream = new FileInputStream(file);
                properties.load(fileInputStream);
                properties.putIfAbsent("logged", "false");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                properties.store(fileOutputStream, "Dashboard properties");


                File directory = new File("user/");
                properties.load(fileInputStream);
                if(directory.exists()){
                    if(properties.getProperty("logged").equals("false"))
                        return "login";
                    else
                        return "main";
                } else
                    return "account";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserDetail getUserDetail() {
        return userDetail;
    }


}
