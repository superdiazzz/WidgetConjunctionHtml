package com.ngerancang.textconjunctionhtml2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ngerancang.textconjunctionhtml2.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class OpenYoutubeView extends FrameLayout {
    public OpenYoutubeView(Context context, String url) {
        super(context);
        init(context, url);
    }

    public OpenYoutubeView(Context context){
        super(context);
    }

    public OpenYoutubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OpenYoutubeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, String url) {

        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // no_pics
        ImageView img = new ImageView(context);
        img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Picasso.with(context)
                .load(url)
                .error(R.drawable.no_pics)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .into(img, new CallbackYoutube(this, context));
        this.addView(img);

    }

    private class CallbackYoutube implements Callback{

        private OpenYoutubeView ytVid;
        private Context context;

        private CallbackYoutube(OpenYoutubeView ytVideo, Context context){
            this.ytVid = ytVideo;
            this.context = context;
        }

        @Override
        public void onSuccess() {
            // button play
            ImageView playImg = new ImageView(context);
            playImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_play));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(56, 56);
            playImg.setLayoutParams(params);
            ytVid.addView(playImg);
            params.gravity = Gravity.CENTER;
        }

        @Override
        public void onError() {

        }
    }

}
