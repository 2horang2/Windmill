package data.models;

/**
 * Created by hyun on 2015-08-09.
 */
public class Reply {

    String user_img;
    String user;
    String text;

    public String getChatroomidx() {
        return chatroomidx;
    }

    public void setChatroomidx(String chatroomidx) {
        this.chatroomidx = chatroomidx;
    }

    String chatroomidx;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    String idx;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    String date;
}
