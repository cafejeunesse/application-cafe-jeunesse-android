package com.cafejeunesse.android.fragment.servicefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.Service;

import java.util.List;

/**
 * Created by David Levayer on 12/03/15.
 */
public class ServiceArrayAdapter extends ArrayAdapter<Service> {

    public ServiceArrayAdapter(Context context, List<Service> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Service s = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.servicefragment_list_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.service_name)).setText(s.getName());
        ((TextView) convertView.findViewById(R.id.service_information)).setText(s.getInformation());

        // Return the completed view to render on screen
        return convertView;
    }
}
