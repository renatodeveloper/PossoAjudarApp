package com.possoajudar.app.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by renato on 14/02/2018.
 */

public class MoviesResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<Movie> getResults() {
        return results;
    }
}
