package windmill.windmill;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import java.util.ArrayList;
import java.util.StringTokenizer;

import data.models.PREF;
import data.models.UPDATE;

/**
 * Created by icelancer on 15. 2. 21..
 */
public class GcmIntentService extends GCMBaseIntentService {

    static String re_message=null;
    public static final String SEND_ID="721004047544";

    public static boolean msg = false;

    public GcmIntentService(){
        this(SEND_ID);
    }
    public GcmIntentService(String senderId){
        super(senderId);
    }


    private static void generateNotification(Context context, String message) {

        int icon = R.mipmap.ic_launcher;

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        context.getSystemService(Context.NOTIFICATION_SERVICE);


        String title = "도시말고 시골";
        Intent notificationIntent = new Intent(context, GcmIntentService.class);
        re_message=message;

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        UPDATE.mymeeting=true;
        String msg = re_message;
        StringTokenizer st = new StringTokenizer(msg, "|");
            System.out.print("제목이 왜안돼333"+msg);
            int cnt = st.countTokens();

            if(cnt>2){
                String idx,title2;
                title2 = st.nextToken();
            idx = st.nextToken();
            message = st.nextToken();

            Bundle extras = new Bundle();

            extras.putString("idx", idx);
            extras.putString("title", title2);
            Intent intent2 = new Intent(context, chat_exp.class);
            intent2.putExtras(extras);

            PendingIntent intent =  PendingIntent.getActivity(context, 0,intent2, PendingIntent.FLAG_UPDATE_CURRENT);

            notification.setLatestEventInfo(context, title, message, intent);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notification);
        }
        else{
            PendingIntent intent =  PendingIntent.getActivity(context, 0,null,0);
            notification.setLatestEventInfo(context, title, message, intent);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notification);
        }

        //sksmns23fkekqngkwl






        //chat_exp.ChatAsync.execute(null, null);
    }
    @Override
    protected void onError(Context arg0, String arg1) {

    }


    @Override
    protected void onMessage(Context context, Intent intent) {

        SharedPreferences pref; // 사용할 SharedPreferences 선언
        final String PREF_NAME = "prev_name"; // 사용할 SharedPreferences 이름
        pref = getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);

       // Vibrator vibrator = (Vibrator)getSystemService(context.VIBRATOR_SERVICE);
       // vibrator.vibrate(1000);
       // WakeUpScreen.acquire(getApplicationContext(), 10000);

        msg=true;
        String msg = intent.getStringExtra("msg");
        Log.e("getmessage", "getmessage:" + msg);

        if(msg!=null) {
            StringTokenizer st = new StringTokenizer(msg, "|");
            int cnt = st.countTokens();
            String idx = null;
            if (cnt > 2) {
                idx = st.nextToken();
                idx = st.nextToken();
                idx = st.nextToken();
            }
            if (pref.getString("alarm", "true").equals("true") && !idx.equals(PREF.id)) {
                generateNotification(context, msg);
            }
        }




    }

    @Override
    protected void onRegistered(Context context, String reg_id) {
         Log.e("키를 등록합니다", reg_id);
    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {

        Log.e("키를 제거합니다", "제거되었습니다.");

    }

}
