package data.models;

@javax.annotation.Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Mountain extends Mountain {

    private final String id;
    private final String img;
    private final String name;
    private final String address;
    private final String text;
    private final String user;
    private final String user_img;
    private final String category;
    private final String lng;
    private final String la;
    private final String like;
    private final String hate;
    private final String members;
    private final String reviews;

    AutoValue_Mountain(
            String id,
            String img,
            String name,
            String address,
            String text,
            String user,
            String user_img,
            String category,
            String lng,
            String la,
            String like,
            String hate,
            String members,
            String reviews) {
        if (id == null) {
            throw new NullPointerException("Null id");
        }
        this.id = id;
        if (img == null) {
            throw new NullPointerException("Null img");
        }
        this.img = img;
        if (name == null) {
            throw new NullPointerException("Null name");
        }
        this.name = name;
        if (address == null) {
            throw new NullPointerException("Null address");
        }
        this.address = address;
        if (text == null) {
            throw new NullPointerException("Null text");
        }
        this.text = text;
        if (user == null) {
            throw new NullPointerException("Null user");
        }
        this.user = user;
        if (user_img == null) {
            throw new NullPointerException("Null user");
        }
        this.user_img = user_img;
        if (category == null) {
            throw new NullPointerException("Null user");
        }
        this.category = category;

        if (lng == null) {
            throw new NullPointerException("Null user");
        }
        this.lng = lng;


        if (la == null) {
            throw new NullPointerException("Null user");
        }
        this.la = la;

        if (like == null) {
            throw new NullPointerException("Null user");
        }
        this.like = like;

        if (hate == null) {
            throw new NullPointerException("Null user");
        }
        this.hate = hate;

        if (members == null) {
            throw new NullPointerException("Null user");
        }
        this.members = members;

        if (reviews == null) {
            throw new NullPointerException("Null user");
        }
        this.reviews = reviews;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String img() {
        return img;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public String user() {
        return user;
    }

    @Override
    public String user_img() {
        return user_img;
    }

    @Override
    public String category() {
        return category;
    }

    @Override
    public String lng() {
        return lng;
    }

    @Override
    public String la() { return la; }


    public String like() {
        return like;
    }


    public String hate() { return hate; }


    public String members() {
        return members;
    }


    public String reviews() {
        return reviews;
    }


    @Override
    public String toString() {
        return "Mountain{"
                + "id=" + id
                + ", img=" + img
                + ", name=" + name
                + ", address=" + address
                + ", text=" + text
                + ", user=" + user
                + ", user_img=" + user_img
                + ", category=" + category
                + ", lng=" + lng
                + ", la=" + la
                + ", like=" + like
                + ", hate=" + hate
                + ", members=" + members
                + ", reviews=" + reviews
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Mountain) {
            Mountain that = (Mountain) o;
            return (this.id.equals(that.id()))
                    && (this.img.equals(that.img()))
                    && (this.name.equals(that.name()))
                    && (this.address.equals(that.address()))
                    && (this.text.equals(that.text()))
                    && (this.user.equals(that.user()))
                    && (this.user_img.equals(that.user_img()))
                    && (this.category.equals(that.category()))
                    && (this.lng.equals(that.lng()))
                    && (this.la.equals(that.la()))
                    && (this.like.equals(that.like()))
                    && (this.hate.equals(that.hate()))
                    && (this.members.equals(that.members()))
                    && (this.reviews.equals(that.reviews()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= id.hashCode();
        h *= 1000003;
        h ^= img.hashCode();
        h *= 1000003;
        h ^= name.hashCode();
        h *= 1000003;
        h ^= address.hashCode();
        h *= 1000003;
        h ^= text.hashCode();
        h *= 1000003;
        h ^= user.hashCode();
        h *= 1000003;
        h ^= user_img.hashCode();
        h *= 1000003;
        h ^= category.hashCode();
        h *= 1000003;
        h ^= lng.hashCode();
        h *= 1000003;
        h ^= la.hashCode();
        h *= 1000003;
        h ^= like.hashCode();
        h *= 1000003;
        h ^= hate.hashCode();
        h *= 1000003;
        h ^= members.hashCode();
        h *= 1000003;
        h ^= reviews.hashCode();
        return h;
    }
}
