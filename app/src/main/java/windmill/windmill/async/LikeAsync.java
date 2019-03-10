package windmill.windmill.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import data.models.Like;
import data.models.LikeList;
import data.models.Mountain;
import data.models.MountainList;
import data.models.PREF;
import data.models.UPDATE;
import windmill.windmill.R;
import windmill.windmill.RecipeDetailActivity;

/**
 * Created by jhm1283 on 2015-09-10.
 */
class LikeAsync extends AsyncTask<String, String, ArrayList<Like>> {

    @Override
    protected ArrayList<Like> doInBackground(String... strings) {
        return connectLike();
    }

    @Override
    protected void onPostExecute(ArrayList<Like> Likes) {
    }

    private int tr_count;
    private Source source;

    private ArrayList<Like> connectLike() {

        LikeList.likelist.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain_user_list.php");

            LikeList.likelist.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;


            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Like like = new Like();
                like.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                like.setUser(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                like.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                like.setWriter(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                like.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                like.setAddress(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                like.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());

                if (like.getUser().equals(PREF.id))
                    LikeList.likelist.add(like);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        UPDATE.like = false;

        return LikeList.likelist;

    }
}