package com.cafejeunesse.android.structure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by David Levayer on 08/02/15.
 */
public class News {
    private String title;
    private Calendar publishingDate;
    private String article;

    public News (String title, Date d, String article){
        this.title = title;
        this.publishingDate = Calendar.getInstance();
        publishingDate.setTime(d);
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishingDay() {
        /*
        int day = publishingDate.get(Calendar.DAY_OF_MONTH);
        String res = String.valueOf(day);
        if(day<=9)
            res = "0".concat(res);
        return res;*/
        return new SimpleDateFormat("dd").format(publishingDate.getTime());
    }

    public String getPublishingMonth(){
        return new SimpleDateFormat("MMM").format(publishingDate.getTime());
    }

    public String getArticle() {
        return article;
    }
}
