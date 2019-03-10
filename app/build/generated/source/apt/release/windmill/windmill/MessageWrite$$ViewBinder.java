// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MessageWrite$$ViewBinder<T extends windmill.windmill.MessageWrite> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689724, "field 'title'");
    target.title = finder.castView(view, 2131689724, "field 'title'");
    view = finder.findRequiredView(source, 2131689725, "field 'text'");
    target.text = finder.castView(view, 2131689725, "field 'text'");
    view = finder.findRequiredView(source, 2131689720, "field 'ok'");
    target.ok = finder.castView(view, 2131689720, "field 'ok'");
  }

  @Override public void unbind(T target) {
    target.title = null;
    target.text = null;
    target.ok = null;
  }
}
