/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import data.models.Chatroom;
import data.models.MeetingList;
import data.models.MountainList;
import data.models.PREF;
import windmill.windmill.IntroActivity;
import windmill.windmill.R;
import windmill.windmill.SettingNotify;
import windmill.windmill.SettingsProfileUpdate;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

   // @Bind(R.id.setting_user)
   // TextView user;

    @Bind(R.id.setting_logout)
    RelativeLayout setting_logout;
    @Bind(R.id.setting_update)
    RelativeLayout setting_update;
    @Bind(R.id.setting_notify)
    RelativeLayout setting_notify;
    @Bind(R.id.setting_faq)
    RelativeLayout setting_faq;
    @Bind(R.id.setting_withdraw)
    RelativeLayout setting_withdraw;
    @Bind(R.id.setting_pw)
    RelativeLayout setting_pw;
    @Bind(R.id.setting_setting)
    RelativeLayout setting_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ButterKnife.bind(this);
       // user.setText(PREF.id + "님 안녕하세요^.^");
      //  user.setGravity(Gravity.CENTER);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        setting_logout.setOnClickListener(this);
        setting_update.setOnClickListener(this);
        setting_notify.setOnClickListener(this);
        setting_faq.setOnClickListener(this);
        setting_withdraw.setOnClickListener(this);
        setting_setting.setOnClickListener(this);
        setting_pw.setOnClickListener(this);
    }

    public void onClick(View v) {
//Toast.makeText(getApplicationContext(), "³È³È", Toast.LENGTH_LONG).show();
        if (v == setting_logout) {
            Toast.makeText(getApplicationContext(), "로그아웃 되셨습니다", Toast.LENGTH_SHORT).show();
            logout();
            finish();
            Intent aboutIntent = new Intent(getApplicationContext(),
                    IntroActivity.class);
            startActivity(aboutIntent);
        } else if (v == setting_update) {
            Intent aboutIntent = new Intent(getApplicationContext(),
                    SettingsProfileUpdate.class);
            startActivity(aboutIntent);
        } else if (v == setting_notify) {
            Intent aboutIntent = new Intent(getApplicationContext(),
                    SettingNotify.class);
            startActivity(aboutIntent);
        } else if (v == setting_faq) {
            displayEmailApp();
        } else if (v == setting_withdraw) {
            CustomDialog d = new CustomDialog(this);
            d.show();
        }else if (v == setting_setting) {
            SettingDialog d = new SettingDialog(this);
            d.show();
        }
        else if (v == setting_pw) {
            dd = new UpdateDialog(this);
            dd.show();
        }
    }

    private void displayEmailApp()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        String subject = "[문의사항]";
        String body ="USER : "+PREF.id+" REGID : "+IntroActivity.regId+"\n";
        String[] recipients = new String[]{"cmcm1284@naver.com"};

        emailIntent .putExtra(Intent.EXTRA_EMAIL, recipients);
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent .putExtra(Intent.EXTRA_TEXT, body);
        emailIntent .setType("message/rfc822");
        startActivity(emailIntent);
    }

    UpdateDialog dd;
    TextInputLayout pw1,pw2,pw3;

    public class UpdateDialog extends Dialog{
        public UpdateDialog(Context context){
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_setting_pw);

            pw1 = (TextInputLayout)findViewById(R.id.pw);
            pw2 = (TextInputLayout)findViewById(R.id.pw2);
            pw3 = (TextInputLayout)findViewById(R.id.pw3);

            Button o = (Button) findViewById(R.id.o);
            Button x = (Button) findViewById(R.id.x);

            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(pw1.getEditText().getText().toString().equals("")||pw2.getEditText().getText().toString().equals("")||pw3.getEditText().getText().toString().equals("")){
                        Toast.makeText(SettingsActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_LONG).show();}
                    else if(!pw2.getEditText().getText().toString().equals(pw3.getEditText().getText().toString())){
                        Toast.makeText(SettingsActivity.this,"새로운 비밀번호가 일치하지 않습니다",Toast.LENGTH_LONG).show();
                        pw2.getEditText().setText("");
                        pw3.getEditText().setText("");
                    }
                    else{
                        new update_pw().execute();
                    }

                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


    private void withdraw(){
        new insertReply().execute(null,null);
    }


    private void logout(){
        PREF.login =false;
        PREF.id=null;
        SharedPreferences pref = getSharedPreferences("prev_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        MeetingList.meetingList.clear();
        MountainList.mountainList.clear();

    }


    EditText pw_et;
    String pw;
    public class CustomDialog extends Dialog{
        public CustomDialog(Context context){
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.setting_withdraw);

            pw_et = (EditText)findViewById(R.id.pw);


            Button o = (Button) findViewById(R.id.ok);
            Button x = (Button) findViewById(R.id.cancle);
            pw = pw_et.getText().toString();

            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(pw!="")
                        withdraw();
                    else
                        Toast.makeText(SettingsActivity.this,"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show();
                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    SharedPreferences pref; // 사용할 SharedPreferences 선언
    final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름
    public class SettingDialog extends Dialog{
        public SettingDialog(Context context){
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.setting_setting);

            Button o = (Button) findViewById(R.id.ok);
            Button x = (Button) findViewById(R.id.cancle);
            Switch a = (Switch)findViewById(R.id.switch1);
            pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
            String aa = pref.getString("alarm","true");
            if(aa.equals("true")){ a.setChecked(true);}
            else{ a.setChecked(false);}


            a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(context,"알림이 켜졌습니다",Toast.LENGTH_LONG).show();

                        pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("alarm", "true");
                        editor.commit();
                    } else {
                        Toast.makeText(context,"알림이 꺼졌습니다",Toast.LENGTH_LONG).show();;
                        pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("alarm", "false");
                        editor.commit();

                    }
                }
            });


            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


    String response = "0";
    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    private class insertReply extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(SettingsActivity.this, "",
                    "Please wait..", true, false);
        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/withdraw.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("pw",pw_et.getText().toString()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost, responseHandler);

            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            if(response.contains("true")){
                Toast.makeText(SettingsActivity.this,"ㅠㅠ다시와주세요ㅠㅠ",Toast.LENGTH_LONG).show();
                logout();
                finish();
                Intent aboutIntent = new Intent(getApplicationContext(),IntroActivity.class);
                startActivity(aboutIntent);
            }else{
                Toast.makeText(SettingsActivity.this,"비밀번호가 일치하지 않습니다ㅠ.ㅠ",Toast.LENGTH_LONG).show();
            }
        }
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


    private class update_pw extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(SettingsActivity.this, "",
                    "Please wait..", true, false);
        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/update_pw.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("pw",pw1.getEditText().getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("pw2",pw2.getEditText().getText().toString()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response = httpclient.execute(httppost, responseHandler);



            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());


            }
            return null;
        }
        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            if(response.contains("tlqkfdkwkfahtdlqfurgoTDj")){
                Toast.makeText(SettingsActivity.this, "현재 비밀번호가 일치하지 않습니다ㅠ.ㅠ", Toast.LENGTH_LONG).show();
                pw1.getEditText().setText("");
                pw2.getEditText().setText("");
                pw3.getEditText().setText("");

            }else{
                Toast.makeText(SettingsActivity.this,"비밀번호 수정이 완료되었습니다",Toast.LENGTH_LONG).show();
                dd.dismiss();
            }
        }
    }
}
