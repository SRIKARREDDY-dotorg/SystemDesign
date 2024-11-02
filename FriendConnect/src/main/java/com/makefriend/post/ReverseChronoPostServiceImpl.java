package com.makefriend.post;

import com.makefriend.user.User;
import com.makefriend.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReverseChronoPostServiceImpl implements PostService {
    private final UserService userService;
    private Map<String, List<Post>> posts;
    private static ReverseChronoPostServiceImpl instance;
    private ReverseChronoPostServiceImpl() {
        userService = UserService.getInstance();
        posts = new ConcurrentHashMap<>();
    }
    public static ReverseChronoPostServiceImpl getInstance() {
        if(instance == null) {
            instance = new ReverseChronoPostServiceImpl();
        }
        return instance;
    }
    @Override
    public Post createPost(User author, String content, List<String> images, List<String> videos) {
        if(author == null || content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid post data");
        }
        if(!userService.verifyLogin(author)) {
            throw new IllegalArgumentException("User not logged in");
        }
        final Post post = buildPost(author, content, images, videos);
        List<Post> authorPosts = posts.computeIfAbsent(author.getUserId(), k -> new ArrayList<>());
        authorPosts.add(post);
        return post;
    }

    private Post buildPost(User author, String content, List<String> images, List<String> videos) {
        return new Post.Builder().withAuthor(author).withContent(content).withImages(images).withVideos(videos).build();
    }

    @Override
    public List<Post> getNewsFeedForUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        final List<Post> newsFeed = new ArrayList<>();
        if(!userService.verifyLogin(user)) {
            throw new IllegalArgumentException("User not logged in");
        }
        newsFeed.addAll(posts.getOrDefault(user.getUserId(), new ArrayList<>()));
        // get the friends posts of the User as well
        user.getFriends().forEach(friend -> newsFeed.addAll(posts.getOrDefault(friend.getUserId(), new ArrayList<>())));
        newsFeed.sort((p1, p2) -> Long.compare(p2.getTimestamp(), p1.getTimestamp()));
        return newsFeed;
    }
}
