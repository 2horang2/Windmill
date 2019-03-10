// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MeetingBoardUpdate$$ViewBinder<T extends windmill.windmill.MeetingBoardUpdate> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689683, "field 'meeting_board_img'");
    target.meeting_board_img = finder.castView(view, 2131689683, "field 'meeting_board_img'");
    view = finder.findRequiredView(source, 2131689684, "field 'meeting_board_cate'");
    target.meeting_board_cate = finder.castView(view, 2131689684, "field 'meeting_board_cate'");
    view = finder.findRequiredView(source, 2131689680, "field 'title'");
    target.title = finder.castView(view, 2131689680, "field 'title'");
    view = finder.findRequiredView(source, 2131689681, "field 'text'");
    target.text = finder.castView(view, 2131689681, "field 'text'");
    view = finder.findRequiredView(source, 2131689685, "field 'btn'");
    target.btn = finder.castView(view, 2131689685, "field 'btn'");
  }

  @Override public void unbind(T target) {
    target.meeting_board_img = null;
    target.meeting_board_cate = null;
    target.title = null;
    target.text = null;
    target.btn = null;
  }
}
