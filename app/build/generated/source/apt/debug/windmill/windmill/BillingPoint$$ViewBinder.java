// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BillingPoint$$ViewBinder<T extends windmill.windmill.BillingPoint> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689620, "field 'p1000'");
    target.p1000 = finder.castView(view, 2131689620, "field 'p1000'");
    view = finder.findRequiredView(source, 2131689623, "field 'p5000'");
    target.p5000 = finder.castView(view, 2131689623, "field 'p5000'");
    view = finder.findRequiredView(source, 2131689625, "field 'p10000'");
    target.p10000 = finder.castView(view, 2131689625, "field 'p10000'");
    view = finder.findRequiredView(source, 2131689628, "field 'p30000'");
    target.p30000 = finder.castView(view, 2131689628, "field 'p30000'");
    view = finder.findRequiredView(source, 2131689630, "field 'p50000'");
    target.p50000 = finder.castView(view, 2131689630, "field 'p50000'");
    view = finder.findRequiredView(source, 2131689633, "field 'p100000'");
    target.p100000 = finder.castView(view, 2131689633, "field 'p100000'");
    view = finder.findRequiredView(source, 2131689617, "field 'tt'");
    target.tt = finder.castView(view, 2131689617, "field 'tt'");
  }

  @Override public void unbind(T target) {
    target.p1000 = null;
    target.p5000 = null;
    target.p10000 = null;
    target.p30000 = null;
    target.p50000 = null;
    target.p100000 = null;
    target.tt = null;
  }
}
