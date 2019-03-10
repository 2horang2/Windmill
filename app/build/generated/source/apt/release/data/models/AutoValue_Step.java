package data.models;


@javax.annotation.Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Step extends Step {
  private final String description;
  private final String imageUrl;

  AutoValue_Step(
      String description,
      String imageUrl) {
    if (description == null) {
      throw new NullPointerException("Null description");
    }
    this.description = description;
    if (imageUrl == null) {
      throw new NullPointerException("Null imageUrl");
    }
    this.imageUrl = imageUrl;
  }

  @Override
  public String description() {
    return description;
  }

  @Override
  public String imageUrl() {
    return imageUrl;
  }

  @Override
  public String toString() {
    return "Step{"
        + "description=" + description
        + ", imageUrl=" + imageUrl
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Step) {
      Step that = (Step) o;
      return (this.description.equals(that.description()))
          && (this.imageUrl.equals(that.imageUrl()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= description.hashCode();
    h *= 1000003;
    h ^= imageUrl.hashCode();
    return h;
  }
}
