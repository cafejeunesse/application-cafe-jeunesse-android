package com.cafejeunesse.android.fragment;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cafejeunesse.android.structure.CustomTextView;

/**
 * Created by David Levayer on 17/02/15.
 */
public abstract class BasicFragment extends Fragment {

    public final static int FRAGMENT_NUMBER = 7;

    public static final int HOMEFRAGMENT_ID = 0;
    public static final int CAFEFRAGMENT_ID = 1;
    public static final int SERVICEFRAGMENT_ID = 2;
    public static final int GOOGLEMAPFRAGMENT_ID = 3;
    public static final int REGROUPEMENTS_ID = 4;
    public static final int ABOUTFRAGMENT_ID = 5;
    public static final int PARAMETERSFRAGMENT_ID = 6;

    protected View mView;
    protected Context mContext;
    private CustomTextView mCustomLayout;

    protected final void initFragment(LayoutInflater inflater, ViewGroup container, int viewId){

        mCustomLayout = null;
        mContext = getActivity();
        mView = inflater.inflate(viewId,container,false);
    }
}
