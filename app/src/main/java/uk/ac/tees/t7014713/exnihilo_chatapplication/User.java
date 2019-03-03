package uk.ac.tees.t7014713.exnihilo_chatapplication;

/**
 * Created by Glenn on 03/03/2019.
 */

public class User {

    private String userID, username;

    public User() {

    }

    public User(String userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
