package data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Channel2 extends Model {

    @JsonProperty("name")
    public abstract String name();

    @JsonProperty("recipes")
    public abstract List<Meeting> recipes();

    @JsonCreator
    public static Channel2 create(@JsonProperty("name") String name,
                                 @JsonProperty("recipes") List<Meeting> recipes) {
        return new AutoValue_Channel2(name, recipes);

    }

    public static Channel2 dummy() {
        return new AutoValue_Channel2("Channel", Meeting.dummies(5));
    }
}
