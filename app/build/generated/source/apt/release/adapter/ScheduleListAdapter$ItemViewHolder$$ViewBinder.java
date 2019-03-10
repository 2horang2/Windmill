// Generated code from Butter Knife. Do not modify!
package adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ScheduleListAdapter$ItemViewHolder$$ViewBinder<T extends adapter.ScheduleListAdapter.ItemViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689640, "field 'img'");
    target.img = finder.castView(view, 2131689640, "field 'img'");
    view = finder.findRequiredView(source, 2131689873, "field 'mountain'");
    target.mountain = finder.castView(view, 2131689873, "field 'mountain'");
    view = finder.findRequiredView(source, 2131689574, "field 'title'");
    target.title = finder.castView(view, 2131689574, "field 'title'");
    view = finder.findRequiredView(source, 2131689705, "field 'date'");
    target.date = finder.castView(view, 2131689705, "field 'date'");
  }

  @Override public void unbind(T target) {
    target.img = null;
    target.mountain = null;
    target.title = null;
    target.date = null;
  }
}
