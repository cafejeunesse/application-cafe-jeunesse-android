package com.cafejeunesse.android.fragment.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;

/**
 * Created by Gabriel Le Breton on 11/04/15.
 */
public class AboutFragment extends BasicFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initFragment(inflater, container, R.layout.fragment_about_main);
        return mView;
    }
}
