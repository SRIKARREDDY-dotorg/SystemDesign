package com.makefriend.friendship;

import com.makefriend.user.User;

public class FriendshipRequest {
    private User sender;
    private User receiver;
    private FriendshipStatus status;

    public FriendshipRequest(FriendshipStatus status) {
        this.status = status;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
    public User getSender() {
        return sender;
    }
    public User getReceiver() {
        return receiver;
    }
    public FriendshipStatus getStatus() {
        return status;
    }
    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
