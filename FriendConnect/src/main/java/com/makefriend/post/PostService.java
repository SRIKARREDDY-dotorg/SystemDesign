package com.makefriend.post;

import com.makefriend.user.User;

import java.util.List;

public interface PostService {
    public Post createPost(User author, String content, List<String> images, List<String> videos);
    public List<Post> getNewsFeedForUser(User user);
}
