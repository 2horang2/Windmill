package data.models;


@javax.annotation.Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_User extends User {
  private final String name;
  private final String imageUrl;

  AutoValue_User(
      String name,
      String imageUrl) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    if (imageUrl == null) {
      throw new NullPointerException("Null imageUrl");
    }
    this.imageUrl = imageUrl;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String imageUrl() {
    return imageUrl;
  }

  @Override
  public String toString() {
    return "User{"
        + "name=" + name
        + ", imageUrl=" + imageUrl
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof User) {
      User that = (User) o;
      return (this.name.equals(that.name()))
          && (this.imageUrl.equals(that.imageUrl()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= name.hashCode();
    h *= 1000003;
    h ^= imageUrl.hashCode();
    return h;
  }
}
