package data.models;

import java.util.List;

@javax.annotation.Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Channel2 extends Channel2 {
  private final String name;
  private final List<Meeting> recipes;

  AutoValue_Channel2(
      String name,
      List<Meeting> recipes) {
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
  public List<Meeting> recipes() {
    return recipes;
  }

  @Override
  public String toString() {
    return "Channel2{"
        + "name=" + name
        + ", recipes=" + recipes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Channel2) {
      Channel2 that = (Channel2) o;
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
