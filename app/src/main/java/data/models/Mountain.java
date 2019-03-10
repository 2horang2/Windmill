package data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by hyun on 2015-07-29.
 */
public abstract class Mountain extends Model{


    @JsonProperty("id")
    public abstract String id();

    @JsonProperty("img")
    public abstract String img();

    @JsonProperty("name")
    public abstract String name();

    @JsonProperty("address")
    public abstract String address();

    @JsonProperty("text")
    public abstract String text();

    @JsonProperty("user")
    public abstract String user();

    @JsonProperty("user_img")
    public abstract String user_img();

    @JsonProperty("category")
    public abstract String category();

    @JsonProperty("lng")
    public abstract String lng();

    @JsonProperty("la")
    public abstract String la();

    @JsonProperty("like")
    public abstract String like();

    @JsonProperty("hate")
    public abstract String hate();

    @JsonProperty("members")
    public abstract String members();

    @JsonProperty("reviews")
    public abstract String reviews();


    @JsonCreator
    public static Mountain create(@JsonProperty("id") String id,
                                @JsonProperty("img") String img,
                                @JsonProperty("name") String name,
                                @JsonProperty("address") String address,
                                @JsonProperty("text") String text,
                                @JsonProperty("user") String user,
                                @JsonProperty("user_img") String user_img,
                                @JsonProperty("category") String category,
                                  @JsonProperty("lng") String lng,
                                  @JsonProperty("la") String la,
                                  @JsonProperty("like") String like,
                                  @JsonProperty("hate") String hate,
                                  @JsonProperty("members") String members,
                                  @JsonProperty("reviews") String reviews) {
        return new AutoValue_Mountain(id, img, name, address, text, user,user_img,category,lng,la,like,hate,members,reviews);
    }

    // return dummy2;
    public static Mountain dummy(int index) {
        Mountain dummy = new AutoValue_Mountain(MountainList.mountainList.get(index).getId(),
                MountainList.mountainList.get(index).getImg(),
                MountainList.mountainList.get(index).getName(),
                MountainList.mountainList.get(index).getAddress(),
                MountainList.mountainList.get(index).getText(),
                MountainList.mountainList.get(index).getUser(),
                MountainList.mountainList.get(index).getUser_img(),
                MountainList.mountainList.get(index).getCategory(),
                MountainList.mountainList.get(index).getLng(),
                MountainList.mountainList.get(index).getLa(),
                MountainList.mountainList.get(index).getLike(),
                MountainList.mountainList.get(index).getHate(),
                MountainList.mountainList.get(index).getMembers(),
                MountainList.mountainList.get(index).getReviews()
        );
        return dummy;
    }


    public static ArrayList<Mountain> dummies() {
        ArrayList<Mountain> aaa = new ArrayList<Mountain>();
        int i=0;
        for(i=0;i<MountainList.mountainList.size();i++){
           aaa.add(Mountain.dummy(i));
        }
        return aaa;
    }
}





