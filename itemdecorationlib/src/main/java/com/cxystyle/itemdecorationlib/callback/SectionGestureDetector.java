package com.cxystyle.itemdecorationlib.callback;

import android.content.Context;
import android.graphics.Point;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

public class SectionGestureDetector extends GestureDetector.SimpleOnGestureListener {

  private GestureDetector gestureDetector;

  private SparseArray<Point> sectionPoints;

  private OnSectionClickListener onSectionClickListener;

  //方向
  private int ori;

  private int sectionWH;

  public SectionGestureDetector(Context context, int ori, SparseArray<Point> sectionPoints, OnSectionClickListener listener, int sectionWH) {
    gestureDetector = new GestureDetector(context, this);
    this.ori = ori;
    this.sectionPoints = sectionPoints;
    this.onSectionClickListener = listener;
    this.sectionWH = sectionWH;
  }

  public boolean onTouchEvent(MotionEvent e){
    return gestureDetector.onTouchEvent(e);
  }

  @Override public boolean onSingleTapUp(MotionEvent e) {
    for (int i = 0; i < sectionPoints.size(); i++) {
      int pos = sectionPoints.keyAt(i);

      float x = sectionPoints.get(pos).x;
      float y = sectionPoints.get(pos).y;
      float ey = e.getY();
      float ex = e.getX();

      //vertical
      if (ori == RecyclerView.VERTICAL){
        if (ey >= y && ey <= y + sectionWH) {
          if (onSectionClickListener != null) {
            onSectionClickListener.onSectionClick(pos);
            return true;
          }
        }
      }else {
        if (ex >= x && ex <= x + sectionWH){
          if (onSectionClickListener != null){
            onSectionClickListener.onSectionClick(pos);
            return true;
          }
        }
      }

    }
    return super.onSingleTapUp(e);
  }



  public void setOri(int ori){
    this.ori = ori;
  }

}
