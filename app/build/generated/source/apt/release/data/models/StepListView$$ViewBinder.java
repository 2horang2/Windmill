// Generated code from Butter Knife. Do not modify!
package data.models;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class StepListView$$ViewBinder<T extends data.models.StepListView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131690054, "field 'stepsContainer'");
    target.stepsContainer = finder.castView(view, 2131690054, "field 'stepsContainer'");
  }

  @Override public void unbind(T target) {
    target.stepsContainer = null;
  }
}
