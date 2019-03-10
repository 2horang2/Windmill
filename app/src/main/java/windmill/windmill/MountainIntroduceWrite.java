package windmill.windmill;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.PREF;

public class MountainIntroduceWrite extends AppCompatActivity {


    @Bind(R.id.mountain_introduce_cate)
    EditText mountain_introduce_cate;
    @Bind(R.id.mountain_introduce_name)
    EditText mountain_introduce_name;

    @Bind(R.id.mountain_introduce_add)
    EditText mountain_introduce_add;
    @Bind(R.id.mountain_introduce_text)
    EditText mountain_introduce_text;

    @Bind(R.id.mountain_introduce_ph)
    EditText mountain_introduce_ph;
    @Bind(R.id.mountain_introduce_url)
    EditText mountain_introduce_url;

    @Bind(R.id.ok)
    Button ok;
    @Bind(R.id.cancle)
    Button cancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_introduce_write);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra("m_name"));


        }

        mountain_introduce_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ttt=new ShareMainDialog(MountainIntroduceWrite.this);
                ttt.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_null())
                    new insertIntoduce().execute();
            }
        });


    }



    ShareMainDialog ttt;
    String category=null;


    public class ShareMainDialog extends Dialog {
        public ShareMainDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_mountain_introduce_cate);


            Button btn1 = (Button) findViewById(R.id.btn1);
            Button btn2 = (Button) findViewById(R.id.btn2);
            Button btn3 = (Button) findViewById(R.id.btn3);

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ttt.dismiss();
                    category = "맛집";
                    mountain_introduce_cate.setText(category + " 을 선택하셨습니다");
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ttt.dismiss();
                    category = "숙박";
                    mountain_introduce_cate.setText(category + " 을 선택하셨습니다");

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ttt.dismiss();
                    category = "체험 마을";
                    mountain_introduce_cate.setText(category + " 을 선택하셨습니다");
                }
            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_mountain_introduce_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private boolean check_null() {

        if (mountain_introduce_name.getText().toString().equals("") || mountain_introduce_add.getText().toString().equals("")|| mountain_introduce_text.getText().toString().equals(""))
            return false;
        return true;
    }


    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    String response2;
    private class insertIntoduce extends AsyncTask<String, Void, Void> {
        @Override


        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(MountainIntroduceWrite.this, "잠시만 기다려주세요",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {


                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/mountain_introduce_insert.php");
                nameValuePairs = new ArrayList<NameValuePair>(7);
                nameValuePairs.add(new BasicNameValuePair("type", category));
                nameValuePairs.add(new BasicNameValuePair("add", mountain_introduce_add.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("name", mountain_introduce_name.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("text", mountain_introduce_text.getText().toString()));

                if(mountain_introduce_ph.getText().toString().equals(""))
                    nameValuePairs.add(new BasicNameValuePair("ph", mountain_introduce_ph.getText().toString()));
                else
                    nameValuePairs.add(new BasicNameValuePair("ph","null"));

                if(mountain_introduce_url.getText().toString().equals(""))
                    nameValuePairs.add(new BasicNameValuePair("url", mountain_introduce_url.getText().toString()));
                else
                    nameValuePairs.add(new BasicNameValuePair("url","null"));

                nameValuePairs.add(new BasicNameValuePair("mountain_idx", getIntent().getStringExtra("mountain_idx")));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response2 = httpclient.execute(httppost, responseHandler);


                //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                // String re = httpclient.execute(httppost, responseHandler);
                //  System.out.println("멍충시발~ : " + re);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();

            Toast.makeText(MountainIntroduceWrite.this, "등록완료!", Toast.LENGTH_LONG).show();
            finish();

        }
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (KeyCode == KeyEvent.KEYCODE_BACK) {

                finish();
                return true;

            }
        }
        return super.onKeyDown(KeyCode, event);
    }
}
