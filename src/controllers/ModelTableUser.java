package controllers;

/**
 * Model Table that displays the information of the checkout books for each user
 *
 */
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

    /**
     * Constructor that will automatically create a ModelTableUser Object
     * @param username username of the user
     * @param ISBN ISBN of the book
     * @param checkout_date checkout date of the book for this user
     * @param return_date return date of the book for this user
     */
    public ModelTableUser(String username, String ISBN, String checkout_date, String return_date){
        this.ISBN = ISBN;
        this.username = username;
        this.checkout_date = checkout_date;
        this.return_date = return_date;
    }

}
