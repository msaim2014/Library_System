package controllers;

/**
 * ModelTable is the table being used with the
 * observableList to fill the book tables
 */
public class ModelTable {
    String title, author, genre, isbn, availability;

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String numAvailable) {
        this.availability = numAvailable;
    }

    /**
     * Constructor that will create a ModelTable class
     * @param isbn ISBN of the book
     * @param title Title of the book
     * @param author Author of the book
     * @param genre Genre of the book
     * @param numAvailable How many books are available
     */
    public ModelTable(String isbn, String title, String author, String genre, String numAvailable){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = numAvailable;
    }
}
