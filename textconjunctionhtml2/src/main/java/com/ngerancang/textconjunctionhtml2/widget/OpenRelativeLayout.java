package com.ngerancang.textconjunctionhtml2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ngerancang.textconjunctionhtml2.R;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class OpenRelativeLayout extends RelativeLayout {


    private ImageViewTouch imgTouch;


    public OpenRelativeLayout(Context context) {
        super(context);
        initialize(context);
    }

    public OpenRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public OpenRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {

        View view = inflate(context, R.layout.open_relative_layout, null);
        ImageView imgClose = view.findViewById(R.id.btn_close);
        imgClose.setOnClickListener(v -> {
            Toast.makeText(context, "img close diclick!", Toast.LENGTH_SHORT).show();
            this.setVisibility(GONE);
        });
        imgTouch = view.findViewById(R.id.imageViewZoom);
        imgTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        addView(view);
    }

    public ImageViewTouch getImgTouch() {
        return imgTouch;
    }

    public void setImgTouch(ImageViewTouch imgTouch) {
        this.imgTouch = imgTouch;
    }
}
