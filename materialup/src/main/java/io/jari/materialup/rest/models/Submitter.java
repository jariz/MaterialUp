
package io.jari.materialup.rest.models;

import com.google.gson.annotations.SerializedName;

public class Submitter {

    private String url;
    private String nickname;

    @SerializedName("full_name")
    private String fullName;

}
