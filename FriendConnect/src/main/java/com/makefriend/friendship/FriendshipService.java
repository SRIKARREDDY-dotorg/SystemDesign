package com.makefriend.friendship;

import com.makefriend.notification.NotificationService;
import com.makefriend.user.User;
import com.makefriend.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FriendshipService {
    private final Map<String, List<FriendshipRequest>> friendshipRequests;
    private final UserService userService;
    private final NotificationService notificationService;

    private static FriendshipService instance;
    private FriendshipService() {
        friendshipRequests = new ConcurrentHashMap<>();
        userService = UserService.getInstance();
        notificationService = NotificationService.getInstance();
    }

    public static FriendshipService getInstance() {
        if(instance == null) {
            instance = new FriendshipService();
        }
        return instance;
    }
    public void sendRequest(FriendshipRequest friendshipRequest) {
        if(friendshipRequest == null) {
            throw new IllegalArgumentException("Friendship request cannot be null");
        }
        if(friendshipRequest.getReceiver() == null || friendshipRequest.getSender() == null) {
            throw new IllegalArgumentException("Receiver and sender cannot be null");
        }
        if(friendshipRequest.getReceiver().getUsername().equals(friendshipRequest.getSender().getUsername())) {
            throw new IllegalArgumentException("Receiver and sender cannot be the same");
        }
        final User sender = friendshipRequest.getSender();
        if(!userService.verifyLogin(sender)) {
            throw new IllegalArgumentException("Sender is not logged in");
        }
        notificationService.sendFriendRequestNotification(sender, friendshipRequest.getReceiver(), friendshipRequest.getStatus());
        final String receiverUsername = friendshipRequest.getReceiver().getUsername();
        final List<FriendshipRequest> requests = friendshipRequests.getOrDefault(receiverUsername, new ArrayList<>());
        requests.add(friendshipRequest);
        friendshipRequests.put(receiverUsername, requests);
    }
    public List<FriendshipRequest> getRequests(String username) {
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        return friendshipRequests.get(username);
    }
    public void respondRequest(FriendshipRequest friendshipRequest) {
        final FriendshipStatus status = friendshipRequest.getStatus();
        if(status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if(friendshipRequest.getReceiver() == null || friendshipRequest.getSender() == null) {
            throw new IllegalArgumentException("Receiver and sender cannot be null");
        }
        notificationService.sendFriendRequestNotification(friendshipRequest.getReceiver(), friendshipRequest.getSender(), status);
        if(status == FriendshipStatus.ACCEPTED) {
            friendshipRequest.getReceiver().setFriend(friendshipRequest.getSender());
            friendshipRequest.getSender().setFriend(friendshipRequest.getReceiver());
        }
        friendshipRequest.setStatus(status);
    }
}
