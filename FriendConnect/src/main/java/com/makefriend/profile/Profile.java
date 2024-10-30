package com.makefriend.profile;

import java.util.List;

public class Profile {
    private String profilePicture;
    private String  bio;
    private List<String> interests;

    private Profile() {}
    public static Builder builder() {
        return new Builder();
    }

    public String getProfilePicture() {
        return profilePicture;
    }
    public String getBio() {
        return bio;
    }
    public List<String> getInterests() {
        return interests;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public static class Builder {
        private String profilePicture;
        private String bio;
        private List<String> interests;

        public Builder withProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public Builder withBio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder withInterests(List<String> interests) {
            this.interests = interests;
            return this;
        }

        public Profile build() {
            Profile profile = new Profile();
            profile.profilePicture = this.profilePicture;
            profile.bio = this.bio;
            profile.interests = this.interests;
            return profile;
        }
    }
}
