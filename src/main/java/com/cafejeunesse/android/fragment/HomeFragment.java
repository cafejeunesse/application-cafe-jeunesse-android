package com.cafejeunesse.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.News;

import java.util.Date;

/**
 * Created by David Levayer on 17/02/15.
 */
public class HomeFragment extends BasicFragment {

    private ViewGroup mContainerView;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home, container, false);

        // Chargement spécifique au fragment
        mContainerView = (ViewGroup) mView.findViewById(R.id.news_container);

        Date d1 = new Date();

        d1.setDate(2);
        d1.setMonth(0);
        addNews(new News("Création de l'application", d1,
                "Une application Android pour découvrir les services"));

        d1.setDate(12);
        d1.setMonth(0);
        addNews(new News("Ouverture du Café", d1,
                "Consultez les nouveaux horaires d'ouverture"));

        d1.setDate(23);
        d1.setMonth(0);
        addNews(new News("Projet d'habitation du comité des jeunes", d1,
                "Venez découvrir les nouveaux outils mis à disposition"));

        addNews(new News("Sécurité alimentaire - nouveau site", new Date(),
                "Toutes les informations utiles sur ce nouveau lieu"));

        return mView;
    }

    private void addNews(News n){

        ViewGroup newView = (ViewGroup) LayoutInflater.from(mView.getContext()).inflate(
                R.layout.list_item_home, mContainerView, false);

        ((TextView) newView.findViewById(R.id.home_title)).setText(n.getTitle());
        ((TextView) newView.findViewById(R.id.home_description)).setText(n.getArticle());
        ((TextView) newView.findViewById(R.id.home_date_day)).setText(n.getPublishingDay());
        ((TextView) newView.findViewById(R.id.home_date_month)).setText(n.getPublishingMonth());

        mContainerView.addView(newView, 0);
    }
}
