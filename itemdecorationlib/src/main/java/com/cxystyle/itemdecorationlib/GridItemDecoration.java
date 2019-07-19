package com.cxystyle.itemdecorationlib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
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
import java.util.HashMap;

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

  private int mSpanCount;

  //private ArrayMap<Integer, Integer> offsets = new ArrayMap<>();
  private HashMap<Integer, Integer> offsets = new HashMap<>();

  private GridLayoutManager.SpanSizeLookup mDefaultSpanSizeLookup;


  private boolean mNeedAdd;
  private int mNeedAddRowPosition;

  public GridItemDecoration(BaseBuilder builder) {
    super(builder);

    initBuilder(builder);

    mSectionSpan = new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int p) {

        if (p >= 0 && p < mAllItemCount - 1 && isShowSection(p + 1)) {
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

  private void initBuilder(BaseBuilder builder) {
    GridBuilder gridBuilder = (GridBuilder) builder;
    mHorDivideWidth = gridBuilder.getHorDivideWidth();
    mVerDivideWidth = gridBuilder.getVerDivideWidth();
  }

  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);

    int childCount = parent.getChildCount();

    int left, top, right, bottom;

    for (int i = 0; i < childCount; i++) {
      View child = parent.getChildAt(i);
      int pos = parent.getChildAdapterPosition(child);

      //section
      if (mShowSection && isSectionRow(pos)) {
        if (isVertical()) {
          left = mSectionPadding;
          right = mWidth - mSectionPadding;
          if (mIsReverse){
            top = child.getBottom();
            bottom = top + mSectionWH;
          }else {
            bottom = child.getTop();
            top = bottom - mSectionWH;
          }
        }else{
          top = mSectionPadding;
          bottom = mHeight - mSectionPadding;
          if (mIsReverse){
            left = child.getRight();
            right = left + mSectionWH;
          }else{
            right = child.getLeft();
            left = right - mSectionWH;
          }
        }
        c.drawRect(left, top, right, bottom, mSectionPaint);
        c.drawText(getText(pos), getTextX(left, right),
            getTextY(top, bottom), mTextPaint);

        mSectionTops.put(pos, new Point(left, top));
      }


      //divide
      //竖
      int rowPosition = getSpanSizeLookup().getSpanIndex(pos, mSpanCount);

      if (mIsReverse && isHorizontal()){
        right = child.getLeft();
        left = right - mVerDivideWidth;
      }else {
        left = child.getRight();
        right = left + mVerDivideWidth;
      }

      top = child.getTop();
      bottom = child.getBottom() + (isHorizontal() && rowPosition != mSpanCount - 1 ? mHorDivideWidth : 0);

      if (rowPosition  == mSpanCount - 1 && isVertical()){

      }else{
        if (mNeedAdd && rowPosition == mNeedAddRowPosition && isVertical()){
          right += 1;
        }
        c.drawRect(left, top, right, bottom, mDividePaint);
      }


      //横
      if (isVertical() && mIsReverse){
        bottom = child.getTop();
        top = bottom - mHorDivideWidth;
      }else {
        top = child.getBottom();
        bottom = top + mHorDivideWidth;
      }

      right = child.getRight() + (isVertical() && rowPosition != mSpanCount - 1 ? mVerDivideWidth : 0);
      left = child.getLeft();

      if (rowPosition  == mSpanCount - 1 && isHorizontal()){

      }else{
        if (mNeedAdd && rowPosition == mNeedAddRowPosition && isHorizontal()){
          bottom += 1;
        }
        c.drawRect(left, top, right, bottom, mDividePaint);
      }

    }

  }

  @Override
  public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDrawOver(c, parent, state);

    if (mShowSection && mIsStick) {

      View child = parent.getChildAt(0);
      int pos = parent.getChildAdapterPosition(child);

      int left, top, right, bottom, distance;

      if (isVertical()) {
        left = mSectionPadding;
        right = mWidth - mSectionPadding;

        if (mIsReverse) {
          distance = mHeight - child.getTop();
          top = mHeight - mSectionWH;
          bottom = mHeight;

          if (distance <= mSectionWH && isSectionNextRow(pos)) {
            top = mHeight - distance;
            bottom = top + mSectionWH;
          }
        } else {
          distance = child.getBottom();
          top = 0;
          bottom = mSectionWH;

          if (distance <= mSectionWH && isSectionNextRow(pos)) {
            top = distance - mSectionWH;
            bottom = distance;
          }
        }
      } else {
        top = mSectionPadding;
        bottom = mHeight - mSectionPadding;

        if (mIsReverse) {
          distance = mWidth - child.getLeft();
          left = mWidth - mSectionWH;
          right = mWidth;

          if (distance <= mSectionWH && isSectionNextRow(pos)) {
            left = mWidth - distance;
            right = left + mSectionWH;
          }
        } else {
          distance = child.getRight();
          left = 0;
          right = mSectionWH;

          if (distance <= mSectionWH && isSectionNextRow(pos)) {
            left = distance - mSectionWH;
            right = distance;
          }
        }
      }

      c.drawRect(left, top, right, bottom, mSectionPaint);
      c.drawText(getText(pos), getTextX(left, right), getTextY(top, bottom),
          mTextPaint);

      mSectionTops.put(pos, new Point(left, top));

    }


  }

  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

    if (!isInit) {
      super.getItemOffsets(outRect, view, parent, state);

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

      if (isVertical()) {
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

        parent.setPadding(mPadding, parent.getPaddingTop(), mPadding, parent.getPaddingBottom());

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

        parent.setPadding(parent.getPaddingLeft(), mPadding, parent.getPaddingRight(), mPadding);

      }

      for (int i = 1; i < ((mSpanCount / 2) + (mSpanCount % 2)); i++) {
        int left = getLeftOffsetByRowPosition(i);
        int right = getRightOffsetByRowPosition(i, 1);
        int preRight = getLeftOffsetByRowPosition(i - 1);
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

    int pos = parent.getChildAdapterPosition(view);

    int left = getLeftOffset(pos);
    int right = getRightOffset(pos);

    Log.d("asdf", "getItemOffsets: " + pos +"," + left+","+right);

    int top = 0, bottom = 0;

    int divideWidth = isVertical() ? mHorDivideWidth : mVerDivideWidth;

    //int offset = getSpanSizeLookup().getSpanGroupIndex(pos, mSpanCount) == 0 ? mSectionWH : isSectionRow(pos) ? mSectionWH + divideWidth : divideWidth;
    int offset = isSectionRow(pos) ? mSectionWH : divideWidth;
    if (mShowSection){
      top = offset;
    }else{
      bottom = divideWidth;
    }


    if (isVertical()) {
      if (mIsReverse){
      outRect.set(left,bottom,right,top);
      }else {
      outRect.set(left,top,right,bottom);

      }
    }else{
      if (mIsReverse){
        outRect.set(bottom,left,top,right);
      }else{
        outRect.set(top,left,bottom,right);
      }
    }

  }

  private int getLeftOffset(int position) {
    return getLeftOffsetByRowPosition(getSpanSizeLookup().getSpanIndex(position, mSpanCount));
  }

  private int getLeftOffsetByRowPosition(int rowPosition){
    int n = rowPosition + 1;
    return  (n - 1) * (isVertical() ? mVerDivideWidth : mHorDivideWidth) / mSpanCount;
  }

  private int getRightOffset(int position) {
    return getRightOffsetByRowPosition(getSpanSizeLookup().getSpanIndex(position, mSpanCount), getSpanSizeLookup().getSpanSize(position));
  }

  private int getRightOffsetByRowPosition(int rowPosition, int spanSize){
    int n = rowPosition + spanSize;
    return (mSpanCount - n) * (isVertical() ? mVerDivideWidth : mHorDivideWidth) / mSpanCount;
  }

  /**
   * 当前所在的行 是否该显示section
   * @param pos
   * @return
   */
  private boolean isSectionRow(int pos) {
    for (int i = pos; i >= pos - mSpanCount + 1 && i >= 0; i--) {
      if (isShowSection(i)){
        return true;
      }
    }
    return false;
  }

  private boolean isSectionNextRow(int pos){
    for (int i = pos + 1; i <= pos + mSpanCount && i < mAllItemCount ; i++) {
      if (getSpanSizeLookup().getSpanIndex(i, mSpanCount) == 0){
        if (isShowSection(i)){
          return true;
        }
        break;
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
