
package ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.MountainBestAdapter;
import adapter.UserRecipeListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import components.DividerItemDecoration;
import data.models.Fab;
import data.models.Meeting;
import data.models.MeetingList;
import data.models.Meeting_Temp;
import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.PREF;
import data.models.UPDATE;
import windmill.windmill.IntroActivity;
import windmill.windmill.MeetingFilter;
import windmill.windmill.MeetingWrite;
import windmill.windmill.MountainWrite;
import windmill.windmill.R;

public class MainFragment2temp extends Fragment {
    private static final String EXTRA_USER = "user";

    RecyclerView meeting_lv;
    private UserRecipeListAdapter userRecipeListAdapter;
    View v;
    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static MainFragment2temp newInstance(String text) {
        MainFragment2temp mFragment = new MainFragment2temp();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    boolean search=false;
    int index = Index.NEW_INDEX;
    public static String search_st = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_main2_temp, container, false);

      //  ButterKnife.bind(v,this);

        meeting_lv = (RecyclerView)v.findViewById(R.id.scroll2);

        Button sort_hot = (Button) v.findViewById(R.id.sort_hot);
        Button sort_new = (Button) v.findViewById(R.id.sort_new);
        Button sort_filter = (Button)v.findViewById(R.id.sort_filter);
        //editText2
        ImageView sort_search = (ImageView)v.findViewById(R.id.sort_search);
        TextInputLayout ed = (TextInputLayout)v.findViewById(R.id.editText2);
        EditText ed2 = (EditText) v.findViewById(R.id.editText3);



        sort_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (search) {
                    ed.setVisibility(View.GONE);
                    sort_search.setImageDrawable((getResources().getDrawable(R.drawable.search2)));
                    search = false;
                } else {
                    ed.setVisibility(View.VISIBLE);
                    sort_search.setImageDrawable((getResources().getDrawable(R.drawable.x)));
                    search = true;
                }
            }
        });

        ed2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (ed2.getRight() - ed2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        search_st = ed.getEditText().getText().toString();
                        index = 6;
                        setupViews();
                        index = Index.NEW_INDEX;
                        return true;
                    }
                }
                return false;
            }
        });

        ed2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search_st = ed.getEditText().getText().toString();
                    index = 6;
                    setupViews();
                    index = Index.NEW_INDEX;
                    return true;
                }
                return false;
            }
        });

        sort_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MeetingFilter.class));
                getActivity().finish();
            }
        });
        sort_hot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                first = false;
                btn = true;

                meeting_lv.setLayoutManager(new LinearLayoutManager(getActivity()));
                meeting_lv.setHasFixedSize(false);
                userRecipeListAdapter = new UserRecipeListAdapter(Meeting.dummies(Index.HOT_INDEX));
                meeting_lv.addItemDecoration(new DividerItemDecoration(getActivity()));
                meeting_lv.setAdapter(userRecipeListAdapter);


                sort_hot.setBackground(getResources().getDrawable(R.drawable.sort_left_on));
                sort_new.setBackground(getResources().getDrawable(R.drawable.sort_right));

                sort_new.setTextColor(getResources().getColor(R.color.main_dark1));
                sort_hot.setTextColor(getResources().getColor(R.color.white));

                first = true;
            }
        });

        sort_new.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                first = false;
                btn = true;


                meeting_lv.setLayoutManager(new LinearLayoutManager(getActivity()));
                meeting_lv.setHasFixedSize(false);
                userRecipeListAdapter = new UserRecipeListAdapter(Meeting.dummies(Index.NEW_INDEX));
                meeting_lv.addItemDecoration(new DividerItemDecoration(getActivity()));
                meeting_lv.setAdapter(userRecipeListAdapter);

                sort_new.setBackground(getResources().getDrawable(R.drawable.sort_right_on));
                sort_hot.setBackground(getResources().getDrawable(R.drawable.sort_left));

                sort_hot.setTextColor(getResources().getColor(R.color.main_dark1));
                sort_new.setTextColor(getResources().getColor(R.color.white));

                first = true;
            }
        });


        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.scroll2);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.transparent))
                        .sizeResId(R.dimen.divide2r)
                        .build());

        if(UPDATE.meeting_update){
            new MeetingAsync().execute();
        }else
            setupViews();
        setupFab();
        return v;
    }

    boolean first= true,btn=false;
    private void setupViews() {

        if (MeetingList.meetingList.size() > 0) {
            meeting_lv.setVisibility(View.VISIBLE);
            meeting_lv.setLayoutManager(new LinearLayoutManager(getActivity()));
            meeting_lv.setHasFixedSize(false);
            userRecipeListAdapter = new UserRecipeListAdapter(Meeting.dummies(index));
            meeting_lv.addItemDecoration(new DividerItemDecoration(getActivity()));
            meeting_lv.setAdapter(userRecipeListAdapter);



        }else
            meeting_lv.setVisibility(View.GONE);
    }




    private MaterialSheetFab materialSheetFab;
    //  private int statusBarColor;
    private int statusBarColor;

    private void setupFab() {

        Fab fab = (Fab) v.findViewById(R.id.fab);





        // Create material sheet FAB
     //   materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PREF.id == null) {
                    Toast.makeText(getActivity(),"로그인을 해주세요 ㅠ_ㅠ",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), IntroActivity.class));
                }
                else
                    startActivity(new Intent(getActivity(), MeetingWrite.class));

            }
        });





    }




    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getActivity().getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(color);
        }
    }

    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
        }
    }


    class MeetingAsync extends AsyncTask<String, String, ArrayList<Meeting_Temp>> {

        @Override
        protected ArrayList<Meeting_Temp> doInBackground(String... strings) {
            return connectMeeting();
        }

        @Override
        protected void onPostExecute(ArrayList<Meeting_Temp> Mountains) {
            setupViews();

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
            MeetingList.meetingList_temp.clear();
            MeetingList.meetingList_view_temp.clear();
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
        UPDATE.meeting_update=false;
        return MeetingList.meetingList;

    }
}
