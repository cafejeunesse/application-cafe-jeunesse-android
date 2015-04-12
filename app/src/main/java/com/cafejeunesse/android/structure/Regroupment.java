package com.cafejeunesse.android.structure;

import java.util.Objects;

/**
 * Created by Gabriel Le Breton on 12/04/15.
 */
public class Regroupment {

    private String title;
    private String acronyme;
    private String url;

    public Regroupment(String title, String acronyme, String url) {
        this.title = title;
        this.acronyme = acronyme;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        if (!Objects.equals(acronyme, "")) {
            return title + " (" + acronyme + ")";
        } else {
            return title;
        }
    }
}
