package com.cafejeunesse.android.navigationdrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cafejeunesse.android.structure.CustomTextView;

/**
 * Created by David Levayer on 18/03/15.
 */
public class DrawerAdapter extends ArrayAdapter<NavDrawerItem> {

    public DrawerAdapter(Context context, int resource, NavDrawerItem[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        NavDrawerItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item_drawer, parent, false);
        }

        CustomTextView mCustomView = (CustomTextView) convertView.findViewById(R.id.text_item);
        mCustomView.setText(item.getText());
        mCustomView.setFragmentId(item.getFragmentId());

        // Return the completed view to render on screen
        return convertView;
    }
}
