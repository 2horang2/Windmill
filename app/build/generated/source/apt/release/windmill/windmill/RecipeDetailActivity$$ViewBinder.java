// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RecipeDetailActivity$$ViewBinder<T extends windmill.windmill.RecipeDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689637, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131689637, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131689765, "field 'recipeDetailView'");
    target.recipeDetailView = finder.castView(view, 2131689765, "field 'recipeDetailView'");
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
    target.recipeDetailView = null;
  }
}
