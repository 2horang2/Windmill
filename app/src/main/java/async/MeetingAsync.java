package async;

import android.os.AsyncTask;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import data.models.MeetingList;
import data.models.Meeting_Temp;
import data.models.UPDATE;

/**
 * Created by man on 2015-09-04.
 */
class MeetingAsync extends AsyncTask<String, String, ArrayList<Meeting_Temp>> {
    boolean FRESH = false;
    private Source source;
    private int tr_count;

    @Override
    protected ArrayList<Meeting_Temp> doInBackground(String... strings) {
        return connectMeeting();
    }

    @Override
    protected void onPostExecute(ArrayList<Meeting_Temp> Mountains) {

    }

    private ArrayList<Meeting_Temp> connectMeeting() {

        if (UPDATE.meeting_update) {
            try {
                int i = 0;
                MeetingList.meetingList.clear();
                MeetingList.meetingList_view.clear();
                URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting.php");
                InputStream html = url.openStream();
                source = new Source(new InputStreamReader(html, "UTF-8"));

                Element table = source.getAllElements(HTMLElementName.TABLE).get(0);
                tr_count = table.getAllElements(HTMLElementName.TR).size();
                Element tr = null;

                MeetingList.meetingList = new ArrayList<Meeting_Temp>(tr_count);

                for (i = 0; i < tr_count; i++) {

                    tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                    Meeting_Temp tlqkf = new Meeting_Temp();
                    tlqkf.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                    tlqkf.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                    tlqkf.setMountain(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                    tlqkf.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                    tlqkf.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                    System.out.print("제목이 왜안돼3" + tlqkf.getTitle());
                    tlqkf.setSub(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                    tlqkf.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());
                    tlqkf.setUserimg(((Element) tr.getAllElements(HTMLElementName.TD).get(7)).getContent().toString());
                    tlqkf.setCategory(((Element) tr.getAllElements(HTMLElementName.TD).get(8)).getContent().toString());
                    tlqkf.setView(((Element) tr.getAllElements(HTMLElementName.TD).get(9)).getContent().toString());
                    tlqkf.setMember(((Element) tr.getAllElements(HTMLElementName.TD).get(10)).getContent().toString());
                    tlqkf.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(11)).getContent().toString());
                    tlqkf.setMemberImgs(((Element) tr.getAllElements(HTMLElementName.TD).get(12)).getContent().toString());
                    MeetingList.meetingList.add(tlqkf);
                    MeetingList.meetingList_view.add(tlqkf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            UPDATE.meeting_update = false;

        }
        return MeetingList.meetingList;

    }
}