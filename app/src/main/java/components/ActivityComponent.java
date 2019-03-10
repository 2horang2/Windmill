package components;

import android.app.Activity;
import android.app.Application;

import dagger.Component;
import components.ActivityScope;
import components.modules.ActivityModule;

@ActivityScope
@Component(dependencies = Application.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
