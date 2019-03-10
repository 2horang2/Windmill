package windmill.windmill;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.AppActionBuilder;
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

import adapter.MeetingRecommendAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import components.RecipeDetailView;
import data.models.Chatroom;
import data.models.Like;
import data.models.LikeList;
import data.models.MeetingList;
import data.models.MeetingUserList;
import data.models.Meeting_Temp;
import data.models.Mountain;
import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.PREF;
import data.models.UPDATE;

public class RecipeDetailActivity extends Activity {
    public static final String EXTRA_MOUNTAIN = "recipe";
    public static final String EXTRA_IMAGE = "ItemDetailActivity:image";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recipe_detail)
    RecipeDetailView recipeDetailView;

    private Mountain mountain;

    public String lng, la;

    public static void launch(Activity activity, Mountain mountain, ImageView transitionView, String url) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                transitionView,
                EXTRA_IMAGE);

        Intent intent = new Intent(activity, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_MOUNTAIN, mountain.toJson());
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        ActionBar ac = getActionBar();
        if (ac != null) {
            ac.hide();
        }
        ImageView recipeImageView = (ImageView) recipeDetailView.findViewById(R.id.recipe_image);
        ViewCompat.setTransitionName(recipeImageView, EXTRA_IMAGE);
        mountain = Mountain.fromJson(getIntent().getStringExtra(EXTRA_MOUNTAIN), Mountain.class);

        setupViews();
        btn();
    }






    ShareMainDialog ttt;



    public class ShareMainDialog extends Dialog{
        public ShareMainDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_mountain_share);


            Button btn1 = (Button) findViewById(R.id.chat);
            Button btn2 = (Button) findViewById(R.id.kaka);
            Button btn3 = (Button) findViewById(R.id.sms);

            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    share();
                    ttt.dismiss();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   new kakako().execute();
                    ttt.dismiss();

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sendMsg();
                    ttt.dismiss();
                }
            });


        }

        private void sendMsg(){
            try{


                Intent sendIntent = new Intent(Intent.ACTION_VIEW);

                String smsBody = PREF.id + "님이 소개하는( "+mountain.name() +" ) 구경오세요!";
                sendIntent.putExtra("sms_body", smsBody); // 보낼 문자
                //  sendIntent.putExtra("address", "01012341234"); // 받는사람 번호

                sendIntent.setType("vnd.android-dir/mms-sms");
                getContext().startActivity(sendIntent);


            }catch (Exception e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }


    }

    private class kakako extends AsyncTask<String, Void, Void> {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute() {

        }

        @Override /* AsyncTask 실행 부분 */
        protected Void doInBackground(String... params) {
            try {

                KakaoLink kakaoLink;
                KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;
                try {
                    kakaoLink = KakaoLink.getKakaoLink(RecipeDetailActivity.this);
                    kakaoTalkLinkMessageBuilder =
                            kakaoLink.createKakaoTalkLinkMessageBuilder();

                    String text =PREF.id + "님이 강력추천하는 "+mountain.name() +"  구경오세요!!";

                    String imageSrc = mountain.img();
                    int width = 300;
                    int height = 200;
                    kakaoTalkLinkMessageBuilder.addImage(imageSrc, width, height);

                    kakaoTalkLinkMessageBuilder.addText(text);
                    kakaoTalkLinkMessageBuilder.addAppLink("자세히 보기", new AppActionBuilder().setUrl("market://details?id=windmill.windmill").build());
                    //kakaoTalkLinkMessageBuilder.addWebLink("카카오톡 홈페이지로 이동", "https://play.google.com/store/apps/details?id=windmill.windmill");
                    //   kakaoTalkLinkMessageBuilder.addWebLink("카카오톡 홈페이지로 이동", "http://www.kakao.com/services/8");

                    kakaoTalkLinkMessageBuilder.addAppButton("산따라바람따라");

                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), RecipeDetailActivity.this);
                } catch (KakaoParameterException e) {
                    Toast.makeText(RecipeDetailActivity.this,
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



    ShareDialog aaa;
    String msg;
    private void share(){
        msg =mountain.img()+"|"+mountain.name()+"|"+mountain.id()+"|?mountain?link?id?";
        aaa = new ShareDialog(RecipeDetailActivity.this);
        aaa.show();
    }

    ArrayList<Chatroom> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;

    public class ShareDialog extends Dialog implements AdapterView.OnItemClickListener{
        public ShareDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_share_mountain_chatroom);

            ex1_list = (ListView) findViewById(R.id.chatroom_lv);
            new ChatroomAsync().execute();

        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
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
            loagindDialog = ProgressDialog.show(RecipeDetailActivity.this, "메세지보내는중",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/gcm_send_msg.php");
                nameValuePairs = new ArrayList<NameValuePair>(6);
                nameValuePairs.add(new BasicNameValuePair("reg_id", IntroActivity.regId));
                nameValuePairs.add(new BasicNameValuePair("msg", c_title + "|" + c_idx + "|" + PREF.id + " : " + msg));
                nameValuePairs.add(new BasicNameValuePair("id", PREF.id));

                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("text", msg));
                nameValuePairs.add(new BasicNameValuePair("idx", c_idx));

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
            loagindDialog.dismiss();

            Bundle extras = new Bundle();


            extras.putString("idx", c_idx);
            extras.putString("title", c_title);
            Intent intent = new Intent(RecipeDetailActivity.this, chat_exp.class);
            intent.putExtras(extras);
            startActivity(intent);

        }
    }

    String c_idx,c_title;


    class ChatroomAsync extends AsyncTask<String, String, ArrayList<Chatroom>> {

        @Override
        protected ArrayList<Chatroom> doInBackground(String... strings) {
            return connectChatroom();
        }

        @Override
        protected void onPostExecute(ArrayList<Chatroom> Chatrooms) {
            adapter1 = new ViewAdapter(RecipeDetailActivity.this, R.layout.dialog_share_mountain_chatroom, Chatrooms);
            ex1_list.setAdapter(adapter1);
          //  ex1_list.setSelection(list.size()-1);
        }
    }

    class ViewAdapter extends ArrayAdapter<Chatroom> {

        ArrayList<Chatroom> list;
        public ViewAdapter(Context context, int resource, ArrayList<Chatroom> objects) {
            super(context, resource, objects);
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_chatroom, null);
            Chatroom chat = list.get(position);
            if (chat != null) {

                LinearLayout aa = (LinearLayout)convertView.findViewById(R.id.item);
                final TextView title = (TextView) convertView.findViewById(R.id.chatroom_title);
                final TextView msg = (TextView) convertView.findViewById(R.id.chatroom_msg);
                final TextView member = (TextView) convertView.findViewById(R.id.chatroom_member);
                final TextView time = (TextView) convertView.findViewById(R.id.chatroom_time);

                if (title != null)
                    title.setText(chat.getTitle());
                if (msg != null)
                    msg.setText(chat.getMsg());
                if (member != null)
                    // member.setText(chat.getMember());
                    if (time != null)
                        time.setText(chat.getTime());

                ImageView img = (ImageView)convertView.findViewById(R.id.user_image);
                ImageLoader.getInstance().displayImage(chat.getImg(), img);

                aa.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        aaa.dismiss();
                        c_idx = list.get(position).getIdx();
                        c_title = list.get(position).getTitle();
                        new sendMsg().execute();


                    }
                });

            }

            return convertView;
        }

    }

    ProgressDialog Dialog;
    String sw;

    private ArrayList<Chatroom> connectChatroom() {

        list = new ArrayList<Chatroom>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/chatroom_list.php?" + PREF.parameter);

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Chatroom chatroom= new Chatroom();
                chatroom.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                chatroom.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                chatroom.setMsg(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                chatroom.setMember(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                chatroom.setTime(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                chatroom.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());

                list.add(chatroom);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= list.size() - 2; i++) {
            for (int j = i + 1; j <= list.size() - 1; j++) {

                String i_total = list.get(i).getTime();
                String j_total = list.get(j).getTime();
                int aa = i_total.compareTo(j_total);
                if (aa<0) {
                    Chatroom tmp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, tmp);
                }
            }
        }

        return list;

    }

    private void btn() {
        LayoutInflater layoutInflater = getLayoutInflater();

        View header = layoutInflater.inflate(R.layout.view_recipe_detail, null);
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button btn4 = (Button) findViewById(R.id.btn4);
        Button btn5 = (Button) findViewById(R.id.btn5);
        Button btn6 = (Button) findViewById(R.id.btn6);


        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // share();

                Bundle extras = new Bundle();

                String idx =mountain.id();
                String name =mountain.name();

                extras.putString("idx", idx);
                extras.putString("name", name);
                Intent iIntent = new Intent(RecipeDetailActivity.this, MountainIntroduce.class);
                iIntent.putExtras(extras);
                startActivity(iIntent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               // share();

                ttt = new ShareMainDialog(RecipeDetailActivity.this);
                ttt.show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (PREF.id == null) {
                    Toast.makeText(RecipeDetailActivity.this, "로그인하신 후 가입해주세요ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RecipeDetailActivity.this, IntroActivity.class));
                } else {
                    boolean flag = true;
                    for (int i = 0; i < LikeList.likelist.size(); i++) {
                        if (LikeList.likelist.get(i).getIdx().equals(mountain.id())) {
                            flag = false;
                            break;
                        }

                    }
                    if (flag == true) {
                        sw = "favor";
                        new favor().execute(null, null);
                        Toast.makeText(RecipeDetailActivity.this, "즐겨찾기가 등록 완료!", Toast.LENGTH_LONG).show();
                        flag=false;
                    } else {

                        sw = "not";
                        new favor().execute(null, null);
                        Toast.makeText(RecipeDetailActivity.this, "즐겨찾기가 해제되었습니다", Toast.LENGTH_LONG).show();
                        flag=true;
                    }
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Bundle extras = new Bundle();

                Double lng = Double.parseDouble(mountain.lng().toString());
                Double la = Double.parseDouble(mountain.la().toString());

                extras.putDouble("la", la);
                extras.putDouble("lng", lng);
                Intent iIntent = new Intent(RecipeDetailActivity.this, Tmap_exp.class);
                iIntent.putExtras(extras);
                startActivity(iIntent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                tempList.clear();
                if (find_meeting(mountain.name()))
                    setupPopUpView();
                else
                    setupPopUpViewNone();

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("idx", mountain.id());
                extras.putString("name", mountain.name());
                m_name = mountain.name();
                Intent iIntent = new Intent(RecipeDetailActivity.this, MountainBoard.class);
                iIntent.putExtras(extras);
                startActivity(iIntent);
            }
        });
    }

    public static String m_name;
    ArrayList<Meeting_Temp> tempList = new ArrayList<Meeting_Temp>(MeetingList.meetingList.size());

    private boolean find_meeting(String mountainName) {
        boolean cnt = false;
        for (int i = 0; i < MeetingList.meetingList.size(); i++) {
            Meeting_Temp temp = MeetingList.meetingList.get(i);
            if (temp.getMountain().contains(mountainName) || temp.getText().contains(mountainName) || temp.getTitle().contains(mountainName)) {
                tempList.add(temp);
                cnt = true;
            }
        }
        return cnt;
    }

    private void setupPopUpView() {
        View popupView = getLayoutInflater().inflate(R.layout.list_meeting_vp, null);

        PopupWindow mPopupWindow = new PopupWindow(popupView,
                WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);

        ViewPager vp = (ViewPager) popupView.findViewById(R.id.pager);
        MeetingRecommendAdapter adapter = new MeetingRecommendAdapter(getLayoutInflater(), tempList.size(), tempList);
        vp.setAdapter(adapter);


        Button exit_btn1 = (Button) popupView.findViewById(R.id.exit_btn1);
        Button exit_btn2 = (Button) popupView.findViewById(R.id.exit_btn2);

        exit_btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        exit_btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private void setupPopUpViewNone() {
        View popupView = getLayoutInflater().inflate(R.layout.list_item_meeting_none, null);

        PopupWindow mPopupWindow = new PopupWindow(popupView,
                WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);

        Button exit_btn1 = (Button) popupView.findViewById(R.id.exit_btn1);
        Button exit_btn2 = (Button) popupView.findViewById(R.id.exit_btn2);

        Button none_create_meeting = (Button) popupView.findViewById(R.id.none_create_meeting);
        Button none_cancle = (Button) popupView.findViewById(R.id.none_cancle);


        exit_btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        exit_btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        exit_btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        none_cancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        none_create_meeting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("name", mountain.name());
                m_name = mountain.name();
                Intent iIntent = new Intent(RecipeDetailActivity.this, MeetingWrite.class);
                iIntent.putExtras(extras);
                startActivity(iIntent);
            }
        });
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private void setupViews() {
        recipeDetailView.setRecipe(mountain);
    }


    private class favor extends AsyncTask<String, Void, Void>
    {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute()
        {
            super.onPreExecute();
            Dialog = new ProgressDialog(RecipeDetailActivity.this);
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

                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/add_mountain_favor.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("idx",mountain.id()));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("sw", sw));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
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
            UPDATE.mountain_update=true;
            Dialog.dismiss();
            UPDATE.like = true;
            new LikeAsync().execute();

        }
    }

}
