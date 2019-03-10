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
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import data.models.Board;
import data.models.PREF;


public class MeetingBoard extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String chatroom_idx,master,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatroom_idx = getIntent().getStringExtra("idx");
        master = getIntent().getStringExtra("master");
        name = getIntent().getStringExtra("name");

        setContentView(R.layout.activity_meeting_board);
        ex1_list = (ListView)findViewById(R.id.board_lv);
        ex1_list.setOnItemClickListener(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(name);


        }

       new BoardAsync().execute(null, null);

        Button board_write = (Button)findViewById(R.id.board_write);
        board_write.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (PREF.id == null) {
                    Toast.makeText(MeetingBoard.this, "로그인을 해주세요 ㅠ_ㅠ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MeetingBoard.this, IntroActivity.class));
                }
                else {
                    Bundle extras = new Bundle();
                    extras.putString("idx", chatroom_idx);
                    extras.putString("master", master);
                    Intent intent = new Intent(MeetingBoard.this, MeetingBoardWrite.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                    finish();
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

        extras.putString("idx", list.get(position).getIdx());
        extras.putString("c_idx", list.get(position).getChatroom_idx());
        extras.putString("title", list.get(position).getTitle());
        extras.putString("text", list.get(position).getText());
        extras.putString("date", list.get(position).getDate());
        extras.putString("writer", list.get(position).getWriter());
        extras.putString("cate", list.get(position).getCate());
        extras.putString("user", list.get(position).getWriter());
        extras.putString("master", master);

        extras.putString("img", list.get(position).getImg());
        extras.putString("user_image", list.get(position).getUserimg());

        Intent intent = new Intent(MeetingBoard.this, MeetingBoardDetail.class);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }

    ArrayList<Board> list;
    private ListView ex1_list;
    private ViewAdapter adapter1;
    private int tr_count;
    private Source source;

    class BoardAsync extends AsyncTask<String, String, ArrayList<Board>> {
        @Override
        protected ArrayList<Board> doInBackground(String... strings) {
            return connectBoard();
        }
        @Override
        protected void onPostExecute(ArrayList<Board> Boards) {
            adapter1 = new ViewAdapter(MeetingBoard.this, R.layout.activity_meeting_board, Boards);
            ex1_list.setAdapter(adapter1);
            ex1_list.setSelection(list.size()-1);
        }
    }

    class ViewAdapter extends ArrayAdapter<Board> {
        ArrayList<Board> list;
        public ViewAdapter(Context context, int resource, ArrayList<Board> objects) {
            super(context, resource, objects);
            list = objects;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.list_item_board, null);
            Board board = list.get(position);
            if (board != null) {

                final TextView cate = (TextView) convertView.findViewById(R.id.board_cate);
                final TextView title = (TextView) convertView.findViewById(R.id.board_title);
                final TextView writer = (TextView) convertView.findViewById(R.id.board_writer);
                final TextView date = (TextView) convertView.findViewById(R.id.board_date);

                if (title != null)
                    title.setText(board.getTitle());
                if (cate != null)
                    cate.setText(" [ "+board.getCate()+" ] ");
                if (writer != null)
                    writer.setText(board.getWriter());
                if (date != null)
                    date.setText(board.getDate());

                ImageView img = (ImageView) convertView.findViewById(R.id.user_image);

                ImageLoader.getInstance().displayImage(board.getUserimg(), img);

            }
            return convertView;        }

    }

//"http://cmcm1284.cafe24.com/windmill/meeting_gallery.php?id="+PREF.id

    private ArrayList<Board> connectBoard() {

        list = new ArrayList<Board>();
        list.clear();
        try {
            URL url = new URL("http://cmcm1284.cafe24.com/windmill/meeting_board_list.php");

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
                if(chatroom_idx.equals(board.getChatroom_idx()))
                    list.add(board);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meeting_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
