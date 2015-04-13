package com.cafejeunesse.android.structure;

import com.cafejeunesse.android.navigationdrawer.R;

import java.io.Serializable;

/**
 * Created by David Levayer on 16/03/15.
 */
public class ServiceInfoElement implements Serializable {

    private String tag;
    private Object value;

    public ServiceInfoElement(String tag, Object value){
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public Object getValue() {
        return value;
    }

    public int getImageResource() {
        return getImageResource(tag);
    }

    private int getImageResource(String tag) {
        switch (tag) {
            case Service.TAG_PHONENUMBER:
                return R.drawable.icon_blue_phone;
            case Service.TAG_ADDRESS:
                return R.drawable.icon_blue_map_marker;
            case Service.TAG_SERVICES:
                return R.drawable.icon_blue_list;
            case Service.TAG_PRICE:
                return R.drawable.icon_blue_usd;
            case Service.TAG_WEBSITE:
                return R.drawable.icon_blue_globe;
            case Service.TAG_FACEBOOK:
                return R.drawable.icon_blue_facebook;
            case Service.TAG_CATEGORIES:
//                return R.drawable.icon_folders;
            case Service.TAG_KEYWORDS:
//                return R.drawable.icon_tags;
            default:
                return R.drawable.oval_classic;

        }
    }
}
