package data.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import windmill.windmill.MessageWrite;
import windmill.windmill.R;

/**
 * Created by man on 2015-09-17.
 */

public class ProfileDialog extends Dialog {
    public ProfileDialog(Context context,String id2,String img2) {
        super(context);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_user_profile);

        Activity activity = (Activity)context;

        ImageView img = (ImageView) findViewById(R.id.img);
        TextView id = (TextView) findViewById(R.id.ed_join);
        Button o = (Button) findViewById(R.id.o);
        Button msg = (Button) findViewById(R.id.x);

        id.setText(id2);
        ImageLoader.getInstance().displayImage(img2,img);

        // btn.setVisibility(View.GONE);
        o.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        msg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Bundle extras = new Bundle();
                extras.putString("to", id2);
                Intent intent = new Intent(activity, MessageWrite.class);
                intent.putExtras(extras);
                activity.startActivity(intent);
            }
        });


    }

}