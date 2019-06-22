package com.ngerancang.textconjunctionhtml2.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Toast;

import com.ngerancang.textconjunctionhtml2.R;
import com.ngerancang.textconjunctionhtml2.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class OpenImageView extends AppCompatImageView {


    private OpenRelativeLayout mLayout;

    public OpenImageView(Context context, String urlImage, OpenRelativeLayout layout) {
        super(context);
        init(context, urlImage);
        this.mLayout = layout;
    }

    public OpenImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OpenImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, String urlImage){

        Picasso.with(context)
                .load(urlImage)
                .resize(Utils.sizeDevice((Activity) context).x, 300)
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .error(R.drawable.no_pics)
                .into(this);

        this.setOnClickListener(v -> {
            Toast.makeText(context, "Di click!", Toast.LENGTH_SHORT).show();
            // TODO trigger OpenRelativeLayout to VISIBLE
            mLayout.setVisibility(VISIBLE);
            if(this.getDrawable() != null){
                mLayout.getImgTouch().setImageBitmap(((BitmapDrawable) this.getDrawable()).getBitmap(), null, -1, -1);
            }

        });

    }


}
