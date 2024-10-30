package com.makefriend.profile;

import com.makefriend.user.User;

public class ProfileService {
    private static ProfileService instance;

    public static ProfileService getInstance() {
        if(instance == null) {
            return new ProfileService();
        }
        return instance;
    }

    private ProfileService() {}
    public void update(Profile profile, User user) {
        final Profile userProfile = user.getProfile();
        if(!profile.getBio().isBlank()) {
            userProfile.setBio(profile.getBio());
        }
        if(!profile.getInterests().isEmpty()) {
            userProfile.setInterests(profile.getInterests());
        }
        if(!profile.getProfilePicture().isBlank()) {
            userProfile.setProfilePicture(profile.getProfilePicture());
        }
    }
}
