package com.dandelion.udemy.spoiler.data.repository;
// [Imports]
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.dandelion.udemy.spoiler.R;
import com.dandelion.udemy.spoiler.common.GlobalApp;
import com.dandelion.udemy.spoiler.common.MovieRoomDefinition;
import com.dandelion.udemy.spoiler.network.NetworkBoundResource;
import com.dandelion.udemy.spoiler.network.Resource;
import com.dandelion.udemy.spoiler.retrofit.client.MovieApiClient;
import com.dandelion.udemy.spoiler.retrofit.entity.MovieCollectionEntity;
import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;
import com.dandelion.udemy.spoiler.retrofit.interceptor.AuthInterceptor;
import com.dandelion.udemy.spoiler.retrofit.response.MoviesCollection;
import com.dandelion.udemy.spoiler.retrofit.service.MovieApiService;
import com.dandelion.udemy.spoiler.room.MovieRoom;
import com.dandelion.udemy.spoiler.room.dao.MovieDao;

import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// [Movie Repository]: movie data provider that retrieves data from service or local storage
public class MovieRepository {
    // (Vars)
    private final MovieApiService movieApiService;  // Remote data management
    private final MovieDao movieDao;                // Local data management

    // (Constructor)
    public MovieRepository() {
        movieApiService = buildApiService();    // Builds Api service to manage methods over remote server
        movieDao = buildRoomDatabase();         // Builds Room database to manage local storage
    }

    // [Methods]: remote data management

    // Builds a new API service
    private MovieApiService buildApiService() {
        MovieApiClient movieApiClient = MovieApiClient.getInstance();
        return movieApiClient.getService();
    }

    // [Methods]: local data management

    private MovieDao buildRoomDatabase() {
        MovieRoom movieRoom = Room.databaseBuilder(
                GlobalApp.getContext(),
                MovieRoom.class,
                MovieRoomDefinition.ROOM_DATABASE_NAME)
                .build();
        return movieRoom.getMovieDao();
    }

    // [Network Bound]: connection status and flow management
    // - Manages data repository feed flow by selecting when use remote data or local data
    // - Having internet connection available data will by provided by API service
    // - When internet is not available data will be manage in Room local database
    // - For more information check NetworkBoundResource in https://developer.android.com/jetpack/docs/guide

    // Gets data from available resource, remote (API) or local (Room), depending on internet availability
    public LiveData<Resource<List<MovieItemEntity>>> getPopularMovies() {
        // Parameter 1: Retrieves list of MovieEntity > Room (local)
        // Parameter 2: Retrieves list of MoviesCollection > API (remote)
        return new NetworkBoundResource<List<MovieItemEntity>,MovieCollectionEntity>() {
            // (Room)
            @Override
            protected void saveCallResult(@NonNull MovieCollectionEntity item) {
                // (Data Storage): stores response data from API into Room database
                // - Data will be available next time from local resource if needed
                movieDao.saveMovies(item.getMovieResults());
            }
            // (Room)
            @NonNull
            @Override
            protected LiveData<List<MovieItemEntity>> loadFromDb() {
                // (Local Data): gets data from Room database
                return movieDao.getPopularMovies();
            }

            @NonNull
            @Override
            protected Call<MovieCollectionEntity> createCall() {
                // (Remote Data): gets data from remote service by using API
                return movieApiService.getPopularMovies();
            }
        }.getAsLiveData();
    }
}
