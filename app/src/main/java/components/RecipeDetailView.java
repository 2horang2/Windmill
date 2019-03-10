package components;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.Mountain;
import data.models.PREF;
import data.models.UPDATE;
import windmill.windmill.ImageViewActivity;
import windmill.windmill.MainActivity;
import windmill.windmill.R;

public class RecipeDetailView extends FrameLayout {

    @Bind(R.id.recipe_image)
    ImageView recipeImageView;
    @Bind(R.id.title_wrapper)
    View titleWrapperView;
    @Bind(R.id.title_text)
    TextView titleTextView;
    @Bind(R.id.updated_at_text)
    TextView updatedAtTextView;
    @Bind(R.id.description_text)
    TextView descriptionTextView;

    @Bind(R.id.like)
    TextView like;
    @Bind(R.id.hate)
    TextView hate;

    @Bind(R.id.tmp1)
    ImageView tmp1;
    @Bind(R.id.tmp2)
    ImageView tmp2;





    private Callback callback = new Callback() {
        @Override
        public void onSuccess() {
            Bitmap bitmap = ((BitmapDrawable) recipeImageView.getDrawable()).getBitmap();
            new Palette.Builder(bitmap)
                    .generate(palette -> {
                        int darkMutedColor = palette.getDarkMutedColor(R.color.text_color_primary);
                        titleWrapperView.setBackgroundColor(darkMutedColor);
                        int vibrantColor = palette.getVibrantColor(android.R.color.white);
                       // titleTextView.setTextColor(vibrantColor);
                        //like.setTextColor(vibrantColor);
                       // hate.setTextColor(vibrantColor);
                        int lightVibrantColor = palette.getLightVibrantColor(R.color.text_color_tertiary);
                        //updatedAtTextView.setTextColor(lightVibrantColor);
                    });
        }

        @Override
        public void onError() {

        }
    };

    public RecipeDetailView(Context context) {
        super(context);
        setup();
    }

    public RecipeDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.view_recipe_detail, this);
        ButterKnife.bind(this);
    }


    Handler handler;
    String index = "like",sw;
    boolean first = true;
    Mountain recipe;
    public void setRecipe(Mountain recipe) {
        if (!TextUtils.isEmpty(recipe.img())) {
            Picasso.with(getContext())
                    .load(recipe.img())
                    .into(recipeImageView, callback);

        }



        this.recipe = recipe;
        titleTextView.setText(recipe.name());
        updatedAtTextView.setText(recipe.address());
        descriptionTextView.setText(recipe.text());

        recipeImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new Handler();
                handler.postDelayed(irun, 0);
            }
        });

        like.setText(recipe.like());
        hate.setText(recipe.hate());

        if(recipe.members().contains(","+PREF.id+",")){
            first=false;
        }


        tmp1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                index = "like";
                if(first) {
                    new add().execute(null, null);
                }else{
                    Toast.makeText(getContext(),"이미 좋아요 나 싫어요를 누르셨습니다 ㅠ_ㅜ",Toast.LENGTH_LONG).show();
                }
            }
        });
        tmp2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                index = "false";
                if(first) {
                    new add().execute(null, null);
                }else
                    Toast.makeText(getContext(),"이미 좋아요 나 싫어요를 누르셨습니다 ㅠ_ㅜ",Toast.LENGTH_LONG).show();
            }
        });


    }

    Runnable irun = new Runnable() {

        @Override
        public void run() {

            Activity activity = (Activity) getContext();
            Bundle extras = new Bundle();
            extras.putString("img", recipe.img());
            extras.putString("name", recipe.name());
            Intent intent = new Intent(activity, ImageViewActivity.class);
            intent.putExtras(extras);
            activity.startActivity(intent);

            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };



    ProgressDialog Dialog;
    private class add extends AsyncTask<String, Void, Void>
    {
        @Override /* 프로세스가 실행되기 전에 실행 되는 부분 - 초기 설정 부분 */
        protected void onPreExecute()
        {
            super.onPreExecute();
            Dialog = new ProgressDialog(getContext());
            Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); /* 원형 프로그래스 다이얼 로그 스타일로 설정 */
            Dialog.setMessage("잠시만 기다려 주세요.");
            Dialog.show();
        }

        @Override /* AsyncTask 실행 부분 */
        protected Void doInBackground(String... params)
        {
            try{

                HttpPost httppost;
                HttpResponse response;
                HttpClient httpclient;
                List<NameValuePair> nameValuePairs;

                httpclient=new DefaultHttpClient();

                httppost= new HttpPost("http://cmcm1284.cafe24.com/windmill/add_mountain_like.php");
                nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("idx",recipe.id()));
                nameValuePairs.add(new BasicNameValuePair("user", PREF.id));
                nameValuePairs.add(new BasicNameValuePair("index", index));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response=httpclient.execute(httppost);
                System.out.println("ㅅㅅㅅㅅ섭샤ㅣㅓㅅ : " + response);
            }catch(Exception e){
                System.out.println("Exception : " + e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            UPDATE.mountain_update=true;
            Dialog.dismiss();
            check();
        }
    }

    private void check(){
        UPDATE.mountain_update=true;
        first=false;
        if(index=="like"){
            int tmp = Integer.valueOf(recipe.like());
            like.setText((tmp+1)+"");
        }else{
            int tmp = Integer.valueOf(recipe.hate());
            hate.setText((tmp+1)+"");
        }

    }
}
