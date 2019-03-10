package adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.Meeting;
import windmill.windmill.MeetingDetailActivity;
import windmill.windmill.R;

//import rejasupotaro.mds.activities.RecipeDetailActivity;

public class ScheduleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meeting> recipes;

    public ScheduleListAdapter(List<Meeting> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.mountain)
        TextView mountain;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.date)
        TextView date;

        public static ItemViewHolder create(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_schedule, parent, false);
            return new ItemViewHolder(itemView);
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        String idx;
        public void bind(Meeting meeting) {


            idx = meeting.idx();
            mountain.setText(meeting.mountain());
            title.setText(meeting.date()+" , "+meeting.member()+"명");
            date.setText(meeting.title());



            ImageLoader.getInstance().displayImage(meeting.img(), img);

           img.setOnClickListener(v -> {
                new Network_execcute().execute(null,null);
                Activity activity = (Activity) v.getContext();
                MeetingDetailActivity.launch(activity, meeting, img, "");
            });
        }


        private class Network_execcute extends AsyncTask<String, Void, Void>
        {
            @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override /* AsyncTask 실행 부분 */
            protected Void doInBackground(String... params)
            {
                try{

                    HttpPost httppost;
                    HttpResponse response;
                    HttpClient httpclient;
                    List<NameValuePair> nameValuePairs;

                    httpclient=new DefaultHttpClient();

                    httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/add_meeting_view.php");
                    nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("idx",idx));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
                    response=httpclient.execute(httppost);

                }catch(Exception e){
                    System.out.println("Exception : " + e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);
            }
        }
    }
}

