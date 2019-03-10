// Generated code from Butter Knife. Do not modify!
package adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyMeetingAdapter$ViewHolder$$ViewBinder<T extends adapter.MyMeetingAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689941, "field 'recipeImageView'");
    target.recipeImageView = finder.castView(view, 2131689941, "field 'recipeImageView'");
    view = finder.findRequiredView(source, 2131689919, "field 'remove'");
    target.remove = finder.castView(view, 2131689919, "field 'remove'");
    view = finder.findRequiredView(source, 2131689945, "field 'meeting_title'");
    target.meeting_title = finder.castView(view, 2131689945, "field 'meeting_title'");
    view = finder.findRequiredView(source, 2131689942, "field 'meeting_mountain_name'");
    target.meeting_mountain_name = finder.castView(view, 2131689942, "field 'meeting_mountain_name'");
    view = finder.findRequiredView(source, 2131689943, "field 'meeting_mountain_state'");
    target.meeting_mountain_state = finder.castView(view, 2131689943, "field 'meeting_mountain_state'");
  }

  @Override public void unbind(T target) {
    target.recipeImageView = null;
    target.remove = null;
    target.meeting_title = null;
    target.meeting_mountain_name = null;
    target.meeting_mountain_state = null;
  }
}
