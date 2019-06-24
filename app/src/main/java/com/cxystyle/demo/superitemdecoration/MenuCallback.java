package com.cxystyle.demo.superitemdecoration;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface MenuCallback {

  @IntDef({ RecyclerView.VERTICAL, RecyclerView.HORIZONTAL})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Ori{}

  /**
   * 切换显示方向
   */
  void switchOri(@Ori int ori);

  /**
   * 切换显示顺序
   */
  void switchReverse(boolean isReverse);

  /**
   * 切换decoration
   */
  void switchType(boolean showSection);

}
