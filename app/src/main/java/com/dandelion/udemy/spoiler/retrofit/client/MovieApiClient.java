package com.dandelion.udemy.spoiler.retrofit.client;

// [Imports]
import com.dandelion.udemy.spoiler.common.APICommon;
import com.dandelion.udemy.spoiler.retrofit.interceptor.AuthInterceptor;
import com.dandelion.udemy.spoiler.retrofit.service.MovieApiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// [Movie Api Client]: provides requests management to API by using service class
public class MovieApiClient {
    // (Vars)
    private static MovieApiClient instance = null;
    private MovieApiService movieApiService;
    private Retrofit retrofit;

    // (Constructor)
    public MovieApiClient() {
        // Makes an instance of Retrofit for service structure building
        retrofit = new Retrofit.Builder()
                .baseUrl(APICommon.API_BASE_URL)                        // API base url
                .addConverterFactory(GsonConverterFactory.create())     // GSon conversion library
                .client(new OkHttpClient.Builder()                      // Auth interceptor integrated client
                        .addInterceptor(new AuthInterceptor())
                        .build())
                .build();
        // Makes an instance of service (based on Retrofit build parameters)
        movieApiService = retrofit.create(MovieApiService.class);
    }

    // [Methods]: client available methods

    // Makes an instance of client (based on singleton pattern in order to preserve resources)
    public static MovieApiClient getInstance() {
        // If don't have a previous instance of MiniTwitterClient new instance is created
        // ** Singleton pattern model (in order to avoid use resources and preserve memory)
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    // Gets a service instance (that is created in constructor as default)
    public MovieApiService getService() {
        return movieApiService;
    }
}
