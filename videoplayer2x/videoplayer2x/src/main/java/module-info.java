module com.example.videoplayer2x {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.videoplayer2x to javafx.fxml;
    exports com.example.videoplayer2x;
}