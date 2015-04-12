package com.cafejeunesse.android.fragment.home;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cafejeunesse.android.common.view.SlidingTabLayout;
import com.cafejeunesse.android.filemanager.DownloadTask;
import com.cafejeunesse.android.fragment.BasicFragment;
import com.cafejeunesse.android.fragment.Refreshable;
import com.cafejeunesse.android.navigationdrawer.R;
import com.cafejeunesse.android.structure.News;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Created by David Levayer on 17/02/15.
 */
public class HomeFragment extends BasicFragment implements Refreshable, AdapterView.OnItemClickListener {

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    // activités
    private final static String CALENDAR_URL =
        "https://www.google.com/calendar/ical/fvp8k6fun93m32q8pe972sosps%40group.calendar.google.com/public/basic.ics";

    // horaire
    private final static String SCHEDULE_URL =
        "https://www.google.com/calendar/ical/cafejeunessedechicoutimi%40gmail.com/public/basic.ics";

    private final static String APP_FOLDER_PATH = Environment.getExternalStorageDirectory().getPath() + "/cafe-jeunesse";
    private final static String CALENDAR_FILEPATH = APP_FOLDER_PATH + "/calendar.ics";
    private final static String SCHEDULE_FILEPATH = APP_FOLDER_PATH + "/schedule.ics";

    private final static int CALENDAR_TAB_INDEX = 0;
    private final static String CALENDAR_TAB_NAME = "Actualités";

    private final static int SCHEDULE_TAB_INDEX = 1;
    private final static String SCHEDULE_TAB_NAME = "Horaire";

    public final static long TIME_BETWEEN_DOWNLOADS = 86400000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Chargement générique des fragments de l'application
        initFragment(inflater, container, R.layout.fragment_home_main);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);

        File f = new File(HomeFragment.APP_FOLDER_PATH);
        if (!f.exists()) {
            boolean mkdir = f.mkdir();
            if (!mkdir) {
                Toast.makeText(mContext, mContext.getString(R.string.error_on_creating_dir), Toast.LENGTH_LONG).show();
            }
        }
        testAndDownload(HomeFragment.CALENDAR_FILEPATH, HomeFragment.CALENDAR_URL);
        testAndDownload(HomeFragment.SCHEDULE_FILEPATH, HomeFragment.SCHEDULE_URL);

        refresh();
    }

    private void testAndDownload(String filepath, String url) {

        // Téléchargement (si besoin) des calendriers
        File f = new File(filepath);

        // Si le fichier n'est pas présent ou qu'il est trop vieux
        if (!f.exists() || !f.isFile() || (new Date().getTime() - f.lastModified()) > HomeFragment.TIME_BETWEEN_DOWNLOADS)
            startDownload(url, filepath);
    }

    private void startDownload(String url, String fileName) {
        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(mContext, this);
        downloadTask.execute(url, fileName);
    }

    private BasicFragment me() {
        return this;
    }

    public void refresh() {
        // en définissant un nouvel Adapter, on force le ViewPager à recharger les onglets
        mViewPager.setAdapter(new SamplePagerAdapter());
        mSlidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        News n = (News) parent.getAdapter().getItem(position);

        Bundle b = new Bundle();
        b.putString(News.NEWS_TITLE, n.getTitle());
        b.putString(News.NEWS_DESCR, n.getArticle());

        FragmentManager fm = me().getFragmentManager();
        HomeDialogFragment mDialogFragment = new HomeDialogFragment();
        mDialogFragment.setArguments(b);
        mDialogFragment.show(fm, "home_dialog_fragment");
    }

    class SamplePagerAdapter extends PagerAdapter {

        private ListView mListView;
        private NewsArrayAdapter mListViewAdapter;

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
            switch (position) {
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

            mListView = (ListView) view.findViewById(R.id.listview_news);
            mListViewAdapter = new NewsArrayAdapter(mContext, new ArrayList<News>());
            mListView.setAdapter(mListViewAdapter);
            mListView.setOnItemClickListener(HomeFragment.this);

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

        private void reloadNews(int tabIndex) {

            List<News> mNews = null;
            mListViewAdapter.clear();

            try {
                switch (tabIndex) {
                    case CALENDAR_TAB_INDEX:
                        mNews = extractNews(HomeFragment.CALENDAR_FILEPATH);
                        break;
                    case SCHEDULE_TAB_INDEX:
                        mNews = extractNews(HomeFragment.SCHEDULE_FILEPATH);
                        break;
                    default:
                }

                if (mNews != null) {
                    for (News n : mNews)
                        mListViewAdapter.add(n);
                }

            } catch (FileNotFoundException ignored) {
                Toast.makeText(mContext, mContext.getString(R.string.error_while_opening_ics), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(mContext, mContext.getString(R.string.error_on_xml_file_open), Toast.LENGTH_LONG).show();
            } catch (ParserException e) {
                Toast.makeText(mContext, mContext.getString(R.string.error_on_ics_parsing), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        // todo: extract repetitions for shcedule tab
        // todo: check if it's worth adding this data into a db instead
        private List<News> extractNews(String path) throws IOException, ParserException {
            FileInputStream fin = new FileInputStream(path);
            CalendarBuilder builder = new CalendarBuilder();
            net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

            ComponentList events = calendar.getComponents(Component.VEVENT);
            List<News> news = new ArrayList<>();
            for (Object e : events) {
                VEvent event = (VEvent) e;
                news.add(new News(event.getSummary().getValue(), event.getStartDate().getDate(), event.getDescription().getValue()));
            }
            return news;
        }

    }

}
