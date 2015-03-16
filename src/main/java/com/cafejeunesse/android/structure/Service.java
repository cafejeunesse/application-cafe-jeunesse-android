package com.cafejeunesse.android.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David Levayer on 12/03/15.
 */
public class Service {

    private Map<String,Object> serviceData;

    public Service(){

        serviceData = new HashMap<String,Object>();
    }

    public void addInfo(String tag, Object value){
        serviceData.put(tag,value);
    }

    public Object getInfo(String tag){
        if(serviceData.containsKey(tag))
            return serviceData.get(tag);
        else return null;
    }

}
