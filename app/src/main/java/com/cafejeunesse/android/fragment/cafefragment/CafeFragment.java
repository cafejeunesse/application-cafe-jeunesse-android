package com.cafejeunesse.android.fragment.cafefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;

/**
 * Created by David Levayer on 18/03/15.
 */
public class CafeFragment extends BasicFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Chargement générique des fragments de l'application
        initFragment(inflater, container, R.layout.cafefragment_main);

        // TODO ajouter un onClikListener sur le bouton afin d'afficher les regles de vie
        // On peut réutiliser le format de fragment "ServiceDetailsFragment"

        return mView;
    }
}
