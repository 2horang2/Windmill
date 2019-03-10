
package ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.alertdialogpro.AlertDialogPro;
import com.etsy.android.grid.StaggeredGridView;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.MountainBestAdapter;
import adapter.RecipeListAdapter;
import data.models.Category;
import data.models.Channel;
import data.models.Fab;
import data.models.MeetingList;
import data.models.MeetingMyList;
import data.models.MeetingUserList;
import data.models.Meeting_Temp;
import data.models.Mountain;
import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.PREF;
import data.models.UPDATE;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;
import services.ChannelService;
import windmill.windmill.GcmIntentService;
import windmill.windmill.IntroActivity;
import windmill.windmill.Main2Activity;
import windmill.windmill.MeetingFilter;
import windmill.windmill.MeetingWrite;
import windmill.windmill.MountainFilter;
import windmill.windmill.MountainWrite;
import windmill.windmill.R;

public class MainFragment extends Fragment implements View.OnTouchListener {

    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    static boolean first = true;

    StaggeredGridView recipeListView;
    private RecipeListAdapter recipeListAdapter;
    private Subscription channelsSubscription = Subscriptions.empty();


    float xAtDown;
    float xAtUp;
    int count = 0;
    ViewFlipper flipper;

    boolean FRESH = false;
    private Source source;
    private int tr_count;
    String id;

    View v;


    ArrayList<Integer> SelectItems = new ArrayList<Integer>();
    ArrayList<Category> list_cafe;


    public static MainFragment newInstance(String text) {
        MainFragment mFragment = new MainFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_main, container, false);


        recipeListView = (StaggeredGridView) v.findViewById(R.id.channel_recipe_list);
        SharedPreferences pref; // 사용할 SharedPreferences 선언
        pref = getActivity().getSharedPreferences("prev_name", getActivity().MODE_PRIVATE);
        id = pref.getString("id", "");

        new PageAsync().execute(null, null);
        v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        setupFab();
        // UpdateThread whiteThread = new UpdateThread();
        //ㅊㅌㅍㅍㅊㅍㅊ whiteThread.start();


        return v;
    }


    public class UpdateThread extends Thread {
        public void run() {

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (UPDATE.mountain_update) {
                    new PageAsync().execute(null, null);
                }
            }

        }
    }

    @Override

    public void onDestroy() {


        super.onDestroy();
    }


    class PageAsync extends AsyncTask<String, String, ArrayList<Mountain_Temp>> {

        @Override

        protected ArrayList<Mountain_Temp> doInBackground(String... strings) {
            return connectPage();
        }

        @Override
        protected void onPostExecute(ArrayList<Mountain_Temp> Mountains) {
            setupViews();
        }
    }


    private MaterialSheetFab materialSheetFab, materialSheetFab2;
    //  private int statusBarColor;
    private int statusBarColor;

    private void setupFab() {

        Fab fab = (Fab) v.findViewById(R.id.fab);
        Fab fab2 = (Fab) v.findViewById(R.id.fab2);


        // Create material sheet FAB
        //materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PREF.id == null) {
                    Toast.makeText(getActivity(), "로그인을 해주세요 ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), IntroActivity.class));
                } else
                    startActivity(new Intent(getActivity(), MountainWrite.class));

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialog d = new SearchDialog(getActivity());
                d.show();

            }
        });


    }

    public String search_st = null, cate;

    public class SearchDialog extends Dialog {
        public SearchDialog(Context context) {
            super(context);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_search);

            Button o = (Button) findViewById(R.id.ok);
            Button x = (Button) findViewById(R.id.cancle);
            Spinner sp = (Spinner) findViewById(R.id.spinner);
            TextInputLayout ed = (TextInputLayout) findViewById(R.id.editText2);

            ArrayList<String> items = new ArrayList<String>();
            items.add("전체");
            items.add("산");
            items.add("글쓴이");
            items.add("지역");
            items.add("내용");

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cate = items.get(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    cate = null;

                    MountainList.mountainList = MountainList.mountainList_like;
                    setupViews();
                    MountainList.mountainList = MountainList.mountainList_temp;
                }
            });
            ArrayAdapter<String> aa = new ArrayAdapter(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, items);
            aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            sp.setAdapter(aa);

            o.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    search_st = ed.getEditText().getText().toString();
                    ArrayList<Mountain_Temp> tt = new ArrayList<Mountain_Temp>();
                    if (cate.equals("전체")) {
                        for (int i = 0; i < MountainList.mountainList.size(); i++) {
                            if (MountainList.mountainList.get(i).getAddress().contains(search_st) ||
                                    MountainList.mountainList.get(i).getUser().contains(search_st) ||
                                    MountainList.mountainList.get(i).getText().contains(search_st) ||
                                    MountainList.mountainList.get(i).getName().contains(search_st)) {

                                tt.add(MountainList.mountainList.get(i));

                            }
                        }
                    } else if (cate.equals("산")) {
                        for (int i = 0; i < MountainList.mountainList_temp.size(); i++) {
                            if (MountainList.mountainList.get(i).getName().contains(search_st)) {
                                tt.add(MountainList.mountainList.get(i));
                            }
                        }
                    } else if (cate.equals("글쓴이")) {
                        for (int i = 0; i < MountainList.mountainList.size(); i++) {
                            if (MountainList.mountainList.get(i).getUser().contains(search_st)) {
                                tt.add(MountainList.mountainList.get(i));
                            }
                        }
                    } else if (cate.equals("지역")) {
                        for (int i = 0; i < MountainList.mountainList.size(); i++) {
                            if (MountainList.mountainList.get(i).getAddress().contains(search_st)) {
                                tt.add(MountainList.mountainList.get(i));
                            }
                        }
                    } else if (cate == "내용") {
                        for (int i = 0; i < MountainList.mountainList.size(); i++) {
                            if (MountainList.mountainList.get(i).getText().contains(search_st)) {
                                tt.add(MountainList.mountainList.get(i));

                            }
                        }
                    }
                    btn = true;
                    if (tt.size() == 0) {
                        ed.getEditText().setError(ed.getEditText().getText() + "의 검색결과가 존재하지 않습니다ㅠ.ㅠ");
                        ed.getEditText().setText("");
                    } else {
                        MountainList.mountainList = tt;
                        setupViews();
                        MountainList.mountainList = MountainList.mountainList_temp;
                        dismiss();
                    }

                }

            });
            x.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    ArrayList<Mountain_Temp> list;

    private ArrayList<Mountain_Temp> connectPage() {
        list = new ArrayList<Mountain_Temp>();

        if (UPDATE.mountain_update) {
            try {
                int i = 0;
                FRESH = true;
                list.clear();

                URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain.php");
                InputStream html = url.openStream();
                source = new Source(new InputStreamReader(html, "UTF-8"));

                Element table = source.getAllElements(HTMLElementName.TABLE).get(0);
                tr_count = table.getAllElements(HTMLElementName.TR).size();
                Element tr = null;

                MountainList.mountainList = new ArrayList<Mountain_Temp>(tr_count);
                MountainList.mountainList_like = new ArrayList<Mountain_Temp>(tr_count);
                MountainList.mountainList_temp = new ArrayList<Mountain_Temp>(tr_count);
                MountainList.mountainList_reviews = new ArrayList<Mountain_Temp>(tr_count);
                for (i = 0; i < tr_count; i++) {

                    tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);
                    Mountain_Temp tlqkf = new Mountain_Temp();

                    tlqkf.setCategory(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                    tlqkf.setId(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                    tlqkf.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                    tlqkf.setName(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                    tlqkf.setAddress(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                    tlqkf.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                    tlqkf.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());
                    tlqkf.setUser_img(((Element) tr.getAllElements(HTMLElementName.TD).get(7)).getContent().toString());
                    tlqkf.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(8)).getContent().toString());
                    tlqkf.setLng((((Element) tr.getAllElements(HTMLElementName.TD).get(9)).getContent().toString()));
                    tlqkf.setLa((((Element) tr.getAllElements(HTMLElementName.TD).get(10)).getContent().toString()));
                    tlqkf.setLike((((Element) tr.getAllElements(HTMLElementName.TD).get(11)).getContent().toString()));
                    tlqkf.setHate((((Element) tr.getAllElements(HTMLElementName.TD).get(12)).getContent().toString()));
                    tlqkf.setMembers((((Element) tr.getAllElements(HTMLElementName.TD).get(13)).getContent().toString()));
                    tlqkf.setReviews((((Element) tr.getAllElements(HTMLElementName.TD).get(14)).getContent().toString()));

                    MountainList.mountainList.add(i, tlqkf);
                    MountainList.mountainList_temp.add(i, tlqkf);
                    MountainList.mountainList_like.add(i, tlqkf);
                    MountainList.mountainList_reviews.add(i, tlqkf);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            UPDATE.mountain_update = false;
        }
        sort();
        return MountainList.mountainList;
    }

    private void sort() {

        for (int i = 0; i <= MountainList.mountainList_like.size() - 2; i++) {
            for (int j = i + 1; j <= MountainList.mountainList_like.size() - 1; j++) {

                int i_total = Integer.parseInt(MountainList.mountainList_like.get(i).getLike()) - Integer.parseInt(MountainList.mountainList_like.get(i).getHate());
                int j_total = Integer.parseInt(MountainList.mountainList_like.get(j).getLike()) - Integer.parseInt(MountainList.mountainList_like.get(j).getHate());
                if (i_total < j_total) {

                    Mountain_Temp tmp = MountainList.mountainList_like.get(i);
                    MountainList.mountainList_like.set(i, MountainList.mountainList_like.get(j));
                    MountainList.mountainList_like.set(j, tmp);

                }
            }
        }

        for (int i = 0; i <= MountainList.mountainList_reviews.size() - 2; i++) {
            for (int j = i + 1; j <= MountainList.mountainList_reviews.size() - 1; j++) {

                int i_total = Integer.parseInt(MountainList.mountainList_reviews.get(i).getReviews());
                int j_total = Integer.parseInt(MountainList.mountainList_reviews.get(j).getReviews());
                if (i_total < j_total) {

                    Mountain_Temp tmp = MountainList.mountainList_reviews.get(i);
                    MountainList.mountainList_reviews.set(i, MountainList.mountainList_reviews.get(j));
                    MountainList.mountainList_reviews.set(j, tmp);

                }
            }
        }
    }


    private void setupViews() {

        channelsSubscription = AppObservable.bindActivity(getActivity(), new ChannelService().getList())
                .subscribe(this::setupViews);
    }

    boolean btn = false;


    private void setupViews(List<Channel> channels) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        if (first == true && btn == false) {


            View header = layoutInflater.inflate(R.layout.list_header_channel_recipe, null);
            recipeListView.addHeaderView(header);

            ViewPager vp = (ViewPager) header.findViewById(R.id.pager);
            MountainBestAdapter adapter = new MountainBestAdapter(getActivity().getLayoutInflater(), 3, MountainList.mountainList_like);
            vp.setAdapter(adapter);


            Button sort_like = (Button) header.findViewById(R.id.sort_like);
            Button sort_review = (Button) header.findViewById(R.id.sort_review);
            Button sort_new = (Button) header.findViewById(R.id.sort_new);
            Button sort_filter = (Button) header.findViewById(R.id.sort_filter);


            sort_filter.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), MountainFilter.class));
                    getActivity().finish();
                    Main2Activity.index = 4;
                }
            });

            sort_like.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    first = false;
                    btn = true;
                    MountainList.mountainList = MountainList.mountainList_like;
                    setupViews();
                    MountainList.mountainList = MountainList.mountainList_temp;

                    sort_like.setBackground(getResources().getDrawable(R.drawable.sort_left_on));
                    sort_review.setBackground(getResources().getDrawable(R.drawable.sort_center));
                    sort_new.setBackground(getResources().getDrawable(R.drawable.sort_right));

                    sort_new.setTextColor(getResources().getColor(R.color.main_dark1));
                    sort_like.setTextColor(getResources().getColor(R.color.white));
                    sort_review.setTextColor(getResources().getColor(R.color.main_dark1));

                }
            });
            sort_review.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    first = false;
                    btn = true;
                    MountainList.mountainList = MountainList.mountainList_reviews;
                    setupViews();
                    MountainList.mountainList = MountainList.mountainList_temp;

                    sort_review.setBackground(getResources().getDrawable(R.drawable.sort_center_on));
                    sort_like.setBackground(getResources().getDrawable(R.drawable.sort_left));
                    sort_new.setBackground(getResources().getDrawable(R.drawable.sort_right));

                    sort_new.setTextColor(getResources().getColor(R.color.main_dark1));
                    sort_like.setTextColor(getResources().getColor(R.color.main_dark1));
                    sort_review.setTextColor(getResources().getColor(R.color.white));
                    first = true;
                }
            });
            sort_new.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    first = false;
                    btn = true;
                    setupViews();

                    sort_new.setBackground(getResources().getDrawable(R.drawable.sort_right_on));
                    sort_like.setBackground(getResources().getDrawable(R.drawable.sort_left));
                    sort_review.setBackground(getResources().getDrawable(R.drawable.sort_center));

                    sort_new.setTextColor(getResources().getColor(R.color.white));
                    sort_like.setTextColor(getResources().getColor(R.color.main_dark1));
                    sort_review.setTextColor(getResources().getColor(R.color.main_dark1));
                    first = true;
                }
            });


        }


        recipeListAdapter = new RecipeListAdapter(getActivity(), channels.get(0).recipes());
        recipeListView.setAdapter(recipeListAdapter);
        btn = false;


    }


    public boolean onTouch(View v, MotionEvent event) {
        if (v != flipper)
            return false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xAtDown = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            xAtUp = event.getX();
            if (xAtDown > xAtUp) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.left_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.left_out));
                count++;
                if (count < 2)
                    flipper.showNext();
                else {
                    count--;
                }
            } else if (xAtDown < xAtUp) {
                flipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.right_in));
                flipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.right_out));
                count--;
                if (count > -1)
                    flipper.showPrevious();
                else {
                    count++;
                }
            }
        }
        return true;
    }


}
