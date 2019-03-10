package windmill.windmill;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.MeetingList;
import data.models.MountainList;
import data.models.PREF;


public class SettingsProfileUpdate extends AppCompatActivity {


    @Bind(R.id.cancle)
    Button x;
    @Bind(R.id.ok)
    Button o;

    @Bind(R.id.profile_email)
    TextView profile_email;
    @Bind(R.id.profile_id)
    TextView profile_id;
    @Bind(R.id.profile_logout)
    TextView profile_logout;
    @Bind(R.id.profile_pw)
    TextView profile_pw;
    @Bind(R.id.profile_withdraw)
    TextView profile_withdraw;
    TextView v;

    @Bind(R.id.profile_update)
    Button profile_update;

    @Bind(R.id.profile_img)
    ImageView profile_img;

    UpdateEmailDialog ee;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile_update);

        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("프로필 수정");
        }
        //new UserImage().execute(null, null);

        profile_id.setText(PREF.id + "");
        ImageLoader.getInstance().displayImage(PREF.img, profile_img);


        profile_email.setText(PREF.email);



        profile_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(SettingsProfileUpdate.this, "로그아웃 되셨습니다", Toast.LENGTH_SHORT).show();
                ee = new UpdateEmailDialog(SettingsProfileUpdate.this);
                ee.show();
            }
        });
        profile_pw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dd = new UpdateDialog(SettingsProfileUpdate.this);
                dd.show();
            }
        });

        profile_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        profile_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(SettingsProfileUpdate.this, "로그아웃 되셨습니다", Toast.LENGTH_SHORT).show();
                logout();
                finish();
                Intent aboutIntent = new Intent(SettingsProfileUpdate.this,
                        Intro2Activity.class);
                startActivity(aboutIntent);
            }
        });
        profile_withdraw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialog d = new CustomDialog(SettingsProfileUpdate.this);
                d.show();
            }
        });



        profile_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_PICTURE);
            }
        });
        o.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    UploadImageToServer uploadimagetoserver = new UploadImageToServer();
                    uploadimagetoserver
                            .execute("http://cmcm1284.cafe24.com/windmill/image_upload.php");
                } else {
                    Toast.makeText(SettingsProfileUpdate.this,
                            "You didn't insert any image", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }






    // LOG
    private String TAGLOG = "log";
    public String uploadFilePath;
    public String uploadFileName;
    private int REQ_CODE_PICK_PICTURE = 1;
    private int serverResponseCode = 0;
    // 파일을 업로드 하기 위한 변수 선언
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_PICK_PICTURE) {

            if (resultCode == Activity.RESULT_OK) {

                Uri uri = data.getData();
                String path = getPath(uri);
                String name = getName(uri);

                uploadFilePath = path;
                uploadFileName = name;

                Log.i(TAGLOG, "[onActivityResult] uploadFilePath:"
                        + uploadFilePath + ", uploadFileName:" + uploadFileName);

                Bitmap bit = BitmapFactory.decodeFile(path);
                profile_img.setImageBitmap(bit);
            }
        }
    }

    private String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String getName(Uri uri) {

        String[] projection = { MediaStore.Images.ImageColumns.DISPLAY_NAME };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // uri 아이디 찾기
    private String getUriId(Uri uri) {

        String[] projection = { MediaStore.Images.ImageColumns._ID };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // ==========================================================================================

    // ==========================================================================================

    // ============================== 사진을 서버에 전송하기 위한 스레드
    // ========================



    private class UploadImageToServer extends AsyncTask<String, String, String> {

        ProgressDialog mProgressDialog;

        String fileName = uploadFilePath;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 10240 * 10240;
        File sourceFile = new File(uploadFilePath);

        @Override
        protected void onPreExecute() {


            mProgressDialog = new ProgressDialog(SettingsProfileUpdate.this);
            mProgressDialog.setTitle("Loading...");
            mProgressDialog.setMessage("Image uploading...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... serverUrl) {

            if (!sourceFile.isFile()) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.i(TAGLOG,"[UploadImageToServer] Source File not exist :" + uploadFilePath);
                    }
                });
                return null;
            } else {
                try {
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(serverUrl[0]);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);
                    Log.i(TAGLOG, "fileName: " + fileName);

                    dos = new DataOutputStream(conn.getOutputStream());

                    // 사용자 이름으로 폴더를 생성하기 위해 사용자 이름을 서버로 전송한다.

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\""+ lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("mountain");
                    dos.writeBytes(lineEnd);

                    // 이미지 전송
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\""+ fileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();
                    Log.i(TAGLOG, "[UploadImageToServer] HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                    if (serverResponseCode == 200) {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SettingsProfileUpdate.this, "글 등록 성공", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                    //insert_chatroom();

                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SettingsProfileUpdate.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG,
                            "[UploadImageToServer] error: " + ex.getMessage(),ex);

                } catch (Exception e) {

                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SettingsProfileUpdate.this,"Got Exception : see logcat ",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG,
                            "[UploadImageToServer] Upload file to server Exception Exception : "+ e.getMessage(), e);
                }
                Log.i(TAGLOG, "[UploadImageToServer] Finish");
                return null;
            } // End else block
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
            new updateProfile().execute(null,null);
        }
    }

    String response2;
    private class updateProfile extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/update_profile.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", PREF.id));
                //nameValuePairs.add(new BasicNameValuePair("email",profile_email_et.getText().toString()));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response2 = httpclient.execute(httppost, responseHandler);



            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {

            pre_img = PREF.img;
            String msg = pre_img;
            StringTokenizer st = new StringTokenizer(msg,"/");
            while(st.hasMoreTokens()){ //hasMoreTokens()는 다음 토큰이 있는지 없는지 체크.
                pre_img = st.nextToken();
            }

            PREF.img = response2;
            SharedPreferences pref; // 사용할 SharedPreferences 선언
            final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름
            pref =  getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("img", response2);
           // String tt = pref.getString("img", "");
            editor.commit();

            pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            String tt = pref.getString("img", " ");
            System.out.print("이이이"+tt);

            new del_img().execute();
            finish();
        }
    }





    private class del_img extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/mountain/del_img.php");
                nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("file",pre_img));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response3 = httpclient.execute(httppost, responseHandler);

                System.out.print("꾜꾜"+response3+pre_img);

            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {

                 }
    }

    String pre_img;


    private void logout(){
        PREF.login =false;
        PREF.id=null;
        SharedPreferences pref = getSharedPreferences("prev_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

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
                        Toast.makeText(SettingsProfileUpdate.this, "모든 정보를 입력해주세요", Toast.LENGTH_LONG).show();}
                    else if(!pw2.getEditText().getText().toString().equals(pw3.getEditText().getText().toString())){
                        Toast.makeText(SettingsProfileUpdate.this,"새로운 비밀번호가 일치하지 않습니다",Toast.LENGTH_LONG).show();
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

    TextInputLayout email;
    public class UpdateEmailDialog extends Dialog{
        public UpdateEmailDialog(Context context){
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_setting_email);

            email= (TextInputLayout)findViewById(R.id.email);

            Button o = (Button) findViewById(R.id.o);
            Button x = (Button) findViewById(R.id.x);

            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(email.getEditText().getText().toString().equals("")){
                        Toast.makeText(SettingsProfileUpdate.this, "변경하실 이메일을 입력해주세요", Toast.LENGTH_LONG).show();}
                    else{
                        new update_email().execute();
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
        new insertReply().execute(null, null);
    }

    EditText pw_et;
    String pw;
    public class CustomDialog extends Dialog {
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
                        Toast.makeText(SettingsProfileUpdate.this,"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show();
                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


    private class update_pw extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(SettingsProfileUpdate.this, "",
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
                Toast.makeText(SettingsProfileUpdate.this, "현재 비밀번호가 일치하지 않습니다ㅠ.ㅠ", Toast.LENGTH_LONG).show();
                pw1.getEditText().setText("");
                pw2.getEditText().setText("");
                pw3.getEditText().setText("");

            }else{
                Toast.makeText(SettingsProfileUpdate.this,"비밀번호 수정이 완료되었습니다",Toast.LENGTH_LONG).show();
                dd.dismiss();
            }
        }
    }

    private class update_email extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(SettingsProfileUpdate.this, "",
                    "Please wait..", true, false);
        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/update_email.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("email",email.getEditText().getText().toString()));

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

                Toast.makeText(SettingsProfileUpdate.this,"이메일일 수정이 완료되었습니다",Toast.LENGTH_LONG).show();
                profile_email.setText(PREF.email);
                dd.dismiss();

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
            loagindDialog = ProgressDialog.show(SettingsProfileUpdate.this, "",
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
                Toast.makeText(SettingsProfileUpdate.this,"ㅠㅠ다시와주세요ㅠㅠ",Toast.LENGTH_LONG).show();
                logout();
                finish();
                Intent aboutIntent = new Intent(SettingsProfileUpdate.this,IntroActivity.class);
                startActivity(aboutIntent);
            }else{
                Toast.makeText(SettingsProfileUpdate.this,"비밀번호가 일치하지 않습니다ㅠ.ㅠ",Toast.LENGTH_LONG).show();
            }
        }
    }

}
