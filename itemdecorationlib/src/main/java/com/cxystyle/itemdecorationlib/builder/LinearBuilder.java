package com.cxystyle.itemdecorationlib.builder;

import android.graphics.Color;
import com.cxystyle.itemdecorationlib.LinearItemDecoration;
import com.cxystyle.itemdecorationlib.SuperItemDecoration;
import com.cxystyle.itemdecorationlib.callback.OnSectionClickListener;
import com.cxystyle.itemdecorationlib.callback.SectionTitleCallback;

public class LinearBuilder extends BaseBuilder<LinearBuilder> {
  @Override
  public SuperItemDecoration build() {
    return new LinearItemDecoration(this);
  }
}
