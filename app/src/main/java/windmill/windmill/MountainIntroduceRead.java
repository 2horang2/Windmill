package windmill.windmill;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MountainIntroduceRead extends AppCompatActivity {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address)
    TextView address;



    @Bind(R.id.ph)
    TextView ph;
    @Bind(R.id.url)
    TextView url;

    @Bind(R.id.text)
    TextView text;

    @Bind(R.id.type)
    TextView type;

    @Bind(R.id.phh)
    RelativeLayout phh;
    @Bind(R.id.urll)
    RelativeLayout urll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_introduce_read);

        ButterKnife.bind(this);

        name.setText(getIntent().getStringExtra("name"));
        address.setText(getIntent().getStringExtra("address"));

        if(getIntent().getStringExtra("ph").equals("null"))
            phh.setVisibility(View.GONE);
        else
            ph.setText("전화번호 :  "+getIntent().getStringExtra("ph"));

        if(getIntent().getStringExtra("url").equals("null"))
            urll.setVisibility(View.GONE);
        else
            url.setText("홈페이지 :  "+getIntent().getStringExtra("url"));


        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //        Uri uri = Uri.parse(url.getText().toString());

         //       Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          //      startActivity(intent);
            }
        });
        if(getIntent().getStringExtra("text").equals("null"))
            text.setVisibility(View.GONE);
        else
            text.setText(getIntent().getStringExtra("text"));

        type.setText(getIntent().getStringExtra("type"));


        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra("m_name"));


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_mountain_introduce_read, menu);
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
