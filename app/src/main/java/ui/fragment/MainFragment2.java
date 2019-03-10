
package ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import data.models.MeetingList;
import data.models.Meeting_Temp;
import data.models.UPDATE;
import windmill.windmill.IntroActivity;
import windmill.windmill.MeetingWrite;
import windmill.windmill.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MainFragment2 extends Fragment {
    private static final String EXTRA_USER = "user";

    ViewPager viewPager;


    View v;
    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static MainFragment2 newInstance(String text) {
        MainFragment2 mFragment = new MainFragment2();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_main2, container, false);

        ButterKnife.bind(getActivity());


        ActionBar ac = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ac != null){
            ac.setTitle("모임 추천");
        }

        tabLayout = (TabLayout) v.findViewById(R.id.pager_tabs);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);

        setupViewPager();
        //new MeetingAsync().execute(null, null);


        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return v;
    }

    public class UpdateThread2 extends Thread {
        public void run() {

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (UPDATE.meeting_update) {
                    new MeetingAsync().execute(null, null);
                }
            }

        }
    }

    private void setupViewPager() {

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        pagerAdapter.addFragment(MeetingHotListFragment.newInstance(), "HOT");
        pagerAdapter.addFragment(UserRecipeListFragment.newInstance(), "NEW");
        pagerAdapter.addFragment(MeetingRegionListFragment.newInstance(), "지역별");
        pagerAdapter.addFragment(MeetingLikeListFragment.newInstance(), "관심사");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> titles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("검색");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "검색22", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Toast.makeText(getActivity(), R.string.search, Toast.LENGTH_SHORT).show();
                return false;
            }

            private void onclose() {
            }

            protected boolean isAlwaysExpanded() {
                return false;
            }

        });


        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);


        menu.findItem(R.id.menu_add).setVisible(true);

        mSearchCheck = false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case R.id.menu_add:
                Bundle extras = new Bundle();
                //extras.putString("idx",idx );
                Intent intent = new Intent(getActivity(), MeetingWrite.class);
                intent.putExtras(extras);
                startActivity(intent);
                break;

            case R.id.menu_search:
                Fragment mFragment;
                FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                mFragment = MeetingSearchFragment.newInstance("검색");
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (mSearchCheck) {
                // implement your search here
            }
            return false;
        }
    };


    class MeetingAsync extends AsyncTask<String, String, ArrayList<Meeting_Temp>> {

        @Override
        protected ArrayList<Meeting_Temp> doInBackground(String... strings) {
            return connectMeeting();
        }

        @Override
        protected void onPostExecute(ArrayList<Meeting_Temp> Mountains) {
            setupViewPager();
        }
    }

    ArrayList<Meeting_Temp> list;
    Source source;
    int tr_count;

    private ArrayList<Meeting_Temp> connectMeeting() {
        list = new ArrayList<Meeting_Temp>();

        try {
            int i = 0;

            list.clear();
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
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
UPDATE.meeting_update=false;
        return MeetingList.meetingList;

    }
}
