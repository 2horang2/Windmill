package ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.RecipeListAdapter;
import data.models.Channel;
import data.models.Like;
import data.models.MeetingMyList;
import data.models.Mountain;
import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.PREF;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;
import services.ChannelService;
import windmill.windmill.MeetingBoard;
import windmill.windmill.MeetingBoardDetail;
import windmill.windmill.MeetingDetailActivity;
import windmill.windmill.R;
import windmill.windmill.RecipeDetailActivity;


public class MainFragment4 extends Fragment implements AdapterView.OnItemClickListener {
    private static final String EXTRA_USER = "user";

    boolean first = true;

    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static MainFragment4 newInstance(String text) {
        MainFragment4 mFragment = new MainFragment4();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    private RecipeListAdapter mListAdapter;
    private Subscription channelsSubscription = Subscriptions.empty();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_main4, container, false);

        ex1_list = (ListView) v.findViewById(R.id.my_mountain_list);
        ActionBar ac = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ac != null){
            ac.setTitle("즐겨찾기");
        }
        new LikeAsync().execute();

        return v;
    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub


    }

    public static Mountain dummy(Mountain_Temp meetingList) {
        Mountain dummy = new AutoValue_Mountain3(
                meetingList.getId(),
                meetingList.getImg(),
                meetingList.getName(),
                meetingList.getAddress(),
                meetingList.getText(),
                meetingList.getUser(),
                meetingList.getUser_img(),
                meetingList.getCategory(),
                meetingList.getLng(),
                meetingList.getLa(),
                meetingList.getLike(),
                meetingList.getHate(),
                meetingList.getMembers(),
                meetingList.getReviews()
        );
        return dummy;
    }

    ArrayList<Like> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private View v;
    private Source source;

    class LikeAsync extends AsyncTask<String, String, ArrayList<Like>> {

        @Override
        protected ArrayList<Like> doInBackground(String... strings) {
            return connectLike();
        }

        @Override
        protected void onPostExecute(ArrayList<Like> Likes) {
            adapter1 = new ViewAdapter(getActivity(), R.layout.fragment_main4, Likes);
            ex1_list.setAdapter(adapter1);
            ex1_list.setSelection(list.size() - 1);
        }
    }

    class ViewAdapter extends ArrayAdapter<Like> {

        ArrayList<Like> list;

        public ViewAdapter(Context context, int resource, ArrayList<Like> objects) {
            super(context, resource, objects);
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            Like like = list.get(position);
            if (like != null) {

                LayoutInflater linf = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = linf.inflate(R.layout.list_item_like, null);


                final TextView date = (TextView) convertView.findViewById(R.id.date);
                if (date != null && !like.getAddress().equals(""))
                    date.setText(like.getDate());

                final TextView user = (TextView) convertView.findViewById(R.id.user);
                if (user != null && !like.getAddress().equals(""))
                    user.setText(like.getWriter());

                final TextView mountain = (TextView) convertView.findViewById(R.id.mountain);
                if (mountain != null && !like.getAddress().equals(""))
                    mountain.setText(like.getTitle());
                final TextView address = (TextView) convertView.findViewById(R.id.address);
                if (address != null && !like.getAddress().equals(""))
                    address.setText(like.getAddress());

                ImageView img = (ImageView) convertView.findViewById(R.id.img);
                if (img != null && like.getImg() != null && !like.getImg().equals("") && !like.getImg().equals(" ")) {
                    Picasso.with(getContext())
                            .load(like.getImg())
                            .into(img);
                }

                img.setOnClickListener(v -> {
                    Mountain temp = null;
                    for (int i = 0; i < MountainList.mountainList.size(); i++) {
                        if (MountainList.mountainList.get(i).getId().equals(list.get(position).getIdx())) {
                            temp = dummy(MountainList.mountainList.get(i));
                            break;
                        }
                    }

                    //new Network_execcute().execute(null,null);
                    Activity activity = (Activity) v.getContext();
                    RecipeDetailActivity.launch(activity, temp, img, "");
                });

            }

            return convertView;
        }

    }


    private ArrayList<Like> connectLike() {

        list = new ArrayList<Like>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain_user_list.php");

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;


            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Like like = new Like();
                like.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                like.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                like.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                like.setWriter(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                like.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                like.setAddress(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                like.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());

                if (like.getUser().equals(PREF.id))
                    list.add(like);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
