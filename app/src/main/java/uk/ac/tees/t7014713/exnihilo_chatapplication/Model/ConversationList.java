package uk.ac.tees.t7014713.exnihilo_chatapplication.Model;

/**
 * Created by Glenn on 10/03/2019.
 */

public class ConversationList {

    private String id;

    public ConversationList(String id) {
        this.setId(id);
    }

    public ConversationList() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
