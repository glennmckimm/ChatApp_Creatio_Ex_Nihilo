package uk.ac.tees.t7014713.exnihilo_chatapplication.Model;

/**
 * Created by Glenn on 03/03/2019.
 */

public class User {

    private String userUID, username;

    public User() {

    }

    public User(String userUID, String username) {
        this.userUID = userUID;
        this.username = username;
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
}
