package com.cafejeunesse.android.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David Levayer on 12/03/15.
 */
public class Service {

    public final static String SERVICE_MAP_INFO = "myServiceMap";
    public final static String SERVICE_TITLE = "myServiceTitle";
    public final static String SERVICE_DESCR = "myServiceDescription";

    public final static String TAG_PHONENUMBER = "service_phone";
    public final static String TAG_ADDRESS = "service_address";
    public final static String TAG_SERVICES = "service_services";
    public final static String TAG_PRICE = "service_price";
    public final static String TAG_WEBSITE = "service_website";
    public final static String TAG_FACEBOOK = "service_facebook";
    public final static String TAG_CATEGORIES = "service_categories";
    public final static String TAG_KEYWORDS = "service_keywords";

    private long id;
    private String serviceName;
    private String serviceDescription;
    private float lat, lon;
    private Map<String,Object> serviceData;

    public Service(){
        serviceData = new HashMap<String,Object>();
    }

    public Service(String name, String description){
        this.serviceName = name;
        this.serviceDescription = description;
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

    public HashMap getMapInfo(){
        return (HashMap)serviceData;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription){
        this.serviceDescription = serviceDescription;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
