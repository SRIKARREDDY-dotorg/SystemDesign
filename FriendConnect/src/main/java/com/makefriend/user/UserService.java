package com.makefriend.user;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users;
    private final List<User> loggedInUsers;
    private static UserService instance;
    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
            return instance;
        }
        return instance;
    }

    private UserService() {
        this.users = new ArrayList<>();
        this.loggedInUsers = new ArrayList<>();
    }

    public void registerUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if(user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if(user.getEmailId() == null || user.getEmailId().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if(user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if(users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            throw new IllegalArgumentException("Username already exists");
        }
        users.add(user);
    }

    public void login(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if(user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if(user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if(!users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()))) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        loggedInUsers.add(user);
        System.out.println(user.getUsername() + " logged in successfully");
    }

    public void logout(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if(!loggedInUsers.contains(user)) {
            throw new IllegalArgumentException("User is not logged in");
        }
        loggedInUsers.remove(user);
        System.out.println(user.getUsername() + " logged out successfully");
    }

    public boolean verifyLogin(User user) {
        return loggedInUsers.contains(user);
    }

    public void addNotification(User user, String notification) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if(notification == null || notification.isBlank()) {
            throw new IllegalArgumentException("Notification cannot be null or empty");
        }
        System.out.println("Hey " + user.getUsername() + " you have a new notification: "+ notification);
    }
}
