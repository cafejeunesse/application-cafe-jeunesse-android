package com.cafejeunesse.android.fragment.cafe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;

/**
 * Created by David Levayer on 07/04/15.
 */
public class CafeLifeRulesFragment extends BasicFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initFragment(inflater,container, R.layout.fragment_cafe_liferules);
        return mView;
    }
}
