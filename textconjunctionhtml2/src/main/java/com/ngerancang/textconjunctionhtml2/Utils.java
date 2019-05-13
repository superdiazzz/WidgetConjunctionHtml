package com.ngerancang.textconjunctionhtml2;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class Utils {

    public static Point sizeDevice(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
