
package io.jari.materialup.models;

import java.util.HashMap;
import java.util.Map;


public class Source {

    private String name;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
