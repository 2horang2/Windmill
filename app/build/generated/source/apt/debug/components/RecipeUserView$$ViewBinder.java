// Generated code from Butter Knife. Do not modify!
package components;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RecipeUserView$$ViewBinder<T extends components.RecipeUserView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689660, "field 'userImageView'");
    target.userImageView = finder.castView(view, 2131689660, "field 'userImageView'");
    view = finder.findRequiredView(source, 2131689957, "field 'userNameTextView'");
    target.userNameTextView = finder.castView(view, 2131689957, "field 'userNameTextView'");
  }

  @Override public void unbind(T target) {
    target.userImageView = null;
    target.userNameTextView = null;
  }
}
