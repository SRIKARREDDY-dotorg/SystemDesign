// Online Java Compiler
// Use this editor to write, compile and run your Java code online

class Main {
    public static void main(String[] args) {
        System.out.println("Try programiz.pro");
    }
}

class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Profile profile;
    private List<User> friends;
    private PrivacySettings privacySettings;
    
    private User(UserBuilder builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.profile = builder.profile;
        this.privacySettings = new PrivacySettings();
        this.friends = new ArrayList<>();
    }
    
    public String getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    
    public String setName(String name) {
        this.name = name;
    }
    public String setEmail(String email) {
        this.email = email;
    }
    public String setPassword(String password) {
        this.password = password;
    }
    
    public Profile getProfile() {
        return profile;
    }
    public String getPrivacySettings() {
        return privacySettings;
    }
    public void addFriend(User friend) {
        if(!friends.contain(friend)) {
            friends.add(friend);
        }
    }
    public void removeFriend(User friend) {
        if(friends.contain(friend)) {
            friends.remove(friend);
        }
    }
    
    public static class UserBuilder() {
        private String userId;
        private String name;
        private String email;
        private String password;
        private Profile profile;
        
        public UserBuilder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }
        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }
        public UserBuilder withProfile(Profile profile) {
            this.profile = profile;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}

class Profile {
    private String bio;
    private String profilePicture;
    private List<String> interests;
    
    private Profile(ProfileBuilder profileBuilder) {
        this.bio = builder.bio;
        this.profilePicture = builder.profilePicture;
        this.interests = builder.interests;
    }
    
    public String getBio() {
        return this.bio;
    }
    public String getProfilePicture() {
        return this.profilePicture;
    }
    public String getInterests() {
        return this.interests;
    }
    
    public static class ProfileBuilder() {
        private String bio;
        private String profilePicture;
        private List<String> interests;
        
        public ProfileBuilder withProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }
        public ProfileBuilder withBio(String bio) {
            this.bio = bio;
            return this;
        }
        public ProfileBuilder withInterests(List<String> interests) {
            this.interests = interests;
            return this;
        }
        public Profile build() {
            return new Profile(this);
        }
    }
}

class PrivacySettings {
    private boolean isPublic;
    
    public PrivacySettings() {
        this.isPublic = true;
    }
    
    public void setVisibility(boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    public boolean isPublic() {
        return this.isPublic;
    }
}
