package windmill.windmill;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.PREF;

public class MessageRead extends AppCompatActivity {

    @Bind(R.id.person)
    TextView tv_person;
    @Bind(R.id.date)
    TextView tv_date;
    @Bind(R.id.text)
    TextView tv_text;
    @Bind(R.id.img)
    ImageView iv_img;

    String person, img, text, date, type=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_read);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        person = getIntent().getStringExtra("person");
        img = getIntent().getStringExtra("img");
        text = getIntent().getStringExtra("text");
        date = getIntent().getStringExtra("date");

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(type);
        }

        if (type.equals("받은쪽지함")) {
            tv_person.setText("보낸 사람 :" + person);
            tv_date.setText("받은 날짜 :" + date);
            tv_text.setText(text);

            ImageLoader.getInstance().displayImage(img, iv_img);
            invalidateOptionsMenu();
        }else{
            tv_person.setText("받은 사람 :" + person);
            tv_date.setText("보낸 날짜 :" + date);
            tv_text.setText(text);

            ImageLoader.getInstance().displayImage(img, iv_img);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(type.equals("받은쪽지함")) {
            getMenuInflater().inflate(R.menu.menu_message_read, menu);
        }

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                Bundle extras = new Bundle();
                extras.putString("to", person);
                Intent intent = new Intent(MessageRead.this, MessageWrite.class);
                intent.putExtras(extras);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
