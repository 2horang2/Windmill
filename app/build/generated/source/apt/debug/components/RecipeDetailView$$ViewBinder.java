// Generated code from Butter Knife. Do not modify!
package components;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RecipeDetailView$$ViewBinder<T extends components.RecipeDetailView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689941, "field 'recipeImageView'");
    target.recipeImageView = finder.castView(view, 2131689941, "field 'recipeImageView'");
    view = finder.findRequiredView(source, 2131690034, "field 'titleWrapperView'");
    target.titleWrapperView = view;
    view = finder.findRequiredView(source, 2131689962, "field 'titleTextView'");
    target.titleTextView = finder.castView(view, 2131689962, "field 'titleTextView'");
    view = finder.findRequiredView(source, 2131690043, "field 'updatedAtTextView'");
    target.updatedAtTextView = finder.castView(view, 2131690043, "field 'updatedAtTextView'");
    view = finder.findRequiredView(source, 2131690044, "field 'descriptionTextView'");
    target.descriptionTextView = finder.castView(view, 2131690044, "field 'descriptionTextView'");
    view = finder.findRequiredView(source, 2131689959, "field 'like'");
    target.like = finder.castView(view, 2131689959, "field 'like'");
    view = finder.findRequiredView(source, 2131689961, "field 'hate'");
    target.hate = finder.castView(view, 2131689961, "field 'hate'");
    view = finder.findRequiredView(source, 2131689958, "field 'tmp1'");
    target.tmp1 = finder.castView(view, 2131689958, "field 'tmp1'");
    view = finder.findRequiredView(source, 2131689960, "field 'tmp2'");
    target.tmp2 = finder.castView(view, 2131689960, "field 'tmp2'");
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
