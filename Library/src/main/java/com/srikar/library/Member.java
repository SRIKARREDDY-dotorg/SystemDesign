package com.srikar.library;

import java.util.List;
import java.util.UUID;

public class Member {
    private final String id;
    private String name;
    private String email;
    private List<Book> booksBorrowed;

    private Member() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBooksBorrowed() {
        return booksBorrowed;
    }

    public void setBooksBorrowed(List<Book> booksBorrowed) {
        this.booksBorrowed = booksBorrowed;
    }

    public static class Builder {
        private final Member member;

        private Builder() {
            this.member = new Member();
        }

        public Builder withName(String name) {
            member.setName(name);
            return this;
        }

        public Builder withEmail(String email) {
            member.setEmail(email);
            return this;
        }

        public Builder withBooksBorrowed(List<Book> booksBorrowed) {
            member.setBooksBorrowed(booksBorrowed);
            return this;
        }

        public Member build() {
            return member;
        }
    }
}
