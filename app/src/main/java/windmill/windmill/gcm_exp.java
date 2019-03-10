package windmill.windmill;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class gcm_exp extends ActionBarActivity {

    AsyncTask<?, ?, ?> regIDInsertTask;
    TextView message;
    EditText msg;
    Button send;
    ProgressDialog loagindDialog;

    String response_check_id = "0";
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

    public static String regId;
    String id;
    String myResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcm_exp);
        message=(TextView)findViewById(R.id.re_message);

        msg=(EditText)findViewById(R.id.msg);
        send=(Button)findViewById(R.id.msg_send);


        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new sendMsg().execute(regId);

            }
        });
        if(GcmIntentService.re_message!=null){
            message.setText(GcmIntentService.re_message);
        }else{
            registerGcm();
        }
    }



    public void registerGcm() {

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        regId = GCMRegistrar.getRegistrationId(this);

        //message.setText("aa"+regId);

        if (regId.equals("")) {
            GCMRegistrar.register(this,GcmIntentService.SEND_ID);
        } else {
            Log.e("reg_id",regId);
        }
        regIDInsertTask = new regIDInsertTask().execute(regId);
    }


    private class regIDInsertTask extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(gcm_exp.this, "키 등록 중입니다..",
                    "Please wait..", true, false);
        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/gcm_exp.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("reg_id",regId));
                nameValuePairs.add(new BasicNameValuePair("id","jhm1283"));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                response=httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response_check_id = httpclient.execute(httppost, responseHandler);
                // php 에서 에코한 값을 받아오는 변수
                // 존재하는 아이디가 없으면 echo 1 을 해주세요
                System.out.print("뀨뀨"+response_check_id);
            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;


        }



        protected void onPostExecute(Void result) {

            loagindDialog.dismiss();

        }

    }


    private class sendMsg extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(gcm_exp.this, "메세지보내는중",
                    "Please wait..", true, false);
        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/gcm_send_msg.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("reg_id",regId));
                nameValuePairs.add(new BasicNameValuePair("msg",msg.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("id","jhm1283"));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                response=httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response_check_id = httpclient.execute(httppost, responseHandler);
                // php 에서 에코한 값을 받아오는 변수
                // 존재하는 아이디가 없으면 echo 1 을 해주세요
                System.out.print("뀨뀨"+response_check_id);
            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();

        }
    }


    public void HttpPostData(String reg_id , String pnum) {

        try {

            URL url = new URL("http://cmcm1284.cafe24.com/windmill/gcm_exp.php");       // URL 설정

            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

            //--------------------------

            //   전송 모드 설정 - 기본적인 설정이다

            //--------------------------

            http.setDefaultUseCaches(false);

            http.setDoInput(true);

            http.setDoOutput(true);

            http.setRequestMethod("POST");


            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            StringBuffer buffer = new StringBuffer();

            buffer.append("reg_id").append("=").append(reg_id).append("&");                 // php 변수에 값 대입
            buffer.append("pnum").append("=").append(pnum);



            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR");

            PrintWriter writer = new PrintWriter(outStream);

            writer.write(buffer.toString());

            writer.flush();

            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");

            BufferedReader reader = new BufferedReader(tmp);

            StringBuilder builder = new StringBuilder();

            String str;

            while ((str = reader.readLine()) != null) {

                builder.append(str + "\n");

            }



            myResult = builder.toString();
            System.out.print("뀨뀨"+myResult);



        } catch (MalformedURLException e) {

            //

        } catch (IOException e) {

            //

        } // try

    } // HttpPostData

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gcm_exp, menu);
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
