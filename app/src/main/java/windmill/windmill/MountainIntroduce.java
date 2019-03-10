package windmill.windmill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.*;


public class MountainIntroduce extends AppCompatActivity implements AdapterView.OnItemClickListener{

    Button sort_all;
    Button sort_food;
    Button sort_sleep;

    ArrayList<Button> btns = new ArrayList<Button>();

    String mountain_idx,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_introduce);

        sort_all = (Button)findViewById(R.id.sort_all);
        sort_food = (Button)findViewById(R.id.sort_food);
        sort_sleep = (Button)findViewById(R.id.sort_sleep);

        btns.add(sort_all);
        btns.add(sort_food);
        btns.add(sort_sleep);

        strs.add("체험 마을");
        strs.add("맛집");
        strs.add("숙박");

        mountain_idx = getIntent().getStringExtra("idx");
        name = getIntent().getStringExtra("name");


        ex1_list = (ListView)findViewById(R.id.lv);
        ex1_list.setOnItemClickListener(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(name);

        }
        setupFab();
        new MountainIntroduceAsync().execute(null, null);
        int i;

        sort_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(0);
            }
        });
        sort_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(1);
            }
        });
        sort_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort(2);
            }
        });

    }

    ArrayList<String> strs = new ArrayList<String>();
    private void sort(int index){

        for(int i=0;i<btns.size();i++){
            if(i==index){
                switch (i){
                    case 0:
                        btns.get(i).setBackground(getResources().getDrawable(R.drawable.sort_left_on));
                        break;
                    case 1:
                        btns.get(i).setBackground(getResources().getDrawable(R.drawable.sort_center_on));
                        break;
                    case 2:
                        btns.get(i).setBackground(getResources().getDrawable(R.drawable.sort_right_on));
                        break;
                }
                btns.get(i).setTextColor(getResources().getColor(R.color.white));
            }else{
                switch (i){
                    case 0:
                        btns.get(i).setBackground(getResources().getDrawable(R.drawable.sort_left));
                        break;
                    case 1:
                        btns.get(i).setBackground(getResources().getDrawable(R.drawable.sort_center));
                        break;
                    case 2:
                        btns.get(i).setBackground(getResources().getDrawable(R.drawable.sort_right));
                        break;
                }
                btns.get(i).setTextColor(getResources().getColor(R.color.main_dark1));
            }
        }

        sort_list = new ArrayList<Food>();


        for(int j=0;j<list.size();j++){
            if(strs.get(index).equals(list.get(j).getType()))
                sort_list.add(list.get(j));
        }

        adapter1 = new ViewAdapter(MountainIntroduce.this, R.layout.activity_mountain_introduce, sort_list);
        ex1_list.setAdapter(adapter1);

    }

    private void setupFab() {
        Bundle extras = new Bundle();
        data.models.Fab fab = (data.models.Fab) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PREF.id == null) {
                    Toast.makeText(MountainIntroduce.this, "로그인을 해주세요 ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MountainIntroduce.this, IntroActivity.class));
                } else {
                    extras.putString("m_name", name);
                    extras.putString("mountain_idx", mountain_idx);
                    Intent intent = new Intent(MountainIntroduce.this, MountainIntroduceWrite.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Bundle extras = new Bundle();

        extras.putString("m_name", name);
        extras.putString("name", list.get(position).getName());
        extras.putString("address", list.get(position).getAdd());
        extras.putString("ph", list.get(position).getPh());
        extras.putString("url", list.get(position).getUrl());
        extras.putString("text", list.get(position).getText());
        extras.putString("type", list.get(position).getType());

               Intent intent = new Intent(MountainIntroduce.this, MountainIntroduceRead.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    ArrayList<Food> sort_list;
    ArrayList<Food> list = new ArrayList<Food>();
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;
    ArrayList<Food> tmp_list = new ArrayList<Food>();
    class MountainIntroduceAsync extends AsyncTask<String, String, ArrayList<Food>> {


        @Override
        protected ArrayList<Food> doInBackground(String... strings) {
            return connectFood();
        }
        @Override
        protected void onPostExecute(ArrayList<Food> Foods) {
            adapter1 = new ViewAdapter(MountainIntroduce.this, R.layout.activity_mountain_introduce, Foods);
            ex1_list.setAdapter(adapter1);
            sort(1);
        }
    }


    class ViewAdapter extends ArrayAdapter<Food> {

        public ViewAdapter(Context context, int resource, ArrayList<Food> objects) {
            super(context, resource, objects);
            tmp_list = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_food, null);
            Food food = tmp_list.get(position);
            if (food != null) {

                final TextView title = (TextView) convertView.findViewById(R.id.name);
                final TextView writer = (TextView) convertView.findViewById(R.id.add);

                if (title != null)
                    title.setText(food.getName());
                if (writer != null)
                    writer.setText(food.getAdd());


                TextView icon = (TextView) convertView.findViewById(R.id.type);
                icon.setText(food.getType());


            }
            return convertView;        }

    }

    private ArrayList<Food> connectFood() {
        try {

            list.clear();
            String para = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(mountain_idx, "UTF-8");
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/mountain_intorducce.php?" + para);
            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;



            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Food food = new Food();
                food.setType(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                food.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                food.setName(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                food.setAdd(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                food.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                food.setPh(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                food.setUrl(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());

                list.add(food);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

    }


}
