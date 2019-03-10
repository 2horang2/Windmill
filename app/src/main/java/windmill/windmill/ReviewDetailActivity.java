package windmill.windmill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import components.ReviewDetailView;
import data.models.Review;


public class ReviewDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOUNTAIN = "recipe";
    public static final String EXTRA_IMAGE = "ItemDetailActivity:image";

    @Bind(R.id.review_detail)
    ReviewDetailView reviewDetailView;

    static Review review;

    public static void launch(Activity activity, Review review2, ImageView transitionView, String url) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                transitionView,
                EXTRA_IMAGE);

        review = review2;
        Intent intent = new Intent(activity, ReviewDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.hide();
            //actionBar.setDisplayHomeAsUpEnabled(true);
           // actionBar.setHomeButtonEnabled(true);
            //actionBar.setTitle(review.getTitle()+"");
        }

        ImageView review_img = (ImageView) reviewDetailView.findViewById(R.id.review_image);
        ViewCompat.setTransitionName(review_img, EXTRA_IMAGE);
        setupViews();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle extras = new Bundle();
                extras.putString("idx", review.getIdx());
                extras.putString("name", RecipeDetailActivity.m_name);
                Intent iIntent = new Intent(ReviewDetailActivity.this, MountainBoard.class);
                iIntent.putExtras(extras);
                startActivity(iIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown( int KeyCode, KeyEvent event )
    {

        if( event.getAction() == KeyEvent.ACTION_DOWN ){
            if( KeyCode == KeyEvent.KEYCODE_BACK ){


                Bundle extras = new Bundle();
                extras.putString("idx", review.getIdx());
                extras.putString("name", RecipeDetailActivity.m_name);
                Intent iIntent = new Intent(ReviewDetailActivity.this, MountainBoard.class);
                iIntent.putExtras(extras);
                startActivity(iIntent);
                finish();
                return true;

            }
        }
        return super.onKeyDown( KeyCode, event );
    }

    private void setupViews() {
        reviewDetailView.setReview(review);
    }


}
