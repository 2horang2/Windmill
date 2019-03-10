package windmill.windmill;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class JoinActivity extends Activity {


    @Bind(R.id.profile_update)
    Button profile_update;
    @Bind(R.id.profile_img)
    ImageView profile_img;
    @Bind(R.id.join_id)
    TextInputLayout join_id;
    @Bind(R.id.join_email)
    TextInputLayout join_email;
    @Bind(R.id.join_pw)
    TextInputLayout join_pw;
    @Bind(R.id.join_pw2)
    TextInputLayout join_pw2;
    @Bind(R.id.join_btn)
    Button join_btn;

    String img = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        ButterKnife.bind(this);

        ActionBar ac = getActionBar();
        if (ac != null) {
            ac.hide();
        }



        profile_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_PICTURE);
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (uploadFilePath != null) {

                    if(check()) {
                        img="not null";
                        UploadImageToServer uploadimagetoserver = new UploadImageToServer();
                        uploadimagetoserver
                                .execute("http://cmcm1284.cafe24.com/windmill/image_upload.php");
                    }
                } else {
                    if(check()) {
                        new updateProfile().execute(null, null);
                    }
                }
            }
        });

    }

    boolean check(){

        if(join_id.getEditText().getText().toString().equals("")||join_email.getEditText().getText().toString().equals("")||join_pw.getEditText().getText().toString().equals("")||join_pw2.getEditText().getText().toString().equals("")){
            Toast.makeText(JoinActivity.this,"모든 정보를 입력해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!join_pw.getEditText().getText().toString().equals(join_pw2.getEditText().getText().toString())){
            Toast.makeText(JoinActivity.this,"비밀번호가 일치하지 않습니다",Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
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


            mProgressDialog = new ProgressDialog(JoinActivity.this);
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
                                Toast.makeText(JoinActivity.this, "글 등록 성공", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(JoinActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG,
                            "[UploadImageToServer] error: " + ex.getMessage(),ex);

                } catch (Exception e) {

                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(JoinActivity.this,"Got Exception : see logcat ",Toast.LENGTH_SHORT).show();
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
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/join.php");
                nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("id",join_id.getEditText().getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email",join_email.getEditText().getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("pw",join_pw.getEditText().getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("img",img));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                response2 = httpclient.execute(httppost, responseHandler);

            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {

            if(response2.equals("wndqhrdlek")){
                join_id.getEditText().setText(null);
                join_id.setError("이미 존재하는 아이디입니다");
            }else
                Toast.makeText(JoinActivity.this, "즐거운 여행 되세요\n산바람바람따라 잘부탁드려요^_^", Toast.LENGTH_SHORT).show();
                finish();
        }
    }

    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
}
