// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MessageRead$$ViewBinder<T extends windmill.windmill.MessageRead> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689723, "field 'tv_person'");
    target.tv_person = finder.castView(view, 2131689723, "field 'tv_person'");
    view = finder.findRequiredView(source, 2131689705, "field 'tv_date'");
    target.tv_date = finder.castView(view, 2131689705, "field 'tv_date'");
    view = finder.findRequiredView(source, 2131689709, "field 'tv_text'");
    target.tv_text = finder.castView(view, 2131689709, "field 'tv_text'");
    view = finder.findRequiredView(source, 2131689640, "field 'iv_img'");
    target.iv_img = finder.castView(view, 2131689640, "field 'iv_img'");
  }

  @Override public void unbind(T target) {
    target.tv_person = null;
    target.tv_date = null;
    target.tv_text = null;
    target.iv_img = null;
  }
}
