
package io.jari.materialup.models;

import java.util.HashMap;
import java.util.Map;


public class Maker {

    private String url;
    private String nickname;
    private String full_name;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return The full_name
     */
    public String getFullName() {
        return full_name;
    }

    /**
     * @param fullName The full_name
     */
    public void setFullName(String fullName) {
        this.full_name = fullName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
