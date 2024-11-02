package com.makefriend.comment;

import com.makefriend.notification.NotificationService;
import com.makefriend.post.Post;
import com.makefriend.user.User;
import com.makefriend.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentService {
    private static CommentService instance;
    private final UserService userService;
    private final NotificationService notificationService;
    private Map<String, List<Comment>> comments;

    private CommentService() {
        this.userService = UserService.getInstance();
        this.notificationService = NotificationService.getInstance();
        this.comments = new HashMap<>();
    }
    public static CommentService getInstance() {
        if(instance == null) {
            instance = new CommentService();
        }
        return instance;
    }

    public void addComment(User user, String content, Post post) {
        if(user == null || content == null || post == null) {
            throw new IllegalArgumentException("User, content and post cannot be null");
        }
        if(!userService.verifyLogin(user)) {
            System.out.println("User " + user.getUsername() + " is not logged in");
            return;
        }
        final Comment comment = Comment.builder().withContent(content).withUser(user).build();
        comments.computeIfAbsent(post.getPostId(), k -> new ArrayList<>()).add(comment);
        notificationService.sendCommentNotification(user, post.getAuthor(), post.getContent());
    }

    public List<Comment> getCommentsForPost(Post post) {
        if(post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        return comments.get(post.getPostId());
    }
}
