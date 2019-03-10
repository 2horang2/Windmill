package windmill.windmill;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;


public class RemoteFetchService extends Service {

	private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	public static ArrayList<ListItem> listItemList;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/*
	 * Retrieve appwidget id from intent it is needed to update widget later
	 * initialize our AQuery class
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID))
			appWidgetId = intent.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);

		fetchDataFromWeb();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * method which fetches data(json) from web aquery takes params
	 * remoteJsonUrl = from where data to be fetched String.class = return
	 * format of data once fetched i.e. in which format the fetched data be
	 * returned AjaxCallback = class to notify with data once it is fetched
	 */
	private void fetchDataFromWeb() {
		processResult();
	}

	private void processResult() {
		populateListItem();
		populateWidget();
	}

	private void populateListItem() {
		SharedPreferences pref; // 사용할 SharedPreferences 선언
		final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름
		pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
		String id = pref.getString("id", "");


		SharedPreferences prefs = getSharedPreferences("mm", Context.MODE_MULTI_PROCESS);
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

	/**
	 * Method which sends broadcast to WidgetProvider
	 * so that widget is notified to do necessary action
	 * and here action == WidgetProvider.DATA_FETCHED
	 */
	private void populateWidget() {

		Intent widgetUpdateIntent = new Intent();
		widgetUpdateIntent.setAction(WidgetProvider.DATA_FETCHED);
		widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
		sendBroadcast(widgetUpdateIntent);

		this.stopSelf();
	}
}
