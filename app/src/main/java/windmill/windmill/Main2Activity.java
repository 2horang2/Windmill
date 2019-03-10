package windmill.windmill;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.PagerAdapter;

import adapter.FragmentPageAdapter;
import services.OnFragmentInteractionListener;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import ui.fragment.MainFragment;
import ui.fragment.MainFragment2temp;
import ui.fragment.MainFragment3;
import ui.fragment.MainFragment4;
import ui.fragment.MainFragment5;
import ui.fragment.MainFragment6;
import ui.fragment.MeetingHotListFragment;
import ui.fragment.MeetingLikeListFragment;
import ui.fragment.MeetingRegionListFragment;
import ui.fragment.SettingFragment;
import ui.fragment.UserRecipeListFragment;

public class Main2Activity extends AppCompatActivity {

    public static int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);


        setupViewPager(mViewpager);
        setupTabLayout(mTabs);


        mViewpager.setCurrentItem(index);
        index = 1;
    }


    TabLayout mTabs;
    ViewPager mViewpager;

    private FragmentPageAdapter pageAdapter;

    private void initInstances() {

        mTabs = (TabLayout) findViewById(R.id.tabs);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);


    }

    boolean mFlag = false;

    @Override

    public boolean onKeyDown (int KeyCode, KeyEvent event) {

        if (event.getAction()==KeyEvent.ACTION_DOWN) {

            if (KeyCode == KeyEvent.KEYCODE_BACK){

                if (!mFlag){

                    Toast.makeText(this, "뒤로 버튼을 한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();

                    mFlag = true;

                    mKillHandler.sendEmptyMessageDelayed(0,2000);

                    return false; }

                else {   moveTaskToBack(true);

                    finish();

                    android.os.Process.killProcess(android.os.Process.myPid());

                } } }

        return super.onKeyDown(KeyCode, event);

    }



// 종료 버튼이 눌리지 x, mFlag값 복원

    Handler mKillHandler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            if (msg.what == 0)
                mFlag = false;
        }
    };





public void setupViewPager(ViewPager viewPager){
        /// mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
        pageAdapter=new FragmentPageAdapter(getApplicationContext(),getSupportFragmentManager());

        // TextView tab_text = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_textview,null);
        //   tab_text.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_tabbar_chat,0,0);
        pageAdapter.addFragment(MainFragment6.newInstance("채팅방"),"",R.drawable.ic_tabbar_chat);
        pageAdapter.addFragment(MainFragment2temp.newInstance("모임 소개"),"",R.drawable.ic_tabbar_meeting);
        pageAdapter.addFragment(MainFragment5.newInstance("나의 모임"),"",R.drawable.ic_tabbar_mymeeting);
        pageAdapter.addFragment(MainFragment3.newInstance("일정"),"",R.drawable.ic_tabbar_schedule);
        pageAdapter.addFragment(MainFragment.newInstance("산 소개"),"",R.drawable.ic_tabbar_mountain);
        pageAdapter.addFragment(SettingFragment.newInstance("설정"),"",R.drawable.ic_tabbar_setting);
        viewPager.setAdapter(pageAdapter);
        }

public void setupTabLayout(TabLayout tabLayout){
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewpager);
        for(int i=0;i<tabLayout.getTabCount();i++){
        TabLayout.Tab tab=tabLayout.getTabAt(i);
        tab.setCustomView(pageAdapter.getTabView(i));
        }

        tabLayout.requestFocus();
        }
@Override
public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2,menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id=item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_settings){
        return true;
        }

        return super.onOptionsItemSelected(item);
        }
        }
