package com.cxystyle.itemdecorationlib.builder;

import com.cxystyle.itemdecorationlib.GridItemDecoration;
import com.cxystyle.itemdecorationlib.SuperItemDecoration;
import java.util.List;

public class GridBuilder extends BaseBuilder<GridBuilder> {

  @Override
  public SuperItemDecoration build() {
    return new GridItemDecoration(this);
  }

}
