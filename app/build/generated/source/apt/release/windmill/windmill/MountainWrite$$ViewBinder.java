// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MountainWrite$$ViewBinder<T extends windmill.windmill.MountainWrite> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689758, "field 'mountain_write_img'");
    target.mountain_write_img = finder.castView(view, 2131689758, "field 'mountain_write_img'");
    view = finder.findRequiredView(source, 2131689759, "field 'mountain_write_cate'");
    target.mountain_write_cate = finder.castView(view, 2131689759, "field 'mountain_write_cate'");
    view = finder.findRequiredView(source, 2131689761, "field 'mountain_write_title'");
    target.mountain_write_title = finder.castView(view, 2131689761, "field 'mountain_write_title'");
    view = finder.findRequiredView(source, 2131689757, "field 'mountain_write_text'");
    target.mountain_write_text = finder.castView(view, 2131689757, "field 'mountain_write_text'");
    view = finder.findRequiredView(source, 2131689762, "field 'mountain_write_add'");
    target.mountain_write_add = finder.castView(view, 2131689762, "field 'mountain_write_add'");
    view = finder.findRequiredView(source, 2131689720, "field 'ok'");
    target.ok = finder.castView(view, 2131689720, "field 'ok'");
    view = finder.findRequiredView(source, 2131689721, "field 'cancle'");
    target.cancle = finder.castView(view, 2131689721, "field 'cancle'");
  }

  @Override public void unbind(T target) {
    target.mountain_write_img = null;
    target.mountain_write_cate = null;
    target.mountain_write_title = null;
    target.mountain_write_text = null;
    target.mountain_write_add = null;
    target.ok = null;
    target.cancle = null;
  }
}
