package com.makefriend.like;

import com.makefriend.notification.NotificationService;
import com.makefriend.post.Post;
import com.makefriend.user.User;
import com.makefriend.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LikeService {
    private static LikeService instance;
    private final NotificationService notificationService;
    private final UserService userService;
    private Map<String, List<Like>> likes;

    private LikeService() {
        this.userService = UserService.getInstance();
        this.likes = new ConcurrentHashMap<>();
        this.notificationService = NotificationService.getInstance();
    }
    public static LikeService getInstance() {
        if(instance == null) {
            instance = new LikeService();
        }
        return instance;
    }

    public void likePost(User user, Post post) {
        if(user == null || post == null) {
            throw new IllegalArgumentException("User and post cannot be null");
        }
        if(!userService.verifyLogin(user)) {
            throw new IllegalArgumentException("User is not logged in");
        }
        if(post.getAuthor().equals(user)) {
            System.out.println("You like your own post");
        }
        if(likes.containsKey(post.getPostId()) && likes.get(post.getPostId()).contains(post.getAuthor())) {
            throw new IllegalArgumentException("User has already liked this post");
        }
        notificationService.sendLikeNotification(user, post.getAuthor(), post.getContent());
        final Like like = Like.builder().withPost(post).withUser(user).build();
        final List<Like> likesByPost = likes.computeIfAbsent(post.getPostId(), k -> new ArrayList<>());
        likesByPost.add(like);
    }

    public List<Like> getLikesByPost(Post post) {
        if(post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        return likes.get(post.getPostId());
    }
}
