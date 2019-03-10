package data.models;

/**
 * Created by hyun on 2015-08-08.
 */
public class Chat {
    String user;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    String img;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    String idx;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String text;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;
}
