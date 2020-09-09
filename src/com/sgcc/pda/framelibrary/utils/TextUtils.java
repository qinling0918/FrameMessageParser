package com.sgcc.pda.framelibrary.utils;

import android.support.annotation.Nullable;

/**
 * Created by qinling on 2018/10/16 16:05
 * Description:
 */
public class TextUtils {
  /*  public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }*/
    private static boolean isWhiteSpaces(@Nullable CharSequence s) {
        return s != null && s.toString().matches("\\s+");
    }

    public static boolean isEmpty(@Nullable CharSequence text) {
        return text == null || text.length() == 0 || isWhiteSpaces(text) /*|| text.equalsIgnoreCase("null")*/;
    }

}
