package com.cafejeunesse.android.structure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by David Levayer on 08/02/15.
 */
public class News {

    public final static String NEWS_TITLE = "hometitle";
    public final static String NEWS_DESCR = "homedescription";

    private String title;
    private Calendar publishingDate;
    private String article;

    public News(String title, Date d, String article) {
        this.title = title;
        this.publishingDate = Calendar.getInstance();
        publishingDate.setTime(d);
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishingDay() {
        return new SimpleDateFormat("dd").format(publishingDate.getTime());
    }

    public String getPublishingMonth() {
        return new SimpleDateFormat("MMM").format(publishingDate.getTime());
    }

    public String getArticle() {
        return article;
    }
}
