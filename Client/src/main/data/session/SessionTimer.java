package main.data.session;

import javafx.application.Platform;

import java.util.TimerTask;

public class SessionTimer extends TimerTask {
    private Session session;

    SessionTimer(Session session) {
        this.session = session;
    }

    @Override
    public void run() {
        Platform.runLater(() -> session.logout());
    }
}
