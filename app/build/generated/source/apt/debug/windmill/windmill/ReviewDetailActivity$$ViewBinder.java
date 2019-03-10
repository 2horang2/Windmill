// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ReviewDetailActivity$$ViewBinder<T extends windmill.windmill.ReviewDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689772, "field 'reviewDetailView'");
    target.reviewDetailView = finder.castView(view, 2131689772, "field 'reviewDetailView'");
  }

  @Override public void unbind(T target) {
    target.reviewDetailView = null;
  }
}
