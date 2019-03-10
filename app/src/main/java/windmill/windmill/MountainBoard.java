package windmill.windmill;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;
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

import data.models.*;


public class MountainBoard extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String chatroom_idx,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatroom_idx = getIntent().getStringExtra("idx");
        name = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_mountain_board);
        ex1_list = (ListView)findViewById(R.id.board_lv);
        ex1_list.setOnItemClickListener(this);

        setupFab();
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setHomeButtonEnabled(true);
            ac.setTitle(name);
        }

        new ReviewAsync().execute(null, null);

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Bundle extras = new Bundle();

        Review review = list.get(position);
        MountainBoard.mpo = position;
        ReviewDetailActivity.launch(MountainBoard.this, review, aa.get(position), "");
        finish();

    }
    static public int mpo = 0;
    ArrayList<Review> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;
    String img,msg,id;
    class ReviewAsync extends AsyncTask<String, String, ArrayList<Review>> {

        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute()
        {
            super.onPreExecute();
            Dialog = new ProgressDialog(MountainBoard.this);
            Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); /* 원형 프로그래스 다이얼 로그 스타일로 설정 */
            Dialog.setMessage("잠시만 기다려 주세용><");
            Dialog.show();
        }

        @Override
        protected ArrayList<Review> doInBackground(String... strings) {
            return connectReview();
        }
        @Override
        protected void onPostExecute(ArrayList<Review> Reviews) {
            adapter1 = new ViewAdapter(MountainBoard.this, R.layout.activity_mountain_board, Reviews);
            ex1_list.setAdapter(adapter1);
            ex1_list.setSelection(mpo);
            MountainBoard.mpo = 0;
            Dialog.dismiss();
        }
    }

    ArrayList<ImageView> aa = new ArrayList<ImageView>();
    class ViewAdapter extends ArrayAdapter<Review> {
        ArrayList<Review> list;
        public ViewAdapter(Context context, int resource, ArrayList<Review> objects) {
            super(context, resource, objects);
            list = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_mountain_board, null);
            Review review = list.get(position);
            if (review != null) {

                final TextView text = (TextView) convertView.findViewById(R.id.m_text);
                final TextView title = (TextView) convertView.findViewById(R.id.m_title);
                final TextView writer = (TextView) convertView.findViewById(R.id.m_user);
                final TextView date = (TextView) convertView.findViewById(R.id.m_time);

                final TextView m_like = (TextView) convertView.findViewById(R.id.m_like);
                final TextView m_reply = (TextView) convertView.findViewById(R.id.m_reply);

                if (title != null)
                    title.setText(review.getTitle());
                if (text != null)
                    text.setText(review.getText());
                if (writer != null)
                    writer.setText(review.getUser());
                if (date != null)
                    date.setText(review.getDate());


                if (m_like != null)
                    m_like.setText("좋아요 " +review.getLike());
                if (m_reply != null)
                    m_reply.setText("댓글 " +review.getReply());



                ImageView re = (ImageView)convertView.findViewById(R.id.m_reply_btn);
                ImageView like = (ImageView)convertView.findViewById(R.id.m_like_btn);
                ImageView share = (ImageView)convertView.findViewById(R.id.m_share_btn);
                ImageView warning = (ImageView)convertView.findViewById(R.id.m_warning_btn);

                review.setMsg_img(review.getImg());
                review.setMsg(review.getTitle());

                re.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ReplyDialog aa = new ReplyDialog(getContext(),review.getId());
                        aa.show();
                        //Toast.makeText(getContext(), "댓글", Toast.LENGTH_LONG).show();
                    }
                });
                like.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (PREF.id ==null) {
                            Toast.makeText(MountainBoard.this,"로그인을 해주세요 ㅠ_ㅠ",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MountainBoard.this ,IntroActivity.class));
                        }
                        else {
                            if (review.getMembers().contains("," + PREF.id + ",")) {
                                Toast.makeText(MountainBoard.this, "이미 좋아요나 신고를 하셨습니다ㅠ_ㅜ", Toast.LENGTH_LONG).show();
                            } else {
                                mpo = position;
                                id = review.getId();
                                new like_add().execute();
                            }
                        }
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        img = review.getMsg_img();
                        msg = "[2030산악회]\n사람들, 산악에 빠지다..♥" + review.getUser() + "님이 직접 찍으신 "+name+"!!\n서로 멋진 풍경의 산을 공유해보아요!";
                        new kakako().execute();
                    }
                });
                warning.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (PREF.id == null) {
                            Toast.makeText(MountainBoard.this,"로그인을 해주세요 ㅠ_ㅠ",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MountainBoard.this ,IntroActivity.class));
                        }
                        else {
                            if (review.getMembers().contains("," + PREF.id + ",")) {
                                Toast.makeText(MountainBoard.this, "이미 좋아요나 신고를 하셨습니다ㅠ_ㅜ", Toast.LENGTH_LONG).show();
                            } else {
                                mpo = position;
                                id = review.getId();
                                new warning_add().execute();
                            }
                        }
                    }
                });

                ImageView img2 = (ImageView)convertView.findViewById(R.id.m_img);
                aa.add(img2);
                /*
                Picasso.with(img2.getContext())
                        .load(review.getImg())
                        .into(img2);
                */

                ImageView img = (ImageView)convertView.findViewById(R.id.m_user_img);
                  /*
                Picasso.with(img.getContext())
                        .load(review.getUser_img())
                        .into(img);
*/

                ImageLoader.getInstance().displayImage(review.getImg(), img2);
                ImageLoader.getInstance().displayImage(review.getUser_img(), img);



                TextView line = (TextView)convertView.findViewById(R.id.line);
                RelativeLayout r = (RelativeLayout)convertView.findViewById(R.id.re1);
                if(review.getR_user().equals("null")){

                }else {
                    r.setVisibility(View.VISIBLE);
                    ImageView r_img = (ImageView) convertView.findViewById(R.id.reply_img);

                   // Picasso.with(r_img.getContext())
                    //        .load(review.getR_img())
                     //       .into(r_img);


                    ImageLoader.getInstance().displayImage(review.getR_img(), r_img);


                    final TextView r_user = (TextView) convertView.findViewById(R.id.reply_user);
                    final TextView r_date = (TextView) convertView.findViewById(R.id.reply_date);
                    final TextView r_text = (TextView) convertView.findViewById(R.id.reply_text);
                    if (r_user != null)
                        r_user.setText(review.getR_user());
                    if (r_date != null)
                        r_date.setText(review.getR_date());
                    if (r_text != null)
                        r_text.setText(review.getR_text());

                }
                RelativeLayout r2 = (RelativeLayout)convertView.findViewById(R.id.re2);
                if(review.getR_user2().equals("null")){

                }else {
                    line.setVisibility(View.VISIBLE);
                    r2.setVisibility(View.VISIBLE);
                    ImageView r_img2 = (ImageView) convertView.findViewById(R.id.reply_img2);
                 //   Picasso.with(r_img2.getContext())
                   //         .load(review.getR_img2())
                   //         .into(r_img2);

                    ImageLoader.getInstance().displayImage(review.getR_img2(), r_img2);

                    final TextView r_user2 = (TextView) convertView.findViewById(R.id.reply_user2);
                    final TextView r_date2 = (TextView) convertView.findViewById(R.id.reply_date2);
                    final TextView r_text2 = (TextView) convertView.findViewById(R.id.reply_text2);

                    if (r_user2 != null)
                        r_user2.setText(review.getR_user2());
                    if (r_date2 != null)
                        r_date2.setText(review.getR_date2());
                    if (r_text2 != null)
                        r_text2.setText(review.getR_text2());
                }

            }
            return convertView;        }

    }



    private ArrayList<Review> connectReview() {

        list = new ArrayList<Review>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain_review_list.php");
            list.clear();
            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));
            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);
                Review review= new Review();
                review.setId(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                review.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                review.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                review.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                review.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                review.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                review.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());
                review.setUser_img(((Element) tr.getAllElements(HTMLElementName.TD).get(7)).getContent().toString());


                review.setR_img(((Element) tr.getAllElements(HTMLElementName.TD).get(8)).getContent().toString());
                review.setR_user(((Element) tr.getAllElements(HTMLElementName.TD).get(9)).getContent().toString());
                review.setR_date(((Element) tr.getAllElements(HTMLElementName.TD).get(10)).getContent().toString());
                review.setR_text(((Element) tr.getAllElements(HTMLElementName.TD).get(11)).getContent().toString());
                review.setR_img2(((Element) tr.getAllElements(HTMLElementName.TD).get(12)).getContent().toString());
                review.setR_user2(((Element) tr.getAllElements(HTMLElementName.TD).get(13)).getContent().toString());
                review.setR_date2(((Element) tr.getAllElements(HTMLElementName.TD).get(14)).getContent().toString());
                review.setR_text2(((Element) tr.getAllElements(HTMLElementName.TD).get(15)).getContent().toString());


                review.setReply(((Element) tr.getAllElements(HTMLElementName.TD).get(16)).getContent().toString());
                review.setLike(((Element) tr.getAllElements(HTMLElementName.TD).get(17)).getContent().toString());
                review.setWarning(((Element) tr.getAllElements(HTMLElementName.TD).get(18)).getContent().toString());


                review.setMembers(((Element) tr.getAllElements(HTMLElementName.TD).get(19)).getContent().toString());

                if(chatroom_idx.equals(review.getIdx()))
                    list.add(review);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    String reply=null,re_idx=null;
    public class ReplyDialog extends android.app.Dialog {
        public ReplyDialog(Context context,String idx){
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_review_reply);

            TextInputLayout ed = (TextInputLayout) findViewById(R.id.ed);
            EditText ed2 = (EditText) findViewById(R.id.ed2);
            Button o = (Button) findViewById(R.id.o);
            reply_list = (ListView)findViewById(R.id.lv);
            re_idx = idx;
            new ReplyAsync().execute();

            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (PREF.id == null) {
                        Toast.makeText(MountainBoard.this,"로그인을 해주세요 ㅠ_ㅠ",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MountainBoard.this ,IntroActivity.class));
                    }
                    else {
                        reply = ed.getEditText().getText().toString();
                        if (reply.equals("")) {
                            ed2.setError("댓글을 입력해주세요 ㅠ_ㅠ");
                        } else
                            new insertReply().execute();
                    }
                }
            });

        }
    }

    ProgressDialog loagindDialog;
    private class insertReply extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(MountainBoard.this, "",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/mountain_review_insert_reply.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("text", reply));
                nameValuePairs.add(new BasicNameValuePair("idx", re_idx));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response = httpclient.execute(httppost);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            new ReplyAsync().execute();
        }
    }

    ListView reply_list;
    ViewAdapter2 adapter2;
    class ReplyAsync extends AsyncTask<String, String, ArrayList<Reply>> {
        @Override
        protected ArrayList<Reply> doInBackground(String... strings) {
            return connectReply();
        }
        @Override
        protected void onPostExecute(ArrayList<Reply> Replys) {
            adapter2 = new ViewAdapter2(MountainBoard.this, R.layout.view_review_detail, Replys);
            reply_list.setAdapter(adapter2);
            reply_list.setSelection(list_re.size()-1);
            // ex1_list.setSelection(list.size()-1);
        }
    }


    class ViewAdapter2 extends ArrayAdapter<Reply> {
        ArrayList<Reply> list;
        public ViewAdapter2(Context context, int resource, ArrayList<Reply> objects) {
            super(context, resource, objects);
            list = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_reply, null);
            Reply reply = list.get(position);
            if (reply != null) {

                final TextView user = (TextView) convertView.findViewById(R.id.reply_user);
                final TextView text = (TextView) convertView.findViewById(R.id.reply_text);
                final TextView date = (TextView) convertView.findViewById(R.id.reply_date);


                if (user != null && reply.getUser()!=null)
                    user.setText(reply.getUser());
                if (text != null && reply.getText()!=null)
                    text.setText(reply.getText());
                if (date != null && reply.getDate()!=null)
                    date.setText(reply.getDate());

                ImageView img = (ImageView)convertView.findViewById(R.id.reply_img);
                if(reply.getUser_img()!=null && !reply.getUser_img().toString().equals("") ){

                    ImageLoader.getInstance().displayImage(reply.getUser_img(), img);

                }
            }
            return convertView;        }

    }


    ArrayList<Reply> list_re;

    private ArrayList<Reply> connectReply() {
        list_re = new ArrayList<Reply>();
        list_re.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain_review_reply_list.php");

            list_re.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Reply reply= new Reply();
                reply.setUser_img(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                reply.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                reply.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                reply.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                reply.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                reply.setChatroomidx(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());


                if(reply.getChatroomidx().equals(re_idx))
                    list_re.add(reply);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list_re;

    }


    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

    private class kakako extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
        }

        @Override /* AsyncTask 실행 부분 */
        protected Void doInBackground(String... params) {
            try {

                KakaoLink kakaoLink;
                KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;
                try {
                    kakaoLink = KakaoLink.getKakaoLink(MountainBoard.this);
                    kakaoTalkLinkMessageBuilder =
                            kakaoLink.createKakaoTalkLinkMessageBuilder();

                    String text = msg;

                    String imageSrc = img;
                    int width = 300;
                    int height = 200;
                    kakaoTalkLinkMessageBuilder.addImage(imageSrc, width, height);

                    kakaoTalkLinkMessageBuilder.addText(text);
                    //  kakaoTalkLinkMessageBuilder.addAppLink("자세히 보기", new AppActionBuilder().setUrl("http://cmcm1284.cafe24.com/windmill/mountain.php").build());
                    //   kakaoTalkLinkMessageBuilder.addWebLink("카카오톡 홈페이지로 이동", "http://www.kakao.com/services/8");

                    kakaoTalkLinkMessageBuilder.addAppButton("산악회2030");

                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), MountainBoard.this);
                } catch (KakaoParameterException e) {
                    Toast.makeText(MountainBoard.this,
                            "카카오톡을 설치하세요", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }




    private class warning_add extends AsyncTask<String, Void, Void>
    {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute()
        {
            super.onPreExecute();
            Dialog = new ProgressDialog(MountainBoard.this);
            Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); /* 원형 프로그래스 다이얼 로그 스타일로 설정 */
            Dialog.setMessage("잠시만 기다려 주세요.");
            Dialog.show();
        }

        @Override /* AsyncTask 실행 부분 */
        protected Void doInBackground(String... params)
        {
            try{

                HttpPost httppost;
                HttpResponse response;
                HttpClient httpclient;
                List<NameValuePair> nameValuePairs;

                httpclient=new DefaultHttpClient();

                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/mountain_review_warning.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response=httpclient.execute(httppost);
            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Dialog.dismiss();
            new ReviewAsync().execute();
        }
    }


    ProgressDialog Dialog;
    private class like_add extends AsyncTask<String, Void, Void>
    {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute()
        {
            super.onPreExecute();
            Dialog = new ProgressDialog(MountainBoard.this);
            Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); /* 원형 프로그래스 다이얼 로그 스타일로 설정 */
            Dialog.setMessage("잠시만 기다려 주세요.");
            Dialog.show();
        }

        @Override /* AsyncTask 실행 부분 */
        protected Void doInBackground(String... params)
        {
            try{

                HttpPost httppost;
                HttpResponse response;
                HttpClient httpclient;
                List<NameValuePair> nameValuePairs;

                httpclient=new DefaultHttpClient();

                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/mountain_review_like.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response=httpclient.execute(httppost);
            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            Dialog.dismiss();
            new ReviewAsync().execute();

        }
    }



    private MaterialSheetFab materialSheetFab,materialSheetFab2;
    //  private int statusBarColor;
    private int statusBarColor;

    private void setupFab() {

        data.models.Fab fab = (data.models.Fab) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PREF.id == null) {
                    Toast.makeText(MountainBoard.this,"로그인을 해주세요 ㅠ_ㅠ",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MountainBoard.this, IntroActivity.class));
                }
                else{
                    Bundle extras = new Bundle();
                    extras.putString("idx", chatroom_idx);
                    extras.putString("name", name);
                    Intent intent = new Intent(MountainBoard.this, MountainReviewWrite.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                    finish();
                }


            }
        });


    }


}
