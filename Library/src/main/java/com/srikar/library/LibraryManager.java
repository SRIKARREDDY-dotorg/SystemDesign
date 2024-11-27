package com.srikar.library;

import java.util.List;

// Validate the usecases for the library
// whether to store the map of member to books or have it in member object?
// what needs to be the history?
public class LibraryManager {
    private static LibraryManager INSTANCE;
    private final BookManager bookManager;
    private final MemberManager memberManager;

    private LibraryManager() {
        bookManager = BookManager.getInstance();
        memberManager = MemberManager.getInstance();
    }

    public static LibraryManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryManager();
        }
        return INSTANCE;
    }
    public void addBooks(List<Book> books) {
        bookManager.addBooks(books);
    }
    public void removeBooks(List<Book> books) {
        bookManager.removeBooks(books);
    }
    public void registerMember(String name, String email) {
        memberManager.addMember(member);
    }
}
