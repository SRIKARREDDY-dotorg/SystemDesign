package com.makefriend;

import com.makefriend.comment.Comment;
import com.makefriend.comment.CommentService;
import com.makefriend.friendship.FriendshipRequest;
import com.makefriend.friendship.FriendshipService;
import com.makefriend.friendship.FriendshipStatus;
import com.makefriend.like.Like;
import com.makefriend.like.LikeService;
import com.makefriend.post.Post;
import com.makefriend.post.PostService;
import com.makefriend.post.ReverseChronoPostServiceImpl;
import com.makefriend.profile.Profile;
import com.makefriend.profile.ProfileService;
import com.makefriend.user.User;
import com.makefriend.user.UserService;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        final UserService userService = UserService.getInstance();
        final ProfileService profileService = ProfileService.getInstance();
        final FriendshipService friendshipService = FriendshipService.getInstance();
        final PostService postService = ReverseChronoPostServiceImpl.getInstance();
        final LikeService likeService = LikeService.getInstance();
        final CommentService commentService = CommentService.getInstance();
        final Profile profile = Profile.builder().withBio("I am awesome").withProfilePicture("Profile Pic").withInterests(List.of("Coding")).build();
        final User user1 = User.builder().withUsername("pochana").withPassword("12345").withEmailId("pochanasrikar@gmail.com").withProfile(profile).build();
        final User user2 = User.builder().withUsername("srikar").withPassword("12345").withEmailId("XXXXXXXXXXXXXXXX").build();
        final User user3 = User.builder().withUsername("reddy").withPassword("2389e23").withEmailId("218eqdewhueuic").build();
        final FriendshipRequest friendshipRequest = new FriendshipRequest(FriendshipStatus.PENDING);
        friendshipRequest.setSender(user1);
        friendshipRequest.setReceiver(user2);
        profileService.update(profile, user1);
        userService.registerUser(user1);
        userService.login(user1);
        userService.registerUser(user2);
        userService.login(user2);
        userService.registerUser(user3);
        userService.login(user3);
        friendshipService.sendRequest(friendshipRequest);
        friendshipRequest.setStatus(FriendshipStatus.ACCEPTED);
        friendshipService.respondRequest(friendshipRequest);
        Post post1 = postService.createPost(user1, "Hello World", List.of("Image1", "Image2"), List.of("Video1", "Video2"));
        Post post2 = postService.createPost(user2, "Hello Moon", List.of("Image1", "Image2"), List.of("Video1", "Video2"));
        Post post3 = postService.createPost(user3, "Hello Sun", List.of("Image1", "Image2"), List.of("Video1", "Video2"));
        final List<Post> newsFeed =postService.getNewsFeedForUser(user1);
        newsFeed.stream().forEach(post -> System.out.println(post.getContent()));

        likeService.likePost(user3, post1);
        likeService.likePost(user2, post1);
        likeService.likePost(user1, post1);
        List<Like> likes = likeService.getLikesByPost(post1);
        System.out.println("Total number of like to post: " + post1.getContent() + " is "+ likes.size());
        likes.stream().forEach(like -> System.out.println(like.getUser().getUsername()));

        commentService.addComment(user1, "This is a comment", post1);
        commentService.addComment(user2, "This is another comment", post1);
        List<Comment> comments = commentService.getCommentsForPost(post1);
        System.out.println("Total number of comments to post: " + post1.getContent() + " is "+ comments.size());
        comments.stream().forEach(comment -> System.out.println(comment.getUser().getUsername() + ": " + comment.getContent()));
    }
}