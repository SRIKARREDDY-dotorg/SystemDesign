package com.makefriend.post;

import com.makefriend.user.User;
import com.makefriend.util.IDGenerator;

import java.util.List;

public class Post {
    private final String postId;
    private String content;
    private User author;
    private List<String> images;
    private List<String> videos;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    private Post() {
        this.postId = IDGenerator.generateId();
    }

    public static class Builder {
        private String content;
        private User author;
        private List<String> images;
        private List<String> videos;

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withAuthor(User author) {
            this.author = author;
            return this;
        }

        public Builder withImages(List<String> images) {
            this.images = images;
            return this;
        }

        public Builder withVideos(List<String> videos) {
            this.videos = videos;
            return this;
        }

        public Post build() {
            Post post = new Post();
            post.content = this.content;
            post.author = this.author;
            post.images = this.images;
            post.videos = this.videos;
            post.timestamp = System.currentTimeMillis();
            return post;
        }
    }
}
