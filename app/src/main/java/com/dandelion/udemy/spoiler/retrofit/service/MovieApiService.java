package com.dandelion.udemy.spoiler.retrofit.service;

// [Imports]
import com.dandelion.udemy.spoiler.common.APICommon;
import com.dandelion.udemy.spoiler.retrofit.entity.MovieCollectionEntity;
import retrofit2.Call;
import retrofit2.http.GET;

// [Movie Api Service]: movie api management service
public interface MovieApiService {
    @GET(APICommon.API_ENDPOINT_MOVIE_POPULAR)
    Call<MovieCollectionEntity> getPopularMovies();
}
