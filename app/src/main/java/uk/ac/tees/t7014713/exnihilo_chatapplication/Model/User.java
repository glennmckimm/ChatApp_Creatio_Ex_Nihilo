package uk.ac.tees.t7014713.exnihilo_chatapplication.Model;

/**
 * Created by Glenn on 03/03/2019.
 */

public class User {

    private String userUID;
    private String username;
    private String search;

    public User() { }

    public User(String userUID, String username, String search) {
        this.userUID = userUID;
        this.username = username;
        this.search = search;
    }

    public void setUserUID(String userUID) {
       this.userUID = userUID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }
}
