package windmill.windmill;

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
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.PREF;
import data.models.UPDATE;


public class MeetingWrite extends AppCompatActivity {

    String[] items = { "공지", "문의", "자유", "후기", "기타", };
    String cate = null;

    @Bind(R.id.meeting_write_img)
    EditText meeting_write_img;
    @Bind(R.id.meeting_write_title)
    EditText meeting_write_title;
    @Bind(R.id.meeting_write_mountain)
    EditText meeting_write_mountain;
    @Bind(R.id.meeting_write_sub)
    EditText meeting_write_sub;
    @Bind(R.id.meeting_write_text)
    EditText meeting_write_text;
    @Bind(R.id.meeting_write_category)
    EditText meeting_write_category;
    @Bind(R.id.meeting_write_member)
    EditText meeting_write_member;


    static public EditText meeting_write_date;


    @Bind(R.id.ok)
    Button ok;
    @Bind(R.id.cancle)
    Button cancle;
String m_name;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_write);



        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("모임 작성");
        }

        ButterKnife.bind(this);

        m_name = getIntent().getStringExtra("name");
        if(m_name!=null)
            meeting_write_mountain.setText(m_name);

        meeting_write_date = (EditText)findViewById(R.id.meeting_write_date);

        meeting_write_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");

            }
        });

        meeting_write_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQ_CODE_PICK_PICTURE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (check()) {
                    if (uploadFilePath != null) {

                        // new insertBorad().execute(null,null);

                        UploadImageToServer uploadimagetoserver = new UploadImageToServer();
                        uploadimagetoserver
                                .execute("http://cmcm1284.cafe24.com/windmill/image_upload.php");
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "You didn't insert any image", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }


    public boolean check(){
        if(!meeting_write_img.getText().toString().equals("대표사진 선택 완료")){
            Toast.makeText(MeetingWrite.this,"대표사진을 선택해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        if(meeting_write_date.getText().toString().contains("날짜")){
            Toast.makeText(MeetingWrite.this,"모임의 날짜를 선택해주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        if(meeting_write_mountain.getText().toString().equals("")){
            Toast.makeText(MeetingWrite.this,"만나시는 산의 이름을 적어주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        if(meeting_write_sub.getText().toString().equals("")){
            Toast.makeText(MeetingWrite.this,"만나시는 장소의 주소를 적어주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        if(meeting_write_title.getText().toString().equals("")) {
            Toast.makeText(MeetingWrite.this,"모임의 이름을 적어주세요",Toast.LENGTH_LONG).show();
            return false;
        }
        if(meeting_write_member.getText().toString().equals("")) {
            Toast.makeText(MeetingWrite.this,"모임의 인원 수를 적어주세요",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    public static class SimpleCalendarDialogFragment extends DialogFragment implements OnDateChangedListener {

        private TextView textView;
        public String sdate = null;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_basic, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            textView = (TextView) view.findViewById(R.id.textView);

            MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);

            widget.setOnDateChangedListener(this);

            view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sdate==null){
                        //Toast.makeText(MeetingWrite.this,"날짜를 선택해주세요",Toast.LENGTH_LONG).show();
                    }else{
                        sel_date = sdate;
                        meeting_write_date.setText(sdate);
                        meeting_write_date.setTextColor(getResources().getColor(R.color.main_dark1));
                        dismiss();
                    }
                }
            });
        }

        @Override
        public void onDateChanged(@NonNull MaterialCalendarView widget, CalendarDay date) {
            //textView.setText(FORMATTER.format(date.getDate()));

            sdate = date.getYear()+"/"+(date.getMonth()+1)+"/"+date.getDay();
            textView.setText(sdate);

        }
    }

    static String sel_date;
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

               // meeting_write_img.setImageBitmap(bit);

                meeting_write_img.setText("대표사진 선택 완료");
                meeting_write_img.setTextColor(getResources().getColor(R.color.main_dark1));
                meeting_write_img.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.camera2),null);
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


            mProgressDialog = new ProgressDialog(MeetingWrite.this);
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
                                Toast.makeText(MeetingWrite.this,"글 등록 성공", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MeetingWrite.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAGLOG,
                            "[UploadImageToServer] error: " + ex.getMessage(),ex);

                } catch (Exception e) {

                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MeetingWrite.this,"Got Exception : see logcat ",Toast.LENGTH_SHORT).show();
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
            new insertBorad().execute(null,null);
        }
    }


    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    private class insertBorad extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_insert.php");
                nameValuePairs = new ArrayList<NameValuePair>(8);
                nameValuePairs.add(new BasicNameValuePair("id", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("category",meeting_write_category.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("mountain",meeting_write_mountain.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("title",meeting_write_title.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("sub",meeting_write_sub.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("text", meeting_write_text.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("capacity", meeting_write_member.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("date", sel_date));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response2 = httpclient.execute(httppost, responseHandler);



                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_withdraw.php");
                nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("reg_id", IntroActivity.regId));
                nameValuePairs.add(new BasicNameValuePair("sw", "not_del"));
                nameValuePairs.add(new BasicNameValuePair("idx", response2.toString()));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                //System.out.println("멍충시발~ : " + IntroActivity.regId);

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                responseHandler = new BasicResponseHandler();
                response2 = httpclient.execute(httppost, responseHandler);

            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            UPDATE.meeting_update=true;
            UPDATE.mymeeting=true;
            finish();
            Main2Activity.index=1;
            startActivity(new Intent(MeetingWrite.this,Main2Activity.class));
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
}
