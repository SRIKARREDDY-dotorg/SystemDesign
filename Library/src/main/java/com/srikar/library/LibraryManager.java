package com.srikar.library;

import java.util.Date;
import java.util.List;

public class LibraryManager {
    private static LibraryManager INSTANCE;
    private final BookManager bookManager;
    private final MemberManager memberManager;
    private final int MAX_NUM_OF_BOOKS = 5;
    private final int LOAN_DURATION = 7;
    private final int CHARGE_PER_DAY = 1;

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

    // Todo Add a feature for user to choose the books in the returned books
    public List<Book> borrowBooks(String memberId, List<String> keywords) {
        if (!memberManager.isValidMember(memberId)) {
            throw new IllegalArgumentException("Invalid member id");
        }

        List<Book> books = bookManager.fetchBooks(keywords);
        if (books.isEmpty()) {
            throw new IllegalArgumentException("No books found");
        }
        Member member = memberManager.getMember(memberId);
        int currentLimit = MAX_NUM_OF_BOOKS - member.getBooksBorrowed().size();
        List<Book> borrowedBooks = member.getBooksBorrowed();
        for (Book book : borrowedBooks) {
            if (book.getLoanDuration() > LOAN_DURATION) {
                String history = "This book is: " + book.getTitle() + ", is overdue charge: " + (book.getLoanDuration() - LOAN_DURATION) * CHARGE_PER_DAY + "rs";
                member.addHistory(history);
                System.out.println(history);
            }
        }
        if (currentLimit == 0) {
            throw new IllegalArgumentException("You have reached the limit of books to borrow");
        } else if (currentLimit < 0) {
            throw new IllegalArgumentException("You have exceeded the limit of books to borrow");
        } else if (books.size() > currentLimit) {
            books = books.subList(0, currentLimit);
        }
        member.setBooksBorrowed(books);
        books.forEach(book -> {
            book.setBorrowed(true);
            book.setBorrowedOn(new Date());
            book.setBorrowedBy(member);
        });
        List<String> titles = books.stream().map(Book::getTitle).toList();
        member.addHistory("These books are: " + titles + ", Borrowed");
        return books;
    }

    // TODO Handle the payments flow separately.
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
        // Handle the cases if you are returning someone else's borrowed books
        if (borrowedBooks.isEmpty()) {
            throw new IllegalArgumentException("You have not borrowed any books");
        }
        // Handle the charge payment for the books that are overdue
        List<Book> overdueBooks = borrowedBooks.stream().filter(book -> book.getLoanDuration() > LOAN_DURATION).toList();
        if (!overdueBooks.isEmpty()) {
            overdueBooks.forEach(book -> {
                String history = "This book is: " + book.getTitle() + ", is overdue charge: " + (book.getLoanDuration() - LOAN_DURATION) * CHARGE_PER_DAY + "rs";
                member.addHistory(history);
                System.out.println(history);
            });
        }
        borrowedBooks.removeAll(validBooks);
        member.setBooksBorrowed(borrowedBooks);
        books.forEach(book -> book.setBorrowed(false));
        member.addHistory("These books are: " + books.stream().map(Book::getTitle).toList() + ", Returned");
    }
}
