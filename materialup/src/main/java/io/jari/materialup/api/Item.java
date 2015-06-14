package io.jari.materialup.api;

import java.io.Serializable;

/**
 * Created by jari on 07/06/15.
 */
public class Item implements Serializable {
    public String id;

    public String title;
    public String imageUrl;
    public String score;
    public String comments;
    public String views;

    public String makerUrl;
    public String makerAvatar;
    public String makerName;

    public String categoryLink;
    public String categoryName;

    public String label;
}
