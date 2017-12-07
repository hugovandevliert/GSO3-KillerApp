package main;

import main.data.session.Session;
import main.data.model.User;
import main.ui.controller.BaseController;

import java.io.IOException;
import java.util.ArrayList;

public class ApplicationManager {
    private BaseController basecontroller;
    private Session session;

    public void setBasecontroller(BaseController basecontroller) {
        this.basecontroller = basecontroller;
    }

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
        basecontroller.logout();
    }

    public boolean register(String username, String password, String name, String function) {
        //TODO: make a proper register method.
        return false;
    }
}
