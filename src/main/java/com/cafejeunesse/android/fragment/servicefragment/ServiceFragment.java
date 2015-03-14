package com.cafejeunesse.android.fragment.servicefragment;

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
        initFragment();

        // Chargement spécifique au fragment
        mView = inflater.inflate(R.layout.servicefragment_main, container, false);
        mListView = (ListView)mView.findViewById(R.id.listview_services);
        mListViewAdapter = new ServiceArrayAdapter(mContext, new ArrayList<Service>());
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnItemClickListener(this);

        // TODO remove when SQL-Lite implemented
        Service s = new Service();
        s.setName("Café Jeunesse de Chicoutimi");
        s.setInformation("Milieu de vie adressé aux jeunes adultes de 18 à 30 ans.");
        mListViewAdapter.add(s);

        s = new Service();
        s.setName("Service de travail de rue de Chicoutimi");
        s.setInformation("Offre des services dans les lieux de regroupement naturels" +
                " et les milieux de vie des aux personnes de 12 ans et plus");
        mListViewAdapter.add(s);

        return mView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO
    }
}
