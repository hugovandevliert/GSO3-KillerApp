package main.util.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class NotificationPlayer {

    private final String path;

    public NotificationPlayer(final String path) {
        this.path = path;
    }

    public void playSound() {
        final Media hit = new Media(new File(path).toURI().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
