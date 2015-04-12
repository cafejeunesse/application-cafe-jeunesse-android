package com.cafejeunesse.android.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.News;

import java.util.ArrayList;

/**
 * Created by David Levayer on 12/03/15.
 */
public class NewsArrayAdapter extends ArrayAdapter<News> {

    public NewsArrayAdapter(Context context, ArrayList<News> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        News n = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_list_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.home_title)).setText(n.getTitle());
        ((TextView) convertView.findViewById(R.id.home_description)).setText(n.getArticle());
        ((TextView) convertView.findViewById(R.id.home_date_day)).setText(n.getPublishingDay());
        ((TextView) convertView.findViewById(R.id.home_date_month)).setText(n.getPublishingMonth());

        // Return the completed view to render on screen
        return convertView;
    }
}
