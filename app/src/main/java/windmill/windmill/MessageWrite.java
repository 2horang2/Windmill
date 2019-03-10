package windmill.windmill;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.TextView;
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

public class MessageWrite extends AppCompatActivity {


    @Bind(R.id.msg_to)
    EditText title;
    @Bind(R.id.msg_text)
    EditText text;

    @Bind(R.id.ok)
    Button ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_write);

        ButterKnife.bind(this);

        String to = getIntent().getStringExtra("to");

        if (to != null)
            title.setText(to);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("쪽지 보내기");
        }


        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (check_null()) {
                    ttt = new CateDialog(MessageWrite.this);
                    ttt.show();
                } else {
                    Toast.makeText(MessageWrite.this, "모든 정보를 입력해주세요ㅠ.ㅠ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_mountain_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if(id== R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    CateDialog ttt;

    public class CateDialog extends Dialog {
        public CateDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_send_msg);

            Button o = (Button) findViewById(R.id.button4);
            Button x = (Button) findViewById(R.id.button5);

            TextView aa = (TextView)findViewById(R.id.textView9);
            aa.setText("현재 보유 포인트 : " + PREF.point);



            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(PREF.point<100){
                        Toast.makeText(MessageWrite.this,"포인트가 모자릅니다ㅠ_ㅠ 충전해주세요",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MessageWrite.this, BillingPoint.class));
                    }else{

                        new insertMsg().execute();
                    }

                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    ttt.dismiss();

                }
            });

        }
    }

    private boolean check_null() {

        if (title.getText().toString().equals("") || text.getText().toString().equals(""))
            return false;
        return true;
    }


    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    String response2;
    private class insertMsg extends AsyncTask<String, Void, Void> {
        @Override


        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(MessageWrite.this, "잠시만 기다려주세요",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/msg_insert.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("person", title.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("text", text.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("person_send", PREF.id));

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

            if(response2.equals("djqtdjdy")){
                Toast.makeText(MessageWrite.this, "받는 이가 존재하지 않습니다", Toast.LENGTH_LONG).show();
                title.setText("");
            }
            else {
                PREF.point = PREF.point - 100;
                SharedPreferences pref = getSharedPreferences("prev_name", Context.MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("point", PREF.point);
                editor.commit();
                Toast.makeText(MessageWrite.this, "쪽지 발송 완료!", Toast.LENGTH_LONG).show();
                finish();
            }
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
