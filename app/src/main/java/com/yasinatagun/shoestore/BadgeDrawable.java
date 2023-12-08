package com.yasinatagun.shoestore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class BadgeDrawable extends Drawable {
  private Paint mBadgePaint;
  private Paint mTextPaint;
  private Rect mTxtRect = new Rect();

  private String mCount = "";
  private boolean mWillDraw;

  public BadgeDrawable(Context context) {

    mBadgePaint = new Paint();
    mBadgePaint.setColor(Color.RED);
    mBadgePaint.setAntiAlias(true);
    mBadgePaint.setStyle(Paint.Style.FILL);

    mTextPaint = new Paint();
    mTextPaint.setColor(Color.WHITE);
    mTextPaint.setTypeface(Typeface.DEFAULT);
    mTextPaint.setAntiAlias(true);
    mTextPaint.setTextAlign(Paint.Align.CENTER);
  }

  @Override
  public void draw(Canvas canvas) {

    if (!mWillDraw) {
      return;
    }
    Rect bounds = getBounds();
    float width = bounds.right - bounds.left;
    float height = bounds.bottom - bounds.top;

    mTextPaint.setTextSize(width/3);


    float radius = ((Math.max(width, height) / 2)) / 2;
    float centerX = (width - radius - 1) +5;
    float centerY = radius -5;
    if(mCount.length() <= 2){
      canvas.drawCircle(centerX, centerY, (int)(radius+5.5), mBadgePaint);
    }
    else{
      canvas.drawCircle(centerX, centerY, (int)(radius+6.5), mBadgePaint);
    }
    // Draw badge count text inside the circle.
    mTextPaint.getTextBounds(mCount, 0, mCount.length(), mTxtRect);
    float textHeight = mTxtRect.bottom - mTxtRect.top;
    float textY = centerY + (textHeight / 2f);
    if(mCount.length() > 2)
      canvas.drawText("99+", centerX, textY, mTextPaint);
    else
      canvas.drawText(mCount, centerX, textY, mTextPaint);
  }
  public void setCount(String count) {
    mCount = count;

    // Only draw a badge if there are notifications.
    mWillDraw = !count.equalsIgnoreCase("0");
    invalidateSelf();
  }

  @Override
  public void setAlpha(int alpha) {
    // do nothing
  }

  @Override
  public void setColorFilter(ColorFilter cf) {
    // do nothing
  }

  @Override
  public int getOpacity() {
    return PixelFormat.UNKNOWN;
  }
}