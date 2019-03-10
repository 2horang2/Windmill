package windmill.windmill;

import android.os.AsyncTask;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import data.models.MeetingUserList;
import data.models.PREF;

/**
 * Created by man on 2015-09-04.
 */
class UserMeetingAsync extends AsyncTask<String, String, ArrayList<String>> {

    boolean FRESH = false;
    private Source source;
    private int tr_count;

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        return connectUserMeeting();
    }

    protected void onPostExecute(ArrayList<String> Mountains) {
    }


    private ArrayList<String> connectUserMeeting() {
        try {
            int i = 0;
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting_user_list.php");
            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));
            Element table = source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);
                String idx = ((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString();
                String user = ((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString();
                if (user.equals(PREF.id))
                    MeetingUserList.meetinguserlist.add(idx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MeetingUserList.meetinguserlist;

    }
}