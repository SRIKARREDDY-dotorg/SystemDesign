package com.makefriend.like;

import com.makefriend.post.Post;
import com.makefriend.user.User;
import com.makefriend.util.IDGenerator;

public class Like {
    private final String likeId;
    private User user;
    private Post post;
    private long timestamp;

    private Like() {
        this.likeId = IDGenerator.generateId();
    }

    public static Builder builder() {
        return new Builder();
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getLikeId() {
        return likeId;
    }
    public User getUser() {
        return user;
    }
    public Post getPost() {
        return post;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public static class Builder {
        private User user;
        private Post post;

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withPost(Post post) {
            this.post = post;
            return this;
        }

        public Like build() {
            Like like = new Like();
            like.user = this.user;
            like.post = this.post;
            like.timestamp = System.currentTimeMillis();
            return like;
        }
    }
}
