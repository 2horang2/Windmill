package ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import adapter.ScheduleListAdapter;
import adapter.UserRecipeListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import components.DividerItemDecoration;
import data.models.Meeting;
import data.models.MeetingList;
import data.models.PREF;
import utils.EventDecorator;
import utils.MySelectorDecorator;
import windmill.windmill.MeetingDetailActivity;
import windmill.windmill.R;


public class MainFragment3 extends Fragment implements OnDateChangedListener{


    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    MaterialCalendarView widget;

    public static MainFragment3 newInstance(String text) {
        MainFragment3 mFragment = new MainFragment3();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }
    private ScheduleListAdapter scheduleListAdapter;
    View v;
    RecyclerView userRecipeListView;
    boolean fold = false;
    public static String user_date;
    String mm,dd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_main3, container, false);


        widget = (MaterialCalendarView)v.findViewById(R.id.calendarView2);
        userRecipeListView = (RecyclerView)v.findViewById(R.id.scroll);
        RelativeLayout aa = (RelativeLayout)v.findViewById(R.id.img);
        TextView aaa = (TextView)v.findViewById(R.id.text);
        userRecipeListView.setVisibility(View.GONE);
        //
        widget.setOnDateChangedListener(this);

        widget.addDecorator(new PrimeDayDisableDecorator());
        // Add a second decorator that explicitly enables days <= 10. This will work because
        //  decorators are applied in order, and the system allows re-enabling
        widget.addDecorator(new EnableOneToTenDecorator());

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar.getTime());


        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

        aa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(fold){
                    widget.setVisibility(View.VISIBLE);
                    aaa.setText("달력 접기");
                    aaa.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.fold_down), null);
                    fold= false;}
                else{
                    widget.setVisibility(View.GONE);
                    fold=true;
                    aaa.setText("달력 보기");
                    aaa.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.fold_up), null);
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.scroll);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        //  .color(R.color.transparent)
                        .sizeResId(R.dimen.divider)
                        .build());
        // setupViews();

        return v;
    }


    @Override
    public void onDateChanged(@NonNull MaterialCalendarView widget, CalendarDay date) {
        //  textView.setText(FORMATTER.format(date.getDate()));

        int m = date.getMonth()+1;



        mm = m+"";
        dd = date.getDay()+"";

        user_date = date.getYear()+"/"+mm+"/"+dd;
        //btn.setText(date.getYear()+"/"+(date.getMonth()+1)+"/"+date.getDay());

        userRecipeListView.setVisibility(View.VISIBLE);

        userRecipeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userRecipeListView.setHasFixedSize(false);
        scheduleListAdapter = new ScheduleListAdapter(Meeting.dummies(Index.DATE_INDEX));
        userRecipeListView.addItemDecoration(new DividerItemDecoration(getActivity()));
        userRecipeListView.setAdapter(scheduleListAdapter);
    }


    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {


            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);

            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < MeetingList.meetingList.size(); i++) {

                String tmp_date = MeetingList.meetingList.get(i).getDate();
                StringTokenizer st = new StringTokenizer(tmp_date, "/");

                int y,d,m;
                if(st.countTokens()>2) {
                    y = Integer.parseInt(st.nextToken());
                    m = Integer.parseInt(st.nextToken());
                    d = Integer.parseInt(st.nextToken());
                }else{
                    y = 0;
                    m = 1;
                    d = 0;
                }

                CalendarDay day = new CalendarDay(y,m-1,d);
                dates.add(day);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            widget.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }

    private static class PrimeDayDisableDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return PRIME_TABLE[day.getDay()];
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }

        private static boolean[] PRIME_TABLE = {
                true,  // 0?
                true,
                true, // 2
                true, // 3
                true,
                true, // 5
                true,
                true, // 7
                false,
                false,
                false,
                false, // 11
                false,
                false, // 13
                false,
                false,
                false,
                false, // 17
                false,
                false, // 19
                false,
                false,
                false,
                false, // 23
                false,
                false,
                false,
                false,
                false,
                false, // 29
                false,
                false, // 31
                false,
                false,
                false, //PADDING
        };
    }

    private static class EnableOneToTenDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.getDay() <= 10;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(false);
        }
    }

}
