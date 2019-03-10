package windmill.windmill;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TextInputLayout;
import com.google.android.gcm.GCMRegistrar;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import data.models.MeetingMyList;
import data.models.Meeting_Temp;
import data.models.PREF;


public class IntroActivity extends Activity {
    Button login_btn;
    TextView join;

    AsyncTask<?, ?, ?> regIDInsertTask;
    //TextView message;
    ProgressDialog loagindDialog;

    String response_check_id = "0";
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

    public static String regId;
    String id;

    public SharedPreferences pref; // 사용할 SharedPreferences 선언
    private final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름

    TextInputLayout id_et;
    TextInputLayout pw_et;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        id_et = (TextInputLayout) findViewById(R.id.intro_id);
        pw_et = (TextInputLayout) findViewById(R.id.intro_pw);
        final boolean login = false;
        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        ActionBar ac = getActionBar();
        if (ac != null) {
            ac.hide();
        }

        if (GcmIntentService.re_message != null) {
            //message.setText(GcmIntentService.re_message);
        } else {
            registerGcm();
        }

        login_btn = (Button) findViewById(R.id.intro_btn);
        join = (TextView) findViewById(R.id.intro_register);
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Network_execcute().execute();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent iIntent = new Intent(IntroActivity.this, JoinActivity.class);
                startActivity(iIntent);
            }
        });
    }

    Runnable irun = new Runnable() {

        @Override
        public void run() {
            Intent iIntent = new Intent(IntroActivity.this, Intro2Activity.class);
            startActivity(iIntent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        Intent iIntent = new Intent(IntroActivity.this, Main2Activity.class);
        startActivity(iIntent);
        finish();
    }


    String response2 = "-2", response3;
    ProgressDialog Dialog;

    private class Network_execcute extends AsyncTask<String, Void, Void> {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute() {
            /* Dialog 설정 구문 */
            Dialog = new ProgressDialog(IntroActivity.this);
            Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); /* 원형 프로그래스 다이얼 로그 스타일로 설정 */
            Dialog.setMessage("잠시만 기다려 주세요.");
            Dialog.show();
            super.onPreExecute();
        }

        protected Void doInBackground(String... params) {

            try {

                HttpPost httppost;
                HttpResponse response;
                HttpClient httpclient;
                List<NameValuePair> nameValuePairs;

                String id = id_et.getEditText().getText().toString();
                String pw = pw_et.getEditText().getText().toString();

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/login.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("pw", pw));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response3 = httpclient.execute(httppost, responseHandler);
            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Dialog.dismiss(); /* Dialog 종료 부분 */
            if (!response3.equals("dksldpdydjqtdjdy")) {

                sharedPreference();
                Toast.makeText(IntroActivity.this, "환영합니당♡", Toast.LENGTH_SHORT).show();

                PREF.login = true;
                PREF.id = id_et.getEditText().getText().toString();
                PREF.email = email;
                PREF.img = img;
                PREF.reg_id = regId;

                PREF.point = pref.getInt("point",0);

                if (GcmIntentService.re_message != null) {
                    //message.setText(GcmIntentService.re_message);
                } else {
                    registerGcm();
                }

            } else {
                Toast.makeText(IntroActivity.this, "입력 하신 아이디와 비밀번호가 존재하지 않습니다ㅠ_ㅠ \n다시 입력해주세요", Toast.LENGTH_SHORT).show();
                id_et.getEditText().setText("");
                pw_et.getEditText().setText("");
            }
            super.onPostExecute(result);

        }
    }

    String img, email;

    public void sharedPreference() {

        String id, pw;
        int point;
        id = id_et.getEditText().getText().toString();
        pw = pw_et.getEditText().getText().toString();

        String msg = response3;
        StringTokenizer st = new StringTokenizer(msg, "*");
        ArrayList<String> temp = new ArrayList<String>();
        while (st.hasMoreTokens()) { //hasMoreTokens()는 다음 토큰이 있는지 없는지 체크.
            temp.add(st.nextToken());
        }

            img = temp.get(0);
            email = temp.get(1);
            point = Integer.valueOf(temp.get(2));


        pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("login", true);
        editor.putString("id", id);
        editor.putString("pw", pw);
        editor.putString("email", temp.get(1));
        editor.putString("img", temp.get(0));
        editor.putString("alarm", "true");
        editor.putInt("point", point);
        editor.commit();
    }


    public void registerGcm() {

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        regId = GCMRegistrar.getRegistrationId(this);

        //message.setText("aa"+regId);

        if (regId.equals("")) {
            GCMRegistrar.register(this, GcmIntentService.SEND_ID);
        } else {
            pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("reg_id", regId);
            editor.commit();
            Log.e("reg_id", regId);
        }

        regIDInsertTask = new regIDInsertTask().execute(regId);
    }

    private class regIDInsertTask extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(IntroActivity.this, "",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/gcm_exp.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("reg_id", regId));
                nameValuePairs.add(new BasicNameValuePair("id", PREF.id));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response_check_id = httpclient.execute(httppost, responseHandler);
                // php 에서 에코한 값을 받아오는 변수
                // 존재하는 아이디가 없으면 echo 1 을 해주세요
                System.out.print("뀨뀨" + response_check_id);
            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {

            loagindDialog.dismiss();

            PREF PREF = new PREF();
            boolean check_login = pref.getBoolean("login", false);
            pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("reg_id", regId);
            editor.commit();

            if (check_login) {

                PREF.login = true;
                String id = pref.getString("id", "");
                PREF.id = id;
                PREF.img = pref.getString("img", "");
                PREF.email = pref.getString("email", "");
                PREF.img = pref.getString("img", "");
                PREF.reg_id = pref.getString("reg_id", "");
                PREF.point = pref.getInt("point",0);

                Toast.makeText(IntroActivity.this, id + "님 반갑습니당^0^", Toast.LENGTH_SHORT).show();
                handler = new Handler();
                handler.postDelayed(irun, 1000);
            }

        }

    }


}
