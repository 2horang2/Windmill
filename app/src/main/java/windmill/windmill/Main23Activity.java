package windmill.windmill;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class Main23Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);

        getSupportActionBar().hide();

        ArrayList<init> tempList = new ArrayList<init>(6);

        init temp = new init();
        temp.setTitle("대한민국의 아름다운 산들을");
        temp.setTitle2("공유하실 수 있으십니다");
        temp.setImg(getResources().getDrawable(R.drawable.init1));
        tempList.add(temp);

        temp = new init();
        temp.setTitle("다른 사람들과 즐길 수 있는 모임을");
        temp.setTitle2("추천해드립니다");
        temp.setImg(getResources().getDrawable(R.drawable.init3));
        tempList.add(temp);

        temp = new init();
        temp.setTitle("같이 즐길 수 있으신 분들과");
        temp.setTitle2("소통이 가능합니다");
        temp.setImg(getResources().getDrawable(R.drawable.init44));
        tempList.add(temp);

        temp = new init();
        temp.setTitle("당신의 자랑스런 사진을");
        temp.setTitle2("다른 사람들에게 자랑해주세요");
        temp.setImg(getResources().getDrawable(R.drawable.init2));
        tempList.add(temp);

        temp = new init();
        temp.setTitle("여러 모임에 참여하셔서");
        temp.setTitle2("다른 사람들을 재미나게해주세요");
        temp.setImg(getResources().getDrawable(R.drawable.init6));
        tempList.add(temp);

        temp = new init();
        temp.setTitle("이제부터 산따라바람따라");
        temp.setTitle2("시작합니다");
        temp.setImg(getResources().getDrawable(R.drawable.init1));
        tempList.add(temp);

        ViewPager vp = (ViewPager) findViewById(R.id.pager);
        initAdapter adapter = new initAdapter(getLayoutInflater(), 6, tempList);
        vp.setAdapter(adapter);
    }


}
