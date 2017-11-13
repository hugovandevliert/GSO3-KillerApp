package main;

import main.data.model.Chat;
import main.data.model.User;

import java.util.ArrayList;

public class ApplicationManager {
    private User currentUser;

    public boolean login(final String username, final String password) {
        //TODO: make a proper login method.

        currentUser = new User(1, "testUser", "Hugo", "CEO", null, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>());

        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
