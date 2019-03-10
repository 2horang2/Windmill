package adapter;

import data.models.Meeting;


/**
 * Created by hyun on 2015-07-31.
 */
@javax.annotation.Generated("com.google.auto.value.processor.AutoValueProcessor")
public class AutoValue_Meeting2 extends Meeting {

    private final String idx;
    private final String img;
    private final String mountain;
    private final String date;
    private final String title;
    private final String sub;
    private final String user;
    private final String userimg;
    private final String category;

    private final String view;
    private final String member;
    private final String text;
    private final String member_imgs;

    AutoValue_Meeting2(
            String idx,
            String img,
            String mountain,
            String date,
            String title,
            String sub,
            String user,
            String userimg,
            String category,
            String view,
            String member,
            String text,
            String member_imgs

    ){
        if (idx == null) {
            throw new NullPointerException("Null idx");
        }
        this.idx = idx;
        if (img == null) {
            throw new NullPointerException("Null img");
        }
        this.img = img;
        if (mountain == null) {
            throw new NullPointerException("Null address");
        }
        this.mountain = mountain;
        if (date == null) {
            throw new NullPointerException("Null text");
        }
        this.date = date;
        if (title == null) {
            throw new NullPointerException("Null text");
        }
        this.title = title;
        if (sub == null) {
            throw new NullPointerException("Null user");
        }
        this.sub = sub;
        if (user == null) {
            throw new NullPointerException("Null user");
        }
        this.user = user;
        if (userimg == null) {
            throw new NullPointerException("Null user");
        }
        this.userimg = userimg;
        if (category == null) {
            throw new NullPointerException("Null user");
        }
        this.category = category;

        if (view == null) {
            throw new NullPointerException("Null user");
        }
        this.view = view;

        if (member == null) {
            throw new NullPointerException("Null user");
        }
        this.member = member;

        if (text == null) {
            throw new NullPointerException("Null user");
        }
        this.text = text;

        if (member_imgs == null) {
            throw new NullPointerException("Null user");
        }
        this.member_imgs = member_imgs;
    }


    public String idx() {
        return idx;
    }


    public String img() {
        return img;
    }


    public String mountain() {
        return mountain;
    }


    public String title() {
        return title;
    }


    public String sub() {
        return sub;
    }


    public String user() {
        return user;
    }


    public String userimg() {
        return userimg;
    }


    public String category() {
        return category;
    }


    public String date() {
        return date;
    }


    public String view() {
        return view;
    }


    public String member() {
        return member;
    }


    public String text() {
        return text;
    }

    public String member_imgs() {
        return member_imgs;
    }

    public String toString() {
        return "Mountain{"
                + "idx=" + idx
                + ", img=" + img
                + ", mountain=" + mountain
                + ", date=" + date
                + ", title=" + title
                + ", sub=" + sub
                + ", user=" + user
                + ", userimg=" + userimg
                + ", category=" + category
                + ", view=" + view
                + ", member=" + member
                + ", text=" + text
                + ", member_imgs=" + member_imgs
                + "}";
    }


    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Meeting) {
            Meeting that = (Meeting) o;
            return (this.idx.equals(that.idx()))
                    && (this.img.equals(that.img()))
                    && (this.mountain.equals(that.mountain()))
                    && (this.date.equals(that.date()))
                    && (this.title.equals(that.title()))
                    && (this.sub.equals(that.sub()))
                    && (this.user.equals(that.user()))
                    && (this.userimg.equals(that.userimg()))
                    && (this.category.equals(that.category())
                    && (this.view.equals(that.view()))
                    && (this.member.equals(that.member()))
                    && (this.text.equals(that.text())))
                    && (this.member_imgs.equals(that.member_imgs()));
        }
        return false;
    }


    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= idx.hashCode();
        h *= 1000003;
        h ^= img.hashCode();
        h *= 1000003;
        h ^= mountain.hashCode();
        h *= 1000003;
        h ^= date.hashCode();
        h *= 1000003;
        h ^= title.hashCode();
        h *= 1000003;
        h ^= sub.hashCode();
        h *= 1000003;
        h ^= user.hashCode();
        h *= 1000003;
        h ^= userimg.hashCode();
        h *= 1000003;
        h ^= category.hashCode();
        h *= 1000003;
        h ^= view.hashCode();
        h *= 1000003;
        h ^= member.hashCode();
        h *= 1000003;
        h ^= text.hashCode();
        h *= 1000003;
        h ^= member_imgs.hashCode();
        return h;
    }
}
