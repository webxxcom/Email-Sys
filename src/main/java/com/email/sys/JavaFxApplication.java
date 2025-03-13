package com.email.sys;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
public class JavaFxApplication extends Application {
    ConfigurableApplicationContext springContext;
    SceneManager sceneManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(JavaFxApplication.class);
        sceneManager = springContext.getBean(SceneManager.class);
    }

    @Override
    public void start(Stage primaryStage) {
        sceneManager.setStage(primaryStage);

        sceneManager.goTo(Views.LOG_IN);
        primaryStage.setTitle("Email");
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }
}

