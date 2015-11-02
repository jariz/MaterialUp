
package io.jari.materialup.models;

import java.util.HashMap;
import java.util.Map;


public class Thumbnails {

    private String teaser_url;
    private String preview_url;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The teaser_url
     */
    public String getTeaserUrl() {
        return teaser_url;
    }

    /**
     * @param teaserUrl The teaser_url
     */
    public void setTeaserUrl(String teaserUrl) {
        this.teaser_url = teaserUrl;
    }

    /**
     * @return The preview_url
     */
    public String getPreviewUrl() {
        return preview_url;
    }

    /**
     * @param previewUrl The preview_url
     */
    public void setPreviewUrl(String previewUrl) {
        this.preview_url = previewUrl;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
