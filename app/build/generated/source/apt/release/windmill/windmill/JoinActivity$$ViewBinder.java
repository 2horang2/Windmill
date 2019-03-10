// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class JoinActivity$$ViewBinder<T extends windmill.windmill.JoinActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689778, "field 'profile_update'");
    target.profile_update = finder.castView(view, 2131689778, "field 'profile_update'");
    view = finder.findRequiredView(source, 2131689777, "field 'profile_img'");
    target.profile_img = finder.castView(view, 2131689777, "field 'profile_img'");
    view = finder.findRequiredView(source, 2131689881, "field 'join_id'");
    target.join_id = finder.castView(view, 2131689881, "field 'join_id'");
    view = finder.findRequiredView(source, 2131689882, "field 'join_email'");
    target.join_email = finder.castView(view, 2131689882, "field 'join_email'");
    view = finder.findRequiredView(source, 2131689867, "field 'join_pw'");
    target.join_pw = finder.castView(view, 2131689867, "field 'join_pw'");
    view = finder.findRequiredView(source, 2131689883, "field 'join_pw2'");
    target.join_pw2 = finder.castView(view, 2131689883, "field 'join_pw2'");
    view = finder.findRequiredView(source, 2131689884, "field 'join_btn'");
    target.join_btn = finder.castView(view, 2131689884, "field 'join_btn'");
  }

  @Override public void unbind(T target) {
    target.profile_update = null;
    target.profile_img = null;
    target.join_id = null;
    target.join_email = null;
    target.join_pw = null;
    target.join_pw2 = null;
    target.join_btn = null;
  }
}
