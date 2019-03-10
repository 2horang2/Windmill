package windmill.windmill;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import windmill.windmill.ProgressBar;

import com.google.android.gcm.GCMRegistrar;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import data.models.MeetingList;
import data.models.Meeting_Temp;
import data.models.PREF;
import data.models.UPDATE;

public class Intro2Activity extends Activity {

    public SharedPreferences pref; // 사용할 SharedPreferences 선언
    private final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_intro2);
        ActionBar ac = getActionBar();
        if (ac != null) {
            ac.hide();
        }



        SharedPreferences init; // 사용할 SharedPreferences 선언
        init = getSharedPreferences("init", Context.MODE_MULTI_PROCESS);



        if(!init.contains("init")){
            SharedPreferences.Editor editor = init.edit();
            editor.putBoolean("init", false);
            editor.commit();
            finish();
            startActivity(new Intent(Intro2Activity.this, Main23Activity.class));
        }else {

            progressBar= (ProgressBar) findViewById(R.id.progress);
            PREF PREF = new PREF();
            pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor = pref.edit();

            boolean check_login = pref.getBoolean("login", false);


            if (check_login) {
                PREF.login = true;
                String id = pref.getString("id", "");
                PREF.id = id;
                PREF.img = pref.getString("img", "");
                PREF.email = pref.getString("email", "");
                PREF.img = pref.getString("img", "");
                PREF.reg_id = pref.getString("reg_id", "");
                PREF.point = pref.getInt("point", 0);
                new LikeAsync().execute(null, null);
                editor.commit();

                try {
                    PREF.parameter = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(PREF.id, "UTF-8");
                } catch (Exception e) {

                }

            } else {

            }
            registerGcm();

            new PageAsync().execute(null, null);
            new MeetingAsync().execute(null, null);

        }


    }



    public static boolean intro = false;
    class MeetingAsync extends AsyncTask<String, String, ArrayList<Meeting_Temp>> {
        boolean FRESH = false;
        private Source source;
        private int tr_count;

        @Override
        protected ArrayList<Meeting_Temp> doInBackground(String... strings) {
            return connectMeeting();
        }

        @Override
        protected void onPostExecute(ArrayList<Meeting_Temp> Mountains) {
            Intent iIntent = new Intent(Intro2Activity.this, Main2Activity.class);
            startActivity(iIntent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        private ArrayList<Meeting_Temp> connectMeeting() {

            if (UPDATE.meeting_update) {
                try {
                    int i = 0;
                    MeetingList.meetingList.clear();
                    MeetingList.meetingList_view.clear();
                    URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting.php");
                    InputStream html = url.openStream();
                    source = new Source(new InputStreamReader(html, "UTF-8"));

                    Element table = source.getAllElements(HTMLElementName.TABLE).get(0);
                    tr_count = table.getAllElements(HTMLElementName.TR).size();
                    Element tr = null;

                    MeetingList.meetingList = new ArrayList<Meeting_Temp>(tr_count);

                    for (i = 0; i < tr_count; i++) {

                        tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                        Meeting_Temp tlqkf = new Meeting_Temp();
                        tlqkf.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                        tlqkf.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                        tlqkf.setMountain(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                        tlqkf.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                        tlqkf.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());

                        tlqkf.setSub(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                        tlqkf.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());
                        tlqkf.setUserimg(((Element) tr.getAllElements(HTMLElementName.TD).get(7)).getContent().toString());
                        tlqkf.setCategory(((Element) tr.getAllElements(HTMLElementName.TD).get(8)).getContent().toString());
                        tlqkf.setView(((Element) tr.getAllElements(HTMLElementName.TD).get(9)).getContent().toString());
                        tlqkf.setMember(((Element) tr.getAllElements(HTMLElementName.TD).get(10)).getContent().toString());
                        tlqkf.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(11)).getContent().toString());
                        tlqkf.setMemberImgs(((Element) tr.getAllElements(HTMLElementName.TD).get(12)).getContent().toString());
                        MeetingList.meetingList.add(tlqkf);
                        MeetingList.meetingList_view.add(tlqkf);
                        MeetingList.meetingList_temp.add(tlqkf);
                        MeetingList.meetingList_view_temp.add(tlqkf);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UPDATE.meeting_update = false;

            }
            return MeetingList.meetingList;

        }
    }

    String regId;

    public void registerGcm() {

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        regId = GCMRegistrar.getRegistrationId(this);

        //message.setText("aa"+regId);

        if (regId.equals("")) {
            GCMRegistrar.register(this, GcmIntentService.SEND_ID);
        } else {
            pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("reg_id", regId);
            editor.commit();
            Log.e("reg_id", regId);
        }

        IntroActivity.regId =regId;

        PREF PREF = new PREF();
        boolean check_login = pref.getBoolean("login", false);
        pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("reg_id", regId);
        editor.commit();

        if (check_login) {

            PREF.login = true;
            String id = pref.getString("id", "");
            PREF.id = id;
            PREF.img = pref.getString("img", "");
            PREF.email = pref.getString("email", "");
            PREF.img = pref.getString("img", "");
            PREF.reg_id = pref.getString("reg_id", "");
            PREF.point = pref.getInt("point",0);
            Toast.makeText(Intro2Activity.this, id + "님 반갑습니당^0^", Toast.LENGTH_SHORT).show();
        }
    }







}
