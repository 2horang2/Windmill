package windmill.windmill;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import windmill.windmill.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import javax.annotation.Resource;

import data.models.Meeting_Temp;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 */
public class ListProvider implements RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        SharedPreferences pref; // 사용할 SharedPreferences 선언
        final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        String id = pref.getString("id", "");


        SharedPreferences prefs = context.getSharedPreferences("mm", Context.MODE_MULTI_PROCESS);
        int size = prefs.getInt("Status_size", 0);

        for (int i = 0; i < size; i++) {
            String str = prefs.getString("Status_" + i, null);
            ListItem listItem = new ListItem();
            StringTokenizer stringTokenizer;
            stringTokenizer = new StringTokenizer(str, "|");

            listItem.idx = stringTokenizer.nextToken().toString();
            listItem.img = stringTokenizer.nextToken().toString();
            listItem.title = stringTokenizer.nextToken().toString();
            listItem.mountain = stringTokenizer.nextToken().toString();
            listItem.date = stringTokenizer.nextToken().toString();
            listItemList.add(listItem);


        }

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    Map<Integer, Boolean> flags = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

    Bitmap mBitmap;
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_row);
        ListItem listItem = listItemList.get(position);



     //   Uri imgUri = Uri.parse(listItem.img);
        //remoteView.setImageViewUri(R.id.recipe_image, imgUri);
       // Intent intent = new Intent(context, Main2Activity.class);
       // PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);

     //   remoteView.setOnClickPendingIntent(R.id.recipe_image, pending);

        remoteView.setTextViewText(R.id.meeting_title, listItem.title);
        remoteView.setTextViewText(R.id.meeting_mountain_name, listItem.mountain);
        remoteView.setTextViewText(R.id.meeting_mountain_state, listItem.date);



       // rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        Intent i = new Intent();
        remoteView.setOnClickFillInIntent(R.id.recipe_image, i);


        //remoteView.setOnClickPendingIntent(R.id.meeting_title, pending);


        flags.put(position, false);
        handler.post(new Runnable() {
            @Override
            public void run() {
                ImageLoader.getInstance().loadImage(listItem.img, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        flags.put(position, true);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        flags.put(position, true);
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
                        mBitmap = bitmap;
                        flags.put(position, true);
                    }
                });
            }
        });

        while (!flags.get(position)) {
            try {
            Thread.sleep(10);}catch (Exception e){}
        }
        flags.put(position, false);
        if (mBitmap != null) {
            remoteView.setImageViewBitmap(R.id.recipe_image, mBitmap);
        } else {
            remoteView.setImageViewResource(R.id.recipe_image, R.drawable.camera);
        }
        mBitmap = null;



        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }


}
