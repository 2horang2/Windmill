package windmill.windmill;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import adapter.FragmentPageAdapter;
import ui.fragment.MainFragment;
import ui.fragment.MainFragment2temp;
import ui.fragment.MainFragment3;
import ui.fragment.MainFragment5;
import ui.fragment.MainFragment6;
import ui.fragment.SendMessage;
import ui.fragment.SettingFragment;
import ui.fragment.TakeMessage;

public class MessageActivity extends AppCompatActivity {


    TabLayout mTabs;
    ViewPager mViewpager;

    private FragmentPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);


        setupViewPager(mViewpager);
        setupTabLayout(mTabs);

    }


    public void setupViewPager(ViewPager viewPager){
        /// mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
        pageAdapter=new FragmentPageAdapter(getApplicationContext(),getSupportFragmentManager());

        // TextView tab_text = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_textview,null);
        //   tab_text.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_tabbar_chat,0,0);
        pageAdapter.addFragment(TakeMessage.newInstance("받은 쪽지함"),"받은 쪽지함",0);
        pageAdapter.addFragment(SendMessage.newInstance("보낸 쪽지함"),"보낸 쪽지함",0);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
