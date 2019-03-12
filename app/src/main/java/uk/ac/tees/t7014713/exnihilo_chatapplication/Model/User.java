package uk.ac.tees.t7014713.exnihilo_chatapplication.Model;

/**
 * Created by Glenn on 03/03/2019.
 */

public class User {

    private String userUID;
    private String username;
    private String search;
    private String imageURL;

    public User() { }

    public User(String userUID, String username, String search, String imageURL) {
        this.userUID = userUID;
        this.username = username;
        this.search = search;
        this.imageURL = imageURL;
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

    public void setImageURL(String search) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
