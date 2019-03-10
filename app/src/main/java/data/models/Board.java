package data.models;

import java.util.ArrayList;

/**
 * Created by hyun on 2015-08-09.
 */
public class Board {

    String idx;
    String cate;
    String title;
    String writer;
    String date;
    String view;
    String text;



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    String img;

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    String userimg;

    public String getChatroom_idx() {
        return chatroom_idx;
    }

    public void setChatroom_idx(String chatroom_idx) {
        this.chatroom_idx = chatroom_idx;
    }

    String chatroom_idx;
    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
