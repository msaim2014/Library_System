package controllers;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String numAvailable;
    private String checkOut;
    private String checkIn;

    public Book(String isbn, String title, String author, String genre, String numAvailable, String checkOut, String checkIn) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.numAvailable = numAvailable;
        this.checkOut = checkOut;
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
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

    public String getNumAvailable() {
        return numAvailable;
    }

    public void setNumAvailable(String numberAvailable) {
        this.numAvailable = numberAvailable;
    }
}
