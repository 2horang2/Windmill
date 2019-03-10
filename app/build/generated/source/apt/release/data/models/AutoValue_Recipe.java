package data.models;

import java.util.List;

@javax.annotation.Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Recipe extends Recipe {
  private final String title;
  private final String description;
  private final String imageUrl;
  private final List<Step> steps;
  private final String updatedAt;
  private final User user;

  AutoValue_Recipe(
      String title,
      String description,
      String imageUrl,
      List<Step> steps,
      String updatedAt,
      User user) {
    if (title == null) {
      throw new NullPointerException("Null title");
    }
    this.title = title;
    if (description == null) {
      throw new NullPointerException("Null description");
    }
    this.description = description;
    if (imageUrl == null) {
      throw new NullPointerException("Null imageUrl");
    }
    this.imageUrl = imageUrl;
    if (steps == null) {
      throw new NullPointerException("Null steps");
    }
    this.steps = steps;
    if (updatedAt == null) {
      throw new NullPointerException("Null updatedAt");
    }
    this.updatedAt = updatedAt;
    if (user == null) {
      throw new NullPointerException("Null user");
    }
    this.user = user;
  }

  @Override
  public String title() {
    return title;
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
  public List<Step> steps() {
    return steps;
  }

  @Override
  public String updatedAt() {
    return updatedAt;
  }

  @Override
  public User user() {
    return user;
  }

  @Override
  public String toString() {
    return "Recipe{"
        + "title=" + title
        + ", description=" + description
        + ", imageUrl=" + imageUrl
        + ", steps=" + steps
        + ", updatedAt=" + updatedAt
        + ", user=" + user
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Recipe) {
      Recipe that = (Recipe) o;
      return (this.title.equals(that.title()))
          && (this.description.equals(that.description()))
          && (this.imageUrl.equals(that.imageUrl()))
          && (this.steps.equals(that.steps()))
          && (this.updatedAt.equals(that.updatedAt()))
          && (this.user.equals(that.user()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= title.hashCode();
    h *= 1000003;
    h ^= description.hashCode();
    h *= 1000003;
    h ^= imageUrl.hashCode();
    h *= 1000003;
    h ^= steps.hashCode();
    h *= 1000003;
    h ^= updatedAt.hashCode();
    h *= 1000003;
    h ^= user.hashCode();
    return h;
  }
}
