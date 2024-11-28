package com.srikar.library;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookManager {
    private static BookManager INSTANCE;
    private final Map<String, Book> bookCatalog;

    private BookManager() {
        bookCatalog = new ConcurrentHashMap<>();
    }

    public static BookManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookManager();
        }
        return INSTANCE;
    }

    public boolean isValidBook(String bookId) {
        return bookCatalog.containsKey(bookId);
    }

    public List<Book> fetchBooks(List<String> keywords) {
        if (keywords.size() == 0) {
            return bookCatalog.values().stream().filter(book -> !book.isBorrowed()).toList();
        }
        // search for each keyword
        return bookCatalog.values().stream()
                .filter(book -> keywords.stream()
                        .anyMatch(keyword ->
                                (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.getIsbn().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.getPublicationYear().toString().toLowerCase().contains(keyword.toLowerCase())) && !book.isBorrowed()))
                .toList();
    }

    public void addBooks(List<Book> books) {
        books.forEach(book -> bookCatalog.put(book.getId(), book));
    }

    public void removeBooks(List<Book> books) {
        books.forEach(book -> bookCatalog.remove(book.getId()));
    }
}
