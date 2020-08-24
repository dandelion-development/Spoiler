package com.dandelion.udemy.spoiler.data.viewmodel;
// [Imports]
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.dandelion.udemy.spoiler.data.repository.MovieRepository;
import com.dandelion.udemy.spoiler.network.Resource;
import com.dandelion.udemy.spoiler.retrofit.entity.MovieItemEntity;

import java.util.List;

// [Movie View Model]: does linkage between data and use interface in order prepared data to be shown
public class MovieViewModel extends ViewModel {
    // (Vars)
    private final LiveData<Resource<List<MovieItemEntity>>> popularMovies;
    private MovieRepository movieRepository;

    // (Constructor)
    public MovieViewModel() {
        movieRepository = new MovieRepository();
        popularMovies = movieRepository.getPopularMovies();
    }

    // [Methods]: public methods

    // Gets popular movies collection
    public LiveData<Resource<List<MovieItemEntity>>> getPopularMovies() {
        return popularMovies;
    }
}
