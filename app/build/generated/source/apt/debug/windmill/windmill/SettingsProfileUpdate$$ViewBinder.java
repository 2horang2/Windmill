// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingsProfileUpdate$$ViewBinder<T extends windmill.windmill.SettingsProfileUpdate> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689727, "field 'x'");
    target.x = finder.castView(view, 2131689727, "field 'x'");
    view = finder.findRequiredView(source, 2131689726, "field 'o'");
    target.o = finder.castView(view, 2131689726, "field 'o'");
    view = finder.findRequiredView(source, 2131689787, "field 'profile_email'");
    target.profile_email = finder.castView(view, 2131689787, "field 'profile_email'");
    view = finder.findRequiredView(source, 2131689786, "field 'profile_id'");
    target.profile_id = finder.castView(view, 2131689786, "field 'profile_id'");
    view = finder.findRequiredView(source, 2131689790, "field 'profile_logout'");
    target.profile_logout = finder.castView(view, 2131689790, "field 'profile_logout'");
    view = finder.findRequiredView(source, 2131689789, "field 'profile_pw'");
    target.profile_pw = finder.castView(view, 2131689789, "field 'profile_pw'");
    view = finder.findRequiredView(source, 2131689791, "field 'profile_withdraw'");
    target.profile_withdraw = finder.castView(view, 2131689791, "field 'profile_withdraw'");
    view = finder.findRequiredView(source, 2131689784, "field 'profile_update'");
    target.profile_update = finder.castView(view, 2131689784, "field 'profile_update'");
    view = finder.findRequiredView(source, 2131689783, "field 'profile_img'");
    target.profile_img = finder.castView(view, 2131689783, "field 'profile_img'");
  }

  @Override public void unbind(T target) {
    target.x = null;
    target.o = null;
    target.profile_email = null;
    target.profile_id = null;
    target.profile_logout = null;
    target.profile_pw = null;
    target.profile_withdraw = null;
    target.profile_update = null;
    target.profile_img = null;
  }
}
