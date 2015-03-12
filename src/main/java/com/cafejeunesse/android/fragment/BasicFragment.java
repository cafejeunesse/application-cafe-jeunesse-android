package com.cafejeunesse.android.fragment;

import android.app.Fragment;
import android.content.Context;
import android.view.View;

/**
 * Created by David Levayer on 17/02/15.
 */
public abstract class BasicFragment extends Fragment {

    protected View mView;
    protected Context mContext;

    protected final void initFragment(){
        mContext = getActivity();
    }

}
