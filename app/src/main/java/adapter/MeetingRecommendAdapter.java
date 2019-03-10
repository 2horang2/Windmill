package adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import data.models.Meeting;
import data.models.MeetingList;
import data.models.Meeting_Temp;
import windmill.windmill.MeetingDetailActivity;
import windmill.windmill.R;

public class MeetingRecommendAdapter extends PagerAdapter {


    LayoutInflater inflater;
    int size;
    ArrayList<Meeting_Temp> tempList = new ArrayList<Meeting_Temp>(MeetingList.meetingList.size());
    public MeetingRecommendAdapter(LayoutInflater inflater, int size,ArrayList<Meeting_Temp> tempList) {

        // TODO Auto-generated constructor stub
        //전달 받은 LayoutInflater를 멤버변수로 전달
        this.inflater = inflater;
        this.size = size;
        this.tempList = tempList;

    }


    public int getCount() {
        // TODO Auto-generated method stub
        return size; //이미지 개수 리턴(그림이 10개라서 10을 리턴)
    }


    public static Meeting dummy(Meeting_Temp meetingList) {
        Meeting dummy = new AutoValue_Meeting2(meetingList.getIdx(),
                meetingList.getImg(),
                meetingList.getMountain(),
                meetingList.getDate(),
                meetingList.getTitle(),
                meetingList.getSub(),
                meetingList.getUser(),
                meetingList.getUserimg(),
                meetingList.getCategory(),
                meetingList.getView(),
                meetingList.getMember(),
                meetingList.getText(),
                meetingList.getMemberImgs()
        );
        return dummy;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // TODO Auto-generated method stub


        View view = null;

        view = inflater.inflate(R.layout.list_item_meeting, null);

        TextView order = (TextView) view.findViewById(R.id.order);
        order.setText((position + 1) + " / " + size);
        container.addView(view);

        ImageView img = (ImageView)view.findViewById(R.id.recipe_image);
        TextView meeting_mountain_name = (TextView)view.findViewById(R.id.meeting_mountain_name);
        TextView meeting_mountain_state = (TextView)view.findViewById(R.id.meeting_mountain_state);
        TextView meeting_title = (TextView)view.findViewById(R.id.meeting_title);


        meeting_mountain_name.setText(tempList.get(position).getMountain());
        meeting_mountain_state.setText(tempList.get(position).getDate()+" , "+tempList.get(position).getMember()+"명");
        meeting_title.setText(tempList.get(position).getTitle());

        Meeting temp = dummy(tempList.get(position));




        ImageLoader.getInstance().displayImage(tempList.get(position).getImg(), img);

        //Picasso.with(img.getContext())
        //        .load(tempList.get(position).getImg())
       //         .into(img);
        img.setOnClickListener(v -> {
            //new Network_execcute().execute(null,null);
             Activity activity = (Activity) v.getContext();
             MeetingDetailActivity.launch(activity, temp, img, "");
        });


        return view;

    }


    //화면에 보이지 않은 View는파쾨를 해서 메모리를 관리함.

    //첫번째 파라미터 : ViewPager

    //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)

    //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {

        // TODO Auto-generated method stub


        //ViewPager에서 보이지 않는 View는 제거

        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시

        container.removeView((View) object);


    }


    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드

    @Override

    public boolean isViewFromObject(View v, Object obj) {

        // TODO Auto-generated method stub

        return v == obj;

    }

}


