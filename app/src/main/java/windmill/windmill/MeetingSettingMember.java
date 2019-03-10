package windmill.windmill;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.StringTokenizer;

import data.models.MeetingSettingMemberData;
import data.models.MeetingUserList;
import data.models.Mountain;
import data.models.MountainList;
import data.models.PREF;
import data.models.ProfileDialog;
import data.models.UPDATE;

public class MeetingSettingMember extends AppCompatActivity {
    ArrayList<MeetingSettingMemberData> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private View v;
    private Source source;

    String meeting_idx;
    String cate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_setting_member);


        meeting_idx = getIntent().getStringExtra("id");
        cate = getIntent().getStringExtra("cate");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            if(!cate.equals("not"))
                actionBar.setTitle("멤버 관리");
            else actionBar.setTitle("멤버 보기");
        }

        invalidateOptionsMenu();
        ex1_list = (ListView) findViewById(R.id.m_lv);
        new MeetingSettingMemberDataAsync().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(!cate.equals("not"))
            getMenuInflater().inflate(R.menu.menu_meeting_setting_member, menu);
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

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class MeetingSettingMemberDataAsync extends AsyncTask<String, String, ArrayList<MeetingSettingMemberData>> {
        protected void onPreExecute() {

            loagindDialog = ProgressDialog.show(MeetingSettingMember.this, "",
                    "Please wait..", true, false);
            super.onPreExecute();
        }
        @Override
        protected ArrayList<MeetingSettingMemberData> doInBackground(String... strings) {

            return connectMeetingSettingMemberData();
        }

        @Override
        protected void onPostExecute(ArrayList<MeetingSettingMemberData> MeetingSettingMemberDatas) {
            adapter1 = new ViewAdapter(MeetingSettingMember.this, R.layout.activity_meeting_setting_member, MeetingSettingMemberDatas);
            ex1_list.setAdapter(adapter1);
            loagindDialog.dismiss();
        }
    }

    class ViewAdapter extends ArrayAdapter<MeetingSettingMemberData> {

        ArrayList<MeetingSettingMemberData> list;

        public ViewAdapter(Context context, int resource, ArrayList<MeetingSettingMemberData> objects) {
            super(context, resource, objects);
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_meeting_setting_member, null);
            MeetingSettingMemberData chat = list.get(position);
            if (chat != null) {

                ImageView img = (ImageView) convertView.findViewById(R.id.user_image);

                if (img != null && img != null && !chat.getMember_img().equals("")) {
                    ImageLoader.getInstance().displayImage(chat.getMember_img(), img);

                }


                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileDialog aaa = new ProfileDialog(MeetingSettingMember.this,chat.getMember_id(),chat.getMember_img());
                        aaa.show();

                    }
                });

                /*
                * Picasso.with(img.getContext())
               .load(chat.getMember_img())
               .into(img);*/
                final TextView member_name = (TextView) convertView.findViewById(R.id.member_name);
                if (member_name != null  && !chat.getMember_id().equals(""))
                    member_name.setText(chat.getMember_id());
                final TextView member_dd = (TextView) convertView.findViewById(R.id.delete);

                if(PREF.id!=null) {
                    if (!PREF.id.equals(chat.getMember_id())) {
                        member_dd.setVisibility(View.GONE);
                    }
                }
                if(!cate.equals("not")) {
                    member_dd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d_d = new DeleteDialog(MeetingSettingMember.this, chat.getMember_id());
                            d_d.show();
                        }
                    });
                }else {
                    member_dd.setVisibility(View.GONE);
                }


            }

            return convertView;
        }

    }


    ProgressDialog loagindDialog;

    private class withdraw extends AsyncTask<String, Void, Void> {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute() {

            loagindDialog = ProgressDialog.show(MeetingSettingMember.this, "",
                    "Please wait..", true, false);
            super.onPreExecute();
        }

        @Override /* AsyncTask 실행 부분 */
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
                nameValuePairs.add(new BasicNameValuePair("user", del_member));

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

            new MeetingSettingMemberDataAsync().execute();
            loagindDialog.dismiss();

        }
    }

    private ArrayList<MeetingSettingMemberData> connectMeetingSettingMemberData() {

        list = new ArrayList<MeetingSettingMemberData>();
        list.clear();
        try {

            String parameter = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(meeting_idx, "UTF-8");
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting_setting_member.php?"+ parameter);

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;


            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                MeetingSettingMemberData chat = new MeetingSettingMemberData();
                chat.setMeeting_idx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                chat.setMember_id(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                chat.setMember_img(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());

                list.add(chat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

    }





    DeleteDialog d_d;


    public class DeleteDialog extends Dialog {
        public DeleteDialog(Context context,String member) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_check);

            Button btn1 = (Button) findViewById(R.id.o);
            Button btn2 = (Button) findViewById(R.id.x);
            TextView tt = (TextView)findViewById(R.id.textView8);
            tt.setText(member +"님을 탈퇴시키겠습니까?");


            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    del_member = member;
                    new withdraw().execute();
                    Toast.makeText(context, "강퇴 완료!", Toast.LENGTH_LONG).show();
                    d_d.dismiss();
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    d_d.dismiss();

                }
            });

        }
    }

    String del_member;
}
