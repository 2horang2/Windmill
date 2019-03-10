// Generated code from Butter Knife. Do not modify!
package components;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class StepView$$ViewBinder<T extends components.StepView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131690058, "field 'stepDescriptionTextView'");
    target.stepDescriptionTextView = finder.castView(view, 2131690058, "field 'stepDescriptionTextView'");
    view = finder.findRequiredView(source, 2131690059, "field 'stepImageView'");
    target.stepImageView = finder.castView(view, 2131690059, "field 'stepImageView'");
  }

  @Override public void unbind(T target) {
    target.stepDescriptionTextView = null;
    target.stepImageView = null;
  }
}
