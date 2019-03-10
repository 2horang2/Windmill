// Generated code from Butter Knife. Do not modify!
package adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RecipeListAdapter$ViewHolder$$ViewBinder<T extends adapter.RecipeListAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689941, "field 'recipeImageView'");
    target.recipeImageView = finder.castView(view, 2131689941, "field 'recipeImageView'");
    view = finder.findRequiredView(source, 2131689660, "field 'userImageView'");
    target.userImageView = finder.castView(view, 2131689660, "field 'userImageView'");
    view = finder.findRequiredView(source, 2131689957, "field 'userNameTextView'");
    target.userNameTextView = finder.castView(view, 2131689957, "field 'userNameTextView'");
    view = finder.findRequiredView(source, 2131689962, "field 'titleTextView'");
    target.titleTextView = finder.castView(view, 2131689962, "field 'titleTextView'");
    view = finder.findRequiredView(source, 2131689959, "field 'like'");
    target.like = finder.castView(view, 2131689959, "field 'like'");
    view = finder.findRequiredView(source, 2131689961, "field 'hate'");
    target.hate = finder.castView(view, 2131689961, "field 'hate'");
  }

  @Override public void unbind(T target) {
    target.recipeImageView = null;
    target.userImageView = null;
    target.userNameTextView = null;
    target.titleTextView = null;
    target.like = null;
    target.hate = null;
  }
}