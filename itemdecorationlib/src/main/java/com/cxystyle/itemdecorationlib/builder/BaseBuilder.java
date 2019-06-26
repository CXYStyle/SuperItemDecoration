package com.cxystyle.itemdecorationlib.builder;

import android.graphics.Color;
import com.cxystyle.itemdecorationlib.GridItemDecoration;
import com.cxystyle.itemdecorationlib.SuperItemDecoration;
import com.cxystyle.itemdecorationlib.callback.OnSectionClickListener;
import com.cxystyle.itemdecorationlib.callback.SectionTitleCallback;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseBuilder<T extends BaseBuilder> {

  private static final int DEFAULT_SECTION_WIDTH_HEIGHT = 32;
  private static final int DEFAULT_SECTION_PADDING = 0;
  private static final int DEFAULT_SECTION_TEXT_PADDING = 0;
  private static final int DEFAULT_SECTION_COLOR = Color.parseColor("#c6c6c6");
  private static final int DEFAULT_SECTION_TEXT_SIZE = 14;
  private static final int DEFAULT_SECTION_TEXT_COLOR = Color.parseColor("#999999");
  private static final boolean DEFAULT_IS_STICK = false;


  private static final boolean DEFAULT_SHOW_SECTION = false;

  //section
  //宽或高
  protected int sectionWH = DEFAULT_SECTION_WIDTH_HEIGHT;
  //左右或上下边距
  protected int sectionPadding = DEFAULT_SECTION_PADDING;
  //文字左边或上边边距
  protected int sectionTextPadding = DEFAULT_SECTION_TEXT_PADDING;
  //颜色
  protected int sectionColor = DEFAULT_SECTION_COLOR;
  //文字尺寸
  protected int sectionTextSize = DEFAULT_SECTION_TEXT_SIZE;
  //文字颜色
  protected int sectionTextColor = DEFAULT_SECTION_TEXT_COLOR;
  //固定头部
  protected boolean isStick = DEFAULT_IS_STICK;
  //是否显示分类title
  protected boolean showSection = DEFAULT_SHOW_SECTION;

  //颜色
  protected int divideColor = getDefaultDivideColor();

  protected abstract int getDefaultDivideColor();

  //section 分类title数据回调
  protected SectionTitleCallback callback;



  //section 分类title点击回调
  protected OnSectionClickListener onSectionClickListener;

  public int getSectionWH() {
    return sectionWH;
  }

  public T setSectionWH(int sectionWH) {
    this.sectionWH = sectionWH;
    return (T) this;
  }

  public int getSectionPadding() {
    return sectionPadding;
  }

  public T setSectionPadding(int sectionPadding) {
    this.sectionPadding = sectionPadding;
    return (T) this;
  }

  public int getSectionTextPadding() {
    return sectionTextPadding;
  }

  public T setSectionTextPadding(int sectionTextPadding) {
    this.sectionTextPadding = sectionTextPadding;
    return (T) this;
  }

  public int getSectionColor() {
    return sectionColor;
  }

  public T setSectionColor(int sectionColor) {
    this.sectionColor = sectionColor;
    return (T) this;
  }

  public int getSectionTextSize() {
    return sectionTextSize;
  }

  public T setSectionTextSize(int sectionTextSize) {
    this.sectionTextSize = sectionTextSize;
    return (T) this;
  }

  public int getSectionTextColor() {
    return sectionTextColor;
  }

  public T setSectionTextColor(int sectionTextColor) {
    this.sectionTextColor = sectionTextColor;
    return (T) this;
  }

  public boolean isStick() {
    return isStick;
  }

  public T setStick(boolean stick) {
    isStick = stick;
    return (T) this;
  }

  public int getDivideColor() {
    return divideColor;
  }

  public BaseBuilder setDivideColor(int divideColor) {
    this.divideColor = divideColor;
    return (T) this;
  }

  public SectionTitleCallback getCallback() {
    return callback;
  }

  public T setCallback(SectionTitleCallback callback) {
    this.callback = callback;
    return (T) this;
  }

  public boolean isShowSection() {
    return showSection;
  }

  public T setShowSection(boolean showSection) {
    this.showSection = showSection;
    return (T) this;
  }

  public OnSectionClickListener getOnSectionClickListener() {
    return onSectionClickListener;
  }

  public T setOnSectionClickListener(
      OnSectionClickListener onSectionClickListener) {
    this.onSectionClickListener = onSectionClickListener;
    return (T) this;
  }


  public abstract SuperItemDecoration build();

}
