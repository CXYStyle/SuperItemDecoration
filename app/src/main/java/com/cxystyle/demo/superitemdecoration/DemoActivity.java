package com.cxystyle.demo.superitemdecoration;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cxystyle.itemdecorationlib.builder.BaseBuilder;
import com.cxystyle.itemdecorationlib.builder.GridBuilder;
import com.cxystyle.itemdecorationlib.builder.LinearBuilder;
import com.cxystyle.itemdecorationlib.callback.OnSectionClickListener;
import com.cxystyle.itemdecorationlib.callback.SectionTitleCallback;

public class DemoActivity extends BaseActivity {

  private RecyclerView mRv;
  private BaseBuilder mBuilder;
  private SimpleAdapter mAdapter;

  protected int mLayoutManager = 1;
  protected int mMode = 1;
  protected int mOrientation = RecyclerView.VERTICAL;
  protected boolean mReverse = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_demo);

    getData();

    initRecycleView();
  }

  private void getData() {
    mLayoutManager = getIntent().getIntExtra("layoutmanager", mLayoutManager);
    mMode = getIntent().getIntExtra("mode", mMode);
    mOrientation = getIntent().getIntExtra("orientation", mOrientation);
    mReverse = getIntent().getBooleanExtra("reverse", mReverse);
  }

  private void initRecycleView() {
    mRv = findViewById(R.id.rv);

    //括号内为Horizontal的情况
    mBuilder = mLayoutManager == 1 ? new LinearBuilder() : new GridBuilder();
    mBuilder
        //分割线颜色
        //.setDivideColor(Color.RED)


        //以下为section设置，不需要可以不设置
        //是否显示section，默认false
        .setShowSection(mMode == 2)
        //section颜色
        .setSectionColor(Color.GRAY)
        //section高度(宽度)
        .setSectionWH(56)
        //section左右(上下)间距
        .setSectionPadding(dp2px(0))
        //section文字颜色
        .setSectionTextColor(Color.BLACK)
        //section文字离左(上)间距， 如果值小于0，则为居中
        .setSectionTextPadding(dp2px(16))
        //section 文字size
        .setSectionTextSize(32)
        //section是否吸顶
        .setStick(true)
        //section 文字回调，用于section分类
        .setCallback(new SectionTitleCallback() {
          @Override public String getSectionTitle(int pos) {
            return String.valueOf(Datas.list.get(pos)).substring(0, 1);

            //int i = Datas.list.get(pos);
            //if (i < 60){
            //  return "不及格";
            //}else if(i >= 60 && i < 80){
            //  return "差";
            //}else if(i >= 80 && i < 90){
            //  return "良";
            //}else{
            //  return "优";
            //}

          }
        })
        .setOnSectionClickListener(new OnSectionClickListener() {
          @Override public void onSectionClick(int position) {
            Toast.makeText(DemoActivity.this, "click section: " + position, Toast.LENGTH_SHORT).show();
          }
        });
    if (mLayoutManager == 1){
      ((LinearBuilder) mBuilder)
          //分割线高度(宽度)
          .setDivideWidth(1)
          //分割线左右(上下)间距
          .setDividePadding(dp2px(16));
    }else{
      ((GridBuilder) mBuilder)
          .setHorDivideWidth(20)
          .setVerDivideWidth(13);
    }


    int layoutId = mLayoutManager == 1 ? R.layout.item : mOrientation == RecyclerView.VERTICAL ? R.layout.item_grid_ver : R.layout.item_grid_hor;
    mRv.setLayoutManager(mLayoutManager == 1 ? new LinearLayoutManager(this, mOrientation, mReverse) : new GridLayoutManager(this, 4, mOrientation, mReverse));

    mAdapter = new SimpleAdapter(layoutId, Datas.list);
    mRv.addItemDecoration(mBuilder.build());
    mRv.setAdapter(mAdapter);
  }

  private int dp2px(int dp){
    return (int) (getResources().getDisplayMetrics().density * dp + 0.5);
  }


}
