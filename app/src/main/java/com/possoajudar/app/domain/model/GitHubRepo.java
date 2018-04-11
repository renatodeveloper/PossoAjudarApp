package com.possoajudar.app.domain.model;

import com.google.gson.annotations.SerializedName;

public class GitHubRepo {

    @SerializedName("id")
    private String idRep;

    private String name;

    @SerializedName("full_name")
    private String fullName;

    public GitHubRepo() {}

    public String getId()       { return idRep; }
    public String getName()     { return name; }
    public String getFullName() { return fullName; }
}
