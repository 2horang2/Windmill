package windmill.windmill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

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

import data.models.Chat;
import data.models.Mountain;
import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.PREF;
import data.models.ProfileDialog;


public class chat_exp extends AppCompatActivity {

    String idx, title;
    TextInputLayout txt;
    String msg, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 가져오기
        //idx ="1";
        idx = getIntent().getStringExtra("idx");
        title = getIntent().getStringExtra("title");


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(title+"");
        }


        SharedPreferences pref; // 사용할 SharedPreferences 선언
        pref = getSharedPreferences("prev_name", MODE_PRIVATE);
        id = pref.getString("id", "");

        setContentView(R.layout.activity_chat_exp);
        ex1_list = (ListView) findViewById(R.id.chat_lv);

        new ChatAsync().execute(null, null);

        txt = (TextInputLayout) findViewById(R.id.editText);
        Button btn = (Button) findViewById(R.id.button8);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                msg = txt.getEditText().getText().toString();
                txt.getEditText().setText("");
                new sendMsg().execute("아이디");
            }
        });

        MsgThread whiteThread = new MsgThread();

        whiteThread.start();

    }

    int aaaaa = 0;


    public class MsgThread extends Thread {
        public void run() {

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (GcmIntentService.re_message != null) {
                    new ChatAsync().execute(null, null);
                    GcmIntentService.re_message = null;
                }
            }

        }
    }


    String response_check_id = "0";
    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

    private class sendMsg extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/gcm_send_msg.php");
                nameValuePairs = new ArrayList<NameValuePair>(6);
                nameValuePairs.add(new BasicNameValuePair("reg_id", IntroActivity.regId));
                nameValuePairs.add(new BasicNameValuePair("msg", title + "|" + idx + "|" + PREF.id + " : " + msg));
                nameValuePairs.add(new BasicNameValuePair("id", id));

                nameValuePairs.add(new BasicNameValuePair("user", id));
                nameValuePairs.add(new BasicNameValuePair("text", msg));
                nameValuePairs.add(new BasicNameValuePair("idx", idx));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                // response=httpclient.execute(httppost);
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
            new ChatAsync().execute(null, null);

        }
    }


    ArrayList<Chat> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private View v;
    private Source source;

    public static Mountain dummy(Mountain_Temp meetingList) {
        Mountain dummy = new AutoValue_Mountain3(
                meetingList.getId(),
                meetingList.getImg(),
                meetingList.getName(),
                meetingList.getAddress(),
                meetingList.getText(),
                meetingList.getUser(),
                meetingList.getUser_img(),
                meetingList.getCategory(),
                meetingList.getLng(),
                meetingList.getLa(),
                meetingList.getLike(),
                meetingList.getHate(),
                meetingList.getMembers(),
                meetingList.getReviews()
        );
        return dummy;
    }

    class ChatAsync extends AsyncTask<String, String, ArrayList<Chat>> {

        @Override
        protected ArrayList<Chat> doInBackground(String... strings) {
            return connectChat();
        }

        @Override
        protected void onPostExecute(ArrayList<Chat> Chats) {
            adapter1 = new ViewAdapter(chat_exp.this, R.layout.activity_chat_exp, Chats);
            ex1_list.setAdapter(adapter1);
            ex1_list.setSelection(list.size() - 1);
          //  loagindDialog.dismiss();
        }
    }

    class ViewAdapter extends ArrayAdapter<Chat> {

        ArrayList<Chat> list;

        public ViewAdapter(Context context, int resource, ArrayList<Chat> objects) {
            super(context, resource, objects);
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            Chat chat = list.get(position);
            if (chat != null) {

                if (chat.getUser().equals(PREF.id)) {

                    if (chat.getText().contains("?mountain?link?id?")) {

                        LayoutInflater linf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = linf.inflate(R.layout.item_chat_exp_mountain_my, null);

                        String msg = chat.getText();
                        StringTokenizer st = new StringTokenizer(msg, "|");



                        String img2,mountain2,m_idx,aa;
                        img2 = st.nextToken();
                        mountain2 = st.nextToken();
                        m_idx = st.nextToken();

                        System.out.print("제목이 왜안돼333img2"+img2+mountain2+m_idx);

                        ImageView img = (ImageView) convertView.findViewById(R.id.img);

                    if (img != null && img != null ) {
                        ImageLoader.getInstance().displayImage(img2, img);

                    }
                        final TextView mountain = (TextView) convertView.findViewById(R.id.mountain);
                        if (mountain != null)
                            mountain.setText(mountain2);

                        final RelativeLayout m_link = (RelativeLayout) convertView.findViewById(R.id.m_link);

                        m_link.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                Mountain temp = null;
                                for(int i=0;i< MountainList.mountainList.size();i++){
                                    if(MountainList.mountainList.get(i).getId().equals(m_idx)){
                                        temp = dummy(MountainList.mountainList.get(i));
                                        break;
                                    }
                                }
                                if(temp!=null) {
                                    Activity activity = (Activity) v.getContext();
                                    RecipeDetailActivity.launch(activity, temp, img, "");
                                }

                            }
                        });



                    } else {

                        LayoutInflater linf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = linf.inflate(R.layout.item_chat_exp_my, null);


                        final TextView text = (TextView) convertView.findViewById(R.id.chat_text);
                        if (text != null)
                            text.setText(chat.getText());

                        final TextView date = (TextView) convertView.findViewById(R.id.chat_date);
                        if (date != null)
                            date.setText(chat.getDate());
                    }


                } else {

                    if (chat.getText().contains("?mountain?link?id?")) {

                        LayoutInflater linf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = linf.inflate(R.layout.item_chat_exp_mountain, null);

                        String msg = chat.getText();
                        StringTokenizer st = new StringTokenizer(msg, "|");


                        String img2,mountain2,m_idx,aa;
                        img2 = st.nextToken();
                        mountain2 = st.nextToken();
                        m_idx = st.nextToken();

                        System.out.print("제목이 왜안돼333img2"+img2+mountain2+m_idx);

                        ImageView img = (ImageView) convertView.findViewById(R.id.img);
                        if (img != null && img != null ) {
                            ImageLoader.getInstance().displayImage(img2, img);
                        }

                        final TextView mountain = (TextView) convertView.findViewById(R.id.mountain);
                        if (mountain != null)
                            mountain.setText(mountain2);

                        final RelativeLayout m_link = (RelativeLayout) convertView.findViewById(R.id.m_link);

                        m_link.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                Mountain temp = null;
                                for(int i=0;i< MountainList.mountainList.size();i++){
                                    if(MountainList.mountainList.get(i).getId().equals(m_idx)){
                                        temp = dummy(MountainList.mountainList.get(i));
                                        break;
                                    }
                                }
                                if(temp!=null) {
                                    Activity activity = (Activity) v.getContext();
                                    RecipeDetailActivity.launch(activity, temp, img, "");
                                }

                            }
                        });

                        final TextView user = (TextView) convertView.findViewById(R.id.chat_user);
                        if (user != null)
                            user.setText(chat.getUser());
                        ImageView userimg = (ImageView) convertView.findViewById(R.id.user_image);
                        if (userimg != null && chat.getImg() != null && !chat.getImg().equals("") && !chat.getImg().equals(" ")) {
                         //   Picasso.with(getContext())
                         //           .load(chat.getImg())
                          //          .into(userimg);

                            ImageLoader.getInstance().displayImage(chat.getImg(), userimg);
                        }
                        userimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProfileDialog aaa = new ProfileDialog(chat_exp.this,chat.getUser(),chat.getImg());
                                aaa.show();
                            }
                        });

                    } else {
                        LayoutInflater linf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = linf.inflate(R.layout.item_chat_exp, null);

                        final TextView user = (TextView) convertView.findViewById(R.id.chat_user);
                        if (user != null)
                            user.setText(chat.getUser());


                        final TextView text = (TextView) convertView.findViewById(R.id.chat_text);
                        if (text != null)
                            text.setText(chat.getText());

                        final TextView date = (TextView) convertView.findViewById(R.id.chat_date);
                        if (date != null)
                            date.setText(chat.getDate());


                        ImageView img = (ImageView) convertView.findViewById(R.id.user_image);
                        if (img != null && chat.getImg() != null && !chat.getImg().equals("") && !chat.getImg().equals(" ")) {
                        //    Picasso.with(getContext())
                       //             .load(chat.getImg())
                         //           .into(img);
                            ImageLoader.getInstance().displayImage(chat.getImg(), img);
                        }

                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ProfileDialog aaa = new ProfileDialog(chat_exp.this,chat.getUser(),chat.getImg());
                                aaa.show();

                            }
                        });
                    }

                }


            }

            return convertView;
        }

    }



    private ArrayList<Chat> connectChat() {

        list = new ArrayList<Chat>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/chatroom.php");

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;


            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Chat chat = new Chat();
                chat.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                chat.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                chat.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                chat.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                chat.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());

                if (chat.getIdx().equals(idx))
                    list.add(chat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

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