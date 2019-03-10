// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MountainIntroduceRead$$ViewBinder<T extends windmill.windmill.MountainIntroduceRead> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689745, "field 'name'");
    target.name = finder.castView(view, 2131689745, "field 'name'");
    view = finder.findRequiredView(source, 2131689746, "field 'address'");
    target.address = finder.castView(view, 2131689746, "field 'address'");
    view = finder.findRequiredView(source, 2131689751, "field 'ph'");
    target.ph = finder.castView(view, 2131689751, "field 'ph'");
    view = finder.findRequiredView(source, 2131689753, "field 'url'");
    target.url = finder.castView(view, 2131689753, "field 'url'");
    view = finder.findRequiredView(source, 2131689715, "field 'text'");
    target.text = finder.castView(view, 2131689715, "field 'text'");
    view = finder.findRequiredView(source, 2131689744, "field 'type'");
    target.type = finder.castView(view, 2131689744, "field 'type'");
    view = finder.findRequiredView(source, 2131689750, "field 'phh'");
    target.phh = finder.castView(view, 2131689750, "field 'phh'");
    view = finder.findRequiredView(source, 2131689752, "field 'urll'");
    target.urll = finder.castView(view, 2131689752, "field 'urll'");
  }

  @Override public void unbind(T target) {
    target.name = null;
    target.address = null;
    target.ph = null;
    target.url = null;
    target.text = null;
    target.type = null;
    target.phh = null;
    target.urll = null;
  }
}
