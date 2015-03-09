package com.cafejeunesse.android.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.cafejeunesse.android.navigationdrawer.R;

/**
 * Created by David Levayer on 09/03/15.
 */
public class HomeDialogFragment extends DialogFragment {

    private TextView title, content;
    private final static float dialogSize = 0.9f;

    public HomeDialogFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_item_home_details, container, false);

        this.title = (TextView) view.findViewById(R.id.home_details_title);
        this.content = (TextView) view.findViewById(R.id.home_details_description);

        Bundle b = getArguments();
        this.title.setText(b.getString(HomeFragment.HOME_TITLE));
        this.content.setText(b.getString(HomeFragment.HOME_DESCR));

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
