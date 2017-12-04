package main.data;

import java.util.TimerTask;

public class SessionTimer extends TimerTask {
    private Session session;

    public SessionTimer(Session session) {
        this.session = session;
    }

    @Override
    public void run() {
        session.logout();
    }
}
