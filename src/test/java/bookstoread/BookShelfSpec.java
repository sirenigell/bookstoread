package bookstoread;

import booksread.Book;
import booksread.BookShelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;

    @BeforeEach
    void init() throws Exception{
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel",
                LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick " +
                "Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
    }

    @Test
    public void shelfEmptyNoBookAdded()throws Exception{

        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");

    }

    @Test
    void bookshelfContainsTwoBooksWhenTwoBooksAdded(){
        shelf.add(effectiveJava, codeComplete);
        List<Book> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    @Test
    void emptyBookShelfWhenAddIsCalledWithoutBooks(){
        shelf.add();
        assertTrue(shelf.books().isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    void booksReturnedFromBookShelfIsImmutableForClient(){
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = shelf.books();
        try {
            books.add(mythicalManMonth);
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof  UnsupportedOperationException, () ->
                    "Should throw UnsupportedOperationException");
        }
    }

    @Disabled("Needs to implement Comparator")
    @Test
    void arrangeBooksByAlphabeticOrder(){
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);

        List<Book> sortedShelf = shelf.arrange();
        assertEquals(Arrays.asList(codeComplete, effectiveJava,
                mythicalManMonth ), sortedShelf, ()->"Books should be arranged in alphabetic order");

    }

    @Test
    void booksInInsertingOrderAfterCallingArrange(){
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        shelf.arrange();
        List<Book> books = shelf.books();
        assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth),books, ()->"Books are not in insertion order");
    }

    @Test
    void bookshelfArrangedByUserChoice(){
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);
        Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
        List<Book> books = shelf.arrange(reversed);
        assertThat(books).isSortedAccordingTo(reversed);
    }

}














