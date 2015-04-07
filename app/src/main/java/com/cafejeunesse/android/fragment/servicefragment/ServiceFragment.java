package com.cafejeunesse.android.fragment.servicefragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cafejeunesse.android.database.ServiceDataSource;
import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.Service;

import java.util.ArrayList;
import java.util.List;

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
        // TODO refactor pour rendre l'import transparent ?
        ServiceDataSource mDataSource = new ServiceDataSource(getActivity());
        mDataSource.open();
        List<Service> values = mDataSource.getAllServices();

        if(values.size() == 0) {
            mDataSource.importDataFromAsset(ServiceDataSource.NEWDB_FILEPATH);
            values = mDataSource.getAllServices();
        }

        for(Service s: values) {
            String name = s.getServiceName();
            if (name != null && !name.isEmpty())
                mListViewAdapter.add(s);
        }

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
