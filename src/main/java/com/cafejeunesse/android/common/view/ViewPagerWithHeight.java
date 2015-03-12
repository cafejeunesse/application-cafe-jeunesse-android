package com.cafejeunesse.android.common.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by David Levayer on 10/03/15.
 */
public class ViewPagerWithHeight extends ViewPager {

    private int containerHeight;

    public ViewPagerWithHeight(Context context) {
        super(context);
    }

    public ViewPagerWithHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(containerHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setContainerHeight(int containerHeight){
        this.containerHeight = containerHeight;
    }
}
