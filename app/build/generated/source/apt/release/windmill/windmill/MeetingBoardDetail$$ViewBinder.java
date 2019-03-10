// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MeetingBoardDetail$$ViewBinder<T extends windmill.windmill.MeetingBoardDetail> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689654, "field 'user_image'");
    target.user_image = finder.castView(view, 2131689654, "field 'user_image'");
    view = finder.findRequiredView(source, 2131689661, "field 'review_image'");
    target.review_image = finder.castView(view, 2131689661, "field 'review_image'");
    view = finder.findRequiredView(source, 2131689656, "field 'cate'");
    target.cate = finder.castView(view, 2131689656, "field 'cate'");
    view = finder.findRequiredView(source, 2131689658, "field 'title'");
    target.title = finder.castView(view, 2131689658, "field 'title'");
    view = finder.findRequiredView(source, 2131689655, "field 'writer'");
    target.writer = finder.castView(view, 2131689655, "field 'writer'");
    view = finder.findRequiredView(source, 2131689659, "field 'date'");
    target.date = finder.castView(view, 2131689659, "field 'date'");
    view = finder.findRequiredView(source, 2131689662, "field 'text'");
    target.text = finder.castView(view, 2131689662, "field 'text'");
    view = finder.findRequiredView(source, 2131689672, "field 'wirting_reply'");
    target.wirting_reply = finder.castView(view, 2131689672, "field 'wirting_reply'");
    view = finder.findRequiredView(source, 2131689673, "field 'wirting_reply_btn'");
    target.wirting_reply_btn = finder.castView(view, 2131689673, "field 'wirting_reply_btn'");
    view = finder.findRequiredView(source, 2131689669, "field 'aa'");
    target.aa = finder.castView(view, 2131689669, "field 'aa'");
    view = finder.findRequiredView(source, 2131689663, "field 'rr_up_del'");
    target.rr_up_del = finder.castView(view, 2131689663, "field 'rr_up_del'");
    view = finder.findRequiredView(source, 2131689664, "field 'update'");
    target.update = finder.castView(view, 2131689664, "field 'update'");
    view = finder.findRequiredView(source, 2131689665, "field 'delete'");
    target.delete = finder.castView(view, 2131689665, "field 'delete'");
    view = finder.findRequiredView(source, 2131689667, "field 'reply_fold'");
    target.reply_fold = finder.castView(view, 2131689667, "field 'reply_fold'");
  }

  @Override public void unbind(T target) {
    target.user_image = null;
    target.review_image = null;
    target.cate = null;
    target.title = null;
    target.writer = null;
    target.date = null;
    target.text = null;
    target.wirting_reply = null;
    target.wirting_reply_btn = null;
    target.aa = null;
    target.rr_up_del = null;
    target.update = null;
    target.delete = null;
    target.reply_fold = null;
  }
}
