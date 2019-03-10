package windmill.windmill;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ExpandableAdapter;
import data.models.Notify;

public class SettingNotify extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_notify);
        ex1_list = (ListView)findViewById(R.id.list);
new NotifyAsync().execute();


        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("공지사항");
        }


    }


    ArrayList<Notify> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private View v;
    private Source source;

    class NotifyAsync extends AsyncTask<String, String, ArrayList<Notify>> {

        @Override
        protected ArrayList<Notify> doInBackground(String... strings) {
            return connectNotify();
        }

        @Override
        protected void onPostExecute(ArrayList<Notify> Notifys) {
            adapter1 = new ViewAdapter(SettingNotify.this, R.layout.activity_setting_notify, Notifys);
            ex1_list.setAdapter(adapter1);
            ex1_list.setSelection(list.size() - 1);
        }
    }

    class ViewAdapter extends ArrayAdapter<Notify> {

        ArrayList<Notify> list;

        public ViewAdapter(Context context, int resource, ArrayList<Notify> objects) {
            super(context, resource, objects);
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_notify, null);
            Notify notify = list.get(position);
            if (notify != null) {

                final TextView title = (TextView) convertView.findViewById(R.id.title);
                if (title != null)
                    title.setText(notify.getTitle());

                final TextView text = (TextView) convertView.findViewById(R.id.text);
                if (text != null)
                    text.setText(notify.getText());

                final TextView date = (TextView) convertView.findViewById(R.id.date);
                if (date != null)
                    date.setText(notify.getDate());

                final RelativeLayout rr = (RelativeLayout) convertView.findViewById(R.id.rr);
                if(notify.isFold()){
                    rr.setVisibility(View.VISIBLE);
                }else{
                    rr.setVisibility(View.GONE);
                }

                ImageView btn =  (ImageView) convertView.findViewById(R.id.btn);
                final RelativeLayout r = (RelativeLayout) convertView.findViewById(R.id.r);
                r.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if(notify.isFold()){
                            notify.setFold(false);
                            rr.setVisibility(View.GONE);
                            btn.setImageResource(R.drawable.right);
                        }else{
                            notify.setFold(true);
                            rr.setVisibility(View.VISIBLE);
                            btn.setImageResource(R.drawable.down);

                        }
                    }
                });




            }

            return convertView;
        }

    }


    private ArrayList<Notify> connectNotify() {

        list = new ArrayList<Notify>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/notify.php");

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;


            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Notify notify = new Notify();
                notify.setId(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                notify.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                notify.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                notify.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                notify.setFold(false);

                list.add(notify);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_setting_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
