package com.cafejeunesse.android.fragment.regroupements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.Regroupment;

import java.util.ArrayList;

/**
 * Created by Gabriel Le Breton on 12/04/15.
 */
public class RegroupmentsArrayAdapter extends ArrayAdapter<Regroupment> {

    public RegroupmentsArrayAdapter(Context context, ArrayList<Regroupment> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Regroupment n = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_regroupments_list_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.regroupment_title)).setText(n.toString());
        ((TextView) convertView.findViewById(R.id.regroupment_url)).setText(n.getUrl());

        // Return the completed view to render on screen
        return convertView;
    }
}
