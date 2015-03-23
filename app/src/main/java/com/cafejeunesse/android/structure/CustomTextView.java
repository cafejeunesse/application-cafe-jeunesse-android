package com.cafejeunesse.android.structure;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;

/**
 * Created by David Levayer on 18/03/15.
 */
public class CustomTextView extends TextView {

    private int mFragmentId;

    private static int[] HOME_FRAGMENT = {R.attr.homeFragment};
    private static int[] SERVICE_FRAGMENT = {R.attr.serviceFragment};
    private static int[] CAFE_FRAGMENT = {R.attr.cafeFragment};
    private static int[] GOOGLEMAP_FRAGMENT = {R.attr.googlemapFragment};

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {

        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        switch(mFragmentId){
            case BasicFragment.HOMEFRAGMENT_ID:
                mergeDrawableStates(drawableState, HOME_FRAGMENT);
                break;
            case BasicFragment.SERVICEFRAGMENT_ID:
                mergeDrawableStates(drawableState, SERVICE_FRAGMENT);
                break;
            case BasicFragment.CAFEFRAGMENT_ID:
                mergeDrawableStates(drawableState, CAFE_FRAGMENT);
                break;
            case BasicFragment.GOOGLEMAPFRAGMENT_ID:
                mergeDrawableStates(drawableState, GOOGLEMAP_FRAGMENT);
                break;
            default:
                return super.onCreateDrawableState(extraSpace);
        }
        return drawableState;
    }

    public int getFragmentId() {
        return mFragmentId;
    }

    public void setFragmentId(int mFragmentName) {
        this.mFragmentId = mFragmentName;
        //refreshDrawableState();
    }
}
