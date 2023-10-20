package com.example.videoplayer2x;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        String videoPath = "file:///C:/Users/Nurzada/Pictures/iCloud%20Photos/Photos/хв/IMG_4894.MP4"; //  путь на ваш

        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        StackPane root = new StackPane();
        root.getChildren().add(mediaView);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("JavaFX Video Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
