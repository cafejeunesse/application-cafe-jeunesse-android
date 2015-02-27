package com.cafejeunesse.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cafejeunesse.android.filemanager.DownloadTask;
import com.cafejeunesse.android.filemanager.NewsParser;
import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.News;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by David Levayer on 17/02/15.
 */
public class HomeFragment extends BasicFragment implements Refreshable {

    private ViewGroup mContainerView;
    private View mView;
    private Context mContext;

    public final static String CALENDAR_URL =
            "https://www.google.com/calendar/feeds/fvp8k6fun93m32q8pe972sosps%40group.calendar.google.com/public/basic";

    public final static String CALENDAR_FILEPATH = Environment.getExternalStorageDirectory().getPath()+"/calendar.xml";

    public final static String SCHEDULE_FILEPATH = Environment.getExternalStorageDirectory().getPath()+"/horaires.xml";

    public final static long TIME_BETWEEN_DOWNLOADS = 86400000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home, container, false);

        // Chargement spécifique au fragment
        mContainerView = (ViewGroup) mView.findViewById(R.id.news_container);
        mContext = getActivity();

        // Téléchargement (si besoin) des calendriers
        File f = new File(HomeFragment.CALENDAR_FILEPATH);

        boolean download = false;
        // Si le fichier n'est pas présent
        if(!f.exists() || !f.isFile()) download = true;
        // Si le fichier est trop vieux
        else if((new Date().getTime() - f.lastModified())>HomeFragment.TIME_BETWEEN_DOWNLOADS) download = true;

        if(download){
            startDownload(HomeFragment.CALENDAR_URL,HomeFragment.CALENDAR_FILEPATH);
        } else {
            reloadNews();
        }

        return mView;
    }

    private void startDownload(String url, String fileName){
        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(mContext, this);
        downloadTask.execute(url,fileName);
    }

    private void reloadNews(){

        mContainerView.removeAllViews();
        List<News> mNews = null;

        try {
            mNews = new NewsParser().parseFileForNews(HomeFragment.CALENDAR_FILEPATH);
            for (News n : mNews)
                addNews(n);
        } catch (FileNotFoundException e){
            // TODO export dans un String.xml
            Toast.makeText(mContext,"Fichier XML introuvable",Toast.LENGTH_LONG).show();
        } catch (XmlPullParserException e){
            // TODO export dans un String.xml
            Toast.makeText(mContext,"Erreur lors de la lecture du fichier XML",Toast.LENGTH_LONG).show();
        } catch (IOException e){
            // TODO export dans un String.xml
            Toast.makeText(mContext,"Erreur lors de l'ouverture du fichier XML",Toast.LENGTH_LONG).show();
        }
    }

    private void addNews(News n){

        ViewGroup newView = (ViewGroup) LayoutInflater.from(mView.getContext()).inflate(
                R.layout.list_item_home, mContainerView, false);

        ((TextView) newView.findViewById(R.id.home_title)).setText(n.getTitle());
        ((TextView) newView.findViewById(R.id.home_description)).setText(n.getArticle());
        ((TextView) newView.findViewById(R.id.home_date_day)).setText(n.getPublishingDay());
        ((TextView) newView.findViewById(R.id.home_date_month)).setText(n.getPublishingMonth());

        newView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO DialogFragment
                Toast.makeText(v.getContext(),((TextView) v.findViewById(R.id.home_title)).getText(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mContainerView.addView(newView, 0);
    }

    public void refresh(){
        reloadNews();
    }
}
