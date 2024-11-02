package com.makefriend.comment;

import com.makefriend.post.Post;
import com.makefriend.user.User;
import com.makefriend.util.IDGenerator;

public class Comment {
    private final String id;
    private String content;
    private User user;
    private Post post;
    private long timestamp;

    private Comment() {
        this.id = IDGenerator.generateId();
    }
    public String getId() {
        return id;
    }
    public String getContent() {
        return content;
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
    public void setContent(String content) {
        this.content = content;
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

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String content;
        private User user;
        private Post post;

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withPost(Post post) {
            this.post = post;
            return this;
        }

        public Comment build() {
            Comment comment = new Comment();
            comment.content = this.content;
            comment.user = this.user;
            comment.post = this.post;
            comment.timestamp = System.currentTimeMillis();
            return comment;
        }
    }
}
