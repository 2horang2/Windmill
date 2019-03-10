// Generated code from Butter Knife. Do not modify!
package ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingFragment$$ViewBinder<T extends ui.fragment.SettingFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689850, "field 'st_profile'");
    target.st_profile = finder.castView(view, 2131689850, "field 'st_profile'");
    view = finder.findRequiredView(source, 2131689852, "field 'st_gallery'");
    target.st_gallery = finder.castView(view, 2131689852, "field 'st_gallery'");
    view = finder.findRequiredView(source, 2131689853, "field 'st_like'");
    target.st_like = finder.castView(view, 2131689853, "field 'st_like'");
    view = finder.findRequiredView(source, 2131689854, "field 'st_setting'");
    target.st_setting = finder.castView(view, 2131689854, "field 'st_setting'");
    view = finder.findRequiredView(source, 2131689855, "field 'st_notify'");
    target.st_notify = finder.castView(view, 2131689855, "field 'st_notify'");
    view = finder.findRequiredView(source, 2131689856, "field 'st_faq'");
    target.st_faq = finder.castView(view, 2131689856, "field 'st_faq'");
    view = finder.findRequiredView(source, 2131689851, "field 'fi_tv'");
    target.fi_tv = finder.castView(view, 2131689851, "field 'fi_tv'");
  }

  @Override public void unbind(T target) {
    target.st_profile = null;
    target.st_gallery = null;
    target.st_like = null;
    target.st_setting = null;
    target.st_notify = null;
    target.st_faq = null;
    target.fi_tv = null;
  }
}
