package com.dandelion.udemy.spoiler.retrofit.response;
// [Imports]
import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;

import java.util.List;

// [Movies Collection]: represents a movie list response (a collection of movie entities)
public class MoviesCollection {
    // (Vars)
    private List<MovieItemEntity> results;
    // (Getters && Setters)
    public List<MovieItemEntity> getResults() {
        return results;
    }
    public void setResults(List<MovieItemEntity> results) {
        this.results = results;
    }
}
