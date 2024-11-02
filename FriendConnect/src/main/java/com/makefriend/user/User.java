package com.makefriend.user;

import com.makefriend.profile.Profile;
import com.makefriend.util.IDGenerator;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String userId;
    private String username;
    private String emailId;
    private String password;
    private Profile profile;
    private List<User> friends;

    private User() {
        this.userId = IDGenerator.generateId();
        this.friends = new ArrayList<>();
    }
    public void setFriend(User user) {
        friends.add(user);
    }
    public List<User> getFriends() {
        return friends;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getEmailId() {
        return emailId;
    }
    public String getPassword() {
        return password;
    }
    public Profile getProfile() {
        return profile;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String emailId;
        private String password;
        private Profile profile;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmailId(String emailId) {
            this.emailId = emailId;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withProfile(Profile profile) {
            this.profile = profile;
            return this;
        }

        public User build() {
            User user = new User();
            user.username = this.username;
            user.emailId = this.emailId;
            user.password = this.password;
            user.profile = this.profile;
            return user;
        }
    }
}
