package controllers;

public class ModelTableUser {
    String ISBN, username, checkout_date, return_date;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCheckoutDate() {
        return checkout_date;
    }

    public void setCheckoutDate(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getReturnDate() {
        return return_date;
    }

    public void setReturnDate(String return_date) {
        this.return_date = return_date;
    }

    public ModelTableUser(String username, String ISBN, String checkout_date, String return_date){
        this.ISBN = ISBN;
        this.username = username;
        this.checkout_date = checkout_date;
        this.return_date = return_date;
    }

}
