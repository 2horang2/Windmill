// Generated code from Butter Knife. Do not modify!
package ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainFragment5$$ViewBinder<T extends ui.fragment.MainFragment5> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689852, "field 'userRecipeListView'");
    target.userRecipeListView = finder.castView(view, 2131689852, "field 'userRecipeListView'");
  }

  @Override public void unbind(T target) {
    target.userRecipeListView = null;
  }
}
