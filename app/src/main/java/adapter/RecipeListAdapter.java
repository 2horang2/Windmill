package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import data.models.Mountain;
import windmill.windmill.R;
import windmill.windmill.RecipeDetailActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class RecipeListAdapter extends BindableAdapter<Mountain> {
    public RecipeListAdapter(Context context, List<Mountain> recipes) {
        super(context, recipes);
    }

    static class ViewHolder {
        @Bind(R.id.recipe_image)
        ImageView recipeImageView;
        @Bind(R.id.user_image)
        ImageView userImageView;
        @Bind(R.id.user_name_text)
        TextView userNameTextView;
        @Bind(R.id.title_text)
        TextView titleTextView;

        @Bind(R.id.like)
        TextView like;
        @Bind(R.id.hate)
        TextView hate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.list_item_recipe, null, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }




        @Override
    public void bindView(Mountain mountain, int position, View view) {
        final ViewHolder holder = (ViewHolder) view.getTag();
      

       // Picasso.with(view.getContext())
     //           .load(mountain.img())
      //          .into(holder.recipeImageView);
        //holder.userImageView.setImageResource(R.drawable.user);

     //   Picasso.with(view.getContext())
     //           .load(mountain.user_img())
     //           .into(holder.userImageView);

        ImageLoader.getInstance().displayImage(mountain.img(),holder.recipeImageView);
        ImageLoader.getInstance().displayImage(mountain.user_img(),holder.userImageView);






        holder.userNameTextView.setText(mountain.user());
        holder.titleTextView.setText(mountain.name());
        holder.like.setText(mountain.like());
        holder.hate.setText(mountain.hate());

        view.setOnClickListener(v -> {
            Activity activity = (Activity) v.getContext();
            RecipeDetailActivity.launch(activity, mountain, holder.recipeImageView, "");
        });
    }
}
