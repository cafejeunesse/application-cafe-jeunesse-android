package com.cafejeunesse.android.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cafejeunesse.android.common.view.SlidingTabLayout;
import com.cafejeunesse.android.common.view.ViewPagerWithHeight;
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

    public final static String HOME_TITLE = "hometitle";
    public final static String HOME_DESCR = "homedescription";

    private ViewGroup mContainerView;
    private View mView;
    private Context mContext;
    private ViewPagerWithHeight mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    private final static String CALENDAR_URL =
            "https://www.google.com/calendar/feeds/fvp8k6fun93m32q8pe972sosps%40group.calendar.google.com/public/basic";

    private final static String SCHEDULE_URL =
            "https://www.google.com/calendar/feeds/cafejeunessedechicoutimi%40gmail.com/public/basic";

    private final static String CALENDAR_FILEPATH = Environment.getExternalStorageDirectory().getPath()+"/calendar.xml";

    private final static String SCHEDULE_FILEPATH = Environment.getExternalStorageDirectory().getPath()+"/horaires.xml";

    private final static int CALENDAR_TAB_INDEX = 0;
    private final static String CALENDAR_TAB_NAME = "Actualités";

    private final static int SCHEDULE_TAB_INDEX = 1;
    private final static String SCHEDULE_TAB_NAME = "Horaires";

    public final static long TIME_BETWEEN_DOWNLOADS = 86400000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home, container, false);

        // Chargement spécifique au fragment
        mContext = getActivity();

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mViewPager = (ViewPagerWithHeight) mView.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());

        final View pagerContainer = (View) mView.findViewById(R.id.viewpagercontainer);
        pagerContainer.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.setContainerHeight(pagerContainer.getMeasuredHeight());
            }
        });

        mSlidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                mViewPager.requestLayout();
                mViewPager.invalidate();
            }
        });

        testAndDownload(HomeFragment.CALENDAR_FILEPATH, HomeFragment.CALENDAR_URL);
        testAndDownload(HomeFragment.SCHEDULE_FILEPATH, HomeFragment.SCHEDULE_URL);
    }

    private void testAndDownload(String filepath, String url){

        // Téléchargement (si besoin) des calendriers
        File f = new File(filepath);

        boolean download = false;
        // Si le fichier n'est pas présent
        if(!f.exists() || !f.isFile()) download = true;
            // Si le fichier est trop vieux
        else if((new Date().getTime() - f.lastModified())>HomeFragment.TIME_BETWEEN_DOWNLOADS) download = true;

        if(download){
            startDownload(url,filepath);
        } else {
            refresh();
        }
    }

    private void startDownload(String url, String fileName){
        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(mContext, this);
        downloadTask.execute(url,fileName);
    }

    private void reloadNews(int tabIndex){

        if(mContainerView == null)
            return;

        mContainerView.removeAllViews();
        List<News> mNews = null;

        try {

            switch(tabIndex){
                case CALENDAR_TAB_INDEX:
                    mNews = new NewsParser().parseFileForNews(HomeFragment.CALENDAR_FILEPATH);
                    break;
                case SCHEDULE_TAB_INDEX:
                    mNews = new NewsParser().parseFileForNews(HomeFragment.SCHEDULE_FILEPATH);
                    break;
                default:
            }

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

    private void addNews(final News n){

        ViewGroup newView = (ViewGroup) LayoutInflater.from(mView.getContext()).inflate(
                R.layout.list_item_home, mContainerView, false);

        ((TextView) newView.findViewById(R.id.home_title)).setText(n.getTitle());
        ((TextView) newView.findViewById(R.id.home_description)).setText(n.getArticle());
        ((TextView) newView.findViewById(R.id.home_date_day)).setText(n.getPublishingDay());
        ((TextView) newView.findViewById(R.id.home_date_month)).setText(n.getPublishingMonth());

        newView.setOnTouchListener(new View.OnTouchListener() {

            // TODO better way ? Can't swipe when on Title
            private float incertitude = 20;
            private float lastX, lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int move = event.getAction();

                switch(move){
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getX();
                        lastY = event.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float diffX = Math.abs(lastX-event.getX());
                        float diffY = Math.abs(lastY-event.getY());
                        if(diffX < incertitude && diffY < incertitude){
                            Bundle b = new Bundle();
                            b.putString(HOME_TITLE,n.getTitle());
                            b.putString(HOME_DESCR,n.getArticle());

                            FragmentManager fm = me().getFragmentManager();
                            HomeDialogFragment mDialogFragment = new HomeDialogFragment();
                            mDialogFragment.setArguments(b);
                            mDialogFragment.show(fm,"home_dialog_fragment");
                            return true;
                        }
                    default:
                        return false;
                }
            }
        });

        /*
        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString(HOME_TITLE,n.getTitle());
                b.putString(HOME_DESCR,n.getArticle());

                FragmentManager fm = me().getFragmentManager();
                HomeDialogFragment mDialogFragment = new HomeDialogFragment();
                mDialogFragment.setArguments(b);
                mDialogFragment.show(fm,"home_dialog_fragment");
            }
        });
        */

        mContainerView.addView(newView, 0);
    }

    private BasicFragment me() {
        return this;
    }

    public void refresh(){
        int tabNumber = mViewPager.getChildCount();
        for(int i=0; i<tabNumber; i++)
            reloadNews(i);
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case CALENDAR_TAB_INDEX:
                    return CALENDAR_TAB_NAME;
                case SCHEDULE_TAB_INDEX:
                    return SCHEDULE_TAB_NAME;
            }
            return "noName";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_home_pager,
                    container, false);

            mContainerView = (ViewGroup) view.findViewById(R.id.news_container);
            reloadNews(position);

            // Add the newly created View to the ViewPager
            container.addView(view);

            // Return the View
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }



    }

}
