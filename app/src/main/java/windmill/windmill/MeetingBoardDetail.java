package windmill.windmill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.Board;
import data.models.PREF;
import data.models.Reply;


public class MeetingBoardDetail extends AppCompatActivity {

@Bind(R.id.user_image)
ImageView user_image;
    @Bind(R.id.review_image)
    ImageView review_image;
    @Bind(R.id.board_cate)
    TextView cate;
    @Bind(R.id.writing_title)
    TextView title;
    @Bind(R.id.writing_writer)
    TextView writer;
    @Bind(R.id.writing_date)
    TextView date;
    @Bind(R.id.writing_text)
    TextView text;
    @Bind(R.id.wirting_reply)
    EditText wirting_reply;
    @Bind(R.id.writing_reply_btn)
    Button wirting_reply_btn;
   // @Bind(R.id.writing_scroll)
  //  ScrollView writing_scroll;
    @Bind(R.id.aa)
    LinearLayout aa;

    @Bind(R.id.rr_up_del)
    RelativeLayout rr_up_del;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.delete)
    Button delete;

    @Bind(R.id.tttttt)
    TextView reply_fold;

    boolean reply = true;
    Board temp = new Board();

    ArrayList<Reply> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;
    String c_idx,user;

    boolean aaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_board_detail);





        ButterKnife.bind(this);

        ex1_list = (ListView)findViewById(R.id.writing_reply_listView);

        new ReplyAsync().execute(null, null);

        temp.setIdx(getIntent().getStringExtra("idx"));
        temp.setTitle(getIntent().getStringExtra("title"));
        temp.setDate(getIntent().getStringExtra("date"));
        temp.setWriter(getIntent().getStringExtra("writer"));
        temp.setCate(getIntent().getStringExtra("cate"));
        temp.setText(getIntent().getStringExtra("text"));
        temp.setChatroom_idx(getIntent().getStringExtra("c_idx"));

        c_idx = getIntent().getStringExtra("c_idx");
        user = getIntent().getStringExtra("user");


        temp.setImg(getIntent().getStringExtra("img"));
        temp.setUserimg(getIntent().getStringExtra("user_image"));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("게시판");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        //review_image

        cate.setText(" [ " + temp.getCate() + " ] ");
        title.setText( temp.getTitle());
        writer.setText(temp.getWriter());
        date.setText(temp.getDate());
        text.setText(temp.getText());

        if(!temp.getImg().equals("NULL")){
            review_image.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(temp.getImg(), review_image);
        }


        if (!TextUtils.isEmpty(temp.getUserimg())) {
           // Picasso.with(getApplicationContext())
            //        .load(temp.getUserimg())
             //       .into(user_image);

            ImageLoader.getInstance().displayImage(temp.getUserimg(), user_image);
        }

        if(user.equals(PREF.id)){
            rr_up_del.setVisibility(View.VISIBLE);
        }
        reply_fold.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(reply){
                    reply = false;
                    ex1_list.setVisibility(View.GONE);

                    reply_fold.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.fold_up), null);
                }else{
                    reply = true;
                    ex1_list.setVisibility(View.VISIBLE);
                    reply_fold.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.fold_down), null);
                }

            }
        });

        wirting_reply_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (PREF.id == null) {
                    Toast.makeText(MeetingBoardDetail.this, "로그인을 해주세요 ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MeetingBoardDetail.this ,IntroActivity.class));
                }
                else {new insertReply().execute(null, null);}
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("idx", temp.getIdx());
                extras.putString("cate", temp.getCate());
                extras.putString("title", temp.getTitle());
                extras.putString("text", temp.getText());
                extras.putString("c_idx",temp.getChatroom_idx());
                extras.putString("img",temp.getImg());
                Intent intent = new Intent(MeetingBoardDetail.this, MeetingBoardUpdate.class);
                intent.putExtras(extras);
                startActivity(intent);
                finish();


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new board_del().execute(null, null);
            }
        });

    }


    public boolean onKeyDown( int KeyCode, KeyEvent event )
    {

        if( event.getAction() == KeyEvent.ACTION_DOWN ){
            if( KeyCode == KeyEvent.KEYCODE_BACK ){
                Bundle extras = new Bundle();
                extras.putString("idx", temp.getChatroom_idx());
                Intent intent = new Intent(MeetingBoardDetail.this, MeetingBoard.class);
                intent.putExtras(extras);
                startActivity(intent);
                finish();
                return true;

            }
        }
        return super.onKeyDown( KeyCode, event );
    }

    private class board_del extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(MeetingBoardDetail.this, "",
                    "Please wait..", true, false);
        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_board_delete.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("user", temp.getWriter()));
                nameValuePairs.add(new BasicNameValuePair("idx",temp.getIdx()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                response=httpclient.execute(httppost);


            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            Bundle extras = new Bundle();
            extras.putString("idx", temp.getChatroom_idx());
            Intent intent = new Intent(MeetingBoardDetail.this, MeetingBoard.class);
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        }
    }








    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    private class insertReply extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(MeetingBoardDetail.this, "메세지보내는중",
                    "Please wait..", true, false);
        }
        @Override

        protected Void doInBackground(String... params) {
            try{

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_board_insert_reply.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("text",wirting_reply.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("idx",temp.getIdx()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                response=httpclient.execute(httppost);


            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            wirting_reply.setText("");
            new ReplyAsync().execute();
        }
    }

    private void setLisetViewHeight(final ListView listView,int count){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        if(count == 0){
            count = listAdapter.getCount();
        }
        for (int i = 0; i < listAdapter.getCount(); i++) {
            if(i>=count)
                break;
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (count - 1));
        listView.setLayoutParams(params);
    }





    class ReplyAsync extends AsyncTask<String, String, ArrayList<Reply>> {
        @Override
        protected ArrayList<Reply> doInBackground(String... strings) {
            return connectReply();
        }
        @Override
        protected void onPostExecute(ArrayList<Reply> Replys) {
            adapter1 = new ViewAdapter(MeetingBoardDetail.this, R.layout.activity_meeting_board_detail, Replys);
            ex1_list.setAdapter(adapter1);
            setLisetViewHeight(ex1_list, list.size());
           // writing_scroll.scrollTo(0, 200);
            // ex1_list.setSelection(list.size()-1);
        }
    }

    class ViewAdapter extends ArrayAdapter<Reply> {
        ArrayList<Reply> list;
        public ViewAdapter(Context context, int resource, ArrayList<Reply> objects) {
            super(context, resource, objects);
            list = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_reply, null);
            Reply reply = list.get(position);
            if (reply != null) {

                final TextView user = (TextView) convertView.findViewById(R.id.reply_user);
                final TextView text = (TextView) convertView.findViewById(R.id.reply_text);
                final TextView date = (TextView) convertView.findViewById(R.id.reply_date);


                if (user != null)
                    user.setText(reply.getUser());
                if (text != null)
                    text.setText(reply.getText());
                if (date != null)
                    date.setText(reply.getDate());

                ImageView img = (ImageView)convertView.findViewById(R.id.reply_img);

                ImageLoader.getInstance().displayImage(reply.getUser_img(), img);
            }
            return convertView;        }

    }



    private ArrayList<Reply> connectReply() {

        list = new ArrayList<Reply>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting_board_reply_list.php");

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Reply reply = new Reply();
                reply.setUser_img(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                reply.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                reply.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                reply.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                reply.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                reply.setChatroomidx(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                if(reply.getChatroomidx().equals(temp.getIdx()))
                    list.add(reply);
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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meeting_board_detail, menu);
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
    */
}
