package ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import data.models.Msg;
import data.models.Fab;
import data.models.MeetingList;
import data.models.MeetingMyList;
import data.models.MeetingUserList;
import data.models.PREF;
import data.models.UPDATE;
import windmill.windmill.IntroActivity;
import windmill.windmill.Main2Activity;
import windmill.windmill.MeetingWrite;
import windmill.windmill.MessageRead;
import windmill.windmill.MessageWrite;
import windmill.windmill.R;


public class TakeMessage extends Fragment implements AdapterView.OnItemClickListener {
    private static final String EXTRA_USER = "user";

    View v;

    boolean setting = false;

    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public static TakeMessage newInstance(String text) {
        TakeMessage mFragment = new TakeMessage();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        SharedPreferences pref;
        final String PREF_NAME = "prev_name";
        pref = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);


        v = inflater.inflate(R.layout.fragment_takemessage, container, false);
        ex1_list = (ListView) v.findViewById(R.id.msg_lv);
        ex1_list.setOnItemClickListener(this);
        new MsgAsync().execute();
        setupFab();


        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Bundle extras = new Bundle();
        extras.putString("type", "받은쪽지함");
        extras.putString("person", list.get(position).getSend_person());
        extras.putString("img", list.get(position).getSend_person_img());
        extras.putString("text", list.get(position).getText());
        extras.putString("date", list.get(position).getDate());
        Intent intent = new Intent(getActivity(), MessageRead.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;
    ArrayList<Msg> list = new ArrayList<Msg>();

    class MsgAsync extends AsyncTask<String, String, ArrayList<Msg>> {

        @Override
        protected ArrayList<Msg> doInBackground(String... strings) {
            return connectMsg();
        }

        @Override
        protected void onPostExecute(ArrayList<Msg> Msgs) {
            adapter1 = new ViewAdapter(getActivity(), R.layout.fragment_takemessage, Msgs);
            ex1_list.setAdapter(adapter1);

        }
    }


    class ViewAdapter extends ArrayAdapter<Msg> {

        ArrayList<Msg> list;

        public ViewAdapter(Context context, int resource, ArrayList<Msg> objects) {
            super(context, resource, objects);
            list = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_msg, null);
            Msg msg = list.get(position);
            if (msg != null) {

                final TextView person = (TextView) convertView.findViewById(R.id.person);
                final TextView text = (TextView) convertView.findViewById(R.id.text);
                final TextView date = (TextView) convertView.findViewById(R.id.date);

                if (person != null)
                    person.setText("보낸 이 : " + msg.getSend_person());
                if (text != null)
                    text.setText(msg.getText());
                if (date != null)
                    date.setText(msg.getDate());

                ImageView img = (ImageView) convertView.findViewById(R.id.img);
                ImageLoader.getInstance().displayImage(msg.getSend_person_img(), img);

            }

            return convertView;
        }
    }


    private ArrayList<Msg> connectMsg() {


        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/msg_list.php?" + PREF.parameter);

            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Msg msgroom = new Msg();
                msgroom.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                msgroom.setPerson(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                msgroom.setSend_person(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                msgroom.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                msgroom.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                msgroom.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                msgroom.setSend_person_img(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());

                list.add(msgroom);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }

    private void setupFab() {
        Bundle extras = new Bundle();
        Fab fab = (Fab) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PREF.id == null) {
                    Toast.makeText(getActivity(), "로그인을 해주세요 ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), IntroActivity.class));
                } else {
                    extras.putString("to", null);
                    Intent intent = new Intent(getActivity(), MessageWrite.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }

            }
        });

    }


}
