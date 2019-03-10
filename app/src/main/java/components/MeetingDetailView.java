package components;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kakao.kakaolink.AppActionBuilder;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Callback;
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.Meeting;
import data.models.MeetingMyList;
import data.models.MeetingUserList;
import data.models.Meeting_Temp;
import data.models.PREF;
import data.models.ProfileDialog;
import data.models.UPDATE;
import windmill.windmill.ImageViewActivity;
import windmill.windmill.Intro2Activity;
import windmill.windmill.IntroActivity;
import windmill.windmill.Main2Activity;
import windmill.windmill.MainActivity;
import windmill.windmill.MeetingBoard;
import windmill.windmill.MeetingBoardWrite;
import windmill.windmill.MeetingSettingMember;
import windmill.windmill.MeetingUpdate;
import windmill.windmill.R;
import windmill.windmill.RecipeDetailActivity;
import windmill.windmill.chat_exp;


public class MeetingDetailView extends FrameLayout {

    ShareMainDialog ttt3;
    Activity activity = (Activity) getContext();
    @Bind(R.id.meeting_img)
    ImageView recipeImageView;
    // @Bind(R.id.title_wrapper)
    //   View titleWrapperView;
    @Bind(R.id.meeting_title)
    TextView MeetingTitle;

    @Bind(R.id.title_wrapper)
    RelativeLayout title_wrapper;


    @Bind(R.id.meeting_mountain_add)
    TextView meeting_mountain_add;
    @Bind(R.id.meeting_mountain_name)
    TextView meetingMountainName;
    @Bind(R.id.meeting_mountain_state)
    TextView meetingMountainState;

    @Bind(R.id.meeting_text)
    TextView meetingText;


    @Bind(R.id.meeting_join)
    Button meeting_join;
    @Bind(R.id.meeting_chat)
    Button meeting_chat;
    @Bind(R.id.meeting_board)
    Button meeting_board;

    @Bind(R.id.r1)
    MaterialRippleLayout r1;
    @Bind(R.id.r2)
    MaterialRippleLayout r2;
    @Bind(R.id.r3)
    MaterialRippleLayout r3;

    @Bind(R.id.meeting_link)
    Button meeting_link;

    @Bind(R.id.leader_id)
    TextView leader_id;
    @Bind(R.id.leader_img)
    ImageView leader_img;
    @Bind(R.id.member_list)
    TextView member_list;


    String idx, sw;
    LinearLayout layout;
    //meeting_join

    boolean link = false;

    KakaoLink kakaoLink;
    KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder;
    // @Bind(R.id.recipe_user)
    // RecipeUserView recipeUserView;

    Meeting meeting;

    private Callback callback = new Callback() {
        @Override
        public void onSuccess() {
            Bitmap bitmap = ((BitmapDrawable) recipeImageView.getDrawable()).getBitmap();
            new Palette.Builder(bitmap)
                    .generate(palette -> {
                        int darkMutedColor = palette.getDarkMutedColor(R.color.text_color_primary);
                        title_wrapper.setBackgroundColor(darkMutedColor);
                        int vibrantColor = palette.getVibrantColor(android.R.color.white);
                        int lightVibrantColor = palette.getLightVibrantColor(R.color.text_color_tertiary);
                        //updatedAtTextView.setTextColor(lightVibrantColor);
                    });
        }

        @Override
        public void onError() {

        }
    };

    public MeetingDetailView(Context context) {
        super(context);
        setup();
    }

    View v;

    public MeetingDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        v = inflate(getContext(), R.layout.view_meeting_detail, this);
        ButterKnife.bind(this);
    }

    Handler handler;

    public void setMeeting(Meeting meeting) {

        this.meeting = meeting;
        if (!TextUtils.isEmpty(meeting.img())) {
            Picasso.with(getContext())
                    .load(meeting.img())
                    .into(recipeImageView, callback);
        }

        recipeImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new Handler();
                handler.postDelayed(irun, 0);
            }
        });


        String msg = meeting.member_imgs();
        StringTokenizer st = new StringTokenizer(msg, ",");
        int cnt = st.countTokens();
        ArrayList<String> temp = new ArrayList<String>(cnt);

        while (st.hasMoreTokens()) { //hasMoreTokens()는 다음 토큰이 있는지 없는지 체크.
            temp.add(st.nextToken());
        }


        meetingMountainState.setText("    " + meeting.date());
        meetingMountainName.setText("  " + cnt + " / " + meeting.member() + "명");
        MeetingTitle.setText("[" + meeting.mountain() + "] " + meeting.title());
        meetingText.setText(meeting.text());
        meeting_mountain_add.setText("    " + meeting.sub());
        leader_id.setText("모임장 : " + meeting.user());
        ImageLoader.getInstance().displayImage(meeting.userimg(), leader_img);


        leader_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialog aaa = new ProfileDialog(activity, meeting.user(), meeting.userimg());
                aaa.show();

            }
        });


        idx = meeting.idx();

        if (MeetingUserList.meetinguserlist.contains(meeting.idx())) {
            r3.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);

            meeting_join.setText("탈퇴하기");
            sw = "true";
        } else {
            r3.setVisibility(View.GONE);
            r2.setVisibility(View.GONE);
            sw = "false";
        }

        //https://developers.kakao.com/docs/android#gradle-환경설정

        meeting_link.setOnClickListener(v -> {
            ttt3 = new ShareMainDialog(getContext());
            ttt3.show();

        });


        member_list.setOnClickListener(v -> {
            Bundle extras = new Bundle();
            extras.putString("cate", "not");
            extras.putString("id", meeting.idx());
            Intent intent = new Intent(activity, MeetingSettingMember.class);
            intent.putExtras(extras);
            activity.startActivity(intent);


        });

        meeting_join.setOnClickListener(v -> {


            if (PREF.id == null) {
                Toast.makeText(activity, "로그인하신 후 가입해주세요ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                activity.startActivity(new Intent(activity, IntroActivity.class));
            } else {
                if (sw == "false") {
                    if (cnt >= Integer.valueOf(meeting.member())) {
                        Toast.makeText(getContext(), "이미 회원이 꽉 찬 모임입니다ㅜ_ㅜ", Toast.LENGTH_LONG).show();
                    } else {
                        join = new JoinDialog(getContext());
                        join.show();
                    }
                } else {

                    if(PREF.id.equals(meeting.user())){
                        Toast.makeText(getContext(), "모임장은 모임을 탈퇴 할 수 없습니다.", Toast.LENGTH_LONG).show();
                    }else {
                        out = new OutDialog(getContext());
                        out.show();
                    }
                }
            }

                    });
                    meeting_chat.setOnClickListener(v -> {

                        Activity activity = (Activity) getContext();
                        Bundle extras = new Bundle();
                        extras.putString("idx", meeting.idx());
                        extras.putString("title", meeting.title());
                        Intent intent = new Intent(activity, chat_exp.class);
                        intent.putExtras(extras);
                        activity.startActivity(intent);

                        // Activity activity = (Activity) getContext();
                        //UserProfileActivity.launch(activity, user, Transition.PUSH_RIGHT_TO_LEFT);
                    });
                    meeting_board.setOnClickListener(v -> {

                        Activity activity = (Activity) getContext();
                        Bundle extras = new Bundle();
                        extras.putString("idx", meeting.idx());

                        extras.putString("master", meeting.user());
                        extras.putString("name", meeting.title());
                        Intent intent = new Intent(activity, MeetingBoard.class);
                        intent.putExtras(extras);
                        activity.startActivity(intent);

                    });

                }

                OutDialog out;
    public class OutDialog extends Dialog {
        public OutDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_check);

            Button btn = (Button) findViewById(R.id.o);
            Button btn2 = (Button) findViewById(R.id.x);
            TextView tv = (TextView) findViewById(R.id.textView8);

            tv.setText("정말 탈퇴하시겠어요?");


            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    new withdraw().execute();
                    new MeetingMyAsync().execute();
                    out.dismiss();

                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    out.dismiss();
                }
            });


        }

    }

        EditText join_ed;
        ImageView join_img;
        JoinDialog join;
        public class JoinDialog extends Dialog {
            public JoinDialog(Context context) {
                super(context);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.dialog_meeting_join);

                Button btn = (Button) findViewById(R.id.o);
                Button btn2 = (Button) findViewById(R.id.x);
                join_ed = (EditText) findViewById(R.id.ed_join);
                join_img = (ImageView) findViewById(R.id.img);


                ImageLoader.getInstance().displayImage(PREF.img, join_img);


                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!join_ed.getText().toString().equals("")) {

                            new insertBorad().execute(null, null);
                            new withdraw().execute();

                            new MeetingMyAsync().execute();
                            join.dismiss();
                        } else {
                            Toast.makeText(activity, "모든 정보를 입력해주세요ㅠ.ㅠ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        join.dismiss();
                    }
                });


            }

        }


    public static int dpToPx(Context context, int dp) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        return px;

    }

    Runnable irun = new Runnable() {

        @Override
        public void run() {

            Activity activity = (Activity) getContext();
            Bundle extras = new Bundle();
            extras.putString("img", meeting.img());
            extras.putString("name", meeting.title());
            Intent intent = new Intent(activity, ImageViewActivity.class);
            intent.putExtras(extras);
            activity.startActivity(intent);

            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    private void sendMsg() {
        try {


            Intent sendIntent = new Intent(Intent.ACTION_VIEW);

            String smsBody = meeting.title() + "( " + meeting.mountain() + " )";

            sendIntent.putExtra("sms_body", smsBody); // 보낼 문자


            //  sendIntent.putExtra("address", "01012341234"); // 받는사람 번호

            sendIntent.setType("vnd.android-dir/mms-sms");

            getContext().startActivity(sendIntent);


        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    String response_check_id = "0";
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;

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
                    kakaoLink = KakaoLink.getKakaoLink(getContext());
                    kakaoTalkLinkMessageBuilder =
                            kakaoLink.createKakaoTalkLinkMessageBuilder();

                    String text = meeting.title() + "( " + meeting.mountain() + " )";

                    String imageSrc = meeting.img();
                    int width = 300;
                    int height = 200;
                    kakaoTalkLinkMessageBuilder.addImage(imageSrc, width, height);

                    kakaoTalkLinkMessageBuilder.addText(text);
                  //  kakaoTalkLinkMessageBuilder.addAppLink("자세히 보기", new AppActionBuilder().setUrl("market://details?id=windmill.windmill").build());
                    //   kakaoTalkLinkMessageBuilder.addWebLink("카카오톡 홈페이지로 이동", "http://www.kakao.com/services/8");

                    kakaoTalkLinkMessageBuilder.addAppButton("산따라바람따라");

                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder.build(), getContext());
                } catch (KakaoParameterException e) {
                    Toast.makeText(getContext(),
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

    ProgressDialog loagindDialog;

    private class withdraw extends AsyncTask<String, Void, Void> {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute() {
            loagindDialog = ProgressDialog.show(getContext(), "잠시만 기다려주세요",
                    "Please wait..", true, false);
            super.onPreExecute();
        }
        protected void onPostExecute(ArrayList<Meeting_Temp> Mountains) {
            loagindDialog.dismiss();
        }
        @Override /* AsyncTask 실행 부분 */
        protected Void doInBackground(String... params) {
            String re;
            try {
                HttpPost httppost;
                HttpResponse response;
                HttpClient httpclient;
                List<NameValuePair> nameValuePairs;

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_withdraw.php");
                nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("reg_id", IntroActivity.regId.toString()));
                nameValuePairs.add(new BasicNameValuePair("sw", sw));
                nameValuePairs.add(new BasicNameValuePair("idx", meeting.idx()));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                re = httpclient.execute(httppost, responseHandler);
            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (sw.equals("true")) {
                meeting_join.setText("가입하기");
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);
                sw = "false";
                MeetingUserList.meetinguserlist.remove(idx);
            } else {
                meeting_join.setText("탈퇴하기");
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                sw = "true";
                MeetingUserList.meetinguserlist.add(idx);
            }
            UPDATE.meeting_update = true;
            UPDATE.mymeeting = true;

            loagindDialog.dismiss();

        }
    }



    private Source source;
    private int tr_count;
    ProgressDialog my;
    class MeetingMyAsync extends AsyncTask<String, String, ArrayList<String>> {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute() {
            my = ProgressDialog.show(getContext(), "",
                    "업데이트중입니다", true, false);
            super.onPreExecute();
        }
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            return connectMeetingMy();
        }

        protected void onPostExecute(ArrayList<Meeting_Temp> Mountains) {
          //  loagindDialog.dismiss();
        }
    }


    private ArrayList<String> connectMeetingMy() {

        try {
            int i = 0;
            MeetingMyList.meetingmylist.clear();
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting_user_list.php");
            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);
                if (((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString().equals(PREF.id)) {
                    MeetingMyList.meetingmylist.add(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        my.dismiss();
        return MeetingMyList.meetingmylist;
    }


    public class ShareMainDialog extends Dialog {
        public ShareMainDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_mountain_share);

            Button btn = (Button) findViewById(R.id.chat);
            Button btn2 = (Button) findViewById(R.id.kaka);
            Button btn3 = (Button) findViewById(R.id.sms);

            findViewById(R.id.rr).setVisibility(View.GONE);

            // btn.setVisibility(View.GONE);
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new kakako().execute();
                    ttt3.dismiss();

                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sendMsg();
                    ttt3.dismiss();
                }
            });


        }

    }


    ProgressDialog insertb;
    private class insertBorad extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            insertb = ProgressDialog.show(getContext(), "잠시만 기다려주세요",
                    "글 등록중입니다다", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_board_insert.php");
                nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("chatroom_idx", meeting.idx()));
                nameValuePairs.add(new BasicNameValuePair("category", "가입인사"));
                nameValuePairs.add(new BasicNameValuePair("title", PREF.id + "님이 가입하셨습니다"));
                nameValuePairs.add(new BasicNameValuePair("writer", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("text", join_ed.getText().toString()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response2 = httpclient.execute(httppost, responseHandler);


                //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                // String re = httpclient.execute(httppost, responseHandler);
                //  System.out.println("멍충시발~ : " + re);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            insertb.dismiss();

        }
    }

}

