// Generated code from Butter Knife. Do not modify!
package components;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RecipeDetailView$$ViewBinder<T extends components.RecipeDetailView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689935, "field 'recipeImageView'");
    target.recipeImageView = finder.castView(view, 2131689935, "field 'recipeImageView'");
    view = finder.findRequiredView(source, 2131690028, "field 'titleWrapperView'");
    target.titleWrapperView = view;
    view = finder.findRequiredView(source, 2131689956, "field 'titleTextView'");
    target.titleTextView = finder.castView(view, 2131689956, "field 'titleTextView'");
    view = finder.findRequiredView(source, 2131690037, "field 'updatedAtTextView'");
    target.updatedAtTextView = finder.castView(view, 2131690037, "field 'updatedAtTextView'");
    view = finder.findRequiredView(source, 2131690038, "field 'descriptionTextView'");
    target.descriptionTextView = finder.castView(view, 2131690038, "field 'descriptionTextView'");
    view = finder.findRequiredView(source, 2131689953, "field 'like'");
    target.like = finder.castView(view, 2131689953, "field 'like'");
    view = finder.findRequiredView(source, 2131689955, "field 'hate'");
    target.hate = finder.castView(view, 2131689955, "field 'hate'");
    view = finder.findRequiredView(source, 2131689952, "field 'tmp1'");
    target.tmp1 = finder.castView(view, 2131689952, "field 'tmp1'");
    view = finder.findRequiredView(source, 2131689954, "field 'tmp2'");
    target.tmp2 = finder.castView(view, 2131689954, "field 'tmp2'");
  }

  @Override public void unbind(T target) {
    target.recipeImageView = null;
    target.titleWrapperView = null;
    target.titleTextView = null;
    target.updatedAtTextView = null;
    target.descriptionTextView = null;
    target.like = null;
    target.hate = null;
    target.tmp1 = null;
    target.tmp2 = null;
  }
}
