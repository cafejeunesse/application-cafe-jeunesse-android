package com.cafejeunesse.android.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Levayer on 12/03/15.
 */
public class Service {

    private String name;
    private String phoneNumber;
    private String address;
    private String information;
    private List<String> services;
    private String priceInformation;
    private String webSite;
    private String facebookPage;
    private List<String> categories;
    private List<String> keywords;

    public Service(){
        services = new ArrayList<String>();
        categories = new ArrayList<String>();
        keywords = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation() {
        return information;
    }

    public List<String> getServices() {
        return services;
    }

    public String getPriceInformation() {
        return priceInformation;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public void setPriceInformation(String priceInformation) {
        this.priceInformation = priceInformation;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
