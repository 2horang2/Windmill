// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class JoinActivity$$ViewBinder<T extends windmill.windmill.JoinActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689784, "field 'profile_update'");
    target.profile_update = finder.castView(view, 2131689784, "field 'profile_update'");
    view = finder.findRequiredView(source, 2131689783, "field 'profile_img'");
    target.profile_img = finder.castView(view, 2131689783, "field 'profile_img'");
    view = finder.findRequiredView(source, 2131689887, "field 'join_id'");
    target.join_id = finder.castView(view, 2131689887, "field 'join_id'");
    view = finder.findRequiredView(source, 2131689888, "field 'join_email'");
    target.join_email = finder.castView(view, 2131689888, "field 'join_email'");
    view = finder.findRequiredView(source, 2131689873, "field 'join_pw'");
    target.join_pw = finder.castView(view, 2131689873, "field 'join_pw'");
    view = finder.findRequiredView(source, 2131689889, "field 'join_pw2'");
    target.join_pw2 = finder.castView(view, 2131689889, "field 'join_pw2'");
    view = finder.findRequiredView(source, 2131689890, "field 'join_btn'");
    target.join_btn = finder.castView(view, 2131689890, "field 'join_btn'");
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
