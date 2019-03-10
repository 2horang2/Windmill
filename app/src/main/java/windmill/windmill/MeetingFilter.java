package windmill.windmill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.MeetingList;
import data.models.Meeting_Temp;

public class MeetingFilter extends AppCompatActivity {

    @Bind(R.id.member_0)
    Button member_0;
    @Bind(R.id.member_1)
    Button member_1;
    @Bind(R.id.member_2)
    Button member_2;
    @Bind(R.id.member_3)
    Button member_3;


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

    @Bind(R.id.reset)
    Button reset;

    @Bind(R.id.o)
    TextView o;
    @Bind(R.id.x)
    TextView x;


    int last_members=0;
    ArrayList<Boolean> members = new ArrayList<Boolean>();
    ArrayList<Boolean> regions = new ArrayList<Boolean>();
    ArrayList<Button> array = new ArrayList<Button>();
    ArrayList<String> regions_str = new ArrayList<String>();
    private void init(){

        regions_str.clear();
        array.clear();
        members.clear();
        regions.clear();

        array.add(region_0);
        array.add(region_1);
        array.add(region_2);
        array.add(region_3);
        array.add(region_4);
        array.add(region_5);
        array.add(region_6);

        regions_str.add("");
        regions_str.add("경기");
        regions_str.add("강원");
        regions_str.add("충청");
        regions_str.add("전라");
        regions_str.add("경상");
        regions_str.add("제주도");


        for(int i=0;i<4;i++)
            members.add(false);
        members.set(0, true);
        for(int i=0;i<7;i++)
            regions.add(false);
        regions.set(0, true);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_filter);

        getSupportActionBar().hide();

        ButterKnife.bind(this);
        init();


        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // MeetingList.meetingList = MeetingList.meetingList_temp;
                // MeetingList.meetingList_view = MeetingList.meetingList_view_temp;
                init();
                members_reset();
                region_reset();

                array.get(0).setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                array.get(0).setTextColor(getResources().getColor(R.color.white));
                members.set(0, true);
                member_0.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                member_0.setTextColor(getResources().getColor(R.color.white));
                regions.set(0, true);
            }
        });

        member_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                members_reset();
                member_0.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                member_0.setTextColor(getResources().getColor(R.color.white));
                members.set(0, true);

            }
        });
        member_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                members_reset();
                member_1.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                member_1.setTextColor(getResources().getColor(R.color.white));
                members.set(1, true);
            }
        });
        member_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                members_reset();
                member_2.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                member_2.setTextColor(getResources().getColor(R.color.white));
                members.set(2, true);

            }
        });
        member_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                members_reset();
                member_3.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                member_3.setTextColor(getResources().getColor(R.color.white));
                members.set(3, true);
            }
        });





        region_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regions.set(0, true);
                region_reset();
                region_0.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                region_0.setTextColor(getResources().getColor(R.color.white));
                region_0.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);

            }
        });
        region_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                region_all_reset();

                if (regions.get(1) == false) {
                    regions.set(1, true);
                    region_1.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                    region_1.setTextColor(getResources().getColor(R.color.white));
                    region_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);

                } else {
                    regions.set(1, false);
                    region_1.setBackground(getResources().getDrawable(R.drawable.button_gui5));
                    region_1.setTextColor(getResources().getColor(R.color.main_dark1));
                    region_1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                }
            }
        });
        region_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                region_all_reset();

                if (regions.get(2) == false) {
                    regions.set(2, true);
                    region_2.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                    region_2.setTextColor(getResources().getColor(R.color.white));
                    region_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                } else {
                    regions.set(2, false);
                    region_2.setBackground(getResources().getDrawable(R.drawable.button_gui5));
                    region_2.setTextColor(getResources().getColor(R.color.main_dark1));
                    region_2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                }
            }
        });
        region_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                region_all_reset();


                if (regions.get(3) == false) {
                    regions.set(3, true);
                    region_3.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                    region_3.setTextColor(getResources().getColor(R.color.white));
                    region_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                } else {
                    regions.set(3, false);
                    region_3.setBackground(getResources().getDrawable(R.drawable.button_gui5));
                    region_3.setTextColor(getResources().getColor(R.color.main_dark1));
                    region_3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                }
            }
        });

        region_4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                region_all_reset();


                if (regions.get(4) == false) {
                    regions.set(4, true);
                    region_4.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                    region_4.setTextColor(getResources().getColor(R.color.white));
                    region_4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                } else {
                    regions.set(4, false);
                    region_4.setBackground(getResources().getDrawable(R.drawable.button_gui5));
                    region_4.setTextColor(getResources().getColor(R.color.main_dark1));
                    region_4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                }
            }
        });
        region_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                region_all_reset();


                if (regions.get(5) == false) {
                    regions.set(5, true);
                    region_5.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                    region_5.setTextColor(getResources().getColor(R.color.white));
                    region_5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                } else {
                    regions.set(5, false);
                    region_5.setBackground(getResources().getDrawable(R.drawable.button_gui5));
                    region_5.setTextColor(getResources().getColor(R.color.main_dark1));
                    region_5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                }
            }
        });
        region_6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                region_all_reset();


                if (regions.get(6) == false) {
                    regions.set(6, true);
                    region_6.setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
                    region_6.setTextColor(getResources().getColor(R.color.white));
                    region_6.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                } else {
                    regions.set(6, false);
                    region_6.setBackground(getResources().getDrawable(R.drawable.button_gui5));
                    region_6.setTextColor(getResources().getColor(R.color.main_dark1));
                    region_6.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
                }
            }
        });



        o.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filter();
                Main2Activity.index = 1;
                startActivity(new Intent(MeetingFilter.this, Main2Activity.class));
                finish();
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Main2Activity.index = 1;
                startActivity(new Intent(MeetingFilter.this, Main2Activity.class));
                finish();
            }
        });
    }
    @Override

    public boolean onKeyDown (int KeyCode, KeyEvent event) {

        if (event.getAction()==KeyEvent.ACTION_DOWN) {

            if (KeyCode == KeyEvent.KEYCODE_BACK){
                Main2Activity.index = 1;
                startActivity(new Intent(MeetingFilter.this, Main2Activity.class));
                finish();
            } }

        return super.onKeyDown(KeyCode, event);

    }

    private void filter(){


        int min=0,max=0;
        if(members.get(0)){
            min=0;max=99;}
        else if(members.get(1)){
            min=0;max=10;
        }else if(members.get(2)){
            min=11;max=30;
        }else if(members.get(3)){
            min=31;max=99;
        }
        MeetingList.meetingList.clear();
        MeetingList.meetingList_view.clear();
        //MeetingList.meetingList_view.add(tmp);
        for(int i=0; i<MeetingList.meetingList_temp.size();i++){
            Meeting_Temp tmp = MeetingList.meetingList_temp.get(i);
            for(int j=0;j<regions.size();j++){
                if(regions.get(j)){
                   if((tmp.getSub().toString().contains(regions_str.get(j))||regions.get(0))&&(Integer.valueOf(tmp.getMember())>min&&Integer.valueOf(tmp.getMember())<max)){
                       MeetingList.meetingList.add(tmp);
                       break;
                   }
                }
            }
        }
        for(int i=0; i<MeetingList.meetingList_view_temp.size();i++){
            Meeting_Temp tmp = MeetingList.meetingList_view_temp.get(i);
            for(int j=0;j<regions.size();j++){
                if(regions.get(j)){
                    if((tmp.getSub().toString().contains(regions_str.get(j))||regions.get(0))&&(Integer.valueOf(tmp.getMember())>min&&Integer.valueOf(tmp.getMember())<max)){
                        MeetingList.meetingList_view.add(tmp);
                        break;
                    }
                }
            }
        }

    }

    private void members_reset(){
        member_0.setBackground(getResources().getDrawable(R.drawable.button_gui5));
        member_0.setTextColor(getResources().getColor(R.color.main_dark1));

        member_1.setBackground(getResources().getDrawable(R.drawable.button_gui5));
        member_1.setTextColor(getResources().getColor(R.color.main_dark1));

        member_2.setBackground(getResources().getDrawable(R.drawable.button_gui5));
        member_2.setTextColor(getResources().getColor(R.color.main_dark1));

        member_3.setBackground(getResources().getDrawable(R.drawable.button_gui5));
        member_3.setTextColor(getResources().getColor(R.color.main_dark1));

        for(int i=1;i<members.size();i++){
            members.set(i, false);
        }
        members.set(0, false);
    }


    private void region_reset(){
        regions.set(0,true);
        array.get(0).setBackground(getResources().getDrawable(R.drawable.button_gui5_sel2));
        array.get(0).setTextColor(getResources().getColor(R.color.white));
        array.get(0).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
        for(int i=1;i<array.size();i++){
            array.get(i).setBackground(getResources().getDrawable(R.drawable.button_gui5));
            array.get(i).setTextColor(getResources().getColor(R.color.main_dark1));
            array.get(i).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);
            regions.set(i,false);
        }

    }
    private void region_all_reset(){
            regions.set(0,false);
            array.get(0).setBackground(getResources().getDrawable(R.drawable.button_gui5));
            array.get(0).setTextColor(getResources().getColor(R.color.main_dark1));
            array.get(0).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mountain2), null, null, null);


    }
}
