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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

import data.models.Board;
import data.models.Galleries;
import data.models.GalleryItem;
import data.models.PREF;


public class MeetingGallery extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(PREF.id!=null) {
            setContentView(R.layout.activity_meeting_gallery);
            ex1_list = (ListView) findViewById(R.id.gallery_lv);
            ex1_list.setOnItemClickListener(this);

            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setTitle("갤러리");
            }

            new BoardAsync().execute(null, null);
        }else{
            setContentView(R.layout.activity_meeting_gallery_none);

            Button aa = (Button)findViewById(R.id.chat_login);
            aa.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent iIntent = new Intent(MeetingGallery.this, IntroActivity.class);
                    startActivity(iIntent);
                    finish();
                    Main2Activity.index=5;
                }
            });
        }

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Bundle extras = new Bundle();

        extras.putString("idx", list.get(position).getIdx());
        extras.putString("c_idx", list.get(position).getChatroom_idx());
        extras.putString("title", list.get(position).getTitle());
        extras.putString("text", list.get(position).getText());
        extras.putString("date", list.get(position).getDate());
        extras.putString("writer", list.get(position).getWriter());
        extras.putString("cate", list.get(position).getCate());
        extras.putString("user", list.get(position).getWriter());
        //extras.putString("master", master);
        Intent intent = new Intent(MeetingGallery.this, MeetingBoardDetail.class);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }

    ArrayList<Board>  list = new ArrayList<Board>();
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;


    private ListView ex2_list;
    private ViewAdapter2 adapter2;

    String c_date = null;
    int index=0;

    class BoardAsync extends AsyncTask<String, String, ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(String... strings) {
            return connectBoard();
        }
        @Override
        protected void onPostExecute(ArrayList<GalleryItem> Boards) {
            adapter1 = new ViewAdapter(MeetingGallery.this, R.layout.activity_meeting_gallery, Boards);
            ex1_list.setAdapter(adapter1);
            //ex1_list.setSelection(list.size()-1);
        }
    }

    class ViewAdapter extends ArrayAdapter<GalleryItem> {
        ArrayList<GalleryItem> list_gallery;
        public ViewAdapter(Context context, int resource, ArrayList<GalleryItem> objects) {
            super(context, resource, objects);
            list_gallery = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.activity_meeting_gallery_date, null);
            GalleryItem board = list_gallery.get(position);
            if (board != null) {


                StringTokenizer aa = new StringTokenizer(board.getDate(),"-");
                String y = aa.nextToken();
                String m = aa.nextToken();
                String d = aa.nextToken();

                final TextView date = (TextView) convertView.findViewById(R.id.date);
                if (date != null)
                    date.setText(d);

                final TextView mm = (TextView) convertView.findViewById(R.id.month);
                if (mm != null)
                    mm.setText(m+"월");

                final TextView yy = (TextView) convertView.findViewById(R.id.year);
                if (yy != null)
                    yy.setText(y);

                index = position;
                ex2_list = (ListView) convertView.findViewById(R.id.gallery_lv_date);


                ggg_list.clear();
                try {

                    for (int i = 0; i < galleries.get(index).getIndex().size() ; i++) {
                        Galleries gallery = new Galleries();
                        gallery.setPosition(index);
                        gallery.setImg1(list.get(galleries.get(index).getIndex().get(i++)).getImg());
                        if(i==galleries.get(index).getIndex().size())
                            gallery.setImg2("null");
                        else
                            gallery.setImg2(list.get(galleries.get(index).getIndex().get(i++)).getImg());
                        if(i==galleries.get(index).getIndex().size())
                            gallery.setImg3("null");
                        else
                            gallery.setImg3(list.get(galleries.get(index).getIndex().get(i++)).getImg());
                        ggg_list.add(gallery);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //ArrayList<Galleries>
                adapter2 = new ViewAdapter2(MeetingGallery.this, R.layout.activity_meeting_gallery_date, ggg_list);
                ex2_list.setAdapter(adapter2);



            }
            return convertView;
        }

    }

//ArrayList<Board>  list = new ArrayList<Board>();
    private ArrayList<GalleryItem> connectBoard() {


        list.clear();
        try {
            String parameter = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(PREF.id, "UTF-8");
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting_gallery.php?"+ parameter);

            //URL url = new URL(aa);
            list.clear();

            InputStream html = url.openStream();
            source = new Source(new InputStreamReader(html, "UTF-8"));

            Element table = (Element) source.getAllElements(HTMLElementName.TABLE).get(0);
            tr_count = table.getAllElements(HTMLElementName.TR).size();
            Element tr = null;

            for (int i = 0; i < tr_count; i++) {
                tr = (Element) table.getAllElements(HTMLElementName.TR).get(i);

                Board board= new Board();
                board.setChatroom_idx(((Element) tr.getAllElements(HTMLElementName.TD).get(0)).getContent().toString());
                board.setCate(((Element) tr.getAllElements(HTMLElementName.TD).get(1)).getContent().toString());
                board.setTitle(((Element) tr.getAllElements(HTMLElementName.TD).get(2)).getContent().toString());
                board.setWriter(((Element) tr.getAllElements(HTMLElementName.TD).get(3)).getContent().toString());
                board.setDate(((Element) tr.getAllElements(HTMLElementName.TD).get(4)).getContent().toString());
                board.setView(((Element) tr.getAllElements(HTMLElementName.TD).get(5)).getContent().toString());
                board.setText(((Element) tr.getAllElements(HTMLElementName.TD).get(6)).getContent().toString());
                board.setIdx(((Element) tr.getAllElements(HTMLElementName.TD).get(7)).getContent().toString());
                board.setUserimg(((Element) tr.getAllElements(HTMLElementName.TD).get(8)).getContent().toString());
                board.setImg(((Element) tr.getAllElements(HTMLElementName.TD).get(9)).getContent().toString());
                list.add(board);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        galleries.clear();
        int i  = 0;
        if(list.size()>0) {
            String date = list.get(i).getDate();
            GalleryItem tmp = new GalleryItem();
            ArrayList<Integer> index_tmp = new ArrayList<Integer>();

            for (i = 0; i < list.size(); i++) {

                if (date.equals(list.get(i).getDate())) {
                    tmp.setDate(date);
                    index_tmp.add(i);
                } else {
                    tmp.setIndex(index_tmp);
                    galleries.add(tmp);

                    tmp = new GalleryItem();
                    index_tmp = new ArrayList<Integer>();
                    date = list.get(i).getDate();
                    i--;
                }
            }

            tmp.setIndex(index_tmp);
            galleries.add(tmp);

        }
        return galleries;

    }

    ArrayList<GalleryItem> galleries = new ArrayList<GalleryItem>();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<Galleries>  list_g = new ArrayList<Galleries>();


    class ViewAdapter2 extends ArrayAdapter<Galleries> {
        ArrayList<Galleries> list_g;
        public ViewAdapter2(Context context, int resource, ArrayList<Galleries> objects) {
            super(context, resource, objects);
            list_g = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_metting_gallery_photo, null);
            final Galleries g = list_g.get(position);
            if (list_g != null) {

                ImageView img1 = (ImageView)convertView.findViewById(R.id.i1);
                if(g.getImg1().equals("null"))
                    ImageLoader.getInstance().displayImage(g.getImg1(), img1);
                else {
                    ImageLoader.getInstance().displayImage(g.getImg1(), img1);
                }

                ImageView img2 = (ImageView)convertView.findViewById(R.id.i2);
                if(g.getImg2().equals("null"))
                    img2.setVisibility(View.GONE);
                else {
                    ImageLoader.getInstance().displayImage(g.getImg2(), img2);
                }

                ImageView img3 = (ImageView)convertView.findViewById(R.id.i3);
                if(g.getImg3().equals("null"))
                    img3.setVisibility(View.GONE);
                else {
                    ImageLoader.getInstance().displayImage(g.getImg3(), img3);
                }



                img1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        move(galleries.get(g.getPosition()).getIndex().get(0));
                    }
                });
                img2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        move(galleries.get(g.getPosition()).getIndex().get(1));
                    }
                });

                img3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        move(galleries.get(g.getPosition()).getIndex().get(2));
                    }
                });




            }
            return convertView;
        }

    }

    ArrayList<Galleries> ggg_list = new ArrayList<Galleries>();


    public void move(int position){
        Bundle extras = new Bundle();

        extras.putString("idx", list.get(position).getIdx());
        extras.putString("c_idx", list.get(position).getChatroom_idx());
        extras.putString("title", list.get(position).getTitle());
        extras.putString("text", list.get(position).getText());
        extras.putString("date", list.get(position).getDate());
        extras.putString("writer", list.get(position).getWriter());
        extras.putString("cate", list.get(position).getCate());
        extras.putString("user", list.get(position).getWriter());
        //extras.putString("master", master);
        Intent intent = new Intent(MeetingGallery.this, MeetingBoardDetail.class);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }
}
