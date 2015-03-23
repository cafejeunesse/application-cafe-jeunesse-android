package com.cafejeunesse.android.fragment.servicefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.Service;
import com.cafejeunesse.android.structure.ServiceInfoElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by David Levayer on 12/03/15.
 */
public class ServiceDetailsFragment extends Fragment {

    private final static float dialogSize = 1f;
    private ListView mListView;

    public ServiceDetailsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.servicefragment_list_item_details, container, false);

        Bundle b = getArguments();
        String title = b.getString(Service.SERVICE_TITLE);
        String description = b.getString(Service.SERVICE_DESCR);
        HashMap<String,Object> mInfo = (HashMap)b.getSerializable(Service.SERVICE_MAP_INFO);

        // Chargement du titre et de la description
        ((TextView)view.findViewById(R.id.service_details_title)).setText(title);
        ((TextView)view.findViewById(R.id.service_details_description)).setText(description);

        // Chargement de la liste des informations
        mListView = (ListView) view.findViewById(R.id.listview_services_details);

        ServiceInfoArrayAdapter mServiceInfoAdapter =
                new ServiceInfoArrayAdapter(getActivity().getApplicationContext(),
                new ArrayList<ServiceInfoElement>());

        mListView.setAdapter(mServiceInfoAdapter);

        for(Map.Entry<String,Object> entry : mInfo.entrySet()){

            if(!(entry.getValue() instanceof String))
                continue;

            ServiceInfoElement mElement = new ServiceInfoElement(entry.getKey(),entry.getValue());
            mServiceInfoAdapter.add(mElement);
        }

        return view;
    }
}
