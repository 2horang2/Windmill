// Generated code from Butter Knife. Do not modify!
package components;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ReviewDetailView$$ViewBinder<T extends components.ReviewDetailView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689661, "field 'review_image'");
    target.review_image = finder.castView(view, 2131689661, "field 'review_image'");
    view = finder.findRequiredView(source, 2131689654, "field 'user_image'");
    target.user_image = finder.castView(view, 2131689654, "field 'user_image'");
    view = finder.findRequiredView(source, 2131690045, "field 'review_title'");
    target.review_title = finder.castView(view, 2131690045, "field 'review_title'");
    view = finder.findRequiredView(source, 2131690044, "field 'review_user'");
    target.review_user = finder.castView(view, 2131690044, "field 'review_user'");
    view = finder.findRequiredView(source, 2131690046, "field 'review_date'");
    target.review_date = finder.castView(view, 2131690046, "field 'review_date'");
    view = finder.findRequiredView(source, 2131690048, "field 'review_text'");
    target.review_text = finder.castView(view, 2131690048, "field 'review_text'");
    view = finder.findRequiredView(source, 2131690050, "field 'review_reply_et'");
    target.review_reply_et = finder.castView(view, 2131690050, "field 'review_reply_et'");
    view = finder.findRequiredView(source, 2131690051, "field 'review_reply_btn'");
    target.review_reply_btn = finder.castView(view, 2131690051, "field 'review_reply_btn'");
    view = finder.findRequiredView(source, 2131689663, "field 'rr_up_del'");
    target.rr_up_del = finder.castView(view, 2131689663, "field 'rr_up_del'");
    view = finder.findRequiredView(source, 2131689664, "field 'update'");
    target.update = finder.castView(view, 2131689664, "field 'update'");
    view = finder.findRequiredView(source, 2131689665, "field 'delete'");
    target.delete = finder.castView(view, 2131689665, "field 'delete'");
  }

  @Override public void unbind(T target) {
    target.review_image = null;
    target.user_image = null;
    target.review_title = null;
    target.review_user = null;
    target.review_date = null;
    target.review_text = null;
    target.review_reply_et = null;
    target.review_reply_btn = null;
    target.rr_up_del = null;
    target.update = null;
    target.delete = null;
  }
}
