// Generated code from Butter Knife. Do not modify!
package windmill.windmill;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MountainIntroduceWrite$$ViewBinder<T extends windmill.windmill.MountainIntroduceWrite> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689755, "field 'mountain_introduce_cate'");
    target.mountain_introduce_cate = finder.castView(view, 2131689755, "field 'mountain_introduce_cate'");
    view = finder.findRequiredView(source, 2131689757, "field 'mountain_introduce_name'");
    target.mountain_introduce_name = finder.castView(view, 2131689757, "field 'mountain_introduce_name'");
    view = finder.findRequiredView(source, 2131689758, "field 'mountain_introduce_add'");
    target.mountain_introduce_add = finder.castView(view, 2131689758, "field 'mountain_introduce_add'");
    view = finder.findRequiredView(source, 2131689759, "field 'mountain_introduce_text'");
    target.mountain_introduce_text = finder.castView(view, 2131689759, "field 'mountain_introduce_text'");
    view = finder.findRequiredView(source, 2131689760, "field 'mountain_introduce_ph'");
    target.mountain_introduce_ph = finder.castView(view, 2131689760, "field 'mountain_introduce_ph'");
    view = finder.findRequiredView(source, 2131689761, "field 'mountain_introduce_url'");
    target.mountain_introduce_url = finder.castView(view, 2131689761, "field 'mountain_introduce_url'");
    view = finder.findRequiredView(source, 2131689726, "field 'ok'");
    target.ok = finder.castView(view, 2131689726, "field 'ok'");
    view = finder.findRequiredView(source, 2131689727, "field 'cancle'");
    target.cancle = finder.castView(view, 2131689727, "field 'cancle'");
  }

  @Override public void unbind(T target) {
    target.mountain_introduce_cate = null;
    target.mountain_introduce_name = null;
    target.mountain_introduce_add = null;
    target.mountain_introduce_text = null;
    target.mountain_introduce_ph = null;
    target.mountain_introduce_url = null;
    target.ok = null;
    target.cancle = null;
  }
}
