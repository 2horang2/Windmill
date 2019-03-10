package windmill.windmill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import components.AndroidApplication;
import components.ApplicationComponent;
import components.modules.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
