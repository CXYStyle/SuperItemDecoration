package com.cxystyle.itemdecorationlib;

import android.content.Context;

public class Utils {

  public static int dp2px(Context context, float dp) {
    return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
  }

  public static int sp2px(Context context, float sp) {
    return (int) (context.getResources().getDisplayMetrics().scaledDensity * sp + 0.5f);
  }

}
