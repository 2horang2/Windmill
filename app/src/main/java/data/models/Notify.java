package data.models;

/**
 * Created by jhm1283 on 2015-09-02.
 */
public class Notify {
    String id;
    String title;
    String date;

    public boolean isFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    boolean fold = false;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String text;
}
