package ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapter.MountainBestAdapter;
import adapter.MyMeetingAdapter;
import adapter.RecipeListAdapter;
import adapter.UserRecipeListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import components.DividerItemDecoration;
import data.models.Channel2;
import data.models.Chatroom;
import data.models.ChatroomList;
import data.models.Meeting;
import data.models.MeetingList;
import data.models.MeetingMyList;
import data.models.MeetingUserList;
import data.models.Meeting_Temp;
import data.models.MountainList;
import data.models.PREF;
import data.models.UPDATE;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;
import services.ChannelService2;
import windmill.windmill.IntroActivity;
import windmill.windmill.Main2Activity;
import windmill.windmill.R;


public class MainFragment5 extends Fragment {
    private static final String EXTRA_USER = "user";

    static public boolean setting = false;
    View v;
    @Bind(R.id.my_meeting_lv)
    StaggeredGridView userRecipeListView;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static MainFragment5 newInstance(String text) {
        MainFragment5 mFragment = new MainFragment5();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        SharedPreferences pref; // ?ъ슜??SharedPreferences ?좎뼵
        final String PREF_NAME = "prev_name"; // ?ъ슜??SharedPreferences ?대쫫
        pref = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);

        if (PREF.id != null) {
            v = inflater.inflate(R.layout.fragment_main5, container, false);
            ButterKnife.bind(this, v);

            if (UPDATE.mymeeting || UPDATE.meeting_update)
                new ChatroomAsync().execute();
            else setupViews();

            Button aa = (Button) v.findViewById(R.id.setting);
            v.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!setting) {
                        aa.setText("완료");
                        setting = true;
                    } else {
                        aa.setText("관리");
                                setting = false;
                    }
                    if (UPDATE.mymeeting) {
                        new ChatroomAsync().execute();
                        setupViews();
                    }
                    else setupViews();
                }
            });
        } else {
            v = inflater.inflate(R.layout.fragment_main5_none, container, false);

            Button aa = (Button) v.findViewById(R.id.chat_login);
            aa.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent iIntent = new Intent(getActivity(), IntroActivity.class);
                    startActivity(iIntent);
                    getActivity().finish();
                    Main2Activity.index = 2;
                }
            });
        }
        return v;


    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private MyMeetingAdapter recipeListAdapter;

    private Subscription channelsSubscription = Subscriptions.empty();

    private void setupViews() {

        channelsSubscription = AppObservable.bindActivity(getActivity(), new ChannelService2().getList())
                .subscribe(this::setupViews);
    }


    private void setupViews(List<Channel2> channels) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        recipeListAdapter = new MyMeetingAdapter(getActivity(), channels.get(0).recipes());
        userRecipeListView.setAdapter(recipeListAdapter);


    }


    private int tr_count;
    private Source source;
    ProgressDialog loagindDialog;
    class ChatroomAsync extends AsyncTask<String, String, ArrayList<String>> {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute() {

            loagindDialog = ProgressDialog.show(getActivity(), "",
                    "Please wait..", true, false);
            super.onPreExecute();
        }
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            return connectChatroom();
        }

        @Override
        protected void onPostExecute(ArrayList<String> Chatrooms) {
            setupViews();
            loagindDialog.dismiss();
        }
    }


    private ArrayList<String> connectChatroom() {

        if (UPDATE.mymeeting) {
            try {

                URL url = new URL("http://cmcm1284.cafe24.com/windmill/chatroom_list.php?" + PREF.parameter);


                ChatroomList.chatroomList.clear();
                MeetingMyList.meetingmylist.clear();

                InputStream html = url.openStream();
                source = new Source(new InputStreamReader(html, "UTF-8"));

                Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
                tr_count = table.getAllElements(HTMLElementName.TR).size();
                Element tr = null;

                for (int i = 0; i < tr_count; i++) {
                    tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                    Chatroom chatroom = new Chatroom();
                    chatroom.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                    chatroom.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                    chatroom.setMsg(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                    chatroom.setMember(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                    chatroom.setTime(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                    chatroom.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());


                    MeetingMyList.meetingmylist.add(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                    ChatroomList.chatroomList.add(chatroom);
                    MeetingUserList.meetinguserlist.add(chatroom.getIdx());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i <= ChatroomList.chatroomList.size() - 2; i++) {
                for (int j = i + 1; j <= ChatroomList.chatroomList.size() - 1; j++) {

                    String i_total = ChatroomList.chatroomList.get(i).getTime();
                    String j_total = ChatroomList.chatroomList.get(j).getTime();
                    int aa = i_total.compareTo(j_total);
                    if (aa < 0) {
                        Chatroom tmp = ChatroomList.chatroomList.get(i);
                        ChatroomList.chatroomList.set(i, ChatroomList.chatroomList.get(j));
                        ChatroomList.chatroomList.set(j, tmp);
                    }
                }
            }
            UPDATE.mymeeting = false;

        }

        save_mymeeting();
        return MeetingMyList.meetingmylist;
    }

    public void save_mymeeting() {
        SharedPreferences sp = getActivity().getSharedPreferences("mm", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", MeetingMyList.meetingmylist.size()); /*sKey is an array*/

        int j=0;
        for (int i = 0; i < MeetingList.meetingList_temp.size(); i++) {
            if (MeetingMyList.meetingmylist.contains(MeetingList.meetingList_temp.get(i).getIdx())) {

                mEdit1.remove("Status_" + j);

                String str = MeetingList.meetingList_temp.get(i).getIdx()+"|"
                        +MeetingList.meetingList_temp.get(i).getImg()+"|"
                        +MeetingList.meetingList_temp.get(i).getTitle()+"|"
                        +MeetingList.meetingList_temp.get(i).getMountain()+"|"
                        +MeetingList.meetingList_temp.get(i).getDate();

                mEdit1.putString("Status_" + j ,str );
                j++;

            }
        }
        mEdit1.commit();

    }

}
