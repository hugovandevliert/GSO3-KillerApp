package main;

import main.data.Session;
import main.data.model.User;
import java.util.ArrayList;

public class ApplicationManager {
    private Session session;

    public User getCurrentUser() {
        return session.getCurrentUser();
    }

    public boolean login(final String username, final String password) {
        //TODO: make a proper login method.

        User currentUser = new User(1, "testUser", "Hugo", "CEO", null, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());

        session = new Session(currentUser, this);

        return true;
    }

    public void logout() {
        session = null;
        System.out.print("logout");
    }

    public boolean register(String username, String password, String name, String function) {
        //TODO: make a proper register method.
        return false;
    }
}
