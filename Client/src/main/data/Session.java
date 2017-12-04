package main.data;

import main.ApplicationManager;
import main.data.model.User;
import java.time.LocalDateTime;
import java.util.Timer;

public class Session {
    private LocalDateTime loginTime;
    private ApplicationManager applicationManager;
    private User currentUser;

    public Session(User currentUser, ApplicationManager applicationManager) {
        this.currentUser = currentUser;
        this.applicationManager = applicationManager;
        loginTime = LocalDateTime.now();

        Timer auctionCountdown = new Timer();
        //auctionCountdown.schedule(new SessionTimer(this), 0, 1000);
    }

    public User getCurrentUser() { return currentUser; }
}
