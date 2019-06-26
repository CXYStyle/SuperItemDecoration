package com.cxystyle.itemdecorationlib.builder;

import android.graphics.Color;
import com.cxystyle.itemdecorationlib.LinearItemDecoration;
import com.cxystyle.itemdecorationlib.SuperItemDecoration;

public class LinearBuilder extends BaseBuilder<LinearBuilder> {
  private static final int DEFAULT_DIVIDE_WIDTH = 10;
  private static final int DEFAULT_DIVIDE_PADDING = 16;

  //分割线宽度
  protected int divideWidth = DEFAULT_DIVIDE_WIDTH;
  //两边边距
  protected int dividePadding = DEFAULT_DIVIDE_PADDING;


  public int getDivideWidth() {
    return divideWidth;
  }

  public LinearBuilder setDivideWidth(int divideWidth) {
    this.divideWidth = divideWidth;
    return this;
  }

  public int getDividePadding() {
    return dividePadding;
  }

  public LinearBuilder setDividePadding(int dividePadding) {
    this.dividePadding = dividePadding;
    return this;
  }

  @Override protected int getDefaultDivideColor() {
    return Color.parseColor("#c6c6c6");
  }

  @Override
  public SuperItemDecoration build() {
    return new LinearItemDecoration(this);
  }
}
