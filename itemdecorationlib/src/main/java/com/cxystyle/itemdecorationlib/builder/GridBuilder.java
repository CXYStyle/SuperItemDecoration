package com.cxystyle.itemdecorationlib.builder;

import android.graphics.Color;
import com.cxystyle.itemdecorationlib.GridItemDecoration;
import com.cxystyle.itemdecorationlib.SuperItemDecoration;
import java.util.List;

public class GridBuilder extends BaseBuilder<GridBuilder> {

  private static final int DEFAULT_HOR_DIVIDE_WIDTH = 10;
  private static final int DEFAULT_HOR_DIVIDE_PADDING = 16;

  private static final int DEFAULT_VER_DIVIDE_WIDTH = 10;
  private static final int DEFAULT_VER_DIVIDE_PADDING = 16;


  //水平分割线宽度
  protected int horDivideWidth = DEFAULT_HOR_DIVIDE_WIDTH;
  //水平分割线距离recyclerview的间距
  protected int horDividePadding = DEFAULT_HOR_DIVIDE_PADDING;

  //垂直分割线宽度
  protected int verDivideWidth = DEFAULT_VER_DIVIDE_WIDTH;
  //垂直分割线距离recyclerview的间距
  protected int verDividePadding = DEFAULT_VER_DIVIDE_PADDING;

  public int getHorDivideWidth() {
    return horDivideWidth;
  }

  public GridBuilder setHorDivideWidth(int horDivideWidth) {
    this.horDivideWidth = horDivideWidth;
    return this;
  }

  public int getHorDividePadding() {
    return horDividePadding;
  }

  public GridBuilder setHorDividePadding(int horDividePadding) {
    this.horDividePadding = horDividePadding;
    return this;
  }

  public int getVerDivideWidth() {
    return verDivideWidth;
  }

  public GridBuilder setVerDivideWidth(int verDivideWidth) {
    this.verDivideWidth = verDivideWidth;
    return this;
  }

  public int getVerDividePadding() {
    return verDividePadding;
  }

  public GridBuilder setVerDividePadding(int verDividePadding) {
    this.verDividePadding = verDividePadding;
    return this;
  }

  @Override protected int getDefaultDivideColor() {
    return Color.WHITE;
  }

  @Override
  public SuperItemDecoration build() {
    return new GridItemDecoration(this);
  }

}
