package components;

import android.app.Activity;
import android.app.Application;
import components.modules.ActivityModule;
import components.modules.ActivityModule_ActivityFactory;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerActivityComponent implements ActivityComponent {
  private Provider<Activity> activityProvider;

  private DaggerActivityComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.activityProvider = ScopedProvider.create(ActivityModule_ActivityFactory.create(builder.activityModule));
  }

  @Override
  public Activity activity() {  
    return activityProvider.get();
  }

  public static final class Builder {
    private ActivityModule activityModule;
    private Application application;
  
    private Builder() {  
    }
  
    public ActivityComponent build() {  
      if (activityModule == null) {
        throw new IllegalStateException("activityModule must be set");
      }
      if (application == null) {
        this.application = new Application();
      }
      return new DaggerActivityComponent(this);
    }
  
    public Builder activityModule(ActivityModule activityModule) {  
      if (activityModule == null) {
        throw new NullPointerException("activityModule");
      }
      this.activityModule = activityModule;
      return this;
    }
  
    public Builder application(Application application) {  
      if (application == null) {
        throw new NullPointerException("application");
      }
      this.application = application;
      return this;
    }
  }
}

