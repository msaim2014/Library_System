package controllers;

public class Account {
    private String username;
    private String pass;
    private int isAdmin;

    private static Account account = null;

    //private so no one can create an account object
    private Account(){}

    //use getInstance instead of constructor
    public static Account getInstance(){
        if(account==null){
            account = new Account();
        }
        return account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
