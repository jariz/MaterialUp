package io.jari.materialup.models;

import java.io.Serializable;

/**
 * Created by jari on 07/06/15.
 */
public class Item implements Serializable {

    private String id;
    private String title;
    private String imageUrl;
    private String score;
    private String comments;
    private String views;
    private String makerUrl;
    private String makerAvatar;
    private String makerName;
    private String categoryLink;
    private String categoryName;
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getMakerUrl() {
        return makerUrl;
    }

    public void setMakerUrl(String makerUrl) {
        this.makerUrl = makerUrl;
    }

    public String getMakerAvatar() {
        return makerAvatar;
    }

    public void setMakerAvatar(String makerAvatar) {
        this.makerAvatar = makerAvatar;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getCategoryLink() {
        return categoryLink;
    }

    public void setCategoryLink(String categoryLink) {
        this.categoryLink = categoryLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
