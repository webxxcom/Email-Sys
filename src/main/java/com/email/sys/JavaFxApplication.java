package com.email.sys;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JavaFxApplication extends Application {
    SceneManager sceneManager;
    ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(JavaFxApplication.class);
        sceneManager = springContext.getBean(SceneManager.class);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneManager.setStage(primaryStage);

        sceneManager.switchScene(Views.LOG_IN);
        primaryStage.setTitle("Email");
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

