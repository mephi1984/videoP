package com.example.videoplayer2x;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class MediaPlayerController {

    @FXML
    private Button btnPlay;

    @FXML
    private Label lblDuration;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider;

    @FXML
    private Button btnRewind;

    @FXML
    private Button btnForward;

    @FXML
    private TextField linkInput;

    @FXML
    private ImageView btnPlayIcon;

    @FXML
    private Button btnFind;


    @FXML
    private Slider sliderVolume;


    private Media media;
    private MediaPlayer mediaPlayer;

    private boolean isPlayed = false;

    @FXML
    void btnPlay(MouseEvent event) {
        if(!isPlayed){
            mediaPlayer.play();
            btnPlayIcon.setVisible(true);
            Image img = new Image(getClass().getResourceAsStream("/media/play.png"));
            btnPlayIcon.setImage(img);
            isPlayed = true;
        }else {
            mediaPlayer.pause();
            btnPlayIcon.setVisible(true);
            Image img = new Image(getClass().getResourceAsStream("/media/pause.png"));
            btnPlayIcon.setImage(img);
            isPlayed = false;
        }
    }

    @FXML
    void btnStop(MouseEvent event) {
        mediaPlayer.stop();
        isPlayed = false;
    }

    @FXML
    void selectMedia(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            String url = selectedFile.toURI().toString();

            loadMedia(url);

            mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
                double currentTimeSeconds = newValue.toSeconds();
                double totalDurationSeconds = media.getDuration().toSeconds();

                int currentHours = (int) (currentTimeSeconds / 3600);
                int remainingSeconds = (int) (currentTimeSeconds % 3600);
                int currentMinutes = (int) (remainingSeconds / 60);
                int currentSeconds = (int) (remainingSeconds % 60);

                int totalHours = (int) (totalDurationSeconds / 3600);
                remainingSeconds = (int) (totalDurationSeconds % 3600);
                int totalMinutes = (int) (remainingSeconds / 60);
                int totalSeconds = (int) (remainingSeconds % 60);

                slider.setValue(currentTimeSeconds);
                lblDuration.setText("Прод.: " + String.format("%02d:%02d:%02d", currentHours, currentMinutes, currentSeconds)
                        + " / " + String.format("%02d:%02d:%02d", totalHours, totalMinutes, totalSeconds));
            }));

            mediaPlayer.setOnReady(() ->{
                Duration totalDuration = media.getDuration();
                double totalDurationSeconds = totalDuration.toSeconds();

                int totalHours = (int) (totalDurationSeconds / 3600);
                int remainingSeconds = (int) (totalDurationSeconds % 3600);
                int totalMinutes = (int) (remainingSeconds / 60);
                int totalSeconds = (int) (remainingSeconds % 60);

                slider.setMax(totalDuration.toSeconds());
                lblDuration.setText("Прод.: " + String.format("%02d:%02d:%02d", totalHours, totalMinutes, totalSeconds));
            });

            Scene scene = mediaView.getScene();
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());

            sliderVolume.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                                    Number old_val, Number new_val) {
                    mediaPlayer.setVolume(new_val.doubleValue() / 100);
                }
            });

            //mediaPlayer.setAutoPlay(true);

        }

    }

    @FXML
    private void rewindMedia(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(10)));
    }

    @FXML
    private void forwardMedia(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10)));

    }


    @FXML
    private void sliderPressed(MouseEvent event){
        mediaPlayer.seek(Duration.seconds(slider.getValue()));
    }

    @FXML
    void findMedia(ActionEvent event) {
        String url = linkInput.getText();
        if (!url.isEmpty()) {
            loadMedia(url);
        }
    }

    private void loadMedia(String url) {
        media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }
}
