package com.ssf.smart;

import java.io.Serializable;

/**
 * Created by Android on 2015/10/31.
 */
public class GridBean implements Serializable {
    private String title;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
