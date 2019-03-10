package components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import components.modules.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
}
