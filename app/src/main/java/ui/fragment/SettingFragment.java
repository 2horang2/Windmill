package ui.fragment;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.Chatroom;
import data.models.MeetingUserList;
import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.PREF;
import windmill.windmill.BillingPoint;
import windmill.windmill.IntroActivity;
import windmill.windmill.Main2Activity;
import windmill.windmill.MeetingGallery;
import windmill.windmill.MountanLikeActivity;
import windmill.windmill.R;
import windmill.windmill.SettingNotify;
import windmill.windmill.SettingsProfileUpdate;
import windmill.windmill.chat_exp;


public class SettingFragment extends Fragment  {
    private static final String EXTRA_USER = "user";

    View v;
    //LinearLayout

    @Bind(R.id.st_profile)
    LinearLayout st_profile;
    @Bind(R.id.st_gallery)
    LinearLayout st_gallery;
    @Bind(R.id.st_like)
    LinearLayout st_like;
    @Bind(R.id.st_setting)
    LinearLayout st_setting;
    @Bind(R.id.st_notify)
    LinearLayout st_notify;
    @Bind(R.id.st_faq)
    LinearLayout st_faq;
    @Bind(R.id.fi_tv)
    TextView fi_tv;

    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static SettingFragment newInstance(String text){
        SettingFragment mFragment = new SettingFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this,v);



        if(PREF.id==null){
            fi_tv.setText("로그인");
        }
        st_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PREF.id==null){
                    fi_tv.setText("로그인");
                    Intent it = new Intent(getActivity(), IntroActivity.class);
                    //Activity activity = (Activity) getContext();
                    startActivity(it);
                    getActivity().finish();
                    Main2Activity.index=5;
                }else {
                    Intent it = new Intent(getActivity(), SettingsProfileUpdate.class);
                    //Activity activity = (Activity) getContext();
                    startActivity(it);
                }
            }
        });
        st_gallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), MeetingGallery.class);
                startActivity(it);
            }
        });

        st_like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), MountanLikeActivity.class);
                startActivity(it);
            }
        });
        st_setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SettingDialog d = new SettingDialog(getActivity());
                d.show();
            }
        });


        st_notify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), SettingNotify.class);
                startActivity(it);
            }
        });

        st_faq.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayEmailApp();
            }
        });


        return v;
    }




    SharedPreferences pref; // 사용할 SharedPreferences 선언
    final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름
    public class SettingDialog extends Dialog {
        public SettingDialog(Context context){
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.setting_setting);

            Button o = (Button) findViewById(R.id.o);
            Button x = (Button) findViewById(R.id.x);
            Switch a = (Switch)findViewById(R.id.switch1);
            pref = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
            String aa = pref.getString("alarm","true");
            if(aa.equals("true")){ a.setChecked(true);}
            else{ a.setChecked(false);}


            a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(context, "알림이 켜졌습니다", Toast.LENGTH_LONG).show();

                        pref =  getActivity().getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("alarm", "true");
                        editor.commit();
                    } else {
                        Toast.makeText(context,"알림이 꺼졌습니다",Toast.LENGTH_LONG).show();;
                        pref =  getActivity().getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("alarm", "false");
                        editor.commit();

                    }
                }
            });


            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
    private void displayEmailApp()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        String subject = "[문의사항]";
        String body ="USER : "+ PREF.id+" REGID : "+ IntroActivity.regId+"\n";
        String[] recipients = new String[]{"cmcm1284@naver.com"};

        emailIntent .putExtra(Intent.EXTRA_EMAIL, recipients);
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent .putExtra(Intent.EXTRA_TEXT, body);
        emailIntent .setType("message/rfc822");
        startActivity(emailIntent);
    }
}
