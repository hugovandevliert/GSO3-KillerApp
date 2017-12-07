package main.data.session;

import main.ApplicationManager;
import main.data.model.User;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;

public class Session {
    private ApplicationManager applicationManager;
    private User currentUser;

    public Session(User currentUser, ApplicationManager applicationManager) {
        this.currentUser = currentUser;
        this.applicationManager = applicationManager;

        final Instant loginTime = new Date().toInstant().plus(Duration.ofHours(24));
        final Timer auctionCountdown = new Timer();
        auctionCountdown.schedule(new SessionTimer(this), Date.from(loginTime));
    }

    public User getCurrentUser() { return currentUser; }

    public void logout() {
        applicationManager.logout();
    }
}
