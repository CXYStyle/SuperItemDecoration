package com.cxystyle.itemdecorationlib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cxystyle.itemdecorationlib.builder.BaseBuilder;
import java.util.ArrayList;

/**
 *
 */
public class GridItemDecoration extends SuperItemDecoration {

  private GridLayoutManager.SpanSizeLookup mSectionSpan;

  private int mAllCount;
  private int mSpanCount;

  private ArrayMap<Integer, Integer> offsets = new ArrayMap<>();
  private ArrayList sectionPositions = new ArrayList();
  private GridLayoutManager.SpanSizeLookup mDefaultSpanSizeLookup;

  public GridItemDecoration(BaseBuilder builder) {
    super(builder);
    mSectionSpan = new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int p) {

        if (p >= 0 && p < mAllCount - 1 && isShowSection(p + 1)) {
          int spanIndex = p % mSpanCount;

          int offset = getOffset(p);

          int newIndex = (offset + spanIndex) % mSpanCount;
          Log.d("123", "getSpanSize: " + p + "," + offset + "," + spanIndex + "," + newIndex);

          int newSize = mSpanCount - newIndex;

          if (offsets.get(p) == null) {
            offsets.put(p, (offset + newSize - 1) % mSpanCount);
          }
          return newSize;
        }

        return 1;
      }
    };

    mDefaultSpanSizeLookup = new GridLayoutManager.DefaultSpanSizeLookup();

  }

  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);

    int allCount = parent.getAdapter().getItemCount();

    int childCount = parent.getChildCount();

    int left, top, right, bottom;


    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);
      int pos = parent.getChildAdapterPosition(child);

      //section
      if (mShowSection && isSectionRow(pos)) {
        top = child.getTop() - mSectionWH;
        bottom = top + mSectionWH;
        left = 0;
        right = mWidth;
        c.drawRect(left, top, right, bottom, mSectionPaint);
      }



      //divide
      //竖
      left = child.getRight();
      right = left + mDivideWH;
      top = child.getTop();
      bottom = child.getBottom() + mDivideWH;
      c.drawRect(left, top, right, bottom, mDividePaint);
      //横
      top = child.getBottom();
      bottom = top + mDivideWH;
      left = child.getLeft() - mDivideWH;
      right = child.getRight();
      c.drawRect(left, top, right, bottom, mDividePaint);



    }

  }

  @Override
  public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDrawOver(c, parent, state);




  }

  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);

    mWidth = parent.getWidth();
    mHeight = parent.getHeight();
    mAllCount = parent.getAdapter().getItemCount();

    final int pos = parent.getChildAdapterPosition(view);

      RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
      if (layoutManager instanceof GridLayoutManager) {
        mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        mOrientation = ((GridLayoutManager) layoutManager).getOrientation();
        mIsReverse = ((GridLayoutManager) layoutManager).getReverseLayout();

        if (mShowSection) {
          ((GridLayoutManager) layoutManager).setSpanSizeLookup(mSectionSpan);
        }else {
          ((GridLayoutManager) layoutManager).setSpanSizeLookup(mDefaultSpanSizeLookup);
        }
      }

    int spanSize;
    int rowPosition;

    spanSize = getSpanSizeLookup().getSpanSize(pos);
    rowPosition = getSpanSizeLookup().getSpanIndex(pos, mSpanCount);

    //包含左右间距的平分公式：left = (c - t) / c;   right = (t + 1) / c;
    //int t = pos % mSpanCount;
    //int left = (int) (((float) (mSpanCount - t)) / mSpanCount * mDivideWH);
    //int top = 0;
    //int right = (int) (((float) (t + 1) / mSpanCount) * mDivideWH);
    //int bottom = mDivideWH;

    //******************************

    //不包含最左最右， 只算中间分割线的平分公式： left = (n-1)/c; right = (c-n)/c
    //int n = pos % mSpanCount + 1;
    //int left = (n - 1) * mDivideWH / mSpanCount;
    //int right = (mSpanCount - n) * mDivideWH / mSpanCount;
    //int top = 0;
    //int bottom = mDivideWH;

    int width = view.getWidth();
    Log.d("123", "getItemOffsets: " + width + view.getMeasuredWidth());

    int spc = mWidth - (width * mSpanCount) - (mDivideWH * (mSpanCount - 1));

    //int n = pos % mSpanCount + 1;
    int leftN = rowPosition + 1;
    int rightN = rowPosition + spanSize;


    int left = (leftN - 1) * mDivideWH / mSpanCount;
    int right = (mSpanCount - rightN) * mDivideWH / mSpanCount;

    int top = 0, bottom = 0;

    int offset = getSpanSizeLookup().getSpanGroupIndex(pos, mSpanCount) == 0 ? mSectionWH : isSectionRow(pos) ? mSectionWH + mDivideWH : mDivideWH;
    if (mShowSection){
      top = offset;
    }else{
      bottom = mDivideWH;
    }


    if (mOrientation == RecyclerView.VERTICAL) {
      outRect.set(left,top,right,bottom);
    }else{
      outRect.set(top,left,bottom,right);
    }

  }

  private boolean isSectionRow(int pos) {
    for (int i = pos; i >= pos - mSpanCount + 1 && i >= 0; i--) {
      if (isShowSection(i)){
        return true;
      }
    }
    return false;
  }

  private int getOffset(int p) {
    if (p == 0) return 0;
    for (int i = p - 1; i >= 0; i--) {
      Integer offset = offsets.get(i);
      if (offset != null){
        return offset;
      }
    }
    return 0;
  }

  private GridLayoutManager.SpanSizeLookup getSpanSizeLookup(){
    if (mShowSection){
      return mSectionSpan;
    }
    return mDefaultSpanSizeLookup;
  }

}
