package data.models;

/**
 * Created by hyun on 2015-07-29.
 */
public class Mountain_Temp {


    public String id;
    public String img;
    public String name;
    public String address;
    public String text;
    public String user;
    public String user_img;
    public String lng;
    public String la;
    public String members;

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String reviews;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String like;

    public String getHate() {
        return hate;
    }

    public void setHate(String hate) {
        this.hate = hate;
    }

    public String hate;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String category;
    public String date;


}
