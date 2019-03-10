// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MeetingUpdate$$ViewBinder<T extends windmill.windmill.MeetingUpdate> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689712, "field 'meeting_write_img'");
    target.meeting_write_img = finder.castView(view, 2131689712, "field 'meeting_write_img'");
    view = finder.findRequiredView(source, 2131689717, "field 'meeting_write_title'");
    target.meeting_write_title = finder.castView(view, 2131689717, "field 'meeting_write_title'");
    view = finder.findRequiredView(source, 2131689715, "field 'meeting_write_mountain'");
    target.meeting_write_mountain = finder.castView(view, 2131689715, "field 'meeting_write_mountain'");
    view = finder.findRequiredView(source, 2131689716, "field 'meeting_write_sub'");
    target.meeting_write_sub = finder.castView(view, 2131689716, "field 'meeting_write_sub'");
    view = finder.findRequiredView(source, 2131689718, "field 'meeting_write_text'");
    target.meeting_write_text = finder.castView(view, 2131689718, "field 'meeting_write_text'");
    view = finder.findRequiredView(source, 2131689714, "field 'meeting_write_category'");
    target.meeting_write_category = finder.castView(view, 2131689714, "field 'meeting_write_category'");
    view = finder.findRequiredView(source, 2131689719, "field 'meeting_write_member'");
    target.meeting_write_member = finder.castView(view, 2131689719, "field 'meeting_write_member'");
    view = finder.findRequiredView(source, 2131689720, "field 'ok'");
    target.ok = finder.castView(view, 2131689720, "field 'ok'");
    view = finder.findRequiredView(source, 2131689721, "field 'cancle'");
    target.cancle = finder.castView(view, 2131689721, "field 'cancle'");
  }

  @Override public void unbind(T target) {
    target.meeting_write_img = null;
    target.meeting_write_title = null;
    target.meeting_write_mountain = null;
    target.meeting_write_sub = null;
    target.meeting_write_text = null;
    target.meeting_write_category = null;
    target.meeting_write_member = null;
    target.ok = null;
    target.cancle = null;
  }
}
