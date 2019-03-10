// Generated code from Butter Knife. Do not modify!
package ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingsActivity$$ViewBinder<T extends ui.activity.SettingsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689771, "field 'setting_logout'");
    target.setting_logout = finder.castView(view, 2131689771, "field 'setting_logout'");
    view = finder.findRequiredView(source, 2131689772, "field 'setting_update'");
    target.setting_update = finder.castView(view, 2131689772, "field 'setting_update'");
    view = finder.findRequiredView(source, 2131689768, "field 'setting_notify'");
    target.setting_notify = finder.castView(view, 2131689768, "field 'setting_notify'");
    view = finder.findRequiredView(source, 2131689769, "field 'setting_faq'");
    target.setting_faq = finder.castView(view, 2131689769, "field 'setting_faq'");
    view = finder.findRequiredView(source, 2131689776, "field 'setting_withdraw'");
    target.setting_withdraw = finder.castView(view, 2131689776, "field 'setting_withdraw'");
    view = finder.findRequiredView(source, 2131689773, "field 'setting_pw'");
    target.setting_pw = finder.castView(view, 2131689773, "field 'setting_pw'");
    view = finder.findRequiredView(source, 2131689775, "field 'setting_setting'");
    target.setting_setting = finder.castView(view, 2131689775, "field 'setting_setting'");
  }

  @Override public void unbind(T target) {
    target.setting_logout = null;
    target.setting_update = null;
    target.setting_notify = null;
    target.setting_faq = null;
    target.setting_withdraw = null;
    target.setting_pw = null;
    target.setting_setting = null;
  }
}
