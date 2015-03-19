package com.cafejeunesse.android.navigationdrawer;

/**
 * Created by David Levayer on 18/03/15.
 */
public class NavDrawerItem {

    private int fragmentId;
    private String text;

    public NavDrawerItem(int fragmentId, String text){
        this.fragmentId = fragmentId;
        this.text = text;
    }

    public int getFragmentId(){
        return fragmentId;
    }

    public String getText(){
        return text;
    }
}
