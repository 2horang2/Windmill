package data.models;

/**
 * Created by hyun on 2015-08-15.
 */
public class Review {

    String id;
    String idx;
    String user;
    String title;
    String text;
    String img;
    String user_img;

    String msg;

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    String members;

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    String warning;

    public String getMsg_img() {
        return msg_img;
    }

    public void setMsg_img(String msg_img) {
        this.msg_img = msg_img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    String msg_img;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    String like,reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String date;

    public String getR_img() {
        return r_img;
    }

    public void setR_img(String r_img) {
        this.r_img = r_img;
    }

    public String getR_user() {
        return r_user;
    }

    public void setR_user(String r_user) {
        this.r_user = r_user;
    }

    public String getR_text() {
        return r_text;
    }

    public void setR_text(String r_text) {
        this.r_text = r_text;
    }

    public String getR_date() {
        return r_date;
    }

    public void setR_date(String r_date) {
        this.r_date = r_date;
    }

    public String getR_user2() {
        return r_user2;
    }

    public void setR_user2(String r_user2) {
        this.r_user2 = r_user2;
    }

    public String getR_img2() {
        return r_img2;
    }

    public void setR_img2(String r_img2) {
        this.r_img2 = r_img2;
    }

    public String getR_text2() {
        return r_text2;
    }

    public void setR_text2(String r_text2) {
        this.r_text2 = r_text2;
    }

    public String getR_date2() {
        return r_date2;
    }

    public void setR_date2(String r_date2) {
        this.r_date2 = r_date2;
    }

    String r_img,r_user,r_text,r_date;
    String r_img2,r_user2,r_text2,r_date2;
}
