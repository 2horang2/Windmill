package windmill.windmill;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import components.MeetingDetailView;
import data.models.Meeting;
import data.models.PREF;
import data.models.UPDATE;


public class MeetingDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MEETING = "meeting";
    public static final String EXTRA_IMAGE = "ItemDetailActivity:image";

    private Meeting meeting;

    public String lng, la;

    @Bind(R.id.meeting_detail)
    MeetingDetailView meetingDetailView;

    static Meeting m;

    public static void launch(Activity activity, Meeting meeting, ImageView transitionView, String url) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                transitionView,
                EXTRA_IMAGE);
        m = meeting;
        Intent intent = new Intent(activity, MeetingDetailActivity.class);
        intent.putExtra(EXTRA_MEETING, meeting.toJson());
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(m.title());


        }
        invalidateOptionsMenu();
        ImageView recipeImageView = (ImageView) meetingDetailView.findViewById(R.id.meeting_img);
        ViewCompat.setTransitionName(recipeImageView, EXTRA_IMAGE);

        meeting = Meeting.fromJson(getIntent().getStringExtra(EXTRA_MEETING), Meeting.class);

        setupView();
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View header = layoutInflater.inflate(R.layout.view_meeting_detail, null);
    }

    private void setupView() {
        meetingDetailView.setMeeting(meeting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(m!=null && PREF.id!=null) {
            if (PREF.id.equals(m.user()))
                getMenuInflater().inflate(R.menu.menu_meeting_detail, menu);
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
                ttt = new CateDialog(MeetingDetailActivity.this);
                ttt.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    CateDialog ttt;


    public class CateDialog extends Dialog {
        public CateDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_meeting_setting);

            Button btn1 = (Button) findViewById(R.id.s1);
            Button btn2 = (Button) findViewById(R.id.s2);
            Button btn3 = (Button) findViewById(R.id.s3);


            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Bundle extras = new Bundle();
                    extras.putString("id",meeting.idx());
                    extras.putString("cate","aaaaa");
                    Intent intent = new Intent(MeetingDetailActivity.this, MeetingSettingMember.class);
                    intent.putExtras(extras);
                    startActivity(intent);

                    ttt.dismiss();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Bundle extras = new Bundle();
                    extras.putString("idx", meeting.idx());
                    extras.putString("cate", meeting.category());
                    extras.putString("img", meeting.img());
                    extras.putString("title", meeting.title());
                    extras.putString("sub", meeting.sub());
                    extras.putString("mountain", meeting.mountain());
                    extras.putString("text", meeting.text());
                    extras.putString("date", meeting.date());
                    extras.putString("member", meeting.member());
                    Intent intent = new Intent(MeetingDetailActivity.this, MeetingUpdate.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                    ttt.dismiss();

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    d_d = new DeleteDialog(MeetingDetailActivity.this);
                    d_d.show();
                    ttt.dismiss();
                }
            });

        }
    }



    DeleteDialog d_d;


    public class DeleteDialog extends Dialog {
        public DeleteDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_check);

            Button btn1 = (Button) findViewById(R.id.o);
            Button btn2 = (Button) findViewById(R.id.x);


            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    new meeting_del().execute();
                    d_d.dismiss();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    d_d.dismiss();

                }
            });

        }
    }

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog loagindDialog;

    private class meeting_del extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(MeetingDetailActivity.this, "",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_delete.php");
                nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("user", meeting.user()));
                nameValuePairs.add(new BasicNameValuePair("idx", meeting.idx()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response = httpclient.execute(httppost);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            UPDATE.meeting_update=true;
            finish();
            Main2Activity.index=1;
            startActivity(new Intent(MeetingDetailActivity.this, Main2Activity.class));

        }
    }

}
