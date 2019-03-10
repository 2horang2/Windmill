package adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import data.models.Mountain;
import data.models.MountainList;
import data.models.Mountain_Temp;
import windmill.windmill.R;
import windmill.windmill.RecipeDetailActivity;

/**
 * Created by hyun on 2015-08-15.
 */
public class MountainBestAdapter extends PagerAdapter {


    LayoutInflater inflater;
    int size;
    ArrayList<Mountain_Temp> tempList = new ArrayList<Mountain_Temp>(MountainList.mountainList_like.size());

    public MountainBestAdapter(LayoutInflater inflater, int size, ArrayList<Mountain_Temp> tempList) {

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


    public static Mountain dummy(Mountain_Temp meetingList) {
        Mountain dummy = new AutoValue_Mountain2(
                meetingList.getId(),
                meetingList.getImg(),
                meetingList.getName(),
                meetingList.getAddress(),
                meetingList.getText(),
                meetingList.getUser(),
                meetingList.getUser_img(),
                meetingList.getCategory(),
                meetingList.getLng(),
                meetingList.getLa(),
                meetingList.getLike(),
                meetingList.getHate(),
                meetingList.getMembers(),
                meetingList.getReviews()
        );
        return dummy;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // TODO Auto-generated method stub


        View view = null;

        view = inflater.inflate(R.layout.list_item_mountain_best, null);

        container.addView(view);

        ImageView img = (ImageView) view.findViewById(R.id.recipe_image);
        TextView title_text = (TextView) view.findViewById(R.id.title_text);
        ImageView user_image = (ImageView) view.findViewById(R.id.user_image);
        TextView user = (TextView) view.findViewById(R.id.user_name_text);
        TextView like = (TextView) view.findViewById(R.id.like);
        TextView hate = (TextView) view.findViewById(R.id.hate);
        TextView best = (TextView) view.findViewById(R.id.bset_index);

        best.setText("BEST" + (position + 1));

        if (!tempList.get(position).getImg().equals("")) {
            Picasso.with(view.getContext())
                    .load(tempList.get(position).getImg())
                    .into(img);
        }

        if (!tempList.get(position).getUser_img().equals("")) {
            Picasso.with(view.getContext())
                    .load(tempList.get(position).getUser_img())
                    .into(user_image);
        }

        if (!tempList.get(position).getUser().equals(""))
            user.setText(tempList.get(position).getUser());


        title_text.setText(tempList.get(position).getName());
        hate.setText(tempList.get(position).getHate());
        like.setText(tempList.get(position).getLike());


        Mountain temp = dummy(tempList.get(position));

        Picasso.with(img.getContext())
                .load(tempList.get(position).getImg())
                .into(img);
        img.setOnClickListener(v -> {
            //new Network_execcute().execute(null,null);
            Activity activity = (Activity) v.getContext();
            RecipeDetailActivity.launch(activity, temp, img, "");
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


        container.removeView((View) object);


    }


    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드

    @Override

    public boolean isViewFromObject(View v, Object obj) {

        // TODO Auto-generated method stub

        return v == obj;

    }
}
