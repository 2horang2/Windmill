package data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class Recipe extends Model {
    public static final Recipe dummy0 = new AutoValue_Recipe(
            "지리산",
            "Super easy, healthy tacos without using packaged taco seasoning. Spice-wise you just need some chili powder and ground cumin!",
            "http://cmcm1284.cafe24.com/okchun/house/imagex.jpg",
            Step.dummies(),
            "Updated at 05/06/2015",
            User.dummy());
    public static final Recipe dummy1 = new AutoValue_Recipe(
            "덕유산 국립공원",
            "A national dish of Slovakia! Halusky are like soft gnocchi, somewhat similar to German Spaetzle noodles. It's mixed with a slightly tangy, yet creamy Slovakian sheep cheese called Bryndza and topped with fried bacon. Bryndza can be hard to find outside of Slovakia, but you get pretty close by substituting another soft cheese like feta, cottage, goat cheese, etc. When I visited Slovakia, my friend introduced me to this dish. I had the chance to eat it with fresh brynza goat cheese in Spisské!",
            "http://cmcm1284.cafe24.com/okchun/house/imagex.jpg",
            Step.dummies(),
            "Updated at 06/06/2015",
            User.dummy());
    public static final Recipe dummy2 = new AutoValue_Recipe(
            "포항 갈매기 맛있나",
            "This is an old family favorite and a popular treat in America. The recipe is pretty sweet so you can probably safely reduce the sugar! Both kinds contain peanut butter, but you can optionally leave out the cocoa powder for just peanut butter cookies. Takes no more than 10 minutes to make (though you'll have to wait a while for them to set)! Get creative by mixing in coconut flakes, nuts, nutella, chocolate chips or whatever sounds fun!",
            "http://cmcm1284.cafe24.com/okchun/house/jang.jpg",
            Step.dummies(),
            "Updated at 07/06/2015",
            User.dummy());

    @JsonProperty("title")
    public abstract String title();

    @JsonProperty("description")
    public abstract String description();

    @JsonProperty("image_url")
    public abstract String imageUrl();

    @JsonProperty("steps")
    public abstract List<Step> steps();

    @JsonProperty("updated_at")
    public abstract String updatedAt();

    @JsonProperty("user")
    public abstract User user();

    @JsonCreator
    public static Recipe create(@JsonProperty("title") String title,
                                @JsonProperty("description") String description,
                                @JsonProperty("image_url") String imageUrl,
                                @JsonProperty("steps") List<Step> steps,
                                @JsonProperty("updated_at") String updatedAt,
                                @JsonProperty("user") User user) {
        return new AutoValue_Recipe(title, description, imageUrl, steps, updatedAt, user);
    }

    public static Recipe dummy() {
        int index = new SecureRandom().nextInt(3);
        switch (index) {
            case 0:
                return dummy0;
            case 1:
                return dummy1;
            default:
                return dummy2;
        }
    }


    public static List<Recipe> dummies() {
        return new ArrayList<Recipe>() {{
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
        }};
    }
}
