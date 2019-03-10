package components;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.PREF;
import data.models.Reply;
import data.models.Review;
import windmill.windmill.IntroActivity;
import windmill.windmill.MeetingBoard;
import windmill.windmill.MeetingBoardDetail;
import windmill.windmill.MeetingBoardUpdate;
import windmill.windmill.MeetingUpdate;
import windmill.windmill.MountainBoard;
import windmill.windmill.MountainReviewUpdate;
import windmill.windmill.R;
import windmill.windmill.RecipeDetailActivity;

public class ReviewDetailView extends FrameLayout {

    @Bind(R.id.review_image)
    ImageView review_image;

    @Bind(R.id.user_image)
    ImageView user_image;

    @Bind(R.id.review_title)
    TextView review_title;
    @Bind(R.id.review_user)
    TextView review_user;
    @Bind(R.id.review_date)
    TextView review_date;
    @Bind(R.id.review_text)
    TextView review_text;



    @Bind(R.id.review_reply_et)
    EditText review_reply_et;
    @Bind(R.id.review_reply_btn)
    Button review_reply_btn;


    @Bind(R.id.rr_up_del)
    RelativeLayout rr_up_del;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.delete)
    Button delete;

    Review review;
    public ReviewDetailView(Context context) {
        super(context);
        setup();
    }

    public ReviewDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.view_review_detail, this);
        ButterKnife.bind(this);
    }

    ListView ex1_list;
    public void setReview(Review review) {
        /*
        if (!TextUtils.isEmpty(review.getImg())) {
            Picasso.with(getContext())
                    .load(review.getImg())
                    .into(review_image);
        }

*/
        ImageLoader.getInstance().displayImage(review.getImg(), review_image);


        ImageLoader.getInstance().displayImage(review.getUser_img(), user_image);



        if(review.getUser().equals(PREF.id)){
            rr_up_del.setVisibility(View.VISIBLE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("id", review.getId());
                extras.putString("idx", review.getIdx());
                extras.putString("title", review.getTitle());
                extras.putString("text", review.getText());
                extras.putString("img", review.getImg());
                Intent intent = new Intent(getContext(), MountainReviewUpdate.class);
                intent.putExtras(extras);
                getContext().startActivity(intent);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new review_del().execute(null, null);
            }
        });

        this.review = review;

        review_title.setText(review.getTitle());
        review_user.setText(review.getUser());
        review_date.setText(review.getDate());
        review_text.setText(review.getText());

        ex1_list = (ListView)findViewById(R.id.review_reply_lv);

        new ReplyAsync().execute(null, null);

        review_reply_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (PREF.id == null) {
                    Toast.makeText(getContext(), "로그인을 해주세요 ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                    getContext().startActivity(new Intent(getContext(),IntroActivity.class));
                }
                else new insertReply().execute(null, null);
            }
        });
    }


    private class review_del extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(getContext(), "",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/mountain_review_delete.php");
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("user", review.getUser()));
                nameValuePairs.add(new BasicNameValuePair("id", review.getId()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response = httpclient.execute(httppost);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            Activity activity = (Activity) getContext();
            Bundle extras = new Bundle();
            extras.putString("idx", review.getIdx());
            extras.putString("name", RecipeDetailActivity.m_name);
            Intent iIntent = new Intent(getContext(), MountainBoard.class);
            iIntent.putExtras(extras);
            activity.startActivity(iIntent);
            activity.finish();


        }
    }


    public boolean onKeyDown( int KeyCode, KeyEvent event )
    {

        if( event.getAction() == KeyEvent.ACTION_DOWN ){
            if( KeyCode == KeyEvent.KEYCODE_BACK ){

                Activity activity = (Activity) getContext();
                Bundle extras = new Bundle();
                extras.putString("idx", review.getIdx());
                extras.putString("name", RecipeDetailActivity.m_name);
                Intent iIntent = new Intent(getContext(), MountainBoard.class);
                iIntent.putExtras(extras);
                activity.startActivity(iIntent);
                activity.finish();
                return true;

            }
        }
        return super.onKeyDown( KeyCode, event );
    }



    ProgressDialog loagindDialog;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    private class insertReply extends AsyncTask<String, Void, Void> {
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            loagindDialog = ProgressDialog.show(getContext(), "",
                    "Please wait..", true, false);
        }

        @Override

        protected Void doInBackground(String... params) {
            try {

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/mountain_review_insert_reply.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("text", review_reply_et.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("idx", review.getId()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response = httpclient.execute(httppost);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            review_reply_et.setText("");
            new ReplyAsync().execute();
        }
    }


    class ReplyAsync extends AsyncTask<String, String, ArrayList<Reply>> {
        @Override
        protected ArrayList<Reply> doInBackground(String... strings) {
            return connectReply();
        }
        @Override
        protected void onPostExecute(ArrayList<Reply> Replys) {
            adapter1 = new ViewAdapter(getContext(), R.layout.view_review_detail, Replys);
            ex1_list.setAdapter(adapter1);
            setLisetViewHeight(ex1_list, list.size());
            // ex1_list.setSelection(list.size()-1);
        }
    }

    private void setLisetViewHeight(final ListView listView,int count){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        if(count == 0){
            count = listAdapter.getCount();
        }
        for (int i = 0; i < listAdapter.getCount(); i++) {
            if(i>=count)
                break;
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (count - 1));
        listView.setLayoutParams(params);
    }



    class ViewAdapter extends ArrayAdapter<Reply> {
        ArrayList<Reply> list;
        public ViewAdapter(Context context, int resource, ArrayList<Reply> objects) {
            super(context, resource, objects);
            list = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_reply, null);
            Reply reply = list.get(position);
            if (reply != null) {

                final TextView user = (TextView) convertView.findViewById(R.id.reply_user);
                final TextView text = (TextView) convertView.findViewById(R.id.reply_text);
                final TextView date = (TextView) convertView.findViewById(R.id.reply_date);


                if (user != null && reply.getUser()!=null)
                    user.setText(reply.getUser());
                if (text != null && reply.getText()!=null)
                    text.setText(reply.getText());
                if (date != null && reply.getDate()!=null)
                    date.setText(reply.getDate());

                ImageView img = (ImageView)convertView.findViewById(R.id.reply_img);
                if(reply.getUser_img()!=null && !reply.getUser_img().toString().equals("") ){
                    Picasso.with(img.getContext())
                            .load(reply.getUser_img())
                            .into(img);
                }


                /*
                *

                * */
            }
            return convertView;        }

    }


    ArrayList<Reply> list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;

    private ArrayList<Reply> connectReply() {

        list = new ArrayList<Reply>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain_review_reply_list.php");

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Reply reply= new Reply();
                reply.setUser_img(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                reply.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                reply.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                reply.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                reply.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                reply.setChatroomidx(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());


                if(reply.getChatroomidx().equals(review.getId()))
                    list.add(reply);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

    }


}
