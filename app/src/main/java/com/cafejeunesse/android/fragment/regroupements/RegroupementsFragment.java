package com.cafejeunesse.android.fragment.regroupements;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.Regroupment;

import java.util.ArrayList;

/**
 * Created by Gabriel Le Breton on 11/04/15.
 */
public class RegroupementsFragment extends BasicFragment {

    private final ArrayList<Regroupment> regroupments;

    public RegroupementsFragment() {
        regroupments = getRegroupments();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initFragment(inflater, container, R.layout.fragment_regroupements_main);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView regroupmentsListView = (ListView) mView.findViewById(R.id.regroupments_listview);
        RegroupmentsArrayAdapter mListViewAdapter = new RegroupmentsArrayAdapter(mContext, regroupments);
        regroupmentsListView.setAdapter(mListViewAdapter);

        regroupmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Regroupment item = regroupments.get(position);
                String url = item.getUrl();
                Uri uri = Uri.parse(url);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    // todo: store this online and download it
    public ArrayList<Regroupment> getRegroupments() {
        ArrayList<Regroupment> regroupments = new ArrayList<>();
        regroupments.add(new Regroupment("Regroupement des organismes communautaires autonomes jeunesse du Québec", "ROCAJQ", "http://rocajq.org/"));
        regroupments.add(new Regroupment("Corporation de développement communautaire du Roc", "CDC du Roc", "http://www.cdcduroc.com/"));
        regroupments.add(new Regroupment("Regroupement des cuisines collectives du Québec", "RCCQ", "http://www.rccq.org/fr/"));
        regroupments.add(new Regroupment("Table régionale des organismes communautaires du Saguenay-Lac-Saint-Jean", "TROC02", "http://www.troc02.org/"));
        regroupments.add(new Regroupment("Mouvement d’éducation populaire et d’action communautaire du Québec", "MÉPACQ", "http://www.mepacq.qc.ca/"));
        return regroupments;
    }
}
