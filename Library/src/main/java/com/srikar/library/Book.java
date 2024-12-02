package com.srikar.library;

import java.util.Date;
import java.util.UUID;

public class Book {
    private final String id;
    private String title;
    private String author;
    private String isbn;
    private Long publicationYear;
    private boolean isBorrowed;
    private Member borrowedBy;
    private Date borrowedOn;

    private Book() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Long publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public Member getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(Member borrowedBy) {
        this.borrowedBy = borrowedBy;
    }
    public Date getBorrowedOn() {
        return borrowedOn;
    }
    public void setBorrowedOn(Date borrowedOn) {
        this.borrowedOn = borrowedOn;
    }

    /**
     * Number of days since the book was borrowed
     * @return
     */
    public Long getLoanDuration() {
        return borrowedOn != null ? (new Date().getTime() - borrowedOn.getTime()) / (1000 * 60 * 60 * 24) : 0;
    }

    public static class Builder {
        private final Book book;

        private Builder() {
            this.book = new Book();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder withTitle(String title) {
            book.setTitle(title);
            return this;
        }

        public Builder withAuthor(String author) {
            book.setAuthor(author);
            return this;
        }

        public Builder withIsbn(String isbn) {
            book.setIsbn(isbn);
            return this;
        }

        public Builder withPublicationYear(Long publicationYear) {
            book.setPublicationYear(publicationYear);
            return this;
        }

        public Builder withIsBorrowed(boolean isBorrowed) {
            book.isBorrowed = isBorrowed;
            return this;
        }

        public Builder withBorrowedBy(Member borrowedBy) {
            book.borrowedBy = borrowedBy;
            return this;
        }

        public Builder withBorrowedOn(Date borrowedOn) {
            book.borrowedOn = borrowedOn;
            return this;
        }

        public Book build() {
            return book;
        }
    }
}
