package com.srikar.library;

import java.util.Date;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LibraryManager libraryManager = LibraryManager.getInstance();
        MemberManager memberManager = MemberManager.getInstance();
        BookManager bookManager = BookManager.getInstance();
        Member member1 = Member.Builder.newInstance().withName("Srikar").withEmail("srikar@gmail.com").build();
        Member member2 = Member.Builder.newInstance().withName("Reddy").withEmail("reddy@gmail.com").build();
        memberManager.addMember(member1);
        memberManager.addMember(member2);
        Book book1 = Book.Builder.newInstance().withTitle("The Alchemist").withAuthor("Paulo Coelho").withPublicationYear(1988L).withIsbn("9780061120084").build();
        Book book2 = Book.Builder.newInstance().withTitle("The Da Vinci Code").withAuthor("Dan Brown").withPublicationYear(2003L).withIsbn("9780307474278").build();
        Book book3 = Book.Builder.newInstance().withTitle("The Catcher in the Rye").withAuthor("J.D. Salinger").withPublicationYear(1951L).withIsbn("9780316769174").build();
        Book book4 = Book.Builder.newInstance().withTitle("The Great Gatsby").withAuthor("F. Scott Fitzgerald").withPublicationYear(1925L).withIsbn("9780743273565").build();
        Book book5 = Book.Builder.newInstance().withTitle("The Hunger Games").withAuthor("Suzanne Collins").withPublicationYear(2008L).withIsbn("9780439023481").build();
        Book book6 = Book.Builder.newInstance().withTitle("The Hobbit").withAuthor("J.R.R. Tolkien").withPublicationYear(1937L).withIsbn("9780547928227").build();
        Book book7 = Book.Builder.newInstance().withTitle("The Kite Runner").withAuthor("Khaled Hosseini").withPublicationYear(2003L).withIsbn("9781594480003").build();
        Book book8 = Book.Builder.newInstance().withTitle("The Lord of the Rings").withAuthor("J.R.R. Tolkien").withPublicationYear(1954L).withIsbn("9780618640157").build();
        bookManager.addBooks(List.of(book1, book2, book3, book4, book5, book6, book7, book8));
        List<Book> books = libraryManager.borrowBooks(member1.getId(), List.of("9780061120084", "9780307474278", "9780316769174", "9780743273565", "9780439023481", "9780547928227", "9781594480003", "9780618640157"));

        // Add 7 days to the current date
        // Simulate Loan duration
        book1.setBorrowedOn(new Date(System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000));
        System.out.println("The loan duration is " + book1.getLoanDuration());
        System.out.println("====================================================================================================");
        libraryManager.returnBooks(member1.getId(), books.subList(0, 3));
    }
}