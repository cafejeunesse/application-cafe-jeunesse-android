package com.cafejeunesse.android.fragment.servicefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;

/**
 * Created by David Levayer on 12/03/15.
 */
public class ServiceFragment extends BasicFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Chargement générique des fragments de l'application
        initFragment();

        // Chargement spécifique au fragment
        mView = inflater.inflate(R.layout.servicefragment_main, container, false);

        return mView;
    }
}
