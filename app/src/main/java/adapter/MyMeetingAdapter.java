package adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.Meeting;
import data.models.PREF;
import data.models.UPDATE;
import ui.fragment.MainFragment5;
import windmill.windmill.MeetingDetailActivity;
import windmill.windmill.R;

public class MyMeetingAdapter extends BindableAdapter2<Meeting> {
    public MyMeetingAdapter(Context context, List<Meeting> recipes) {
        super(context, recipes);
    }

    static class ViewHolder {
        @Bind(R.id.recipe_image)
        ImageView recipeImageView;
        @Bind(R.id.remove)
        ImageView remove;

        @Bind(R.id.meeting_title)
        TextView meeting_title;
        @Bind(R.id.meeting_mountain_name)
        TextView meeting_mountain_name;

        @Bind(R.id.meeting_mountain_state)
        TextView meeting_mountain_state;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.list_item_my_meeting, null, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

String meeting_idx;
    @Override
    public void bindView(Meeting meeting, int position, View view) {
        final ViewHolder holder = (ViewHolder) view.getTag();


       // Picasso.with(view.getContext())
      //          .load(meeting.img())
      //          .into(holder.recipeImageView);
        ImageView remove = (ImageView) view.findViewById(R.id.remove);
        if(MainFragment5.setting ==true) {

            remove.setVisibility(View.VISIBLE);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meeting_idx = meeting.idx();
                    new withdraw().execute();
                }
            });
        }else  remove.setVisibility(View.GONE);

        holder.meeting_mountain_state.setText(meeting.date());
        holder.meeting_mountain_name.setText(meeting.mountain());
        holder.meeting_title.setText(meeting.title());

        ImageLoader.getInstance().displayImage(meeting.img(), holder.recipeImageView);

        view.setOnClickListener(v -> {
            Activity activity = (Activity) v.getContext();
            MeetingDetailActivity.launch(activity, meeting, holder.recipeImageView, "");
        });
    }

    ProgressDialog loagindDialog;
    private class withdraw extends AsyncTask<String, Void, Void> {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute() {

            loagindDialog = ProgressDialog.show(getContext(), "",
                    "Please wait..", true, false);
            super.onPreExecute();
        }

        @Override /* AsyncTask 실행 부분 */
        protected Void doInBackground(String... params) {
            try {
                HttpPost httppost;
                HttpResponse response;
                HttpClient httpclient;
                List<NameValuePair> nameValuePairs;

                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://cmcm1284.cafe24.com/windmill/meeting_withdraw.php");
                nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("reg_id", null));
                nameValuePairs.add(new BasicNameValuePair("sw", "true"));
                nameValuePairs.add(new BasicNameValuePair("idx", meeting_idx));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String re = httpclient.execute(httppost, responseHandler);


            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            loagindDialog.dismiss();
            UPDATE.mymeeting = true;
            UPDATE.meeting_update = true;
        }
    }
}
