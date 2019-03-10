package async;

import android.os.AsyncTask;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import data.models.MountainList;
import data.models.Mountain_Temp;
import data.models.UPDATE;

/**
 * Created by man on 2015-09-04.
 */

class PageAsync extends AsyncTask<String, String, ArrayList<Mountain_Temp>> {
    boolean FRESH = false;
    private Source source;
    private int tr_count;
    @Override

    protected ArrayList<Mountain_Temp> doInBackground(String... strings) {
        return connectPage();
    }

    @Override
    protected void onPostExecute(ArrayList<Mountain_Temp> Mountains) {
    }


    ArrayList<Mountain_Temp> list;

    private ArrayList<Mountain_Temp> connectPage() {
        list = new ArrayList<Mountain_Temp>();

        if (UPDATE.mountain_update) {
            try {
                int i = 0;
                FRESH = true;
                list.clear();

                URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain.php");
                InputStream html = url.openStream();
                source = new Source(new InputStreamReader(html, "UTF-8"));

                Element table = source.getAllElements(HTMLElementName.TABLE).get(0);
                tr_count = table.getAllElements(HTMLElementName.TR).size();
                Element tr = null;

                MountainList.mountainList = new ArrayList<Mountain_Temp>(tr_count);
                MountainList.mountainList_like = new ArrayList<Mountain_Temp>(tr_count);
                MountainList.mountainList_temp = new ArrayList<Mountain_Temp>(tr_count);
                MountainList.mountainList_reviews = new ArrayList<Mountain_Temp>(tr_count);
                for (i = 0; i < tr_count; i++) {

                    tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);
                    Mountain_Temp tlqkf = new Mountain_Temp();

                    tlqkf.setCategory(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                    tlqkf.setId(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                    tlqkf.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                    tlqkf.setName(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                    tlqkf.setAddress(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                    tlqkf.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                    tlqkf.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());
                    tlqkf.setUser_img(((Element) tr.getAllElements(HTMLElementName.TD).get(7)).getContent().toString());
                    tlqkf.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(8)).getContent().toString());
                    tlqkf.setLng((((Element) tr.getAllElements(HTMLElementName.TD).get(9)).getContent().toString()));
                    tlqkf.setLa((((Element) tr.getAllElements(HTMLElementName.TD).get(10)).getContent().toString()));
                    tlqkf.setLike((((Element) tr.getAllElements(HTMLElementName.TD).get(11)).getContent().toString()));
                    tlqkf.setHate((((Element) tr.getAllElements(HTMLElementName.TD).get(12)).getContent().toString()));
                    tlqkf.setMembers((((Element) tr.getAllElements(HTMLElementName.TD).get(13)).getContent().toString()));
                    tlqkf.setReviews((((Element) tr.getAllElements(HTMLElementName.TD).get(14)).getContent().toString()));

                    MountainList.mountainList.add(i, tlqkf);
                    MountainList.mountainList_temp.add(i, tlqkf);
                    MountainList.mountainList_like.add(i, tlqkf);
                    MountainList.mountainList_reviews.add(i, tlqkf);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            UPDATE.mountain_update = false;
        }
        sort();
        return MountainList.mountainList;
    }

    private void sort() {

        for (int i = 0; i <= MountainList.mountainList_like.size() - 2; i++) {
            for (int j = i + 1; j <= MountainList.mountainList_like.size() - 1; j++) {

                int i_total = Integer.parseInt(MountainList.mountainList_like.get(i).getLike()) - Integer.parseInt(MountainList.mountainList_like.get(i).getHate());
                int j_total = Integer.parseInt(MountainList.mountainList_like.get(j).getLike()) - Integer.parseInt(MountainList.mountainList_like.get(j).getHate());
                if (i_total < j_total) {

                    Mountain_Temp tmp = MountainList.mountainList_like.get(i);
                    MountainList.mountainList_like.set(i, MountainList.mountainList_like.get(j));
                    MountainList.mountainList_like.set(j, tmp);

                }
            }
        }

        for (int i = 0; i <= MountainList.mountainList_reviews.size() - 2; i++) {
            for (int j = i + 1; j <= MountainList.mountainList_reviews.size() - 1; j++) {

                int i_total = Integer.parseInt(MountainList.mountainList_reviews.get(i).getReviews());
                int j_total = Integer.parseInt(MountainList.mountainList_reviews.get(j).getReviews());
                if (i_total < j_total) {

                    Mountain_Temp tmp = MountainList.mountainList_reviews.get(i);
                    MountainList.mountainList_reviews.set(i, MountainList.mountainList_reviews.get(j));
                    MountainList.mountainList_reviews.set(j, tmp);

                }
            }
        }
    }

}