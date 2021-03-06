package controllers;

/**
 * Book class that holds the information for a book
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String availability;

    /**
     * Constructor that automatically creates a book class
     * @param isbn ISBN of the book
     * @param title Title of the book
     * @param author Author of the book
     * @param genre Genre of the book
     * @param availability How many are available
     */
    public Book(String isbn, String title, String author, String genre, String availability) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String numberAvailable) {
        this.availability = numberAvailable;
    }
}
