package main;

import main.data.model.Chat;
import main.data.model.User;
import main.data.repository.ChatRepository;
import main.data.repository.MessageRepository;
import main.data.repository.UserRepository;
import main.data.session.Session;
import main.rmi.ClientManager;
import main.ui.controller.BaseController;
import main.ui.controller.ChatController;
import main.util.sec.HashCalculator;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ApplicationManager {
    private UserRepository userRepository = new UserRepository();
    private ChatRepository chatRepository = new ChatRepository();
    private MessageRepository messageRepository = new MessageRepository();

    private BaseController baseController;
    private ChatController openedChat;
    private HashCalculator hashCalculator = new HashCalculator();
    private ClientManager clientManager;
    private Session session;
    private List<User> allUsers;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    public User getCurrentUser() {
        return session.getCurrentUser();
    }

    public boolean login(final String username, final String password) throws SQLException, ConnectException {
        final String[] saltAndHash = userRepository.getSaltAndHash(username);

        if (saltAndHash.length > 0 && hashCalculator.hashString(password, saltAndHash[0]).equals(saltAndHash[1])){
            session = new Session(userRepository.getUserByUsername(username), this);

            loadPrivateChats();
            loadGroupChats();
            loadMemos();

            allUsers = userRepository.getAllUsers();
            clientManager = new ClientManager();
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        session = null;
        baseController.logout();
    }

    public boolean register(String username, String password, String name, String function) throws SQLException, ConnectException {
        if (!userRepository.checkUsernameAvailability(username)) {
            throw new IllegalArgumentException("This username is already in use.\nPlease choose a new username.");
        } else if (name == null || name.length() == 0){
            throw new IllegalArgumentException("Name can not be empty.");
        } else if (username == null || username.length() == 0){
            throw new IllegalArgumentException("Username can not be empty.");
        } else if (username.length() > 16){
            throw new IllegalArgumentException("Username can not be longer than 16 characters.");
        } else if (password.length() < 6){
            throw new IllegalArgumentException("Password should be at least 6 characters");
        } else if (password.matches("^[0-9]*$")) {
            throw new IllegalArgumentException("Password should not only contain numbers");
        } else if (password.length() > 32) {
            throw new IllegalArgumentException("Password should not exceed 32 characters");
        } else if (function.equals("function")) {
            throw new IllegalArgumentException("Please choose a function");
        }

        final String salt = hashCalculator.generateSalt();
        return userRepository.registerUser(username, hashCalculator.hashString(password, salt), salt, name, function);
    }

    public void loadPrivateChats() throws SQLException, ConnectException {
        session.getCurrentUser().setPrivateChats(chatRepository.getPrivateChatsByUserId(session.getCurrentUser().getId()));
        for (Chat chat : getCurrentUser().getPrivateChats()) {
            chat.setUsers((ArrayList<User>) userRepository.getUsersByChatId(chat.getId()));
        }
    }

    public void loadGroupChats() throws SQLException, ConnectException {
        session.getCurrentUser().setGroupChats(chatRepository.getGroupChatsByUserId(session.getCurrentUser().getId()));
        for (Chat chat : getCurrentUser().getGroupChats()) {
            chat.setUsers((ArrayList<User>) userRepository.getUsersByChatId(chat.getId()));
        }
    }

    public void loadMemos() throws SQLException, ConnectException {
        session.getCurrentUser().setMemos(chatRepository.getMemosByUserId(session.getCurrentUser().getId()));
        for (Chat chat : getCurrentUser().getMemos()) {
            chat.setUsers((ArrayList<User>) userRepository.getUsersByChatId(chat.getId()));
        }
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ChatRepository getChatRepository() {
        return chatRepository;
    }

    public MessageRepository getMessageRepository() {
        return messageRepository;
    }

    public ClientManager getClientManager() {
        return this.clientManager;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public ChatController getOpenedChat() {
        return openedChat;
    }

    public void setOpenedChat(ChatController openedChat) {
        this.openedChat = openedChat;
    }
}
