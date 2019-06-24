package com.cxystyle.itemdecorationlib;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cxystyle.itemdecorationlib.builder.BaseBuilder;
import com.cxystyle.itemdecorationlib.callback.OnSectionClickListener;
import com.cxystyle.itemdecorationlib.callback.SectionGestureDetector;
import com.cxystyle.itemdecorationlib.callback.SectionTitleCallback;

public class SuperItemDecoration extends RecyclerView.ItemDecoration {

  private SectionGestureDetector sectionGestureDetector;

  protected SparseArray mSectionTops;

  protected int mWidth;
  protected int mHeight;

  protected boolean mShowSection;
  protected int mSectionWH;
  protected int mSectionPadding;
  protected int mSectionTextPadding;
  protected int mSectionColor;
  protected int mSectionTextSize;
  protected int mSectionTextColor;
  protected boolean mIsStick;

  protected int mDivideWH;
  protected int mDividePadding;
  protected int mDivideColor;

  protected Paint mSectionPaint;
  protected Paint mDividePaint;
  protected Paint mTextPaint;

  protected SectionTitleCallback mCallback;

  protected OnSectionClickListener mSectionClickListener;

  protected boolean mIsReverse;

  protected int mOrientation;

  protected boolean mIsTextCenter;

  public SuperItemDecoration(BaseBuilder builder) {
    mSectionTops = new SparseArray();
    init(builder);
  }

  private void init(BaseBuilder builder) {
    this.mShowSection = builder.isShowSection();

    this.mCallback = builder.getCallback();
    this.mSectionClickListener = builder.getOnSectionClickListener();

    this.mSectionWH = builder.getSectionWH();
    this.mSectionPadding = builder.getSectionPadding();
    this.mSectionTextPadding = builder.getSectionTextPadding();
    this.mSectionColor = builder.getSectionColor();
    this.mSectionTextSize = builder.getSectionTextSize();
    this.mSectionTextColor = builder.getSectionTextColor();
    this.mIsStick = builder.isStick();

    this.mDivideWH = builder.getDivideWH();
    this.mDividePadding = builder.getDividePadding();
    this.mDivideColor = builder.getDivideColor();


    mSectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mSectionPaint.setColor(mSectionColor);

    mDividePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mDividePaint.setColor(mDivideColor);

    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mTextPaint.setColor(mSectionTextColor);
    mTextPaint.setTextSize(mSectionTextSize);
    mTextPaint.setTextAlign(Paint.Align.LEFT);

    mIsTextCenter = mSectionTextPadding < 0 ? true : false;

  }

  @Override public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);
    mSectionTops.clear();
    mWidth = parent.getWidth();
    mHeight = parent.getHeight();
  }

  @Override public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDrawOver(c, parent, state);

    if (sectionGestureDetector == null){
      sectionGestureDetector = new SectionGestureDetector(parent.getContext(), mOrientation,
          mSectionTops, mSectionClickListener, mSectionWH);
      parent.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
          return sectionGestureDetector.onTouchEvent(e);
        }
      });
    }else{
      sectionGestureDetector.setOri(mOrientation);
    }
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
  }


  protected String getText(int pos){
    if (mCallback != null){
      String sectionTitle = mCallback.getSectionTitle(pos);
      if (sectionTitle != null && !TextUtils.isEmpty(sectionTitle)){
        return sectionTitle;
      }else{
        return "";
      }
    }
    return "";
  }


  protected boolean isShowSection(int position) {
    if (position == 0) {
      return true;
    } else {
      String text = getText(position - 1);
      String text1 = getText(position);
      if (text.equals(text1)) {
        return false;
      } else {
        return true;
      }
    }
  }

}
