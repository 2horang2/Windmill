
package windmill.windmill;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import data.models.MeetingMyList;
import data.models.Meeting_Temp;
import data.models.PREF;
import data.models.UPDATE;
import ui.activity.SettingsActivity;
import ui.fragment.MainFragment;
import ui.fragment.MainFragment2;
import ui.fragment.MainFragment3;
import ui.fragment.MainFragment4;
import ui.fragment.MainFragment5;
import ui.fragment.MainFragment6;

public class MainActivity extends NavigationLiveo implements OnItemClickListener {

    private HelpLiveo mHelpLiveo;
    Toolbar toolbar;
    TextView user_name;





    public static int pxToDp(Context context, int px) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;

    }




    public static int dpToPx(Context context, int dp) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        return px;

    }


    @Override
    public void onInt(Bundle savedInstanceState) {


        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);


        mCustomHeader.setMinimumHeight(dpToPx(getApplicationContext(),200));


        // mCustomHeader.setScaleY(500);
        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);
        ImageView bg = (ImageView)mCustomHeader.findViewById(R.id.imageView3);
        user_name = (TextView)mCustomHeader.findViewById(R.id.user_name);
        TextView user_email = (TextView)mCustomHeader.findViewById(R.id.user_email);
        Button btn = (Button)mCustomHeader.findViewById(R.id.button3);
        user_name.setText(PREF.id);
        user_email.setText(PREF.email);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               Intent intent = new Intent(MainActivity.this,SettingsProfileUpdate.class);
                startActivity(intent);

            }
        });

        if(PREF.img!=null){
            Picasso.with(getApplicationContext())
                    .load(PREF.img)
                    .into(imageView);

            Picasso.with(getApplicationContext())
                    .load(PREF.img)
                    .into(bg);
        }else
            imageView.setImageResource(R.drawable.ic_no_user);



        mHelpLiveo = new HelpLiveo();
        // mHelpLiveo.add("나의 좋아요 목록", R.drawable.ic_inbox_black_24dp, 7);
       // mHelpLiveo.addSubHeader(getString(R.string.categories)); //Item subHeader
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.add("산 소개", R.drawable.ic_star_black_24dp);
        mHelpLiveo.add("모임 찾기", R.drawable.ic_send_black_24dp);
        mHelpLiveo.add("최근 모임", R.drawable.ic_drafts_black_24dp);
        mHelpLiveo.addSeparator(); // Item separator
        mHelpLiveo.addSeparator(); // Item separator

        mHelpLiveo.add("즐겨찾기", R.drawable.ic_star_black_24dp);
        mHelpLiveo.add("나의 모임", R.drawable.ic_drafts_black_24dp);

        mHelpLiveo.add("대화방", R.drawable.ic_send_black_24dp);
        mHelpLiveo.add("이벤트", R.drawable.ic_send_black_24dp);

        with(this).startingPosition(2) //Starting position in the list
                .customHeader(mCustomHeader)
                .addAllHelpItem(mHelpLiveo.getHelp())
                .colorItemSelected(R.color.nliveo_blue_colorPrimary)
                .colorNameSubHeader(R.color.nliveo_blue_colorPrimary)
                .footerItem("환경설정", R.drawable.ic_settings_black_24dp)
                .setOnClickUser(onClickPhoto)
                        // .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .build();

        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 2 ? 15 : 0);



    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ErrorThread whiteThread = new ErrorThread();
        whiteThread.start();

    }

    public class ErrorThread extends Thread {
        public void run() {

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (user_name.getText().equals(null)) {
                    finish();
                    Intent intent = new Intent(MainActivity.this,IntroActivity.class);
                    startActivity(intent);
                }
            }

        }
    }


    @Override
    public void onItemClick(int position) {
        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Intent iIntent;
      //  ActionBar ac = getActionBar();
        PREF.index= position;
        switch (position){

            case 2:
                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();

                break;

            case 3:
                mFragment = MainFragment2.newInstance(mHelpLiveo.get(position).getName());
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();

                break;

            case 4:
                mFragment = MainFragment3.newInstance(mHelpLiveo.get(position).getName());
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;

            case 7:
                mFragment = MainFragment4.newInstance(mHelpLiveo.get(position).getName());
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;

            case 8:
                mFragment = MainFragment5.newInstance(mHelpLiveo.get(position).getName());
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;

            case 9:
                mFragment = MainFragment6.newInstance(mHelpLiveo.get(position).getName());
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;


            default:
                //startActivity(new Intent(getApplicationContext(), chat_exp.class));
                closeDrawer();
                //mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
                break;
        }

        //if (mFragment != null){
        //    mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
      //  }

        setElevationToolBar(position != 2 ? 15 : 0);
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    public boolean onKeyDown( int KeyCode, KeyEvent event )
    {

        if( event.getAction() == KeyEvent.ACTION_DOWN ){
            if( KeyCode == KeyEvent.KEYCODE_BACK ){


                ExitDialog d = new ExitDialog(this);
                d.show();




            }
        }
        return super.onKeyDown( KeyCode, event );
    }

    public class ExitDialog extends Dialog {
        public ExitDialog(Context context){
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_exit);

            Button o = (Button) findViewById(R.id.ok);
            Button x = (Button) findViewById(R.id.cancle);

            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    moveTaskToBack(true);

                    finish();

                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "onClickPhoto :D", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            closeDrawer();
        }
    };
}

