package windmill.windmill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.MeetingList;
import data.models.MountainList;
import data.models.Mountain_Temp;

public class MountainFilter extends AppCompatActivity {

    @Bind(R.id.region_0)
    Button region_0;

    @Bind(R.id.region_1)
    Button region_1;
    @Bind(R.id.region_2)
    Button region_2;
    @Bind(R.id.region_3)
    Button region_3;

    @Bind(R.id.region_4)
    Button region_4;
    @Bind(R.id.region_5)
    Button region_5;
    @Bind(R.id.region_6)
    Button region_6;

    @Bind(R.id.to)
    EditText to;
    @Bind(R.id.from)
    EditText from;




    @Bind(R.id.reset)
    Button reset;

    @Bind(R.id.o)
    TextView o;
    @Bind(R.id.x)
    TextView x;

    int init_min = 0 ,init_max = 2200;
    int min,max;
    int last_members = 0;
    ArrayList<Boolean> regions = new ArrayList<Boolean>();

    ArrayList<Button> region_array = new ArrayList<Button>();
    ArrayList<String> regions_str = new ArrayList<String>();

    private void init() {

        region_array.clear();
        regions.clear();

        region_array.add(region_0);
        region_array.add(region_1);
        region_array.add(region_2);
        region_array.add(region_3);
        region_array.add(region_4);
        region_array.add(region_5);
        region_array.add(region_6);

        regions_str.add("");
        regions_str.add("경기");
        regions_str.add("강원");
        regions_str.add("충청");
        regions_str.add("전라");
        regions_str.add("경상");
        regions_str.add("제주도");

        min = init_min;
        max = init_max;


        to.setText(min+"");
        from.setText(max+"");
        for (int i = 0; i < 7; i++)
            regions.add(false);
        regions.set(0, true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_filter);

        ButterKnife.bind(this);
        init();
        getSupportActionBar().hide();

        o.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filter();
                Main2Activity.index=4;
                startActivity(new Intent(MountainFilter.this, Main2Activity.class));
                finish();
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MountainFilter.this, Main2Activity.class));
                finish();
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MeetingList.meetingList = MeetingList.meetingList_temp;
                MeetingList.meetingList_view = MeetingList.meetingList_view_temp;
                init();

                region_reset();

                region_array.get(0).setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                region_array.get(0).setTextColor(getResources().getColor(R.color.white));

            }
        });

        region_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_region(0);
            }
        });
        region_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_region(1);
            }
        });
        region_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_region(2);
            }
        });
        region_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_region(3);
            }
        });
        region_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_region(4);
            }
        });
        region_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_region(5);
            }
        });
        region_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                click_region(6);
            }
        });




    }


    private void filter(){


        MountainList.mountainList.clear();
        if(regions.get(0)&&min==init_min&&max==init_max) {
            MountainList.mountainList = MountainList.mountainList_temp;
        }else {

            min = Integer.valueOf(to.getText().toString());
            max = Integer.valueOf(from.getText().toString());
            //MountainList.MountainList_view.add(tmp);
            for (int i = 0; i < MountainList.mountainList_temp.size(); i++) {
                Mountain_Temp tmp = MountainList.mountainList_temp.get(i);
                for (int j = 1; j < regions.size(); j++) {
                    if (regions.get(j)) {
                        if ((tmp.getAddress().contains(regions_str.get(j)) || regions.get(0))
                                &&( Integer.valueOf(tmp.getCategory())>=min&&Integer.valueOf(tmp.getCategory())<=max)) {
                            MountainList.mountainList.add(tmp);
                            break;
                        }
                    }
                }
            }

        }
    }

    public void click_region(int i) {
        if (i == 0) {
            regions.set(0, true);
            region_reset();
            region_0.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
            region_0.setTextColor(getResources().getColor(R.color.white));
            region_0.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2),null,null,null);

        } else {

            region_all_reset();

            if(regions.get(i)==false) {
                regions.set(i, true);
                region_array.get(i).setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                region_array.get(i).setTextColor(getResources().getColor(R.color.white));
                region_array.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);

            }else{
                regions.set(i, false);
                region_array.get(i).setBackground(getResources().getDrawable(R.drawable.button_gui5));
                region_array.get(i).setTextColor(getResources().getColor(R.color.main_dark1));
                region_array.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
            }
        }
    }


    private void region_reset(){

        for(int i=1;i<region_array.size();i++){
            region_array.get(i).setBackground(getResources().getDrawable(R.drawable.button_gui5));
            region_array.get(i).setTextColor(getResources().getColor(R.color.main_dark1));
            region_array.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
            regions.set(i,false);
        }

    }
    private void region_all_reset(){
        regions.set(0, false);
        region_array.get(0).setBackground(getResources().getDrawable(R.drawable.button_gui5));
        region_array.get(0).setTextColor(getResources().getColor(R.color.main_dark1));
        region_array.get(0).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);


    }





    @Override

    public boolean onKeyDown (int KeyCode, KeyEvent event) {

        if (event.getAction()==KeyEvent.ACTION_DOWN) {

            if (KeyCode == KeyEvent.KEYCODE_BACK){

                startActivity(new Intent(MountainFilter.this, Main2Activity.class));
                finish();
                 } }

        return super.onKeyDown(KeyCode, event);

    }

}



