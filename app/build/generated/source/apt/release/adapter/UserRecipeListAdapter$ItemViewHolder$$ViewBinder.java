// Generated code from Butter Knife. Do not modify!
package adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserRecipeListAdapter$ItemViewHolder$$ViewBinder<T extends adapter.UserRecipeListAdapter.ItemViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689935, "field 'recipeImageView'");
    target.recipeImageView = finder.castView(view, 2131689935, "field 'recipeImageView'");
    view = finder.findRequiredView(source, 2131689936, "field 'MountainName'");
    target.MountainName = finder.castView(view, 2131689936, "field 'MountainName'");
    view = finder.findRequiredView(source, 2131689937, "field 'MountainState'");
    target.MountainState = finder.castView(view, 2131689937, "field 'MountainState'");
    view = finder.findRequiredView(source, 2131689939, "field 'MountainTitle'");
    target.MountainTitle = finder.castView(view, 2131689939, "field 'MountainTitle'");
  }

  @Override public void unbind(T target) {
    target.recipeImageView = null;
    target.MountainName = null;
    target.MountainState = null;
    target.MountainTitle = null;
  }
}
