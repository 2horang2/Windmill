package windmill.windmill;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import data.models.Mountain;
import data.models.MountainList;
import windmill.windmill.Intro2Activity;
import windmill.windmill.IntroActivity;
import windmill.windmill.R;
import windmill.windmill.RecipeDetailActivity;

/**
 * Created by hyun on 2015-08-15.
 */
public class initAdapter extends PagerAdapter {


    LayoutInflater inflater;
    int size;
    ArrayList<init> tempList = new ArrayList<init>(6);

    public initAdapter(LayoutInflater inflater, int size, ArrayList<init> tempList) {

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





    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // TODO Auto-generated method stub


        View view = null;

        if(position!=size-1) {

            view = inflater.inflate(R.layout.list_item_init, null);

            container.addView(view);

            RelativeLayout title_rl = (RelativeLayout) view.findViewById(R.id.title_rl);
            title_rl.setVisibility(View.VISIBLE);
            Animation ani = AnimationUtils.loadAnimation(view.getContext(), R.anim.translate_down);
            title_rl.startAnimation(ani);


            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            TextView title2 = (TextView) view.findViewById(R.id.title2);

            RelativeLayout all = (RelativeLayout) view.findViewById(R.id.all);


            ImageView t1 = (ImageView) view.findViewById(R.id.t1);
            ImageView t2 = (ImageView) view.findViewById(R.id.t2);
            ImageView t3 = (ImageView) view.findViewById(R.id.t3);
            ImageView t4 = (ImageView) view.findViewById(R.id.t4);
            ImageView t5 = (ImageView) view.findViewById(R.id.t5);
            ImageView t6 = (ImageView) view.findViewById(R.id.t6);
            ArrayList<ImageView> Arr = new ArrayList<ImageView>();
            Arr.add(t1);
            Arr.add(t2);
            Arr.add(t3);
            Arr.add(t4);
            Arr.add(t5);
            Arr.add(t6);

            Arr.get(position).setBackgroundResource(R.drawable.circle);
            title.setText(tempList.get(position).getTitle());
            title2.setText(tempList.get(position).getTitle2());
            img.setImageDrawable(tempList.get(position).getImg());


        }else{

            view = inflater.inflate(R.layout.list_item_init_last, null);

            container.addView(view);

            RelativeLayout title_rl = (RelativeLayout) view.findViewById(R.id.title_rl);
            title_rl.setVisibility(View.VISIBLE);
            Animation ani = AnimationUtils.loadAnimation(view.getContext(), R.anim.translate_down);
            title_rl.startAnimation(ani);


            TextView title = (TextView) view.findViewById(R.id.title);
            TextView title2 = (TextView) view.findViewById(R.id.title2);

            RelativeLayout all = (RelativeLayout) view.findViewById(R.id.all);


            ImageView t1 = (ImageView) view.findViewById(R.id.t1);
            ImageView t2 = (ImageView) view.findViewById(R.id.t2);
            ImageView t3 = (ImageView) view.findViewById(R.id.t3);
            ImageView t4 = (ImageView) view.findViewById(R.id.t4);
            ImageView t5 = (ImageView) view.findViewById(R.id.t5);
            ImageView t6 = (ImageView) view.findViewById(R.id.t6);
            ArrayList<ImageView> Arr = new ArrayList<ImageView>();
            Arr.add(t1);
            Arr.add(t2);
            Arr.add(t3);
            Arr.add(t4);
            Arr.add(t5);
            Arr.add(t6);

            Arr.get(position).setBackgroundResource(R.drawable.circle);
            title.setText(tempList.get(position).getTitle());
            title2.setText(tempList.get(position).getTitle2());

            title_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    v.getContext().startActivity(new Intent(v.getContext(), Intro2Activity.class));
                }
            });

            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    v.getContext().startActivity(new Intent(v.getContext(), Intro2Activity.class));
                }
            });

        }

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
