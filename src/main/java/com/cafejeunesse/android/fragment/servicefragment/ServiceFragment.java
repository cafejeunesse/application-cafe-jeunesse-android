package com.cafejeunesse.android.fragment.servicefragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.Service;

import java.util.ArrayList;

/**
 * Created by David Levayer on 12/03/15.
 */
public class ServiceFragment extends BasicFragment implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ServiceArrayAdapter mListViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Chargement générique des fragments de l'application
        initFragment(inflater, container, R.layout.servicefragment_main);

        // Chargement spécifique au fragment
        mListView = (ListView)mView.findViewById(R.id.listview_services);
        mListViewAdapter = new ServiceArrayAdapter(mContext, new ArrayList<Service>());
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnItemClickListener(this);

        // TODO remove when SQLite implemented
        Service s = new Service(
                "Café Jeunesse de Chicoutimi",
                "Milieu de vie adressé aux jeunes adultes de 18 à 30 ans."
        );
        s.addInfo(Service.TAG_FACEBOOK,"https://www.facebook.com/pages/Cafe-Jeunesse-de-Chicoutimi/748417201846712?fref=ts");
        s.addInfo(Service.TAG_WEBSITE,"http://www.cafejeunesse.com/");
        s.addInfo(Service.TAG_PRICE,"Gratuit, sauf pour les activités et soupers à 2$ et le dépannage alimentaire au coût de 5$ pour 10 dépannages. ");
        s.addInfo(Service.TAG_PHONENUMBER,"418 696-2871");
        s.addInfo(Service.TAG_ADDRESS,"30, rue Jacques-Cartier Ouest, C.P. 8233, Chicoutimi, Québec, G7H 5B7");


        mListViewAdapter.add(s);

        s = new Service(
                "Service de travail de rue de Chicoutimi",
                "Offre des services dans les lieux de regroupement naturels" +
                        " et les milieux de vie des aux personnes de 12 ans et plus"
        );
        s.addInfo(Service.TAG_PHONENUMBER,"418 545-0999");
        s.addInfo(Service.TAG_ADDRESS,"345, rue Petit – C.P. 8154, Chicoutimi (Québec) G7H 5B7");
        mListViewAdapter.add(s);

        return mView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Service s = (Service) parent.getAdapter().getItem(position);

        Bundle b = new Bundle();
        b.putSerializable(Service.SERVICE_MAP_INFO, s.getMapInfo());
        b.putString(Service.SERVICE_TITLE, s.getServiceName());
        b.putString(Service.SERVICE_DESCR, s.getServiceDescription());

        FragmentManager fm = getFragmentManager();
        ServiceDetailsFragment mDialogFragment = new ServiceDetailsFragment();
        mDialogFragment.setArguments(b);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, mDialogFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
