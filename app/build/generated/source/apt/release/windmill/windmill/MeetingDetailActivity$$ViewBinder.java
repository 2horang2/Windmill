// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MeetingDetailActivity$$ViewBinder<T extends windmill.windmill.MeetingDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689680, "field 'meetingDetailView'");
    target.meetingDetailView = finder.castView(view, 2131689680, "field 'meetingDetailView'");
  }

  @Override public void unbind(T target) {
    target.meetingDetailView = null;
  }
}
