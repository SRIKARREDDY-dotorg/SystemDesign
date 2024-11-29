package com.srikar.library;

import java.util.List;

// Todo Add limitations of Max Num of books and Loan duration (charge 1rs per day).
public class LibraryManager {
    private static LibraryManager INSTANCE;
    private final BookManager bookManager;
    private final MemberManager memberManager;
    private final int MAX_NUM_OF_BOOKS = 5;
    private final int LOAN_DURATION = 7;

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
        Member member = Member.Builder.newInstance().withName(name).withEmail(email).build();
        memberManager.addMember(member);
    }
    public void removeMember(String memberId) {
        memberManager.removeMember(memberId);
    }
    public List<Book> borrowBooks(String memberId, List<String> keywords) {
        if (!memberManager.isValidMember(memberId)) {
            throw new IllegalArgumentException("Invalid member id");
        }

        List<Book> books = bookManager.fetchBooks(keywords);
        Member member = memberManager.getMember(memberId);
        member.setBooksBorrowed(books);
        books.forEach(book -> book.setBorrowed(true));
        List<String> titles = books.stream().map(Book::getTitle).toList();
        member.addHistory("These books are: " + titles + ", Borrowed");
        return books;
    }

    public void returnBooks(String memberId, List<Book> books) {
        if (!memberManager.isValidMember(memberId)) {
            throw new IllegalArgumentException("Invalid member id");
        }
        books.stream().map(Book::getId).forEach(bookId -> {
            if (!bookManager.isValidBook(bookId)) {
                throw new IllegalArgumentException("Invalid book id: " + bookId);
            }
        });
        List<Book> validBooks = books.stream().filter(book -> bookManager.isValidBook(book.getId())).toList();
        Member member = memberManager.getMember(memberId);
        List<Book> borrowedBooks = member.getBooksBorrowed();
        borrowedBooks.removeAll(validBooks);
        member.setBooksBorrowed(borrowedBooks);
        books.forEach(book -> book.setBorrowed(false));
        member.addHistory("These books are: " + books.stream().map(Book::getTitle).toList() + ", Returned");
    }
}
