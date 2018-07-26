/*
 * (C) Copyright 2011-2013 li.jamling@gmail.com. 
 *
 * This software is the property of li.jamling@gmail.com.
 * You have to accept the terms in the license file before use.
 *
 */
package cn.ieclipse.af.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.ieclipse.af.R;

public class ViewPagerV4 extends ViewPager {

    private boolean disableWipe = false;

    public ViewPagerV4(Context context) {
        super(context);
    }

    public ViewPagerV4(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerV4);
            disableWipe = a.getBoolean(R.styleable.ViewPagerV4_af_disableWipe, false);

            a.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return !disableWipe && super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return !disableWipe && super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View view = viewOfClickOnScreen(ev);
            if (view != null) {
                int index = indexOfChild(view);
                if (getCurrentItem() != index) {
                    setCurrentItem(indexOfChild(view));
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ScrollLayout) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }

    public void setDisableWipe(boolean disableWipe) {
        this.disableWipe = disableWipe;
    }

    private View viewOfClickOnScreen(MotionEvent ev) {
        int childCount = getChildCount();
        int[] location = new int[2];
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.getLocationOnScreen(location);

            int minX = location[0];
            int minY = getTop();

            int maxX = location[0] + v.getWidth();
            int maxY = getBottom();

            float x = ev.getX();
            float y = ev.getY();

            if ((x > minX && x < maxX) && (y > minY && y < maxY)) {
                return v;
            }
        }
        return null;
    }
}
