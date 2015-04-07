package com.cafejeunesse.android.fragment.cafefragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.fragment.servicefragment.ServiceDetailsFragment;
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
        Button b = (Button) mView.findViewById(R.id.cafe_button_rules);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CafeLifeRulesFragment mDialogFragment = new CafeLifeRulesFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_frame, mDialogFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return mView;
    }
}
