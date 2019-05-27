package com.cxystyle.itemdecorationlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView 的LinearLayoutManager 万能分割线。 可以设置Section(分类title)和分割线。
 *
 * @author cxy
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

  private Context context;

  private int width;
  private int height;

  private int sectionHeight;
  private int sectionPadding;
  private int sectionTextPadding;
  private int sectionColor;
  private int sectionTextSize;
  private int sectionTextColor;
  private boolean isStick;

  private int divideHeight;
  private int dividePadding;
  private int divideColor;

  private Paint sectionPaint;
  private Paint dividePaint;
  private Paint textPaint;

  private SectionCallback callback;

  //文字基线到文字中心的距离
  private float textDistance;
  //文字的高度
  private float textHeight;

  private boolean reverseLayout;

  private int orientation;

  private boolean showSection;

  private boolean isTextCenter;

  private LinearItemDecoration(Builder builder) {
    super();

    this.showSection = builder.showSection;

    this.context = builder.context;

    this.callback = builder.callback;

    this.sectionHeight = builder.sectionHeight;
    this.sectionPadding = builder.sectionPadding;
    this.sectionTextPadding = builder.sectionTextPadding;
    this.sectionColor = builder.sectionColor;
    this.sectionTextSize = builder.sectionTextSize;
    this.sectionTextColor = builder.sectionTextColor;
    this.isStick = builder.isStick;

    this.divideHeight = builder.divideHeight;
    this.dividePadding = builder.dividePadding;
    this.divideColor = builder.divideColor;

    isTextCenter = sectionTextPadding < 0 ? true : false;

    init();
  }

  /**
   * 初始化
   */
  private void init() {

    sectionHeight = dp2px(context, sectionHeight);
    sectionPadding = dp2px(context, sectionPadding);
    sectionTextPadding = dp2px(context, sectionTextPadding);
    sectionTextSize = sp2px(context, sectionTextSize);

    divideHeight = dp2px(context, divideHeight);
    dividePadding = dp2px(context, dividePadding);

    initPaint();
  }

  /**
   * 初始化画笔
   */
  private void initPaint() {
    sectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    sectionPaint.setColor(sectionColor);

    dividePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    dividePaint.setColor(divideColor);

    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    textPaint.setColor(sectionTextColor);
    textPaint.setTextSize(sectionTextSize);
    textPaint.setTextAlign(Paint.Align.LEFT);
    Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
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

      width = parent.getWidth();
      height = parent.getHeight();

      int childCount = parent.getChildCount();
      for (int i = 0; i < childCount; i++) {
        View child = parent.getChildAt(i);
        int position = parent.getChildAdapterPosition(child);

        //section
        if (showSection && isShowSection(position)) {
          int left, top, right, bottom;
          if (orientation == LinearLayoutManager.VERTICAL) {
            //VERTICAL
            left = sectionPadding;
            right = width - sectionPadding;
            if (reverseLayout) {
              bottom = child.getBottom() + sectionHeight;
              top = child.getBottom();
            } else {
              top = child.getTop() - sectionHeight;
              bottom = child.getTop();
            }
          } else {
            //HORIZONTAL
            top = sectionPadding;
            bottom = height - sectionPadding;
            if (reverseLayout) {
              right = child.getRight() + sectionHeight;
              left = child.getRight();
            } else {
              left = child.getLeft() - sectionHeight;
              right = child.getLeft();
            }
          }
          c.drawRect(left, top, right, bottom, sectionPaint);
          c.drawText(getText(position), getTextX(left, right),
              getTextY(top, bottom), textPaint);
        }

        //divide
        if (divideHeight != 0) {
          int top, bottom, left, right;
          if (orientation == LinearLayoutManager.VERTICAL) {
            left = dividePadding;
            right = width - dividePadding;
            if (reverseLayout) {
              bottom = child.getTop();
              top = bottom - divideHeight;
            } else {
              top = child.getBottom();
              bottom = top + divideHeight;
            }
          } else {
            top = dividePadding;
            bottom = height - dividePadding;
            if (reverseLayout) {
              right = child.getLeft();
              left = right - divideHeight;
            } else {
              left = child.getRight();
              right = left + divideHeight;
            }
          }
          c.drawRect(left, top, right, bottom, dividePaint);
        }
      }
    }
  }

  @Override public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    super.onDrawOver(c, parent, state);
    Log.d("ggg", "onDrawOver");

    if (showSection && isStick) {

      View child = parent.getChildAt(0);
      int pos = parent.getChildAdapterPosition(child);

      int left, top, right, bottom, distance;

      if (orientation == LinearLayoutManager.VERTICAL) {
        left = sectionPadding;
        right = width - sectionPadding;

        if (reverseLayout) {
          distance = height - child.getTop();
          top = height - sectionHeight;
          bottom = height;

          if (distance <= sectionHeight && isShowSection(pos + 1)) {
            top = height - distance;
            bottom = top + sectionHeight;
          }
        } else {
          distance = child.getBottom();
          top = 0;
          bottom = sectionHeight;

          if (distance <= sectionHeight && isShowSection(pos + 1)) {
            top = distance - sectionHeight;
            bottom = distance;
          }
        }
      } else {
        top = sectionPadding;
        bottom = height - sectionPadding;

        if (reverseLayout) {
          distance = width - child.getLeft();
          left = width - sectionHeight;
          right = width;

          if (distance <= sectionHeight && isShowSection(pos + 1)) {
            left = width - distance;
            right = left + sectionHeight;
          }
        } else {
          distance = child.getRight();
          left = 0;
          right = sectionHeight;

          if (distance <= sectionHeight && isShowSection(pos + 1)) {
            left = distance - sectionHeight;
            right = distance;
          }
        }
      }

      c.drawRect(left, top, right, bottom, sectionPaint);
      c.drawText(getText(pos), getTextX(left, right), getTextY(top, bottom),
          textPaint);

    }
  }

  @Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
      @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    //section 为顶部偏移， divide为底部偏移

    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    if (layoutManager instanceof LinearLayoutManager) {
      reverseLayout = ((LinearLayoutManager) layoutManager).getReverseLayout();
      orientation = ((LinearLayoutManager) layoutManager).getOrientation();
    }
    int pos = parent.getChildAdapterPosition(view);

    if (showSection) {

      int offset = isShowSection(pos) ? sectionHeight : divideHeight;

      if (orientation == LinearLayoutManager.VERTICAL) {
        if (reverseLayout) {
          outRect.bottom = offset;
        } else {
          outRect.top = offset;
        }
      } else {
        if (reverseLayout) {
          outRect.right = offset;
        } else {
          outRect.left = offset;
        }
      }
    } else {
      if (orientation == LinearLayoutManager.VERTICAL) {
        if (reverseLayout) {
          outRect.top = divideHeight;
        } else {
          outRect.bottom = divideHeight;
        }
      } else {
        if (reverseLayout) {
          outRect.left = divideHeight;
        } else {
          outRect.right = divideHeight;
        }
      }
    }
  }


  private String getText(int pos){
    if (callback != null){
      String sectionTitle = callback.getSectionTitle(pos);
      if (sectionTitle != null && !TextUtils.isEmpty(sectionTitle)){
        return sectionTitle;
      }else{
        return "";
      }
    }
    return "";
  }

  private int getTextX(int left, int right) {

    if (orientation == RecyclerView.HORIZONTAL || isTextCenter) {
      textPaint.setTextAlign(Paint.Align.CENTER);
      return (left + right) / 2;
    } else {
      textPaint.setTextAlign(Paint.Align.LEFT);
      return sectionPadding + sectionTextPadding;
    }
  }

  private float getTextY(int top, int bottom) {
    if (orientation == RecyclerView.VERTICAL || isTextCenter) {
      return (bottom + top) / 2 + textDistance;
    } else {
      return sectionPadding + sectionTextPadding + textHeight;
    }
  }

  private boolean isShowSection(int position) {
    if (position == 0) {
      return true;
    } else {
      if (getText(position - 1).equals(getText(position))) {
        return false;
      } else {
        return true;
      }
    }
  }

  public static int dp2px(Context context, float dp) {
    return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
  }

  public static int sp2px(Context context, float sp) {
    return (int) (context.getResources().getDisplayMetrics().scaledDensity * sp + 0.5f);
  }

  public static class Builder {

    private static final int DEFAULT_SECTION_HEIGHT = 32;
    private static final int DEFAULT_SECTION_PADDING = 0;
    private static final int DEFAULT_SECTION_TEXT_PADDING = 0;
    private static final int DEFAULT_SECTION_COLOR = Color.parseColor("#c6c6c6");
    private static final int DEFAULT_SECTION_TEXT_SIZE = 14;
    private static final int DEFAULT_SECTION_TEXT_COLOR = Color.parseColor("#999999");
    private static final boolean DEFAULT_IS_STICK = false;
    private static final int DEFAULT_DIVIDE_HEIGHT = 10;
    private static final int DEFAULT_DIVIDE_PADDING = 16;
    private static final int DEFAULT_DIVIDE_COLOR = Color.parseColor("#c6c6c6");

    private Context context;

    private int sectionHeight = DEFAULT_SECTION_HEIGHT;
    private int sectionPadding = DEFAULT_SECTION_PADDING;
    private int sectionTextPadding = DEFAULT_SECTION_TEXT_PADDING;
    private int sectionColor = DEFAULT_SECTION_COLOR;
    private int sectionTextSize = DEFAULT_SECTION_TEXT_SIZE;
    private int sectionTextColor = DEFAULT_SECTION_TEXT_COLOR;
    private boolean isStick = DEFAULT_IS_STICK;

    private int divideHeight = DEFAULT_DIVIDE_HEIGHT;
    private int dividePadding = DEFAULT_DIVIDE_PADDING;
    private int divideColor = DEFAULT_DIVIDE_COLOR;

    private SectionCallback callback;

    private boolean showSection;

    public Builder(Context context) {
      this.context = context;
    }

    public Builder setShowSection(boolean showSection) {
      this.showSection = showSection;
      return this;
    }

    /**
     * @param sectionHeight dp
     */
    public Builder setSectionHeight(int sectionHeight) {
      this.sectionHeight = sectionHeight;
      return this;
    }

    /**
     * @param sectionPadding dp
     */
    public Builder setSectionPadding(int sectionPadding) {
      this.sectionPadding = sectionPadding;
      return this;
    }

    /**
     * @param sectionTextPadding dp
     */
    public Builder setSectionTextPadding(int sectionTextPadding) {
      this.sectionTextPadding = sectionTextPadding;
      return this;
    }

    /**
     * @param sectionColor not resource id
     */
    public Builder setSectionColor(int sectionColor) {
      this.sectionColor = sectionColor;
      return this;
    }

    /**
     * @param sectionTextSize sp
     */
    public Builder setSectionTextSize(int sectionTextSize) {
      this.sectionTextSize = sectionTextSize;
      return this;
    }

    /**
     * @param sectionTextColor not resource id
     */
    public Builder setSectionTextColor(int sectionTextColor) {
      this.sectionTextColor = sectionTextColor;
      return this;
    }

    /**
     * 是否吸顶
     */
    public Builder setStick(boolean stick) {
      isStick = stick;
      return this;
    }

    /**
     * @param divideHeight dp
     */
    public Builder setDivideHeight(int divideHeight) {
      this.divideHeight = divideHeight;
      return this;
    }

    /**
     * @param dividePadding dp
     */
    public Builder setDividePadding(int dividePadding) {
      this.dividePadding = dividePadding;
      return this;
    }

    /**
     * @param divideColor not resource id
     */
    public Builder setDivideColor(int divideColor) {
      this.divideColor = divideColor;
      return this;
    }

    /**
     *
     */
    public Builder setCallback(SectionCallback callback) {
      this.callback = callback;
      return this;
    }

    public LinearItemDecoration build(){
      return new LinearItemDecoration(this);
    }



  }

  /**
   * 数据回调
   */
  public interface SectionCallback {
    String getSectionTitle(int pos);
  }

  //@IntDef({TYPE_ONLY_DIVIDE, TYPE_ONLY_SECTION, TYPE_BOTH})
  //@Retention(RetentionPolicy.RUNTIME)
  //public @interface DecorationType{}
}
