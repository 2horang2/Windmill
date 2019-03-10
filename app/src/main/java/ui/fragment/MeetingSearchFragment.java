package ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

import adapter.UserRecipeListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import components.DividerItemDecoration;
import data.models.Meeting;
import data.models.MeetingList;
import data.models.PREF;
import utils.EventDecorator;
import utils.MySelectorDecorator;
import windmill.windmill.MeetingBoard;
import windmill.windmill.R;


public class MeetingSearchFragment extends Fragment {

    @Bind(R.id.scroll)
    RecyclerView userRecipeListView;

    private UserRecipeListAdapter userRecipeListAdapter;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    MaterialCalendarView widget;
    public static String search_st = null;

    public static MeetingSearchFragment newInstance(String text) {
        MeetingSearchFragment mFragment = new MeetingSearchFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    View view;


    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment mFragment;
            FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
            mFragment = MainFragment2.newInstance("산");
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
            //getActivity().finish();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_meeting_search, container, false);

        TextInputLayout ed = (TextInputLayout) view.findViewById(R.id.ed);
        EditText ed2 = (EditText) view.findViewById(R.id.ed2);

        ed2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (ed2.getRight() - ed2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        search_st = ed.getEditText().getText().toString();
                        setupViews();
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
                    setupViews();

                    return true;

                }

                return false;

            }

        });

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment mFragment;
                    FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
                    mFragment = MainFragment2.newInstance("산");
                    mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                    //getActivity().finish();
                    return true;
                } else {
                    return false;
                }
            }
        });

        ButterKnife.bind(this, view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scroll);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.WHITE)
                        .sizeResId(R.dimen.divider)
                        .build());


        return view;


    }

    private void setupViews() {

        userRecipeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userRecipeListView.setHasFixedSize(false);
        userRecipeListAdapter = new UserRecipeListAdapter(Meeting.dummies(6));
        userRecipeListView.addItemDecoration(new DividerItemDecoration(getActivity()));
        userRecipeListView.setAdapter(userRecipeListAdapter);
    }


}
