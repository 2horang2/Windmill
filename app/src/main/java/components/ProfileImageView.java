package components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.widget.ImageView;

/**
 * Created by man on 2015-09-05.
 */


public class ProfileImageView extends ImageView {


    private int n = 5;

    private int rWidth = 0;


    private int rHeight = 0;


    public ProfileImageView(Context context) {


        super(context);


    }


    public void setHeight(int h) {


        rHeight = h;


    }


    public void setWidth(int w) {


        rWidth = w;


    }


    @Override


    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);


        float[] outerR = new float[]{n, n, n, n, n, n, n, n};


        RectF inset = new RectF(5, 5, 5, 5); // 모서리 둥근 사각형 띠의 두께.


        float[] innerR = new float[]{n, n, n, n, n, n, n, n};


        ShapeDrawable mDrawables = new ShapeDrawable(new RoundRectShape(outerR, inset, innerR));


        mDrawables.getPaint().setColor(Color.WHITE);


        mDrawables.getPaint().setAntiAlias(true);


        mDrawables.setBounds(-2, -2, rWidth + 1, rHeight + 1);


        mDrawables.draw(canvas);


    }


}