package com.example.movietracker.utils;

import android.content.Context;

public class Utils {
    public static int convertDpToPixels(Context context, float dp)
    {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int boolToInt(Boolean value) {
        return value.compareTo(false);
    }

    public  static  boolean intToBool(int value){
        return value == 1;
    }
}
