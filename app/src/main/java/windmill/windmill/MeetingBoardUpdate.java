package windmill.windmill;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.HttpResponse;
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
import data.models.MeetingUserList;
import data.models.PREF;


public class MeetingBoardUpdate extends AppCompatActivity {

    String  master;

    @Bind(R.id.meeting_board_img)
    ImageView meeting_board_img;
    @Bind(R.id.meeting_board_cate)
    ImageView meeting_board_cate;

    @Bind(R.id.meeting_board_title)
    EditText title;
    @Bind(R.id.meeting_board_text)
    EditText text;



    @Bind(R.id.meeting_board_btn)
    Button btn;
    ArrayList<String> items;
    String idx,ti,te,chatroom_idx,cate,img,sw="false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_board_write);

        ButterKnife.bind(this);
        chatroom_idx = getIntent().getStringExtra("idx");
        master = getIntent().getStringExtra("master");


        idx = getIntent().getStringExtra("idx");
        ti = getIntent().getStringExtra("title");
        te = getIntent().getStringExtra("text");
        cate = getIntent().getStringExtra("cate");
        chatroom_idx= getIntent().getStringExtra("c_idx");
        img= getIntent().getStringExtra("img");

        title.setText(ti);
        text.setText(te);

        if(!img.equals("NULL"))
            ImageLoader.getInstance().displayImage(img, meeting_board_img);
        meeting_board_cate.setImageDrawable(getResources().getDrawable(R.drawable.tag2));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("글 수정");
        }

        items = new ArrayList<String>();
        if (PREF.id == null) {
            Toast.makeText(MeetingBoardUpdate.this, "죄송합니다 ㅠ,ㅠ 다시 로그인해주세요!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MeetingBoardUpdate.this, IntroActivity.class);
            startActivity(i);
            finish();
        }

        meeting_board_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_PICTURE);
            }
        });
        meeting_board_cate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ttt = new CateDialog(MeetingBoardUpdate.this);
                ttt.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (check_null()) {
                    // new insertBorad().execute(null, null);
                    if (uploadFilePath != null) {

                        // new insertBorad().execute(null,null);

                        UploadImageToServer uploadimagetoserver = new UploadImageToServer();
                        uploadimagetoserver
                                .execute("http://cmcm1284.cafe24.com/windmill/image_upload.php");
                    } else {
                        new updateBorad().execute(null, null);
                    }
                }
                else {
                    Toast.makeText(MeetingBoardUpdate.this, "모든 정보를 입력해주세요ㅠ.ㅠ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    CateDialog ttt;


    public class CateDialog extends Dialog {
        public CateDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_meeting_review_cate);

            Button btn1 = (Button) findViewById(R.id.s1);
            Button btn2 = (Button) findViewById(R.id.s2);
            Button btn3 = (Button) findViewById(R.id.s3);
            Button btn4 = (Button) findViewById(R.id.s4);
            Button btn5 = (Button) findViewById(R.id.s5);
            Button btn6 = (Button) findViewById(R.id.s6);

            if (!PREF.id.equals(master))
                btn1.setVisibility(View.GONE);
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    cate = "공지";
                    meeting_board_cate.setImageDrawable(getResources().getDrawable(R.drawable.tag2));
                    ttt.dismiss();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    cate = "문의";
                    meeting_board_cate.setImageDrawable(getResources().getDrawable(R.drawable.tag2));
                    ttt.dismiss();

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cate = "자유";
                    meeting_board_cate.setImageDrawable(getResources().getDrawable(R.drawable.tag2));
                    ttt.dismiss();
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    cate = "후기";
                    meeting_board_cate.setImageDrawable(getResources().getDrawable(R.drawable.tag2));
                    ttt.dismiss();
                }
            });
            btn5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cate = "날씨";
                    meeting_board_cate.setImageDrawable(getResources().getDrawable(R.drawable.tag2));
                    ttt.dismiss();
                }
            });
            btn6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    cate = "기타";
                    meeting_board_cate.setImageDrawable(getResources().getDrawable(R.drawable.tag2));
                    ttt.dismiss();
                }
            });


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

    private boolean check_null() {


        if(title.getText().toString().equals("")||text.getText().toString().equals("")||cate==null)
            return false;
        return true;
    }

    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

    private class updateBorad extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(MeetingBoardUpdate.this, "잠시만 기다려주세요",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {


                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_board_update.php");
                nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("idx", idx));
                nameValuePairs.add(new BasicNameValuePair("category",cate));
                nameValuePairs.add(new BasicNameValuePair("title",title.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("text",text.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("img_sw",sw));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response2 = httpclient.execute(httppost, responseHandler);



            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();

            Bundle extras = new Bundle();
            extras.putString("idx", chatroom_idx);
            Intent intent = new Intent(MeetingBoardUpdate.this, MeetingBoard.class);
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        }
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (KeyCode == KeyEvent.KEYCODE_BACK) {

                Bundle extras = new Bundle();
                extras.putString("idx", chatroom_idx);
                Intent intent = new Intent(MeetingBoardUpdate.this, MeetingBoard.class);
                intent.putExtras(extras);
                startActivity(intent);
                finish();
                return true;

            }
        }
        return super.onKeyDown(KeyCode, event);
    }


    /*
    * public boolean onKeyDown( int KeyCode, KeyEvent event )
    {

        if( event.getAction() == KeyEvent.ACTION_DOWN ){
            if( KeyCode == KeyEvent.KEYCODE_BACK ){
                Activity activity = (Activity) getApplicationContext();
                Bundle extras = new Bundle();
                extras.putString("idx", temp.getChatroom_idx());
                Intent intent = new Intent(activity, MeetingBoard.class);
                intent.putExtras(extras);
                activity.startActivity(intent);
                return true;

            }
        }
        return super.onKeyDown( KeyCode, event );
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_meeting_board_write, menu);
        return true;
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
                //mountain_write_img.setImageBitmap(bit);
                sw ="true";
                meeting_board_img.setImageDrawable(getResources().getDrawable(R.drawable.camera2));
            }
        }
    }

    private String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String getName(Uri uri) {

        String[] projection = {MediaStore.Images.ImageColumns.DISPLAY_NAME};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    // uri 아이디 찾기
    private String getUriId(Uri uri) {

        String[] projection = {MediaStore.Images.ImageColumns._ID};
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


            mProgressDialog = new ProgressDialog(MeetingBoardUpdate.this);
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
                        Log.i(TAGLOG, "[UploadImageToServer] Source File not exist :" + uploadFilePath);
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
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("mountain");
                    dos.writeBytes(lineEnd);

                    // 이미지 전송
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
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
                                Toast.makeText(MeetingBoardUpdate.this, "글 등록 성공", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MeetingBoardUpdate.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG,
                            "[UploadImageToServer] error: " + ex.getMessage(), ex);

                } catch (Exception e) {

                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MeetingBoardUpdate.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG,
                            "[UploadImageToServer] Upload file to server Exception Exception : " + e.getMessage(), e);
                }
                Log.i(TAGLOG, "[UploadImageToServer] Finish");
                return null;
            } // End else block
        }

        @Override
        protected void onPostExecute(String s) {
            mProgressDialog.dismiss();
            new updateBorad().execute(null, null);
        }
    }


}
