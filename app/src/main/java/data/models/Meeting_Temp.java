package data.models;

/**
 * Created by hyun on 2015-07-31.
 */
public class Meeting_Temp {
    String idx;
    String img;
    String mountain;
    String date;
    String title;
    String sub;
    String user;
    String userimg;
    String Category;

    String view;
    String member;
    String text;

    public String getMemberImgs() {
        return memberImgs;
    }

    public void setMemberImgs(String memberImgs) {
        this.memberImgs = memberImgs;
    }

    String memberImgs;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }



    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }



    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMountain() {
        return mountain;
    }

    public void setMountain(String mountain) {
        this.mountain = mountain;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
