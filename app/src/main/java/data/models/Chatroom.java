package data.models;

/**
 * Created by hyun on 2015-08-08.
 */
public class Chatroom {

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String idx;
    String title;
    String msg;
    String member;
    String time;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    String img;

}
