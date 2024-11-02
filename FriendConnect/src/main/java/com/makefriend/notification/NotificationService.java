package com.makefriend.notification;

import com.makefriend.friendship.FriendshipStatus;
import com.makefriend.user.User;
import com.makefriend.user.UserService;

public class NotificationService {
    private static NotificationService instance;
    private final UserService userService;
    public static NotificationService getInstance() {
        if(instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }
    private NotificationService() {
        userService = UserService.getInstance();
    }
    public void sendFriendRequestNotification(User sender, User receiver, FriendshipStatus friendshipStatus) {
        if(sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender and receiver cannot be null");
        }
        if(!userService.verifyLogin(receiver)) {
            System.out.println("User " + receiver.getUsername() + " is not logged in");
            return;
        }

        String message = "User " + sender.getUsername() + " sent you a friend request";
        if(friendshipStatus == FriendshipStatus.ACCEPTED) {
            message = "User " + sender.getUsername() + " accepted your friend request";
        } else if(friendshipStatus == FriendshipStatus.DECLINED) {
            message = "User " + sender.getUsername() + " rejected your friend request";
        }
        userService.addNotification(receiver, message);
    }

    public void sendLikeNotification(User sender, User receiver, String postContent) {
        if(sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender and receiver cannot be null");
        }
        if(!userService.verifyLogin(receiver)) {
            System.out.println("User " + receiver.getUsername() + " is not logged in");
            return;
        }
        String message = "User " + sender.getUsername() + " liked your post: " + postContent;
        userService.addNotification(receiver, message);
    }

    public void sendCommentNotification(User sender, User receiver, String postContent) {
        if(sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender and receiver cannot be null");
        }
        if(!userService.verifyLogin(receiver)) {
            System.out.println("User " + receiver.getUsername() + " is not logged in");
            return;
        }
        String message = "User " + sender.getUsername() + " commented on your post: " + postContent;
        userService.addNotification(receiver, message);
    }
}
