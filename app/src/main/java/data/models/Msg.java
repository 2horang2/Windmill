package data.models;

/**
 * Created by USER on 2015-09-15.
 */
public class Msg {

    public String getSend_person_img() {
        return send_person_img;
    }

    public void setSend_person_img(String send_person_img) {
        this.send_person_img = send_person_img;
    }

    String send_person_img;
    public String getSend_person() {
        return send_person;
    }

    public void setSend_person(String send_person) {
        this.send_person = send_person;
    }

    String send_person;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    String idx,person,text,date,img;
}
