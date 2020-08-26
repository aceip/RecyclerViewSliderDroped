package com.slider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class ElasticHorizontalScrollView extends HorizontalScrollView {
  private float x;
  private int threshold = 0;

  public ElasticHorizontalScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);

    DisplayMetrics metrics = getResources().getDisplayMetrics();
  }

  public ElasticHorizontalScrollView(Context context) {
    this(context,null);
  }

  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    if (ev == null) {
      return super.onTouchEvent(ev);
    } else {
      return commOnTouchEvent(ev);
    }
  }

  public void reset(){
    scrollTo(0,0);
  }

  private boolean commOnTouchEvent(MotionEvent ev) {
    int action = ev.getAction();
    int length = threshold;
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        x = ev.getX();
        break;
      case MotionEvent.ACTION_UP:
        //复原位置
       if((ev.getX() - x)>0){
         if(getScrollX()>length/2){
           smoothScrollTo(length,0);
         }else {
           smoothScrollTo(0,0);
         }
       }else {
         if(getScrollX()>length/2){
           smoothScrollTo(length,0);
         }else {
           smoothScrollTo(0,0);
         }
       }
       return true;
      case MotionEvent.ACTION_MOVE:
          return super.onTouchEvent(ev);
      default:
        return true;

    }
    return true;
  }
}