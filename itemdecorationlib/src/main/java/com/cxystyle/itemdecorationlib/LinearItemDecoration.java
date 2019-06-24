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

/**
 * RecyclerView 的LinearLayoutManager 万能分割线。 可以设置Section(分类title)和分割线。
 *
 * @author cxy
 */
public class LinearItemDecoration extends SuperItemDecoration {

  //文字基线到文字中心的距离
  private float textDistance;
  //文字的高度
  private float textHeight;

  public LinearItemDecoration(BaseBuilder builder) {
    super(builder);

    Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
    textDistance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    textHeight = fontMetrics.bottom - fontMetrics.top;

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
          if (mOrientation == LinearLayoutManager.VERTICAL) {
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

        //divide
        if (mDivideWH != 0) {
          int top, bottom, left, right;
          if (mOrientation == LinearLayoutManager.VERTICAL) {
            left = mDividePadding;
            right = mWidth - mDividePadding;
            if (reverseLayout) {
              bottom = child.getTop();
              top = bottom - mDivideWH;
            } else {
              top = child.getBottom();
              bottom = top + mDivideWH;
            }
          } else {
            top = mDividePadding;
            bottom = mHeight - mDividePadding;
            if (reverseLayout) {
              right = child.getLeft();
              left = right - mDivideWH;
            } else {
              left = child.getRight();
              right = left + mDivideWH;
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

      if (mOrientation == LinearLayoutManager.VERTICAL) {
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
    super.getItemOffsets(outRect, view, parent, state);
    //section 为顶部偏移， divide为底部偏移

    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    if (layoutManager instanceof LinearLayoutManager) {
      mIsReverse = ((LinearLayoutManager) layoutManager).getReverseLayout();
      mOrientation = ((LinearLayoutManager) layoutManager).getOrientation();
    }
    int pos = parent.getChildAdapterPosition(view);

    if (mShowSection) {

      int offset = pos == 0 ? mSectionWH : isShowSection(pos) ? mSectionWH + mDivideWH : mDivideWH;

      if (mOrientation == LinearLayoutManager.VERTICAL) {
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
      if (mOrientation == LinearLayoutManager.VERTICAL) {
        if (mIsReverse) {
          outRect.top = mDivideWH;
        } else {
          outRect.bottom = mDivideWH;
        }
      } else {
        if (mIsReverse) {
          outRect.left = mDivideWH;
        } else {
          outRect.right = mDivideWH;
        }
      }
    }
  }


  private float getTextX(int left, int right) {

    if (mOrientation == RecyclerView.HORIZONTAL || mIsTextCenter) {
      mTextPaint.setTextAlign(Paint.Align.CENTER);
      return (left + right) / 2;
    } else {
      mTextPaint.setTextAlign(Paint.Align.LEFT);
      return mSectionPadding + mSectionTextPadding;
    }
  }

  private float getTextY(int top, int bottom) {
    if (mOrientation == RecyclerView.VERTICAL || mIsTextCenter) {
      return (bottom + top) / 2 + textDistance;
    } else {
      return mSectionPadding + mSectionTextPadding + textHeight;
    }
  }





}
