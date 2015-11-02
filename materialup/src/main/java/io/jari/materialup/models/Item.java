
package io.jari.materialup.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

    private String name;
    private int id;
    private String slug;
    private String url;
    private String label;
    private String redirect_url;
    private Thumbnails thumbnails;
    private int upvotes_count;
    private int comments_count;
    private int view_count;
    private String platform;
    private Source source;
    private String published_at;
    private Submitter submitter;
    private List<Maker> makers = new ArrayList<Maker>();
    private Category category;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     *
     * @param slug
     *     The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     *
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     *     The redirect_url
     */
    public String getRedirectUrl() {
        return redirect_url;
    }

    /**
     *
     * @param redirectUrl
     *     The redirect_url
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirect_url = redirectUrl;
    }

    /**
     *
     * @return
     *     The thumbnails
     */
    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    /**
     *
     * @param thumbnails
     *     The thumbnails
     */
    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    /**
     *
     * @return
     *     The upvotes_count
     */
    public int getUpvotesCount() {
        return upvotes_count;
    }

    /**
     *
     * @param upvotesCount
     *     The upvotes_count
     */
    public void setUpvotesCount(int upvotesCount) {
        this.upvotes_count = upvotesCount;
    }

    /**
     *
     * @return
     *     The comments_count
     */
    public int getCommentsCount() {
        return comments_count;
    }

    /**
     *
     * @param commentsCount
     *     The comments_count
     */
    public void setCommentsCount(int commentsCount) {
        this.comments_count = commentsCount;
    }

    /**
     *
     * @return
     *     The view_count
     */
    public int getViewCount() {
        return view_count;
    }

    /**
     *
     * @param viewCount
     *     The view_count
     */
    public void setViewCount(int viewCount) {
        this.view_count = viewCount;
    }

    /**
     *
     * @return
     *     The platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     *
     * @param platform
     *     The platform
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     *
     * @return
     *     The source
     */
    public Source getSource() {
        return source;
    }

    /**
     *
     * @param source
     *     The source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     *
     * @return
     *     The published_at
     */
    public String getPublishedAt() {
        return published_at;
    }

    /**
     *
     * @param publishedAt
     *     The published_at
     */
    public void setPublishedAt(String publishedAt) {
        this.published_at = publishedAt;
    }

    /**
     *
     * @return
     *     The submitter
     */
    public Submitter getSubmitter() {
        return submitter;
    }

    /**
     * @param submitter The submitter
     */
    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    /**
     * @return The makers
     */
    public List<Maker> getMakers() {
        return makers;
    }

    /**
     * @param makers The makers
     */
    public void setMakers(List<Maker> makers) {
        this.makers = makers;
    }

    /**
     * @return The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
