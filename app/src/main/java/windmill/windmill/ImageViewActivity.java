package windmill.windmill;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);


        String name = getIntent().getStringExtra("name");
        String img = getIntent().getStringExtra("img");

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setHomeButtonEnabled(true);
            ac.setTitle(name);
        }


        ImageView aa = (ImageView)findViewById(R.id.img);
        ImageLoader.getInstance().displayImage(img, aa);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
