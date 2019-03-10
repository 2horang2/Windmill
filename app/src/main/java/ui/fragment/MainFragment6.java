package ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import data.models.Chatroom;
import data.models.ChatroomList;
import data.models.MeetingList;
import data.models.MeetingMyList;
import data.models.MeetingUserList;
import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.PREF;
import data.models.UPDATE;
import windmill.windmill.IntroActivity;
import windmill.windmill.JoinActivity;
import windmill.windmill.Main2Activity;
import windmill.windmill.MessageActivity;
import windmill.windmill.R;
import windmill.windmill.chat_exp;


public class MainFragment6 extends Fragment implements AdapterView.OnItemClickListener {
    private static final String EXTRA_USER = "user";

    View v;

    boolean setting =false;

    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static MainFragment6 newInstance(String text) {
        MainFragment6 mFragment = new MainFragment6();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        SharedPreferences pref; // ?ъ슜??SharedPreferences ?좎뼵
        final String PREF_NAME = "prev_name"; // ?ъ슜??SharedPreferences ?대쫫
        pref = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);

        if (PREF.id != null) {
            v = inflater.inflate(R.layout.fragment_main6, container, false);
            ex1_list = (ListView) v.findViewById(R.id.chatroom_lv);
            ex1_list.setOnItemClickListener(this);
            new ChatroomAsync().execute();

            v.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   startActivity(new Intent(getActivity(), MessageActivity.class));
                }
            });
            Button setting2 = (Button)v.findViewById(R.id.setting);
            setting2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!setting) {
                        setting2.setText("관리");
                        setting = true;
                    }else{
                        setting2.setText("완료");
                                setting = false;
                    }
                    adapter1 = new ViewAdapter(getActivity(), R.layout.fragment_main6, ChatroomList.chatroomList);
                    ex1_list.setAdapter(adapter1);
                }
            });

        } else {
            v = inflater.inflate(R.layout.fragment_main6_none, container, false);

            Button aa = (Button) v.findViewById(R.id.chat_login);
            aa.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent iIntent = new Intent(getActivity(), IntroActivity.class);
                    startActivity(iIntent);
                    getActivity().finish();
                    Main2Activity.index = 0;
                }
            });
        }
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Bundle extras = new Bundle();
        extras.putString("idx", ChatroomList.chatroomList.get(position).getIdx());
        extras.putString("title",  ChatroomList.chatroomList.get(position).getTitle());
        Intent intent = new Intent(getActivity(), chat_exp.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;

    class ChatroomAsync extends AsyncTask<String, String, ArrayList<Chatroom>> {

        @Override
        protected ArrayList<Chatroom> doInBackground(String... strings) {
            return connectChatroom();
        }

        @Override
        protected void onPostExecute(ArrayList<Chatroom> Chatrooms) {
            adapter1 = new ViewAdapter(getActivity(), R.layout.fragment_main6, Chatrooms);
            ex1_list.setAdapter(adapter1);

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

            LayoutInflater linf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_chatroom, null);
            Chatroom chat = list.get(position);
            if (chat != null) {

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

                ImageView img = (ImageView) convertView.findViewById(R.id.user_image);
                ///   Picasso.with(img.getContext())
                //           .load(chat.getImg())
                //         .into(img);

                ImageLoader.getInstance().displayImage(chat.getImg(), img);

                if(setting ==true) {
                    ImageView remove = (ImageView) convertView.findViewById(R.id.remove);
                    remove.setVisibility(View.VISIBLE);
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            meeting_idx = chat.getIdx();
                            new withdraw().execute();
                        }
                    });
                }

            }

            return convertView;
        }

    }

    ProgressDialog loagindDialog;
    String meeting_idx;
    private class withdraw extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {

            loagindDialog = ProgressDialog.show(getActivity(), "",
                    "Please wait..", true, false);
            super.onPreExecute();
        }


        protected Void doInBackground(String... params) {
            try {
                HttpPost httppost;
                HttpResponse response;
                HttpClient httpclient;
                List<NameValuePair> nameValuePairs;

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_withdraw.php");
                nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("reg_id", null));
                nameValuePairs.add(new BasicNameValuePair("sw", "true"));
                nameValuePairs.add(new BasicNameValuePair("idx", meeting_idx));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String re = httpclient.execute(httppost, responseHandler);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            UPDATE.mymeeting = true;
            UPDATE.meeting_update = true;

            new ChatroomAsync().execute();

        }
    }


    private ArrayList<Chatroom> connectChatroom() {

        if (UPDATE.mymeeting||UPDATE.meeting_update) {


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
            save_mymeeting();
            UPDATE.mymeeting = false;
            UPDATE.meeting_update = false;
        }


        return ChatroomList.chatroomList;
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
