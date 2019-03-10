package components;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import components.modules.ApplicationModule;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        initializeInjector();
        initializeStetho();
    }

    public void initializeInjector() {
      //  this.applicationComponent = DaggerApplicationComponent.builder()
     //        .applicationModule(new ApplicationModule(this))
        //        .build();
    }

    private void initializeStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
