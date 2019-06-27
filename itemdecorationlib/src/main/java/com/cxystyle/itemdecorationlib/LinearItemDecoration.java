package com.cxystyle.itemdecorationlib;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cxystyle.itemdecorationlib.builder.BaseBuilder;
import com.cxystyle.itemdecorationlib.builder.LinearBuilder;

/**
 * RecyclerView 的LinearLayoutManager 万能分割线。 可以设置Section(分类title)和分割线。
 *
 * @author cxy
 */
public class LinearItemDecoration extends SuperItemDecoration {

  //分割线宽
  protected int mDivideWidth;
  //分割线距离recyclerview 间距
  protected int mDividePadding;

  public LinearItemDecoration(BaseBuilder builder) {
    super(builder);

    initBuilder((LinearBuilder) builder);

  }

  private void initBuilder(LinearBuilder builder) {
    LinearBuilder linearBuilder = builder;
    this.mDivideWidth = linearBuilder.getDivideWidth();
    this.mDividePadding = linearBuilder.getDividePadding();
  }

  @Override public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);
    Log.d("ggg", "onDraw");

    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

    if (layoutManager instanceof LinearLayoutManager) {
      boolean reverseLayout = ((LinearLayoutManager) layoutManager).getReverseLayout();

      int childCount = parent.getChildCount();

      for (int i = 0; i < childCount; i++) {
        View child = parent.getChildAt(i);
        int position = parent.getChildAdapterPosition(child);

        //section
        if (mShowSection && isShowSection(position)) {
          int left, top, right, bottom;
          if (isVertical()) {
            //VERTICAL
            left = mSectionPadding;
            right = mWidth - mSectionPadding;
            if (reverseLayout) {
              bottom = child.getBottom() + mSectionWH;
              top = child.getBottom();
            } else {
              top = child.getTop() - mSectionWH;
              bottom = child.getTop();
            }
          } else {
            //HORIZONTAL
            top = mSectionPadding;
            bottom = mHeight - mSectionPadding;
            if (reverseLayout) {
              right = child.getRight() + mSectionWH;
              left = child.getRight();
            } else {
              left = child.getLeft() - mSectionWH;
              right = child.getLeft();
            }
          }
          c.drawRect(left, top, right, bottom, mSectionPaint);
          c.drawText(getText(position), getTextX(left, right),
              getTextY(top, bottom), mTextPaint);

          mSectionTops.put(position, new Point(left, top));

        }


        //if (position < mAllItemCount - 1 && isShowSection(position + 1) || position == mAllItemCount - 1){
        //  //下一个显示section 或者是最后一个 则不画divide
        //  continue;
        //}

        //divide
        if (mDivideWidth != 0) {
          int top, bottom, left, right;
          if (isVertical()) {
            left = mDividePadding;
            right = mWidth - mDividePadding;
            if (reverseLayout) {
              bottom = child.getTop();
              top = bottom - mDivideWidth;
            } else {
              top = child.getBottom();
              bottom = top + mDivideWidth;
            }
          } else {
            top = mDividePadding;
            bottom = mHeight - mDividePadding;
            if (reverseLayout) {
              right = child.getLeft();
              left = right - mDivideWidth;
            } else {
              left = child.getRight();
              right = left + mDivideWidth;
            }
          }
          c.drawRect(left, top, right, bottom, mDividePaint);
        }
      }
    }
  }


  @Override public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDrawOver(c, parent, state);


    if (mShowSection && mIsStick) {

      View child = parent.getChildAt(0);
      int pos = parent.getChildAdapterPosition(child);
      Log.d("ggg", "onDrawOver:" + pos);

      int left, top, right, bottom, distance;

      if (isVertical()) {
        left = mSectionPadding;
        right = mWidth - mSectionPadding;

        if (mIsReverse) {
          distance = mHeight - child.getTop();
          top = mHeight - mSectionWH;
          bottom = mHeight;

          if (distance <= mSectionWH && isShowSection(pos + 1)) {
            top = mHeight - distance;
            bottom = top + mSectionWH;
          }
        } else {
          distance = child.getBottom();
          top = 0;
          bottom = mSectionWH;

          if (distance <= mSectionWH && isShowSection(pos + 1)) {
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

          if (distance <= mSectionWH && isShowSection(pos + 1)) {
            left = mWidth - distance;
            right = left + mSectionWH;
          }
        } else {
          distance = child.getRight();
          left = 0;
          right = mSectionWH;

          if (distance <= mSectionWH && isShowSection(pos + 1)) {
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

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    if (!isInit) {
      super.getItemOffsets(outRect, view, parent, state);
      RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
      if (layoutManager instanceof LinearLayoutManager) {
        mIsReverse = ((LinearLayoutManager) layoutManager).getReverseLayout();
        mOrientation = ((LinearLayoutManager) layoutManager).getOrientation();
      }
      isInit = true;
    }


    int pos = parent.getChildAdapterPosition(view);

    if (mShowSection) {

      //int offset = pos == 0 ? mSectionWH : isShowSection(pos) ? mSectionWH + mDivideWidth
      //    : mDivideWidth;

      int offset = isShowSection(pos) ? mSectionWH : mDivideWidth;

      if (isVertical()) {
        if (mIsReverse) {
          outRect.bottom = offset;
        } else {
          outRect.top = offset;
        }
      } else {
        if (mIsReverse) {
          outRect.right = offset;
        } else {
          outRect.left = offset;
        }
      }
    } else {
      if (isVertical()) {
        if (mIsReverse) {
          outRect.top = mDivideWidth;
        } else {
          outRect.bottom = mDivideWidth;
        }
      } else {
        if (mIsReverse) {
          outRect.left = mDivideWidth;
        } else {
          outRect.right = mDivideWidth;
        }
      }
    }
  }


  //private float getTextX(int left, int right) {
  //
  //  if (isHorizontal() || mIsTextCenter) {
  //    mTextPaint.setTextAlign(Paint.Align.CENTER);
  //    return (left + right) / 2;
  //  } else {
  //    mTextPaint.setTextAlign(Paint.Align.LEFT);
  //    return mSectionPadding + mSectionTextPadding;
  //  }
  //}
  //
  //private float getTextY(int top, int bottom) {
  //  if (isVertical() || mIsTextCenter) {
  //    return (bottom + top) / 2 + textDistance;
  //  } else {
  //    return mSectionPadding + mSectionTextPadding + textHeight;
  //  }
  //}





}
