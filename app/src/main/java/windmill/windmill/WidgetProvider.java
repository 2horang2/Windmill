package windmill.windmill;

import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

    /*
     * this method is called every 30 mins as specified on widgetinfo.xml
     * this method is also called on every phone reboot
     */
    public static final String DATA_FETCHED = "com.wordpress.laaptu.DATA_FETCHED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        /*int[] appWidgetIds holds ids of multiple instance of your widget
		 * meaning you are placing more than one widgets on your homescreen*/
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = updateWidgetListView(context,
                    appWidgetIds[i]);

            Intent clickIntent = new Intent(context, Intro2Activity.class);

            PendingIntent clickPI = PendingIntent.getActivity(context, 0,
                    clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.listViewWidget, clickPI);

            // 바탕화면 위젯은 별도의 프로세스로 동작되고 있으므로 직접적으로 접근할수 없고 RemoveViews 통해서 접근할 수 있음
        //    Intent intent_memo = new Intent(context, Intro2Activity.class);
         //   PendingIntent pendingIntent_memo= PendingIntent.getActivity(context, 0, intent_memo, 0);
           // remoteViews.setOnClickPendingIntent(R.id.Button01, pendingIntent_memo);



            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }






        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget, svcIntent);
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);







        return remoteViews;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(DATA_FETCHED)) {
            int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

    }


}
