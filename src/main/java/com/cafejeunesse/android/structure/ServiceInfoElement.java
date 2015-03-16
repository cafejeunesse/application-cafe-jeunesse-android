package com.cafejeunesse.android.structure;

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
}
