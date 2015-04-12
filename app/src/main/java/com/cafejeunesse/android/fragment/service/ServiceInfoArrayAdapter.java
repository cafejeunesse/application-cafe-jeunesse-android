package com.cafejeunesse.android.fragment.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.ServiceInfoElement;

import java.util.ArrayList;

/**
 * Created by David Levayer on 16/03/15.
 */
public class ServiceInfoArrayAdapter extends ArrayAdapter<ServiceInfoElement> {

    public ServiceInfoArrayAdapter(Context context, ArrayList<ServiceInfoElement> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        ServiceInfoElement sie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_service_list_item_details_list, parent, false);
        }

        ((ImageView) convertView.findViewById(R.id.service_details_icon)).setImageResource(sie.getImageResource());
        ((TextView) convertView.findViewById(R.id.service_details_type)).setText(sie.getTag());
        ((TextView) convertView.findViewById(R.id.service_details_title)).setText((String) sie.getValue());

        // Return the completed view to render on screen
        return convertView;
    }
}
