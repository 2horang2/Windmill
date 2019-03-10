package windmill.windmill;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.skp.Tmap.TMapView;
public class Tmap_exp extends Activity {


    private RelativeLayout mMainRelativeLayout =null;




    double la,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap_exp);

        la = getIntent().getDoubleExtra("la",0);
        lng = getIntent().getDoubleExtra("lng", 0);


        ActionBar ac = getActionBar();
        if (ac != null) {
            ac.hide();
        }
        mMainRelativeLayout =(RelativeLayout)findViewById(R.id.mainRelativeLayout);

        TMapView mMapView = new TMapView(this);        // TmapView생성
        mMapView.setTrafficInfo(true);
        mMapView.setCenterPoint(lng,la);
        mMainRelativeLayout.addView(mMapView);
        mMapView.setSKPMapApiKey("c60dbca4-92ff-3e5d-a293-6c382fce05aa");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tmap_exp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
