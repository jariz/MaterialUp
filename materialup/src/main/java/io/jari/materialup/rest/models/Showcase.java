
package io.jari.materialup.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Showcase {

    private String name;

    private Integer id;

    private String slug;

    private String url;

    private String label;

    @SerializedName("redirect_url")
    private String redirectUrl;

    private Thumbnails thumbnails;

    @SerializedName("upvotes_count")
    private Integer upvotesCount;

    @SerializedName("comments_count")
    private Integer commentsCount;

    @SerializedName("view_count")
    private Integer viewCount;

    private String platform;

    private Source source;

    @SerializedName("published_at")
    private String publishedAt;

    private Submitter submitter;

    private List<Maker> makers = new ArrayList<>();

    private Category category;

    public String getName() {
        return name;
    }
}
