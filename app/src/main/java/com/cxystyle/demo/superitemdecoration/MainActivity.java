package com.cxystyle.demo.superitemdecoration;

import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cxystyle.itemdecorationlib.LinearItemDecoration;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

  private RecyclerView mRv;

  private RecyclerView.Adapter mAdapter;

  private LinearItemDecoration.Builder builder;

  private LinearItemDecoration decorationDivider, decorationSection;

  private LinearLayoutManager linearLayoutManager;

  private GridLayoutManager gridLayoutManager;

  private ArrayList<String> list;
  private MenuItem menuType;
  private MenuItem menuLayout;
  private MenuItem menuOri;
  private MenuItem menuReverse;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initData();

    mRv = findViewById(R.id.rv);

    //括号内为Horizontal的情况
    builder = new LinearItemDecoration.Builder(this)
        //分割线高度(宽度)
        .setDivideHeight(8)
        //分割线颜色
        .setDivideColor(Color.GREEN)
        //分割线左右(上下)间距
        .setDividePadding(16)

        //以下为section设置，不需要可以不设置
        //是否显示section，默认false
        .setShowSection(false)
        //section颜色
        .setSectionColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary))
        //section高度(宽度)
        .setSectionHeight(32)
        //section左右(上下)间距
        .setSectionPadding(0)
        //section文字颜色
        .setSectionTextColor(Color.WHITE)
        //section文字离左(上)间距， 如果值小于0，则为居中
        .setSectionTextPadding(-1)
        //section 文字size
        .setSectionTextSize(16)
        //section是否吸顶
        .setStick(true)
        //section 文字回调，用于section分类
        .setCallback(new LinearItemDecoration.SectionCallback() {
          @Override public String getSectionTitle(int pos) {
            return list.get(pos).substring(0, 1);
          }
        });

    decorationDivider = builder.build();

    decorationSection = builder.setShowSection(true).build();

    linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    gridLayoutManager = new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false);

    mAdapter = new SimpleAdapter(R.layout.item, list);
    mRv.setLayoutManager(linearLayoutManager);
    mRv.addItemDecoration(decorationDivider);
    mRv.setAdapter(mAdapter);

  }

  private void initData() {
    list = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      list.add(String.valueOf((int) (Math.random() * 99 + 1)));
    }

    Collections.sort(list, new Comparator<String>() {
      @Override public int compare(String o1, String o2) {
        int first = o1.charAt(0) - o2.charAt(0);
        return first == 0 ? Integer.valueOf(o1) - Integer.valueOf(o2) : first;
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    menuType = menu.findItem(R.id.type);
    //menuLayout = menu.findItem(R.id.layout);
    menuOri = menu.findItem(R.id.orientation);
    menuReverse = menu.findItem(R.id.reverse);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.divide:
        switchType(false);
        menuType.setTitle("DIVIDE模式");
        break;
      case R.id.section:
        switchType(true);
        menuType.setTitle("SECTION模式");
        break;
      //case R.id.linear:
      //  switchLayout(LAYOUT_LINEAR);
      //  menuLayout.setTitle("LinearLayout布局");
      //  break;
      //case R.id.grid:
      //  switchLayout(LAYOUT_GRID);
      //  menuLayout.setTitle("GridLayout布局");
      //  break;
      case R.id.vertical:
        switchOri(RecyclerView.VERTICAL);
        menuOri.setTitle("VERTICAL方向");
        break;
      case R.id.horizontal:
        switchOri(RecyclerView.HORIZONTAL);
        menuOri.setTitle("HORIZONTAL方向");
        break;
      case R.id.zheng:
        switchReverse(false);
        menuReverse.setTitle("正序");
        break;
      case R.id.fan:
        switchReverse(true);
        menuReverse.setTitle("倒序");
        break;

      default:
        break;
    }

    mAdapter.notifyDataSetChanged();

    return super.onOptionsItemSelected(item);
  }

  /**
   * 切换显示方向
   */
  private void switchOri(@Ori int ori) {
    ((LinearLayoutManager) mRv.getLayoutManager()).setOrientation(ori);
  }

  /**
   * 切换显示顺序
   */
  private void switchReverse(boolean isReverse) {
    ((LinearLayoutManager) mRv.getLayoutManager()).setReverseLayout(isReverse);
  }

  /**
   * 切换视图
   */
  private void switchLayout(@Layout int layout) {
    mRv.setLayoutManager(layout == LAYOUT_LINEAR ? linearLayoutManager : gridLayoutManager);
  }

  /**
   * 切换decoration
   */
  private void switchType(boolean showSection) {
    removeAllItemDecoration();
    mRv.addItemDecoration(showSection ? decorationSection : decorationDivider);
  }

  private void removeAllItemDecoration() {
    int itemDecorationCount = mRv.getItemDecorationCount();
    for (int i = 0; i < itemDecorationCount; i++) {
      mRv.removeItemDecorationAt(i);
    }
  }


  public static final int LAYOUT_LINEAR = 1, LAYOUT_GRID = 2;
  @IntDef({LAYOUT_LINEAR, LAYOUT_GRID})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Layout{}

  @IntDef({RecyclerView.VERTICAL, RecyclerView.HORIZONTAL})
  @Retention(RetentionPolicy.RUNTIME)
  @interface Ori{}

}
