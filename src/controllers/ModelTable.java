package controllers;

public class ModelTable {
    String title, author, genre, isbn, numAvailable;

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

    public String getNumAvailable() {
        return numAvailable;
    }

    public void setNumAvailable(String numAvailable) {
        this.numAvailable = numAvailable;
    }

    public ModelTable(String isbn, String title, String author, String genre, String numAvailable){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.numAvailable = numAvailable;
    }

}
