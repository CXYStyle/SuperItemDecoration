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
import com.cxystyle.itemdecorationlib.builder.GridBuilder;
import java.util.ArrayList;

/**
 *
 */
public class GridItemDecoration extends SuperItemDecoration {

  private int mHorDivideWidth;
  private int mHorDividePadding;
  private int mVerDivideWidth;
  private int mVerDividePadding;

  //item距离recyclerview的边距
  private int mPadding;

  private GridLayoutManager.SpanSizeLookup mSectionSpan;

  private int mAllCount;
  private int mSpanCount;

  private ArrayMap<Integer, Integer> offsets = new ArrayMap<>();
  private ArrayList sectionPositions = new ArrayList();
  private GridLayoutManager.SpanSizeLookup mDefaultSpanSizeLookup;


  private boolean mNeedAdd;
  private int mNeedAddRowPosition;
  private boolean isInit;

  public GridItemDecoration(BaseBuilder builder) {
    super(builder);

    initBuilder(builder);

    mSectionSpan = new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int p) {

        if (p >= 0 && p < mAllCount - 1 && isShowSection(p + 1)) {
          int spanIndex = p % mSpanCount;

          int offset = getOffset(p);

          int newIndex = (offset + spanIndex) % mSpanCount;
          //Log.d("123", "getSpanSize: " + p + "," + offset + "," + spanIndex + "," + newIndex);

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

  private void initBuilder(BaseBuilder builder) {
    GridBuilder gridBuilder = (GridBuilder) builder;
    mHorDivideWidth = gridBuilder.getHorDivideWidth();
    mVerDivideWidth = gridBuilder.getVerDivideWidth();
    mHorDividePadding = gridBuilder.getHorDividePadding();
    mVerDividePadding = gridBuilder.getVerDividePadding();
  }

  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);

    //int allCount = parent.getAdapter().getItemCount();
    //
    //int childCount = parent.getChildCount();
    //
    //int left, top, right, bottom;
    //
    //for (int i = 0; i < childCount; i++) {
    //  View child = parent.getChildAt(i);
    //  int pos = parent.getChildAdapterPosition(child);
    //
    //  //section
    //  if (mShowSection && isSectionRow(pos)) {
    //    top = child.getTop() - mSectionWH;
    //    bottom = top + mSectionWH;
    //    left = 0;
    //    right = mWidth;
    //    c.drawRect(left, top, right, bottom, mSectionPaint);
    //  }
    //
    //
    //
    //  //divide
    //  //竖 画右边
    //
    //  int rowPosition = getSpanSizeLookup().getSpanIndex(pos, mSpanCount);
    //  left = child.getRight();
    //  right = left + mVerDivideWidth;
    //  top = child.getTop();
    //  bottom = child.getBottom() + mHorDivideWidth;
    //
    //  if (rowPosition  == mSpanCount - 1){
    //
    //  }else{
    //    if (mNeedAdd && rowPosition == mNeedAddRowPosition){
    //      right += 1;
    //    }
    //    c.drawRect(left, top, right, bottom, mDividePaint);
    //  }
    //
    //
    //
    //  //横 画下
    //  top = child.getBottom();
    //  bottom = top + mHorDivideWidth;
    //  left = child.getLeft();
    //  right = child.getRight();
    //  c.drawRect(left, top, right, bottom, mDividePaint);
    //
    //}

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

    int pos = parent.getChildAdapterPosition(view);

    if (!isInit) {

      mWidth = parent.getWidth();
      mHeight = parent.getHeight();
      mAllCount = parent.getAdapter().getItemCount();

      RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
      if (layoutManager instanceof GridLayoutManager) {
        mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        mOrientation = ((GridLayoutManager) layoutManager).getOrientation();
        mIsReverse = ((GridLayoutManager) layoutManager).getReverseLayout();

        if (mShowSection) {
          ((GridLayoutManager) layoutManager).setSpanSizeLookup(mSectionSpan);
        } else {
          ((GridLayoutManager) layoutManager).setSpanSizeLookup(mDefaultSpanSizeLookup);
        }
      }

      int itemOffset;

      if (mOrientation == RecyclerView.VERTICAL) {
        int width = view.getLayoutParams().width;
        if (width < 0) {
          mPadding = 0;
        } else {
          mPadding = (mWidth - (width * mSpanCount) - mVerDivideWidth * (mSpanCount - 1)) / 2;
        }
        itemOffset = (mWidth - mPadding * 2) / mSpanCount - width;

        if (mPadding < 0) {
          mPadding = 0;
        }

        parent.setPadding(mPadding, 0, mPadding, 0);

      }else{
        int height = view.getLayoutParams().height;
        if (height < 0) {
          mPadding = 0;
        } else {
          mPadding = (mHeight - (height * mSpanCount) - mHorDivideWidth * (mSpanCount - 1)) / 2;
        }
        itemOffset = (mHeight - mPadding * 2) / mSpanCount - height;

        if (mPadding < 0) {
          mPadding = 0;
        }

        parent.setPadding(0, mPadding, 0, mPadding);

      }

      for (int i = 1; i < ((mSpanCount / 2) + (mSpanCount % 2)); i++) {
        int left = getLeftOffset(i);
        int right = getRightOffset(i);
        int preRight = getLeftOffset(i - 1);
        if (left + right != itemOffset || preRight + left != mVerDivideWidth) {
          mNeedAdd = true;
          mNeedAddRowPosition = i;
          break;
        }
      }

      isInit = true;

    }

    //包含左右间距的平分公式：left = (c - t) / c;   right = (t + 1) / c;
    //int t = pos % mSpanCount;
    //int left = (int) (((float) (mSpanCount - t)) / mSpanCount * mDivideWidth);
    //int top = 0;
    //int right = (int) (((float) (t + 1) / mSpanCount) * mDivideWidth);
    //int bottom = mDivideWidth;

    //******************************

    //不包含最左最右， 只算中间分割线的平分公式： left = (n-1)/c; right = (c-n)/c
    //int n = pos % mSpanCount + 1;
    //int left = (n - 1) * mDivideWidth / mSpanCount;
    //int right = (mSpanCount - n) * mDivideWidth / mSpanCount;
    //int top = 0;
    //int bottom = mDivideWidth;



    int left = getLeftOffset(pos);
    int right = getRightOffset(pos);

    Log.d("asdf", "getItemOffsets: " + pos +"," + left+","+right);

    int top = 0, bottom = 0;

    int divideWidth = mOrientation == RecyclerView.VERTICAL ? mHorDivideWidth : mVerDivideWidth;

    int offset = getSpanSizeLookup().getSpanGroupIndex(pos, mSpanCount) == 0 ? mSectionWH : isSectionRow(pos) ? mSectionWH + divideWidth : divideWidth;
    if (mShowSection){
      top = offset;
    }else{
      bottom = divideWidth;
    }


    if (mOrientation == RecyclerView.VERTICAL) {
      outRect.set(left,top,right,bottom);
    }else{
      outRect.set(top,left,bottom,right);
    }

  }

  private int getLeftOffset(int position) {
    return getLeftOffsetByRowPosition(getSpanSizeLookup().getSpanIndex(position, mSpanCount));
  }

  private int getLeftOffsetByRowPosition(int rowPosition){
    int n = rowPosition + 1;
    return  (n - 1) * (mOrientation == RecyclerView.VERTICAL ? mVerDivideWidth : mHorDivideWidth) / mSpanCount;
  }

  private int getRightOffset(int position) {
    return getRightOffsetByRowPosition(getSpanSizeLookup().getSpanIndex(position, mSpanCount));
  }

  private int getRightOffsetByRowPosition(int rowPosition){
    int n = rowPosition + 1;
    return (mSpanCount - n) * (mOrientation == RecyclerView.VERTICAL ? mVerDivideWidth : mHorDivideWidth) / mSpanCount;
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
