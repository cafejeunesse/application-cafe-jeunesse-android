package com.cafejeunesse.android.fragment.servicefragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
public class ServiceDialogFragment extends DialogFragment {

    private final static float dialogSize = 1f;
    private ListView mListView;

    public ServiceDialogFragment(){

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null) {
            return;
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int dialogHeight = (int)(displaymetrics.heightPixels * dialogSize);
        int dialogWidth = (int)(displaymetrics.widthPixels * dialogSize);

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

    }
}
