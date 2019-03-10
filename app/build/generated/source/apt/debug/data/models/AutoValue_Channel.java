package data.models;

import java.util.List;

@javax.annotation.Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Channel extends Channel {
  private final String name;
  private final List<Mountain> recipes;

  AutoValue_Channel(
      String name,
      List<Mountain> recipes) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    if (recipes == null) {
      throw new NullPointerException("Null recipes");
    }
    this.recipes = recipes;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public List<Mountain> recipes() {
    return recipes;
  }

  @Override
  public String toString() {
    return "Channel{"
        + "name=" + name
        + ", recipes=" + recipes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Channel) {
      Channel that = (Channel) o;
      return (this.name.equals(that.name()))
          && (this.recipes.equals(that.recipes()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= name.hashCode();
    h *= 1000003;
    h ^= recipes.hashCode();
    return h;
  }
}
